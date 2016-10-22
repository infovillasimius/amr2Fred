package amr2fred;

import static amr2fred.Glossary.ARG;
import static amr2fred.Glossary.ENDLESS;
import static amr2fred.Glossary.FRED;
import static amr2fred.Glossary.NULL;
import static amr2fred.Glossary.PIVOT;
import static amr2fred.Glossary.TOP;
import static amr2fred.Glossary.VN_ROLE;
import static amr2fred.Glossary.nodeStatus.AMR;
import static amr2fred.Glossary.nodeStatus.OK;
import static amr2fred.Glossary.nodeStatus.REMOVE;
import static amr2fred.Glossary.wordType.VERB;
import static amr2fred.NumberToWord.convert;
import java.util.ArrayList;
import java.util.Iterator;
import static amr2fred.Glossary.AMR_MOD;

/**
 *
 * @author anto
 */
public class Parser {

    private static Parser parser;               //contiene l'istanza singola del parser
    private ArrayList<Node> nodes;             //contiene tutti i nodi istanziati e consente di recuperare il nodo relativo a una variabile amr
    private ArrayList<Couple> couples;         //contiene le coppie formate dalle var trovate e dal numero di occorrenze di ognuna
    private ArrayList<Node> removed;           //contiene i nodi rimossi
    private ArrayList<Node> toAdd;             //contiene i nodi da aggiungere ad un nodo alla fine della iterazione di lavorazione della lista (usato per evitare errori di concurrency)
    private Node rootCopy;                     //contiene una copia dell'albero amr in lavorazione
    static int endless;                         //contiene il valore numerico delle operazioni di visita dei nodi del grafo amr - interrompe le operazioni in caso di errore di ricorsività
    private boolean topic = true;              //flag per l'aggiunta del valore topic ad un nodo

