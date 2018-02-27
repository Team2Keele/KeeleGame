/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2.pacman.framework;

public class Score {
    
    protected int score = 0;
    protected float multi;
    
    private void increment(int points) {
        score = score + points;

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
