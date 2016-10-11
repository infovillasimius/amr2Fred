/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amr2fred;

import static amr2fred.Glossary.ARG_OF;
import static amr2fred.Glossary.ENDLESS;
import static amr2fred.Glossary.RECURSIVE_ERROR;
import amr2fred.Glossary.nodeStatus;
import static amr2fred.Glossary.nodeStatus.AMR;
import static amr2fred.Glossary.nodeStatus.ERROR;
import amr2fred.Glossary.wordType;
import static amr2fred.Glossary.wordType.OTHER;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author anto
 */
public class Node {

    public static int liv = 0;
    String relation;
    String var;
    ArrayList<Node> list;
    private nodeStatus status;
    private wordType type;
    static int id;
    private int nodeId;

    public Node(String var, String relation) {
        this.var = var;
        this.relation = relation;
        this.list = new ArrayList<>();
        this.status = AMR;
        this.type = OTHER;
        this.nodeId = id;
        Node.id += 1;
    }

    public Node(String var, String relation, nodeStatus status) {
        this.relation = relation;
        this.var = var;
        this.status = status;
        this.list = new ArrayList<>();
        this.type = OTHER;
        this.nodeId = id;
        Node.id += 1;
    }

    @Override
    public String toString() {
        if (Parser.endless > ENDLESS) {
            return RECURSIVE_ERROR;
        }
        String stringa = "\n" + this.spaces(liv);
        if (relation != "top") {
            stringa = stringa + "{" + relation + " -> " + var + " -> ";

        } else {
            stringa = "{" + var + " -> ";
        }
        if (!list.isEmpty()) {
            Node.liv++;

            stringa = stringa + list + '}';
            Node.liv--;
        } else {
            stringa = stringa + list + '}';
        }
        if (status.ordinal() > 0) {
            stringa = "\n"+this.spaces(liv)+"<error"+liv+">" + stringa + "</error"+liv+">";
        }
        return stringa;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.var);
        hash = 79 * hash + Objects.hashCode(this.list);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (this.nodeId != other.nodeId) {
            return false;
        }

        return true;
    }

    public Node getCopy(Node node, String relation) {
        Node newNode = new Node(node.var, relation, node.status);
        newNode.list = node.list;
        newNode.nodeId = node.nodeId;
        return newNode;
    }

    public Node getCopy() {
        if (Parser.endless > ENDLESS) {
            return null;
        }
        Parser.endless += 1;
        Node newNode = new Node(var, relation, status);
        newNode.list = new ArrayList<>();
        for (Node n : list) {
            newNode.list.add(n.getCopy());
        }
        newNode.nodeId = nodeId;
        return newNode;
    }

    public Node getInstance() {
        for (Node n : this.list) {
            if (n.relation.equalsIgnoreCase("instance")) {
                return n;
            }
        }

        return null;
    }

    public Node getChild(String relation) {
        for (Node n : this.list) {
            if (n.relation.equalsIgnoreCase(relation)) {
                return n;
            }
        }
        return null;
    }
    
    public Node getArgOf (){
        for (Node n : this.list) {
            if (n.relation.matches(ARG_OF)){
                return n;
            }    
        }
        return null;
    }

    public String spaces(int n) {

        String spaces = "";
        for (int i = 0; i < n; i++) {
            spaces = spaces.concat("\t");
        }
        return spaces;
    }

    public nodeStatus getStatus() {
        return status;
    }

    public void setStatus(nodeStatus status) {
        this.status = status;
    }

    public int getTreStatus() {
        if (Parser.endless > ENDLESS) {
            return 1000000;
        }
        int somma = this.status.ordinal();
        for (Node n : list) {
            somma = somma + n.getTreStatus();
        }
        return somma;
    }

    public wordType getType() {
        return type;
    }

    public void setType(wordType type) {
        this.type = type;
    }

    public String toString2() {
        if (Parser.endless > ENDLESS) {
            return RECURSIVE_ERROR;
        }
        String stringa = "\n" + this.spaces(liv);
        if (relation != "top") {
            stringa = stringa + "{" + relation + " -> " + var + " -> ";

        } else {
            stringa = "{" + var + " -> ";
        }
        if (!list.isEmpty()) {
            Node.liv++;

            stringa = stringa + list2String(list) + '}';
            Node.liv--;
        } else {
            stringa = stringa + list2String(list) + '}';
        }

        return stringa;
    }

    private String list2String(ArrayList<Node> list) {
        if (list.isEmpty()) {
            return "";
        }
        String string = "";
        for (Node n : list) {
            string += n.toString2();
        }
        return string;
    }

}
