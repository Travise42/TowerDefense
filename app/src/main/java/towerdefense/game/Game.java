package towerdefense.game;

import static towerdefense.func.ImageHandler.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Game {

    Panel td;

    BufferedImage testImage;

    public Game( Panel towerDefense ) {
        td = towerDefense;

        testImage = resizeImage( loadImage( "testImage.png" ), 120, 120 );
    }

    public void update() {
        
    }

    public void draw( Graphics g ) {
        g.drawImage( testImage, td.tick, 100, td );
    }
    
}
