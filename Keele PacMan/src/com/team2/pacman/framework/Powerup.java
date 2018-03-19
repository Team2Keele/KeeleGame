/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2.pacman.framework;

import java.awt.Point;
import java.util.Random;

public class Powerup extends Entity
{
    private long powerTime;
    private int timerLength;
    private PowerType type;
    
    public static enum PowerType 
    {
        NONE, SPEED, SLOW, ENEMY_SLOW, MULTIPLIER, ENEMY_VULNERABLE;
    
        public static PowerType getRandomPower()
        {
            Random random = new Random();
            return values()[random.nextInt(values().length - 1) + 1];
        }
    }
    
    public Powerup(Map map, Point.Float pos, Point size)
    {
        super(map, pos, size);
        type = PowerType.getRandomPower();
        powerTime = -1;
        timerLength = 10000;
    }
    
    public Powerup(Map map, Point.Float pos, Point size, PowerType type)
    {
        super(map, pos, size);
        this.type = type;
        powerTime = -1;
        timerLength = 10000;
    }
    
    public boolean timerDone()
    {
        if(powerTime == -1)
        {
            powerTime = System.currentTimeMillis() + timerLength;
        }
        
        if(System.currentTimeMillis() > powerTime)
        {
            deactivate();
            return true;
        }
        
        return false;
    }
    
    public PowerType getType()
    {
        return type;
    }
}
