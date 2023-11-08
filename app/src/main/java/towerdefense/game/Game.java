package towerdefense.game;

////import static towerdefense.func.ImageHandler.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
////import java.awt.image.BufferedImage;

import towerdefense.game.env.Camera;
import towerdefense.game.env.MapHandler;
import towerdefense.game.gui.MapInteractions;

public class Game {

    public Panel panel;
    public Camera camera;
    public MapHandler map;
    public MapInteractions mi;

    public int gameTick;

    public int mx = 0, my = 0;

    ////private BufferedImage testImage;

    public Game( Panel jpanel ) {
        panel = jpanel;

        mi = new MapInteractions( this );
        camera = new Camera( this );
        map = new MapHandler( this );

        gameTick = 0;

        mi.selectTowerPlacement( MapInteractions.NO_TOWER );

        ////testImage = resizeImage( loadImage( "testImage.png" ), 120, 120 );
    }

    public void update() {
        gameTick++;

        camera.update();
        map.update();
    }

    public void draw( Graphics g ) {
        ////g.drawImage( testImage, panel.tick, 100, panel );
        map.draw( g );
        mi.drawHighlightedRegion( g, mx, my );
    }

    public void updateMouse( MouseEvent e ) {
        mx = e.getX() - 8;
        my = e.getY() - 30;
    }

    public void keyCalled( int key ) {
        if ( 0 <= key - 48 && key - 48 <= 5 ) {
            mi.selectTowerPlacement( key - 48 );
        }
    }

    public int getStage() {
        return map.map.stage;
    }
    
}
