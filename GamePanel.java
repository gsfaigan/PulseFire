import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;
import java.awt.Font;
/**
 * main game pane where the game takes place
 * handle the rendering of game objects
 * manage the game loop to update positions and check for events
 * respond to player input via key presses
 * handle rendering the movement of objects
 * collision detection
 * trigger game events like scoring and game over
 * 
 * @Gabriel Faigan
 * @Nov-Dec, 2024
 */
public class GamePanel extends JPanel implements KeyListener{
    Player p; // the player
    int lives; // total lives allowed for a level
    int playerLives;  // the current player lives
    int level; // the current level
    //  TESTING int hits = 0;
    ArrayList<Enemy> enemies; // list of enemies on screen, adding to this to add an enemy to a level
    boolean levelBeat = false; // did the player beat the current level
    boolean displayWin = false; // when the player beats the last level to display win message
    boolean gameOver = false; // did the player lose all lives
    boolean restart = false; // restarting the current level
    boolean clearEnemies = false; // clearing enemies when restarting or when winnning
    Timing t; // displayed timer
    String finalTime; // final time on win screen
    public GamePanel(int level, int playerLives, Timing t){
        this.t = t; // always add prev time from last level to the new timer, so it doesn't keep starting from 0
        t.start();

        p = new Player();
        enemies = new ArrayList<>();
        this.playerLives = playerLives;
        this.level = level;
        this.lives = playerLives;

        // level generation with placing enemies and deciding the player's total bullets and bullet speed
        // Enemy arguments in order: x, y, enemy speed x, bullet speed, firing frequency, enemy size
        switch(level){
            case 1:
                enemies.add(new Enemy(350,100,0,7,3000,1));
                p.totalBullets = 1;
                break;
            case 2:
                enemies.add(new Enemy(100,100,2,7,1500,1));
                p.totalBullets = 3;
                break;
            case 3:
                enemies.add(new Enemy(100,100,2,7,1500,1));
                enemies.add(new Enemy(50,50,5,3,700,1));
                enemies.add(new Enemy(50,60,8,1,600,1));
                p.totalBullets = 5;
                break;
            case 4:
                enemies.add(new Enemy(100,100,1,10,400,3));
                p.totalBullets = 3;
                break;
            case 5:
                enemies.add(new Enemy(100,100,1,10,800,2));
                enemies.add(new Enemy(120,150,2,8,700,2));
                enemies.add(new Enemy(200,200,4,6,600,1));
                p.totalBullets = 2;
                break;
            case 6:
                enemies.add(new Enemy(100,100,7,4,400,5));
                p.totalBullets = 3;
                break;
            case 7:
                enemies.add(new Enemy(100,200,5,2,200,2));
                enemies.add(new Enemy(80,300,4,2,250,2));
                p.totalBullets = 5;
                break;
            case 8:
                enemies.add(new Enemy(100,200,4,2,150,6));
                p.totalBullets = 4;
                break;
            case 9:
                enemies.add(new Enemy(100,100,3,2,1000,1));
                enemies.add(new Enemy(150,150,3,2,1000,1));
                enemies.add(new Enemy(150,155,3,2,1000,1));
                enemies.add(new Enemy(170,300,3,2,1000,1));
                enemies.add(new Enemy(180,250,3,2,1000,1));
                enemies.add(new Enemy(200,350,3,2,1000,1));
                enemies.add(new Enemy(230,100,3,2,1000,1));
                enemies.add(new Enemy(270,300,3,2,1000,1));
                enemies.add(new Enemy(100,400,3,2,1000,1));
                enemies.add(new Enemy(150,350,3,2,1000,1));
                p.totalBullets = 10;
                break;
            case 10:
                enemies.add(new Enemy(100,100,3,5,700,1));
                enemies.add(new Enemy(150,150,4,5,700,2));
                enemies.add(new Enemy(200,200,3,5,700,3));
                enemies.add(new Enemy(250,250,4,5,700,2));
                p.totalBullets = 6;
                break;
            case 11:
                enemies.add(new Enemy(100,100,1,12,100,1));
                enemies.add(new Enemy(250,150,3,8,200,2));
                enemies.add(new Enemy(400,200,5,5,300,3));
                p.totalBullets = 7;
                break;
            case 12:
                enemies.add(new Enemy(50,50,4,6,500,1));
                enemies.add(new Enemy(100,100,3,8,400,2));
                enemies.add(new Enemy(200,150,2,10,300,3));
                enemies.add(new Enemy(300,200,1,12,200,4));
                p.totalBullets = 8;
                break;
            case 13:
                enemies.add(new Enemy(150,100,5,7,1000,1));
                enemies.add(new Enemy(250,150,4,7,1000,2));
                enemies.add(new Enemy(350,200,6,7,1000,3));
                p.totalBullets = 9;
                break;
            case 14:
                enemies.add(new Enemy(100,100,2,5,700,2));
                enemies.add(new Enemy(150,150,3,6,600,3));
                enemies.add(new Enemy(200,200,4,7,500,4));
                enemies.add(new Enemy(300,250,5,8,400,2));
                p.totalBullets = 10;
                break;
            case 15:
                enemies.add(new Enemy(50,50,1,8,500,1));
                enemies.add(new Enemy(100,100,2,8,600,2));
                enemies.add(new Enemy(200,200,3,8,700,3));
                enemies.add(new Enemy(300,300,4,8,800,4));
                enemies.add(new Enemy(100,100,1,12,100,1));
                enemies.add(new Enemy(250,150,3,8,200,2));
                enemies.add(new Enemy(400,200,5,5,300,3));
                p.totalBullets = 10;
                break;
            case 16:
                enemies.add(new Enemy(150,100,4,10,300,2));
                enemies.add(new Enemy(250,150,3,10,400,3));
                enemies.add(new Enemy(350,200,4,10,500,4));
                enemies.add(new Enemy(400,250,5,10,600,5));
                p.totalBullets = 11;
                break;
            case 17:
                enemies.add(new Enemy(100,100,1,8,300,1));
                enemies.add(new Enemy(150,150,2,8,400,1));
                enemies.add(new Enemy(200,200,3,8,500,1));
                enemies.add(new Enemy(300,300,4,8,600,1));
                p.totalBullets = 12;
                break;
            case 18:
                enemies.add(new Enemy(100,100,3,7,200,2));
                enemies.add(new Enemy(150,150,4,7,200,3));
                enemies.add(new Enemy(200,200,5,7,200,4));
                enemies.add(new Enemy(100,100,1,12,100,1));
                p.totalBullets = 13;
                break;
            case 19:
                enemies.add(new Enemy(100,100,5,6,300,1));
                enemies.add(new Enemy(200,200,6,6,400,2));
                enemies.add(new Enemy(300,300,7,6,500,3));
                p.totalBullets = 14;
                break;    

            case 20:
                enemies.add(new Enemy(100,100,1,10,150,1));
                enemies.add(new Enemy(200,250,2,10,300,2));
                enemies.add(new Enemy(100,100,3,10,150,1));
                enemies.add(new Enemy(200,250,4,10,300,7));
                p.totalBullets = 5;
                break;
            case 21:
                for(int i=1; i<20; i++){
                    enemies.add(new Enemy(i*10, i*10, i, i*2, i*200, i%2+1));
                }
                p.totalBullets = 10;
                break;
            case 22:
                enemies.add(new Enemy(100,100,1,10,100,3));
                enemies.add(new Enemy(130,100,2,10,100,3));
                enemies.add(new Enemy(160,100,3,10,100,3));
                enemies.add(new Enemy(190,100,4,10,200,3));
                enemies.add(new Enemy(210,100,5,10,200,3));
                p.totalBullets = 10;
                break;
            case 23:
                for(int i=1; i<20; i++){
                    enemies.add(new Enemy(i*10, i*10, i%4+1, i%4+5, (i%4+1)*200, i%3+1));
                }
                p.totalBullets = 10;
                p.bulletSpeed = 30;
                break;
            case 24:
                enemies.add(new Enemy(200,200, 2, 12, 1500, 1));
                enemies.add(new Enemy(300,200, 2, 12, 2000, 2));
                enemies.add(new Enemy(200,300, 2, 12, 1500, 1));
                enemies.add(new Enemy(300,300, 2, 12, 2000, 2));
                enemies.add(new Enemy(100,400, 2, 12, 1500, 1));
                p.totalBullets = 7;
                p.bulletSpeed = 9;
                break;
            case 25:
                finalTime = t.getTime();
                displayWin = true;
                Sound.stopLoopSound();
                break;
        }

        // allowing the panel to receive key inputs
        setFocusable(true);
        addKeyListener(this);

        // timer for enemy shooting frequency
        // needs to iterate through each enemy in the list and have a seperate timer for each
        for(Enemy enemy : enemies){
            Timer eShootTimer = new Timer(enemy.shootInterval, 
                    new ActionListener(){
                        public void actionPerformed(ActionEvent eeee){
                            enemy.shoot();
                        }
                    }); 
            eShootTimer.start();
        }
    }

