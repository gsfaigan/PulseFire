import javax.swing.JFrame;
import java.awt.Color;
/**
 * creates and displays the game window
 * adds the main game panel to the window
 * sets window size, background colour, close operation and other properties
 * manages advancing levels and different live counts on each level
 *
 * @Gabriel Faigan
 * @Nov-Dec, 2024
 */
public class GameFrame extends JFrame{
    GamePanel p;
    int level = 1;
    int lives = 3;
    Timing timer = new Timing();
    public GameFrame(){
        timer.start();

        // setting frame properties
        setSize(600,900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p = new GamePanel(level,lives,timer);
        setLocationRelativeTo(null);
        add(p);
        setBackground(new Color(255,200,100));
        setVisible(true);
        setResizable(false);        
        Sound.playLoopSound("MUSIC.wav"); // adding background music

        // game loop
        while(true){
            try{
                Thread.sleep(10);
            }catch(Exception e){}
            repaint();

            //restarting level
            if(p.restart){
                level = p.level;
                remove(p);
                lives = p.lives; // reset to total lives for the level being restarted
                p = new GamePanel(level, lives, timer);
                add(p);

                // request focus
                p.setFocusable(true);
                p.requestFocusInWindow();

                // recalculate the layout when adding components
                revalidate();
                repaint();
            }

            // beating a level
            if(p.levelBeat){
                level++;
                remove(p); // remove previous level

                int R = 180 + (int)(Math.random() * 76); // Range: 180-255
                int G = 180 + (int)(Math.random() * 76); // Range: 180-255
                int B = 200 + (int)(Math.random() * 56); // Range: 200-255
                setBackground(new Color(R,B,G)); // change background colour every new level

                // sounds when advancing to next level
                if(level==25){
                    Sound.playSound("WIN.wav");
                }else{
                    Sound.playSound("COMPLETE.wav");
                }

                // setting the lives for each level
                switch(level){
                    case 2: lives = 4; break;
                    case 3: lives = 2; break;
                    case 4: lives = 2; break;
                    case 5: lives = 5; break;
                    case 6: lives = 2; break;
                    case 7: lives = 3; break;
                    case 8: lives = 6; break;
                    case 9: lives = 5; break;
                    case 10: lives = 7; break;
                    case 11: lives = 5; break;
                    case 12: lives = 8; break;
                    case 13: lives = 2; break;
                    case 14: lives = 2; break;
                    case 15: lives = 15; break;
                    case 16: lives = 5; break;
                    case 17: lives = 6; break;
                    case 18: lives = 12; break;
                    case 19: lives = 5; break;
                    case 20: lives = 29; break;
                    case 21: lives = 15; break;
                    case 22: lives = 33; break;
                    case 23: lives = 79; break;
                    case 24: lives = 3; break;
                }
                p = new GamePanel(level, lives, timer);
                add(p);

                // request focus
                p.setFocusable(true);
                p.requestFocusInWindow();

                // recalculate the layout when adding components
                revalidate();
                repaint();
            }
        }
    }

    public static void main(String[] args){
        new GameFrame();
    }
}