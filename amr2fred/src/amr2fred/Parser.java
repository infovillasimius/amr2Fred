/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amr2fred;

import static amr2fred.Glossary.DUL_EVENT;
import static amr2fred.Glossary.FRED;
import static amr2fred.Glossary.INSTANCE;
import static amr2fred.Glossary.TOP;
import static amr2fred.Glossary.nodeStatus.OK;
import static amr2fred.Glossary.wordType.VERB;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.Element;

/**
 *
 * @author anto
 */
public class Parser {

    private static Parser parser;
    private ArrayList<Node> nodes;
    private ArrayList<Couple> couples;

    private Parser() {
        this.nodes = new ArrayList<>();
        this.couples = new ArrayList<>();

    }

    public static Parser getInstance() {
        if (parser == null) {
            parser = new Parser();
        }
        parser.nodes = new ArrayList<>();
        parser.couples = new ArrayList<>();
        return parser;
    }

    public Node parse(String amr) {
        ArrayList<String> amrList = string2Array(amr);
        Node root = getNodes(TOP, amrList);
        root = predicate(root);
        root= findVnClass(root);
        return root;
    }

    /**
     * Trasforma la stringa in input in un array di stringhe formato dalle parole che erano nella stringa originale
     * @param amr
     * @return 
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
        amr = amr.replaceAll ("\r\n|\r|\n", " ");
        amr = amr.replaceAll ("\t", " ");

        while (amr.contains("  ")) {
            amr = amr.replace("  ", " ");
        }

        return amr;
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

    public Node predicate(Node root) {
        if (root==null){
            return null;
        }
        if (root.list.isEmpty()) {
            return root;
        }

        Node instance = root.getInstance();

        if (instance == null) {
            return root;
        } else if (root.list.size()==1 && root.relation.equalsIgnoreCase(TOP)){
            root.list.add(new Node(Glossary.TOPIC, Glossary.DUL_HAS_QUALITY,OK));
            root.setStatus(OK);
        }
        
        //Interpretazione verbi ed eliminazione instance (per essere un verbo deve terminare con -xx
        if (instance.var.length() > 3) {
            String idVerb = instance.var.substring(instance.var.length() - 3);
            if (idVerb.matches("-[0-9]+")) {
                root.setType(VERB);
                String lemma = instance.var.substring(0, instance.var.length() - 3);
                //Elaborazione modality
                if (lemma.equalsIgnoreCase("likely") || lemma.equalsIgnoreCase("recommend")) {
                    Node arg1 = root.getChild(":arg1");
                    if (arg1 != null) {
                        root.var = arg1.var;
                        root.list.add(new Node(Glossary.BOXING_NECESSARY, Glossary.BOXING_HAS_MODALITY, OK));
                        root.list.addAll(arg1.list);
                        root.list.remove(arg1);
                        root.list.remove(instance);
                        root = predicate(root);

                    }
                } else if (lemma.equalsIgnoreCase("obligate")) {
                    Node arg2 = root.getChild(":arg2");
                    if (arg2 != null) {
                        root.var = arg2.var;
                        root.list.add(new Node(Glossary.BOXING_NECESSARY, Glossary.BOXING_HAS_MODALITY, OK));
                        root.list.addAll(arg2.list);
                        root.list.remove(arg2);
                        root.list.remove(instance);
                        root = predicate(root);
                    }
                } else if (lemma.equalsIgnoreCase("possible") || lemma.equalsIgnoreCase("permit")) {
                    Node arg1 = root.getChild(":arg1");
                    if (arg1 != null) {
                        root.var = arg1.var;
                        root.list.add(new Node(Glossary.BOXING_POSSIBLE, Glossary.BOXING_HAS_MODALITY, OK));
                        root.list.addAll(arg1.list);
                        root.list.remove(arg1);
                        root.list.remove(instance);
                        root = predicate(root);
                    }

                } else {
                    //Elaborazione della instance e trasferimento verbo nella root, seguito dal numero di occorrenza
                    root.var = instance.var.substring(0, instance.var.length() - 3) + "_" + occurrence(instance.var.substring(0, instance.var.length() - 3));
                    instance.relation = Glossary.RDF_TYPE;
                    instance.var = instance.var.substring(0, 1).toUpperCase() + instance.var.substring(1, instance.var.length() - 3).toLowerCase();
                    instance.list.add(new Node(Glossary.DUL_EVENT, Glossary.RDFS_SUBCLASS_OF, OK));
                    if (!instance.relation.matches(":arg.")) {
                        instance.setStatus(OK);
                    }

                    if (!root.relation.matches(":arg.")) {
                        root.setStatus(OK);
                    }
                }

            } else {

                //Controllo ed elaborazione sui nodi "uguali" per i verbi
                for (Node n : getEquals(root)) {

                    Node instanceInList = n.getInstance();
                    n.var = FRED + instanceInList.var + "_" + occurrence(instance.var);
                    instanceInList.relation = Glossary.RDF_TYPE;
                    instanceInList.var = FRED + instance.var.substring(0, 1).toUpperCase() + instance.var.substring(1).toLowerCase();
                    if (!instanceInList.relation.matches(":arg.")) {
                        instanceInList.setStatus(OK);
                    }
                }
            }
        } else {

            //Controllo ed elaborazione instance sui nodi "uguali" per le altre cose
            String nVar = FRED + instance.var + "_" + occurrence(instance.var);
            for (Node n : getEquals(root)) {

                Node instanceInList = n.getInstance();
                if (instanceInList != null) {
                    n.var = nVar;
                    instanceInList.relation = Glossary.RDF_TYPE;
                    instanceInList.var = FRED + instance.var.substring(0, 1).toUpperCase() + instance.var.substring(1).toLowerCase();
                    if (!instanceInList.relation.matches(":arg.")) {
                        instanceInList.setStatus(OK);
                    }

                } else {
                    n.var = nVar;
                    if (!root.relation.matches(":arg.")) {
                        root.setStatus(OK);
                    }
                }
            }
        }
        //Elaborazione della lista
        for (Node n : root.list) {

            if (n.relation.equalsIgnoreCase(":polarity")) {
                n.relation = Glossary.BOXING_HAS_THRUTH_VALUE;
                n.var = "boxing:false";
                n.setStatus(OK);
            } else if (n.relation.equalsIgnoreCase(":mode") && n.var.equalsIgnoreCase("imperative")) {
                n.var = "fred:Topic";
                n.relation = Glossary.DUL_HAS_QUALITY;
                n.setStatus(OK);
            } else if (n.relation.equalsIgnoreCase(":prep-against")) {
                n.relation = FRED + "against";
                n.setStatus(OK);

            }
            /*else if(n.relation.equalsIgnoreCase(":poss")){
                n.relation="boxing:hasThruthValue";
                n.var="boxing:false";
            }*/

            n = predicate(n);
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

    
    private Node findVnClass (Node root){
        if(root==null){
            return null;
        }
        if(root.getType()==VERB){
            String lemma=root.getChild(Glossary.RDF_TYPE).var;
            //System.out.println(lemma);
            
            
            
            Element predicate = Reader.jreader(lemma);
            List argmaps=predicate.getChildren();
            if (argmaps.size()==1){
                Element argmap=(Element) argmaps.get(0);
                System.out.println(lemma+" -> "+argmap.getAttributeValue(Glossary.VN_CLASS));
            } else {
                System.out.println(argmaps.size());
            }
            //Reader.getRole(lemma, predicate);
            
        }
        
        for(Node n:root.list){
            n=findVnClass(n);
        }
        return root;
    }

    
}
