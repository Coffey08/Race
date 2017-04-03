package org.coffey.race;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * class describing Enemy
 * interface implements org.coffey.race.Moveable
 * interface implements org.coffey.race.Paintable
 */
public class Enemy implements Moveable, Paintable {
    private final Image img; // enemy image
    private int x0; // coordinate of the left point of the enemy on the X axis
    private int y0; // coordinate of the top point of the enemy on the Y axis
    private int v; // enemy speed
    private final int width; // enemy width
    private final int height; // enemy height
    private int acceleration; // enemy acceleration

    public Enemy(int x0, int y0, int v, int acceleration) {
        this.img = new ImageIcon(getClass().getResource("/enemy.png")).getImage(); // enemy image
        this.width = img.getWidth(null); // the width of the enemy is the image width
        this.height = img.getHeight(null); // the height of the enemy is the image height
        this.x0 = x0; // coordinate of the left point in the X axis is the left part of the enemy - 0
        this.y0 = y0; // coordinate of the top point in the Y axis is the top part of the enemy - 0
        this.v = v; // speed is the speed of the enemy
        this.acceleration = acceleration; // acceleration is the acceleration of the enemy
    }

    public Rectangle2D getRect() {
        return new Rectangle2D.Double(x0, y0, width, height); // get the enemy image borders
    }

    /**
     * method responsible for drawing
     * @param g works with graphics
     */
    public void paint(Graphics g) {
        g.drawImage(img, x0, y0, null); // draw the enemy
    }

    /**
     * method responsible for movement
     */
    public void move() {
        y0 = y0 + acceleration - v; // y0 will change depending on the acceleration and speed of the enemy
    }
}