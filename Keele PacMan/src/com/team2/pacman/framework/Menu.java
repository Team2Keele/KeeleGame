/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team2.pacman.framework;

import com.team2.pacman.test.TestGame;
import com.team2.pacman.window.Game;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author elliott
 */
public class Menu {

    String startString;
    String exitString;
    String restartString;
    String resumeString;
    private Sprite startSplashScreen;
    private Sprite pausedSplashScreen;
    private Sprite endSplashScreen;
    private Font messageFont;
    private Game.GameState currentState;
    private Game gameInst;

    public Menu(Game game) {
        messageFont = Game.loadFont(25);
        startString = "[SPACE] START GAME";
        exitString = "[ESC] EXIT";
        restartString = "[SPACE] RESTART";
        resumeString = "[SPACE] RESUME";
        startSplashScreen = new Sprite("startScreen.png", 1000, 1, 0);
        pausedSplashScreen = new Sprite("pausedscreen.png", 1000, 1, 0);
        endSplashScreen = new Sprite("endscreen.png", 1000, 1, 0);
        currentState = Game.GameState.START;
        gameInst = game;
    }

    public void update(Game.GameState state) {
        currentState = state;
    }

    public void render(Graphics g, Game gameInstance) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setFont(messageFont);
        String scoreString = "YOUR SCORE: " + gameInst.getCurrentScore();
        Rectangle2D scoreBounds = messageFont.getStringBounds(scoreString, g2.getFontRenderContext());
        
        switch (currentState) {
            case START:
                startSplashScreen.render(g2, 0, 0, gameInstance.getWidth(), gameInstance.getHeight());      
                drawOptions(g2, startString, exitString);
                break;
            case RUNNING:
                break;
            case PAUSED:
                pausedSplashScreen.render(g2, 0, 0, gameInstance.getWidth(), gameInstance.getHeight());
                drawOptions(g2, resumeString, exitString);
                break;
            case END:
                endSplashScreen.render(g2, 0, 0, gameInstance.getWidth(), gameInstance.getHeight());
                drawOptions(g2, restartString, exitString);
                break;
        }
        
        if(currentState != Game.GameState.RUNNING)
        {
            g2.drawString(scoreString, (gameInst.getWidth() / 2) - (int)(scoreBounds.getWidth() / 2), (int)(gameInst.getHeight() * 0.9));
        }
    }
    
    private void drawOptions(Graphics2D g2, String str1, String str2)
    {   
        FontRenderContext frc = g2.getFontRenderContext();
        Rectangle2D bounds1 = messageFont.getStringBounds(str1, frc);
        Rectangle2D bounds2 = messageFont.getStringBounds(str2, frc);
        int middleX = gameInst.getWidth() / 2;
        int middleY = gameInst.getHeight() / 2;
        
        g2.drawString(str1, middleX - (int)(bounds1.getWidth() / 2), middleY - (int)(bounds1.getHeight() / 2));
        g2.drawString(exitString, middleX - (int)(bounds2.getWidth() / 2), middleY - (int)(bounds2.getHeight() / 2) + (int)(bounds2.getHeight() * 1.5));
    }

    public void update(TestGame.GameState state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
