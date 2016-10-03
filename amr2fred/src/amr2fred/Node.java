/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amr2fred;

import amr2fred.Glossary.nodeStatus;
import static amr2fred.Glossary.nodeStatus.AMR;
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

    public Node(String var, String relation) {
        this.var = var;
        this.relation = relation;
        this.list = new ArrayList<>();
        this.status = AMR;
        this.type=OTHER;
    }

    public Node(String var, String relation, nodeStatus status) {
        this.relation = relation;
        this.var = var;
        this.status = status;
        this.list = new ArrayList<>();
        this.type=OTHER;
    }

    @Override
    public String toString() {
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
        if(status.ordinal()>0){
            stringa="\n*begin of incorrect node*"+stringa+"*end of incorrect node*\n";
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
        if (!this.var.toString().equalsIgnoreCase(other.var.toString())) {
            return false;
        }
        if (!Objects.equals(this.list, other.list)) {
            return false;
        }
        return true;
    }

    public Node getCopy(Node node, String relation) {
        Node newNode = new Node(node.var, relation);
        newNode.list = node.list;
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
        int somma = this.status.ordinal();
        for(Node n:list){
            somma=somma+n.getTreStatus();
        }
        return somma;
    }

    public wordType getType() {
        return type;
    }

    public void setType(wordType type) {
        this.type = type;
    }
    
    

}
