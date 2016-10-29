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
 * Glossary class for strings, arrays and enum used in amr2fred.
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

    /**
     * Instance nodes "relation" field
     */
    public static final String INSTANCE = "instance";

    /**
     * Local name for fred
     */
    public static final String FRED = "fred:";

    /**
     * Name space for fred
     */
    public static final String FRED_NS = "http://www.ontologydesignpatterns.org/ont/fred/domain.owl#";

    public static final String FRED_TOPIC = "fred:Topic";
    public static final String FRED_ABOUT = "fred:about";

    /**
     * Local name for dul:
     */
    public static final String DUL = "dul:";

    /**
     * Name space for dul
     */
    public static final String DUL_NS = "http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#";

    public static final String DUL_EVENT = "dul:Event";
    public static final String DUL_HAS_QUALITY = "dul:hasQuality";
    public static final String DUL_HAS_DATA_VALUE = "dul:hasDataValue";

    /**
     * Local name for boxer
     */
    public static final String BOXER = "boxer:";

    /**
     * Name space for boxer
     */
    public static final String BOXER_NS = "http://www.ontologydesignpatterns.org/ont/boxer/boxer.owl#";

    /**
     * Local name for boxing
     */
    public static final String BOXING = "boxing:";

    /**
     * Name space for boxing
     */
    public static final String BOXING_NS = "http://www.ontologydesignpatterns.org/ont/boxer/boxing.owl#";

    public static final String BOXING_NECESSARY = "boxing:Necessary";
    public static final String BOXING_POSSIBLE = "boxing:Possible";
    public static final String BOXING_HAS_MODALITY = "boxing:hasModality";
    public static final String BOXING_FALSE = "boxing:False";
    public static final String BOXING_HAS_THRUTH_VALUE = "boxing:hasThruthValue";

    /**
     * Local name for quant
     */
    public static final String QUANT = "quant:";

    /**
     * Name space for quant
     */
    public static final String QUANT_NS = "http://www.ontologydesignpatterns.org/ont/fred/quantifiers.owl#";

    public static final String QUANT_HAS_DETERMINER = "quant:hasDeterminer";
    public static final String QUANT_HAS_QUANTIFIER = "quant:hasQuantifier";

    /**
     * Local name for owl
     */
    public static final String OWL = "owl:";

    /**
     * Name space for owl
     */
    public static final String OWL_NS = org.apache.jena.vocabulary.OWL.getURI();
    public static final String OWL_THING = "owl:Thing";

    /**
     * Local name for rdf
     */
    public static final String RDF = "rdf:";

    /**
     * Name space for rdf
     */
    public static final String RDF_NS = org.apache.jena.vocabulary.RDF.getURI();

    public static final String RDF_TYPE = "rdf:type";

    /**
     * Local name for rdfs
     */
    public static final String RDFS = "rdfs:";

    /**
     * Name space for rdfs
     */
    public static final String RDFS_NS = org.apache.jena.vocabulary.RDFS.getURI();
    public static final String RDFS_SUBCLASS_OF = "rdfs:subClassOf";

    /**
     * Local name for vn.role
     */
    public static final String VN_ROLE = "vn.role:";
    public static final String VN_ROLE_NS = "http://www.ontologydesignpatterns.org/ont/vn/abox/role/vnrole.owl#";
    public static final String VN_ROLE_LOCATION = VN_ROLE + ":Location";

    /**
     * Local name for vn.data
     */
    public static final String VN_DATA = "vn.data:";
    public static final String VN_DATA_NS = "http://www.ontologydesignpatterns.org/ont/vn/data/";

    public static final String NN_INTEGER_NS = "http://www.w3.org/2001/XMLSchema#nonNegativeInteger";
    public static final String NN_INTEGER = "^[0-9]+$";

    /**
     * String for AMR elements identification
     */
    public static final String AMR_RELATION_BEGIN = ":";

    //Regex usate dal parser
    public static final String AMR_ARG = ":arg.";
    public static final String AMR_INVERSE = ":+.+-of";

    //Stringhe pattern AMR tradotti   
    public static final String AMR_POLARITY = ":polarity";
    public static final String AMR_MODE = ":mode";
    public static final String AMR_POSS = ":poss";
    public static final String AMR_PREP_AGAINST = ":prep-against";
    public static final String AMR_QUANT = ":quant";
    public static final String AMR_TOPIC = ":topic";

    //Stringhe pattern da fare
    public static final String AMR_OP = ":op"; // con root.var=and e n.relation=AMR_OP
    public static final String AMR_LOCATION = ":location";
    public static final String AMR_MOD = ":mod";
    public static final String AMR_DOMAIN = ":domain";

    //Stringhe usate per il riconoscimento dal parser
    public static final String PERSON = " I i you You YOU we We WE they They THEY ";
    public static final String MALE = " he He HE ";
    public static final String FEMALE = " she She SHE ";
    public static final String THING = " It it IT ";
    public static final String DEMONSTRATIVES = " that those this these ";

    //Stringhe usate per la gestione del file predmatrix.txt
    public static final String PB_ROLESET = "pb-roleset";
    public static final String VN_CLASS = "vn-class";
    public static final String PB_ARG = "pb-arg";
    public static final String PIVOT = "Pivot";
    public static final String NULL = "Null";

    /**
     * Array of Fred elements local names
     */
    public static final String[] PREFIX = {FRED, DUL, BOXER, BOXING, QUANT, VN_ROLE,
        RDF, RDFS, OWL, VN_DATA};

    /**
     * Array of fred elements name space
     */
    public static final String[] NAMESPACE = {FRED_NS, DUL_NS, BOXER_NS, BOXING_NS,
        QUANT_NS, VN_ROLE_NS, RDF_NS, RDFS_NS, OWL_NS, VN_DATA_NS};

    /**
     * Fred's element names number
     */
    public static final int PREFIX_NUM = 10;

    /**
     * Jena's writers output modes
     */
    public static final String[] RDF_MODE = {"RDF/XML", "RDF/XML-ABBREV", "N-TRIPLES", "TURTLE"};

    /**
     * Number of Jena's writers output modes
     */
    public static final int RDF_MODE_MAX = 4;

    public static final String AMR_RELATIONS[] = {AMR_MOD, AMR_DOMAIN, AMR_POLARITY, AMR_MODE, AMR_POSS,
        AMR_PREP_AGAINST, AMR_QUANT, AMR_TOPIC, AMR_LOCATION};

    public static final String AMR_VARS[] = {".", "", "-", "", "", ".", "", ".", "."};

    public static final String FRED_RELATIONS[] = {DUL_HAS_QUALITY, "", BOXING_HAS_THRUTH_VALUE, "", "",
        FRED + "against", "", FRED_ABOUT, VN_ROLE_LOCATION};

    public static final String FRED_VARS[] = {"", "", BOXING_FALSE, "", "", "", "", "", ""};

    public static final int PATTERNS_NUMBER = 9;

    /**
     * Node types in AMR
     */
    public enum NodeType {
        NOUN, VERB, OTHER
    }

    /**
     * Node status (used in parser)
     */
    public enum NodeStatus {
        OK, AMR, ERROR, REMOVE
    }

    /**
     * Field names of predmatrix table
     */
    public enum LineFields {

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

    /**
     * Jena's writers output modes
     */
    public enum RdfWriteMode {
        RDF_XML, RDF_XML_ABBREV, N_TRIPLES, TURTLE
    }

    private Glossary() {
    }

}
