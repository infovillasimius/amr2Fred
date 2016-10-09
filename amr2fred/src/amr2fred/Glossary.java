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
    
    public static final int ENDLESS=1000;
    public static final String RECURSIVE_ERROR=" recursive error! ";
    public static final String TOP = "top";
    public static final String INSTANCE = "instance";
    public static final String FRED = "fred:";
    public static final String DUL_EVENT = "dul:Event";
    public static final String DUL_HAS_QUALITY = "dul:hasQuality";

    public static final String BOXING_NECESSARY = "boxing:Necessary";
    public static final String BOXING_POSSIBLE = "boxing:Possible";
    public static final String BOXING_HAS_MODALITY = "boxing:hasModality";
    public static final String BOXING_FALSE = "boxing:false";

    public static final String BOXING_HAS_THRUTH_VALUE = "boxing:hasThruthValue";
    
    public static final String QUANT_HAS_DETERMINER = "quant:hasDeterminer";
    
    public static final String OWL_THING="owl:Thing";

    public static final String RDF_TYPE = "rdf:type";
    public static final String RDFS_SUBCLASS_OF = "rdfs:subClassOf";

    public static final String PB_ROLESET = "pb-roleset";
    public static final String VN_CLASS = "vn-class";
    public static final String PB_ARG = "pb-arg";

    public static final String TOPIC = "fred:Topic";

    public static final String ARG = ":arg.";
    
    public static final String PERSON = " I i you You YOU we We WE they They THEY ";
    public static final String MALE = " he He HE ";
    public static final String FEMALE = " she She SHE ";
    public static final String THING = " It it IT ";
    public static final String DEMONSTRATIVES=" that those this these ";
    //public static final String

    public enum wordType {
        OTHER, VERB, PERSON
    }

    public enum nodeStatus {
        OK, AMR, ERROR, REMOVE
    }
    
    public enum pronouns {
        I, YOU, HE, SHE, IT, WE, THEY
    }

}
