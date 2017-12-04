/*
 * Copyright (C) 2016 anto
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package amr2fred;

import static amr2fred.Glossary.ENDLESS;
import static amr2fred.Glossary.FRED;
import static amr2fred.Glossary.NULL;
import static amr2fred.Glossary.PIVOT;
import static amr2fred.Glossary.TOP;
import static amr2fred.Glossary.VN_ROLE;
import static amr2fred.Glossary.NodeStatus.AMR;
import static amr2fred.Glossary.NodeStatus.OK;
import static amr2fred.Glossary.NodeStatus.REMOVE;
import static amr2fred.Glossary.NodeType.VERB;
import java.util.ArrayList;
import java.util.Iterator;
import static amr2fred.Glossary.AMR_ARG;
import static amr2fred.Glossary.AMR_ARG0;
import static amr2fred.Glossary.AMR_ARG1;
import static amr2fred.Glossary.AMR_ARG2;

/**
 * Contains methods for parsing and translating from AMR to FRED
 *
 * @author anto
 */
public class Parser {

    //contiene l'istanza singleton del parser
    private static Parser parser;

    //contiene tutti i nodi istanziati e consente di recuperare il nodo relativo a una variabile amr
    private ArrayList<Node> nodes;

    //contiene le coppie formate dalle var trovate e dal numero di occorrenze di ognuna
    private ArrayList<Couple> couples;

    //contiene i nodi rimossi
    private ArrayList<Node> removed;

    //contiene i nodi da aggiungere ad un nodo alla fine della iterazione di lavorazione della lista (usato per evitare errori di concurrency)
    private ArrayList<Node> toAdd;

    //contiene una copia dell'albero amr in lavorazione
    private Node rootCopy;

    //contiene il valore numerico delle operazioni di visita dei nodi del grafo amr - interrompe le operazioni in caso di errore di ricorsività
    static int endless;

    //flag per l'aggiunta del valore topic al nodo radice
    private boolean topic;

    private Parser() {
        this.nodes = new ArrayList<>();
        this.couples = new ArrayList<>();
        this.removed = new ArrayList<>();
        this.toAdd = new ArrayList<>();
    }

    /**
     * Return Parser singleton instance
     *
     * @return Parser instance
     */
    public static Parser getInstance() {
        if (parser == null) {
            parser = new Parser();
        }
        parser.nodes = new ArrayList<>();
        parser.couples = new ArrayList<>();
        parser.removed = new ArrayList<>();
        parser.toAdd = new ArrayList<>();
        parser.rootCopy = null;
        Node.id = 0;
        parser.topic = true;
        return parser;
    }

    /**
     * Return the copy of the root node used, before elaboration
     *
     * @return Node
     */
    public Node getRootCopy() {
        return rootCopy;
    }

    /**
     * Return the list of node removed becouse of errors
     *
     * @return ArrayList of Node
     */
    public ArrayList<Node> getRemoved() {
        return removed;
    }

    /**
     * Parse AMR string and returns Fred root node
     *
     * @param amr String in amr format
     * @return Node Fred root node
     */
    public Node parse(String amr) {
        /*
        Il nodo root contiene la struttura dati che si ottiene
        passando la stringa amr al metodo string2Array e passando 
        il vettore ottenuto al metodo getNodes insieme alla relazione 
        speciale per il nodo radice.
         */
        Node root = getNodes(TOP, string2Array(amr));

        if (root != null) {
            endless = 0;
            this.rootCopy = root.getCopy();
            //verifica errore per ricorsione
            if (endless > ENDLESS) {
                this.rootCopy = new Node("Error", "Recursive");
                return root;
            }
            //richiama il metodo che effettua la traduzione delle relazioni e dei valori
            root = fredTranslate(root);
            //richiama il metodo che disambigua i verbi ed esplicita i ruoli dei predicati anonimi
            root = findVnClass(root);
            //verifica la necessità di inserire il nodo speciale TOPIC
            root = topic(root);
        }

        return root;
    }

    /**
     * Return the tree without the nodes with translate errors
     *
     * @param root Node
     * @return root Node without errors
     */
    public Node check(Node root) {

        for (Iterator<Node> it = root.list.iterator(); it.hasNext();) {
            Node n = it.next();
            if (n.getStatus() != OK) {
                this.removed.add(n);
                it.remove();
            } else {
                n = check(n);
            }
        }
        if (root.getStatus() != OK) {
            return null;
        }
        return root;
    }

    /*
    Restituisce un arraylist contenente i singoli elementi individuati nella stringa
    in formato amr in input.
     */
    private ArrayList<String> string2Array(String amr) {
        int inizio, fine;
        String word;
        ArrayList<String> list = new ArrayList<>();

        amr = normalize(amr);

        try {
            while (amr.length() > 1) {
                inizio = amr.indexOf(" ") + 1;
                fine = amr.indexOf(" ", inizio);
                word = amr.substring(inizio, fine);
                amr = amr.substring(fine);
                if (!word.startsWith(Glossary.QUOTE)) {
                    list.add(word.toLowerCase());
                } else {
                    list.add(word.replaceAll(Glossary.QUOTE, ""));
                }

            }
        } catch (java.lang.StringIndexOutOfBoundsException e) {
            return null;
        }

        return list;
    }

    /*
    Normalizza l'input inserendo degli spazi prima e dopo gli elementi,
    porta tutto in minuscolo ed elimina i doppi spazi
     */
    private String normalize(String amr) {

        amr = patch(amr);

        amr = amr.replace("(", " ( ");
        amr = amr.replace(")", " ) ");
        amr = amr.replace("/", " / ");
        amr = amr.replaceAll("\r\n|\r|\n", " ");
        amr = amr.replaceAll("\t", " ");

        while (amr.contains("  ")) {
            amr = amr.replace("  ", " ");
        }

        return amr;
    }

    /*
    Trasforma la lista di stringhe in formato amr in una struttura ad albero
    (il grafo viene di fatto privato dei cicli con lo stratagemma dell'utilizzo
    di un oggetto nodo diverso (a livello di oggetto java)
    quando si hanno rami che puntano allo stesso nodo, ma gli oggetti sono
    equivalenti secondo il metodo equals in override.
    Viene effettuato un rudimentale controllo sulle parentesi
    tonde ancora aperte una volta elaborato il contenuto della lista.
     */
    private Node getNodes(String relation, ArrayList<String> amrList) {

        if (amrList == null) {
            return null;
        }

        Node root = new Node(amrList.get(1), relation);
        this.nodes.add(root);
        String word, word2;
        int liv = 0;
        int liv2 = 0;
        boolean flag;
        ArrayList<String> newList = null;

        for (int i = 0; i < amrList.size(); i++) {
            word = amrList.get(i);
            switch (word) {
                case "(":
                    liv++;
                    if (liv == 2) {
                        liv2 = 0;
                        newList = new ArrayList<>();
                        for (int j = i; j < amrList.size(); j++) {
                            word2 = amrList.get(j);
                            switch (word2) {
                                case "(":
                                    liv2++;
                                    newList.add(word2);
                                    break;
                                case ")":
                                    liv2--;
                                    newList.add(word2);
                                    if (liv2 == 0) {
                                        /*
                                        aggiunge alla lista dei nodi quello ottenuto
                                        chiamando ricorsivamente il metodo corrente e passando come argomento 
                                        la lista di parole racchiuse tra le corrispondenti parentesi
                                         */
                                        root.list.add(getNodes(amrList.get(i - 1), newList));
                                        i = j;
                                        j = amrList.size();
                                        liv--;
                                    }
                                    break;
                                default:
                                    newList.add(word2);
                            }
                        }
                    }
                    break;
                case ")":
                    liv--;
                    break;
                case "/":
                    //aggiunge il nodo instance
                    root.list.add(new Node(amrList.get(++i), Glossary.INSTANCE));
                    break;
                default:
                    //verifica riutilizzo variabili
                    if (word.charAt(0) == ':' && amrList.get(i + 1).charAt(0) != '(') {
                        flag = false;
                        for (Node find : this.nodes) {
                            if (find.var.equalsIgnoreCase(amrList.get(i + 1))) {
                                Node newNode = find.getCopy(/*find,*/word);
                                root.list.add(newNode);
                                nodes.add(newNode);
                                flag = true;
                                break;
                            }
                        }

                        if (flag == false) {
                            //aggiunta di un nuovo nodo in caso di verifica negativa
                            root.list.add(new Node(amrList.get(i + 1), word));
                        }
                    }
            }
        }

        if (liv != 0) {
            return null;
        }

        return root;
    }

    /*
    traduce il nodo da amr a fred
    viene usato ricorsivamente dai tre metodi che richiama, a seconda delle condizioni
    che si verificano
     */
    private Node fredTranslate(Node root) {

        if (root == null) {
            return null;
        } else if (root.list.isEmpty()) {
            setEquals(root); //verificare comportamento
            return root;
        }

        if (endless > Glossary.ENDLESS) {
            return root;
        }

        //verifica inversi
        root = this.inverseChecker(root);

        //verifica rate-entity-91
        root = this.rateEntity(root);

        // verifica reifing
        root = this.reifi(root);

        //Elaborazione della lista dei nodi contenuti nel nodo attualmente in lavorazione
        root = this.listElaboration(root);

        //elaborazione del nodo figlio denominato instance in amr
        root = this.instanceElaboration(root);

        return root;
    }

