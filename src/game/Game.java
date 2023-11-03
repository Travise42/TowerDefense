package game;

import main.TowerDefense;

import static func.ImageHandler.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Game {

    TowerDefense td;

    BufferedImage testImage;

    public Game( TowerDefense towerDefense ) {
        td = towerDefense;

        testImage = resizeImage( loadImage( "src/assets/testImage.png" ), 120, 120 );
    }

    public void update() {
        
    }

    public void draw( Graphics g ) {
        g.drawImage( testImage, td.tick, 100, td );
    }
    
}
