/*
 * Copyright (C) 2017 anto
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
package resultsComparator;

import amr2fred.Glossary;
import static amr2fred.Glossary.NodeStatus.OK;
import amr2fred.Node;
import java.util.ArrayList;

/**
 *
 * @author anto
 */
public class Converter {

    public static void main(String[] args) {
        String test = "<http://www.ontologydesignpatterns.org/ont/fred/domain.owl#see_1> <http://www.ontologydesignpatterns.org/ont/vn/abox/role/vnrole.owl#Stimulus> <http://www.ontologydesignpatterns.org/ont/fred/domain.owl#girl_1> .\n"
                + "<http://www.ontologydesignpatterns.org/ont/fred/domain.owl#see_1> <http://www.ontologydesignpatterns.org/ont/vn/abox/role/vnrole.owl#Experiencer> <http://www.ontologydesignpatterns.org/ont/fred/domain.owl#boy_1> .\n"
                + "<http://www.ontologydesignpatterns.org/ont/fred/domain.owl#see_1> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.ontologydesignpatterns.org/ont/fred/domain.owl#See> .\n"
                + "<http://www.ontologydesignpatterns.org/ont/fred/domain.owl#Want> <http://www.w3.org/2002/07/owl#equivalentClass> <http://www.ontologydesignpatterns.org/ont/vn/data/Want_32010110> .\n"
                + "<http://www.ontologydesignpatterns.org/ont/fred/domain.owl#Want> <http://www.w3.org/2000/01/rdf-schema#subClassOf> <http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#Event> .\n"
                + "<http://www.ontologydesignpatterns.org/ont/fred/domain.owl#want_1> <http://www.ontologydesignpatterns.org/ont/vn/abox/role/vnrole.owl#Experiencer> <http://www.ontologydesignpatterns.org/ont/fred/domain.owl#girl_1> .\n"
                + "<http://www.ontologydesignpatterns.org/ont/fred/domain.owl#want_1> <http://www.ontologydesignpatterns.org/ont/vn/abox/role/vnrole.owl#Theme> <http://www.ontologydesignpatterns.org/ont/fred/domain.owl#boy_1> .\n"
                + "<http://www.ontologydesignpatterns.org/ont/fred/domain.owl#want_1> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.ontologydesignpatterns.org/ont/fred/domain.owl#Want> .\n"
                + "<http://www.ontologydesignpatterns.org/ont/fred/domain.owl#girl_1> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.ontologydesignpatterns.org/ont/fred/domain.owl#Girl> .\n"
                + "<http://www.ontologydesignpatterns.org/ont/fred/domain.owl#See> <http://www.w3.org/2002/07/owl#equivalentClass> <http://www.ontologydesignpatterns.org/ont/vn/data/See_30011000> .\n"
                + "<http://www.ontologydesignpatterns.org/ont/fred/domain.owl#See> <http://www.w3.org/2000/01/rdf-schema#subClassOf> <http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#Event> .\n"
                + "<http://www.ontologydesignpatterns.org/ont/fred/domain.owl#boy_1> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.ontologydesignpatterns.org/ont/fred/domain.owl#Boy> .";
       
        Node toNode = toNode(test);
        System.out.println(toNode);
    }

    private Converter() {
    }

    /**
     *  Convert the string containing the results into a list of triples
     * @param result a string with the result of a conversion from amr or from fred
     * @return a list of triple
     */
    public static ArrayList<Triple> getList(String result) {
        ArrayList<Triple> list = new ArrayList<>();
        String temp = "", temp1 = "", temp2 = "";
        int start, end;
        Triple triple;

        while (result.contains(" .")) {

            temp = result.substring(0, result.indexOf(" "));
            result = result.substring(result.indexOf(" ") + 1);

            temp1 = result.substring(0, result.indexOf(" "));
            result = result.substring(result.indexOf(" ") + 1);

            temp2 = result.substring(0, result.indexOf(" ."));
            result = result.substring(result.indexOf(" .") + 2);

            triple = new Triple(temp, temp1, temp2);

            list.add(triple);
        }
        return list;
    }

