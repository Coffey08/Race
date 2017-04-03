package org.coffey.race;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * class describing the game
 * extends JPanel class
 * interface implements ActionListener, Runnable
 * interface implements org.coffey.race.Paintable
 */
public class Road extends JPanel implements ActionListener, Runnable, Paintable {

    private final Image img; // road image
    private final int width; // road width
    private final int height; // road height
    private final int leftGreen; // grass left of the road
    private final int rightGreen; // grass right of the road
    private int stop; // for the control player movement
    private int changeAcceleration; // for changing acceleration of player
    private int changeX; // for the player turns
    private int layer1; // the first layer of the road
    private int layer2; // the second layer of the road
    private Timer timer; // actions to be performed with a certain interval
    private Thread enemiesFactory; // thread (the appearance of enemies)
    private List<Enemy> enemies; // enemies list
    private Player player; // player
    private Enemy enemy; // enemy

    public Road() {
        this.img = new ImageIcon(getClass().getResource("/road.png")).getImage(); // road image
        this.width = img.getWidth(null); // the width of the road is the image width
        this.height = img.getHeight(null); // the height of the road is the image height
        this.leftGreen = 50; // leftGreen is 50
        this.rightGreen = 465; // rightGreen is 465
        this.stop = 0; // stop is 0
        this.changeAcceleration = 1; // changeAcceleration is 1
        this.changeX = 15; // changeX is 15
        this.layer1 = stop; // the coordinate of the first layer of the road equals the variable "stop"
        this.layer2 = -height; // the coordinate of the second layer of the road equals the road height with a negative value
        this.timer = new Timer(20, this); // set the interval perform actions
        this.timer.start(); // timer start
        this.enemiesFactory = new Thread(this);
        enemiesFactory.start(); // thread start (the appearance of enemies)
        this.enemies = new LinkedList<>();
        addKeyListener(new myKeyAdapter()); // add KeyListener - keystrokes regulation
        setFocusable(true); // set the focused state of the component
        this.player = new Player(0, 0);
        player.setX0(255); // specify the coordinate x0 for the player
        player.setY0(680); // specify the coordinate y0 for the player
        this.enemy = new Enemy(0, 0, 0, player.getV());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * regulates keystrokes
     */
    private class myKeyAdapter extends KeyAdapter {

        /**
         * changing the speed and location of the player, depending on the keystrokes
         *
         * @param e is set to key
         */
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode(); // get information about a key is pressed
            if (key == KeyEvent.VK_UP) { // if pressed the up arrow
                player.setDv(changeAcceleration); // increase acceleration
            }
            if (key == KeyEvent.VK_DOWN) { // if pressed the down arrow
                player.setDv(-changeAcceleration); // decreases acceleration
            }
            if (key == KeyEvent.VK_LEFT) { // if pressed the left arrow
                player.setDx(-changeX); // the player moves to the left
            }
            if (key == KeyEvent.VK_RIGHT) { // if pressed the right arrow
                player.setDx(changeX); // the player moves to the right
            }
        }

        /**
         * changing the speed and location of the player, depending on the key is released
         *
         * @param e is set to key
         */
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode(); // get information about a key is pressed
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) { // if the key left or right is released
                player.setDx(stop);  // a player does not move to the side
            }
            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) { // if the key up or down is released
                player.setDv(stop); // player speed does not change
            }
        }
    }

    /**
     * method responsible for drawing
     *
     * @param g works with graphics
     */
    public void paint(Graphics g) {
        g.drawImage(img, 0, layer1, null); // draw the first layer of the road
        g.drawImage(img, 0, layer2, null); // draw the second layer of the road
        player.paint(g); // draw the player
        for (Enemy e : enemies) {
            e.paint(g); // draw from a list "enemies" all enemies
        }

        double v = (200 / player.getMaxV()) * player.getV(); // speed, which will be displayed
        int s = (player.getS() / 1000); // the distance traveled by the player which will be displayed
        g.setColor(Color.blue); // set the string color
        Font font = new Font("Arial", Font.ITALIC, 20); // create a string font
        g.setFont(font); // set the string font
        g.drawString("Speed: " + v + " " + "km/h", 10, 30); // draw speed
        g.drawString("Way: " + s + "km", 10, 60); // draw the distance traveled by the player
    }

    /**
     * method responsible for movement
     */
    public void move() {
        player.move(); // the player is moved
        for (Enemy e : enemies) { // all the enemies of the list of "enemies" are moved
            e.move();
        }
        if (player.getV() <= player.getMinV()) { // if player's speed is less or equal to the minimum speed of the player, then
            player.setV(player.getMinV()); // player speed is the minimum
        } else if (player.getV() >= player.getMaxV()) { // if player's speed is greater or equal the maximum, then
            player.setV(player.getMaxV()); // player speed is the maximum
        }

        if (player.getX0() <= leftGreen) { // if the player coordinate x0 is less or equal to the coordinates of the left grass, then
            player.setX0(leftGreen); // player coordinate x0 is the coordinate of the left grass
        } else if (player.getX0() >= rightGreen) { // if the player coordinate x0 is greater or equal to the coordinates of the right grass, then
            player.setX0(rightGreen); // player coordinate x0 is the coordinate of the right grass
        }

        if (layer2 + player.getV() >= stop) { // if the coordinate of the second layer of the road is greater or equal to 0, then
            layer1 = stop; // the coordinate of the first layer of the road equals the variable "z" (0)
            layer2 = -height; // the coordinate of the second layer of the road equals the road height with a negative value (-1240)
        } else { // otherwise
            layer1 = layer1 + player.getV(); // the first layer of the road is moved
            layer2 = layer2 + player.getV(); // the second layer of the road is moved
        }
        testCollision(); // start "testCollision" method
        testWin(); // start "testWin" method
    }


    /**
     * launches the methods
     *
     * @param e event handler
     */
    public void actionPerformed(ActionEvent e) {
        move(); // start moving method
        repaint(); // redraw
    }

    /**
     * checks whether the player wins
     */
    public void testWin() {
        int s = (player.getS() / 1000); // the distance traveled by the player
        if (s >= 100) { // if the distance traveled by the player is 100
            JOptionPane.showMessageDialog(null, "You win!!!"); // display a message about winning
            System.exit(1); // quit the game
        }
    }

    /**
     * checks whether the player collides with the enemies
     */
    public void testCollision() {
        for (Enemy e : enemies) {
            if (player.getRect().intersects(e.getRect())) { // if the player will face the enemy
                JOptionPane.showMessageDialog(null, "You lose"); // display a message about losing
                System.exit(1); // quit the game
            }
        }
    }

    /**
     * implements the interface "Runnable"
     */
    public void run() {
        while(true) {
            Random rand = new Random(); // random class
            try {  // if the code snippet does not generate an exception, then
                Thread.sleep(rand.nextInt(1200)); // thread falls asleep for a while from 0 to 2000 milliseconds
                enemies.add(new Enemy(rand.nextInt(465), -150, rand.nextInt(60), player.getV())); // to the list of "enemies" are added the enemies
            } catch(InterruptedException e) { // otherwise the remainder of code in the "try" block is skipped and the processing code in the "catch" block
                e.printStackTrace(); // generate a stack trace
            }
        }
    }
}
