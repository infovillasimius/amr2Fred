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
    public static final String BOXING_HAS_TRUTH_VALUE = "boxing:hasTruthValue";
    public static final String BOXING_UNKNOWN = "boxing:Unknown";

    /**
     * Local name for quant
     */
    public static final String QUANT = "quant:";
    
    public static final String QUANT_EVERY=QUANT+"every";

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
    public static final String VN_ROLE_INSTRUMENT = VN_ROLE + "Instrument";
    public static final String VN_ROLE_CAUSE = VN_ROLE + "Cause";
    public static final String VN_ROLE_EXPERIENCER = VN_ROLE + "Expreriencer";
    public static final String VN_ROLE_THEME = VN_ROLE + "Theme";

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

    //Stringhe pattern AMR tradotti   
    public static final String AMR_POLARITY = ":polarity";
    public static final String AMR_MINUS = "-";
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
    
    public static final String AMR_ENTITY="-entity";

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
    public static final String DIGRAPH_INI="digraph {\n charset=\"utf-8\"; \n";
    public static final String DIGRAPH_END="}";
    
    

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
        VN_ROLE_LOCATION, DUL_HAS_QUALITY, VN_ROLE_BENEFICIARY, FRED_WITH, VN_ROLE_BENEFICIARY, VN_ROLE_TIME,
        VN_ROLE_INSTRUMENT, DUL_HAS_QUALITY, FRED_FOR, VN_ROLE_CAUSE, FRED_LIKE, VN_ROLE_LOCATION, FRED_ALTHOUGH,
        FRED_IN, DUL_HAS_QUALITY, FRED_IN, FRED_INCLUDE, FRED_OF, DUL_ASSOCIATED_WITH, FRED_WITH};

    public static final String FRED_VARS[] = {"", BOXING_FALSE, "", "", "", "", "", "", "", "",
        "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};

    public static final int PATTERNS_NUMBER = 27;

    public static final String QUOTE = "\"";

    public static final String ADJECTIVE[] = {"aback", "abaft", "abandoned", "abashed", "aberrant",
        "abhorrent", "abiding", "abject", "ablaze", "able", "abnormal", "aboard", "aboriginal",
        "abortive", "abounding", "abrasive", "abrupt", "absent", "absorbed", "absorbing",
        "abstracted", "absurd", "abundant", "abusive", "acceptable", "accessible",
        "accidental", "accurate", "acid", "acidic", "acoustic", "acrid", "actually",
        "ad", "hoc", "adamant", "adaptable", "addicted", "adhesive", "adjoining",
        "adorable", "adventurous", "afraid", "aggressive", "agonizing", "agreeable",
        "ahead", "ajar", "alcoholic", "alert", "alike", "alive", "alleged", "alluring",
        "aloof", "amazing", "ambiguous", "ambitious", "amuck", "amused", "amusing",
        "ancient", "angry", "animated", "annoyed", "annoying", "anxious", "apathetic",
        "aquatic", "aromatic", "arrogant", "ashamed", "aspiring", "assorted", "astonishing",
        "attractive", "auspicious", "automatic", "available", "average", "awake",
        "aware", "awesome", "awful", "axiomatic", "bad", "barbarous", "bashful",
        "bawdy", "beautiful", "befitting", "belligerent", "beneficial", "bent",
        "berserk", "best", "better", "bewildered", "big", "billowy", "bite-sized",
        "bitter", "bizarre", "black", "black-and-white", "bloody", "blue", "blue-eyed",
        "blushing", "boiling", "boorish", "bored", "boring", "bouncy", "boundless",
        "brainy", "brash", "brave", "brawny", "breakable", "breezy", "brief", "bright",
        "bright", "broad", "broken", "brown", "bumpy", "burly", "bustling", "busy",
        "cagey", "calculating", "callous", "calm", "capable", "capricious", "careful",
        "careless", "caring", "cautious", "ceaseless", "certain", "changeable",
        "charming", "cheap", "cheerful", "chemical", "chief", "childlike", "chilly",
        "chivalrous", "chubby", "chunky", "clammy", "classy", "clean", "clear",
        "clever", "cloistered", "cloudy", "closed", "clumsy", "cluttered", "coherent",
        "cold", "colorful", "colossal", "combative", "comfortable", "common", "complete",
        "complex", "concerned", "condemned", "confused", "conscious", "cooing", "cool",
        "cooperative", "coordinated", "courageous", "cowardly", "crabby", "craven",
        "crazy", "creepy", "crooked", "crowded", "cruel", "cuddly", "cultured",
        "cumbersome", "curious", "curly", "curved", "curvy", "cut", "cute", "cute",
        "cynical", "daffy", "daily", "damaged", "damaging", "damp", "dangerous",
        "dapper", "dark", "dashing", "dazzling", "dead", "deadpan", "deafening",
        "dear", "debonair", "decisive", "decorous", "deep", "deeply", "defeated",
        "defective", "defiant", "delicate", "delicious", "delightful", "demonic",
        "delirious", "dependent", "depressed", "deranged", "descriptive", "deserted",
        "detailed", "determined", "devilish", "didactic", "different", "difficult",
        "diligent", "direful", "dirty", "disagreeable", "disastrous", "discreet",
        "disgusted", "disgusting", "disillusioned", "dispensable", "distinct",
        "disturbed", "divergent", "dizzy", "domineering", "doubtful", "drab",
        "draconian", "dramatic", "dreary", "drunk", "dry", "dull", "dusty", "dusty",
        "dynamic", "dysfunctional", "eager", "early", "earsplitting", "earthy",
        "easy", "eatable", "economic", "educated", "efficacious", "efficient",
        "eight", "elastic", "elated", "elderly", "electric", "elegant", "elfin",
        "elite", "embarrassed", "eminent", "empty", "enchanted", "enchanting",
        "encouraging", "endurable", "energetic", "enormous", "entertaining",
        "enthusiastic", "envious", "equable", "equal", "erect", "erratic",
        "ethereal", "evanescent", "evasive", "even", "excellent", "excited",
        "exciting", "exclusive", "exotic", "expensive", "extra-large", "extra-small",
        "exuberant", "exultant", "fabulous", "faded", "faint", "fair", "faithful",
        "fallacious", "false", "familiar", "famous", "fanatical", "fancy", "fantastic",
        "far", "far-flung", "fascinated", "fast", "fat", "faulty", "fearful", "fearless",
        "feeble", "feigned", "female", "fertile", "festive", "few", "fierce", "filthy",
        "fine", "finicky", "first", "five", "fixed", "flagrant", "flaky", "flashy",
        "flat", "flawless", "flimsy", "flippant", "flowery", "fluffy", "fluttering",
        "foamy", "foolish", "foregoing", "forgetful", "fortunate", "four", "frail",
        "fragile", "frantic", "free", "freezing", "frequent", "fresh", "fretful",
        "friendly", "frightened", "frightening", "full", "fumbling", "functional",
        "funny", "furry", "furtive", "future", "futuristic", "fuzzy", "gabby",
        "gainful", "gamy", "gaping", "garrulous", "gaudy", "general", "gentle",
        "giant", "giddy", "gifted", "gigantic", "glamorous", "gleaming", "glib",
        "glistening", "glorious", "glossy", "godly", "good", "goofy", "gorgeous",
        "graceful", "grandiose", "grateful", "gratis", "gray", "greasy", "great",
        "greedy", "green", "grey", "grieving", "groovy", "grotesque", "grouchy",
        "grubby", "gruesome", "grumpy", "guarded", "guiltless", "gullible", "gusty",
        "guttural", "habitual", "half", "hallowed", "halting", "handsome", "handsomely",
        "handy", "hanging", "hapless", "happy", "hard", "hard-to-find", "harmonious",
        "harsh", "hateful", "heady", "healthy", "heartbreaking", "heavenly", "heavy",
        "hellish", "helpful", "helpless", "hesitant", "hideous", "high", "highfalutin",
        "high-pitched", "hilarious", "hissing", "historical", "holistic", "hollow",
        "homeless", "homely", "honorable", "horrible", "hospitable", "hot", "huge",
        "hulking", "humdrum", "humorous", "hungry", "hurried", "hurt", "hushed",
        "husky", "hypnotic", "hysterical", "icky", "icy", "idiotic", "ignorant",
        "ill", "illegal", "ill-fated", "ill-informed", "illustrious", "imaginary",
        "immense", "imminent", "impartial", "imperfect", "impolite", "important",
        "imported", "impossible", "incandescent", "incompetent", "inconclusive",
        "industrious", "incredible", "inexpensive", "infamous", "innate", "innocent",
        "inquisitive", "insidious", "instinctive", "intelligent", "interesting",
        "internal", "invincible", "irate", "irritating", "itchy", "jaded", "jagged",
        "jazzy", "jealous", "jittery", "jobless", "jolly", "joyous", "judicious",
        "juicy", "jumbled", "jumpy", "juvenile", "kaput", "keen", "kind", "kindhearted",
        "kindly", "knotty", "knowing", "knowledgeable", "known", "labored", "lackadaisical",
        "lacking", "lame", "lamentable", "languid", "large", "last", "late", "laughable",
        "lavish", "lazy", "lean", "learned", "left", "legal", "lethal", "level", "lewd",
        "light", "like", "likeable", "limping", "literate", "little", "lively", "lively",
        "living", "lonely", "long", "longing", "long-term", "loose", "lopsided", "loud",
        "loutish", "lovely", "loving", "low", "lowly", "lucky", "ludicrous", "lumpy",
        "lush", "luxuriant", "lying", "lyrical", "macabre", "macho", "maddening",
        "madly", "magenta", "magical", "magnificent", "majestic", "makeshift", "male",
        "malicious", "mammoth", "maniacal", "many", "marked", "massive", "married",
        "marvelous", "material", "materialistic", "mature", "mean", "measly", "meaty",
        "medical", "meek", "mellow", "melodic", "melodious", "melted", "merciful", "mere", "messy",
        "mighty", "military", "milky", "mindless", "miniature", "minor", "miscreant",
        "misty", "mixed", "moaning", "modern", "moldy", "momentous", "motionless",
        "mountainous", "muddled", "mundane", "murky", "mushy", "mute", "mysterious",
        "naive", "nappy", "narrow", "nasty", "natural", "naughty", "nauseating", "near",
        "neat", "nebulous", "necessary", "needless", "needy", "neighborly", "nervous",
        "new", "next", "nice", "nifty", "nimble", "nine", "nippy", "noiseless", "noisy",
        "nonchalant", "nondescript", "nonstop", "normal", "nostalgic", "nosy", "noxious",
        "null", "numberless", "numerous", "nutritious", "nutty", "oafish", "obedient",
        "obeisant", "obese", "obnoxious", "obscene", "obsequious", "observant", "obsolete",
        "obtainable", "oceanic", "odd", "offbeat", "old", "old-fashioned", "omniscient",
        "one", "onerous", "open", "opposite", "optimal", "orange", "ordinary", "organic",
        "ossified", "outgoing", "outrageous", "outstanding", "oval", "overconfident",
        "overjoyed", "overrated", "overt", "overwrought", "painful", "painstaking",
        "pale", "paltry", "panicky", "panoramic", "parallel", "parched", "parsimonious",
        "past", "pastoral", "pathetic", "peaceful", "penitent", "perfect", "periodic",
        "permissible", "perpetual", "petite", "petite", "phobic", "physical", "picayune",
        "pink", "piquant", "placid", "plain", "plant", "plastic", "plausible", "pleasant",
        "plucky", "pointless", "poised", "polite", "political", "poor", "possessive",
        "possible", "powerful", "precious", "premium", "present", "pretty", "previous",
        "pricey", "prickly", "private", "probable", "productive", "profuse", "protective",
        "proud", "psychedelic", "psychotic", "public", "puffy", "pumped", "puny",
        "purple", "purring", "pushy", "puzzled", "puzzling", "quack", "quaint", "quarrelsome",
        "questionable", "quick", "quickest", "quiet", "quirky", "quixotic", "quizzical",
        "rabid", "racial", "ragged", "rainy", "rambunctious", "rampant", "rapid",
        "rare", "raspy", "ratty", "ready", "real", "rebel", "receptive", "recondite",
        "red", "redundant", "reflective", "regular", "relieved", "remarkable", "reminiscent",
        "repulsive", "resolute", "resonant", "responsible", "rhetorical", "rich",
        "right", "righteous", "rightful", "rigid", "ripe", "ritzy", "roasted", "robust",
        "romantic", "roomy", "rotten", "rough", "round", "royal", "ruddy", "rude",
        "rural", "rustic", "ruthless", "sable", "sad", "safe", "salty", "same", "sassy",
        "satisfying", "savory", "scandalous", "scarce", "scared", "scary", "scattered",
        "scientific", "scintillating", "scrawny", "screeching", "second", "second-hand",
        "secret", "secretive", "sedate", "seemly", "selective", "selfish", "separate",
        "serious", "shaggy", "shaky", "shallow", "sharp", "shiny", "shivering", "shocking",
        "short", "shrill", "shut", "shy", "sick", "silent", "silent", "silky", "silly",
        "simple", "simplistic", "sincere", "six", "skillful", "skinny", "sleepy", "slim",
        "slimy", "slippery", "sloppy", "slow", "small", "smart", "smelly", "smiling",
        "smoggy", "smooth", "sneaky", "snobbish", "snotty", "soft", "soggy", "solid",
        "somber", "sophisticated", "sordid", "sore", "sore", "sour", "sparkling",
        "special", "spectacular", "spicy", "spiffy", "spiky", "spiritual", "spiteful",
        "splendid", "spooky", "spotless", "spotted", "spotty", "spurious", "squalid",
        "square", "squealing", "squeamish", "staking", "stale", "standing", "statuesque",
        "steadfast", "steady", "steep", "stereotyped", "sticky", "stiff", "stimulating",
        "stingy", "stormy", "straight", "strange", "striped", "strong", "stupendous",
        "stupid", "sturdy", "subdued", "subsequent", "substantial", "successful",
        "succinct", "sudden", "sulky", "super", "superb", "superficial", "supreme",
        "swanky", "sweet", "sweltering", "swift", "symptomatic", "synonymous", "taboo",
        "tacit", "tacky", "talented", "tall", "tame", "tan", "tangible", "tangy",
        "tart", "tasteful", "tasteless", "tasty", "tawdry", "tearful", "tedious",
        "teeny", "teeny-tiny", "telling", "temporary", "ten", "tender", "tense",
        "tense", "tenuous", "terrible", "terrific", "tested", "testy", "thankful",
        "therapeutic", "thick", "thin", "thinkable", "third", "thirsty", "thirsty",
        "thoughtful", "thoughtless", "threatening", "three", "thundering", "tidy",
        "tight", "tightfisted", "tiny", "tired", "tiresome", "toothsome", "torpid",
        "tough", "towering", "tranquil", "trashy", "tremendous", "tricky", "trite",
        "troubled", "truculent", "true", "truthful", "two", "typical", "ubiquitous",
        "ugliest", "ugly", "ultra", "unable", "unaccountable", "unadvised", "unarmed",
        "unbecoming", "unbiased", "uncovered", "understood", "undesirable", "unequal",
        "unequaled", "uneven", "unhealthy", "uninterested", "unique", "unkempt", "unknown",
        "unnatural", "unruly", "unsightly", "unsuitable", "untidy", "unused", "unusual",
        "unwieldy", "unwritten", "upbeat", "uppity", "upset", "uptight", "used", "useful",
        "useless", "utopian", "utter", "uttermost", "vacuous", "vagabond", "vague",
        "valuable", "various", "vast", "vengeful", "venomous", "verdant", "versed",
        "victorious", "vigorous", "violent", "violet", "vivacious", "voiceless",
        "volatile", "voracious", "vulgar", "wacky", "waggish", "waiting", "wakeful",
        "wandering", "wanting", "warlike", "warm", "wary", "wasteful", "watery",
        "weak", "wealthy", "weary", "well-groomed", "well-made", "well-off", "well-to-do",
        "wet", "whimsical", "whispering", "white", "whole", "wholesale", "wicked",
        "wide", "wide-eyed", "wiggly", "wild", "willing", "windy", "wiry", "wise",
        "wistful", "witty", "woebegone", "womanly", "wonderful", "wooden", "woozy",
        "workable", "worried", "worthless", "wrathful", "wretched", "wrong", "wry"};

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
     * Jena's writers output modes
     */
    public enum RdfWriteMode {
        RDF_XML, RDF_XML_ABBREV, N_TRIPLES, TURTLE
    }

    private Glossary() {
    }

}
