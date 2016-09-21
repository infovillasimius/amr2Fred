/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amr2fred;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author anto
 */
public class Node {
    String relation;
    String var;
    ArrayList<Node> list;

    public Node(String var, String relation) {
        this.var = var;
        this.relation=relation;
        this.list=new ArrayList<>();
    }

    @Override
    public String toString() {
        return "\nNode{" + "relation=" + relation + ", var=" + var + ", list=" + list + '}';
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
        if (!Objects.equals(this.var, other.var)) {
            return false;
        }
        if (!Objects.equals(this.list, other.list)) {
            return false;
        }
        return true;
    }
    
    public Node getCopy(Node node, String relation){
        Node newNode=new Node(node.var, relation);
        newNode.list=node.list;
        return newNode;
    }
}
