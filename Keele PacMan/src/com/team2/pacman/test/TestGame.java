package com.team2.pacman.test;

import com.team2.pacman.framework.*;
import com.team2.pacman.framework.Controllable.Direction;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.io.InputStream;

public class TestGame extends Canvas implements Runnable, KeyListener {

    public static final String VERSION = "0.1a";    //Keele PacMan game version
    public GameState state = GameState.START;

    private boolean running = false;    //State of the game thread
    private Thread thread;              //Game thread

    /*Testing map and sprites rendering abilities*/
    private Map gameMap;
    private Sprite testSprite1 = new Sprite("spriteTest.png", 16, 2);
    private Player testP1;
    private Score gameScore;
    private Font scoreFont;
    private Color scoreColor;
    private long scoreTime;

    public TestGame() throws Controllable.InvalidStartTileException
    {       
        this.gameMap = new Map("testmap.txt", "map.png", 100f);
        testP1 = new Player(this, gameMap, gameMap.getTile(4, 4), 0.9f);
        testP1.setSprite(new Sprite("player.png", 16, 1));
        testP1.setSpeed(4);
        gameScore = new Score();  
        loadFont();
        scoreColor = Color.WHITE;
        scoreTime = System.currentTimeMillis();
    }
    
    private void loadFont()
    {
        try
        {
            InputStream is = TestGame.class.getResourceAsStream("../res/" + "pixel_font.ttf");
            scoreFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.BOLD, 25);
        }
        catch(FontFormatException | IOException e)
        {
            System.out.print("Error loading custom font");
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e)
    {
        
    }
    
    @Override
    public void keyPressed(KeyEvent e)
    {
        Direction playerMove = Direction.NONE;
        
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                playerMove = Direction.UP;
                break;
            
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                playerMove = Direction.LEFT;
                break;
                
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                playerMove = Direction.DOWN;
                break;
                
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                playerMove = Direction.RIGHT;
                break;
        }
        
        testP1.turn(playerMove);
    }
    
    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    public synchronized void start() {
        //Stops making multiple threads of one that is already running
        if (running) {
            return;
        }
        //if the game is not already running then start a new thread
        running = true;

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfUpdates = 60.0;
        double ns = 1000000000 / amountOfUpdates;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                int fps = frames;
                int ticks = updates;
                System.out.println("FPS: " + fps + " TICKS: " + ticks);
                updates = 0;
                frames = 0;
            }
        }
    }

    private void update() {

        //TODO handle the changes of game states and the updates of every tick of each entity and collision
        switch (state) {
            case START: //handle the start of the game / menu stuff
                testP1.update();
                if(System.currentTimeMillis() > scoreTime + 300)
                {
                    gameScore.increment(1);
                    scoreTime = System.currentTimeMillis();
                }
                break;
            case RUNNING: //normal game loop
                break;
            case END: //handle the finishing of the game / win or lose.
                break;
            default:
                break;
        }
    }

    private void render() {

        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        //Draw Everything here
        g.setColor(Color.black);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        //draw map
        gameMap.render(g);
        testP1.render(g);
        
        //draw Score
        g.setColor(scoreColor);
        g.setFont(scoreFont);
        g.drawString(gameScore.getScore() + "", 20, 40);

        g.dispose();
        bs.show();
    }
    
    public Map getMapInstance()
    {
        return gameMap;
    }
    
    public void incrementScore(int inc)
    {
        gameScore.increment(inc);
    }
    
    public int getCurrentScore()
    {
        return gameScore.getScore();
    }

    public void reset() {
        //TODO handle all value resets here: player lives, score, enemy, map, timer, powerups... everything.

        //set the game state to start again
        changeState(GameState.START);
    }

    public void changeState(GameState newState) {
        state = newState;
    }

    public static void main(String args[]) {
        try
        {
            TestGame game = new TestGame();
            int windowSizeX = (int)(game.gameMap.getGridSize().x * game.gameMap.getTileSize());
            int windowSizeY = (int)(game.gameMap.getGridSize().y * game.gameMap.getTileSize());
            TestWindow window = new TestWindow(windowSizeX, windowSizeY, "Keele PacMan ver: " + VERSION, game);
        }
        catch(Controllable.InvalidStartTileException ex)
        {
            System.out.print("ERROR: " + ex.getMessage() + "\n");
        }
    }

    public enum GameState {
        START, RUNNING, END
    }
}
