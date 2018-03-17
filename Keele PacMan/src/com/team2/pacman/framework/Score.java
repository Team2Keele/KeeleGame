
package com.team2.pacman.framework;

public class Score {
    
    private int score;
    private float multi;
    
    public Score()
    {
        score = 0;
        multi = 1;
    }
    
    public void increment(int points) {
        score = score + (int)(points * multi);
    }
    
    public void decrement(int points) {
        score = score - points;
    }
    
    public int getScore() {
        return score;
    }
    
   public void setMulti(float setMulti) { //passed the score multiplier, 
       multi = setMulti;    
   }
   
   public void reset() {
       score = 0;    
   }
}
