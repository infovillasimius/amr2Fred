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
import static amr2fred.Glossary.NodeStatus.REMOVE;

/**
 * Basic object for the data structure.
 *
 * @author anto
 */
public class Node {

    //usato per dare ad ogni nodo un identificativo univoco
    static int id;

    //informazione usata per la gestione dell'indentazione nel metodo toString()
    static int liv = 0;

    //valore ramo
    String relation;
    
    //label relazione
    String label;

    //valore foglia
    String var;

    //nodi collegati
    ArrayList<Node> list;
    
    // Nodo genitore
    Node parent;
    
    //nodi da portare sulla lista del parent
    ArrayList<Node> parent_list;
    
    // visibilità in modalità grafica
    boolean visibility = true;
    
    // Special prefix for instances
    boolean prefix = false;

    //contiene lo stato di lavorazione del nodo - usato per verificare gli errori
    private NodeStatus status;

    //usato per distinguere la lavorazione da effettuare su ogni nodo 
    private NodeType type;

    //memorizza identificativo univoco del nodo
    private int nodeId;

    //memorizza la var originale nel caso sia un verbo, per l'uso con la predmatrix
    private String verb;
    
    private boolean malformed = false;

    public Node(String var, String relation) {
        this.var = var;
        this.relation = relation;
        this.list = new ArrayList<>();
        this.parent_list = new ArrayList<>();
        this.status = AMR;
        this.type = OTHER;
        this.nodeId = id;
        Node.id += 1;
    }
    
        public Node(String var, String relation, NodeStatus status, boolean visibility) {
        this.var = var;
        this.relation = relation;
        this.status = status;
        this.list = new ArrayList<>();
        this.parent_list = new ArrayList<>();
        this.type = OTHER;
        this.nodeId = id;
        this.visibility = visibility;
        Node.id += 1;
    }