    private Parser() {
        this.nodes = new ArrayList<>();
        this.couples = new ArrayList<>();
        this.removed = new ArrayList<>();
        this.toAdd = new ArrayList<>();
    }

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
        return parser;
    }

    public Node getRootCopy() {
        return rootCopy;
    }

    public ArrayList<Node> getRemoved() {
        return removed;
    }

    /**
     * Effettua il parsing della stringa amr e restituisce il nodo radice Fred
     *
     * @param amr String che contiene il grafo in formato amr
     * @return Node nodo radice albero Fred
     */
    public Node parse(String amr) {
        ArrayList<String> amrList = string2Array(amr);
        Node root = getNodes(TOP, amrList);
        if (root != null) {
            endless = 0;
            this.rootCopy = root.getCopy();
            if (endless > ENDLESS) {
                this.rootCopy = new Node("Error", "Recursive");
                return root;
            }
        }
        root = predicate(root);
        root = findVnClass(root);

        return root;
    }

    /**
     * Restituisce l'albero ripulito dai nodi che presentano errori di
     * traduzione
     *
     * @param root nodo radice dell'albero che si intende ripulire da tutti i
     * nodi non ok
     * @return Node nodo radice albero senza errori
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
            //System.out.println(root.relation + " " + root.var + " " + root.getTreStatus() + " " + root.getStatus());
            return null;
        }
        //System.out.println(root);
        return root;
    }

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
                list.add(word);
            }
        } catch (java.lang.StringIndexOutOfBoundsException e) {
            return null;
        }

        return list;
    }

    private String normalize(String amr) {
        amr = amr.replace("(", " ( ");
        amr = amr.replace(")", " ) ");
        amr = amr.replace("/", " / ");
        amr = amr.replaceAll("\r\n|\r|\n", " ");
        amr = amr.replaceAll("\t", " ");

        while (amr.contains("  ")) {
            amr = amr.replace("  ", " ");
        }

        return amr.toLowerCase();
    }

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
                                        root.list.add(getNodes(amrList.get(i - 1), newList));
                                        //System.out.println(root);
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
                    root.list.add(new Node(amrList.get(++i), Glossary.INSTANCE));
                    break;
                default:
                    if (word.charAt(0) == ':' && amrList.get(i + 1).charAt(0) != '(') {
                        flag = false;
                        for (Node find : this.nodes) {
                            if (find.var.equalsIgnoreCase(amrList.get(i + 1))) {
                                Node newNode = find.getCopy(find, word);
                                root.list.add(newNode);
                                nodes.add(newNode);
                                flag = true;
                                break;
                            }
                        }

                        if (flag == false) {
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

    private Node predicate(Node root) {

        if (root == null) {
            return null;
        } else if (root.list.isEmpty()) {
            setEquals(root); //verificare comportamento
            return root;
        }

        if (endless > Glossary.ENDLESS) {
            return root;
        }
        root = this.argOf(root);
        root = this.listElaboration(root);
        root = this.instanceElaboration(root);

        return root;
    }

    private Node modality(Node root) {
        if (root == null || root.getInstance() == null) {
            return root;
        }
        Node instance = root.getInstance();
        String lemma = instance.var.substring(0, instance.var.length() - 3);
        if (lemma.equalsIgnoreCase("likely") || lemma.equalsIgnoreCase("recommend")) {
            Node arg1 = root.getChild(":arg1");
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
            Node arg2 = root.getChild(":arg2");
            if (arg2 != null) {
                //System.out.println(root.var);
                root.var = arg2.var;
                root.list.add(new Node(Glossary.BOXING_NECESSARY, Glossary.BOXING_HAS_MODALITY, OK));
                root.list.addAll(arg2.list);
                root.list.remove(arg2);
                root.list.remove(instance);
                root = modality(root);
                root.setStatus(OK);
            }
        } else if (lemma.equalsIgnoreCase("possible") || lemma.equalsIgnoreCase("permit")) {
            Node arg1 = root.getChild(":arg1");
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
        root.setVerb("id:" + instance.var.substring(0, instance.var.length() - 3) + "." + instance.var.substring(instance.var.length() - 2));
        args(root);
        return root;
    }

    private Node instanceElaboration(Node root) {
        //Interpretazione verbi ed eliminazione instance (per essere un verbo deve terminare con -xx)
        Node instance = root.getInstance();

        if (root.getStatus() == OK && root.relation.startsWith(Glossary.BEGIN_OF_AMR_RELATION) && !root.relation.equalsIgnoreCase(TOP)) {
            root.setStatus(Glossary.nodeStatus.AMR);
            return root;
        }

        if (instance == null) {
            return root;
        }

        if (instance.var.length() > 3 && instance.var.substring(instance.var.length() - 3).matches("-[0-9]+")) {
            root.setType(VERB);

            topic = false;
            root = modality(root);
            if (root.getStatus() == OK) {
                return root;
            }
            //Elaborazione della instance e trasferimento verbo nella root, seguito dal numero di occorrenza
            root.var = FRED + instance.var.substring(0, instance.var.length() - 3) + "_" + occurrence(instance.var.substring(0, instance.var.length() - 3));
            instance.relation = Glossary.RDF_TYPE;
            root.setVerb("id:" + instance.var.substring(0, instance.var.length() - 3) + "." + instance.var.substring(instance.var.length() - 2));
            args(root);
            //System.out.println(root.getVerb());
            instance.var = FRED + instance.var.substring(0, 1).toUpperCase() + instance.var.substring(1, instance.var.length() - 3).toLowerCase();
            if (root.list.size() == 1 && root.relation.equalsIgnoreCase(TOP)) {
                root.list.add(new Node(Glossary.TOPIC, Glossary.DUL_HAS_QUALITY, OK));
            } else {
                instance.list.add(new Node(Glossary.DUL_EVENT, Glossary.RDFS_SUBCLASS_OF, OK));
            }

            if (!instance.relation.startsWith(Glossary.BEGIN_OF_AMR_RELATION)) {
                instance.setStatus(OK);
            } else {
                instance.setStatus(AMR);
            }
            if (!root.relation.startsWith(Glossary.BEGIN_OF_AMR_RELATION)) {
                root.setStatus(OK);
            } else {
                root.setStatus(AMR);
            }

        } else {

            //Controllo ed elaborazione instance sui nodi "uguali" per le altre cose
            String nVar = FRED + instance.var + "_" + occurrence(instance.var);
            for (Node n : getEquals(root)) {

                Node instanceInList = n.getInstance();
                if (instanceInList != null) {
                    n.var = nVar;
                    instanceInList.relation = Glossary.RDF_TYPE;
                    instanceInList.var = FRED + firstUpper(instance.var);
                    if (!instanceInList.relation.startsWith(Glossary.BEGIN_OF_AMR_RELATION)) {
                        instanceInList.setStatus(OK);
                    }

                } else {
                    n.var = nVar;
                }
            }
            if (!root.relation.startsWith(Glossary.BEGIN_OF_AMR_RELATION)) {
                root.setStatus(OK);
            } else {
                root.setStatus(AMR);
            }
        }

        return root;
    }

    /**
     * Elaborazione della lista dei nodi collegati al nodo radice
     *
     * @param Node root
     * @return Node root - Il nodo radice la cui lista è stata elaborata
     */
    private Node listElaboration(Node root) {
        //Elaborazione della lista

        for (Iterator<Node> it = root.list.iterator(); it.hasNext();) {
            Node n = it.next();

            if (root.getInstance() != null) {
                System.out.println(root.getInstance().var);
            }
            if (n.relation.equalsIgnoreCase(Glossary.AMR_POLARITY)) {
                n.relation = Glossary.BOXING_HAS_THRUTH_VALUE;
                n.var = Glossary.BOXING_FALSE;
                n.setStatus(OK);
            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_MODE) && n.var.equalsIgnoreCase("imperative")) {
                //n.var = Glossary.TOPIC;
                //n.relation = Glossary.DUL_HAS_QUALITY;
                n.setStatus(REMOVE); // era ok
            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_MODE) && n.var.equalsIgnoreCase("expressive")) {
                n.var = Glossary.TOPIC;
                n.relation = Glossary.RDF_TYPE;
                n.setStatus(OK);
                root.setStatus(OK);
            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_MODE) && n.var.equalsIgnoreCase("interrogative")) {
                n.setStatus(REMOVE);
            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_PREP_AGAINST)) {
                n.relation = FRED + "against";
                n.setStatus(OK);
            } else if (n.getInstance() != null && n.getInstance().var.equalsIgnoreCase("amr-unknown")) {
                n.setStatus(REMOVE);
            } else if (Glossary.PERSON.contains(" " + n.var + " ")) {
                n.var = "person";
                this.setEquals(root);
            } else if (Glossary.MALE.contains(" " + n.var + " ")) {
                n.var = "male";
                this.setEquals(root);
            } else if (Glossary.FEMALE.contains(" " + n.var + " ")) {
                n.var = "female";
                this.setEquals(root);
            } else if (n.getInstance() != null && Glossary.THING.contains(" " + n.getInstance().var + " ")) {
                n.var = FRED + "neuter_" + occurrence("neuter");
                n.getInstance().var = Glossary.OWL_THING;
                n.getInstance().setStatus(OK);
                n.getInstance().relation = Glossary.RDF_TYPE;
                this.setEquals(root);
                n.setStatus(OK);
            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_POSS)) {
                n.relation = FRED + root.var.replaceAll(FRED, "") + "Of";
            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_MOD) && n.getInstance() != null) {
                if (Glossary.DEMONSTRATIVES.contains(" " + n.getInstance().var + " ")) {
                    n.relation = Glossary.QUANT_HAS_DETERMINER;
                }
                /*else {
                    n.relation = Glossary.RDF_TYPE;
                }*/
                n.var = FRED + firstUpper(n.getInstance().var);
                n.list.remove(n.getInstance());
                if (topic && root.relation.equalsIgnoreCase(TOP)) {
                    toAdd.add(new Node(Glossary.TOPIC, Glossary.DUL_HAS_QUALITY, OK));
                }
                n.setStatus(OK);
            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_DOMAIN) && n.getInstance() != null) {
                n.relation = Glossary.RDF_TYPE;
                n.var = FRED + firstUpper(n.getInstance().var);
                n.list.remove(n.getInstance());
                n.setStatus(OK);
            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_QUANT) && n.getInstance() == null) {
                n.relation = Glossary.DUL_HAS_DATA_VALUE;
                toAdd.add(new Node(convert(Integer.parseInt(n.var)), Glossary.QUANT_HAS_QUANTIFIER, OK));
                n.setStatus(OK);
            } else if (n.relation.equalsIgnoreCase(Glossary.AMR_MOD) && n.getInstance() != null) {
                //TODO
            }
            /*else if(n.relation.equalsIgnoreCase(":poss")){
            n.relation="boxing:hasThruthValue";
            n.var="boxing:false";
            }*/
            if (n.getStatus() != REMOVE) {
                n = predicate(n);
            } else {
                this.removed.add(n);
                it.remove();
            }
            if (n.relation.startsWith(Glossary.BEGIN_OF_AMR_RELATION)) {
                n.setStatus(AMR);
            }
        }
        for (Iterator<Node> it = root.list.iterator(); it.hasNext();) {
            Node n = it.next();
            if (n.getStatus() == REMOVE) {
                this.removed.add(n);
                it.remove();
            }
        }
        root.list.addAll(toAdd);
        toAdd.clear();
        return root;
    }

    private Node findVnClass(Node root) {
        if (root == null) {
            return null;
        }

        String lemma = root.getVerb();

        if (root.getType() == VERB) {

            PredMatrix pred = PredMatrix.getPredMatrix();
            ArrayList<Line> result = pred.find(lemma, Glossary.lineFields.ID_PRED);
            if (result != null && !result.isEmpty()) {
                String vnClass = result.get(0).getLine().get(Glossary.lineFields.VN_CLASS_NUMBER.ordinal()).substring(3);
                String vnSubClass = pred.find(lemma, Glossary.lineFields.ID_PRED).get(0).getLine().get(Glossary.lineFields.VN_SUBCLASS_NUMBER.ordinal()).substring(3);
                Node n = root.getChild(Glossary.RDF_TYPE);

                if (vnSubClass.equalsIgnoreCase("null") && !vnClass.equalsIgnoreCase("null")) {
                    n.list.add(new Node(Glossary.VN_DATA + (n.var.replaceAll(FRED, "")) + "_" + vnClass, "owl:equivalentClass", OK));
                } else if (!vnSubClass.equalsIgnoreCase("null")) {
                    n.list.add(new Node(Glossary.VN_DATA + (n.var.replaceAll(FRED, "")) + "_" + vnSubClass, "owl:equivalentClass", OK));
                }
            }

            //System.out.println(vnClass);
        }

        if (root.relation.matches(Glossary.ARG)) {
            PredMatrix pred = PredMatrix.getPredMatrix();
            //System.out.println(root.relation.substring(4));
            //System.out.println(lemma);
            String r = "pb:" + root.relation.substring(4);
            String role;
            String frame;
            ArrayList<Line> result = pred.find(lemma, Glossary.lineFields.ID_PRED, Glossary.lineFields.PB_ARG, r);
            if (result != null && !result.isEmpty()) {
                role = result.get(0).getLine().get(Glossary.lineFields.VN_ROLE.ordinal()).substring(3);
                frame = result.get(0).getLine().get(Glossary.lineFields.FN_FRAME_ELEMENT.ordinal()).substring(3);
                if ((role.equalsIgnoreCase(PIVOT) || (role.equalsIgnoreCase(NULL))) && !frame.equalsIgnoreCase(NULL)) {
                    root.relation = VN_ROLE + frame;
                    //System.out.println(frame);
                    root.setStatus(OK);
                } else if (!role.equalsIgnoreCase(NULL)) {
                    root.relation = VN_ROLE + role;
                    root.setStatus(OK);
                    //System.out.println(role);
                }
            }

        }

        for (Node n : root.list) {
            n = findVnClass(n);
        }
        return root;
    }

    private int occurrence(String word) {
        int occorrenceNum = 1;
        for (Couple c : this.couples) {
            if (word.equalsIgnoreCase(c.getWord())) {
                occorrenceNum++;
                c.setOccurence(occorrenceNum);
            }
        }
        if (occorrenceNum == 1) {
            this.couples.add(new Couple(1, word));
        }
        return occorrenceNum;
    }

    private ArrayList<Node> getEquals(Node node) {
        ArrayList<Node> equalsList = new ArrayList<>();
        for (Node n : this.nodes) {
            if (n.equals(node)) {
                equalsList.add(n);
            }
        }

        return equalsList;
    }

    private void setEquals(Node root) {
        for (Node n : getEquals(root)) {
            n.var = root.var;
        }
    }

    private String firstUpper(String string) {
        string = string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
        return string;
    }

    private Node argOf(Node root) {
        if (root == null || root.list.isEmpty() || root.getArgOf() == null) {

            return root;
        }

        Node n = root.getArgOf();
        //System.out.println(n +" ok");
        root.list.remove(n);
        String arg = n.relation;
        n.relation = root.relation;
        root.relation = arg.substring(0, arg.length() - 3);
        n.list.add(root);
        return argOf(n);
    }

    private void args(Node root) {
        for (Node n : root.list) {
            if (n.relation.matches(ARG) && n.getType() != VERB) {
                n.setVerb(root.getVerb());
            }
        }
    }

    private Node mod(Node root) {
        if (root == null || root.list.isEmpty() || root.getChild(AMR_MOD) == null) {

            return root;
        }

        Node n = root.getChild(AMR_MOD);
        root.list.remove(n);
        String arg = n.relation;
        n.relation = root.relation;
        root.relation = arg.substring(0, arg.length() - 3);
        n.list.add(root);
        return mod(n);
    }

}
