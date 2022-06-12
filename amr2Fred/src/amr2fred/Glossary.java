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

    public static final String DUL_EVENT = DUL + "Event";
    public static final String DUL_HAS_QUALITY = DUL + "hasQuality";
    public static final String DUL_HAS_DATA_VALUE = DUL + "hasDataValue";
    public static final String DUL_ASSOCIATED_WITH = DUL + "associatedWith";
    public static final String DUL_HAS_MEMBER = DUL + "hasMember";

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

    public static final String RATE_ENTITY = "rate-entity-91";

    /**
     * Local name for vn.data
     */
    public static final String VN_DATA = "vn.data:";
    public static final String VN_DATA_NS = "http://www.ontologydesignpatterns.org/ont/vn/data/";

    public static final String NN_INTEGER_NS = "http://www.w3.org/2001/XMLSchema#nonNegativeInteger";
    public static final String NN_INTEGER = "^[0-9]+$";
    public static final String NN_INTEGER2 = "^[0-9]+[.]*[0-9]*$";

    public static final String DATE_SCHEMA_NS = "http://www.w3.org/2001/XMLSchema#date";
    public static final String DATE_SCHEMA = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
    
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

    //Stringhe pattern AMR tradotti   
    public static final String AMR_POLARITY = ":polarity";
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
    public static final String AMR_NS = "https://www.isi.edu/~ulf/amr/lib/amr-dict.html#";
    public static final String AMR = "amr:";

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

    public static final String PB_SCHEMA_NS = "https://w3id.org/framester/pb/schema/";
    public static final String PB_SCHEMA = "pbschema:";

    public static final String FN_FRAME_NS = "https://w3id.org/framester/framenet/abox/frame/";
    public static final String FN_FRAME = "fnframe:";

    public static final String FS_SCHEMA_SUBSUMED_UNDER = FS_SCHEMA + "subsumedUnder";

    /**
     * Array of Fred elements local names
     */
    public static final String[] PREFIX = {FRED, DUL, BOXER, BOXING, QUANT, VN_ROLE,
        RDF, RDFS, OWL, VN_DATA, DBPEDIA, SCHEMA_ORG, AMR, VA, BN, WN30_SCHEMA,
        WN30_INSTANCES, FS_SCHEMA, PB_DATA, PB_SCHEMA, FN_FRAME};

    /**
     * Array of fred elements name space
     */
    public static final String[] NAMESPACE = {FRED_NS, DUL_NS, BOXER_NS, BOXING_NS,
        QUANT_NS, VN_ROLE_NS, RDF_NS, RDFS_NS, OWL_NS, VN_DATA_NS, DBPEDIA_NS,
        SCHEMA_ORG_NS, AMR_NS, VA_NS, BN_NS, WN30_SCHEMA_NS,
        WN30_INSTANCES_NS, FS_SCHEMA_NS, PB_DATA_NS, PB_SCHEMA_NS, FN_FRAME_NS};

    /**
     * Fred's element names number
     */
    public static final int PREFIX_NUM = 21;

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
        VN_ROLE_INSTRUMENT, DUL_HAS_QUALITY, FRED_FOR, VN_ROLE_CAUSE, FRED_LIKE, VN_ROLE_LOCATION, FRED_ALTHOUGH,
        FRED_IN, DUL_HAS_QUALITY, FRED_IN, FRED_INCLUDE, FRED_OF, DUL_ASSOCIATED_WITH, FRED_WITH};

    public static final String FRED_VARS[] = {"", BOXING_FALSE, "", "", "", "", "", "", "", "",
        "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};

    public static final int PATTERNS_NUMBER = 27;

    public static final String QUOTE = "\"";

    public static final String ADJECTIVE[] = {"aback", "abaft", "abandoned", "abashed", "abdominal",
     "aberrant", "abhorrent", "abiding", "abject", "ablaze", "able", "able-bodied", "abnormal",
     "aboard", "aboriginal", "abortive", "abounding", "abrasive", "abrupt", "absent", "absentminded",
     "absolute", "absorbed", "absorbing", "abstracted", "absurd", "abundant", "abusive", "abysmal",
     "academic", "acceptable", "accepting", "accessible", "accidental", "acclaimed", "accommodating",
     "accompanying", "accountable", "accurate", "accusative", "accused", "accusing", "acerbic",
     "achievable", "aching", "acid", "acidic", "acknowledged", "acoustic", "acrid", "acrimonious",
     "acrobatic", "actionable", "active", "actual", "actually", "ad", "ad hoc", "adamant",
     "adaptable", "adaptive", "addicted", "addictive", "additional", "adept", "adequate",
     "adhesive", "adjacent", "adjoining", "adjustable", "administrative", "admirable", "admired",
     "admiring", "adopted", "adoptive", "adorable", "adored", "adoring", "adrenalized", "adroit",
     "adult", "advanced", "advantageous", "adventurous", "adversarial", "advisable", "aerial",
     "affable", "affected", "affectionate", "affirmative", "affordable", "afraid", "afternoon",
     "ageless", "aggravated", "aggravating", "aggressive", "agitated", "agonizing", "agrarian",
     "agreeable", "ahead", "aimless", "airline", "airsick", "ajar", "alarmed", "alarming", "alcoholic",
     "alert", "algebraic", "alien", "alienated", "alike", "alive", "all", "all-around", "all-purpose",
     "all-too-common", "alleged", "allowable", "alluring", "allusive", "alone", "aloof", "alterable",
     "alternating", "alternative", "amazed", "amazing", "ambiguous", "ambitious", "ambulant", "ambulatory",
     "amiable", "amicable", "amphibian", "amuck", "amused", "amusing", "an", "ancient", "anecdotal",
     "anemic", "angelic", "angered", "angry", "angular", "animal", "animated", "annoyed", "annoying",
     "annual", "anonymous", "another", "antagonistic", "anticipated", "anticlimactic", "anticorrosive",
     "antiquated", "antiseptic", "antisocial", "antsy", "anxious", "any", "apathetic", "apologetic",
     "apologizing", "appalling", "appealing", "appetizing", "applauding", "applicable", "applicative",
     "appreciative", "apprehensive", "approachable", "approaching", "appropriate", "approving",
     "approximate", "aquatic", "architectural", "ardent", "arduous", "arguable", "argumentative",
     "arid", "aristocratic", "aromatic", "arresting", "arrogant", "artful", "artificial", "artistic",
     "artless", "ashamed", "aspiring", "assertive", "assignable", "assorted", "assumable", "assured",
     "assuring", "astonished", "astonishing", "astounded", "astounding", "astringent", "astronomical",
     "astute", "asymmetrical", "athletic", "atomic", "atrocious", "attachable", "attainable", "attentive",
     "attractive", "attributable", "atypical", "audacious", "auspicious", "authentic", "authoritarian",
     "authoritative", "autobiographic", "autographed", "automatic", "autonomous", "available", "avant-garde",
     "avenging", "average", "avian", "avid", "avoidable", "awake", "awakening", "aware", "away", "awesome",
     "awful", "awkward", "axiomatic", "babbling", "baby", "background", "backhanded", "bacterial", "bad",
     "bad-tempered", "baffled", "baffling", "bald", "balding", "balmy", "bandaged", "banging", "bankable",
     "banned", "bantering", "barbaric", "barbarous", "barbequed", "barefooted", "barking", "barren", "bashful",
     "basic", "battered", "batty", "bawdy", "bawling", "beady", "beaming", "bearable", "beautiful",
     "beckoning", "bedazzled", "bedazzling", "beefy", "beeping", "befitting", "befuddled", "beginning",
     "belching", "believable", "bellicose", "belligerent", "bellowing", "bendable", "beneficial",
     "benevolent", "benign", "bent", "berserk", "best", "betrayed", "better", "better off",
     "better-late-than-never", "bewildered", "bewildering", "bewitched", "bewitching", "biased",
     "biblical", "big", "big-city", "big-headed", "bigger", "biggest", "bighearted", "bigoted",
     "bilingual", "billable", "billowy", "binary", "binding", "bioactive", "biodegradable",
     "biographical", "bite-sized", "biting", "bitter", "bizarre", "black", "black-and-blue",
     "black-and-white", "blamable", "blameless", "bland", "blank", "blaring", "blasphemous",
     "blatant", "blazing", "bleached", "bleak", "bleary", "bleary-eyed", "blessed", "blind",
     "blindfolded", "blinding", "blissful", "blistering", "bloated", "blonde", "blood-red",
     "bloodied", "bloodthirsty", "bloody", "blooming", "blossoming", "blue", "blue-eyed",
     "blundering", "blunt", "blurred", "blurry", "blushing", "boastful", "bodacious",
     "bohemian", "boiling", "boisterous", "bold", "bookish", "booming", "boorish",
     "bordering", "bored", "boring", "born", "bossy", "both", "bothered", "bouncing",
     "bouncy", "boundless", "bountiful", "boyish", "braided", "brainless", "brainy",
     "brash", "brassy", "brave", "brawny", "brazen", "breakable", "breathable",
     "breathless", "breathtaking", "breezy", "bribable", "brick", "brief", "bright",
     "bright-eyed", "bright-red", "brilliant", "briny", "brisk", "bristly", "broad",
     "broken", "broken-hearted", "bronchial", "bronze", "bronzed", "brooding", "brown",
     "bruised", "brunette", "brutal", "brutish", "bubbly", "budget", "built-in", "bulky",
     "bumpy", "bungling", "buoyant", "bureaucratic", "burly", "burnable", "burning",
     "bushy", "busiest", "business", "bustling", "busy", "buzzing", "cackling",
     "caged", "cagey", "calculable", "calculated", "calculating", "callous", "calm", "calming", "camouflaged",
     "cancelled", "cancerous", "candid", "cantankerous", "capable", "capricious", "captivated", "captivating",
     "captive", "carefree", "careful", "careless", "caring", "carnivorous", "carpeted", "carsick", "casual",
     "catastrophic", "catatonic", "catchable", "caustic", "cautious", "cavalier", "cavernous", "ceaseless",
     "celebrated", "celestial", "centered", "central", "cerebral", "ceremonial", "certain", "certifiable", "certified",
     "challenged", "challenging", "chance", "changeable", "changing", "chanting", "charging", "charismatic",
     "charitable", "charmed", "charming", "chattering", "chatting", "chatty", "chauvinistic", "cheap", "cheapest",
     "cheeky", "cheerful", "cheering", "cheerless", "cheery", "chemical", "chewable", "chewy", "chic", "chicken",
     "chief", "childish", "childlike", "chilling", "chilly", "chivalrous", "choice", "choking", "choppy",
     "chronological", "chubby", "chuckling", "chunky", "cinematic", "circling", "circular", "circumstantial", "civil",
     "civilian", "civilized", "clammy", "clamoring", "clandestine", "clanging", "clapping", "clashing", "classic",
     "classical", "classifiable", "classified", "classy", "clean", "cleanable", "clear", "cleared", "clearheaded",
     "clever", "climatic", "climbable", "clinging", "clingy", "clinical", "cliquish", "clogged", "cloistered", "close",
     "close-minded", "closeable", "closed", "cloudless", "cloudy", "clownish", "clueless", "clumsy", "cluttered",
     "coachable", "coarse", "cockamamie", "cocky", "codified", "coercive", "cognitive", "coherent", "cohesive",
     "coincidental", "cold", "coldhearted", "collaborative", "collapsed", "collapsing", "collectable", "collegial",
     "colloquial", "colonial", "colorful", "colorless", "colossal", "combative", "combined", "comfortable", "comforted",
     "comforting", "comical", "commanding", "commemorative", "commendable", "commercial", "committed", "common",
     "communal", "communicable", "communicative", "communist", "compact", "comparable", "comparative", "compassionate",
     "compelling", "competent", "competitive", "complacent", "complaining", "complete", "completed", "complex",
     "compliant", "complicated", "complimentary", "compound", "comprehensive", "compulsive", "compulsory", "computer",
     "computerized", "concealable", "concealed", "conceited", "conceivable", "concerned", "concerning", "concerted",
     "concise", "concurrent", "condemned", "condensed", "condescending", "conditional", "confident", "confidential",
     "confirmable", "confirmed", "conflicted", "conflicting", "conformable", "confounded", "confused", "confusing",
     "congenial", "congested", "congressional", "congruent", "congruous", "connectable", "connected", "connecting",
     "connective", "conscientious", "conscious", "consecutive", "consensual", "consenting", "conservative",
     "considerable", "considerate", "consistent", "consoling", "conspicuous", "conspiratorial", "constant",
     "constitutional", "constrictive", "constructive", "consumable", "consummate", "contagious", "containable",
     "contemplative", "contemporary", "contemptible", "contemptuous", "content", "contented", "contentious",
     "contextual", "continual", "continuing", "continuous", "contoured", "contractual", "contradicting",
     "contradictory", "contrarian", "contrary", "contributive", "contrite", "controllable", "controlling",
     "controversial", "convenient", "conventional", "conversational", "convinced", "convincing", "convoluted",
     "convulsive", "cooing", "cooked", "cool", "coolest", "cooperative", "coordinated", "copious", "coquettish",
     "cordial", "corner", "cornered", "corny", "corporate", "corpulent", "correct", "correctable", "corrective",
     "corresponding", "corrosive", "corrupt", "corrupting", "corruptive", "cosmetic", "cosmic", "costly", "cottony",
     "coughing", "courageous", "courteous", "covert", "coveted", "cowardly", "cowering", "coy", "cozy", "crabby",
     "cracked", "crackling", "crafty", "craggy", "crammed", "cramped", "cranky", "crashing", "crass", "craven",
     "crawling", "crazy", "creaking", "creaky", "creamy", "creative", "credible", "creeping", "creepy", "crestfallen",
     "criminal", "crippled", "crippling", "crisp", "crispy", "critical", "crooked", "cropped", "cross", "crossed",
     "crotchety", "crowded", "crucial", "crude", "cruel", "crumbling", "crumbly", "crumply", "crunchable", "crunching",
     "crunchy", "crushable", "crushed", "crusty", "crying", "cryptic", "crystalline", "crystallized", "cuddly",
     "culpable", "cultural", "cultured", "cumbersome", "cumulative", "cunning", "curable", "curative", "curious",
     "curly", "current", "cursed", "curt", "curved", "curvy", "customary", "cut", "cute", "cutting", "cylindrical",
     "cynical", "daffy", "daft", "daily", "dainty", "damaged", "damaging", "damp", "danceable", "dandy", "dangerous",
     "dapper", "daring", "dark", "darkened", "dashing", "daughterly", "daunting", "dawdling", "day", "dazed",
     "dazzling", "dead", "deadly", "deadpan", "deaf", "deafening", "dear", "debatable", "debonair", "decadent",
     "decayed", "decaying", "deceitful", "deceivable", "deceiving", "decent", "decentralized", "deceptive", "decimated",
     "decipherable", "decisive", "declining", "decorative", "decorous", "decreasing", "decrepit", "dedicated", "deep",
     "deepening", "deeply", "defeated", "defective", "defendable", "defenseless", "defensible", "defensive", "defiant",
     "deficient", "definable", "definitive", "deformed", "degenerative", "degraded", "dehydrated", "dejected",
     "delectable", "deliberate", "deliberative", "delicate", "delicious", "delighted", "delightful", "delinquent",
     "delirious", "deliverable", "deluded", "demanding", "demented", "democratic", "demonic", "demonstrative", "demure",
     "deniable", "dense", "dependable", "dependent", "deplorable", "deploring", "depraved", "depressed", "depressing",
     "depressive", "deprived", "deranged", "derivative", "derogative", "derogatory", "descriptive", "deserted",
     "designer", "desirable", "desirous", "desolate", "despairing", "desperate", "despicable", "despised", "despondent",
     "destroyed", "destructive", "detachable", "detached", "detailed", "detectable", "determined", "detestable",
     "detrimental", "devastated", "devastating", "devilish", "devious", "devoted", "devout", "dexterous", "diabolical",
     "diagonal", "didactic", "different", "difficult", "diffuse", "digestive", "digital", "dignified", "digressive",
     "dilapidated", "diligent", "dim", "diminishing", "diminutive", "dingy", "diplomatic", "dire", "direct", "direful",
     "dirty", "disabled", "disadvantaged", "disadvantageous", "disaffected", "disagreeable", "disappearing",
     "disappointed", "disappointing", "disapproving", "disarming", "disastrous", "discarded", "discernable",
     "disciplined", "disconnected", "discontented", "discordant", "discouraged", "discouraging", "discourteous",
     "discredited", "discreet", "discriminating", "discriminatory", "discussable", "disdainful", "diseased",
     "disenchanted", "disgraceful", "disgruntled", "disgusted", "disgusting", "disheartened", "disheartening",
     "dishonest", "dishonorable", "disillusioned", "disinclined", "disingenuous", "disinterested", "disjointed",
     "dislikeable", "disliked", "disloyal", "dismal", "dismissive", "disobedient", "disorderly", "disorganized",
     "disparaging", "disparate", "dispassionate", "dispensable", "displaced", "displeased", "displeasing", "disposable",
     "disproportionate", "disproved", "disputable", "disputatious", "disputed", "disreputable", "disrespectful",
     "disruptive", "dissatisfied", "dissimilar", "dissolvable", "dissolving", "dissonant", "dissuasive", "distant",
     "distasteful", "distinct", "distinctive", "distinguished", "distracted", "distracting", "distraught", "distressed",
     "distressing", "distrustful", "disturbed", "disturbing", "divergent", "diverging", "diverse", "diversified",
     "divided", "divine", "divisive", "dizzy", "dizzying", "doable", "documentary", "dogged", "doggish", "dogmatic",
     "doleful", "dollish", "domed", "domestic", "dominant", "domineering", "dorsal", "doting", "double", "doubtful",
     "doubting", "dovish", "dowdy", "down", "down-and-out", "downhearted", "downloadable", "downtown", "downward",
     "dozing", "drab", "draconian", "drained", "dramatic", "drastic", "dreaded", "dreadful", "dreaming", "dreamy",
     "dreary", "drenched", "dress", "dressy", "dried", "dripping", "drivable", "driven", "droll", "drooping", "droopy",
     "drowsy", "drunk", "dry", "dual", "dubious", "due", "dulcet", "dull", "duplicitous", "durable", "dusty", "dutiful",
     "dwarfish", "dwindling", "dynamic", "dysfunctional", "each", "eager", "ear-piercing", "ear-splitting", "early",
     "earnest", "earsplitting", "earthshaking", "earthy", "east", "eastern", "easy", "eatable", "eccentric", "echoing",
     "ecological", "economic", "economical", "economy", "ecstatic", "edgy", "editable", "educated", "educational",
     "eerie", "effective", "effervescent", "efficacious", "efficient", "effortless", "effusive", "egalitarian",
     "egocentric", "egomaniacal", "egotistical", "eight", "eighth", "either", "elaborate", "elastic", "elated",
     "elderly", "electric", "electrical", "electrifying", "electronic", "elegant", "elementary", "elevated", "elfin",
     "elfish", "eligible", "elite", "eloquent", "elusive", "emaciated", "embarrassed", "embarrassing", "embattled",
     "embittered", "emblematic", "emboldened", "embroiled", "emergency", "eminent", "emotional", "emotionless",
     "empirical", "empty", "enamored", "enchanted", "enchanting", "encouraged", "encouraging", "encrusted",
     "endangered", "endearing", "endemic", "endless", "endurable", "enduring", "energetic", "energizing", "enforceable",
     "engaging", "engrossing", "enhanced", "enigmatic", "enjoyable", "enlarged", "enlightened", "enormous", "enough",
     "enraged", "ensuing", "enterprising", "entertained", "entertaining", "enthralled", "enthused", "enthusiastic",
     "enticing", "entire", "entranced", "entrepreneurial", "enumerable", "enviable", "envious", "environmental",
     "episodic", "equable", "equal", "equidistant", "equitable", "equivalent", "erasable", "erect", "eroding", "errant",
     "erratic", "erroneous", "eruptive", "escalating", "esoteric", "essential", "established", "estimated", "estranged",
     "eternal", "ethereal", "ethical", "ethnic", "euphemistic", "euphoric", "evanescent", "evasive", "even",
     "evenhanded", "evening", "eventful", "eventual", "everlasting", "every", "evil", "evocative", "exacerbating",
     "exact", "exacting", "exaggerated", "exalted", "exasperated", "exasperating", "excellent", "exceptional",
     "excessive", "exchangeable", "excitable", "excited", "exciting", "exclusive", "excruciating", "excusable",
     "executable", "exemplary", "exhausted", "exhausting", "exhaustive", "exhilarated", "exhilarating", "existing",
     "exotic", "expandable", "expanded", "expanding", "expansive", "expectant", "expected", "expedient", "expeditious",
     "expendable", "expensive", "experimental", "expert", "expired", "expiring", "explainable", "explicit", "exploding",
     "exploitative", "exploited", "explosive", "exponential", "exposed", "express", "expressionistic", "expressionless",
     "expressive", "exquisite", "extemporaneous", "extendable", "extended", "extension", "extensive", "exterior",
     "external", "extra", "extra-large", "extra-small", "extraneous", "extraordinary", "extravagant", "extreme",
     "exuberant", "exultant", "eye-popping", "fabled", "fabulous", "facetious", "facial", "factitious", "factual",
     "faded", "fading", "failed", "faint", "fainthearted", "fair", "faithful", "faithless", "fallacious", "false",
     "falsified", "faltering", "familiar", "famished", "famous", "fanatical", "fanciful", "fancy", "fantastic", "far",
     "far-flung", "faraway", "farcical", "farsighted", "fascinated", "fascinating", "fascistic", "fashionable", "fast",
     "fast-moving", "fastest", "fastidious", "fat", "fatal", "fateful", "fatherly", "fathomable", "fathomless",
     "fatigued", "faulty", "favorable", "favorite", "fawning", "feared", "fearful", "fearless", "fearsome", "feathered",
     "feathery", "feckless", "federal", "feeble", "feebleminded", "feeling", "feigned", "felonious", "female",
     "feminine", "fermented", "ferocious", "fertile", "fervent", "fervid", "festive", "fetching", "fetid", "feudal",
     "feverish", "few", "few,", "fewer", "fictional", "fictitious", "fidgeting", "fidgety", "fiendish", "fierce",
     "fiery", "fifth", "filmy", "filtered", "filthy", "final", "financial", "fine", "finicky", "finite", "fireproof",
     "firm", "first", "fiscal", "fishy", "fit", "fitted", "fitting", "five", "fixable", "fixed", "flabby", "flagrant",
     "flaky", "flamboyant", "flaming", "flammable", "flashy", "flat", "flattened", "flattered", "flattering",
     "flavored", "flavorful", "flavorless", "flawed", "flawless", "fleeting", "flexible", "flickering", "flimsy",
     "flippant", "flirtatious", "floating", "flooded", "floppy", "floral", "flowering", "flowery", "fluent", "fluffy",
     "flushed", "fluttering", "flying", "foamy", "focused", "foggy", "folded", "following", "fond", "foolhardy",
     "foolish", "forbidding", "forceful", "foreboding", "foregoing", "foreign", "forensic", "foreseeable", "forged",
     "forgetful", "forgettable", "forgivable", "forgiving", "forgotten", "forked", "formal", "formative", "former",
     "formidable", "formless", "formulaic", "forthright", "fortuitous", "fortunate", "forward", "foul", "foul-smelling",
     "four", "fourth", "foxy", "fractional", "fractious", "fragile", "fragmented", "fragrant", "frail", "frank",
     "frantic", "fraternal", "fraudulent", "frayed", "freakish", "freaky", "freckled", "free", "freezing", "frequent",
     "fresh", "fretful", "fried", "friendly", "frightened", "frightening", "frightful", "frigid", "frilly", "frisky",
     "frivolous", "front", "frosty", "frothy", "frowning", "frozen", "frugal", "fruitful", "fruitless", "fruity",
     "frumpy", "frustrated", "frustrating", "fulfilled", "fulfilling", "full", "fully-grown", "fumbling", "fuming",
     "fun", "fun-loving", "functional", "fundamental", "funniest", "funny", "furious", "furry", "furthest", "furtive",
     "fussy", "futile", "future", "futuristic", "fuzzy", "gabby", "gainful", "gallant", "galling", "game", "gamy",
     "gangly", "gaping", "garbled", "gargantuan", "garish", "garrulous", "gaseous", "gasping", "gaudy", "gaunt",
     "gauzy", "gawky", "general", "generative", "generic", "generous", "genial", "gentle", "genuine", "geographic",
     "geologic", "geometric", "geriatric", "ghastly", "ghostly", "ghoulish", "giant", "giddy", "gifted", "gigantic",
     "giggling", "gilded", "giving", "glad", "glamorous", "glaring", "glass", "glassy", "gleaming", "glib",
     "glistening", "glittering", "global", "globular", "gloomy", "glorious", "glossy", "glowing", "gluey", "glum",
     "gluttonous", "gnarly", "godly", "gold", "golden", "good", "good-looking", "good-natured", "gooey", "goofy",
     "gorgeous", "graceful", "gracious", "gradual", "grainy", "grand", "grandiose", "graphic", "grateful", "gratified",
     "gratifying", "grating", "gratis", "gratuitousgrave", "gray", "greasy", "great", "greatest", "greedy", "green",
     "gregarious", "grey", "grieving", "grim", "grimacing", "grimy", "grinding", "grinning", "gripping", "gritty",
     "grizzled", "groaning", "groggy", "groomed", "groovy", "gross", "grotesque", "grouchy", "growling", "grown-up",
     "grubby", "grueling", "gruesome", "gruff", "grumbling", "grumpy", "guaranteed", "guarded", "guilt-ridden",
     "guiltless", "guilty", "gullible", "gurgling", "gushing", "gushy", "gusty", "gutsy", "guttural", "habitable",
     "habitual", "haggard", "hairless", "hairy", "half", "halfhearted", "hallowed", "halting", "handsome", "handsomely",
     "handy", "hanging", "haphazard", "hapless", "happy", "hard", "hard-to-find", "hardworking", "hardy", "harebrained",
     "harmful", "harmless", "harmonic", "harmonious", "harried", "harsh", "hasty", "hated", "hateful", "haughty",
     "haunting", "hawkish", "hazardous", "hazy", "head", "heady", "healthy", "heartbreaking", "heartbroken",
     "heartless", "heartrending", "hearty", "heated", "heavenly", "heavy", "hectic", "hefty", "heinous", "hellish",
     "helpful", "helpless", "her", "heroic", "hesitant", "hideous", "high", "high-functioning", "high-maintenance",
     "high-pitched", "high-risk", "highest", "highfalutin", "hilarious", "his", "hissing", "historical", "hoarse",
     "hoc", "hoggish", "holiday", "holistic", "hollow", "home", "homeless", "homely", "homeopathic", "homey",
     "homogeneous", "honest", "honking", "honorable", "hopeful", "hopeless", "horizontal", "hormonal", "horned",
     "horrendous", "horrible", "horrid", "horrific", "horrified", "horrifying", "hospitable", "hostile", "hot",
     "hot pink", "hot-blooded", "hot-shot", "hot-tempered", "hotheaded", "hour-long", "house", "howling", "huffy",
     "huge", "huggable", "hulking", "human", "humanitarian", "humanlike", "humble", "humdrum", "humid", "humiliated",
     "humiliating", "humming", "humongous", "humorless", "humorous", "hungry", "hurried", "hurt", "hurtful", "hushed",
     "husky", "hydraulic", "hydrothermal", "hygienic", "hyper-active", "hyperbolic", "hypercritical", "hyperirritable",
     "hypersensitive", "hypertensive", "hypnotic", "hypnotizable", "hypothetical", "hysterical", "icky", "iconoclastic",
     "icy", "icy-cold", "ideal", "idealistic", "identical", "identifiable", "idiosyncratic", "idiotic", "idyllic",
     "ignorable", "ignorant", "ill", "ill-equipped", "ill-fated", "ill-humored", "ill-informed", "illegal", "illegible",
     "illegitimate", "illicit", "illiterate", "illogical", "illuminating", "illusive", "illustrious", "imaginable",
     "imaginary", "imaginative", "imitative", "immaculate", "immanent", "immature", "immeasurable", "immediate",
     "immense", "immensurable", "imminent", "immobile", "immodest", "immoral", "immortal", "immovable", "impartial",
     "impassable", "impassioned", "impatient", "impeccable", "impenetrable", "imperative", "imperceptible",
     "imperceptive", "imperfect", "imperial", "imperialistic", "impermeable", "impersonal", "impertinent", "impervious",
     "impetuous", "impish", "implausible", "implicit", "implosive", "impolite", "imponderable", "important", "imported",
     "imposing", "impossible", "impoverished", "impractical", "imprecise", "impressionable", "impressive", "improbable",
     "improper", "improvable", "improved", "improving", "imprudent", "impulsive", "impure", "inaccessible",
     "inaccurate", "inactive", "inadequate", "inadmissible", "inadvertent", "inadvisable", "inalienable", "inalterable",
     "inane", "inanimate", "inapplicable", "inappropriate", "inapt", "inarguable", "inarticulate", "inartistic",
     "inattentive", "inaudible", "inauspicious", "incalculable", "incandescent", "incapable", "incessant", "incidental",
     "inclusive", "incoherent", "incomparable", "incompatible", "incompetent", "incomplete", "incomprehensible",
     "inconceivable", "inconclusive", "incongruent", "incongruous", "inconsequential", "inconsiderable",
     "inconsiderate", "inconsistent", "inconsolable", "inconspicuous", "incontrovertible", "inconvenient", "incorrect",
     "incorrigible", "incorruptible", "increasing", "incredible", "incredulous", "incremental", "incurable", "indecent",
     "indecipherable", "indecisive", "indefensible", "indefinable", "indefinite", "indelible", "independent",
     "indescribable", "indestructible", "indeterminable", "indeterminate", "indicative", "indifferent", "indigenous",
     "indignant", "indirect", "indiscreet", "indiscriminate", "indispensable", "indisputable", "indistinct",
     "individual", "individualistic", "indivisible", "indomitable", "inductive", "indulgent", "industrial",
     "industrious", "ineffective", "ineffectual", "inefficient", "inelegant", "ineloquent", "inequitable", "inert",
     "inescapable", "inevitable", "inexact", "inexcusable", "inexhaustible", "inexpedient", "inexpensive",
     "inexplicable", "inexpressible", "inexpressive", "inextricable", "infallible", "infamous", "infantile",
     "infatuated", "infected", "infectious", "inferable", "inferior", "infernal", "infinite", "infinitesimal",
     "inflamed", "inflammable", "inflammatory", "inflatable", "inflated", "inflexible", "influential", "informal",
     "informative", "informed", "infrequent", "infuriated", "infuriating", "ingenious", "ingenuous", "inglorious",
     "ingratiating", "inhabitable", "inharmonious", "inherent", "inhibited", "inhospitable", "inhuman", "inhumane",
     "initial", "injudicious", "injured", "injurious", "innate", "inner", "innocent", "innocuous", "innovative",
     "innumerable", "inoffensive", "inoperable", "inoperative", "inopportune", "inordinate", "inorganic", "inquiring",
     "inquisitive", "insane", "insatiable", "inscrutable", "insecure", "insensible", "insensitive", "inseparable",
     "inside", "insidious", "insightful", "insignificant", "insincere", "insipid", "insistent", "insolent",
     "inspirational", "inspired", "inspiring", "instant", "instantaneous", "instinctive", "instinctual",
     "institutional", "instructive", "instrumental", "insubordinate", "insufferable", "insufficient", "insulted",
     "insulting", "insurable", "insurmountable", "intangible", "integral", "intellectual", "intelligent",
     "intelligible", "intended", "intense", "intensive", "intentional", "interactive", "interchangeable",
     "interdepartmental", "interdependent", "interested", "interesting", "interior", "intermediate", "intermittent",
     "internal", "international", "interpersonal", "interracial", "intestinal", "intimate", "intimidating",
     "intolerable", "intolerant", "intravenous", "intrepid", "intricate", "intrigued", "intriguing", "intrinsic",
     "introductory", "introspective", "introverted", "intrusive", "intuitive", "invalid", "invaluable", "invasive",
     "inventive", "invigorating", "invincible", "invisible", "invited", "inviting", "involuntary", "involved", "inward",
     "irascible", "irate", "iridescent", "irksome", "iron", "iron-fisted", "ironic", "irrational", "irreconcilable",
     "irrefutable", "irregular", "irrelative", "irrelevant", "irremovable", "irreparable", "irreplaceable",
     "irrepressible", "irresistible", "irresponsible", "irretrievably", "irreverent", "irreversible", "irrevocable",
     "irritable", "irritated", "irritating", "isolated", "itchy", "its", "itty-bitty", "jabbering", "jaded", "jagged",
     "jarring", "jaundiced", "jazzy", "jealous", "jeering", "jerky", "jiggling", "jittery", "jobless", "jocular",
     "joint", "jolly", "jovial", "joyful", "joyless", "joyous", "jubilant", "judgmental", "judicious", "juicy",
     "jumbled", "jumpy", "junior", "just", "justifiable", "juvenile", "kaput", "keen", "key", "kind", "kindhearted",
     "kindly", "kinesthetic", "kingly", "kitchen", "knavish", "knightly", "knobbed", "knobby", "knotty", "knowable",
     "knowing", "knowledgeable", "known", "labored", "laborious", "lackadaisical", "lacking", "lacy", "lame",
     "lamentable", "languid", "languishing", "lanky", "larcenous", "large", "larger", "largest", "lascivious", "last",
     "lasting", "late", "latent", "later", "lateral", "latest", "latter", "laudable", "laughable", "laughing", "lavish",
     "lawful", "lawless", "lax", "lazy", "lead", "leading", "lean", "learnable", "learned", "leased", "least",
     "leather", "leathery", "lecherous", "leering", "left", "left-handed", "legal", "legendary", "legible",
     "legislative", "legitimate", "lengthy", "lenient", "less", "less-qualified", "lesser", "lesser-known", "lethal",
     "lethargic", "level", "lewd", "liable", "libelous", "liberal", "licensed", "life", "lifeless", "lifelike",
     "lifelong", "light", "light-blue", "lighthearted", "likable", "like", "like-minded", "likeable", "likely",
     "lily-livered", "limber", "limited", "limitless", "limp", "limping", "linear", "lined", "lingering", "linguistic",
     "liquid", "listless", "literal", "literary", "literate", "lithe", "lithographic", "litigious", "little", "livable",
     "live", "lively", "livid", "living", "loathsome", "local", "locatable", "locked", "lofty", "logarithmic",
     "logical", "logistic", "lonely", "long", "long-term", "long-winded", "longer", "longest", "longing", "loose",
     "lopsided", "loquacious", "lordly", "lost", "loud", "lousy", "loutish", "lovable", "loveable", "lovely", "loving",
     "low", "low-calorie", "low-carb", "low-fat", "low-maintenance", "low-ranking", "low-risk", "lower", "lowly",
     "loyal", "lucent", "lucid", "lucky", "lucrative", "ludicrous", "lukewarm", "lulling", "luminescent", "luminous",
     "lumpy", "lurid", "luscious", "lush", "lustrous", "luxuriant", "luxurious", "lying", "lyrical", "macabre",
     "machiavellian", "macho", "mad", "maddening", "madly", "magenta", "magic", "magical", "magnanimous", "magnetic",
     "magnificent", "maiden", "main", "maintainable", "majestic", "major", "makeable", "makeshift", "maladjusted",
     "male", "malevolent", "malicious", "malignant", "malleable", "mammoth", "man-made", "manageable", "managerial",
     "mandatory", "maneuverable", "mangy", "maniacal", "manic", "manicured", "manipulative", "manual", "many", "many,",
     "marbled", "marginal", "marked", "marketable", "married", "marvelous", "masked", "massive", "master", "masterful",
     "matchless", "material", "materialistic", "maternal", "mathematical", "matronly", "matted", "mature", "maximum",
     "meager", "mean", "mean-spirited", "meandering", "meaningful", "meaningless", "measly", "measurable",
     "meat-eating", "meaty", "mechanical", "medical", "medicinal", "meditative", "medium", "medium-rare", "meek",
     "melancholy", "mellow", "melodic", "melodious", "melodramatic", "melted", "memorable", "menacing", "menial",
     "mental", "merciful", "merciless", "mercurial", "mere", "merry", "messy", "metabolic", "metallic", "metaphoric",
     "meteoric", "meticulous", "microscopic", "microwaveable", "middle", "middle-class", "midweek", "mighty", "mild",
     "militant", "militaristic", "military", "milky", "mincing", "mind-bending", "mindful", "mindless", "mini",
     "miniature", "minimal", "minimum", "minor", "minute", "miraculous", "mirthful", "miscellaneous", "mischievous",
     "miscreant", "miserable", "miserly", "misguided", "misleading", "mission", "mistaken", "mistrustful",
     "mistrusting", "misty", "mixed", "mnemonic", "moaning", "mobile", "mocking", "moderate", "modern", "modest",
     "modified", "modular", "moist", "moldy", "momentary", "momentous", "monetary", "money-grubbing", "monopolistic",
     "monosyllabic", "monotone", "monotonous", "monstrous", "monumental", "moody", "moral", "moralistic", "morbid",
     "mordant", "more", "moronic", "morose", "mortal", "mortified", "most", "mother", "motherly", "motionless",
     "motivated", "motivating", "motivational", "motor", "mountain", "mountainous", "mournful", "mouthwatering",
     "movable", "moved", "moving", "much", "muddled", "muddy", "muffled", "muggy", "multicultural", "multifaceted",
     "multipurpose", "multitalented", "mumbled", "mundane", "municipal", "murky", "muscular", "mushy", "musical",
     "musky", "musty", "mutative", "mute", "muted", "mutinous", "muttering", "mutual", "my", "myopic", "mysterious",
     "mystic", "mystical", "mystified", "mystifying", "mythical", "naive", "nameless", "nappy", "narcissistic",
     "narrow", "narrow-minded", "nasal", "nasty", "national", "native", "natural", "naughty", "nauseating", "nauseous",
     "nautical", "navigable", "navy-blue", "near", "nearby", "nearest", "nearsighted", "neat", "nebulous", "necessary",
     "needless", "needy", "nefarious", "negative", "neglected", "neglectful", "negligent", "negligible", "negotiable",
     "neighborly", "neither", "nerve-racking", "nervous", "neurological", "neurotic", "neutral", "new", "newest",
     "next", "next-door", "nice", "nifty", "nightmarish", "nimble", "nine", "ninth", "nippy", "no", "noble",
     "nocturnal", "noiseless", "noisy", "nominal", "nonabrasive", "nonaggressive", "nonchalant", "noncommittal",
     "noncompetitive", "nonconsecutive", "nondescript", "nondestructive", "nonexclusive", "nonnegotiable",
     "nonproductive", "nonrefundable", "nonrenewable", "nonresponsive", "nonrestrictive", "nonreturnable",
     "nonsensical", "nonspecific", "nonstop", "nontransferable", "nonverbal", "nonviolent", "normal", "north",
     "northeast", "northerly", "northwest", "nostalgic", "nosy", "notable", "noticeable", "notorious", "novel",
     "noxious", "null", "numb", "numberless", "numbing", "numerable", "numeric", "numerous", "nutritional",
     "nutritious", "nutty", "oafish", "obedient", "obeisant", "obese", "objectionable", "objective", "obligatory",
     "obliging", "oblique", "oblivious", "oblong", "obnoxious", "obscene", "obscure", "obsequious", "observable",
     "observant", "obsessive", "obsolete", "obstinate", "obstructive", "obtainable", "obtrusive", "obtuse", "obvious",
     "occasional", "occupational", "occupied", "oceanic", "odd", "odd-looking", "odiferous", "odious", "odorless",
     "odorous", "offbeat", "offensive", "offhanded", "official", "officious", "oily", "ok", "okay", "old",
     "old-fashioned", "older", "oldest", "ominous", "omniscient", "omnivorous", "one", "one-hour", "one-sided",
     "onerous", "only", "opaque", "open", "open-minded", "opened", "openhanded", "openhearted", "opening", "operable",
     "operatic", "operational", "operative", "opinionated", "opportune", "opportunistic", "opposable", "opposed",
     "opposing", "opposite", "oppressive", "optimal", "optimistic", "optional", "opulent", "oral", "orange", "ordinary",
     "organic", "organizational", "original", "ornamental", "ornate", "ornery", "orphaned", "orthopedic", "ossified",
     "ostentatious", "other", "otherwise", "our", "outer", "outermost", "outgoing", "outlandish", "outraged",
     "outrageous", "outside", "outspoken", "outstanding", "outward", "oval", "overactive", "overaggressive", "overall",
     "overambitious", "overassertive", "overbearing", "overcast", "overcautious", "overconfident", "overcritical",
     "overcrowded", "overemotional", "overenthusiastic", "overjoyed", "overoptimistic", "overpowering", "overpriced",
     "overprotective", "overqualified", "overrated", "oversensitive", "oversized", "overt", "overwhelmed",
     "overwhelming", "overworked", "overwrought", "overzealous", "own", "oxymoronic", "padded", "painful", "painless",
     "painstaking", "palatable", "palatial", "pale", "pallid", "palpable", "paltry", "pampered", "panicky", "panoramic",
     "paradoxical", "parallel", "paranormal", "parasitic", "parched", "pardonable", "parental", "parenthetic",
     "parking", "parsimonious", "part-time", "partial", "particular", "partisan", "party", "passing", "passionate",
     "passive", "past", "pastoral", "patched", "patchy", "patented", "paternal", "paternalistic", "pathetic",
     "pathological", "patient", "patriotic", "patronizing", "patterned", "payable", "peaceable", "peaceful", "peculiar",
     "pedantic", "pedestrian", "peerless", "peeved", "peevish", "penetrable", "penetrating", "penitent", "pensive",
     "peppery", "perceivable", "perceptible", "perceptive", "perceptual", "peremptory", "perennial", "perfect",
     "perfumed", "perilous", "period", "periodic", "peripheral", "perishable", "perky", "permanent", "permeable",
     "permissible", "permissive", "pernicious", "perpendicular", "perpetual", "perplexed", "perplexing", "persevering",
     "persistent", "personable", "personal", "persuasive", "pert", "pertinent", "perturbed", "perturbing", "pervasive",
     "perverse", "pessimistic", "petite", "pettish", "petty", "petulant", "pharmaceutical", "phenomenal",
     "philanthropic", "philosophical", "phobic", "phonemic", "phonetic", "phosphorescent", "photographic", "physical",
     "physiological", "picayune", "picturesque", "piercing", "pigheaded", "pink", "pious", "piquant", "pitch-dark",
     "pitch-perfect", "piteous", "pithy", "pitiful", "pitiless", "pivotal", "placid", "plaid", "plain", "plane",
     "planned", "plant", "plastic", "platonic", "plausible", "playful", "pleading", "pleasant", "pleased", "pleasing",
     "pleasurable", "plentiful", "pliable", "plodding", "plopping", "plucky", "plump", "pluralistic", "plus", "plush",
     "pneumatic", "poetic", "poignant", "pointless", "poised", "poisonous", "polished", "polite", "political",
     "polka-dotted", "polluted", "polyunsaturated", "pompous", "ponderous", "poor", "poorer", "poorest", "popping",
     "popular", "populous", "porous", "portable", "portly", "positive", "possessive", "possible", "post hoc",
     "posthumous", "postoperative", "potable", "potent", "potential", "powdery", "powerful", "powerless", "practical",
     "pragmatic", "praiseworthy", "precarious", "precious", "precipitous", "precise", "precocious", "preconceived",
     "predicative", "predictable", "predisposed", "predominant", "preeminent", "preemptive", "prefabricated",
     "preferable", "preferential", "pregnant", "prehistoric", "prejudiced", "prejudicial", "preliminary", "premature",
     "premeditated", "premium", "prenatal", "preoccupied", "preoperative", "preparative", "prepared", "preposterous",
     "prescriptive", "present", "presentable", "presidential", "pressing", "pressurized", "prestigious", "presumable",
     "presumptive", "presumptuous", "pretend", "pretentious", "pretty", "prevalent", "preventable", "preventative",
     "preventive", "previous", "priceless", "pricey", "prickly", "prim", "primary", "primitive", "primordial",
     "princely", "principal", "principled", "prior", "prissy", "pristine", "private", "prize", "prized", "proactive",
     "probabilistic", "probable", "problematic", "procedural", "prodigious", "productive", "profane", "professed",
     "professional", "professorial", "proficient", "profitable", "profound", "profuse", "programmable", "progressive",
     "prohibitive", "prolific", "prominent", "promised", "promising", "prompt", "pronounceable", "pronounced", "proof",
     "proper", "prophetic", "proportional", "proportionate", "proportioned", "prospective", "prosperous", "protective",
     "prototypical", "proud", "proverbial", "provisional", "provocative", "provoking", "proximal", "proximate",
     "prudent", "prudential", "prying", "psychedelic", "psychiatric", "psychological", "psychosomatic", "psychotic",
     "public", "puckish", "puffy", "pugnacious", "pumped", "punctual", "pungent", "punishable", "punitive", "puny",
     "pure", "purified", "puritanical", "purple", "purported", "purposeful", "purposeless", "purring", "pushy",
     "pusillanimous", "putrid", "puzzled", "puzzling", "pyrotechnic", "quack", "quackish", "quacky", "quaint",
     "qualified", "qualitative", "quality", "quantifiable", "quantitative", "quarrelsome", "queasy", "queenly",
     "querulous", "questionable", "quick", "quick-acting", "quick-drying", "quick-minded", "quick-paced",
     "quick-tempered", "quick-thinking", "quick-witted", "quickest", "quiet", "quintessential", "quirky", "quivering",
     "quixotic", "quizzical", "quotable", "rabid", "racial", "racist", "radiant", "radical", "radioactive", "ragged",
     "raging", "rainbow colored", "rainy", "rakish", "rambling", "rambunctious", "rampageous", "rampant", "rancid",
     "rancorous", "random", "rank", "rapid", "rapid-fire", "rapturous", "rare", "rascally", "rash", "rasping", "raspy",
     "rational", "ratty", "ravenous", "raving", "ravishing", "raw", "razor-edged", "reactive", "ready", "real",
     "realistic", "reasonable", "reassured", "reassuring", "rebel", "rebellious", "receding", "recent", "receptive",
     "recessive", "rechargeable", "reciprocal", "reckless", "reclusive", "recognizable", "recognized", "recondite",
     "rectangular", "rectifiable", "recurrent", "recyclable", "red", "red-blooded", "reddish", "redeemable", "redolent",
     "redundant", "referential", "refillable", "reflective", "refractive", "refreshing", "refundable", "refurbished",
     "refutable", "regal", "regional", "regretful", "regrettable", "regular", "reigning", "relatable", "relative",
     "relaxed", "relaxing", "relentless", "relevant", "reliable", "relieved", "religious", "reluctant", "remaining",
     "remarkable", "remedial", "reminiscent", "remorseful", "remorseless", "remote", "removable", "renegotiable",
     "renewable", "rented", "repairable", "repairedrepeatable", "repeated", "repentant", "repetitious", "repetitive",
     "replaceable", "replicable", "reported", "reprehensible", "representative", "repressive", "reproachful",
     "reproductive", "republican", "repugnant", "repulsive", "reputable", "reputed", "rescued", "resealable",
     "resentful", "reserved", "resident", "residential", "residual", "resilient", "resolute", "resolvable", "resonant",
     "resounding", "resourceful", "respectable", "respectful", "respective", "responsible", "responsive", "rested",
     "restful", "restless", "restored", "restrained", "restrictive", "retired", "retroactive", "retrogressive",
     "retrospective", "reusable", "revamped", "revealing", "revengeful", "reverent", "reverential", "reverse",
     "reversible", "reviewable", "reviled", "revisable", "revised", "revocable", "revolting", "revolutionary",
     "rewarding", "rhetorical", "rhythmic", "rich", "richer", "richest", "ridiculing", "ridiculous", "right",
     "right-handed", "righteous", "rightful", "rigid", "rigorous", "ringing", "riotous", "ripe", "rippling", "risky",
     "ritualistic", "ritzy", "riveting", "roaring", "roasted", "robotic", "robust", "rocketing", "roguish", "romantic",
     "roomy", "rosy", "rotating", "rotten", "rotting", "rotund", "rough", "round", "roundtable", "rousing", "routine",
     "rowdy", "royal", "ruddy", "rude", "rudimentary", "rueful", "rugged", "ruined", "ruinous", "rumbling", "rumpled",
     "ruptured", "rural", "rusted", "rustic", "rustling", "rusty", "ruthless", "rutted", "sable", "saccharin", "sacred",
     "sacrificial", "sacrilegious", "sad", "saddened", "safe", "saintly", "salacious", "salient", "salt", "salted",
     "salty", "salvageable", "salvaged", "same", "sanctimonious", "sandy", "sane", "sanguine", "sanitary", "sappy",
     "sarcastic", "sardonic", "sassy", "satin", "satiny", "satiric", "satirical", "satisfactory", "satisfied",
     "satisfying", "saucy", "savage", "savory", "savvy", "scalding", "scaly", "scandalous", "scant", "scanty", "scarce",
     "scared", "scarred", "scary", "scathing", "scattered", "scenic", "scented", "scheduled", "schematic", "scholarly",
     "scholastic", "scientific", "scintillating", "scorching", "scornful", "scrabbled", "scraggly", "scrappy",
     "scratched", "scratchy", "scrawny", "screaming", "screeching", "scribbled", "scriptural", "scruffy", "scrumptious",
     "scrupulous", "sculpted", "sculptural", "scummy", "sea", "sealed", "seamless", "searching", "searing", "seasick",
     "seasonable", "seasonal", "secluded", "second", "second-hand", "secondary", "secret", "secretive", "secular",
     "secure", "secured", "sedate", "seditious", "seductive", "seedy", "seeming", "seemly", "seething", "seismic",
     "select", "selected", "selective", "self-absorbed", "self-aggrandizing", "self-assured", "self-centered",
     "self-confident", "self-directed", "self-disciplined", "self-effacing", "self-indulgent", "self-interested",
     "self-reliant", "self-respect", "self-satisfied", "selfish", "selfless", "sellable", "semiconscious",
     "semiofficial", "semiprecious", "semiprofessional", "senior", "sensational", "senseless", "sensible", "sensitive",
     "sensual", "sensuous", "sentimental", "separate", "sequential", "serendipitous", "serene", "serial", "serious",
     "serrated", "serviceable", "seven", "seventh", "several", "severe", "shabbiest", "shabby", "shaded", "shadowed",
     "shadowy", "shady", "shaggy", "shaky", "shallow", "shamefaced", "shameful", "shameless", "shapeless", "shapely",
     "sharp", "sharpened", "shattered", "shattering", "sheepish", "sheer", "sheltered", "shifty", "shimmering",
     "shining", "shiny", "shivering", "shivery", "shocked", "shocking", "shoddy", "short", "short-lived",
     "short-tempered", "short-term", "shortsighted", "showy", "shrewd", "shrieking", "shrill", "shut", "shy", "sick",
     "sickened", "sickening", "sickly", "side-splitting", "signed", "significant", "silent", "silky", "silly", "silver",
     "silver-tongued", "simian", "similar", "simple", "simpleminded", "simplified", "simplistic", "simultaneous",
     "sincere", "sinful", "single", "single-minded", "singular", "sinister", "sinuous", "sisterly", "six", "sixth",
     "sizable", "sizzling", "skeptical", "sketchy", "skilled", "skillful", "skimpy", "skin-deep", "skinny", "skittish",
     "sky-blue", "slanderous", "slanted", "slanting", "sleek", "sleeping", "sleepless", "sleepy", "slender", "slick",
     "slight", "slim", "slimy", "slippery", "sloped", "sloping", "sloppy", "slothful", "slow", "slow-moving",
     "sluggish", "slushy", "sly", "small", "small-minded", "small-scale", "small-time", "small-town", "smaller",
     "smallest", "smarmy", "smart", "smarter", "smartest", "smashing", "smeared", "smelly", "smiling", "smoggy",
     "smoked", "smoky", "smooth", "smothering", "smudged", "smug", "snapping", "snappish", "snappy", "snarling",
     "sneaky", "snide", "snippy", "snobbish", "snoopy", "snooty", "snoring", "snotty", "snow-white", "snug", "snuggly",
     "soaked", "soaking", "soaking wet", "soaring", "sober", "sociable", "social", "socialist", "sociological", "soft",
     "softhearted", "soggy", "solar", "soldierly", "sole", "solemn", "solicitous", "solid", "solitary", "somatic",
     "somber", "some,", "sonic", "sonly", "soothed", "soothing", "sophisticated", "sordid", "sore", "sorrowful",
     "sorry", "soulful", "soulless", "soundless", "sour", "south", "southeasterly", "southern", "southwestern",
     "spacious", "spare", "sparing", "sparkling", "sparkly", "sparse", "spasmodic", "spastic", "spatial", "spattered",
     "special", "specialist", "specialized", "specific", "speckled", "spectacular", "spectral", "speculative",
     "speechless", "speedy", "spellbinding", "spendthrift", "spherical", "spicy", "spiffy", "spiky", "spinal",
     "spineless", "spiral", "spiraled", "spirited", "spiritless", "spiritual", "spiteful", "splashing", "splashy",
     "splattered", "splendid", "splintered", "spoiled", "spoken", "spongy", "spontaneous", "spooky", "sporadic",
     "sporting", "sportsmanly", "spotless", "spotted", "spotty", "springy", "sprite", "spry", "spurious", "squalid",
     "squandered", "square", "squashed", "squashy", "squatting", "squawking", "squealing", "squeamish", "squeezable",
     "squiggly", "squirming", "squirrelly", "stable", "stackable", "stacked", "staggering", "stagnant", "stained",
     "staking", "stale", "stanch", "standard", "standing", "standoffish", "star-crossed", "starched", "stark",
     "startled", "startling", "starving", "stately", "static", "statistical", "statuesque", "status", "statutory",
     "staunch", "steadfast", "steady", "stealth", "steaming", "steamy", "steel", "steely", "steep", "stereophonic",
     "stereotyped", "stereotypical", "sterile", "stern", "sticky", "stiff", "stifled", "stifling", "stigmatic", "still",
     "stilled", "stilted", "stimulating", "stinging", "stingy", "stinking", "stinky", "stirring", "stock", "stodgy",
     "stoic", "stony", "stormy", "stout", "straggly", "straight", "straightforward", "stranded", "strange", "strategic",
     "streaked", "street", "strenuous", "stressful", "stretchy", "strict", "strident", "striking", "stringent",
     "striped", "strong", "stronger", "strongest", "structural", "stubborn", "stubby", "stuck-up", "studied",
     "studious", "stuffed", "stuffy", "stumbling", "stunned", "stunning", "stupendous", "stupid", "sturdy",
     "stuttering", "stylish", "stylistic", "suave", "subconscious", "subdued", "subject", "subjective", "sublime",
     "subliminal", "submissive", "subordinate", "subsequent", "subservient", "substantial", "substantiated",
     "substitute", "subterranean", "subtitled", "subtle", "subversive", "successful", "successive", "succinct",
     "succulent", "such", "sudden", "suffering", "sufficient", "sugary", "suggestive", "suitable", "sulky", "sullen",
     "sumptuous", "sunny", "super", "superabundant", "superb", "supercilious", "superficial", "superhuman", "superior",
     "superlative", "supernatural", "supersensitive", "supersonic", "superstitious", "supple", "supportive", "supposed",
     "suppressive", "supreme", "sure", "sure-footed", "surgical", "surly", "surmountable", "surprised", "surprising",
     "surrealistic", "survivable", "susceptible", "suspected", "suspicious", "sustainable", "swaggering", "swanky",
     "swaying", "sweaty", "sweeping", "sweet", "sweltering", "swift", "swimming", "swinish", "swishing", "swollen",
     "swooping", "syllabic", "syllogistic", "symbiotic", "symbolic", "symmetrical", "sympathetic", "symptomatic",
     "synergistic", "synonymous", "syntactic", "synthetic", "systematic", "taboo", "tacit", "tacky", "tactful",
     "tactical", "tactless", "tactual", "tainted", "take-charge", "talented", "talkative", "tall", "taller", "tallest",
     "tame", "tamed", "tan", "tangential", "tangible", "tangled", "tangy", "tanned", "tantalizing", "tapered", "tardy",
     "targeted", "tarnished", "tart", "tasteful", "tasteless", "tasty", "tattered", "taunting", "taut", "tawdry",
     "taxing", "teachable", "tearful", "tearing", "teasing", "technical", "technological", "tectonic", "tedious",
     "teenage", "teensy", "teeny", "teeny-tiny", "telegraphic", "telekinetic", "telepathic", "telephonic", "telescopic",
     "telling", "temperamental", "temperate", "tempestuous", "temporary", "tempted", "tempting", "ten", "ten-minute",
     "tenable", "tenacious", "tender", "tenderhearted", "tense", "tentative", "tenth", "tenuous", "tepid", "terminal",
     "terrestrial", "terrible", "terrific", "terrified", "terrifying", "territorial", "terse", "tested", "testy",
     "tetchy", "textual", "textural", "thankful", "thankless", "that", "the", "theatrical", "their", "thematic",
     "theological", "theoretical", "therapeutic", "thermal", "these", "thick", "thievish", "thin", "thinkable", "third",
     "thirsty", "this", "thorny", "thorough", "those", "thoughtful", "thoughtless", "thrashed", "threatened",
     "threatening", "three", "thriftless", "thrifty", "thrilled", "thrilling", "throbbing", "thumping", "thundering",
     "thunderous", "ticking", "tickling", "ticklish", "tidal", "tidy", "tight", "tightfisted", "time", "timeless",
     "timely", "timid", "timorous", "tiny", "tipsy", "tired", "tireless", "tiresome", "tiring", "tolerable", "tolerant",
     "tonal", "tone-deaf", "toneless", "toothsome", "toothy", "top", "topical", "topographical", "tormented", "torpid",
     "torrential", "torrid", "torturous", "total", "touched", "touching", "touchy", "tough", "towering", "toxic",
     "traditional", "tragic", "trainable", "trained", "training", "traitorous", "tranquil", "transcendent",
     "transcendental", "transformational", "transformative", "transformed", "transient", "transitional", "transitory",
     "translucent", "transparent", "transplanted", "trapped", "trashed", "trashy", "traumatic", "treacherous",
     "treasonable", "treasonous", "treasured", "treatable", "tremendous", "tremulous", "trenchant", "trendy",
     "triangular", "tribal", "trick", "tricky", "trim", "tripping", "trite", "triumphant", "trivial", "tropical",
     "troubled", "troublesome", "troubling", "truculent", "true", "trusted", "trustful", "trusting", "trustworthy",
     "trusty", "truthful", "trying", "tumultuous", "tuneful", "tuneless", "turbulent", "twinkling", "twinkly",
     "twisted", "twitchy", "two", "typical", "tyrannical", "tyrannous", "ubiquitous", "ugliest", "ugly", "ultimate",
     "ultra", "ultraconservative", "ultrasensitive", "ultrasonic", "ultraviolet", "unabashed", "unabated", "unable",
     "unacceptable", "unaccompanied", "unaccountable", "unaccustomed", "unacknowledged", "unadorned", "unadulterated",
     "unadventurous", "unadvised", "unaffected", "unaffordable", "unafraid", "unaggressive", "unaided", "unalienable",
     "unalterable", "unaltered", "unambiguous", "unanimous", "unannounced", "unanswerable", "unanticipated",
     "unapologetic", "unappealing", "unappetizing", "unappreciative", "unapproachable", "unarmed", "unashamed",
     "unassailable", "unassertive", "unassisted", "unattached", "unattainable", "unattractive", "unauthorized",
     "unavailable", "unavailing", "unavoidable", "unbalanced", "unbearable", "unbeatable", "unbeaten", "unbecoming",
     "unbelievable", "unbelieving", "unbendable", "unbending", "unbiased", "unblemished", "unblinking", "unblushing",
     "unbounded", "unbreakable", "unbridled", "uncanny", "uncaring", "unceasing", "unceremonious", "uncertain",
     "unchangeable", "unchanging", "uncharacteristic", "uncharitable", "uncharted", "uncivil", "uncivilized",
     "unclassified", "unclean", "uncluttered", "uncomely", "uncomfortable", "uncommitted", "uncommon",
     "uncommunicative", "uncomplaining", "uncomprehending", "uncompromising", "unconcerned", "unconditional",
     "unconfirmed", "unconquerable", "unconscionable", "unconscious", "unconstitutional", "unconstrained",
     "unconstructive", "uncontainable", "uncontrollable", "unconventional", "unconvinced", "unconvincing", "uncooked",
     "uncooperative", "uncoordinated", "uncouth", "uncovered", "uncreative", "uncritical", "undamaged", "undated",
     "undaunted", "undeclared", "undefeated", "undefined", "undemocratic", "undeniable", "undependable",
     "underdeveloped", "underfunded", "underhanded", "underprivileged", "understandable", "understanding",
     "understated", "understood", "undeserved", "undesirable", "undetected", "undeterred", "undeveloped", "undeviating",
     "undifferentiated", "undignified", "undiminished", "undiplomatic", "undisciplined", "undiscovered", "undisguised",
     "undisputed", "undistinguished", "undivided", "undoubted", "unearthly", "uneasy", "uneducated", "unemotional",
     "unemployed", "unencumbered", "unending", "unendurable", "unenforceable", "unenthusiastic", "unenviable",
     "unequal", "unequaled", "unequivocal", "unerring", "uneven", "uneventful", "unexceptional", "unexcited",
     "unexpected", "unexplainable", "unexplored", "unexpressive", "unfailing", "unfair", "unfaithful", "unfaltering",
     "unfamiliar", "unfashionable", "unfathomable", "unfavorable", "unfeeling", "unfettered", "unfilled", "unflagging",
     "unflappable", "unflattering", "unflinching", "unfocused", "unforeseeable", "unforgettable", "unforgivable",
     "unforgiving", "unfortunate", "unfriendly", "unfulfilled", "ungallant", "ungenerous", "ungentlemanly",
     "unglamorous", "ungraceful", "ungracious", "ungrateful", "unguarded", "unhandsome", "unhappy", "unharmed",
     "unhealthy", "unheated", "unheeded", "unhelpful", "unhesitating", "unhurried", "uniform", "unilateral",
     "unimaginable", "unimaginative", "unimpeachable", "unimpeded", "unimpressive", "unincorporated", "uninformed",
     "uninhabitable", "uninhibited", "uninitiated", "uninjured", "uninspired", "uninsurable", "unintelligent",
     "unintelligible", "unintended", "unintentional", "uninterested", "uninterrupted", "uninvited", "unique", "united",
     "universal", "unjust", "unjustifiable", "unkempt", "unkind", "unknowing", "unknown", "unlawful", "unlicensed",
     "unlikable", "unlikely", "unlivable", "unloved", "unlucky", "unmanageable", "unmanly", "unmanned", "unmarketable",
     "unmasked", "unmatched", "unmemorable", "unmentionable", "unmerciful", "unmistakable", "unmitigated", "unmodified",
     "unmotivated", "unnatural", "unnecessary", "unnerved", "unnerving", "unnoticeable", "unobserved", "unobtainable",
     "unobtrusive", "unofficial", "unopened", "unopposed", "unorthodox", "unostentatious", "unpalatable",
     "unpardonable", "unpersuasive", "unperturbed", "unplanned", "unpleasant", "unprecedented", "unpredictable",
     "unpretentious", "unprincipled", "unproductive", "unprofessional", "unprofitable", "unpromising",
     "unpronounceable", "unprovoked", "unqualified", "unquantifiable", "unquenchable", "unquestionable", "unquestioned",
     "unquestioning", "unraveled", "unreachable", "unreadable", "unrealistic", "unrealized", "unreasonable",
     "unreceptive", "unrecognizable", "unrecognized", "unredeemable", "unregulated", "unrelenting", "unreliable",
     "unremarkable", "unremitting", "unrepentant", "unrepresentative", "unrepresented", "unreserved", "unrespectable",
     "unresponsive", "unrestrained", "unripe", "unrivaled", "unromantic", "unruffled", "unruly", "unsafe",
     "unsalvageable", "unsatisfactory", "unsatisfied", "unscheduled", "unscholarly", "unscientific", "unscrupulous",
     "unseasonable", "unseemly", "unselfish", "unsettled", "unsettling", "unshakable", "unshapely", "unsightly",
     "unsigned", "unsinkable", "unskilled", "unsociable", "unsolicited", "unsolvable", "unsolved", "unsophisticated",
     "unsound", "unsparing", "unspeakable", "unspoiled", "unstable", "unstated", "unsteady", "unstoppable",
     "unstressed", "unstructured", "unsubstantial", "unsubstantiated", "unsuccessful", "unsuitable", "unsuited",
     "unsupervised", "unsupported", "unsure", "unsurpassable", "unsurpassed", "unsurprising", "unsuspected",
     "unsuspecting", "unsustainable", "unsympathetic", "unsystematic", "untainted", "untamable", "untamed", "untapped",
     "untenable", "untested", "unthinkable", "unthinking", "untidy", "untimely", "untitled", "untouchable",
     "untraditional", "untrained", "untried", "untroubled", "untrustworthy", "untruthful", "unused", "unusual",
     "unverified", "unwary", "unwashed", "unwatchable", "unwavering", "unwholesome", "unwieldy", "unwilling", "unwise",
     "unwitting", "unworkable", "unworldly", "unworthy", "unwritten", "unyielding", "up-to-date", "up-to-the-minute",
     "upbeat", "upmost", "upper", "uppity", "upright", "uproarious", "upset", "upsetting", "upstairs", "uptight",
     "upward", "urbane", "urgent", "usable", "used", "useful", "useless", "usual", "utilitarian", "utopian", "utter",
     "uttermost", "vacant", "vacillating", "vacuous", "vagabond", "vagrant", "vague", "vain", "valiant", "valid",
     "valorous", "valuable", "vanishing", "vapid", "vaporous", "variable", "varied", "various", "varying", "vast",
     "vegetable", "vegetarian", "vegetative", "vehement", "velvety", "venal", "venerable", "vengeful", "venomous",
     "venturesome", "venturous", "veracious", "verbal", "verbose", "verdant", "verifiable", "verified", "veritable",
     "vernacular", "versatile", "versed", "vertical", "very", "vexed", "vexing", "viable", "vibrant", "vibrating",
     "vicarious", "vicious", "victorious", "vigilant", "vigorous", "vile", "villainous", "vindictive", "vinegary",
     "violent", "violet", "viperous", "viral", "virtual", "virtuous", "virulent", "visceral", "viscous", "visible",
     "visionary", "visual", "vital", "vitriolic", "vivacious", "vivid", "vocal", "vocational", "voiceless", "volatile",
     "volcanic", "voluminous", "voluntary", "voluptuous", "voracious", "vulgar", "vulnerable", "wacky", "waggish",
     "wailing", "waiting", "wakeful", "wandering", "wanting", "wanton", "warlike", "warm", "warmest", "warning",
     "warring", "wary", "waspish", "waste", "wasted", "wasteful", "watchful", "waterlogged", "waterproof", "watertight",
     "watery", "wavering", "wax", "waxen", "weak", "weak-willed", "weakened", "wealthy", "wearisome", "weary", "wee",
     "weedy", "week-long", "weekly", "weightless", "weighty", "weird", "welcoming", "well", "well-adjusted",
     "well-argued", "well-aware", "well-balanced", "well-behaved", "well-built", "well-conceived", "well-considered",
     "well-crafted", "well-deserved", "well-developed", "well-done", "well-dressed", "well-educated", "well-equipped",
     "well-established", "well-founded", "well-groomed", "well-heeled", "well-honed", "well-informed",
     "well-intentioned", "well-kempt", "well-known", "well-liked", "well-lit", "well-made", "well-maintained",
     "well-mannered", "well-meaning", "well-off", "well-placed", "well-planned", "well-prepared", "well-qualified",
     "well-read", "well-received", "well-rounded", "well-spoken", "well-suited", "well-thought-of", "well-thought-out",
     "well-to-do", "well-traveled", "well-used", "well-versed", "well-worn", "well-written", "west", "western", "wet",
     "what", "wheezing", "which", "whimpering", "whimsical", "whining", "whispering", "whistling", "white", "whole",
     "wholehearted", "wholesale", "wholesome", "whooping", "whopping", "whose", "wicked", "wide", "wide-eyed",
     "wide-ranging", "widespread", "wiggly", "wild", "willful", "willing", "wily", "windy", "winning", "winsome",
     "winter", "wintery", "wiry", "wise", "wishful", "wispy", "wistful", "withering", "witless", "witty", "wizardly",
     "wobbly", "woebegone", "woeful", "wolfish", "womanly", "wonderful", "wondrous", "wonted", "wood", "wooden",
     "wooing", "wool", "woolen", "woozy", "wordless", "wordy", "work", "work-oriented", "workable", "working",
     "worldly", "worn", "worn down", "worn out", "worried", "worrisome", "worrying", "worse", "worshipful", "worst",
     "worth", "worthless", "worthwhile", "worthy", "wounding", "wrathful", "wrenching", "wretched", "wriggling",
     "wriggly", "wrinkled", "wrinkly", "written", "wrong", "wrongful", "wry", "yawning", "yearly", "yearning", "yellow",
     "yelping", "yielding", "young", "younger", "youngest", "youthful", "yummy", "zany", "zealous", "zestful", "zesty",
     "zippy", "zonked", "zoological"};
    
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

}