    public void paint(Graphics g){
        // Enemy iterator to render enemies individually and then remove them when they die
        Iterator<Enemy> i = enemies.iterator();
        while(i.hasNext()){
            Enemy enemy = i.next();
            enemy.draw(g);

            // collision of player bullets and enemy
            Iterator<Bullet> bulletI = p.b.iterator();
            while(bulletI.hasNext()){
                Bullet bullet = bulletI.next();
                if(enemy.isHit(bullet)){
                    bulletI.remove();
                    if(enemy.hitCount >= enemy.hits*3){
                        i.remove();
                    }
                }
            }
        }

        // when clear enemies is true to clear the enemies and then reset to false
        if(clearEnemies){
            enemies.clear();
            clearEnemies = false;
        }

        // collision of enemy bullets and player
        for(Enemy enemy : enemies){
            Iterator<Bullet> bulletI = enemy.b.iterator();
            while(bulletI.hasNext()){
                Bullet bullet = bulletI.next();

                // when player is damaged by enemy bullet
                if(p.isHit(bullet)){
                    bulletI.remove();
                    playerLives--;
                    Sound.playSound("DAMAGE.wav");

                    // when player dies and has to restart
                    if(playerLives <= 0){
                        gameOver = true;
                        clearEnemies = true;
                        Sound.playSound("LOSE.wav");
                    }
                }
            }
        }

        // conditions to beat a level
        if(enemies.size()==0 && !displayWin && !gameOver){
            //next level or win
            levelBeat=true;
        }

        // rendering different menu screens: win, lose, tutorial
        final Font mono100 = new Font("Monospaced", Font.BOLD, 100);
        final Font mono20 = new Font("Monospaced", Font.BOLD, 20);
        final Font calibri = new Font("Calibri", Font.BOLD, 20);
        final Font comic = new Font("Bahnschrift", Font.PLAIN, 20);
        final int rowSize = 25;

        if(displayWin){
            g.setColor(Color.BLACK);
            g.setFont(mono100);
            g.drawString("YOU WIN!", 50, 150);
            g.setFont(mono20);
            g.drawString("Final Time: "+finalTime, 50, 200);
        }else if(gameOver){
            g.setColor(Color.BLACK);
            g.setFont(mono100);
            g.drawString("LEVEL", 80, 150);
            g.drawString("FAILED!", 80, 250);

            g.setColor(Color.BLACK);
            g.setFont(mono20);
            g.drawString(t.getTime(), 100, 300);

            g.setFont(calibri);
            g.drawString("To try again press R", 100, 350);
        }else{
            if(level==1){
                // tutorial
                g.setColor(Color.BLACK);
                g.setFont(comic);
                g.drawString("Use A/D or Left/Right to move", 100, 400);
                g.drawString("Use Spacebar to shoot", 100, 450);
                g.drawString("Eliminate all enemies to advance", 100, 500);
            }

            // HUD text and graphics
            g.setColor(Color.BLACK);
            g.setFont(mono20);
            g.drawString("Level "+level, 10, rowSize*4);
            g.drawString("Bullets", 10, rowSize*5);
            g.drawString(enemies.size() +(enemies.size()>1?" Enemies Left":" Enemy Left"), 10, rowSize*6);
            g.setColor(Color.BLUE);
            p.draw(g);
            drawLives(g);
            drawBulletsAvailable(g);
            g.setColor(Color.BLACK);
            g.setFont(mono20);
            g.drawString(t.getTime(), 10, rowSize*7);
        }
    }

