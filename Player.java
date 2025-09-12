import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Rectangle;
import javax.swing.Timer;
/**
 * represents the player which can move and shoot
 * track the position from key inputs
 * handle firing bullets when spacebar is pressed
 * draw player and player's bullets
 * 
 * @Gabriel Faigan
 * @Nov-Dec, 2024
 */
public class Player extends JPanel implements KeyListener{
    int x, y; // player position
    int velX = 4; // player moving speed
    // int bulletSpeed = 10; // player bullet speed
    int bulletSpeed = 13;
    boolean movingLeft = false, movingRight = false; // for moving logic
    boolean shooting = false; // for shooting logic
    ArrayList<Bullet> b = new ArrayList<>(); // list of bullets currently active
    int totalBullets; // total bullets allowed in a level
    Timer shootTimer; // shooting timer
    public Player(){
        x = 250; // starting position on lvl 1
        y = 830;
        
        // shooting timer for holding down spacebar
        shootTimer = new Timer(100, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(shooting && b.size() < totalBullets){
                    Sound.playSound("SHOOT.wav");
                    b.add(new Bullet(x+17, y, bulletSpeed));
                }
            }
        });
        shootTimer.start();
    }
    
    // when player presses a key
    public void keyPressed (KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) movingLeft=true;
        if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) movingRight=true;
        if(key == KeyEvent.VK_SPACE){
            if (!shooting) {
                shooting = true;
                if(b.size() < totalBullets){
                    Sound.playSound("SHOOT.wav");
                    b.add(new Bullet(x+17, y, bulletSpeed)); // fire the first bullet immediately
                }
                shootTimer.start(); // start the timer for subsequent bullets
            }
        }
    }
    
    // when player releases a key
    public void keyReleased (KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) movingLeft=false;
        if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) movingRight=false;
        if(key == KeyEvent.VK_SPACE){ 
            shooting=false;
            shootTimer.stop();
        }
    }
    
    // updating player position, bullets, and graphics
    public void draw(Graphics g){
        g.fillRect(x,y,50,20);
        if(movingLeft==true && x>50) x-=velX;
        if(movingRight==true && x<500) x+=velX;
        
        Iterator<Bullet> i = b.iterator();
        while(i.hasNext()){
            Bullet b = i.next();
            b.draw(g);
            if(b.y<0){
                i.remove();
            }
        }
    }
    
    // collision detection between player and enemy bullet
    public boolean isHit(Bullet b){
        Rectangle player = new Rectangle(x,y,50,20);
        Rectangle bullet = new Rectangle(b.x,b.y,10,20);
        return player.intersects(bullet);
    }
    
    public void keyTyped (KeyEvent e){}
}