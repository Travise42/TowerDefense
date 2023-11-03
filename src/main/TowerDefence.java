package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

import static func.ImageHandler.*;

public class TowerDefence extends JPanel implements ActionListener, KeyListener, MouseInputListener {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final int DELAY = 25;

    private Timer timer;

    private int tick = 0;

    BufferedImage testImage;

    public TowerDefence() {
        setPreferredSize( new Dimension( WIDTH, HEIGHT ) );
        setBackground( new Color( 10,10,10 ) );

        testImage = resizeImage( loadImage( "src/assets/testImage.png" ), 40, 40 );
        
        timer = new Timer( DELAY, this );
        timer.start();
    }

    @Override
    public void paintComponent( Graphics g ) {
        // Clear screen for new images
        super.paintComponent( g );

        g.drawImage( testImage, 100, 100, this );

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed( ActionEvent e ) {
        tick = tick + 1;
    }

    @Override
    public void mouseClicked( MouseEvent e ) {

    }

    @Override
    public void mousePressed( MouseEvent e ) {

    }

    @Override
    public void mouseReleased( MouseEvent e ) {

    }

    @Override
    public void mouseEntered( MouseEvent e ) {
        
    }

    @Override
    public void mouseExited( MouseEvent e ) {

    }

    @Override
    public void mouseDragged( MouseEvent e ) {

    }

    @Override
    public void mouseMoved( MouseEvent e ) {

    }

    @Override
    public void keyTyped( KeyEvent e ) {
        
    }

    @Override
    public void keyPressed( KeyEvent e ) {
        
    }

    @Override
    public void keyReleased( KeyEvent e ) {
        
    }
    
}


