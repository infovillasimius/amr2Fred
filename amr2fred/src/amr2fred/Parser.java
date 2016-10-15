/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amr2fred;

import static amr2fred.Glossary.ENDLESS;
import static amr2fred.Glossary.FRED;
import static amr2fred.Glossary.TOP;
import static amr2fred.Glossary.nodeStatus.OK;
import static amr2fred.Glossary.nodeStatus.REMOVE;
import static amr2fred.Glossary.wordType.VERB;
import static amr2fred.NumberToWord.convert;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author anto
 */
public class Parser {

    private static Parser parser;
    private ArrayList<Node> nodes;
    private ArrayList<Couple> couples;
    private ArrayList<Node> removed;
    private ArrayList<Node> toAdd;
    private Node rootCopy;
    static int endless;
    private boolean topic = true;

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

    public Node parse(String amr) {
        ArrayList<String> amrList = string2Array(amr);
        Node root = getNodes(TOP, amrList);
        if (root != null) {
            endless = 0;
            this.rootCopy = root.getCopy();
            if (endless > ENDLESS) {
                this.rootCopy = new Node("Error", "Recursive");
            }
        }
        root = predicate(root);
        root= findVnClass(root);
        return root;
    }
    
    public Node check(Node root){
     
        for(Iterator<Node> it = root.list.iterator(); it.hasNext();){
            Node n=it.next();
            if (n.getStatus() != OK) {
                this.removed.add(n);
                it.remove();
            } else {
                n = check(n);
            }
        }
        
        if(root.getStatus()!=OK){
            System.out.println(root.relation+" "+root.var+" "+root.getTreStatus()+" "+root.getStatus());
            return null;
        }
        System.out.println(root);
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
                System.out.println(root.var);
                root.var = arg2.var;
                System.out.println(root);
                root.list.add(new Node(Glossary.BOXING_NECESSARY, Glossary.BOXING_HAS_MODALITY, OK));
                root.list.addAll(arg2.list);
                root.list.remove(arg2);
                root.list.remove(instance);
                root = modality(root);
                System.out.println(root);
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
        return root;
    }

    private Node instanceElaboration(Node root) {
        //Interpretazione verbi ed eliminazione instance (per essere un verbo deve terminare con -xx)
        Node instance = root.getInstance();

        if (root.getStatus() == OK && root.relation.matches(Glossary.ARG)) {
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
            instance.var = FRED + instance.var.substring(0, 1).toUpperCase() + instance.var.substring(1, instance.var.length() - 3).toLowerCase();
            if (root.list.size() == 1 && root.relation.equalsIgnoreCase(TOP)) {
                root.list.add(new Node(Glossary.TOPIC, Glossary.DUL_HAS_QUALITY, OK));
            } else {
                instance.list.add(new Node(Glossary.DUL_EVENT, Glossary.RDFS_SUBCLASS_OF, OK));
            }
            if (!instance.relation.matches(Glossary.ARG)) {
                instance.setStatus(OK);
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
                    if (!instanceInList.relation.matches(Glossary.ARG)) {
                        instanceInList.setStatus(OK);
                    }

                } else {
                    n.var = nVar;
                }
            }
        }
        if (!root.relation.matches(Glossary.ARG)) {
            root.setStatus(OK);
        }
        return root;
    }

    private Node listElaboration(Node root) {
        //Elaborazione della lista

        for (Iterator<Node> it = root.list.iterator(); it.hasNext();) {
            Node n = it.next();
            if (n.relation.equalsIgnoreCase(":polarity")) {
                n.relation = Glossary.BOXING_HAS_THRUTH_VALUE;
                n.var = Glossary.BOXING_FALSE;
                n.setStatus(OK);
            } else if (n.relation.equalsIgnoreCase(":mode") && n.var.equalsIgnoreCase("imperative")) {
                n.var = Glossary.TOPIC;
                n.relation = Glossary.DUL_HAS_QUALITY;
                n.setStatus(OK);
            } else if (n.relation.equalsIgnoreCase(":mode") && n.var.equalsIgnoreCase("expressive")) {
                n.var = Glossary.TOPIC;
                n.relation = Glossary.RDF_TYPE;
                n.setStatus(OK);
                root.setStatus(OK);
            } else if (n.relation.equalsIgnoreCase(":mode") && n.var.equalsIgnoreCase("interrogative")) {
                n.setStatus(REMOVE);
            } else if (n.relation.equalsIgnoreCase(":prep-against")) {
                n.relation = FRED + "against";
                n.setStatus(OK);
            } else if (n.getInstance() != null && n.getInstance().var.equalsIgnoreCase("amr-unknown")) {
                n.setStatus(REMOVE);
            } else if (n.getInstance() != null && Glossary.PERSON.contains(" " + n.getInstance().var + " ")) {
                n.getInstance().var = "person";
                this.setEquals(root);
            } else if (n.getInstance() != null && Glossary.MALE.contains(" " + n.getInstance().var + " ")) {
                n.getInstance().var = "male";
                this.setEquals(root);
            } else if (n.getInstance() != null && Glossary.FEMALE.contains(" " + n.getInstance().var + " ")) {
                n.getInstance().var = "female";
                this.setEquals(root);
            } else if (n.getInstance() != null && Glossary.THING.contains(" " + n.getInstance().var + " ")) {
                n.var = FRED + "neuter_" + occurrence("neuter");
                n.getInstance().var = Glossary.OWL_THING;
                n.getInstance().setStatus(OK);
                n.getInstance().relation = Glossary.RDF_TYPE;
                this.setEquals(root);
                n.setStatus(OK);
            } else if (n.relation.equalsIgnoreCase(":poss")) {
                n.relation = FRED + root.var.replaceAll(FRED, "") + "Of";
            } else if (n.relation.equalsIgnoreCase(":mod") && n.getInstance() != null) {
                if (Glossary.DEMONSTRATIVES.contains(" " + n.getInstance().var + " ")) {
                    n.relation = Glossary.QUANT_HAS_DETERMINER;
                } else {
                    n.relation = Glossary.RDF_TYPE;
                }
                n.var = FRED + firstUpper(n.getInstance().var);
                n.list.remove(n.getInstance());
                if (/*root.list.size() == 1*/topic && root.relation.equalsIgnoreCase(TOP)) {
                    toAdd.add(new Node(Glossary.TOPIC, Glossary.DUL_HAS_QUALITY, OK));
                }
                n.setStatus(OK);
            } else if (n.relation.equalsIgnoreCase(":domain") && n.getInstance() != null) {
                n.relation = Glossary.RDF_TYPE;
                n.var = FRED + firstUpper(n.getInstance().var);
                n.list.remove(n.getInstance());
                n.setStatus(OK);
            } else if (n.relation.equalsIgnoreCase(":quant") && n.getInstance() == null) {
                n.relation=Glossary.DUL_HAS_DATA_VALUE;
                toAdd.add(new Node(convert(Integer.parseInt(n.var)),Glossary.QUANT_HAS_QUANTIFIER,OK));
                n.setStatus(OK);
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
        if (root.getType() == VERB) {
            String lemma = root.getChild(Glossary.RDF_TYPE).var.toLowerCase();
            
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
    

}
