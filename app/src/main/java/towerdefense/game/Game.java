package towerdefense.game;

import static towerdefense.func.ImageHandler.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import towerdefense.game.env.Camera;
import towerdefense.game.env.Map;
import towerdefense.game.env.MapHandler;

public class Game {

    public Panel panel;
    public Camera camera;
    public MapHandler map;

    public int gameTick;

    //private BufferedImage testImage;

    public Game( Panel jpanel ) {
        panel = jpanel;

        camera = new Camera( this );
        map = new MapHandler( this );
        
        map.map.nextStage();

        gameTick = 0;

        //testImage = resizeImage( loadImage( "testImage.png" ), 120, 120 );
    }

    public void update() {
        gameTick++;

        camera.update();
        map.update();
    }

    public void draw( Graphics g ) {
        //g.drawImage( testImage, panel.tick, 100, panel );
        map.draw( g );
    }

    public int getStage() {
        return map.map.stage;
    }
    
}
