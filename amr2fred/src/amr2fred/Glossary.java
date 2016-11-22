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
    public static final String OWL_THING = OWL + "Thing";
    public static final String OWL_EQUIVALENT_CLASS = OWL + "equivalentClass";
    public static final String OWL_SAME_AS = OWL + "sameAs";

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
    public static final String VN_ROLE_LOCATION = VN_ROLE + "Location";
    public static final String VN_ROLE_SOURCE = VN_ROLE + "Source";
    public static final String VN_ROLE_DESTINATION = VN_ROLE + "Destination";
    public static final String VN_ROLE_BENEFICIARY = VN_ROLE + "Beneficiary";
    public static final String VN_ROLE_TIME = VN_ROLE + "Time";

    /**
     * Local name for vn.data
     */
    public static final String VN_DATA = "vn.data:";
    public static final String VN_DATA_NS = "http://www.ontologydesignpatterns.org/ont/vn/data/";

    public static final String NN_INTEGER_NS = "http://www.w3.org/2001/XMLSchema#nonNegativeInteger";
    public static final String NN_INTEGER = "^[0-9]+$";

    public static final String DBR = "dbr:"; //anche "dbpedia:"
    public static final String DBR_NS = "http://dbpedia.org/resource/";

    public static final String DBO = "dbo:";
    public static final String DBO_NS = "http://dbpedia.org/ontology/";

    public static final String DBPEDIA = "dbpedia:";
    public static final String DBPEDIA_NS = "http://dbpedia.org/resource/";

    public static final String SCHEMA_ORG = "schemaorg:";
    public static final String SCHEMA_ORG_NS = "http://schema.org/";

    /**
     * String for AMR elements identification
     */
    public static final String AMR_RELATION_BEGIN = ":";

    //Regex usate dal parser
    public static final String AMR_ARG = ":arg.";
    public static final String AMR_INVERSE = ":+.+-of";
    public static final String AMR_OP = ":op[0-9]+";
    public static final String ALL = ".+";

    //Stringhe pattern AMR tradotti   
    public static final String AMR_POLARITY = ":polarity";
    public static final String AMR_MINUS = "-";
    public static final String AMR_MODE = ":mode";
    public static final String AMR_POSS = ":poss";
    public static final String AMR_PREP_AGAINST = ":prep-against";
    public static final String AMR_QUANT = ":quant";
    public static final String AMR_TOPIC = ":topic";
    public static final String AMR_UNKNOWN = "amr-unknown";
    public static final String AMR_MOD = ":mod";
    public static final String AMR_LOCATION = ":location";
    public static final String AMR_AND = "and";
    public static final String AMR_SOURCE = ":source";
    public static final String AMR_DESTINATION = ":destination";
    public static final String AMR_DIRECTION = ":direction";
    public static final String AMR_PATH = ":path";
    public static final String AMR_MANNER = ":manner";
    public static final String AMR_WIKI = ":wiki";
    public static final String AMR_NAME = ":name";
    public static final String AMR_PURPOSE = ":purpose";

    public static final String AMR_ACCOMPANIER = ":accompanier";
    public static final String AMR_AGE = ":age";
    public static final String AMR_BENEFICIARY = ":beneficiary";
    public static final String AMR_COMPARED_TO = ":compared-to";
    public static final String AMR_CONCESSION = ":concession";
    public static final String AMR_CONDITION = ":condition";
    public static final String AMR_CONSIST_OF = ":consist-of";
    public static final String AMR_DEGREE = ":degree";
    public static final String AMR_DURATION = ":duration";
    public static final String AMR_EXAMPLE = ":example";
    public static final String AMR_EXTENT = ":extent";
    public static final String AMR_FREQUENCY = ":frequency";
    public static final String AMR_INSTRUMENT = ":instrument";
    public static final String AMR_MEDIUM = ":medium";
    public static final String AMR_ORD = ":ord";
    public static final String AMR_PART = ":part";
    public static final String AMR_SCALE = ":scale";
    public static final String AMR_SUB_EVENT = ":subevent";
    public static final String AMR_TIME = ":time";
    public static final String AMR_UNIT = ":unit";
    public static final String AMR_VALUE = ":value";

    //Stringhe utilizzate durante la traduzione
    public static final String OF = "of";
    public static final String CITY = "city";
    public static final String FRED_MALE = "male";
    public static final String FRED_FEMALE = "female";
    public static final String FRED_NEUTER = "neuter";
    public static final String FRED_PERSON = "person";
    public static final String FRED_MULTIPLE = "multiple";

    //Stringhe pattern con qualcosa da fare    
    public static final String AMR_DOMAIN = ":domain";

    public static final String AMR_IMPERATIVE = "imperative";
    public static final String AMR_EXPRESSIVE = "expressive";
    public static final String AMR_INTERROGATIVE = "interrogative";

    //Stringhe usate per il riconoscimento dal parser
    public static final String PERSON = " I i you You YOU we We WE they They THEY ";
    public static final String MALE = " he He HE ";
    public static final String FEMALE = " she She SHE ";
    public static final String THING = " It it IT that those this these ";
    public static final String DEMONSTRATIVES = " that those this these ";

    //Stringhe usate per la gestione del file predmatrix.txt
    public static final String PIVOT = "Pivot";
    public static final String NULL = "Null";
    public static final String PB = "pb:";
    public static final String ID = "id:";

    /**
     * Array of Fred elements local names
     */
    public static final String[] PREFIX = {FRED, DUL, BOXER, BOXING, QUANT, VN_ROLE,
        RDF, RDFS, OWL, VN_DATA, DBPEDIA, SCHEMA_ORG};

    /**
     * Array of fred elements name space
     */
    public static final String[] NAMESPACE = {FRED_NS, DUL_NS, BOXER_NS, BOXING_NS,
        QUANT_NS, VN_ROLE_NS, RDF_NS, RDFS_NS, OWL_NS, VN_DATA_NS, DBPEDIA_NS, SCHEMA_ORG_NS};

    /**
     * Fred's element names number
     */
    public static final int PREFIX_NUM = 12;

    /**
     * Jena's writers output modes
     */
    public static final String RDF_MODE[] = {"RDF/XML", "RDF/XML-ABBREV", "N-TRIPLES", "TURTLE"};

    /**
     * Number of Jena's writers output modes
     */
    public static final int RDF_MODE_MAX = 4;

    public static final String AMR_RELATIONS[] = {AMR_MOD, AMR_DOMAIN, AMR_POLARITY, AMR_MODE, AMR_POSS,
        AMR_PREP_AGAINST, AMR_QUANT, AMR_TOPIC, AMR_LOCATION, AMR_SOURCE, AMR_DESTINATION, AMR_DIRECTION,
        AMR_PATH, AMR_MANNER, AMR_PURPOSE, AMR_ACCOMPANIER, AMR_BENEFICIARY, AMR_TIME};

    public static final String AMR_VARS[] = {ALL, "", AMR_MINUS, "", "", ALL, "", ALL, ALL, ALL, ALL, ALL,
        ALL, ALL, ALL, ALL, ALL, ALL};

    public static final String FRED_RELATIONS[] = {DUL_HAS_QUALITY, "", BOXING_HAS_THRUTH_VALUE, "", "",
        FRED + "against", "", FRED_ABOUT, VN_ROLE_LOCATION, VN_ROLE_SOURCE, VN_ROLE_DESTINATION, VN_ROLE_DESTINATION,
        VN_ROLE_LOCATION, DUL_HAS_QUALITY, VN_ROLE_BENEFICIARY, FRED + "with", VN_ROLE_BENEFICIARY, VN_ROLE_TIME};

    public static final String FRED_VARS[] = {"", "", BOXING_FALSE, "", "", "", "", "", "", "", "", "", "", "", "",
        "", "", ""};

    public static final int PATTERNS_NUMBER = 18;

    public static final String QUOTE = "\"";

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
