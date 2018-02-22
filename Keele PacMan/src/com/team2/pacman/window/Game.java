package com.team2.pacman.window;

import java.awt.Canvas;

public class Game extends Canvas implements Runnable {

    public static final String VERSION = "0.1a";    //Keele PacMan game version
    
    private boolean running = false;    //State of the game thread
    private Thread thread;              //Game thread
    
    public synchronized void start(){
        //Stops making multiple threads of one that is already running
        if(running)
            return;
        
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
        double delta = 0 ;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            
            while(delta >= 1){
                update();
                updates++;
                delta--;
            }
            render();
            frames++;
            
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                int fps = frames;
                int ticks = updates;
                System.out.println("FPS: " + fps + "TICKS: " + ticks);
                updates = 0;
                frames = 0;
            }
            
        }
        
    }

    
    private void update(){
    }
    
    private void render(){
        
    }
    
    
    
    public static void main(String args[]) {
        Window window = new Window(800, 600, "Keele PacMan ver: " + VERSION, new Game());
    }

}
