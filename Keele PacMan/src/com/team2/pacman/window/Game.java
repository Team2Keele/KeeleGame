package com.team2.pacman.window;

import com.team2.pacman.framework.*;
import com.team2.pacman.test.TestGame;
import static com.team2.pacman.test.TestGame.VERSION;
import com.team2.pacman.test.TestWindow;
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

public class Game extends Canvas implements Runnable, KeyListener {

    public static final String VERSION = "0.1a";    //Keele PacMan game version
    public GameState state = GameState.START;

    private boolean running = false;    //State of the game thread
    private Thread thread;              //Game thread
    private Map gameMap;
    private Score gameScore;
    private Menu gameMenu;
    private Font scoreFont;
    private Color scoreColor;
    private long scoreTime;
    private Player gamePlayer;
    private Enemy[] enemies;
    private float playerSpeed;
    private float enemySpeed;

    public Game(int windowX, int windowY) {
        initialize(windowX, windowY);
    }
    
    private void initialize(int windowX, int windowY)
    {
        try
        {
            this.gameMap = new Map("testmap.txt", "map.png", windowX, windowY);
            playerSpeed = (gameMap.getTileSize().x * gameMap.getTileSize().y) / 500;
            enemySpeed = playerSpeed * 0.9f;
            gamePlayer = new Player(this, gameMap.getTile(12, 8), 0.9f);
            gamePlayer.setSprite(new Sprite("player.png", 16, 1));
            gamePlayer.setSpeed(playerSpeed);
            gameScore = new Score();  
            scoreFont = loadFont(25);
            scoreColor = Color.WHITE;
            scoreTime = System.currentTimeMillis();
            gameMenu = new Menu();
            enemies = new Enemy[]{new Enemy(gameMap, gamePlayer, gameMap.getTile(4, 15), 0.9f),
                                new Enemy(gameMap, gamePlayer, gameMap.getTile(20, 15), 0.9f), 
                                new Enemy(gameMap, gamePlayer, gameMap.getTile(4, 20), 0.9f), 
                                new Enemy(gameMap, gamePlayer, gameMap.getTile(20, 20), 0.9f)};
            
            for(int i = 0; i < enemies.length; i++)
            {
                enemies[i].setSpeed(enemySpeed);
                enemies[i].setVulnerable();
            }
        }
        catch(Controllable.InvalidStartTileException e)
        {
            System.out.print("ERROR: " + e.getMessage() + "\n");
            System.exit(-1);
        }
    }
    
    public static Font loadFont(int fontSize)
    {
        try
        {
            InputStream is = TestGame.class.getResourceAsStream("../res/" + "pixel_font.ttf");
            return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.BOLD, fontSize);
        }
        catch(FontFormatException | IOException e)
        {
            System.out.print("Error loading custom font");
            return new Font("Times New Roman", Font.BOLD, fontSize);
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        
    }
    
    @Override
    public void keyPressed(KeyEvent e) 
    {
        Controllable.Direction playerMove = Controllable.Direction.NONE;
        
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_SPACE:
                switch(state)
                {
                    case START:
                        state = GameState.RUNNING;
                        break;
                    case RUNNING:
                        state = GameState.PAUSED;
                        break;
                    case PAUSED:
                        state = GameState.RUNNING;
                        break;
                    case END:
                        reset();
                        break;
                }
                break;
            case KeyEvent.VK_ESCAPE:
                if(state == GameState.PAUSED || state == GameState.START || state == GameState.END)
                {
                    System.exit(0);
                }
                break;
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                playerMove = Controllable.Direction.UP;
                break;
            
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                playerMove = Controllable.Direction.LEFT;
                break;
                
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                playerMove = Controllable.Direction.DOWN;
                break;
                
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                playerMove = Controllable.Direction.RIGHT;
                break;
        }
        
        gamePlayer.turn(playerMove);
    }
    
    @Override
    public void keyTyped(KeyEvent e)
    {

    }
    
    public void endGame()
    {
        state = GameState.END;
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
                gameMenu.update(state);
                break;
            case RUNNING: //normal game loop
                gameMenu.update(state);
                gamePlayer.update();
                
                for(int i = 0; i < enemies.length; i++)
                {
                    enemies[i].update();
                }
                
                if(System.currentTimeMillis() > scoreTime + 300)
                {
                    gameScore.increment(1);
                    scoreTime = System.currentTimeMillis();
                }
                break;
            case PAUSED:
                gameMenu.update(state);
                break;
            case END: //handle the finishing of the game / win or lose.
                gameMenu.update(state);
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
        
        //draw player and enemies
        gamePlayer.render(g);
        for(int i = 0; i < enemies.length; i++)
        {
            enemies[i].render(g);
        }
        
        //draw Score
        g.setColor(scoreColor);
        g.setFont(scoreFont);
        g.drawString(gameScore.getScore() + "", 20, 40);
        
        //render Menu
        gameMenu.render(g, this);
        
        g.dispose();
        bs.show();
    }
    
    public Enemy[] getEnemies()
    {
        return enemies;
    }
    
    public Map getMapInstance()
    {
        return gameMap;
    }
    
    public int getHighScore()
    {
        return 0;
    }
    
    public void incrementScore(int inc)
    {
        gameScore.increment(inc);
    }
    
    public int getCurrentScore()
    {
        return gameScore.getScore();
    }
    
    public void setScoreMult(float multiplier)
    {
        gameScore.setMulti(multiplier);
    }
    
    public void setEnemiesVulnerable()
    {
        for(int i = 0; i < enemies.length; i++)
        {
            enemies[i].setVulnerable();
        }
    }
    
    public void setEnemiesInvulnerable()
    {
        for(int i = 0; i < enemies.length; i++)
        {
            enemies[i].setInvulnerable();
        }
    }
    
    public void setEnemySpeedMult(float mult)
    {
        for(int i = 0; i < enemies.length; i++)
        {
            enemies[i].setSpeedMult(mult);
        }
    }

    public void reset() {
        initialize(getWidth(), getHeight());

        //set the game state to start again
        state = GameState.START;
    }
    
    public static void main(String args[]) {

        int windowSizeX = 1000;
        int windowSizeY = 1000;
        Game game = new Game(windowSizeX, windowSizeY);
        
        Window window = new Window(windowSizeX, windowSizeY, "Keele PacMan ver: " + VERSION, game);

    }

    public enum GameState {
        START, RUNNING, PAUSED, END;
    }
}