    private static ArrayList<String> tops(ArrayList<Triple> list) {
        ArrayList<String> top = new ArrayList<>();
        ArrayList<String> out = new ArrayList<>();

        for (Triple t : list) {
            String o = t.getObject();
            out.add(o);
        }

        for (Triple t : list) {
            String s = t.getSubject();

            if (!contains(top, s) && !contains(out, s)) {
                top.add(s);
            }

        }

        out = new ArrayList<>();
        out.addAll(top);
        top = new ArrayList<>();

        for (String s : out) {
            top.add(getInternalName(s));
        }

        //System.out.println(out);
        return top;
    }

    private static boolean contains(ArrayList<String> list, String s) {

        for (String s1 : list) {
            if (s1.equalsIgnoreCase(s) || s1.contains(s) || s.contains(s1)) {
                return true;
            }
        }

        return false;
    }

    private static String getInternalName(String s) {
        s = s.replaceAll("<", "");
        s = s.replaceAll(">", "");
        String pref;
        String name;
        int num=s.indexOf("\"");
        if (num>=0){
            pref="";
            name=s.substring(num+1, s.indexOf("\"", num+1));
            return name;
        }

        int dp = s.indexOf("#");
        if (dp < 0) {
            dp = s.lastIndexOf("/");
        }

        if (dp < 0) {
            pref = "";
            name = s;
        } else {
            pref = s.substring(0, dp + 1);
            name = s.substring(dp + 1);
        }

        for (int n = 0; n < amr2fred.Glossary.PREFIX_NUM; n++) {
            if (pref.equalsIgnoreCase(amr2fred.Glossary.NAMESPACE[n])
                    || pref.contains(amr2fred.Glossary.NAMESPACE[n])
                    || amr2fred.Glossary.NAMESPACE[n].contains(pref)) {
                return amr2fred.Glossary.PREFIX[n] + name;
            }
        }
        return amr2fred.Glossary.FRED + name;
    }

    private static ArrayList<Triple> internalList(ArrayList<Triple> list) {
        ArrayList<Triple> newList = new ArrayList<>();

        for (Triple t : list) {
            Triple newT = new Triple(getInternalName(t.getSubject()), getInternalName(t.getRelation()), getInternalName(t.getObject()));
            newList.add(newT);
        }

        return newList;
    }

    public static Node toNode(String rdf) {

        ArrayList<Triple> list = getList(rdf);
        Node root = toNode(list);
        return root;
    }

    public static Node toNode(ArrayList<Triple> list) {
        ArrayList<Node> nList = new ArrayList<>();
        ArrayList<Node> copiaNList = new ArrayList<>();
        ArrayList<String> l = tops(list);
        list = internalList(list);
        if(l.isEmpty() || list.isEmpty()){
            return null;
        }
        for (String s : l) {
            Node n = new Node(s, Glossary.TOP, Glossary.NodeStatus.OK);
            nList.add(n);
        }
        copiaNList.addAll(nList);
        Node root = nList.get(0);

        addNodes(list, nList);

        for (Node n : copiaNList) {
            if (!root.equals(n)) {
                root.getList().add(n);
            }
        }
        return root;
    }

    private static Node getNode(ArrayList<Node> list, String s) {
        for (Node n : list) {
            String str = n.getVar();
            if (str.equalsIgnoreCase(s) || str.contains(s) || s.contains(str)) {
                return n;
            }
        }
        return null;
    }

    private static void addNodes(ArrayList<Triple> list, ArrayList<Node> nList) {

        ArrayList<Triple> newList = new ArrayList<>();
        for (Triple t : list) {
            Node n = getNode(nList, t.getSubject());
            if (n != null) {
                Node newNode = new Node(t.getObject(), t.getRelation(), OK);
                nList.add(newNode);
                n.getList().add(newNode);
            } else {
                newList.add(t);
            }
        }

        if (!newList.isEmpty()) {
            addNodes(newList, nList);
        }

    }

}
