package towerdefense.game;

////import static towerdefense.func.ImageHandler.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
////import java.awt.image.BufferedImage;

import towerdefense.game.env.Camera;
import towerdefense.game.env.MapHandler;
import towerdefense.game.gui.MapInteractions;
import towerdefense.game.player.Player;
import towerdefense.game.towers.Tower;

public class Game {

    public Panel panel;
    public Camera camera;
    public MapHandler map;
    public MapInteractions mi;
    public Player player;

    public int gameTick = 0;

    public int mx = 0, my = 0;

    public Game( Panel jpanel ) {
        panel = jpanel;

        mi = new MapInteractions( this );
        camera = new Camera( this );
        map = new MapHandler( this );
        player = new Player( this );

        newGame();
        System.out.println("Started!");
    }

    public void newGame() {
        player.newGame();
        map.newGame();

        gameTick = 0;
    }

    public void update() {
        gameTick++;

        camera.update();
        map.update();
    }

    public void draw( Graphics g ) {
        map.draw( g );

        mi.drawHighlightedRegion( g, mx, my );
    }

    public void click() {
        mi.interactWithMap( mx, my );
    }

    public void moveMouse( MouseEvent e ) {
        
    }

    public void updateMouse( MouseEvent e ) {
        mx = e.getX(); //-8
        my = e.getY() - 29; //-30
    }

    public void keyCalled( int key ) {
        if ( KeyEvent.VK_0 <= key && key <= KeyEvent.VK_0 + 5 ) {
            mi.selectTowerPlacement( key - KeyEvent.VK_1 );
        }
        switch ( key ) {
            case KeyEvent.VK_SPACE -> map.nextStage();
            case KeyEvent.VK_COMMA -> mi.upgradeSelectedTower( 0 );
            case KeyEvent.VK_PERIOD -> mi.upgradeSelectedTower( 1 );
            case KeyEvent.VK_BACK_SPACE -> mi.deleteSelectedTower();
        }
    }

    public int getStage() {
        return map.map.stage;
    }
    
}
