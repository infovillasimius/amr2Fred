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