    /*
    Controlla ed elabora alcuni casi particolari di verbi che introducono possibilità oppure obbligo
     */
    private Node modality(Node root) {
        //TODO eliminare i magic numbers
        if (root == null || root.getInstance() == null || root.getInstance().var.length() < 4) {
            return root;
        }
        Node instance = root.getInstance();
        String lemma = instance.var.substring(0, instance.var.length() - 3);

        if (lemma.equalsIgnoreCase("likely") || lemma.equalsIgnoreCase("recommend")) {
            Node arg1 = root.getChild(Glossary.AMR_ARG1);
            if (arg1 != null) {
                root.var = arg1.var;
                root.list.add(new Node(Glossary.BOXING_NECESSARY, Glossary.BOXING_HAS_MODALITY, OK));
                root.list.addAll(arg1.list);
                root.list.remove(arg1);
                root.list.remove(instance);
                root = modality(root);
                root.setStatus(OK);

            }
        } else if (lemma.equalsIgnoreCase("obligate")) {
            Node arg2 = root.getChild(Glossary.AMR_ARG2);
            if (arg2 != null) {
                root.var = arg2.var;
                root.list.add(new Node(Glossary.BOXING_NECESSARY, Glossary.BOXING_HAS_MODALITY, OK));
                root.list.addAll(arg2.list);
                root.list.remove(arg2);
                root.list.remove(instance);
                root = modality(root);
                root.setStatus(OK);
            }
        } else if (lemma.equalsIgnoreCase("possible") || lemma.equalsIgnoreCase("permit")) {
            Node arg1 = root.getChild(Glossary.AMR_ARG1);
            if (arg1 != null) {
                root.var = arg1.var;
                root.list.add(new Node(Glossary.BOXING_POSSIBLE, Glossary.BOXING_HAS_MODALITY, OK));
                root.list.addAll(arg1.list);
                root.list.remove(arg1);
                root.list.remove(instance);
                root = modality(root);
                root.setStatus(OK);
            }

        }
        return root;
    }

    /*
    Elabora il nodo instance e verifica se il nodo è un verbo oppure un :arg di un verbo
    chiama ricorsivamente se stessa sulla lista dei nodi figli
     */
    private Node instanceElaboration(Node root) {
        //Interpretazione verbi ed eliminazione instance
        Node instance = root.getInstance();

        if (root.getStatus() == OK && root.relation.startsWith(Glossary.AMR_RELATION_BEGIN)
                && !root.relation.equalsIgnoreCase(TOP)) {
            root.setStatus(Glossary.NodeStatus.AMR);
            return root;
        }

        if (root.getStatus() != OK && !root.relation.startsWith(Glossary.AMR_RELATION_BEGIN)
                && !root.relation.equalsIgnoreCase(TOP)) {
            root.setStatus(OK);
        }

        if (instance != null) {

            if (instance.var.length() > 3 && instance.var.substring(instance.var.length() - 3).matches(Glossary.AMR_VERB)) {
                if (this.isVerb(instance.var)) {
                    root.setType(VERB);
                    topic = false;
                    instance.list.add(new Node(Glossary.DUL_EVENT, Glossary.RDFS_SUBCLASS_OF, OK));
                }

                root = modality(root);
                if (root.getStatus() == OK) {

                    instance = root.getInstance();
                    topic = false;
                    root.setType(VERB);
                }

                //Elaborazione della instance e trasferimento verbo nella root, seguito dal numero di occorrenza
                root.var = FRED + instance.var.substring(0, instance.var.length() - 3) + "_" + occurrence(instance.var.substring(0, instance.var.length() - 3));
                instance.relation = Glossary.RDF_TYPE;
                root.setVerb(Glossary.ID + instance.var.replace('-', '.'));

                args(root);
                instance.var = FRED + firstUpper(instance.var.substring(0, instance.var.length() - 3));

                if (!instance.relation.startsWith(Glossary.AMR_RELATION_BEGIN)) {
                    instance.setStatus(OK);
                } else {
                    instance.setStatus(AMR);
                }
                if (!root.relation.startsWith(Glossary.AMR_RELATION_BEGIN)) {
                    root.setStatus(OK);
                } else {
                    root.setStatus(AMR);
                }

            } else {

                //Controllo ed elaborazione instance sui nodi "uguali" per le altre cose
                root = this.otherInstanceElaboration(root);

                if (!root.relation.startsWith(Glossary.AMR_RELATION_BEGIN)) {
                    /*
                    se la relation di root è stata elaborata e non è più espressa in 
                    AMR il nodo è ok altrimenti deve essere ancora lavorato
                     */
                    root.setStatus(OK);
                } else {
                    root.setStatus(AMR);
                }
            }
        }
        
        for (Node uguale:this.nodes){
            if (uguale.equals(root)){
                uguale.var=root.var;
            }
        }

        for (Node n : root.list) {

            // Chiamata ricorsiva
            n = instanceElaboration(n);
        }

        return root;
    }

