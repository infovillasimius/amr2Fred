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
import static amr2fred.Glossary.TOP;
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
import java.math.BigInteger;
import org.apache.commons.lang3.StringUtils;

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
    private ArrayList<Node> nodesCopy;

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

    private ArrayList<String> vars;

    private Parser() {
        this.nodes = new ArrayList<>();
        this.nodesCopy = new ArrayList<>();
        this.couples = new ArrayList<>();
        this.removed = new ArrayList<>();
        this.toAdd = new ArrayList<>();
        this.vars = new ArrayList<>();
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
        parser.nodesCopy = new ArrayList<>();
        parser.couples = new ArrayList<>();
        parser.removed = new ArrayList<>();
        parser.toAdd = new ArrayList<>();
        parser.vars = new ArrayList<>();
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
        amr = StringUtils.stripAccents(amr);

        /*
        Il nodo root contiene la struttura dati che si ottiene
        passando la stringa amr al metodo string2Array e passando 
        il vettore ottenuto al metodo getNodes insieme alla relazione 
        speciale per il nodo radice.
         */
        Node root = getNodes(TOP, string2Array(amr));

        if (root != null) {
            endless = 0;
            this.rootCopy = root.getCopy(this.nodesCopy);

            //verifica errore per ricorsione
            if (endless > ENDLESS) {
                this.rootCopy = new Node("Error", "Recursive");
                return root;
            }

            //metodo per controllo multi sentence
            root = multi_sentence(root);
            //richiama il metodo che effettua la traduzione delle relazioni e dei valori
            root = fredTranslate(root);
            //richiama il metodo che disambigua i verbi ed esplicita i ruoli dei predicati anonimi
            root = verbs_elaboration(root);
            //verifica la necessità di inserire il nodo speciale TOPIC
            root = topic(root);
            //verifica e tenta correzione errori residui
            root = residual(root);
            //AMR INTEGRATION
            root = amr_integration(root);
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

                if (!word.startsWith(Glossary.QUOTE)) {
                    list.add(word.toLowerCase());
                } else {
                    fine = amr.indexOf("\"", inizio + 1);
                    word = amr.substring(inizio, fine);
                    word = word.trim();
                    while (word.contains("  ")) {
                        word = word.replace("  ", " ");
                    }
                    word = word.replaceAll(" ", "_");
                    word = word.replaceAll("__", "_");
                    word = word.replace("(_", "(");
                    word = word.replace("_)", ")");
                    word = word.replace("_/_", "/");
                    while (word.contains("\"")) {
                        word = word.replace("\"", "");
                    }
                    list.add("literal:" + word.replaceAll(Glossary.QUOTE, ""));
                }
                amr = amr.substring(fine);

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

        amr = amr.replaceAll("\r\n|\r|\n", " ");
        amr = amr.trim();
        //amr = patch(amr);

        amr = amr.replace("(", " ( ");
        amr = amr.replace(")", " ) ");
        amr = amr.replace("/", " / ");

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
                                        root.add(getNodes(amrList.get(i - 1), newList));
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

                    for (Node find : this.nodes) {
                        // verifica esistenza di nodo "uguale" senza istanza, inserito precedentemente
                        if (find.var.equalsIgnoreCase(root.var) && find.getInstance() == null) {
                            find.makeEquals(root.getNodeId());
                        }
                    }
                    root.add(new Node(amrList.get(++i), Glossary.INSTANCE));
                    break;
                default:
                    
                    //verifica riutilizzo variabili
                try {
                    if (word.charAt(0) == ':' && amrList.size() > i + 1 && amrList.get(i + 1).charAt(0) != '(') {
                        flag = false;
                        for (Node find : this.nodes) {
                            if (find.var.equalsIgnoreCase(amrList.get(i + 1))) {
                                Node newNode = find.getCopy(/*find,*/word);
                                root.add(newNode);
                                nodes.add(newNode);
                                flag = true;
                                break;
                            }
                        }

                        if (flag == false) {
                            //aggiunta di un nuovo nodo in caso di verifica negativa
                            Node newNode = new Node(amrList.get(i + 1), word);
                            root.add(newNode);
                            nodes.add(newNode);
                        }

                    }
                } catch (Exception e) {
                    Node newNode = new Node(amrList.get(i + 1), word);
                    root.add(newNode);
                    nodes.add(newNode);
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

        for (Node n : this.nodes) {
            if (n.getInstance() != null) {
                this.vars.add(n.var);
            }
        }

        root = dom_verify(root);

        //verifica ops
        root = control_ops(root);

        //verifica punti elenco
        root = this.li_verify(root);

        //verifica inversi
        root = this.inverseChecker(root);

        //verifica :mod
        root = mod_verify(root);

        //Elaborazione della lista dei nodi contenuti nel nodo attualmente in lavorazione
        root = this.listElaboration(root);

        root = add_parent_list(root);

        //elaborazione del nodo figlio denominato instance in amr
        root = this.instanceElaboration(root);

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
                    instance.add(new Node(Glossary.DUL_EVENT, Glossary.RDFS_SUBCLASS_OF, OK));
                }

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
                instance.var = Glossary.PB_ROLESET + instance.var;
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

            for (Node uguale : this.nodes) {
                if (uguale.equals(root)) {
                    uguale.var = root.var;
                }
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

            if (n.relation.equalsIgnoreCase(Glossary.AMR_WIKIDATA)) {
                if (n.var.equalsIgnoreCase(Glossary.AMR_MINUS)) {
                    n.setStatus(REMOVE);
                } else {
                    n.relation = Glossary.OWL_SAME_AS;
                    n.var = Glossary.WIKIDATA + n.var;
                    n.setStatus(OK);
                }
            }

            if (n.relation.equals(Glossary.PREP_SUBSTITUTION)) {
                n.setStatus(REMOVE);
                toAdd.addAll(n.list);
            }

            if (n.relation.equalsIgnoreCase(Glossary.AMR_POLARITY_OF)) {
                n.relation = Glossary.AMR + Glossary.AMR_POLARITY_OF.substring(1);
            }

            if (n.relation.equalsIgnoreCase(Glossary.AMR_DOMAIN) && n.getInstance() != null && Glossary.DEMONSTRATIVES.contains(" " + n.getInstance().var + " ")) {
                topic = false;
                // caso pronomi o aggettivi dimostrativi
                n.relation = Glossary.QUANT_HAS_DETERMINER;
                n.var = FRED + firstUpper(n.getInstance().var);
                this.removeInstance(n);
                n.setStatus(OK);
            }

            // caso "or" seguito da :op in lista
            if (n.getInstance() != null && (n.getInstance().var.equalsIgnoreCase(Glossary.OR) || n.getInstance().var.equalsIgnoreCase(Glossary.AND))) {

                if (n.getInstance().var.equalsIgnoreCase(Glossary.AND)) {
                    n.getInstance().var = Glossary.CONJUNCT;
                } else {
                    n.getInstance().var = Glossary.DISJUNCT;
                }
                //n.list.remove(n.getInstance());
                ArrayList<Node> ops = n.getOps();
                for (Node n1 : ops) {
                    n1.relation = Glossary.DUL_HAS_MEMBER;
                }
            }

            // caso "date-interval" seguito da :op in lista
            if (n.getInstance() != null && n.getInstance().var.equalsIgnoreCase(Glossary.AMR_DATE_INTERVAL)) {

                ArrayList<Node> ops = n.getOps();
                for (Node n1 : ops) {
                    n1.relation = Glossary.DUL_HAS_MEMBER;
                }

            }

            if (Glossary.PERSON.contains(" " + n.var + " ")) {
                //casi speciali con pronomi personali e aggettivi dimostrativi
                n.var = "person";
                this.setEquals(root);
                root.prefix = true;

            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_NAME)) {
                root.prefix = true;
                if (root.getPoss() != null && root.getInstance() != null) {

                    // caso :poss
                    root.getPoss().relation = FRED + (root.getInstance().var.replaceAll(FRED, "") + Glossary.OF);
                    //n.setStatus(REMOVE);
                }

                //caso :name
                ArrayList<Node> ops = n.getOps();
                if (!ops.isEmpty()) {
                    String name = "";
                    for (Node n1 : ops) {
                        name += Glossary.OP_JOINER + n1.var.replace(Glossary.LITERAL, "");
                    }
                    name = Glossary.FRED + name.substring(1);
                    n.list.removeAll(ops);
                    n.relation = Glossary.AMR + n.relation.substring(1);
                    root.var = name;
                    if (n.getInstance() != null) {
                        n.list.remove(n.getInstance());
                    }
                    if (root.getInstance() != null) {
                        n.var = Glossary.AMR + this.firstUpper(root.getInstance().var);
                        n.relation = Glossary.RDF_TYPE;
                        this.treatInstance(root);
                    }
                    if (!n.list.isEmpty()){
                        toAdd.addAll(n.list);
                        n.list.clear();
                    }
                    n.setStatus(REMOVE);
                }

            } else if (n.getInstance() != null && n.getInstance().var.equalsIgnoreCase(Glossary.OP_NAME) && !n.getOps().isEmpty()) {
                ArrayList<Node> ops = n.getOps();
                String name = "";
                for (Node n1 : ops) {
                    name += Glossary.OP_JOINER + n1.var;
                    n.list.remove(n1);
                }
                name = Glossary.LITERAL + name.substring(1);
                n.var = name;
                this.treatInstance(n);
                //this.removeInstance(n);

            }

            if (n.relation.equalsIgnoreCase(Glossary.AMR_WIKI)) {

                //caso :wiki
                if (n.var.equalsIgnoreCase(Glossary.AMR_MINUS)) {
                    n.setStatus(REMOVE);
                } else {
                    n.relation = Glossary.OWL_SAME_AS;
                    n.var = Glossary.DBPEDIA + n.var;
                    n.setStatus(OK);
                }

            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_MODE)
                    && (n.var.equalsIgnoreCase(Glossary.AMR_IMPERATIVE)
                    || n.var.equalsIgnoreCase(Glossary.AMR_EXPRESSIVE))
                    || n.var.equalsIgnoreCase(Glossary.AMR_INTERROGATIVE)) {
                n.relation = Glossary.AMR + n.relation.substring(1);
                n.var = Glossary.AMR + n.var.replace(":", "");

            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_POLITE)) {
                if (!n.var.equalsIgnoreCase(Glossary.AMR_PLUS)) {
                    n.add(new Node(Glossary.BOXING_FALSE, Glossary.BOXING_HAS_TRUTH_VALUE, OK));
                }
                n.var = Glossary.AMR + n.relation.substring(1);
                n.relation = Glossary.BOXING_HAS_MODALITY;
                n.add(new Node(Glossary.DUL_HAS_QUALITY, Glossary.RDFS_SUB_PROPERTY_OF, OK));

            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_POLARITY) && n.getInstance() != null && n.getInstance().var.equalsIgnoreCase(Glossary.AMR_UNKNOWN)) {
                n.relation = Glossary.BOXING_HAS_TRUTH_VALUE;
                n.var = Glossary.BOXING_UNKNOWN;
                this.removeInstance(n);
                //n.list.remove(n.getInstance());

            } else if (n.getInstance() != null && n.getInstance().var.equalsIgnoreCase(Glossary.AMR_UNKNOWN)) {
                n.var = Glossary.OWL_THING; //Glossary.BOXING_UNKNOWN;
                this.removeInstance(n);
                //n.list.remove(n.getInstance());
                if (n.relation.equalsIgnoreCase(Glossary.AMR_QUANT)) {
                    n.relation = Glossary.AMR + Glossary.AMR_QUANT.substring(1);
                }

                //n.setStatus(REMOVE);
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
                n.add(new Node(Glossary.OWL_THING, Glossary.RDF_TYPE, OK));
                this.setEquals(root);
                n.setStatus(OK);

            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_POSS) && this.getInstance(root.getNodeId()) != null) {

                // caso :poss
                n.relation = FRED + (this.getInstance(root.getNodeId()).var.replaceAll(FRED, "") + Glossary.OF);
                n.setStatus(OK);

            } else if ((n.relation.equalsIgnoreCase(Glossary.AMR_QUANT) || n.relation.equalsIgnoreCase(Glossary.AMR_FREQUENCY)) && n.var.matches(Glossary.NN_INTEGER)
                    && n.getInstance() == null) {

                //casi :quant  e :frequency con valore numerico 
                n.relation = Glossary.DUL_HAS_DATA_VALUE;
                if ((n.var.matches(Glossary.NN_INTEGER) && !new BigInteger(n.var).equals(new BigInteger("1"))) || !n.var.matches(Glossary.NN_INTEGER)) {
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

                n.relation = Glossary.QUANT_HAS_QUANTIFIER;
                if (n.getInstance().var.equalsIgnoreCase(Glossary.FRED_MULTIPLE)) {
                    n.var = Glossary.QUANT + Glossary.FRED_MULTIPLE;
                    this.removeInstance(n);
                }
                //n.var = Glossary.QUANT + Glossary.FRED_MULTIPLE;
                //this.removeInstance(n);
                n.setStatus(OK);

            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_QUANT_OF) && n.getInstance() != null) {
                n.relation = Glossary.FRED + this.getInstance(root.getNodeId()).var + Glossary.OF;
                n.setStatus(OK);

            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_AGE) && root.getInstance() != null && n.getInstance() == null) {

                //caso :age con valore numerico
                String age = n.var;
                n.relation = TOP;
                n.var = "a";
                n.add(new Node("age-01", Glossary.INSTANCE));
                Node n1 = root.getCopy(AMR_ARG1);
                this.nodes.add(n1);
                n.add(n1);
                n.add(new Node(age, AMR_ARG2));
                n = listElaboration(n);

            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_AGE) && root.getInstance() != null && n.getInstance() != null) {

                //caso :age con valore Entity
                n.relation = TOP;

                Node n1 = root.getCopy(AMR_ARG1);
                this.nodes.add(n1);

                Node new_age_node = new Node("zz1", AMR_ARG2);
                this.nodes.add(new_age_node);
                new_age_node.addAll(n.list);
                n.list = new ArrayList<>();
                n.add(n1);
                n.var = "a";
                n.add(new Node("age-01", Glossary.INSTANCE));
                n.add(new_age_node);
                //n.add(new Node(age, AMR_ARG2));
                n = listElaboration(n);

            } else if ((n.relation.equalsIgnoreCase(Glossary.AMR_DEGREE) //    || n.relation.equalsIgnoreCase(Glossary.AMR_TIME)
                    ) && n.getInstance() != null
                    && !isVerb(n.getInstance().var)) {

                //casi :degree :time con instance
                n.var = FRED + firstUpper(n.getInstance().var);
                //TODO
                //this.setEquals(n);
                this.removeInstance(n);
                //n.list.remove(n.getInstance());

            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_MANNER) && n.getInstance() != null
                    && !isVerb(n.getInstance().var)) {
                //caso :manner con forma verbale
                if (n.getInstance().var.matches(Glossary.AMR_VERB2) || !mannerAdverb(n.getInstance().var).isEmpty()) {
                    if (n.getInstance().var.matches(Glossary.AMR_VERB2) && !mannerAdverb(n.getInstance().var.substring(0, n.getInstance().var.length() - 3)).isEmpty()) {
                        n.var = FRED + firstUpper(mannerAdverb(n.getInstance().var.substring(0, n.getInstance().var.length() - 3)));
                    } else if (!mannerAdverb(n.getInstance().var).isEmpty()) {
                        n.var = FRED + firstUpper(mannerAdverb(n.getInstance().var));
                    } else {
                        n.var = FRED + firstUpper(n.getInstance().var.substring(0, n.getInstance().var.length() - 3) /*+ "ly"*/);
                    }

                    this.removeInstance(n);
                    //n.list.remove(n.getInstance());
                } else {

                    //caso :manner non verbale
                    n.relation = Glossary.AMR + Glossary.AMR_MANNER.substring(1);
                }
            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_MANNER) && n.getInstance() != null && root.getInstance() != null
                    && isVerb(n.getInstance().var)) {
                // caso manner verbale 
                n.relation = FRED + root.getInstance().var.substring(0, root.getInstance().var.length() - 3) + Glossary.BY;

            } else if (n.relation.startsWith(Glossary.AMR_PREP)) {
                n.relation = n.relation.replace(Glossary.AMR_PREP, FRED);
            } else if ((n.relation.equalsIgnoreCase(Glossary.AMR_PART_OF)
                    || n.relation.equalsIgnoreCase(Glossary.AMR_CONSIST_OF)) && root.getInstance() != null) {
                n.relation = n.relation.replace(":", Glossary.AMR);
            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_EXTENT) && n.getInstance() != null) {
                n.var = FRED + firstUpper(n.getInstance().var);
                this.removeInstance(n);
                //n.list.remove(n.getInstance());
            }

            if (n.relation.equalsIgnoreCase(Glossary.AMR_VALUE) && n.getInstance() == null) {
                if (n.var.matches(Glossary.NN_INTEGER2) || n.var.matches(Glossary.NN_INTEGER)) {
                    n.relation = Glossary.DUL_HAS_DATA_VALUE;
                } else {
                    n.relation = Glossary.DUL_HAS_QUALITY;
                    n.var = FRED + firstUpper(n.var);
                }
            }

            if (n.relation.equalsIgnoreCase(Glossary.AMR_CONJ_AS_IF)) {
                n.relation = FRED + "as-if";
                n.setStatus(OK);
            }

            if (n.relation.equalsIgnoreCase(Glossary.AMR_CONDITION)) {
                n.relation = Glossary.DUL_HAS_PRECONDITION;
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
                ops.get(0).add(newN);
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

        for (Iterator<Node> it = root.list.iterator();
                it.hasNext();) {
            Node n = it.next();
            if (n.getStatus() == REMOVE) {
                this.removed.add(n);
                it.remove();
            }
        }

        if (!toAdd.isEmpty()) {
            root.addAll(toAdd);
            toAdd.clear();
            root = listElaboration(root);
        }

        for (Node n : root.list) {
            //richiama il metodo di traduzione ricorsivamente
            n = listElaboration(n);

        }

        if (root.relation.equalsIgnoreCase(TOP)
                && !root.getOps().isEmpty()) {

            ArrayList<Node> ops = root.getOps();
            root.list.removeAll(ops);

            Node newRoot = new Node("", "");
            newRoot.substitute(root);
            newRoot.relation = Glossary.DUL_ASSOCIATED_WITH;
            nodes.add(newRoot);
            root.substitute(ops.get(0));
            root.add(newRoot);
            root.relation = TOP;

            for (Node n1 : ops) {
                n1.relation = TOP;
                if (!root.equals(n1)) {
                    root.add(n1);
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
    private Node verbs_elaboration(Node root) {

        if (root == null) {
            return null;
        }
        String lemma = root.getVerb();
        if (root.getType() == VERB) {

            Propbank pb = Propbank.getPropbank();
            String lemma2 = lemma.substring(3).replace(".", "-");
            ArrayList<ArrayList<String>> roles = pb.find(Glossary.PB_ROLESET + lemma2, Glossary.PropbankFrameFields.PB_Frame);

            if (!roles.isEmpty()) {
                String label = roles.get(0).get(Glossary.PropbankFrameFields.PB_FrameLabel.ordinal());
                if (label.length() > 0) {
                    root.getChild(Glossary.RDF_TYPE).list.add(new Node(label, Glossary.RDFS_LABEL, OK));
                }

                ArrayList<String> newNodesVars = new ArrayList<>();
                for (ArrayList<String> l : roles) {

                    String fnFrame = l.get(Glossary.PropbankFrameFields.FN_Frame.ordinal());
                    if (fnFrame != null && fnFrame.length() > 0 && !newNodesVars.contains(fnFrame)) {
                        newNodesVars.add(fnFrame);
                    }

                    String vaFrame = l.get(Glossary.PropbankFrameFields.VA_Frame.ordinal());
                    if (vaFrame != null && vaFrame.length() > 0 && !newNodesVars.contains(vaFrame)) {
                        newNodesVars.add(vaFrame);
                    }
                    //break;
                }
                Node type = root.getChild(Glossary.RDF_TYPE);
                for (String var : newNodesVars) {
                    Node newNode = new Node(var, Glossary.FS_SCHEMA_SUBSUMED_UNDER, OK);
                    type.list.add(newNode);
                    newNode.visibility = false;
                }

                // search for roles
                for (Node n : root.getArgs()) {

                    String r = Glossary.PB_ROLESET + lemma2; //+ "__" + n.relation.substring(4);
                    ArrayList<ArrayList<String>> pbroles = pb.find(r, Glossary.PropbankRoleFields.PB_Frame, Glossary.PB_SCHEMA + n.relation.substring(1), Glossary.PropbankRoleFields.PB_ARG);

                    if (!pbroles.isEmpty() && pbroles.get(0).get(Glossary.PropbankRoleFields.PB_Role.ordinal()) != null) {
                        n.relation = pbroles.get(0).get(Glossary.PropbankRoleFields.PB_Role.ordinal());
                    }
                    n.setStatus(OK);
                }
            }
        }

        for (Node n : root.list) {
            n = verbs_elaboration(n);
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
        string = string.substring(0, 1).toUpperCase() + string.substring(1); //.toLowerCase();
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
        if (root == null) {
            return root;
        }
        ArrayList<Node> invNodes = new ArrayList<>();
        invNodes = root.getInverses(invNodes);

        if (invNodes.isEmpty()) {
            return root;
        }
        ArrayList<Node> inversi = root.getInverses();

        if (root.relation.equalsIgnoreCase(TOP) && inversi.size() == 1 && root.getNodeId() == 0) {
            Node n = root.getInverse();
            root.list.remove(n);
            root.relation = n.relation.substring(0, n.relation.length() - 3);
            n.add(root);
            n.relation = TOP;
            return inverseChecker(n);
        } else if (inversi.size() == 1) {
            Node n = root.getInverse();
            Node newNode = root.getCopy(n.relation.substring(0, n.relation.length() - 3));
            this.nodes.add(newNode);
            n.relation = TOP;
            n.add(newNode);
        } else {
            for (Node n : inversi) {
                Node newNode = root.getCopy(n.relation.substring(0, n.relation.length() - 3));
                this.nodes.add(newNode);
                n.relation = TOP;
                n.add(newNode);
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
            root.add(new Node(Glossary.FRED_TOPIC, Glossary.DUL_HAS_QUALITY, OK));
        }
        return root;
    }

    /*
    Verifica se la parola in input è un verbo contenuto nella Pb2vn
     */
    private boolean isVerb(String word) {
        Propbank prb = Propbank.getPropbank();
        ArrayList<ArrayList<String>> result = prb.find(Glossary.PB_ROLESET + word, Glossary.PropbankFrameFields.PB_Frame);
        return result != null && !result.isEmpty();
    }

    private boolean isVerb(String word, ArrayList<Node> list) {
        Propbank prb = Propbank.getPropbank();
        ArrayList<ArrayList<String>> result;
        result = prb.find(word, list);
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
                boolean flag = true;

                for (int i = 0; i < Glossary.SPECIAL_INSTANCES.length; i++) {
                    if (instance.var.equalsIgnoreCase(Glossary.SPECIAL_INSTANCES[i])) {
                        instanceInList.var = Glossary.SPECIAL_INSTANCES_PREFIX[i] + firstUpper(instance.var);
                        flag = false;
                        break;
                    }
                }

                for (int i = 0; i < Glossary.AMR_INSTANCES.length; i++) {
                    if (instance.var.equalsIgnoreCase(Glossary.AMR_INSTANCES[i])
                            && root.prefix) {
                        instanceInList.var = Glossary.AMR + firstUpper(instance.var);
                        flag = false;
                        break;
                    }
                }

                for (int i = 0; i < Glossary.AMR_ALWAYS_INSTANCES.length; i++) {
                    if (instance.var.equalsIgnoreCase(Glossary.AMR_ALWAYS_INSTANCES[i])) {
                        instanceInList.var = Glossary.AMR + firstUpper(instance.var);
                        flag = false;
                        break;
                    }
                }

                if (flag) {
                    instanceInList.var = FRED + firstUpper(instance.var);
                }

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
    Effettua le elaborazioni necessarie per l'eliminazioni delle istanze dei nodi non verbo
     */
    private Node otherInstanceElaboration(Node root, String prefix) {
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
                boolean flag = true;

                for (int i = 0; i < Glossary.SPECIAL_INSTANCES.length; i++) {
                    if (instance.var.equalsIgnoreCase(Glossary.SPECIAL_INSTANCES[i])) {
                        instanceInList.var = Glossary.SPECIAL_INSTANCES_PREFIX[i] + firstUpper(instance.var);
                        flag = false;
                        break;
                    }
                }

                if (flag) {
                    instanceInList.var = prefix + firstUpper(instance.var);
                }

                if (!instanceInList.relation.startsWith(Glossary.AMR_RELATION_BEGIN)) {
                    instanceInList.setStatus(OK);
                }

            } else {
                n.var = nVar;
            }
        }
        return root;
    }

    /**
     * Disambigua per ora solo DUL
     *
     * @param var
     * @return
     */
    private String disamb(String var) {
        for (int i = 0; i < Glossary.DULS_CHECK.length; i++) {
            if (Glossary.DULS_CHECK[i].equalsIgnoreCase(var)) {
                return Glossary.DULS[i];
            }
        }
        return FRED + var;
    }

    /*
    Effettua le elaborazioni necessarie sul nodo root prima dell'elaborazione della lista dei figli
     */
    private Node rootElaboration(Node root) {
        Node instance = this.getInstance(root.getNodeId()); //root.getInstance();
        if (root.getInstance() != null) {

        }
        // casi "and" e "or" seguito da :opx
        if (root.getInstance() != null && (root.getInstance().var.equalsIgnoreCase(Glossary.AND) || root.getInstance().var.equalsIgnoreCase(Glossary.OR))) {

            if (root.getInstance().var.equalsIgnoreCase(Glossary.AND)) {
                root.getInstance().var = Glossary.CONJUNCT;
            } else {
                root.getInstance().var = Glossary.DISJUNCT;
            }

            ArrayList<Node> ops = root.getOps();
            for (Node n1 : ops) {
                n1.relation = Glossary.DUL_HAS_MEMBER;
            }

        }

        if (instance == null) {
            return root;
        }

        if (root.getChild(Glossary.AMR_CONCESSION) != null) {
            Node concession = root.getChild(Glossary.AMR_CONCESSION);
            Node condition = root.getChild(Glossary.AMR_CONDITION);
            Node swap = new Node("", "");

            if (concession != null && concession.getInstance() != null && concession.getInstance().var.equalsIgnoreCase(Glossary.EVEN_IF)
                    && concession.getChild(Glossary.AMR_OP1) != null) {

                root.list.remove(concession);
                Node op1 = concession.getChild(Glossary.AMR_OP1);
                Node modality = new Node(Glossary.BOXING_NECESSARY, Glossary.BOXING_HAS_MODALITY, OK);
                Node quality = new Node(Glossary.FRED_EVEN, Glossary.DUL_HAS_QUALITY, OK);
                root.add(modality);
                op1.add(modality);
                swap.substitute(root);
                root.substitute(op1);
                root.relation = swap.relation;
                swap.relation = Glossary.FRED_ENTAILS;
                swap.add(quality);
                root.add(swap);
            }

            if (concession != null && concession.getInstance() != null && concession.getInstance().var.equalsIgnoreCase(Glossary.EVEN_WHEN)
                    && concession.getChild(Glossary.AMR_OP1) != null) {

                root.list.remove(concession);
                Node op1 = concession.getChild(Glossary.AMR_OP1);

                Node quality = new Node(Glossary.FRED_EVEN, Glossary.DUL_HAS_QUALITY, OK);
                op1.relation = Glossary.FRED_WHEN;

                root.add(quality);
                root.add(op1);
            }

            if (condition != null && condition.getInstance() != null) {
                root.list.remove(condition);
                Node modality = new Node(Glossary.BOXING_NECESSARY, Glossary.BOXING_HAS_MODALITY, OK);
                root.add(modality);
                condition.add(modality);
                swap.substitute(root);
                root.substitute(condition);
                root.relation = swap.relation;
                swap.relation = Glossary.FRED_ENTAILS;
                root.add(swap);

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
                op1.relation = FRED + direction.getInstance().var + Glossary.OF;
                direction.add(op1);
                Node quant = root.getChild(Glossary.AMR_QUANT);
                root.getInstance().var = quant.getInstance().var.replace(Glossary.QUANTITY, "");
            }
        }

        if (instance.var.length() > 3 && instance.var.substring(instance.var.length() - 3).matches(Glossary.AMR_VERB)
                && !isVerb(instance.var) && root.getArgs().size() == 1) {
            //caso predicato nominale
            topic = false;
            Node arg = root.getArgs().get(0);
            root.list.remove(arg);

            if (root.getChild(Glossary.AMR_DEGREE) != null && root.getChild(Glossary.AMR_DEGREE).getInstance() != null) {
                //caso :degree senza :compared-to
                instance.var = firstUpper(root.getChild(Glossary.AMR_DEGREE).getInstance().var) + firstUpper(instance.var);
                root.list.remove(root.getChild(Glossary.AMR_DEGREE));
            }

            int parentId = root.getNodeId();
            int argId = arg.getNodeId();
            String parentVar = instance.var.substring(0, instance.var.length() - 3);
            if (arg.getInstance() != null) {
                instance.var = arg.getInstance().var;
                this.removeInstance(arg);
                //arg.list.remove(arg.getInstance());
            }
            arg.makeEquals(parentId);
            arg.relation = Glossary.DUL_HAS_QUALITY;
            arg.var = FRED + firstUpper(parentVar.replaceAll(FRED, ""));
            root.addAll(arg.list);
            arg.list.clear();
            root.add(arg);
            root.makeEquals(argId);
            arg.setStatus(OK);

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

        //TODO verificare perchè non funziona con instance
        /*
        if (root.getInstance() != null && root.getInstance().var.matches(Glossary.AMR_QUANTITY) && root.getChild(Glossary.AMR_UNIT) != null) {
            //caso X-quantity + :unit
            Node unit = root.getChild(Glossary.AMR_UNIT);
            root.list.remove(unit);
            this.removeInstance(root);
            //root.list.remove(root.getInstance());
            unit.addAll(root.list);
            unit.relation = root.relation;
            ArrayList<Node> eq = this.getEquals(root);
            for (Node n : eq) {
                if (n.list.isEmpty()) {
                    n.makeEquals(unit);
                }
            }
            root.substitute(unit);
        }
        
        if (root.getChild(Glossary.AMR_QUANT) != null) {

            Node quant = root.getChild(Glossary.AMR_QUANT);

            Node newNode = new Node("", "");

            if (quant.getInstance() != null && quant.getInstance().var.matches(Glossary.AMR_QUANTITY)
                    && quant.getChild(Glossary.AMR_UNIT) != null) {
                Node unit = quant.getChild(Glossary.AMR_UNIT);
                quant.list.remove(unit);
                this.removeInstance(quant);
                //quant.list.remove(quant.getInstance());
                unit.addAll(quant.list);
                unit.relation = quant.relation;
                quant.substitute(unit);
                root.list.remove(quant);
                newNode.substitute(root);
                nodes.add(newNode);
                newNode.relation = FRED + unit.getInstance().var + Glossary.OF;
                quant.add(newNode);
                quant.relation = root.relation;

                ArrayList<Node> eq = this.getEquals(root);
                for (Node n : eq) {
                    if (n.list.isEmpty()) {
                        n.makeEquals(quant);
                    }
                }
                root.substitute(quant);
            }
        }*/
        if (root.getChild(Glossary.AMR_SCALE) != null && root.getChild(Glossary.AMR_SCALE).getInstance() != null) {
            //caso :scale
            Node scale = root.getChild(Glossary.AMR_SCALE);
            scale.relation = Glossary.FRED_ON;
            scale.var = firstUpper(scale.getInstance().var) + "_scale";
            this.removeInstance(scale);
            //scale.list.remove(scale.getInstance());
        }

        if (root.getChild(Glossary.AMR_ORD) != null) {
            //caso :ord
            Node ord = root.getChild(Glossary.AMR_ORD);
            root.list.remove(ord);
            this.removeInstance(ord);
            //ord.list.remove(ord.getInstance());
            root.addAll(ord.list);
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

    private Node dateEntity(Node root) {
        if (root.list.isEmpty() || (root.getInstance() != null
                && !root.getInstance().var.equalsIgnoreCase(Glossary.AMR_DATE_ENTITY))) {
            return root;
        }
        date_child_elaboration(root.getChild(Glossary.AMR_DATE_ERA));
        date_child_elaboration(root.getChild(Glossary.AMR_DATE_DECADE));
        date_child_elaboration(root.getChild(Glossary.AMR_DATE_CENTURY));
        date_child_elaboration(root.getChild(Glossary.AMR_DATE_CALENDAR));
        date_child_elaboration(root.getChild(Glossary.AMR_DATE_WEEKDAY));
        date_child_elaboration(root.getChild(Glossary.AMR_DATE_DAY_PERIOD));
        date_child_elaboration(root.getChild(Glossary.AMR_DATE_QUARTER));
        date_child_elaboration(root.getChild(Glossary.AMR_DATE_SEASON));
        date_child_elaboration(root.getChild(Glossary.AMR_DATE_TIMEZONE));
        date_child_elaboration(root.getChild(Glossary.AMR_DATE_YEAR));
        date_child_elaboration(root.getChild(Glossary.AMR_DATE_MONTH));
        date_child_elaboration(root.getChild(Glossary.AMR_DATE_DAY));
        date_child_elaboration(root.getChild(Glossary.AMR_DATE_YEAR2));
        return root;
    }

    private void date_child_elaboration(Node child) {
        if (child != null) {

            child.relation = Glossary.AMRB + child.relation.substring(1);
            child = this.otherInstanceElaboration(child, Glossary.AMRB);
        }
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
            root.add(quality);
            this.removeInstance(root);
            if (!root.relation.equalsIgnoreCase(TOP)) {
                root.relation = Glossary.PREP_SUBSTITUTION;
            } else {
                Node first = root.list.get(0);
                root.list.remove(first);
                first.addAll(root.list);
                root.substitute(first);
            }
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

    private Node residual(Node root) {
        if (root.var.contains(Glossary.LITERAL)) {
            root.var = root.var.replaceAll(Glossary.LITERAL, "");
            root.setStatus(OK);
        }

        if (root.var.contains(FRED + Glossary.LITERAL2)) {
            root.var = root.var.replaceAll(FRED + Glossary.LITERAL2, "");
            root.setStatus(OK);
        }

        if (root.var.contains("fred:")) {
            String temp = root.var.replace("fred:", "");
            temp = this.disamb(temp);
            root.var = temp;
        }

        if (root.var.contains("fred:Fred:")) {
            root.var = root.var.replace("fred:Fred:", "");
            root.var = FRED + this.firstUpper(root.var);
        }
        if (root.var.matches(Glossary.AMR_VERB2) && root.getStatus() != OK && root.var.length() > 3) { // && this.isVerb(root.var)
            root.add(new Node(Glossary.DUL_EVENT, Glossary.RDFS_SUBCLASS_OF, OK));
            ArrayList<Node> l = root.getArgs();

            String verb;
            if (root.var.contains(":")) {
                verb = root.var.substring(root.var.indexOf(":") + 1).toLowerCase();
            } else {
                verb = root.var;
            }

            for (Node n : l) {
                n.setVerb(verb);
            }
            root.var = root.var.substring(0, root.var.length() - 3);
            root.setType(VERB);

        } else if (root.var.matches(Glossary.AMR_VERB2) && root.getStatus() != OK) {
            String newVar;
            if (root.var.contains(":")) {
                newVar = root.var.substring(root.var.indexOf(":") + 1).toLowerCase();
            } else {
                newVar = root.var;
            }
            root.setMalformed(true);
            root.add(new Node(FRED + this.firstUpper(newVar).substring(0, newVar.length() - 3), Glossary.RDF_TYPE, OK));
            root.var = FRED + newVar.substring(0, newVar.length() - 3) + "_" + occurrence(newVar.substring(0, newVar.length() - 3));
            setEquals(root);
        }

        if (root.relation.matches(Glossary.AMR_ARG)) {
            root.relation = Glossary.VN_ROLE_PREDICATE;
            root.setMalformed(true);
            root.setStatus(OK);
        }
        /*
        if (root.relation.equalsIgnoreCase(Glossary.AMR_DOMAIN)) {

            String newRelation = FRED + "attribute" + Glossary.OF;
            root.relation = newRelation;
            root.setStatus(OK);
        }*/

        if (root.relation.equalsIgnoreCase(Glossary.AMR_COMPARED_TO)) {
            String newRelation = Glossary.AMR + Glossary.AMR_COMPARED_TO;
            root.relation = newRelation;
            root.setStatus(OK);
        }

        if (root.relation.equalsIgnoreCase(Glossary.AMR_MODE)
                && (root.var.equalsIgnoreCase(Glossary.AMR_IMPERATIVE)
                || root.var.equalsIgnoreCase(Glossary.AMR_EXPRESSIVE))
                || root.var.equalsIgnoreCase(Glossary.AMR_INTERROGATIVE)) {
            root.relation = Glossary.AMR + root.relation.substring(1);
            root.var = Glossary.AMR + root.var.replace(":", "");
            root.setStatus(OK);
        }

        if (root.relation.equalsIgnoreCase(Glossary.AMR_CONSIST_OF) || root.relation.equalsIgnoreCase(Glossary.AMR_UNIT)) {
            root.relation = root.relation.replace(":", Glossary.AMR);
            root.setStatus(OK);
        }

        if (root.relation.startsWith(":")) {
            root.relation = root.relation.replace(":", Glossary.AMR);
            if (!root.var.contains(":") && root.getStatus() != OK) {
                root.var = Glossary.FRED + root.var;
            }
            root.setStatus(OK);
        }

        if (root.var.equalsIgnoreCase(Glossary.AMR_MINUS) && root.relation.equalsIgnoreCase("pblr:polarity")) {
            root.var = FRED + "Negative";
        }

        for (Node n : root.getList()) {
            if (!n.var.contains(":") && this.vars.contains(n.var)) {
                n.var = FRED + "malformed_amr/" + n.var;
                n.setMalformed(true);
            }
        }

        for (Iterator<Node> it = root.list.iterator(); it.hasNext();) {
            Node n = it.next();
            if (n.getStatus() == REMOVE) {
                this.removed.add(n);
                it.remove();
            }
        }

        if (!root.var.contains(":") && root.var.matches(Glossary.AMR_VAR)) {
            root.var = FRED + "Undefined";
            root.setMalformed(true);
        }

        for (Node n : root.getList()) {

            n = residual(n);
        }
        return root;
    }

    private Node getInstance(int nodeId) {

        for (Node n : this.nodesCopy) {
            if (n.getNodeId() == nodeId && n.getInstance() != null) {
                return n.getInstance();
            }
        }
        return null;
    }

    private void removeInstance(Node root) {
        for (Node n : this.getEquals(root)) {
            n.var = root.var;
        }
        if (root.getInstance() != null) {
            root.list.remove(root.getInstance());
        }
    }

    private void processInstance(Node root) {
        Node ins = getInstance(root.getNodeId());
        if (ins != null) {
            for (Node n : this.getEquals(root)) {
                n.var = ins.var;
            }
            if (root.getInstance() != null) {
                root.list.remove(root.getInstance());
            }
        }
    }

    private void treatInstance(Node root) {
        for (Node n : this.getEquals(root)) {
            n.var = root.var;
        }
        if (root.getInstance() != null) {
            root.getInstance().setStatus(REMOVE);
        }
    }

    private Node multi_sentence(Node root) {
        if (root.getInstance() != null && root.getInstance().var.equalsIgnoreCase(Glossary.AMR_MULTI_SENTENCE)) {
            ArrayList<Node> sentences = root.getSnt();
            Node newRoot = sentences.remove(0);
            newRoot.relation = TOP;
            newRoot.parent = null;
            newRoot.list.addAll(sentences);
            for (Node n : sentences) {
                n.parent = newRoot;
                n.relation = TOP;
            }
            return newRoot;
        }

        for (Node n : root.list) {
            n = multi_sentence(n);
        }

        return root;
    }

    private Node add_parent_list(Node root) {
        ArrayList<Node> to_add = root.get_nodes_with_parent_list_not_empty();
        if (!to_add.isEmpty()) {

            for (Node n : to_add) {
                for (Node n1 : n.parent_list) {
                    boolean flag = false;
                    for (Node n2 : root.list) {
                        if (n1.relation.equalsIgnoreCase(n2.relation)
                                && n1.var.equalsIgnoreCase(n2.var)) {
                            flag = true;
                        }
                    }
                    if (!flag) {
                        root.list.add(n1);
                    }
                }
                root.list.remove(n);
            }

        }

        for (Node n : root.list) {
            n = add_parent_list(n);
        }
        return root;
    }

    private Node li_verify(Node root) {

        if (root.relation.equalsIgnoreCase(Glossary.AMR_LI)) {
            root.relation = TOP;
            String var = root.parent.var;
            Node new_instance = new Node(Glossary.REIFI_HAVE_LI, Glossary.INSTANCE);
            this.nodes.add(new_instance);

            Node arg1 = new Node(root.var, Glossary.AMR_ARG1);
            this.nodes.add(arg1);
            Node arg2 = new Node(var, Glossary.AMR_ARG2);
            this.nodes.add(arg2);
            arg2.makeEquals(root.parent);
            root.var = "li_" + root.getNodeId();
            root.list.add(new_instance);
            root.list.add(arg1);
            root.list.add(arg2);
        }

        for (Node n : root.list) {
            n = li_verify(n);
        }
        return root;
    }

    Node control_ops(Node root) {
        Node ins = root.getInstance();
        if (ins != null && (!ins.var.equalsIgnoreCase(Glossary.OP_NAME) || !ins.var.equalsIgnoreCase(Glossary.FRED_MULTIPLE))) {
            return root;
        }

        if (!root.getOps().isEmpty()) {
            for (Node n : root.getOps()) {
                if (n.getInstance() == null) {
                    if (n.var.matches(Glossary.NN_INTEGER)) {
                        n.relation = Glossary.DUL_HAS_DATA_VALUE;
                        if ((n.var.matches(Glossary.NN_INTEGER)
                                && !new BigInteger(n.var).equals(new BigInteger("1")))
                                && root.getChild(Glossary.QUANT_HAS_QUANTIFIER) == null
                                && (ins == null || !ins.var.equalsIgnoreCase(Glossary.AMR_VALUE_INTERVAL))) {
                            root.list.add(new Node(Glossary.QUANT + Glossary.FRED_MULTIPLE, Glossary.QUANT_HAS_QUANTIFIER, OK));
                        }
                    } else {
                        n.relation = Glossary.DUL_ASSOCIATED_WITH;
                    }
                }
            }
        }
        for (Node n : root.list) {
            n = control_ops(n);
        }
        return root;
    }

    private Node mod_verify(Node root) {
        Node instance = this.getInstance(root.getNodeId());
        Node dom = root.getChild(Glossary.AMR_DOMAIN);
        ArrayList<Node> mods = root.getChildren(Glossary.AMR_MOD);

        for (Node mod : mods) {

            if (mod != null) {

                Node mod_instance = null;
                if (mod.getInstance() != null) {
                    mod_instance = mod.getInstance();
                } else if (this.getInstance(mod.getNodeId()) != null) {
                    mod_instance = this.getInstance(mod.getNodeId());
                }

                if (mod.getChild(Glossary.AMR_DEGREE) != null
                        && mod.getChild(Glossary.AMR_COMPARED_TO) != null
                        && mod_instance != null
                        && instance != null) {

                    //caso :mod + :degree + :compared-to
                    instance.var = mod_instance.var + firstUpper(instance.var);
                    this.removeInstance(mod);
                    root.list.remove(mod);
                    root.addAll(mod.list);

                } else if (mod_instance != null
                        && instance != null
                        && !isVerb(mod_instance.var)
                        && !mod_instance.var.equalsIgnoreCase(Glossary.DISJUNCT)
                        && !mod_instance.var.equalsIgnoreCase(Glossary.CONJUNCT)
                        && mod.getChild(Glossary.AMR_NAME) == null) {
                    String mod_ins;
                    if (mod.getInstance() != null) {
                        mod_ins = mod.getInstance().var;
                    } else {
                        mod_ins = this.getInstance(mod.getNodeId()).var;
                    }

                    boolean contains = Glossary.ADJECTIVE.contains(mod_ins);
                    boolean demonstratives = Glossary.DEMONSTRATIVES.contains(" " + mod_ins + " ");

                    if (contains) {
                        mod.relation = Glossary.DUL_HAS_QUALITY;
                        mod.var = FRED + firstUpper(mod_ins);
                        this.removeInstance(mod);
                    } else if (demonstratives) {
                        mod.relation = Glossary.QUANT_HAS_DETERMINER;
                        mod.var = FRED + firstUpper(mod_ins);
                        this.removeInstance(mod);
                    } else {

                        if (dom == null) {
                            String root_ins = instance.var;
                            root.var = FRED + root_ins.toLowerCase() + "_" + this.occurrence(root_ins);
                            this.removeInstance(root);
                            mod.var = FRED + this.firstUpper(mod_ins.replace(FRED, "")) + this.firstUpper(root_ins.replace(FRED, ""));
                            this.removeInstance(mod);
                            mod.relation = Glossary.RDF_TYPE;
                            if (mod.getChild(Glossary.RDFS_SUBCLASS_OF) == null) {
                                mod.list.add(new Node(FRED + this.firstUpper(root_ins.replace(FRED, "")), Glossary.RDFS_SUBCLASS_OF));
                            }
                            mod.list.add(new Node(FRED + this.firstUpper(mod_ins.replace(FRED, "")), Glossary.DUL_ASSOCIATED_WITH));

                        } else {
                            String root_ins = instance.var;
                            root.var = FRED + this.firstUpper(mod_ins.replace(FRED, "")) + this.firstUpper(root_ins.replace(FRED, ""));
                            instance.var = root.var;
                            this.removeInstance(root);
                            mod.var = FRED + this.firstUpper(mod_ins.replace(FRED, ""));
                            mod.relation = Glossary.DUL_ASSOCIATED_WITH;
                            this.removeInstance(mod);
                            if (root.getChild(Glossary.RDFS_SUBCLASS_OF) == null) {
                                root.list.add(new Node(FRED + this.firstUpper(root_ins.replace(FRED, "")), Glossary.RDFS_SUBCLASS_OF));
                            }
                        }
                    }
                    mod.setStatus(OK);
                }
            }

        }

        for (Node n : root.list) {
            n = mod_verify(n);
        }
        return root;
    }

    private Node dom_verify(Node root) {
        Node dom = root.getChild(Glossary.AMR_DOMAIN);
        Node instance = this.getInstance(root.getNodeId());
        if (dom != null) {
            topic = false;
            dom.relation = TOP;
            String n_var;
            if (dom.getInstance() == null
                    && this.getInstance(dom.getNodeId()) != null) {
                n_var = this.getInstance(dom.getNodeId()).var;

            } else if (dom.getInstance() != null) {
                n_var = dom.getInstance().var;
            } else {
                n_var = FRED + dom.var.replace(Glossary.LITERAL, "");
                dom.var = n_var;
            }
            String rel = "";
            if (instance == null) {
                rel = Glossary.DUL_HAS_QUALITY;
            } else if (Glossary.ADJECTIVE.contains(instance.var)) {
                rel = Glossary.DUL_HAS_QUALITY;
                this.treatInstance(root);
                root.var = FRED + firstUpper(root.getInstance().var);

            } else {

                rel = Glossary.RDF_TYPE;
                root.var = FRED + firstUpper(instance.var);
                this.removeInstance(root);
            }
            Node new_node = root.getCopy(rel);
            dom.list.add(new_node);
            this.nodes.add(new_node);
        }
        for (Node n : root.list) {
            n = dom_verify(n);
        }
        return root;
    }

    private Node amr_integration(Node root) {
        
        String obj = root.var; // or  root.relation
        for (String a: Glossary.AMR_INTEGRATION){
            //System.out.println(Glossary.AMR+a.substring(1));
            if(obj.equalsIgnoreCase(Glossary.AMR+a.substring(1)) && !a.endsWith("_of")){
                root.list.add(new Node(Glossary.PB_GENERICROLE + a.substring(1), Glossary.OWL_EQUIVALENT_PROPERTY, OK));
                root.list.add(new Node(Glossary.OWL_OBJECT_PROPERTY, Glossary.RDF_TYPE, OK));
                root.list.add(new Node(Glossary.FS_SCHEMA_SEMANTIC_ROLE, Glossary.RDF_TYPE, OK));
                
            } else if (obj.equalsIgnoreCase(Glossary.AMR+a.substring(1)) && a.endsWith("_of")){
                root.list.add(new Node(Glossary.PB_GENERICROLE + a.substring(1).replace("_of", ""), Glossary.OWL_INVERSE_OF, OK));
                root.list.add(new Node(Glossary.OWL_OBJECT_PROPERTY, Glossary.RDF_TYPE, OK));
                root.list.add(new Node(Glossary.FS_SCHEMA_SEMANTIC_ROLE, Glossary.RDF_TYPE, OK));
            }
        }
        for (Node n : root.list) {
            n = amr_integration(n);
        }
        return root;
    }
}
