import javax.swing.JPanel;
import java.awt.Graphics;
/**
 * The bullets
 * Updating their position and drawing them
 *
 * @Gabriel Faigan
 * @Nov, 2024
 */
public class Bullet extends JPanel{
    int x, y; // position
    int velY, velX; // speed down and left or right
    public Bullet(int x, int y, int velY, int velX){
        this.x = x;
        this.y = y;
        this.velY = velY;
        this.velX = velX;
    }
    public Bullet(int x, int y, int velY){
        this(x,y,velY,0);
    }
    
    // draw and update bullet position
    public void draw(Graphics g){
        g.fillRect(x,y,10,20);
        y-=velY;
        x+=velX;
    }
}