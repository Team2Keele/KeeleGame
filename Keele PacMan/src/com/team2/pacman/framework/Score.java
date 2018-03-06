
package com.team2.pacman.framework;

public class Score {
    
    protected int score;
    protected float multi;
    
    public Score()
    {
        score = 0;
        multi = 1;
    }
    
    private void increment(int points) {
        score = score + (int)(points * multi);
    }
    
    private void decrement(int points) {
        score = score - points;
    
    }
    
    private int getScore() {
        return score;
    }
    
   private void setMulti(float setMulti) { //passed the score multiplier, 
       multi = setMulti;    
   }
   
   private void reset() {
       score = 0;    
   }
}
