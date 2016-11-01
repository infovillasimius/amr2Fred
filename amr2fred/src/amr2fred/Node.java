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
import static amr2fred.Glossary.RECURSIVE_ERROR;
import amr2fred.Glossary.NodeStatus;
import static amr2fred.Glossary.NodeStatus.AMR;
import amr2fred.Glossary.NodeType;
import static amr2fred.Glossary.NodeType.OTHER;
import java.util.ArrayList;
import java.util.Objects;
import static amr2fred.Glossary.AMR_INVERSE;

/**
 * Contiene le informazioni relative al ramo (relation) ed alla foglia (var) dei
 * nodi dell'albero amr
 *
 * @author anto
 */
class Node {

    static int id;                             //usato per dare ad ogni nodo un identificativo univoco
    static int liv = 0;                         //informazione usata per la gestione dell'indentazione nel metodo toString()
    String relation;                           //valore ramo   
    String var;                                //valore foglia
    ArrayList<Node> list;                      //nodi collegati
    private NodeStatus status;                  //contiene lo stato di lavorazione del nodo - usato per verificare gli errori
    private NodeType type;                     //usato per distinguere la lavorazione da effettuare su ogni nodo 
    private int nodeId;                       //memorizza identificativo univoco del nodo
    private String verb;                      //memorizza la var originale nel caso sia un verbo, per l'uso con la predmatrix

    public Node(String var, String relation) {
        this.var = var;
        this.relation = relation;
        this.list = new ArrayList<>();
        this.status = AMR;
        this.type = OTHER;
        this.nodeId = id;
        Node.id += 1;
    }

    public Node(String var, String relation, NodeStatus status) {
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
        if (!relation.equalsIgnoreCase(Glossary.TOP)) {
            stringa = stringa + "{" + relation + " -> " + var + " -> ";

        } else {
            stringa = "{" + var + " -> ";
        }
        //System.out.println(relation+" "+var+nodeId);
        if (!list.isEmpty()) {
            Node.liv++;

            stringa = stringa + list + '}';
            Node.liv--;
        } else {
            stringa = stringa + list + '}';
        }
        if (status.ordinal() > 0) {
            stringa = "\n" + this.spaces(liv) + "<error" + liv + ">" + stringa + "</error" + liv + ">";
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

    public Node getCopy(String relation) {
        Node newNode = new Node(this.var, relation, this.status);
        newNode.list = new ArrayList<>();
        newNode.nodeId = this.nodeId;
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

    public Node getInverse() {
        for (Node n : this.list) {
            if (n.relation.matches(AMR_INVERSE)) {
                return n;
            }
        }
        return null;
    }

    private String spaces(int n) {

        String spaces = "";
        for (int i = 0; i < n; i++) {
            spaces = spaces.concat("\t");
        }
        return spaces;
    }

    public NodeStatus getStatus() {
        return status;
    }

    public void setStatus(NodeStatus status) {
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

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public String toString2() {
        if (Parser.endless > ENDLESS) {
            return RECURSIVE_ERROR;
        }
        String stringa = "\n" + this.spaces(liv);
        if (!relation.equalsIgnoreCase(Glossary.TOP)) {
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

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    /**
     * Restituisce una lista con i sottonodi di tipo :arg
     *
     * @return
     */
    public ArrayList<Node> getArgs() {

        ArrayList<Node> argsList = new ArrayList<>();

        if (this.type != Glossary.NodeType.VERB) {
            return argsList;
        }

        for (Node n : list) {
            if (n.relation.matches(Glossary.AMR_ARG)) {
                argsList.add(n);
            }
        }
        return argsList;
    }

}
