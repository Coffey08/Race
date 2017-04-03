package org.coffey.race;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame f = new JFrame("Race"); // frame creation
        Road road = new Road(); // create an object of the game
        road.setPreferredSize(new Dimension(road.getWidth(), road.getHeight())); // set the size of the game
        f.getContentPane().add(road); // add a game to the content panel
        f.pack(); // set the size of the container, which is necessary to display all the components
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // the action which will take place on the closure frame (exit application)
        f.setLocationRelativeTo(null); // setting the frame at the screen center
        f.setVisible(true); // make the frame visible
    }
}
