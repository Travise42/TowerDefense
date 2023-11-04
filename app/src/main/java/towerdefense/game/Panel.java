package towerdefense.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

public class Panel extends JPanel implements ActionListener, KeyListener, MouseInputListener {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final int DELAY = 25;

    private Timer timer;
    Game game;

    public int tick = 0;

    // Initiate the program
    public Panel() {
        setPreferredSize( new Dimension( WIDTH, HEIGHT ) );
        setBackground( new Color( 10,10,10 ) );

        game = new Game( this );
        
        timer = new Timer( DELAY, this );
        timer.start();
    }

    // Runs 30 times per second
    @Override
    public void actionPerformed( ActionEvent e ) {
        tick ++;

        repaint();
    }

    // Runs 30 times per second
    @Override
    public void paintComponent( Graphics g ) {
        // Clear screen for new images
        super.paintComponent( g );

        game.draw( g );

        Toolkit.getDefaultToolkit().sync();
    }

    // Mouse pressed and released without moving
    @Override
    public void mouseClicked( MouseEvent e ) {

    }

    // Mouse initially pressed
    @Override
    public void mousePressed( MouseEvent e ) {

    }

    // Mouse released
    @Override
    public void mouseReleased( MouseEvent e ) {

    }

    // Mouse enters the screen
    @Override
    public void mouseEntered( MouseEvent e ) {
        
    }

    // Mouse leaves the screen
    @Override
    public void mouseExited( MouseEvent e ) {

    }

    // Mouse is moved while pressed
    @Override
    public void mouseDragged( MouseEvent e ) {

    }

    // Mouse is moved while not pressed
    @Override
    public void mouseMoved( MouseEvent e ) {

    }

    // Key is pressed and released without interuption
    @Override
    public void keyTyped( KeyEvent e ) {
        
    }

    // Key is initally pressed
    @Override
    public void keyPressed( KeyEvent e ) {
        
    }

    // Key is released
    @Override
    public void keyReleased( KeyEvent e ) {
        
    }
    
}


