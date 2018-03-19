/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2.pacman.test;

import com.team2.pacman.framework.*;
import com.team2.pacman.test.TestGame;
import com.team2.pacman.window.Game;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author elliott
 */
public class TestMenu {
    private String[] messages;
    private Sprite startSplashScreen;
    private Sprite pausedSplashScreen;
    private Sprite endSplashScreen;
    private Font messageFont;
    private TestGame.GameState currentState;
    
    public TestMenu()
    {
        messages = new String[]{"Press SPACE key to start game, ESC to exit", "", "Press SPACE to resume, ESC to exit", "Press SPACE to restart game, ESC to exit"};
        messageFont = Game.loadFont(50);
        startSplashScreen = new Sprite("startScreen.png", 1000, 1);
        pausedSplashScreen = new Sprite("pausedscreen.png", 1000, 1);
        endSplashScreen = new Sprite("endscreen.png", 1000, 1);
        currentState = TestGame.GameState.START;
    }
    
    public void update(TestGame.GameState state)
    {
        currentState = state;
    }
    
    public void render(Graphics g, TestGame gameInstance)
    {
        switch(currentState)
        {
            case START:
                startSplashScreen.render(g, 0, 0, gameInstance.getWidth(), gameInstance.getHeight());
                break;
            case RUNNING:
                break;
            case PAUSED:
                pausedSplashScreen.render(g, 0, 0, gameInstance.getWidth(), gameInstance.getHeight());
                break;
            case END:
                endSplashScreen.render(g, 0, 0, gameInstance.getWidth(), gameInstance.getHeight());
                break;
        }
    }
}
