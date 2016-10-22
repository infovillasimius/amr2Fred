
package amr2fred;

import static amr2fred.Glossary.ARG_OF;
import static amr2fred.Glossary.ENDLESS;
import static amr2fred.Glossary.RECURSIVE_ERROR;
import amr2fred.Glossary.nodeStatus;
import static amr2fred.Glossary.nodeStatus.AMR;
import amr2fred.Glossary.wordType;
import static amr2fred.Glossary.wordType.OTHER;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Contiene le informazioni relative al ramo (relation) ed alla foglia (var)
 * dei nodi dell'albero amr
 * @author anto
 */
public class Node {

    public static int liv = 0;                  //informazione usata per la gestione dell'indentazione nel metodo toString()
    String relation;                           //valore ramo   
    String var;                                //valore foglia
    ArrayList<Node> list;                      //nodi collegati
    private nodeStatus status;                  //contiene lo stato di lavorazione del nodo - usato per verificare gli errori
    private wordType type;                     //usato per distinguere la lavorazione da effettuare su ogni nodo 
    static int id;                             //usato per dare ad ogni nodo un identificativo univoco
    private int nodeId;                       //memorizza identificativo univoco del nodo
    private String verb;                      //memorozza la var originale nel caso sia un verbo, per l'uso con la predmatrix

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
        if (!relation.equalsIgnoreCase(Glossary.TOP)) {
            stringa = stringa + "{" + relation + " -> " + var + " -> ";

        } else {
            stringa = "{" + var + " -> ";
        }
        //System.out.println(relation+" "+status);
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
            //System.out.println(n.relation+" "+n.relation.matches(ARG_OF));
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
    
    

}
