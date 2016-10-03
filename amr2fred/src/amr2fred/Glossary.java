/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amr2fred;

/**
 *
 * @author anto
 */
public class Glossary {

    public static final String TOP = "top";
    public static final String INSTANCE = "instance";
    public static final String FRED = "fred:";
    public static final String DUL_EVENT = "dul:Event";
    public static final String DUL_HAS_QUALITY = "dul:hasQuality";
    
    public static final String BOXING_NECESSARY = "boxing:Necessary";
    public static final String BOXING_POSSIBLE = "boxing:Possible";
    public static final String BOXING_HAS_MODALITY = "boxing:hasModality";
    
    public static final String BOXING_HAS_THRUTH_VALUE = "boxing:hasThruthValue";
    
    public static final String RDF_TYPE = "rdf:type";
    public static final String RDFS_SUBCLASS_OF = "rdfs:subClassOf";
      
    public static final String PB_ROLESET = "pb-roleset";   
    public static final String VN_CLASS="vn-class";
    public static final String PB_ARG = "pb-arg";
    
    public static final String TOPIC = "Topic";
    //public static final String
    //public static final String

    public enum wordType {
        OTHER, VERB, PERSON
    }
    
    public enum nodeStatus{
        OK, AMR, ERROR
    }

}