    /*
    Elabora la Node list del nodo
     */
    private Node listElaboration(Node root) {

        root = modality(root);

        //root = reifi(root);
        root = rootElaboration(root);

        root = dateEntity(root);

        root = prepControl(root);

        if (root.list.isEmpty()) {
            return root;
        }

        for (Iterator<Node> it = root.list.iterator(); it.hasNext();) {
            Node n = it.next();

            n = prepControl(n);

            if (n.relation.equals(Glossary.PREP_SUBSTITUTION)) {
                n.setStatus(REMOVE);
                toAdd.addAll(n.list);
            }

            if (n.relation.equalsIgnoreCase(Glossary.AMR_DOMAIN)) {
                topic = false;
            }

            // casi "and" ed "or" seguito da :op in lista
            if (n.getInstance() != null && (n.getInstance().var.equalsIgnoreCase(Glossary.AND)
                    || n.getInstance().var.equalsIgnoreCase(Glossary.OR))) {

                n.list.remove(n.getInstance());
                ArrayList<Node> ops = n.getOps();
                for (Node n1 : ops) {
                    n.list.remove(n1);
                }

                for (Node n1 : ops) {
                    n1.relation = n.relation;
                    n1.list.addAll(n.list);
                    this.toAdd.add(n1);

                }
                n.setStatus(REMOVE);

            } else if (Glossary.PERSON.contains(" " + n.var + " ")) {

                //casi speciali con pronomi personali e aggettivi dimostrativi
                n.var = Glossary.FRED_PERSON;
                this.setEquals(root);

            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_NAME)) {
                if(root.getPoss()!=null && root.getInstance() != null){
                     

                // caso :poss
                root.getPoss().relation = FRED +(root.getInstance().var.replaceAll(FRED, "") + Glossary.OF);
                //root.getPoss().setStatus(OK);

            
                }
                //caso :name
                ArrayList<Node> ops = n.getOps();
                if (!ops.isEmpty()) {
                    String name = "";
                    for (Node n1 : ops) {
                        name += "_" + n1.var;
                    }
                    name = FRED + name.substring(1);
                    root.var = name;
                    if (root.getInstance() != null) {
                        root.getInstance().var = FRED + firstUpper(root.getInstance().var);
                        root.getInstance().setStatus(OK);
                        root.getInstance().relation = Glossary.RDF_TYPE;
                        root.setStatus(OK);
                        setEquals(root);
                    }

                }
                n.setStatus(REMOVE);

            }
            
            

            if (n.relation.equalsIgnoreCase(Glossary.AMR_WIKI) && root.getInstance() != null
                    && n.getChild(Glossary.RDF_TYPE) == null) {

                //caso :wiki + schemaorg su nodo wiki
                //TODO da implementare verifica sul sito dell'esistenza della parola
                n.list.add(new Node(Glossary.SCHEMA_ORG + firstUpper(root.getInstance().var), Glossary.RDF_TYPE, OK));

            } else if (n.getInstance() != null && n.getChild(Glossary.AMR_WIKI) != null) {

                //caso :wiki + schemaorg su nodo root
                //TODO da implementare verifica sul sito dell'esistenza della parola
                if (n.getChild(Glossary.AMR_WIKI).getChild(Glossary.RDF_TYPE) == null) {
                    n.getChild(Glossary.AMR_WIKI).list
                            .add(new Node(Glossary.SCHEMA_ORG + firstUpper(n.getInstance().var), Glossary.RDF_TYPE, OK));
                }
            }

            if (n.relation.equalsIgnoreCase(Glossary.AMR_WIKI)) {

                //caso :wiki
                if (!n.var.equalsIgnoreCase(Glossary.AMR_MINUS)) {
                    n.relation = Glossary.OWL_SAME_AS;
                    n.var = Glossary.DBPEDIA + n.var;
                    n.setStatus(OK);
                } else {
                    n.setStatus(REMOVE);
                }

            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_MODE)
                    //Rimozione casi non supportati da Fred
                    && (n.var.equalsIgnoreCase(Glossary.AMR_IMPERATIVE)
                    || n.var.equalsIgnoreCase(Glossary.AMR_EXPRESSIVE))
                    || n.var.equalsIgnoreCase(Glossary.AMR_INTERROGATIVE)) {
                n.setStatus(REMOVE);

            } else if (n.getInstance() != null && n.getInstance().var.equalsIgnoreCase(Glossary.AMR_UNKNOWN)) {
                n.setStatus(REMOVE);

            } else if (Glossary.MALE.contains(" " + n.var + " ")) {

                //casi speciali con pronomi personali e aggettivi dimostrativi
                n.var = Glossary.FRED_MALE;
                this.setEquals(root);

            } else if (Glossary.FEMALE.contains(" " + n.var + " ")) {

                //casi speciali con pronomi personali e aggettivi dimostrativi
                n.var = Glossary.FRED_FEMALE;
                this.setEquals(root);

            } else if (Glossary.THING.contains(" " + n.var + " ")) {

                //casi speciali con pronomi personali e aggettivi dimostrativi
                n.var = Glossary.FRED_NEUTER;
                n.list.add(new Node(Glossary.OWL_THING, Glossary.RDF_TYPE, OK));
                this.setEquals(root);
                n.setStatus(OK);

            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_POSS) && root.getInstance() != null) {

                // caso :poss
                n.relation = FRED + (root.getInstance().var.replaceAll(FRED, "") + Glossary.OF);
                n.setStatus(OK);

            } else if ((n.relation.equalsIgnoreCase(Glossary.AMR_MOD) || n.relation.equalsIgnoreCase(Glossary.AMR_DOMAIN))
                    && n.getInstance() != null && Glossary.DEMONSTRATIVES.contains(" " + n.getInstance().var + " ")) {

                // caso pronomi o aggettivi dimostrativi
                n.relation = Glossary.QUANT_HAS_DETERMINER;
                n.var = FRED + firstUpper(n.getInstance().var);
                n.list.remove(n.getInstance());
                n.setStatus(OK);

            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_DOMAIN) && n.getInstance() != null && root.getInstance() != null) {

                //caso :domain+aggettivo o :domain+nome
                boolean flag = false;
                for (String s : Glossary.ADJECTIVE) {
                    if (s.equalsIgnoreCase(root.getInstance().var)) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    String swap = root.getInstance().var;
                    root.getInstance().var = n.getInstance().var;
                    n.getInstance().var = swap;
                    n.relation = Glossary.DUL_HAS_QUALITY;
                    n.var = FRED + firstUpper(n.getInstance().var);
                    n.list.remove(n.getInstance());
                } else {
                    String swap = root.getInstance().var;
                    root.getInstance().var = n.getInstance().var;
                    n.getInstance().var = swap;
                    n.relation = Glossary.RDF_TYPE;
                    n.var = FRED + firstUpper(n.getInstance().var);
                    n.list.remove(n.getInstance());
                }
                n.setStatus(OK);

            } else if ((n.relation.equalsIgnoreCase(Glossary.AMR_QUANT)
                    || (n.relation.equalsIgnoreCase(Glossary.AMR_FREQUENCY) && n.var.matches(Glossary.NN_INTEGER))) && n.getInstance() == null) {

                //casi :quant  e :frequency con valore numerico 
                n.relation = Glossary.DUL_HAS_DATA_VALUE;
                if ((n.var.matches(Glossary.NN_INTEGER) && Integer.parseInt(n.var) != 1) || !n.var.matches(Glossary.NN_INTEGER)) {
                    toAdd.add(new Node(Glossary.QUANT + Glossary.FRED_MULTIPLE, Glossary.QUANT_HAS_QUANTIFIER, OK));
                }

                n.setStatus(OK);

            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_QUANT) && n.getInstance() != null
                    && !n.getInstance().var.matches(Glossary.AMR_QUANTITY)) {

                //caso :quant  con instance non nulla - valore non numerico
                ArrayList<Node> ops = n.getOps();

                if (ops != null) {
                    for (Node n1 : ops) {
                        n.list.remove(n1);
                        n1.relation = Glossary.DUL_HAS_DATA_VALUE;
                        toAdd.add(n1);
                        n1.setStatus(OK);
                    }
                }

                n.relation = Glossary.DUL_HAS_QUALITY;
                n.var = FRED + firstUpper(n.getInstance().var.replaceAll(FRED, ""));
                n.list.remove(n.getInstance());
                toAdd.add(new Node(Glossary.QUANT + Glossary.FRED_MULTIPLE, Glossary.QUANT_HAS_QUANTIFIER, OK));
                n.setStatus(OK);

            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_MOD) && n.getInstance() != null
                    && !isVerb(n.getInstance().var)) {

                //caso :mod
                n.relation = Glossary.DUL_HAS_QUALITY;
                n.var = FRED + firstUpper(n.getInstance().var);
                n.list.remove(n.getInstance());
                n.setStatus(OK);

            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_AGE) && root.getInstance() != null) {

                //caso :age
                String age = n.var;
                n.relation = TOP;
                n.var = "a";
                n.list.add(new Node("age-01", Glossary.INSTANCE));
                Node n1 = root.getCopy(AMR_ARG1);
                this.nodes.add(n1);
                n.list.add(n1);
                n.list.add(new Node(age, AMR_ARG2));
                n = listElaboration(n);

            } else if ((n.relation.equalsIgnoreCase(Glossary.AMR_DEGREE)
                    || n.relation.equalsIgnoreCase(Glossary.AMR_TIME)) && n.getInstance() != null
                    && !isVerb(n.getInstance().var)) {

                //casi :degree :time con instance
                n.var = FRED + n.getInstance().var;
                n.list.remove(n.getInstance());

            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_MANNER) && n.getInstance() != null
                    && !isVerb(n.getInstance().var)) {
                //caso :manner con forma verbale
                if (n.getInstance().var.matches(Glossary.AMR_VERB2) || !mannerAdverb(n.getInstance().var).isEmpty()) {

                    if (!mannerAdverb(n.getInstance().var).isEmpty()) {
                        n.var = FRED + firstUpper(mannerAdverb(n.getInstance().var));
                    } else {
                        n.var = FRED + firstUpper(n.getInstance().var.substring(0, n.getInstance().var.length() - 3) + "ly");
                    }
                    n.list.remove(n.getInstance());
                } else {

                    //caso :manner non verbale
                    n.relation = Glossary.VN_ROLE_LOCATION;
                }
            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_MANNER) && n.getInstance() != null && root.getInstance() != null
                    && isVerb(n.getInstance().var)) {
                // caso manner verbale 
                n.relation = FRED + root.getInstance().var.substring(0, n.getInstance().var.length() - 3) + Glossary.BY;

            } else if (n.relation.startsWith(Glossary.AMR_PREP)) {
                n.relation = n.relation.replace(Glossary.AMR_PREP, FRED);
            } else if ((n.relation.equalsIgnoreCase(Glossary.AMR_PART_OF)
                    || n.relation.equalsIgnoreCase(Glossary.AMR_CONSIST_OF)) && root.getInstance() != null) {
                n.relation =FRED+ root.getInstance().var + Glossary.OF;
            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_EXTENT) && n.getInstance() != null) {
                n.var = FRED + firstUpper(n.getInstance().var);
                n.list.remove(n.getInstance());
            }
            
            if(n.relation.equalsIgnoreCase(Glossary.AMR_VALUE) && n.getInstance()==null){
                if (n.var.matches(Glossary.NN_INTEGER2) || n.var.matches(Glossary.NN_INTEGER)){
                    n.relation=Glossary.DUL_HAS_DATA_VALUE;
                } else {
                    n.relation=Glossary.DUL_HAS_QUALITY;
                    n.var=FRED+n.var;
                }
            }
            
            if (n.var.contains(Glossary.AMR_ENTITY)){
                n.var=n.var.replace(Glossary.AMR_ENTITY, "");
            }

            if (n.getStatus() != REMOVE) {

                /*
                procedimento per sostituzione mediante Glossary quando non occorre
                nient'altro che una traduzione di node.relation e node.var
                 */
                for (int num = 0; num < Glossary.PATTERNS_NUMBER; num++) {

                    if (n.relation.equalsIgnoreCase(Glossary.AMR_RELATIONS[num])
                            && n.var.matches(Glossary.AMR_VARS[num])) {

                        if (Glossary.FRED_RELATIONS[num].length() > 0) {
                            n.relation = Glossary.FRED_RELATIONS[num];

                        }
                        if (Glossary.FRED_VARS[num].length() > 0) {
                            n.var = Glossary.FRED_VARS[num];
                        }
                        n.setStatus(OK);
                    }
                }
            }

            ArrayList<Node> ops = n.getOps();
            if (!ops.isEmpty()) {
                n.list.removeAll(ops);
                for (Node n1 : ops) {
                    n1.relation = n.relation;
                    toAdd.add(n1);
                }
                n.relation = Glossary.DUL_ASSOCIATED_WITH;
                Node newN = new Node("", "");
                newN.substitute(n);
                n.setStatus(REMOVE);
                nodes.add(newN);
                ops.get(0).list.add(newN);
            }

            if (n.getStatus() == REMOVE) {
                //aggiunge il nodo da rimuovere alla lista dei nodi rimossi
                this.removed.add(n);
                it.remove();
            }

            //verifica che il nodo sia stato effettivamente tradotto
            if (n.relation.startsWith(Glossary.AMR_RELATION_BEGIN)) {
                n.setStatus(AMR);
            } else {
                n.setStatus(OK);
            }
        }

        for (Iterator<Node> it = root.list.iterator(); it.hasNext();) {
            Node n = it.next();
            if (n.getStatus() == REMOVE) {
                this.removed.add(n);
                it.remove();
            }
        }

        if (!toAdd.isEmpty()) {
            root.list.addAll(toAdd);
            toAdd.clear();
            root = listElaboration(root);
        }

        for (Node n : root.list) {
            //richiama il metodo di traduzione ricorsivamente
            n = listElaboration(n);

        }

        if (root.relation.equalsIgnoreCase(TOP) && !root.getOps().isEmpty()) {

            ArrayList<Node> ops = root.getOps();
            root.list.removeAll(ops);

            Node newRoot = new Node("", "");
            newRoot.substitute(root);
            newRoot.relation = Glossary.DUL_ASSOCIATED_WITH;
            nodes.add(newRoot);
            root.substitute(ops.get(0));
            root.list.add(newRoot);
            root.relation = TOP;

            for (Node n1 : ops) {
                n1.relation = TOP;
                if (!root.equals(n1)) {
                    root.list.add(n1);
                }
            }

        }

        if (root.relation.startsWith(Glossary.AMR_RELATION_BEGIN)) {
            root.setStatus(AMR);
        } else {
            root.setStatus(OK);
        }

        return root;
    }

    /*
    Aggiunge i nodi relativi alla vn-class dei verbi e traduce i rispettivi :argx
    da amr in Fred
     */
    private Node findVnClass(Node root) {

        if (root == null) {
            return null;
        }
        String lemma = root.getVerb();
        if (root.getType() == VERB) {

            PredMatrix pred = PredMatrix.getPredMatrix();
            /*
            Trova tutte le righe della tabella predmatrix che contengono il lemma 
            cercato; il risultato è ordinato per valori decrescenti sul campo che 
            indica la frequenza (24_WN_SENSEFREC)
             */
            ArrayList<Line> result = pred.find(lemma, Glossary.LineFields.ID_PRED);
            if (result != null && !result.isEmpty()) {

                /*
                affina la ricerca, partendo dalla prima riga e verificando che il 
                verbo con maggiore valore di frequenza abbia in tabella tutti 
                gli argomenti richiesti: in caso contrario, la ricerca viene effettuata
                sul successivo elemento della lista di partenza.
                 */
                result = pred.find(result, root.getArgs());
                if (result != null && !result.isEmpty()) {
                    String vnClass = result.get(0).getLine().get(Glossary.LineFields.VN_CLASS_NUMBER.ordinal()).substring(3);
                    String vnSubClass = result.get(0).getLine().get(Glossary.LineFields.VN_SUBCLASS_NUMBER.ordinal()).substring(3);
                    Node rdfNode = root.getChild(Glossary.RDF_TYPE);

                    if (vnSubClass.equalsIgnoreCase(Glossary.NULL) && !vnClass.equalsIgnoreCase(Glossary.NULL)) {
                        rdfNode.list.add(new Node(serializeClass(Glossary.VN_DATA + (rdfNode.var.replaceAll(FRED, "")) + "_" + vnClass), Glossary.OWL_EQUIVALENT_CLASS, OK));
                    } else if (!vnSubClass.equalsIgnoreCase(Glossary.NULL)) {
                        rdfNode.list.add(new Node(serializeClass(Glossary.VN_DATA + (rdfNode.var.replaceAll(FRED, "")) + "_" + vnSubClass), Glossary.OWL_EQUIVALENT_CLASS, OK));
                    }
                    //Elabora i nodi argomento esplicitando i relativi nodi
                    for (Node n : root.getArgs()) {
                        String r = Glossary.PB + n.relation.substring(4);
                        String role;
                        String frame;
                        ArrayList<Line> result1 = pred.find(result, r);
                        if (result1 != null && !result1.isEmpty()) {
                            role = result1.get(0).getLine().get(Glossary.LineFields.VN_ROLE.ordinal()).substring(3);
                            frame = result1.get(0).getLine().get(Glossary.LineFields.FN_FRAME_ELEMENT.ordinal()).substring(3);
                            if ((role.equalsIgnoreCase(PIVOT) || (role.equalsIgnoreCase(NULL))) && !frame.equalsIgnoreCase(NULL)) {
                                n.relation = VN_ROLE + frame;
                                n.setStatus(OK);
                            } else if (!role.equalsIgnoreCase(NULL)) {
                                n.relation = VN_ROLE + role;
                                n.setStatus(OK);
                            }
                        }
                    }
                }
            }
        }

        for (Node n : root.list) {
            n = findVnClass(n);
        }
        return root;
    }

    /*
    Restituisce il numero da associare alla parola data
    in base al numero di occorrenze avute al momento
     */
    private int occurrence(String word) {
        int occorrenceNum = 1;
        for (Couple c : this.couples) {
            if (word.equalsIgnoreCase(c.getWord())) {
                occorrenceNum += c.getOccurence();
                c.setOccurence(occorrenceNum);
            }
        }
        if (occorrenceNum == 1) {
            this.couples.add(new Couple(1, word));
        }
        return occorrenceNum;
    }

    /*
    Restituisce la lista dei nodi equivalenti a quello dato
     */
    private ArrayList<Node> getEquals(Node node) {
        ArrayList<Node> equalsList = new ArrayList<>();
        for (Node n : this.nodes) {
            if (n.equals(node)) {
                equalsList.add(n);
            }
        }
        return equalsList;
    }

    /*
    Effettua l'aggiornamento della var sui nodi equivalenti a quello dato
     */
    private void setEquals(Node root) {
        for (Node n : getEquals(root)) {
            n.var = root.var;
        }
    }

    /*
    Rende una stringa con la prima lettera in maiuscolo e tutte le altre in minuscolo
     */
    private String firstUpper(String string) {
        string = string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
        return string;
    }

    /*
    Effettua il controllo degli inversi (x-of). Richiede al nodo dato se
    tra i nodi figli c'è un inverso e lo elabora scambiandolo di posto
    con il nodo dato ed effettuando poi tale controllo ricorsivamente se
    il nodo dato è il nodo radice del grafo, altrimenti viene istanziato un nodo
    copia di quello corrente con relazione corretta presa da quella inversa
    e lo stesso viene aggiunto al nodo che prima aveva la relazione inversa
    e la cui relazione viene impostata a TOP cioe nuova radice
     */
    private Node inverseChecker(Node root) {
        if (root == null || root.list.isEmpty()) {
            return root;
        }
        Node scambio;
        Node n = root.getInverse();
        ArrayList<Node> inversi = root.getInverses();

        if (n != null && inversi.size() > 1) {

            for (Node inv : inversi) {

                if (inv.list.isEmpty()) {
                    Node newNode = root.getCopy(inv.relation.substring(0, inv.relation.length() - 3));
                    this.nodes.add(newNode);
                    getOriginal(inv).list.add(newNode);
                    inv.setStatus(REMOVE);
                } else if (inversi.get(0) != inv) {

                    Node newNode = root.getCopy(inv.relation.substring(0, inv.relation.length() - 3));
                    //newNode.list.add(root.getInstance());
                    this.nodes.add(newNode);
                    inv.relation = TOP;

                    inv.list.add(newNode);
                }
            }
            root = inverseChecker(root);
        }

        if (n != null && inversi.size() == 1) {

            /* if (n.getInstance() == null) {
            System.out.println(n.var);
            }*/
            if (root.relation.equalsIgnoreCase(TOP)) {

                root.list.remove(n);
                String arg = n.relation;
                n.relation = root.relation;
                root.relation = arg.substring(0, arg.length() - 3);
                scambio = root;
                root = n;
                n = scambio;
                root.list.add(n);
                return inverseChecker(root);

            } else if (root.getArgs().size() <= 1 && root.getChild(":arg1-of") != null
                    && root.getChild(":arg1-of").getInstance() != null
                    && root.getChild(":arg1-of").getInstance().var.matches(Glossary.AMR_VERB2)
                    && n.getArgs().isEmpty()
                    && !root.getInstance().var.equalsIgnoreCase(Glossary.AMR_UNKNOWN)) {

                //risolve il caso del nodo con relazione inversa e var "amr-unknown"
                n.relation = n.relation.substring(0, n.relation.length() - 3);
                String swap = root.getInstance().var;
                root.getInstance().var = n.getInstance().var;
                n.getInstance().var = swap;

            } else {

                Node newNode = root.getCopy(n.relation.substring(0, n.relation.length() - 3));

                //newNode.list.add(root.getInstance());
                this.nodes.add(newNode);
                n.relation = TOP;
                n.list.add(newNode);
            }

        }

        for (Node n1 : root.list) {
            n1 = inverseChecker(n1);
        }

        return root;
    }

    /*
    Imposta il valore del verbo di riferimento per i nodi figli
     */
    private void args(Node root) {
        for (Node n : root.list) {
            if (n.relation.matches(AMR_ARG)) {
                n.setVerb(root.getVerb());
            }
        }
    }

    /*
    Aggiunge il nodo TOPIC alla root dell'albero se il valore del attributo topic è true
    Questo avviene se nella frase non ci sono verbi
     */
    private Node topic(Node root) {
        if (topic) {
            root.list.add(new Node(Glossary.FRED_TOPIC, Glossary.DUL_HAS_QUALITY, OK));
        }
        return root;
    }

    /*
    Verifica se la parola in input è un verbo contenuto nella predmatrix
     */
    private boolean isVerb(String word) {

        PredMatrix pred = PredMatrix.getPredMatrix();
        word = Glossary.ID + word.replace('-', '.');
        ArrayList<Line> result = pred.find(word, Glossary.LineFields.ID_PRED);
        return result != null && !result.isEmpty();
    }

    private boolean isVerb(String word, ArrayList<Node> list) {
        PredMatrix pred = PredMatrix.getPredMatrix();
        word = Glossary.ID + word.replace('-', '.');
        ArrayList<Line> result = pred.find(word, Glossary.LineFields.ID_PRED);
        result = pred.find(result, list);
        return result != null && !result.isEmpty();

    }

    /*
    Effettua le elaborazioni necessarie per l'eliminazioni delle istanze dei nodi non verbo
     */
    private Node otherInstanceElaboration(Node root) {
        Node instance = root.getInstance();
        if (instance == null) {
            return root;
        }
        String nVar = FRED + instance.var + "_" + occurrence(instance.var);
        for (Node n : getEquals(root)) {

            Node instanceInList = n.getInstance();
            if (instanceInList != null) {
                n.var = nVar;
                instanceInList.relation = Glossary.RDF_TYPE;
                instanceInList.var = FRED + firstUpper(instance.var);
                if (!instanceInList.relation.startsWith(Glossary.AMR_RELATION_BEGIN)) {
                    instanceInList.setStatus(OK);
                }

            } else {
                n.var = nVar;
            }
        }
        return root;
    }

    /*
    Effettua le elaborazioni necessarie sul nodo root prima dell'elaborazione della lista dei figli
     */
    private Node rootElaboration(Node root) {
        //System.out.println(root.relation + " " + root.var);

        Node instance = root.getInstance();

        if (instance == null) {
            return root;
        }

        if (root.getChild(Glossary.AMR_CONCESSION) != null || root.getChild(Glossary.AMR_CONDITION) != null) {
            Node concession = root.getChild(Glossary.AMR_CONCESSION);
            Node condition = root.getChild(Glossary.AMR_CONDITION);
            Node swap = new Node("", "");
            //swap.substitute(root);

            if (concession != null && concession.getInstance() != null && concession.getInstance().var.equalsIgnoreCase(Glossary.EVEN_IF)
                    && concession.getChild(Glossary.AMR_OP1) != null) {

                root.list.remove(concession);
                Node op1 = concession.getChild(Glossary.AMR_OP1);
                Node modality = new Node(Glossary.BOXING_NECESSARY, Glossary.BOXING_HAS_MODALITY, OK);
                Node quality = new Node(Glossary.FRED_EVEN, Glossary.DUL_HAS_QUALITY, OK);
                root.list.add(modality);
                op1.list.add(modality);
                swap.substitute(root);
                root.substitute(op1);
                root.relation = swap.relation;
                swap.relation = Glossary.FRED_ENTAILS;
                swap.list.add(quality);
                root.list.add(swap);
            }

            if (concession != null && concession.getInstance() != null && concession.getInstance().var.equalsIgnoreCase(Glossary.EVEN_WHEN)
                    && concession.getChild(Glossary.AMR_OP1) != null) {

                root.list.remove(concession);
                Node op1 = concession.getChild(Glossary.AMR_OP1);

                Node quality = new Node(Glossary.FRED_EVEN, Glossary.DUL_HAS_QUALITY, OK);
                op1.relation = Glossary.FRED_WHEN;

                root.list.add(quality);
                root.list.add(op1);
            }

            if (condition != null && condition.getInstance() != null) {
                root.list.remove(condition);
                Node modality = new Node(Glossary.BOXING_NECESSARY, Glossary.BOXING_HAS_MODALITY, OK);
                root.list.add(modality);
                condition.list.add(modality);
                swap.substitute(root);
                root.substitute(condition);
                root.relation = swap.relation;
                swap.relation = Glossary.FRED_ENTAILS;
                root.list.add(swap);

            }

        }

        if (instance.var.equalsIgnoreCase(Glossary.SUM_OF) || instance.var.equalsIgnoreCase(Glossary.PRODUCT_OF)) {
            if (instance.var.equalsIgnoreCase(Glossary.SUM_OF)) {
                instance.var = Glossary.SUM;
                for (Node op : root.getOps()) {
                    op.relation = FRED + Glossary.SUM + Glossary.OF;
                }
            } else {
                instance.var = Glossary.PRODUCT;
                for (Node op : root.getOps()) {
                    op.relation = FRED + Glossary.PRODUCT + Glossary.OF;
                }
            }

        }

        if (instance.var.equalsIgnoreCase(Glossary.AMR_RELATIVE_POSITION)) {

            // caso relative-position
            if (root.getChild(Glossary.AMR_DIRECTION) != null && root.getChild(Glossary.AMR_OP1) != null
                    && root.getChild(Glossary.AMR_QUANT) != null && root.getChild(Glossary.AMR_QUANT).getInstance() != null) {
                Node op1 = getOriginal(root.getChild(Glossary.AMR_OP1));
                root.list.remove(op1);
                Node direction = getOriginal(root.getChild(Glossary.AMR_DIRECTION));
                op1.relation = FRED+direction.getInstance().var + Glossary.OF;
                direction.list.add(op1);
                Node quant = root.getChild(Glossary.AMR_QUANT);
                root.getInstance().var = quant.getInstance().var.replace(Glossary.QUANTITY, "");
            }
        }

        if (instance.var.equalsIgnoreCase(Glossary.HAVE_ORG_ROLE) || instance.var.equalsIgnoreCase(Glossary.HAVE_REL_ROLE)) {

            /*
            casi in cui il verbo è have-org-role-91 o have rel-role-91
            
            TODO Prevedere i casi :arg3 = role of entity B (often left unspecified) 
            e :arg4 = relationship basis (contract, case; rarely used) per HAVE_REL_ROLE 
             */
            String role;
            Node newRoot, arg1, arg2, arg2Instance;

            newRoot = root.getChild(AMR_ARG0);
            arg1 = root.getChild(AMR_ARG1);
            arg2 = root.getChild(AMR_ARG2);
            arg2Instance = null;
            role = "";

            if (arg2 != null && arg2.getInstance() != null) {
                arg2Instance = arg2.getInstance();
                role = arg2Instance.var;
                root.list.remove(arg2);
            } else if (arg2 != null) {
                Node orig = getOriginal(arg2);
                if (orig != null) {
                    arg2Instance = orig.getInstance();
                }
                if (arg2Instance != null) {
                    role = arg2Instance.var;
                    root.list.remove(arg2);
                } else if (orig != null) {
                    role = orig.var;
                } else {
                    role = arg2.var;
                }

            }

            if (arg1 != null) {
                root.list.remove(arg1);
            }

            if (newRoot != null) {

                newRoot.relation = root.relation;
                root.list.remove(newRoot);
                if (arg2Instance != null) {
                    arg2.list.remove(arg2Instance);
                    arg2Instance.relation = Glossary.RDF_TYPE;
                    arg2Instance.var = FRED + firstUpper(arg2Instance.var);
                    arg2Instance.list.addAll(arg2.list);
                    newRoot.list.add(arg2Instance);
                }
                if (arg1 != null) {
                    newRoot.list.add(arg1);
                    arg1.relation = FRED + role + Glossary.OF;
                }
                topic = false;
                root.list.remove(instance);
                newRoot.list.addAll(root.list);
                newRoot.setStatus(OK);
            }

            root.substitute(newRoot);
            return root;
        }

        if (root.relation.equalsIgnoreCase(TOP) && root.getInstance() != null && (root.getInstance().var.equalsIgnoreCase(Glossary.AND)
                || root.getInstance().var.equalsIgnoreCase(Glossary.OR))) {

            // casi "and" e "or" seguiti da :opx
            ArrayList<Node> ops = root.getOps();
            for (Node n1 : ops) {
                root.list.remove(n1);
            }
            root.list.remove(root.getInstance());
            ArrayList<Node> rootList = root.list;
            String rel = root.relation;
            root.substitute(ops.get(0));
            root.relation = rel;
            root.list.addAll(rootList);
            ops.remove(ops.get(0));

            for (Node n1 : ops) {
                n1.relation = rel;
                n1.list.addAll(rootList);
                root.list.add(n1);
            }

        }

        if (root.getChild(Glossary.AMR_MOD) != null
                && root.getChild(Glossary.AMR_MOD).getChild(Glossary.AMR_DEGREE) != null
                && root.getChild(Glossary.AMR_MOD).getChild(Glossary.AMR_COMPARED_TO) != null
                && root.getChild(Glossary.AMR_MOD).getInstance() != null) {

            //caso :mod + :degree + :compared-to
            Node arg = root.getChild(Glossary.AMR_MOD);
            instance.var = arg.getInstance().var + firstUpper(instance.var);
            arg.list.remove(arg.getInstance());
            root.list.remove(arg);
            root.list.addAll(arg.list);
        }

        if (instance.var.length() > 3 && instance.var.substring(instance.var.length() - 3).matches(Glossary.AMR_VERB)
                && !isVerb(instance.var) && root.getArgs().size() == 1) {

            //caso predicato nominale
            topic = false;
            Node arg = root.getArgs().get(0);
            root.list.remove(arg);

            if (root.getChild(Glossary.AMR_DEGREE) != null && root.getChild(Glossary.AMR_DEGREE).getInstance() != null) {

                //caso :degree senza :compared-to
                instance.var = root.getChild(Glossary.AMR_DEGREE).getInstance().var + firstUpper(instance.var);
                root.list.remove(root.getChild(Glossary.AMR_DEGREE));

            }

            String parentVar = instance.var.substring(0, instance.var.length() - 3);
            if (arg.getInstance() != null) {
                instance.var = arg.getInstance().var;
                arg.list.remove(arg.getInstance());
            }
            arg.relation = Glossary.DUL_HAS_QUALITY;
            arg.var = FRED + firstUpper(parentVar.replaceAll(FRED, ""));
            root.list.addAll(arg.list);
            arg.list.clear();
            root.list.add(arg);
            arg.setStatus(OK);

        } else if (root.getChild(Glossary.AMR_DOMAIN) != null && root.getChild(Glossary.AMR_COMPARED_TO) != null
                && root.getChild(Glossary.AMR_DEGREE) != null) {

            //caso :degree + :domain + :compared-to
            topic = false;
            Node arg = root.getChild(Glossary.AMR_DOMAIN);
            root.list.remove(arg);

            if (root.getChild(Glossary.AMR_DEGREE) != null && root.getChild(Glossary.AMR_DEGREE).getInstance() != null) {
                //System.out.println(root.getChild(Glossary.AMR_DEGREE).getInstance().var);
                instance.var = root.getChild(Glossary.AMR_DEGREE).getInstance().var + firstUpper(instance.var);
                root.list.remove(root.getChild(Glossary.AMR_DEGREE));

            }

            String parentVar = instance.var;
            if (arg.getInstance() != null) {
                instance.var = arg.getInstance().var;
                arg.list.remove(arg.getInstance());
            }
            arg.relation = Glossary.DUL_HAS_QUALITY;
            root.getChild(Glossary.AMR_COMPARED_TO).relation = parentVar;
            root.list.addAll(arg.list);

        }

        if (instance.var.length() > 3 && instance.var.substring(instance.var.length() - 3).matches(Glossary.AMR_VERB)
                && !isVerb(instance.var, root.getArgs())) {
            if (root.getChild(AMR_ARG0) != null && root.getChild(AMR_ARG1) != null) {
                root.getChild(AMR_ARG0).relation = Glossary.BOXER_AGENT;
                root.getChild(AMR_ARG1).relation = Glossary.BOXER_PATIENT;
                topic = false;
            }
            if (root.getChild(AMR_ARG1) != null && root.getChild(AMR_ARG2) != null) {
                root.getChild(AMR_ARG1).relation = Glossary.VN_ROLE_EXPERIENCER;
                root.getChild(AMR_ARG2).relation = Glossary.VN_ROLE_CAUSE;
                topic = false;
            }
        }

        if (instance.var.matches(Glossary.AMR_QUANTITY) && root.getChild(Glossary.AMR_UNIT) != null) {
            //caso X-quantity + :unit
            Node unit = root.getChild(Glossary.AMR_UNIT);
            root.list.remove(unit);
            root.list.remove(root.getInstance());
            unit.list.addAll(root.list);
            unit.relation = root.relation;
            root.substitute(unit);
        }

        if (root.getChild(Glossary.AMR_QUANT) != null) {

            Node quant = root.getChild(Glossary.AMR_QUANT);

            Node newNode = new Node("", "");

            if (quant.getInstance() != null && quant.getInstance().var.matches(Glossary.AMR_QUANTITY)
                    && quant.getChild(Glossary.AMR_UNIT) != null) {
                Node unit = quant.getChild(Glossary.AMR_UNIT);
                quant.list.remove(unit);
                quant.list.remove(quant.getInstance());
                unit.list.addAll(quant.list);
                unit.relation = quant.relation;
                quant.substitute(unit);
                root.list.remove(quant);
                newNode.substitute(root);
                nodes.add(newNode);
                newNode.relation = FRED + unit.getInstance().var + Glossary.OF;
                quant.list.add(newNode);
                quant.relation = root.relation;
                root.substitute(quant);

            }

        }

        if (root.getChild(Glossary.AMR_SCALE) != null && root.getChild(Glossary.AMR_SCALE).getInstance() != null) {
            //caso :scale
            Node scale = root.getChild(Glossary.AMR_SCALE);
            scale.relation = Glossary.FRED_ON;
            scale.var = firstUpper(scale.getInstance().var) + "_scale";
            scale.list.remove(scale.getInstance());
        }

        if (root.getChild(Glossary.AMR_ORD) != null) {
            //caso :ord
            Node ord = root.getChild(Glossary.AMR_ORD);
            root.list.remove(ord);
            ord.list.remove(ord.getInstance());
            root.list.addAll(ord.list);
            Node value = ord.getChild(Glossary.AMR_VALUE);
            if (value != null && value.var.matches(Glossary.NN_INTEGER)) {
                int num = Integer.parseInt(value.var);
                String ordNum = ordinal(num);
                value.relation = Glossary.QUANT_HAS_QUANTIFIER;
                value.var = Glossary.QUANT + ordNum;

            }
        }

        return root;
    }

    /**
     * Restituisce il nodo originale tra gli equivalenti
     *
     * @param copy
     * @return
     */
    private Node getOriginal(Node copy) {
        for (Node n : nodes) {
            if (copy.equals(n) && n.getInstance() != null) {
                return n;
            }
        }
        return null;
    }

    /**
     * Elimina le parentesi tonde contenute tra virgolette
     *
     * @param amr
     * @return
     */
    private String patch(String amr) {

        boolean flag = false;
        String newString = "";
        for (int i = 0; i < amr.length(); i++) {
            if (amr.charAt(i) == '"') {
                flag = flag ^ true;
            }
            if (!(amr.charAt(i) == '(' && flag) && !(amr.charAt(i) == ')' && flag)) {

                newString += amr.charAt(i);
            }
        }
        return newString;
    }

    private Node dateEntity(Node root) {

        if (root.list.isEmpty() || (root.getInstance() != null
                && !root.getInstance().var.equalsIgnoreCase(Glossary.AMR_DATE_ENTITY))) {
            return root;
        }

        boolean top = true;

        Node instance = root.getInstance();
        root.list.remove(instance);

        Node era = root.getChild(Glossary.AMR_DATE_ERA);

        if (era != null) {
            era.relation = Glossary.FRED_OF;
            era.var = firstUpper(era.getInstance().var + "_era");
            era.list.remove(era.getInstance());
        }

        Node decade = root.getChild(Glossary.AMR_DATE_DECADE);
        Node century = root.getChild(Glossary.AMR_DATE_CENTURY);

        if (decade != null) {
            top = false;
            root.list.add(new Node("decade", "instance"));
            decade.relation = Glossary.DUL_HAS_QUALITY;
            decade.var = FRED + ordinal(decade.var);
        }

        if (century != null) {
            top = false;
            root.list.add(new Node("century", "instance"));
            century.relation = Glossary.DUL_HAS_QUALITY;
            century.var = FRED + ordinal(century.var);
        }

        Node calendar = root.getChild(Glossary.AMR_DATE_CALENDAR);

        if (calendar != null) {
            if (calendar.getInstance() != null && calendar.getInstance().var.equalsIgnoreCase("year")) {
                root.list.remove(calendar);
                top = false;
                root.var = firstUpper(calendar.getInstance().var);
                root.list.addAll(calendar.list);
                root.list.remove(root.getInstance());
            } else {
                calendar.relation = Glossary.FRED_OF;
            }

            if (era != null) {
                root.list.remove(calendar);
                era.list.add(calendar);
                top = true;
            }
        }

        Node weekDay = root.getChild(Glossary.AMR_DATE_WEEKDAY);
        Node dayperiod = root.getChild(Glossary.AMR_DATE_DAY_PERIOD);

        if (weekDay != null && dayperiod == null) {
            root.var = FRED + firstUpper(weekDay.getInstance().var);
            root.list.remove(weekDay);
            root.list.addAll(weekDay.list);
        } else if (dayperiod != null && weekDay == null) {
            root.var = FRED + firstUpper(dayperiod.getInstance().var);
            root.list.remove(dayperiod);
            root.list.addAll(dayperiod.list);
            top = false;
        } else if (dayperiod != null && weekDay != null) {
            root.var = FRED + firstUpper(dayperiod.getInstance().var);
            root.list.remove(dayperiod);
            root.list.addAll(dayperiod.list);
            top = false;
            weekDay.relation = Glossary.DUL_ASSOCIATED_WITH;
            if (weekDay.getInstance() != null) {
                instance = weekDay.getInstance();
                weekDay.list.remove(instance);
                weekDay.var = FRED + firstUpper(instance.var);
            }
        }

        Node quarter = root.getChild(Glossary.AMR_DATE_QUARTER);

        if (quarter != null) {
            top = false;
            root.list.add(new Node("quarter", "instance"));
            quarter.relation = Glossary.DUL_HAS_QUALITY;
            quarter.var = FRED + ordinal(quarter.var);
        }

        Node season = root.getChild(Glossary.AMR_DATE_SEASON);
        if (season != null) {
            top = false;
            root.var = firstUpper(season.getInstance().var);
            root.list.remove(season);

        }

        Node timezone = root.getChild(Glossary.AMR_DATE_TIMEZONE);
        if (timezone != null) {
            root.list.remove(timezone);
        }

        String newVar = "";
        String newVar2 = "";

        if (root.getChild(Glossary.AMR_DATE_YEAR) != null
                || root.getChild(Glossary.AMR_DATE_MONTH) != null
                || root.getChild(Glossary.AMR_DATE_DAY) != null) {

            Node year = root.getChild(Glossary.AMR_DATE_YEAR);
            Node month = root.getChild(Glossary.AMR_DATE_MONTH);
            Node day = root.getChild(Glossary.AMR_DATE_DAY);
            Node year2 = root.getChild(Glossary.AMR_DATE_YEAR2);

            if (year2 != null) {
                root.list.remove(year2);
                while (year2.var.length() < 4) {
                    year2.var = "0" + year2.var;
                }

                newVar2 += year2.var + "-01-01";
            }

            if (year != null) {
                root.list.remove(year);
                while (year.var.length() < 4) {
                    year.var = "0" + year.var;
                }

                newVar += year.var + "-";
            } else {
                newVar += "0001-";
            }

            if (month != null) {
                root.list.remove(month);
                while (month.var.length() < 2) {
                    month.var = "0" + month.var;
                }
                newVar += month.var + "-";
            } else {
                newVar += "01-";
            }

            if (day != null) {
                root.list.remove(day);
                while (day.var.length() < 2) {
                    day.var = "0" + day.var;
                }
                newVar += day.var;
            } else {
                newVar += "01";
            }

            if (root.relation.equalsIgnoreCase(TOP) || weekDay != null) {

                topic = false;

                if (weekDay != null && top) {

                    root.var = FRED + firstUpper(weekDay.getInstance().var);
                    root.setStatus(OK);
                } else if (top) {

                    instance.var = "date";
                    root.list.add(instance);
                }

                Node date = new Node(newVar, Glossary.FRED_AT, OK);
                Node date2 = new Node(newVar2, Glossary.FRED_AT, OK);
                root.list.add(date);
                if (year2 != null) {
                    root.list.add(date2);
                }

            } else if (top) {
                root.var = newVar;
                //root.list.clear();
                root.setStatus(OK);
            }
        }

        Node time = root.getChild(Glossary.AMR_TIME);
        if (time != null) {
            root.var = FRED + time.var;
            root.list.remove(time);
            root.list.addAll(time.list);
            root.relation = Glossary.VN_ROLE_TIME;
        }

        return root;
    }

    private String mannerAdverb(String var) {
        for (String adv : Glossary.MANNER_ADVERBS) {
            if (adv.matches("^" + var + ".*")) {
                return adv;
            }
        }
        return "";
    }

    private Node prepControl(Node root) {
        if (root.list.isEmpty() || root.getInstance() == null || root.getOps().isEmpty()) {
            return root;
        }

        String var = root.getInstance().var;
        Node quality = new Node(FRED + firstUpper(var.replaceAll(FRED, "")), Glossary.DUL_HAS_QUALITY, OK);

        Node manner = root.getChild(Glossary.AMR_MANNER);
        if (manner != null) {
            manner = root.getChild(Glossary.AMR_MANNER).getInstance();
        }

        Boolean go = false;

        for (String prep : Glossary.PREPOSITION) {
            if (var.equalsIgnoreCase(prep)) {
                go = true;
                break;
            }
        }
        if (go) {
            for (Node n : root.getOps()) {
                n.relation = root.relation;

            }
            if (manner != null && !mannerAdverb(manner.var).isEmpty()) {
                quality.var = FRED + mannerAdverb(manner.var) + firstUpper(quality.var);
                root.list.remove(root.getChild(Glossary.AMR_MANNER));
            } else {
                quality.var = FRED + firstUpper(quality.var);
            }
            root.list.add(quality);
            root.list.remove(root.getInstance());
            if (!root.relation.equalsIgnoreCase(TOP)) {
                root.relation = Glossary.PREP_SUBSTITUTION;
            } else {
                Node first = root.list.get(0);
                root.list.remove(first);
                first.list.addAll(root.list);
                root.substitute(first);
            }
        }

        return root;
    }

    Node reifi(Node root) {

        Node instance = root.getInstance();

        if (instance == null) {
            return root;
        }

        String var = instance.var;

        if (var.equalsIgnoreCase(Glossary.REIFI_BENEFIT)
                || var.equalsIgnoreCase(Glossary.REIFI_EXEMPLIFY)) {

            Node arg0 = root.getChild(Glossary.AMR_ARG0);
            Node arg1 = root.getChild(Glossary.AMR_ARG1);

            root.list.remove(instance);

            if (arg0 != null) {
                arg0.relation = root.relation;
                root.list.remove(arg0);
                arg0.list.addAll(root.list);
                root.substitute(arg0);
            }

            if (arg1 != null) {

                switch (var) {

                    case Glossary.REIFI_BENEFIT:
                        arg1.relation = Glossary.AMR_BENEFICIARY;
                        break;
                    case Glossary.REIFI_EXEMPLIFY:
                        arg1.relation = Glossary.AMR_EXAMPLE;
                        break;

                }

            }

        }

        if (var.equalsIgnoreCase(Glossary.REIFI_INCLUDE)) {

            Node arg2 = root.getChild(Glossary.AMR_ARG2);
            Node arg1 = root.getChild(Glossary.AMR_ARG1);

            root.list.remove(instance);

            if (arg2 != null) {
                arg2.relation = root.relation;
                root.list.remove(arg2);
                arg2.list.addAll(root.list);
                root.substitute(arg2);
            }

            if (arg1 != null) {

                arg1.relation = Glossary.FRED_OF;

            }

        }

        if (var.equalsIgnoreCase(Glossary.REIFI_BE_LOCATED_AT)
                || var.equalsIgnoreCase(Glossary.REIFI_HAVE_CONCESSION)
                || var.equalsIgnoreCase(Glossary.REIFI_HAVE_CONDITION)
                || var.equalsIgnoreCase(Glossary.REIFI_BE_DESTINED_FOR)
                || var.equalsIgnoreCase(Glossary.REIFI_HAVE_EXTENT)
                || var.equalsIgnoreCase(Glossary.REIFI_HAVE_FREQUENCY)
                || var.equalsIgnoreCase(Glossary.REIFI_HAVE_INSTRUMENT)
                || var.equalsIgnoreCase(Glossary.REIFI_HAVE_MANNER)
                || var.equalsIgnoreCase(Glossary.REIFI_HAVE_MOD)
                || var.equalsIgnoreCase(Glossary.REIFI_HAVE_NAME)
                || var.equalsIgnoreCase(Glossary.REIFI_HAVE_PART)
                || var.equalsIgnoreCase(Glossary.REIFI_HAVE_PURPOSE)
                || var.equalsIgnoreCase(Glossary.REIFI_HAVE_QUANT)
                || var.equalsIgnoreCase(Glossary.REIFI_BE_FROM)
                || var.equalsIgnoreCase(Glossary.REIFI_HAVE_SUBEVENT)
                || var.equalsIgnoreCase(Glossary.REIFI_BE_TEMPORALLY_AT)) {

            Node arg1 = root.getChild(Glossary.AMR_ARG1);
            Node arg2 = root.getChild(Glossary.AMR_ARG2);

            root.list.remove(instance);

            if (arg1 != null) {
                arg1.relation = root.relation;
                root.list.remove(arg1);
                arg1.list.addAll(root.list);
                root.substitute(arg1);
            }

            if (arg2 != null) {

                switch (var) {

                    case Glossary.REIFI_BE_LOCATED_AT:
                        arg2.relation = Glossary.AMR_LOCATION;
                        break;
                    case Glossary.REIFI_HAVE_CONCESSION:
                        arg2.relation = Glossary.AMR_CONCESSION;
                        break;
                    case Glossary.REIFI_HAVE_CONDITION:
                        arg2.relation = Glossary.AMR_CONDITION;
                        break;
                    case Glossary.REIFI_BE_DESTINED_FOR:
                        arg2.relation = Glossary.AMR_DESTINATION;
                        break;
                    case Glossary.REIFI_HAVE_EXTENT:
                        arg2.relation = Glossary.AMR_EXTENT;
                        break;
                    case Glossary.REIFI_HAVE_FREQUENCY:
                        arg2.relation = Glossary.AMR_FREQUENCY;
                        break;
                    case Glossary.REIFI_HAVE_INSTRUMENT:
                        arg2.relation = Glossary.AMR_INSTRUMENT;
                        break;
                    case Glossary.REIFI_HAVE_MANNER:
                        arg2.relation = Glossary.AMR_MANNER;
                        break;
                    case Glossary.REIFI_HAVE_MOD:
                        arg2.relation = Glossary.AMR_MOD;
                        break;
                    case Glossary.REIFI_HAVE_NAME:
                        arg2.relation = Glossary.AMR_NAME;
                        break;
                    case Glossary.REIFI_HAVE_PART:
                        arg2.relation = Glossary.AMR_PART;
                        break;
                    case Glossary.REIFI_HAVE_PURPOSE:
                        arg2.relation = Glossary.AMR_PURPOSE;
                        break;
                    case Glossary.REIFI_HAVE_QUANT:
                        arg2.relation = Glossary.AMR_QUANT;
                        break;
                    case Glossary.REIFI_BE_FROM:
                        arg2.relation = Glossary.AMR_SOURCE;
                        break;
                    case Glossary.REIFI_HAVE_SUBEVENT:
                        arg2.relation = Glossary.AMR_SUB_EVENT;
                        break;
                    case Glossary.REIFI_BE_TEMPORALLY_AT:
                        arg2.relation = Glossary.AMR_TIME;
                        break;

                }

                if (arg1 == null) {
                    arg2.relation = root.relation;
                    root.list.remove(arg2);
                    arg2.list.addAll(root.list);
                    root.substitute(arg2);
                }

            }

        }

        if (instance.var.equalsIgnoreCase(Glossary.REIFI_HAVE_POLARITY)) {

            String rel = root.relation;
            Node arg = root.getChild(":arg1-of");
            root.list.remove(arg);
            arg.list.add(new Node(Glossary.BOXING_FALSE, Glossary.BOXING_HAS_TRUTH_VALUE, OK));
            root.substitute(arg);
            root.relation = rel;
            nodes.add(root);
        }

        for (Node n : root.list) {
            n = reifi(n);
        }
        return root;
    }

    static String ordinal(int i) {
        String[] sufixes = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + sufixes[i % 10];

        }
    }

    static String ordinal(String s) {
        if (s.matches(Glossary.NN_INTEGER)) {
            int num = Integer.parseInt(s);
            return ordinal(num);
        }
        return "";
    }

    private Node rateEntity(Node root) {
        if (root.list.isEmpty() || (root.getInstance() == null)) {
            return root;
        }

        if (root.getInstance().var.equalsIgnoreCase(Glossary.RATE_ENTITY)) {
            Node instance = root.getInstance();
            root.list.remove(instance);

            Node arg1 = root.getChild(Glossary.AMR_ARG1);
            Node arg2 = root.getChild(Glossary.AMR_ARG2);
            Node arg3 = root.getChild(Glossary.AMR_ARG3);
            Node arg4 = root.getChild(Glossary.AMR_ARG4);

            if (arg1 != null) {
                int n = 1;
                if (arg1.var.matches(Glossary.NN_INTEGER)) {
                    n = Integer.parseInt(arg1.var);
                    root.var = "t";
                    instance.var = "time";
                    root.list.add(instance);

                    if (n > 1) {
                        root.list.add(new Node(Glossary.QUANT + "multiple", Glossary.QUANT_HAS_QUANTIFIER, OK));
                    }
                    arg1.relation = Glossary.DUL_HAS_DATA_VALUE;
                } else if (arg1.getInstance() != null
                        && arg1.getInstance().var.equalsIgnoreCase("several")) {
                    root.var = "t";
                    instance.var = "time";
                    root.list.add(instance);
                    root.list.add(new Node(Glossary.QUANT + "multiple", Glossary.QUANT_HAS_QUANTIFIER, OK));
                    arg1.relation = Glossary.DUL_HAS_QUALITY;
                    arg1.var=FRED+"Several";
                    arg1.list.remove(arg1.getInstance());

                } else if (arg1.getInstance() != null && !arg1.getInstance().var.equalsIgnoreCase("several")) {
                    root.list.remove(arg1);
                    root.var = arg1.var;
                    root.list.addAll(arg1.list);
                }

            }

            if (arg2 != null) {
                arg2.relation = FRED + "per";
            }

            if (arg4 != null && arg3 == null) {
                root.list.remove(arg4);
                root.var = arg4.var;
                root.list.addAll(arg4.list);
                root.list.add(new Node(Glossary.QUANT_EVERY, Glossary.QUANT_HAS_QUANTIFIER, OK));
            }

            if (arg3 != null && arg4 == null) {
                root.list.remove(arg3);
                root.var = arg3.var;
                root.list.addAll(arg3.list);
                root.list.add(new Node(Glossary.QUANT_EVERY, Glossary.QUANT_HAS_QUANTIFIER, OK));
            }

            if (arg3 != null && arg4 != null && arg1 == null) {
                root.list.remove(arg3);
                root.var = arg3.var;
                root.list.addAll(arg3.list);
                root.list.add(new Node(Glossary.QUANT_EVERY, Glossary.QUANT_HAS_QUANTIFIER, OK));
                arg4.relation = Glossary.DUL_ASSOCIATED_WITH;

            }

            if (arg3 != null && arg4 != null && arg1 != null) {

                arg3.relation = Glossary.DUL_ASSOCIATED_WITH;
                arg4.relation = Glossary.DUL_ASSOCIATED_WITH;

            }

        }

        for (Node n : root.list) {
            n = rateEntity(n);
        }
        return root;
    }
    
   private static String serializeClass(String vnClass) {
        int i = vnClass.indexOf("_");
        String classe = "", sottoclasse = "";
        String result = vnClass.substring(0, i + 1);
        vnClass = vnClass.substring(i + 1);
        //System.out.println(vnClass);

        i = vnClass.indexOf(".");
        int j = vnClass.indexOf("-");
        if (i == -1 && j == -1) {
            return result + vnClass + "00000000".substring(vnClass.length());
        }
        if (i != -1) {
            classe += vnClass.substring(0, i);
            vnClass = vnClass.substring(i + 1);
        } else if (j != -1) {
            classe += vnClass.substring(0, j);
            vnClass = vnClass.substring(j + 1);
        }

        i = vnClass.indexOf(".");
        j = vnClass.indexOf("-");
        if (i == -1 && j == -1) {
            return result + classe + vnClass + "00000000".substring((classe + vnClass).length());
        }
        if (i != -1) {
            sottoclasse = vnClass.substring(0, i);
            if (sottoclasse.length() < 2) {
                sottoclasse = "0" + sottoclasse;
            }
            classe += sottoclasse;
            vnClass = vnClass.substring(i + 1);
        } else if (j != -1) {
            sottoclasse = vnClass.substring(0, j);
            if (sottoclasse.length() < 2) {
                sottoclasse = "0" + sottoclasse;
            }
            classe += sottoclasse;
            vnClass = vnClass.substring(j + 1);
        }

        i = vnClass.indexOf(".");
        j = vnClass.indexOf("-");
        if (i == -1 && j == -1) {
            return result + classe + vnClass + "00000000".substring((classe + vnClass).length());
        }
        if (i != -1) {
            sottoclasse = vnClass.substring(0, i);
            if (sottoclasse.length() < 2) {
                sottoclasse = "0" + sottoclasse;
            }
            classe += sottoclasse;
            vnClass = vnClass.substring(i + 1);
        } else if (j != -1) {
            sottoclasse = vnClass.substring(0, j);
            if (sottoclasse.length() < 2) {
                sottoclasse = "0" + sottoclasse;
            }
            classe += sottoclasse;
            vnClass = vnClass.substring(j + 1);
        }
        vnClass = vnClass.replace(".", "");
        classe += vnClass.replace("-", "");

        return result + classe + "00000000".substring(((classe).length()) % 9);
    }

}
