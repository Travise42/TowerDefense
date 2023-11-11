package towerdefense.game.gui;

import towerdefense.game.Game;
import towerdefense.game.Panel;
import towerdefense.game.env.Map;
import towerdefense.game.towers.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class MapInteractions {

    Game game;

    final public static int NO_TOWER = 0;
    final public static int WIZARD_TOWER = 1;
    final public static int BOMB_TOWER = 2;
    final public static int ARCHER_TOWER = 3;
    final public static int WALL_TOWER = 4;
    final public static int TROOP_TOWER = 5;

    private int selected;
    private int size;

    public MapInteractions( Game game ) {
        this.game = game;

        selected = NO_TOWER;
    }

    public void selectTowerPlacement( int type ) {
        selected = type;

        switch ( selected ) {
            case NO_TOWER -> size = 0;
            case WIZARD_TOWER -> size = 2;
            case BOMB_TOWER -> size = 2;
            case ARCHER_TOWER -> size = 2;
            case WALL_TOWER -> size = 1;
            case TROOP_TOWER -> size = 2;
        }
    }

    public void drawHighlightedRegion( Graphics g, int mouseX, int mouseY ) {
        g.setColor( new Color( 255, 255, 255, 100 ) );

        g.fillRect(
                (int)( getColumn( mouseX ) * game.map.getTileSize() - game.camera.getX() ), //(int)( mousex - ( mousex + screenx ) % tileSize ),
                (int)( getRow( mouseY ) * game.map.getTileSize() - game.camera.getY() ), //(int)( mousey - ( mousey + screeny ) % tileSize ),
                (int) game.map.getTileSize() * size,
                (int) game.map.getTileSize() * size
        );
    }

    public void interactWithMap( int mouseX, int mouseY ) {
        if ( selected == NO_TOWER ) {
            return;
        }

        switch ( selected ) {
            case WIZARD_TOWER -> game.map.towers.add( new WizardTower( game, getColumn(mouseX), getRow(mouseY) ) );
            case BOMB_TOWER -> game.map.towers.add( new BombTower( game, getColumn(mouseX), getRow(mouseY) ) );
            case ARCHER_TOWER -> game.map.towers.add( new ArcherTower( game, getColumn(mouseX), getRow(mouseY) ) );
            case WALL_TOWER -> game.map.towers.add( new WallTower( game, getColumn(mouseX), getRow(mouseY) ) );
            case TROOP_TOWER -> game.map.towers.add( new TroopTower( game, getColumn(mouseX), getRow(mouseY) ) );
        }
    }

    private int getColumn( int x ) {
        return (int) ( ( x + game.camera.getX() ) / game.map.getTileSize() );
    }

    private int getRow( int y ) {
        return (int) ( ( y + game.camera.getY() ) / game.map.getTileSize() );
    }
    
}