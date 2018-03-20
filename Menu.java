
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Adam
 */
public class Menu extends Canvas{
    
    class MenuKeyPressed implements KeyListener{
        public void keyPressed(KeyEvent event){
            if (event.getKeyCode() == KeyEvent.VK_ENTER){
                startGame();
            }
            if (event.getKeyCode() == KeyEvent.VK_P){
                pauseGame();
            }
            if (event.getKeyCode() == KeyEvent.VK_ESCAPE){
                exitGame();
            }
            
        }
        public void keyReleased(KeyEvent event){
            
        }
        public void keyTyped(KeyEvent event){
            
        }

       
    }
    
    //public GameState state = GameState.START;
    //Game gameObj = new Game();
    
    public void update(){
        
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
    public void startGame(){
        state=GameState.RUNNING;
        update();
    }
    public void gameOver(){
        state=GameState.END;
        update();
    }
    public void pauseGame(){
        state=GameState.START;
    }
    public void exitGame(){
        state=GameState.END;
        update();
    }
    public void changeMessage(){
        
    }
    public void render(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.WHITE);
        g.drawString("Press Enter to Start Game", this.getWidth()/2, this.getHeight()/2);
        g.drawString("or press ESC to exit game", this.getWidth()/2, this.getHeight()/2 + 30);
        g.dispose();
    }
}
