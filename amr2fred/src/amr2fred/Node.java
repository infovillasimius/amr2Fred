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
    public static int liv=0;
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
        String stringa="\n"+this.spaces(liv);
        if(relation!="top"){
             stringa=stringa+"{" + relation + " -> " + var + " -> ";
             
        } else {
            stringa=stringa+"{" + var + " -> ";
        }
        if(!list.isEmpty()){Node.liv++;
            
            stringa=stringa+list + '}';
            Node.liv--;
        } else {
            stringa=stringa+list + '}';
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
    
    public Node getCopy(Node node, String relation){
        Node newNode=new Node(node.var, relation);
        newNode.list=node.list;
        return newNode;
    }
    
    public Node getInstance (){
        for(Node n: this.list){
            if(n.relation.equalsIgnoreCase("instance")){
                return n;
            }
        }
        
        return null;
    }
    
    public String spaces(int n){
        
        String spaces="";
        for (int i=0 ; i<n ; i++){
            spaces = spaces.concat("\t");
        }
        return spaces;
    }
    
    
}
