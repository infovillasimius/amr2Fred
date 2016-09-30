/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amr2fred;

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
    public static final String FRED="fred:";

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
        Node root = getNodes("top", amrList);
        return predicate(root);
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
                    root.list.add(new Node(amrList.get(++i), "instance"));
                    break;
                default:
                    if (word.charAt(0) == ':' && amrList.get(i + 1).charAt(0) != '(') {
                        flag = false;
                        for (Node find : this.nodes) {
                            if (find.var.equalsIgnoreCase(amrList.get(i + 1))) {
                                Node newNode=find.getCopy(find, word);
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

        if (root.list.isEmpty()) {
            return root;
        } 
        
        Node instance = root.getInstance();
        
        if(instance==null){
            return root;
        }
        
        if (instance.var.length() > 3){
            String idVerb = instance.var.substring(instance.var.length() - 3);
            if (idVerb.matches("-[0-9]+")) {
                String lemma = instance.var.substring(0, instance.var.length() - 3);
                root.var = instance.var.substring(0, instance.var.length() - 3) + "_" + occurrence(instance.var.substring(0, instance.var.length() - 3));
                instance.relation = "rdf:type";
                instance.var = instance.var.substring(0, 1).toUpperCase() + instance.var.substring(1, instance.var.length() - 3).toLowerCase();
                instance.list.add(new Node("dul:Event", "rdfs:subClassOf"));
            
            } else { 
                for(Node n:getEquals(root)){
                    
                    Node instanceInList = n.getInstance();
                    n.var = FRED+instanceInList.var + "_" + occurrence(instance.var);
                    instanceInList.relation = "rdf:type";
                    instanceInList.var = FRED+instance.var.substring(0, 1).toUpperCase() + instance.var.substring(1).toLowerCase();
                }
            }
        } else {
            String nVar=FRED+instance.var + "_" + occurrence(instance.var);
            for(Node n:getEquals(root)){
                
                Node instanceInList = n.getInstance();
                if (instanceInList!=null){
                    n.var = nVar;
                    instanceInList.relation = "rdf:type";
                    instanceInList.var = FRED+instance.var.substring(0, 1).toUpperCase() + instance.var.substring(1).toLowerCase();
                } else {
                    n.var = nVar;
                }
            }
        }
              
        for (Node n : root.list) {

            if (n.relation.equalsIgnoreCase(":polarity")) {
                n.relation = "boxing:hasThruthValue";
                n.var = "boxing:false";
            } else if (n.relation.equalsIgnoreCase(":mode") && n.var.equalsIgnoreCase("imperative")){
                n.var="fred:Topic";
                n.relation="dul:hasQuality";
            } else if (n.relation.equalsIgnoreCase(":prep-against")){
                n.relation=FRED+"against";
                
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
    
    private ArrayList<Node> getEquals(Node node){
        ArrayList<Node> equalsList = new ArrayList<>();
        for(Node n:this.nodes){
            if (n.equals(node)){
                equalsList.add(n);
            }
        }
        //System.out.println(equalsList);
        return equalsList;
    }

      
                //Element predicate = Reader.jreader(lemma);
                //Reader.getRole(lemma, predicate);
                // System.out.println(lemma+" -> "+predicate.getAttributeValue("vn-class"));
}
