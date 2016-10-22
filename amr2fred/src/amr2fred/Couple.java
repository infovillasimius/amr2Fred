
package amr2fred;

/**
 * Classe utilizzata nella traduzione da amr a fred per il calcolo
 * del numero di occorrenze di una stringa (var). Accoppia ad ogni
 * stringa il numero di occorrenze registrate
 * @author anto
 */
public class Couple {
    private int occurence;
    private String word;

    public Couple(int occurence, String word) {
        this.occurence = occurence;
        this.word = word;
    }

    public int getOccurence() {
        return occurence;
    }

    public void setOccurence(int occurence) {
        this.occurence = occurence;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
    
    
}