    // the green squares at the top that represent player current lives
    public void drawLives(Graphics g){
        g.setColor(Color.green);
        if(playerLives < 30){
            for(int i=0; i<playerLives; i++){
                g.fillRect(10+i*20, 10, 15, 15);
            }
        }else if(playerLives < 60){
            for(int i=0; i<29; i++){
                g.fillRect(10+i*20, 10, 15, 15);
            }
            for(int i=0; i<playerLives-29; i++){
                g.fillRect(10+i*20, 30, 15, 15);
            }
        }else{
            for(int i=0; i<29; i++){
                g.fillRect(10+i*20, 10, 15, 15);
            }
            for(int i=0; i<29; i++){
                g.fillRect(10+i*20, 30, 15, 15);
            }
            for(int i=0; i<playerLives-59; i++){
                g.fillRect(10+i*20, 50, 15, 15);
            }
        }
    }

    // the squares that represent bullets available to shoot
    public void drawBulletsAvailable(Graphics g){
        // background rectangle
        g.setColor(Color.BLACK);
        g.fillRect(97, 110, 1 + 20*p.totalBullets-p.b.size(), 20);

        // squares
        g.setColor(Color.WHITE);
        for(int i=0; i<p.totalBullets-p.b.size(); i++){
            g.fillRect(100+i*20, 113, 15, 15);
        }
    }

    // restarting level R key pressed and also adding player inputs to this keyPressed
    public void keyPressed(KeyEvent e){
        p.keyPressed(e);
        if(gameOver){
            if(e.getKeyCode() == KeyEvent.VK_R){
                restart = true;
            }
        }
    }

    // adding player inputs to this keyReleased
    public void keyReleased(KeyEvent e){ 
        p.keyReleased(e);
    }

    public void keyTyped(KeyEvent e){}
}