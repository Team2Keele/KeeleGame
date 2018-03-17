/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2.pacman.framework;

import com.team2.pacman.window.Game;
import java.awt.Point;

/**
 *
 * @author w4u50
 */
public class Acorn extends Entity
{
    private int pointValue;
    
    public Acorn(Map map, Point.Float pos, Point size)
    {
        super(map, pos, size); 
        pointValue = 10;
    }
    
    public int getValue()
    {
        return pointValue;
    }
}
