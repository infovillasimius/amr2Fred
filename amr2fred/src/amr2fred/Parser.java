/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amr2fred;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author anto
 */
public class Parser {
    private static Parser parser;
    private ArrayList<Node> nodes;

    private Parser() {
        this.nodes=new ArrayList<>();
    }
    
    public static Parser getInstance(){
        if (parser==null){
            parser=new Parser();
        }
        return parser;
    }
    
    private Node getNodes(String relation,ArrayList<String> amrList){
        
        if(amrList==null){
            return null;
        }
        
        Node root=new Node(amrList.get(1),relation);
        this.nodes.add(root);
        String word,word2;
        int liv=0;
        int liv2=0;
        boolean flag;
        ArrayList<String> newList=null;
               
        for (int i=0 ; i<amrList.size(); i++){
            word=amrList.get(i);
            switch (word){
                case "(":
                    liv++;
                    if(liv==2){
                        liv2=0;
                        newList=new ArrayList<>();
                        for(int j=i;j<amrList.size();j++){
                            word2=amrList.get(j);
                            switch(word2){
                                case "(":
                                    liv2++;
                                    newList.add(word2);
                                    break;
                                case ")":
                                    liv2--;
                                    newList.add(word2);
                                    if(liv2==0){
                                        root.list.add(getNodes(amrList.get(i-1),newList));
                                        //System.out.println(root);
                                        i=j;
                                        j=amrList.size();
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
                    root.list.add(new Node(amrList.get(++i),"instance"));
                    break;
                default:
                    if(word.charAt(0)==':' && amrList.get(i+1).charAt(0)!='('){
                        flag=false;
                        for(Node find: this.nodes){
                            if(find.var.equalsIgnoreCase(amrList.get(i+1))){
                                root.list.add(find.getCopy(find, word));
                                flag=true;
                                break;
                            } 
                        }
                        
                        if (flag==false){
                            root.list.add(new Node(amrList.get(i+1),word));
                        }
                    }
            }  
        }
        
        if (liv!=0){   
            return null;
        } 
        
        
        return root;
    }

    
    private ArrayList<String> string2Array(String amr){
        int inizio,fine;
        String word;
        ArrayList<String> list=new ArrayList<>();
        
        amr=amr.replace("(", " ( ");
        amr=amr.replace(")", " ) ");
        
        while(amr.contains("  ")){
            amr=amr.replace("  ", " ");
        }
        try{
            while(amr.length()>1){
                inizio=amr.indexOf(" ")+1;
                fine=amr.indexOf(" ", inizio);
                word=amr.substring(inizio, fine);
                amr=amr.substring(fine);
                list.add(word);
            }
        } catch(java.lang.StringIndexOutOfBoundsException e){
            return null;
        }
        
        return list;
    }
    
    public Node parse(String amr){
        ArrayList<String> amrList=string2Array(amr);
        Node root=getNodes("top",amrList);
        return root;
    }
    
    
    
    
}
