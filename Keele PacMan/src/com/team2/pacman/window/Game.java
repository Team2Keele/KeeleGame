package com.team2.pacman.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    public static final String VERSION = "0.1a";    //Keele PacMan game version
    public GameState state = GameState.START;

    private boolean running = false;    //State of the game thread
    private Thread thread;              //Game thread

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

        g.dispose();
        bs.show();
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
        Window window = new Window(800, 600, "Keele PacMan ver: " + VERSION, new Game());
    }

    public enum GameState {
        START, RUNNING, END
    }
}
