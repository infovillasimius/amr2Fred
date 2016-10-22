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

/**
 * Classe che contiene le stringhe usate nell'applicazione. Non può essere
 * istanziata
 *
 * @author anto
 */
public class Glossary {

    /**
     * Value for recursion errors control
     */
    public static final int ENDLESS = 1000;

    /**
     * Message for recursive errors
     */
    public static final String RECURSIVE_ERROR = " recursive error! ";

    /**
     * Tree root relation
     */
    public static final String TOP = "top";

    public static final String INSTANCE = "instance";

    public static final String FRED = "fred:";
    public static final String FRED_NS = "http://www.ontologydesignpatterns.org/ont/fred/domain.owl#";

    public static final String DUL = "dul:";
    public static final String DUL_NS = "http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#";
    public static final String DUL_EVENT = "dul:Event";
    public static final String DUL_HAS_QUALITY = "dul:hasQuality";
    public static final String DUL_HAS_DATA_VALUE = "dul:hasDataValue";

    public static final String BOXER = "boxer:";
    public static final String BOXER_NS = "http://www.ontologydesignpatterns.org/ont/boxer/boxer.owl#";

    public static final String BOXING = "boxing:";
    public static final String BOXING_NS = "http://www.ontologydesignpatterns.org/ont/boxer/boxing.owl#";
    public static final String BOXING_NECESSARY = "boxing:Necessary";
    public static final String BOXING_POSSIBLE = "boxing:Possible";
    public static final String BOXING_HAS_MODALITY = "boxing:hasModality";
    public static final String BOXING_FALSE = "boxing:False";
    public static final String BOXING_HAS_THRUTH_VALUE = "boxing:hasThruthValue";

    public static final String QUANT = "quant:";
    public static final String QUANT_NS = "http://www.ontologydesignpatterns.org/ont/fred/quantifiers.owl#";
    public static final String QUANT_HAS_DETERMINER = "quant:hasDeterminer";
    public static final String QUANT_HAS_QUANTIFIER = "quant:hasQuantifier";

    public static final String OWL = "owl:";
    public static final String OWL_NS = org.apache.jena.vocabulary.OWL.getURI();
    public static final String OWL_THING = "owl:Thing";

    public static final String RDF = "rdf:";
    public static final String RDF_NS = org.apache.jena.vocabulary.RDF.getURI();

    public static final String RDF_TYPE = "rdf:type";

    public static final String RDFS = "rdfs:";
    public static final String RDFS_NS = org.apache.jena.vocabulary.RDFS.getURI();
    public static final String RDFS_SUBCLASS_OF = "rdfs:subClassOf";

    public static final String PB_ROLESET = "pb-roleset";
    public static final String VN_CLASS = "vn-class";
    public static final String PB_ARG = "pb-arg";

    public static final String TOPIC = "fred:Topic";

    public static final String ARG = ":arg.";
    public static final String ARG_OF = ":+.+-of";

    public static final String PERSON = " I i you You YOU we We WE they They THEY ";
    public static final String MALE = " he He HE ";
    public static final String FEMALE = " she She SHE ";
    public static final String THING = " It it IT ";
    public static final String DEMONSTRATIVES = " that those this these ";
    public static final String PIVOT = "Pivot";
    public static final String NULL = "Null";
    
    public static final String VN_ROLE = "vn.role:";
    public static final String VN_ROLE_NS = "http://www.ontologydesignpatterns.org/ont/vn/abox/role/";
    
    public static final String VN_DATA = "vn.data:";
    public static final String VN_DATA_NS = "http://www.ontologydesignpatterns.org/ont/vn/data/";

    public static final String BEGIN_OF_AMR_RELATION = ":";

    public static String[] prefix = {FRED, DUL, BOXER, BOXING, QUANT, VN_ROLE, 
        RDF, RDFS,OWL,VN_DATA};
    public static String[] namespace = {FRED_NS, DUL_NS, BOXER_NS, BOXING_NS, 
        QUANT_NS, VN_ROLE_NS, RDF_NS, RDFS_NS,OWL_NS,VN_DATA_NS};
    public static int PREFIX_NUM = 10;
    
    public static String[] rdfMode ={"RDF/XML","RDF/XML-ABBREV","N-TRIPLE","TURTLE","N3"};

    /**
     * Stringhe pattern AMR tradotti
     */
    public static final String AMR_MOD = ":mod";
    public static final String AMR_DOMAIN = ":domain";
    public static final String AMR_POLARITY = ":polarity";
    public static final String AMR_MODE = ":mode";
    public static final String AMR_POSS = ":poss";
    public static final String AMR_PREP_AGAINST = ":prep-against";
    public static final String AMR_QUANT = ":quant";

    /**
     * Stringhe pattern da fare
     */
    public static final String AMR_OP = ":op"; // con root.var=and e n.relation=AMR_OP
    public static final String AMR_LOCATION = ":location";

    public enum wordType {
        NOUN, VERB, PERSON, OTHER
    }

    public enum nodeStatus {
        OK, AMR, ERROR, REMOVE
    }

    public enum pronouns {
        I, YOU, HE, SHE, IT, WE, THEY
    }

    /**
     * Nomi dei campi della tabella predmatrix
     */
    public enum lineFields {

        ID_POS,
        ID_PRED,
        ID_ROLE,
        VN_CLASS,
        VN_CLASS_NUMBER,
        VN_SUBCLASS,
        VN_SUBCLASS_NUMBER,
        VN_LEMA,
        VN_ROLE,
        WN_SENSE,
        MCR_iliOffset,
        FN_FRAME,
        FN_LE,
        FN_FRAME_ELEMENT,
        PB_ROLESET,
        PB_ARG,
        MCR_BC,
        MCR_DOMAIN,
        MCR_SUMO,
        MCR_TO,
        MCR_LEXNAME,
        MCR_BLC,
        WN_SENSEFREC,
        WN_SYNSET_REL_NUM,
        ESO_CLASS,
        ESO_ROLE
    }

    private Glossary() {
    }

    public enum rdfWriteMode {
        RDF_XML,RDF_XML_ABBREV,N_TRIPLE,TURTLE,N3
    }
    
    

}
