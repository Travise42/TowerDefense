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
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

public class Panel extends JPanel implements ActionListener, KeyListener, MouseInputListener {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static final int DELAY = 25;

    private Timer timer;
    Game game;
    JFrame frame;

    public int tick = 0;

    public Panel( JFrame jframe ) {
        setPreferredSize( new Dimension( WIDTH, HEIGHT ) );
        setBackground( new Color( 10,10,10 ) );

        frame = jframe;
        game = new Game( this );
        
        timer = new Timer( DELAY, this );
        timer.start();
    }

    // Runs 30 times per second
    @Override
    public void actionPerformed( ActionEvent e ) {
        tick ++;

        game.update();

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

    @Override
    public void mousePressed( MouseEvent e ) {
        game.mouseDown( e );
    }

    @Override
    public void mouseReleased( MouseEvent e ) {
        game.updateMouse( e );
        game.mouseUp( e );
    }

    @Override
    public void mouseEntered( MouseEvent e ) {
        
    }

    @Override
    public void mouseExited( MouseEvent e ) {

    }

    // Mouse is moved while pressed
    @Override
    public void mouseDragged( MouseEvent e ) {
        game.updateMouse( e );
        game.moveMouse( e );
    }

    // Mouse is moved while not pressed
    @Override
    public void mouseMoved( MouseEvent e ) {
        game.updateMouse( e );
        game.moveMouse( e );
    }

    // Key is pressed and released without interuption
    @Override
    public void keyTyped( KeyEvent e ) {

    }

    @Override
    public void keyPressed( KeyEvent e ) {
        game.keyCalled( e.getKeyCode() );
    }

    @Override
    public void keyReleased( KeyEvent e ) {
        
    }
    
}