    public Node(String var, String relation, NodeStatus status) {
        this.relation = relation;
        this.var = var;
        this.status = status;
        this.list = new ArrayList<>();
        this.parent_list = new ArrayList<>();
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

        if (!list.isEmpty()) {
            Node.liv++;

            stringa = stringa + list + '}';
            Node.liv--;
        } else {
            stringa = stringa + list + '}';
        }
        if (status.ordinal() > 0 && !relation.equalsIgnoreCase(Glossary.TOP)) {
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
        return this.nodeId == other.nodeId;
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

    public Node getCopy(ArrayList<Node> nodesCopy) {
        if (Parser.endless > ENDLESS) {
            return null;
        }
        Parser.endless += 1;
        Node newNode = new Node(var, relation, status);
        newNode.nodeId = nodeId;
        nodesCopy.add(newNode);
        newNode.list = new ArrayList<>();
        for (Node n : list) {
            newNode.list.add(n.getCopy(nodesCopy));
        }
        newNode.nodeId = nodeId;
        return newNode;
    }

    public Node getInstance() {
        for (Node n : this.list) {
            if (n.relation.equalsIgnoreCase(Glossary.INSTANCE)) {
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
            if (n.relation.matches(AMR_INVERSE) && !n.relation.equalsIgnoreCase(Glossary.AMR_PREP_ON_BEHALF_OF)
                    && !n.relation.equalsIgnoreCase(Glossary.AMR_CONSIST_OF)
                    && !n.relation.equalsIgnoreCase(Glossary.AMR_PART_OF)
                    && !n.relation.equalsIgnoreCase(Glossary.AMR_SUB_EVENT_OF)
                    && !n.relation.equalsIgnoreCase(Glossary.AMR_QUANT_OF)
                    && !n.relation.equalsIgnoreCase(Glossary.AMR_SUBSET_OF)) {
                return n;
            }
        }
        return null;
    }

    public ArrayList<Node> getInverses() {
        ArrayList<Node> nodes = new ArrayList<>();
        for (Node n : this.list) {
            if (n.relation.matches(AMR_INVERSE) && !n.relation.equalsIgnoreCase(Glossary.AMR_PREP_ON_BEHALF_OF)
                    && !n.relation.equalsIgnoreCase(Glossary.AMR_CONSIST_OF)
                    && !n.relation.equalsIgnoreCase(Glossary.AMR_PART_OF)
                    && !n.relation.equalsIgnoreCase(Glossary.AMR_SUB_EVENT_OF)
                    && !n.relation.equalsIgnoreCase(Glossary.AMR_SUBSET_OF)
                    && !n.relation.equalsIgnoreCase(Glossary.AMR_QUANT_OF)
                    && !n.relation.equalsIgnoreCase(Glossary.AMR_POLARITY_OF)
                    && n.status != REMOVE) {
                nodes.add(n);
            }
        }
        return nodes;
    }

    public ArrayList<Node> getInverses(ArrayList<Node> nodes) {
        for (Node n : this.list) {
            if (n.relation.matches(AMR_INVERSE) && !n.relation.equalsIgnoreCase(Glossary.AMR_PREP_ON_BEHALF_OF)
                    && !n.relation.equalsIgnoreCase(Glossary.AMR_CONSIST_OF)
                    && !n.relation.equalsIgnoreCase(Glossary.AMR_PART_OF)
                    && !n.relation.equalsIgnoreCase(Glossary.AMR_SUB_EVENT_OF)
                    && !n.relation.equalsIgnoreCase(Glossary.AMR_SUBSET_OF)
                    && !n.relation.equalsIgnoreCase(Glossary.AMR_QUANT_OF)
                    && !n.relation.equalsIgnoreCase(Glossary.AMR_POLARITY_OF)
                    && n.status != REMOVE
                    && nodes.indexOf(n) == -1) {
                nodes.add(n);
            }
            nodes = n.getInverses(nodes);
        }
        return nodes;
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

        /*if (this.type != Glossary.NodeType.VERB) {
        return argsList;
        }*/
        for (Node n : list) {
            if (n.relation.matches(Glossary.AMR_ARG)) {
                argsList.add(n);
            }
        }
        return argsList;
    }

    /**
     * Restituisce una lista con i sottonodi di tipo :op
     *
     * @return
     */
    public ArrayList<Node> getOps() {

        ArrayList<Node> argsList = new ArrayList<>();

        for (Node n : list) {
            if (n.relation.matches(Glossary.AMR_OP)) {
                argsList.add(n);
            }
        }
        return argsList;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void substitute(Node node) {
        this.var = node.var;
        this.relation = node.relation;
        this.nodeId = node.nodeId;
        this.list = new ArrayList<>();
        this.list.addAll(node.list);
        this.status = node.status;
        this.type = node.type;
        this.verb = node.verb;
    }

    public void swap(Node node) {
        String swapVar = node.var;
        //String swapRelation = node.relation;
        NodeStatus swapStatus = node.status;
        NodeType swapType = node.type;
        ArrayList<Node> swapList = node.list;
        int swapNodeId = node.nodeId;
        String swapVerb = node.verb;

        node.var = this.var;
        //node.relation = this.relation;
        node.status = this.status;
        node.type = this.type;
        node.list = this.list;
        node.nodeId = this.nodeId;
        node.verb = this.verb;

        this.var = swapVar;
        //this.relation = swapRelation;
        this.status = swapStatus;
        this.type = swapType;
        this.list = swapList;
        this.nodeId = swapNodeId;
        this.verb = swapVerb;
    }

    public Node getPoss() {
        for (Node n : list) {
            if (n.relation.matches(Glossary.AMR_POSS)) {
                return n;
            }
        }
        return null;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public ArrayList<Node> getList() {
        return list;
    }

    void makeEquals(Node n) {
        this.nodeId = n.nodeId;
    }

    void makeEquals(int n) {
        this.nodeId = n;
    }
    
    void add(Node n){
        this.list.add(n);
        n.parent = this;
    }
    
    void addAll(ArrayList<Node> nodes){
        for(Node n : nodes){
            this.list.add(n);
            n.parent = this;
        }
    }
    
    public ArrayList<Node> getChildren(String relation) {

        ArrayList<Node> argsList = new ArrayList<>();

        for (Node n : list) {
            if (n.relation.equalsIgnoreCase(relation)) {
                argsList.add(n);
            }
        }
        return argsList;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    ArrayList<Node> getSnt() {
        ArrayList<Node> snt = new ArrayList<>();
        for(Node n : this.list){
            if (n.relation.matches(Glossary.AMR_SENTENCE)){
                snt.add(n);
            }
        }
        for(Node n : this.list){
            snt.addAll(n.getSnt());
        }
        return snt;
    }
    
    ArrayList<Node> get_nodes_with_parent_list_not_empty() {
        ArrayList<Node> snt = new ArrayList<>();
        for(Node n : this.list){
            if (!n.parent_list.isEmpty()){
                snt.add(n);
            }
        }
        return snt;
    }

    public boolean isMalformed() {
        return malformed;
    }

    public void setMalformed(boolean malformed) {
        this.malformed = malformed;
    }
    
}
