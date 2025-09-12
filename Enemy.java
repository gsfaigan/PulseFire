import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Rectangle;
/**
 * handles enemy logic, drawing and updating enemy position
 * enemy position, enemy speed x, enemy bullet speed, enemy shoot frequency, hits needed to kill enemy and also how big it is
 *
 * @Gabriel Faigan
 * @Nov 2024
 */
public class Enemy extends JPanel{
    int x, y; // enemy position
    int velX, velY; // enemy moving speed and bullet speed
    int hitCount=0; // how many times has player damaged enemy
    int shootInterval; // how fast does the enemy shoot
    int hits; // how many hits does it take to kill the enemy
    ArrayList<Bullet> b = new ArrayList<>(); // list of currently active bullets
    final Color red = new Color(200,0,0); // colour of enemies
    public Enemy(int x, int y, int velX, int velY, int shootInterval, int hits){
        this.x = x;
        this.y = y;
        this.velX = velX;
        this.shootInterval = shootInterval;
        this.velY = velY;
        this.hits = hits;
    }
    
    // drawing and updating enemy position and firing
    public void draw(Graphics g){
        x+=velX;
        
        // when it hits walls turn around and move down a bit
        if(x<10 || x>500){
            velX*=-1;
            y+=10;
        }

        g.setColor(red);
        g.fillRect(x,y, 40*hits-hitCount*10, 40*hits-hitCount*10);
        
        // drawing bullets and removing them when they move off screen
        Iterator<Bullet> i = b.iterator();
        while(i.hasNext()){
            Bullet bullet = i.next();
            bullet.draw(g);
            if(bullet.y>900){
                i.remove();
            }
        }
    }
    
    // adding a bullet to ArrayList of bullets when shooting
    public void shoot(){
        b.add(new Bullet(x+(40*hits-hitCount*10)/2, y+(40*hits-hitCount*10), -velY, this.velX/4));
    }
    
    // collision detection between player bullet and enemy
    public boolean isHit(Bullet b){
        Rectangle enemy = new Rectangle(x,y, 40*hits-hitCount*10, 40*hits-hitCount*10);
        Rectangle bullet = new Rectangle(b.x, b.y, 15, 20);
        if(enemy.intersects(bullet)){
            hitCount++;
            return true;
        }
        return false;
    }
}