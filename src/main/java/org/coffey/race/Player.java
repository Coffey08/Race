package org.coffey.race;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * class describing Player
 * interface implements org.coffey.race.Moveable
 * interface implements org.coffey.race.Paintable
 */
public class Player implements Moveable, Paintable {
    private final Image img; // player image
    private int x0; // coordinate of the left point of the player on the X axis
    private int y0; // coordinate of the top point of the player on the Y axis
    private final int width; // player width
    private final int height; // player height
    private final int maxV; // the maximum speed of the player
    private final int minV; // the minimum speed of the player
    private int v; // player speed
    private int s; // the distance traveled by the player
    private int dv; // player acceleration
    private int dx; // for changing the X coordinate at turns

    public Player(int x0, int y0) {
        this.img = new ImageIcon(getClass().getResource("/player.png")).getImage(); // player image
        this.width = img.getWidth(null); // the width of the player is the image width
        this.height = img.getHeight(null); // the height of the player is the image height
        this.x0 = x0; // coordinate of the left point in the X axis is the left part of the player - 0
        this.y0 = y0; // coordinate of the top point in the Y axis is the top part of the player - 0
        this.maxV = 60; // maxV is 60
        this.minV = 10; // minV is 10
        this.v = 0; // v is 0
        this.s = 0; // s is 0
        this.dv = 0; // dv is 0
        this.dx = 0; // dx is 0
    }

    public Rectangle2D getRect() {
        return new Rectangle2D.Double(x0, y0, width, height); // get the player image borders
    }

    public int getX0() {
        return x0;
    }

    public void setX0(int x) {
        this.x0 = x;
    }

    public void setY0(int y0) {
        this.y0 = y0;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public int getS() {
        return s;
    }

    public void setDv(int dv) {
        this.dv = dv;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getMaxV() {
        return maxV;
    }

    public int getMinV() {
        return minV;
    }

    /**
     * method responsible for drawing
     * @param g works with graphics
     */
    public void paint(Graphics g) {
        g.drawImage(img, x0, y0, null); // draw the player
    }

    /**
     * method responsible for movement
     */
    public void move() {
        s += v; // the way increases at moving
        v += dv; // speed increase
        x0 += dx; // x0 will change depending on the turns
    }
}
