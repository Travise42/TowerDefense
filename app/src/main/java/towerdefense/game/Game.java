package towerdefense.game;

import static towerdefense.func.ImageHandler.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import towerdefense.game.env.Map;

public class Game {

    Panel panel;
    Map map;

    BufferedImage testImage;

    public Game( Panel jpanel ) {
        panel = jpanel;

        map = new Map( this );

        //testImage = resizeImage( loadImage( "testImage.png" ), 120, 120 );
    }

    public void update() {
        
    }

    public void draw( Graphics g ) {
        //g.drawImage( testImage, panel.tick, 100, panel );
    }
    
}
