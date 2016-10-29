
package amr2fred;

/**
 * Classe utilizzata nella traduzione da amr a fred per il calcolo
 * del numero di occorrenze di una stringa (var). Accoppia ad ogni
 * stringa il numero di occorrenze registrate
 * @author anto
 */
class Couple {
    private int occurrence;
    private String word;

    public Couple(int occurence, String word) {
        this.occurrence = occurence;
        this.word = word;
    }

    public int getOccurence() {
        return occurrence;
    }

    public void setOccurence(int occurence) {
        this.occurrence = occurence;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "\nWord: "+word+" - occurrence: "+occurrence;
    }
    
    
    
    
}
