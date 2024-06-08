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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public static String FRED_NS = "http://www.ontologydesignpatterns.org/ont/fred/domain.owl#";
    public static final String DEFAULT_FRED_NS = "http://www.ontologydesignpatterns.org/ont/fred/domain.owl#";

    public static final String FRED_TOPIC = "fred:Topic";
    public static final String FRED_ABOUT = "fred:about";

    /**
     * Local name for dul:
     */
    public static final String DUL = "dul:";

    /**
     * Local name for d0:
     */
    public static final String D0 = "d0:";

    /**
     * Name space for dul and d0
     */
    public static final String DUL_NS = "http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#";
    public static final String D0_NS = "http://www.ontologydesignpatterns.org/ont/d0.owl#";

    public static final String DUL_EVENT = DUL + "Event";
    public static final String DUL_HAS_QUALITY = DUL + "hasQuality";
    public static final String DUL_HAS_DATA_VALUE = DUL + "hasDataValue";
    public static final String DUL_ASSOCIATED_WITH = DUL + "associatedWith";
    public static final String DUL_HAS_MEMBER = DUL + "hasMember";
    public static final String DUL_HAS_PRECONDITION = DUL + "hasPrecondition";
    public static final String DUL_HAS_AMOUNT = DUL + "hasAmount";

    public static final String DUL_AGENT = DUL + "Agent";
    public static final String DUL_CONCEPT = DUL + "Concept";
    public static final String DUL_INFORMATION_ENTITY = DUL + "InformationEntity";
    public static final String DUL_ORGANISM = DUL + "Organism";
    public static final String DUL_ORGANIZATION = DUL + "Organization";
    public static final String DUL_PERSON = DUL + "Person";
    public static final String DUL_NATURAL_PERSON = DUL + "NaturalPerson";
    public static final String DUL_SUBSTANCE = DUL + "Substance";

    public static final String D0_LOCATION = D0 + "Location";
    public static final String D0_TOPIC = D0 + "Topic";

    public static String[] DULS = {DUL_AGENT, DUL_CONCEPT, DUL_INFORMATION_ENTITY, DUL_ORGANISM, DUL_ORGANIZATION, DUL_SUBSTANCE, D0_TOPIC, D0_LOCATION};
    public static String[] DULS_CHECK = {"agent", "concept", "informationentity", "organism", "organization", "substance", "topic", "location"};

    /**
     * Local name for boxer
     */
    public static final String BOXER = "boxer:";

    public static final String BOXER_AGENT = BOXER + "agent";
    public static final String BOXER_PATIENT = BOXER + "patient";
    public static final String BOXER_THEME = BOXER + "theme";

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
    public static final String BOXING_TRUTH = "boxing:Truth";
    public static final String BOXING_HAS_TRUTH_VALUE = "boxing:hasTruthValue";
    public static final String BOXING_UNKNOWN = "boxing:Unknown";

    /**
     * Local name for quant
     */
    public static final String QUANT = "quant:";

    public static final String QUANT_EVERY = QUANT + "every";

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
    public static final String RDFS_SUB_PROPERTY_OF = "rdfs:subPropertyOf";
    public static final String RDFS_LABEL = "rdfs:label";

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
    public static final String VN_ROLE_INSTRUMENT = VN_ROLE + "Instrument";
    public static final String VN_ROLE_CAUSE = VN_ROLE + "Cause";
    public static final String VN_ROLE_EXPERIENCER = VN_ROLE + "Expreriencer";
    public static final String VN_ROLE_THEME = VN_ROLE + "Theme";
    public static final String VN_ROLE_PREDICATE = VN_ROLE + "Predicate";

    public static final String REIFI_BENEFIT = "benefit-01";
    public static final String REIFI_HAVE_CONCESSION = "have-concession-91";
    public static final String REIFI_HAVE_CONDITION = "have-condition-91";
    public static final String REIFI_BE_DESTINED_FOR = "be-destined-for-91";
    public static final String REIFI_EXEMPLIFY = "exemplify-01";
    public static final String REIFI_HAVE_EXTENT = "have-extent-91";
    public static final String REIFI_HAVE_FREQUENCY = "have-frequency-91";
    public static final String REIFI_HAVE_INSTRUMENT = "have-instrument-91";
    public static final String REIFI_BE_LOCATED_AT = "be-located-at-91";
    public static final String REIFI_HAVE_MANNER = "have-manner-91";
    public static final String REIFI_HAVE_MOD = "have-mod-91";
    public static final String REIFI_HAVE_NAME = "have-name-91";
    public static final String REIFI_HAVE_PART = "have-part-91";
    public static final String REIFI_HAVE_POLARITY = "have-polarity-91";
    public static final String REIFI_HAVE_PURPOSE = "have-purpose-91";
    public static final String REIFI_HAVE_QUANT = "have-quant-91";
    public static final String REIFI_BE_FROM = "be-from-91";
    public static final String REIFI_HAVE_SUBEVENT = "have-subevent-91";
    public static final String REIFI_INCLUDE = "include-91";
    public static final String REIFI_BE_TEMPORALLY_AT = "be-temporally-at-91";
    public static final String REIFI_HAVE_DEGREE = "have-degree-91";
    public static final String REIFI_HAVE_LI = "have-li-91";
    public static final String RATE_ENTITY = "rate-entity-91";

    /**
     * Local name for vn.data
     */
    public static final String VN_DATA = "vn.data:";
    public static final String VN_DATA_NS = "http://www.ontologydesignpatterns.org/ont/vn/data/";

    public static final String NN_INTEGER_NS = "http://www.w3.org/2001/XMLSchema#decimal";
    public static final String NN_INTEGER = "^[0-9]+$";
    public static final String NN_INTEGER2 = "^[0-9]+[.]*[0-9]*$";
    public static final String NN_RATIONAL = "^[1-9][0-9]*/[1-9][0-9]*$";

    public static final String DATE_SCHEMA_NS = "http://www.w3.org/2001/XMLSchema#date";
    public static final String DATE_SCHEMA = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";

    public static final String TIME_SCHEMA2_NS = "https://www.w3.org/TR/xmlschema-2/#time";
    public static final String TIME_SCHEMA2 = "time:";
    public static final String TIME_SCHEMA = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

    public static final String STRING_SCHEMA_NS = "http://www.w3.org/2001/XMLSchema#string";

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
    public static final String AMR_VERB = "-[0-9]+$";
    public static final String AMR_VERB2 = ".*-[0-9]+$";
    public static final String AMR_ARG = ":arg.";
    public static final String AMR_INVERSE = ":+.+-of";
    public static final String AMR_OP = ":op[0-9]+";
    public static final String ALL = ".+";
    public static final String AMR_SENTENCE = ":snt[0-9]$";
    public static final String AMR_VAR = "^[a-zA-Z][a-zA-Z]*[0-9][0-9]*$";

    //Stringhe pattern AMR tradotti   
    public static final String AMR_POLARITY = ":polarity";
    public static final String AMR_POLARITY_OF = ":polarity-of";
    public static final String AMR_MINUS = "-";
    public static final String AMR_PLUS = "+";
    public static final String AMR_MODE = ":mode";
    public static final String AMR_POSS = ":poss";
    public static final String AMR_ARG0 = ":arg0";
    public static final String AMR_ARG1 = ":arg1";
    public static final String AMR_ARG2 = ":arg2";
    public static final String AMR_ARG3 = ":arg3";
    public static final String AMR_ARG4 = ":arg4";
    public static final String AMR_ARG5 = ":arg5";
    public static final String AMR_ARG6 = ":arg6";
    public static final String AMR_OP1 = ":op1";
    public static final String AMR_QUANT = ":quant";
    public static final String AMR_TOPIC = ":topic";
    public static final String AMR_UNKNOWN = "amr-unknown";
    public static final String AMR_MOD = ":mod";
    public static final String AMR_LOCATION = ":location";
    public static final String AMR_SOURCE = ":source";
    public static final String AMR_DESTINATION = ":destination";
    public static final String AMR_DIRECTION = ":direction";
    public static final String AMR_PATH = ":path";
    public static final String AMR_MANNER = ":manner";
    public static final String AMR_WIKI = ":wiki";
    public static final String AMR_NAME = ":name";
    public static final String AMR_PURPOSE = ":purpose";
    public static final String AMR_POLITE = ":polite";

    public static final String AMR_ACCOMPANIER = ":accompanier";
    public static final String AMR_AGE = ":age";
    public static final String AMR_BENEFICIARY = ":beneficiary";
    public static final String AMR_CAUSE = ":cause";
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
    public static final String AMR_LI = ":li";
    public static final String AMR_MEDIUM = ":medium";
    public static final String AMR_ORD = ":ord";
    public static final String AMR_PART = ":part";
    public static final String AMR_PART_OF = ":part-of";
    public static final String AMR_QUANT_OF = ":quant-of";
    public static final String AMR_RANGE = ":range";
    public static final String AMR_SCALE = ":scale";
    public static final String AMR_SUB_EVENT = ":subevent";
    public static final String AMR_SUB_EVENT_OF = ":subevent-of";
    public static final String AMR_SUBSET = ":subset";
    public static final String AMR_SUBSET_OF = ":subset-of";
    public static final String AMR_TIME = ":time";
    public static final String AMR_UNIT = ":unit";
    public static final String AMR_VALUE = ":value";

    public static final String AMR_PREP = ":prep-";
    public static final String AMR_PREP_AGAINST = ":prep-against";
    public static final String AMR_PREP_ALONG_WITH = ":prep-along-with";
    public static final String AMR_PREP_AMID = ":prep-amid";
    public static final String AMR_PREP_AMONG = ":prep-among";
    public static final String AMR_PREP_AS = ":prep-as";
    public static final String AMR_PREP_AT = ":prep-at";
    public static final String AMR_PREP_BY = ":prep-by";
    public static final String AMR_PREP_CONCERNING = ":prep-concerning";
    public static final String AMR_PREP_CONSIDERING = ":prep-considering";
    public static final String AMR_PREP_DESPITE = ":prep-despite";
    public static final String AMR_PREP_EXCEPT = ":prep-except";
    public static final String AMR_PREP_EXCLUDING = ":prep-excluding";
    public static final String AMR_PREP_FOLLOWING = ":prep-following";
    public static final String AMR_PREP_FOR = ":prep-for";
    public static final String AMR_PREP_FROM = ":prep-from";
    public static final String AMR_PREP_IN = ":prep-in";
    public static final String AMR_PREP_IN_ADDITION_TO = ":prep-in-addition-to";
    public static final String AMR_PREP_IN_SPITE_OF = ":prep-in-spite-of";
    public static final String AMR_PREP_INTO = ":prep-into";
    public static final String AMR_PREP_LIKE = ":prep-like";
    public static final String AMR_PREP_ON = ":prep-on";
    public static final String AMR_PREP_ON_BEHALF_OF = ":prep-on-behalf-of";
    public static final String AMR_PREP_OPPOSITE = ":prep-opposite";
    public static final String AMR_PREP_PER = ":prep-per";
    public static final String AMR_PREP_REGARDING = ":prep-regarding";
    public static final String AMR_PREP_SAVE = ":prep-save";
    public static final String AMR_PREP_SUCH_AS = ":prep-such-as";
    public static final String AMR_PREP_TROUGH = ":prep-through";
    public static final String AMR_PREP_TO = ":prep-to";
    public static final String AMR_PREP_TOWARD = ":prep-toward";
    public static final String AMR_PREP_UNDER = ":prep-under";
    public static final String AMR_PREP_UNLIKE = ":prep-unlike";
    public static final String AMR_PREP_VERSUS = ":prep-versus";
    public static final String AMR_PREP_WITH = ":prep-with";
    public static final String AMR_PREP_WITHIN = ":prep-within";
    public static final String AMR_PREP_WITHOUT = ":prep-without";
    public static final String AMR_CONJ_AS_IF = ":conj-as-if";

    public static final String AMR_ENTITY = "-entity";

    public static final String AMR_MULTI_SENTENCE = "multi-sentence";

    //Stringhe utilizzate durante la traduzione
    public static final String OF = "Of";
    public static final String BY = "By";
    public static final String CITY = "city";
    public static final String FRED_MALE = "male";
    public static final String FRED_FEMALE = "female";
    public static final String FRED_NEUTER = "neuter";
    public static final String FRED_PERSON = "person";
    public static final String FRED_MULTIPLE = "multiple";
    public static final String FRED_FOR = FRED + "for";
    public static final String FRED_WITH = FRED + "with";
    public static final String FRED_LIKE = FRED + "like";
    public static final String FRED_ALTHOUGH = FRED + "although";
    public static final String FRED_IN = FRED + "in";
    public static final String FRED_AT = FRED + "at";
    public static final String FRED_OF = FRED + "of";
    public static final String FRED_ON = FRED + "on";
    public static final String FRED_ENTAILS = FRED + "entails";
    public static final String FRED_EVEN = FRED + "Even";
    public static final String FRED_WHEN = FRED + "when";
    public static final String FRED_INCLUDE = FRED + "include";

    public static final String AMR_DOMAIN = ":domain";
    public static final String AMR_IMPERATIVE = "imperative";
    public static final String AMR_EXPRESSIVE = "expressive";
    public static final String AMR_INTERROGATIVE = "interrogative";
    public static final String AMR_RELATIVE_POSITION = "relative-position";

    //Stringhe usate per il riconoscimento dal parser
    public static final String PERSON = " I i you You YOU we We WE they They THEY ";
    public static final String MALE = " he He HE ";
    public static final String FEMALE = " she She SHE ";
    public static final String THING = " It it IT that those this these ";
    public static final String THING2 = " It it IT ";
    public static final String DEMONSTRATIVES = " that those this these ";
    public static final String AND = "and";
    public static final String OR = "or";
    public static final String IN = "in";

    //Stringhe usate per la gestione del file predmatrix.txt
    public static final String PIVOT = "Pivot";
    public static final String NULL = "Null";
    public static final String PB = "pb:";
    public static final String ID = "id:";

    //Stringhe usate per la generazione del grafico .dot
    public static final String DIGRAPH_INI = "digraph {\n charset=\"utf-8\"; \n";
    public static final String DIGRAPH_END = "}";

    //Nuovi prefissi e nuovi Spazi Nomi
    public static final String AMR_NS = "http://www.ontologydesignpatterns.org/ont/amr/";
    public static final String AMR = "amr:";

    public static final String AMRB_NS = "http://www.ontologydesignpatterns.org/ont/amrb/";
    public static final String AMRB = "amrb:";

    public static final String VA_NS = "http://verbatlas.org/";
    public static final String VA = "va:";

    public static final String BN_NS = "http://babelnet.org/rdf/";
    public static final String BN = "bn:";

    public static final String WN30_SCHEMA_NS = "https://w3id.org/framester/wn/wn30/schema/";
    public static final String WN30_SCHEMA = "wn30schema:";

    public static final String WN30_INSTANCES_NS = "https://w3id.org/framester/wn/wn30/instances/";
    public static final String WN30_INSTANCES = "wn30instances:";

    public static final String FS_SCHEMA_NS = "https://w3id.org/framester/schema/";
    public static final String FS_SCHEMA = "fschema:";

    public static final String PB_DATA_NS = "https://w3id.org/framester/pb/data/";
    public static final String PB_DATA = "pbdata:";
    
    public static final String PB_ROLESET_NS = "https://w3id.org/framester/data/propbank-3.4.0/RoleSet/";
    public static final String PB_ROLESET = "roleset:";

    public static final String PB_LOCALROLE_NS = "https://w3id.org/framester/data/propbank-3.4.0/LocalRole/";
    public static final String PB_LOCALROLE = "localrole:";
    
    public static final String PB_GENERICROLE_NS = "https://w3id.org/framester/data/propbank-3.4.0/GenericRole/";
    public static final String PB_GENERICROLE = "genericrole:";

    public static final String PB_SCHEMA_NS = "https://w3id.org/framester/schema/propbank/";
    public static final String PB_SCHEMA = "pbschema:";

    public static final String FN_FRAME_NS = "https://w3id.org/framester/framenet/abox/frame/";
    public static final String FN_FRAME = "fnframe:";

    public static final String FS_SCHEMA_SUBSUMED_UNDER = FS_SCHEMA + "subsumedUnder";

    public static final String AMR_WIKIDATA = ":wikidata";
    public static final String WIKIDATA = "wikidata:";
    public static final String WIKIDATA_NS = "http://www.wikidata.org/entity/";

    public static final String LITERAL = "literal:";
    public static final String LITERAL2 = "Literal:";
    public static final String LITERAL_NS = "";

    /**
     * Array of Fred elements local names
     */
    public static final String[] PREFIX = {FRED, DUL, BOXER, BOXING, QUANT, VN_ROLE,
        RDF, RDFS, OWL, VN_DATA, DBPEDIA, SCHEMA_ORG, AMR, VA, BN, WN30_SCHEMA,
        WN30_INSTANCES, FS_SCHEMA, PB_DATA, PB_ROLESET, PB_LOCALROLE, PB_GENERICROLE, 
        PB_SCHEMA, FN_FRAME, PB_LOCALROLE,
        WIKIDATA, D0, TIME_SCHEMA2, AMRB, LITERAL};

    /**
     * Array of fred elements name space
     */
    public static String[] NAMESPACE = {FRED_NS, DUL_NS, BOXER_NS, BOXING_NS,
        QUANT_NS, VN_ROLE_NS, RDF_NS, RDFS_NS, OWL_NS, VN_DATA_NS, DBPEDIA_NS,
        SCHEMA_ORG_NS, AMR_NS, VA_NS, BN_NS, WN30_SCHEMA_NS, WN30_INSTANCES_NS,
        FS_SCHEMA_NS, PB_DATA_NS, PB_ROLESET_NS, PB_LOCALROLE_NS, PB_GENERICROLE_NS, 
        PB_SCHEMA_NS, FN_FRAME_NS, PB_LOCALROLE_NS,
        WIKIDATA_NS, D0_NS, TIME_SCHEMA2_NS, AMRB_NS, LITERAL_NS};

    /**
     * Fred's element names number
     */
    public static final int PREFIX_NUM = 27;

    /**
     * Jena's writers output modes
     */
    public static final String RDF_MODE[] = {"RDF/XML", "RDF/XML-ABBREV", "N-TRIPLES", "TURTLE"};

    /**
     * Number of Jena's writers output modes
     */
    public static final int RDF_MODE_MAX = 4;

    public static final String AMR_RELATIONS[] = {AMR_MOD, AMR_POLARITY, AMR_TOPIC,
        AMR_LOCATION, AMR_SOURCE, AMR_DESTINATION, AMR_DIRECTION,
        AMR_PATH, AMR_MANNER, AMR_PURPOSE, AMR_ACCOMPANIER, AMR_BENEFICIARY,
        AMR_TIME, AMR_INSTRUMENT, AMR_DEGREE, AMR_DURATION, AMR_CAUSE, AMR_EXAMPLE,
        AMR_MEDIUM, AMR_CONCESSION, AMR_SUB_EVENT_OF, AMR_EXTENT, AMR_RANGE,
        AMR_SUBSET, AMR_SUBSET_OF, AMR_FREQUENCY, AMR_PART};

    public static final String AMR_VARS[] = {ALL, AMR_MINUS, ALL, ALL, ALL, ALL, ALL,
        ALL, ALL, ALL, ALL, ALL, ALL, ALL, ALL, ALL, ALL, ALL, ALL, ALL, ALL, ALL, ALL, ALL, ALL, ALL, ALL};

    public static final String FRED_RELATIONS[] = {DUL_HAS_QUALITY, BOXING_HAS_TRUTH_VALUE,
        FRED_ABOUT, VN_ROLE_LOCATION, VN_ROLE_SOURCE, VN_ROLE_DESTINATION, VN_ROLE_DESTINATION,
        VN_ROLE_LOCATION, DUL_HAS_QUALITY, VN_ROLE_PREDICATE, FRED_WITH, VN_ROLE_BENEFICIARY, VN_ROLE_TIME,
        VN_ROLE_INSTRUMENT, DUL_HAS_QUALITY, AMR + AMR_DURATION.substring(1), VN_ROLE_CAUSE, FRED_LIKE, AMR + AMR_MEDIUM.substring(1), FRED_ALTHOUGH,
        FRED_IN, DUL_HAS_QUALITY, FRED_IN, FRED_INCLUDE, FRED_OF, DUL_ASSOCIATED_WITH, FRED_WITH};

    public static final String FRED_VARS[] = {"", BOXING_FALSE, "", "", "", "", "", "", "", "",
        "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};

    public static final int PATTERNS_NUMBER = 27;

    public static final String QUOTE = "\"";

    public static final ArrayList<String> ADJECTIVE = readAdjectives();

    public static final String MANNER_ADVERBS[] = {"accidentally", "angrily", "anxiously",
        "awkwardly", "badly", "beautifully", "blindly", "boldly", "bravely", "brightly",
        "busily", "calmly", "carefully", "carelessly", "cautiously", "cheerfully",
        "clearly", "closely", "correctly", "courageously", "cruelly", "daringly",
        "deliberately", "doubtfully", "eagerly", "easily", "elegantly", "enormously",
        "enthusiastically", "equally", "eventually", "exactly", "faithfully", "fast",
        "fatally", "fiercely", "fondly", "foolishly", "fortunately", "frankly",
        "frantically", "generously", "gently", "gladly", "gracefully", "greedily",
        "happily", "hard", "hastily", "healthily", "honestly", "hungrily", "hurriedly",
        "inadequately", "ingeniously", "innocently", "inquisitively", "irritably",
        "joyously", "justly", "kindly", "lazily", "loosely", "loudly", "madly",
        "mortally", "mysteriously", "neatly", "nervously", "noisily", "obediently",
        "openly", "painfully", "patiently", "perfectly", "politely", "poorly",
        "powerfully", "promptly", "punctually", "quickly", "quietly", "rapidly",
        "rarely", "really", "recklessly", "regularly", "reluctantly", "repeatedly",
        "rightfully", "roughly", "rudely", "sadly", "safely", "selfishly", "sensibly",
        "seriously", "sharply", "shyly", "silently", "sleepily", "slowly", "smoothly",
        "so", "softly", "solemnly", "speedily", "stealthily", "sternly", "straight",
        "stupidly", "successfully", "suddenly", "suspiciously", "swiftly", "tenderly",
        "tensely", "thoughtfully", "tightly", "truthfully", "unexpectedly", "victoriously",
        "violently", "vivaciously", "warmly", "weakly", "wearily", "well", "wildly",
        "wisely"};

    public static final String PREPOSITION[] = {"à-la", "aboard", "about", "above",
        "according-to", "across", "after", "against", "ahead-of", "along", "along-with",
        "alongside", "amid", "amidst-", "among-", "amongst", "anti", "apart-from",
        "around-", "as", "as-for", "as-per", "as-to", "as-well-as", "aside-from",
        "astride", "at", "atop", "away-from", "bar", "barring", "because-of",
        "before", "behind", "below", "beneath", "beside", "besides", "between",
        "beyond", "but", "but-for", "by", "by-means-of", "circa", "close-to",
        "concerning", "considering", "contrary-to", "counting", "cum", "depending-on",
        "despite", "down", "due-to", "during", "except", "except-for", "excepting",
        "excluding", "following", "for", "forward-of", "from", "further-to", "given",
        "gone", "in", "in-addition-to", "in-between", "in-case-of", "in-the-face-of",
        "in-favor-of", "in-front-of", "in-lieu-of", "in-spite-of", "in-view-of",
        "including", "inside", "instead-of", "into", "irrespective-of", "less",
        "like", "minus", "near", "near-to", "next-to", "notwithstanding", "of",
        "off", "on", "on-account-of", "on-behalf-of", "on-board", "on-to", "on-top-of",
        "onto", "opposite", "opposite-to", "other-than", "out-of", "outside",
        "outside-of", "over", "owing-to", "past", "pending", "per", "preparatory-to",
        "prior-to", "plus", "pro", "re", "regarding", "regardless-of", "respecting",
        "round", "save", "save-for", "saving", "since", "than", "thanks-to", "through",
        "throughout", "till", "to", "together-with", "touching", "toward", "towards",
        "under", "underneath", "unlike", "until", "up", "up-against", "up-to",
        "up-until", "upon", "versus", "via", "vis-a-vis", "with", "with-reference-to",
        "with-regard-to", "within", "without", "worth", "exact"};

    public static final String CONJUNCTION[] = {"and", "or", "but", "nor", "so", "for",
        "yet", "after", "although", "as-", "as-if", "as-long", "as-because", "before-",
        "even-if-", "even-though", "once", "since", "so-that", "though", "till",
        "unless", "until", "what", "when", "whenever", "wherever", "whether", "while"};

    public static final String QUANTITY_TYPES[] = {"monetary-quantity", "distance-quantity",
        "area-quantity", "volume-quantity", "temporal-quantity", "frequency-quantity",
        "speed-quantity", "acceleration-quantity", "mass-quantity", "force-quantity",
        "pressure-quantity", "energy-quantity", "power-quantity", "voltage-quantity",
        "charge-quantity", "potential-quantity", "resistance-quantity", "inductance-quantity",
        "magnetic-field-quantity", "magnetic-flux-quantity", "radiation-quantity",
        "concentration-quantity", "temperature-quantity", "score-quantity",
        "fuel-consumption-quantity", "seismic-quantity"};

    /**
     * Special verb for roles in organizations
     */
    public static final String HAVE_ORG_ROLE = "have-org-role-91";

    /**
     * Special verb for relations between persons
     */
    public static final String HAVE_REL_ROLE = "have-rel-role-91";

    public static final String AMR_QUANTITY = ".+-quantity$";
    public static final String QUANTITY = "-quantity";
    public static final String SUM_OF = "sum-of";
    public static final String SUM = "sum";
    public static final String PRODUCT_OF = "product-of";
    public static final String PRODUCT = "product";
    public static final String EVEN_IF = "even-if";
    public static final String EVEN_WHEN = "even-when";

    public static final String AMR_DATE_ENTITY = "date-entity";
    public static final String AMR_DATE_CALENDAR = ":calendar";
    public static final String AMR_DATE_CENTURY = ":century";
    public static final String AMR_DATE_DAY = ":day";
    public static final String AMR_DATE_DAY_PERIOD = ":dayperiod";
    public static final String AMR_DATE_DECADE = ":decade";
    public static final String AMR_DATE_ERA = ":era";
    public static final String AMR_DATE_MONTH = ":month";
    public static final String AMR_DATE_QUARTER = ":quarter";
    public static final String AMR_DATE_SEASON = ":season";
    public static final String AMR_DATE_TIMEZONE = ":timezone";
    public static final String AMR_DATE_WEEKDAY = ":weekday";
    public static final String AMR_DATE_YEAR = ":year";
    public static final String AMR_DATE_YEAR2 = ":year2";
    public static final String AMR_DATE_INTERVAL = "date-interval";

    public static final String PREP_SUBSTITUTION = ":x->y";

    /**
     * Node types in AMR
     */
    public enum NodeType {
        NOUN, VERB, OTHER, AMR2FRED, FRED, COMMON
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
     * Field names of pb2vn table
     */
    public enum Pb2vnFields {

        PB_Role_URI,
        PB_RoleSet,
        PB_Role,
        VerbNet_Role_URI_for_FRED,
        VN_Role,
        VN_Sense,
        VerbNet_VerbSense_URI_for_FRED
    }

    /**
     * Field names of propbankframe table
     */
    public enum PropbankFrameFields {
        PB_Frame,
        PB_FrameLabel,
        PB_Role,
        FN_Frame,
        VA_Frame
    }

    /**
     * Field names of propbankrole table
     */
    public enum PropbankRoleFields {
        PB_Frame,
        PB_Role,
        PB_RoleLabel,
        PB_GenericRole,
        PB_Tr,
        PB_ARG,
        VA_Role
    }
    
    /**
     * Field names of propbankrole table
     */
    public enum old_PropbankRoleFields {
        PB_Role,
        PB_RoleLabel,
        PB_RoleSup,
        VA_Role
    }

    /**
     * Jena's writers output modes
     */
    public enum RdfWriteMode {
        RDF_XML, RDF_XML_ABBREV, N_TRIPLES, TURTLE
    }

    private Glossary() {
    }

    /**
     * read adjective file
     *
     * @return ArrayList
     */
    private static ArrayList<String> readAdjectives() {
        try {
            ArrayList<String> l = new ArrayList<>();
            String FILE4 = "allmostlyadjectives.tsv";
            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(Glossary.class.getResourceAsStream(FILE4)));

            String line = reader.readLine();
            while (line != null) {

                l.add(line);
                line = reader.readLine();
            }
            return l;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Glossary.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(Glossary.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static final String DISJUNCT = "disjunct";
    public static final String CONJUNCT = "conjunct";
    public static final String SPECIAL_INSTANCES[] = {DISJUNCT,CONJUNCT};
    public static final String SPECIAL_INSTANCES_PREFIX[] = {BOXING, BOXING};
    
    public static final String AMR_VALUE_INTERVAL = "value-interval";

    public static final String AMR_INSTANCES[] = {"thing", "person", "family",
        "animal", "language", "nationality", "ethnic-group", "regional-group", "religious-group", "political-movement",
        "organization", "company", "government-organization", "military", "criminal-organization", "political-party",
        "market-sector", "school", "university", "research-institute", "team", "league", "location", "city", "city-district",
        "county", "state", "province", "territory", "country", "local-region", "country-region", "world-region", "continent", 
        "ocean", "sea", "lake", "river", "gulf", "bay", "strait", "canal", "peninsula", "mountain", "volcano", "valley", "canyon", 
        "island", "desert", "forest", "moon", "planet", "star", "constellation", "facility", "airport", "station", "port", "tunnel", 
        "bridge", "road", "railway-line", "canal", "building", "theater", "museum", "palace", "hotel", "worship-place", "market", 
        "sports-facility", "park", "zoo", "amusement-park", "event", "incident", "natural-disaster", "earthquake", "war", 
        "conference", "game", "festival", "product", "vehicle", "ship", "aircraft", "aircraft-type", "spaceship", "car-make", 
        "work-of-art", "picture", "music", "show", "broadcast-program", "publication", "book", "newspaper", "magazine", 
        "journal", "natural-object", "award", "law", "court-decision", "treaty", "music-key", "musical-note", 
        "food-dish", "writing-script", "variable", "program", "molecular-physical-entity", "small-molecule", 
        "protein", "protein-family", "protein-segment", "amino-acid", "macro-molecular-complex", "enzyme", 
        "nucleic-acid", "pathway", "gene", "dna-sequence", "cell", "cell-line", "species", "taxon", "disease", "medical-condition"};
    public static final String AMR_ALWAYS_INSTANCES[] = {AMR_DATE_ENTITY, AMR_DATE_INTERVAL, "percentage-entity", "phone-number-entity",
        "email-address-entity", "url-entity", "score-entity", "string-entity", AMR_VALUE_INTERVAL};
    
    public static final String OP_JOINER = "_";
    public static final String OP_NAME = "name";
    
}
