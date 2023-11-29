package towerdefense.game.gui;

import towerdefense.game.Game;
import towerdefense.game.Panel;
import towerdefense.game.env.Map;
import towerdefense.game.towers.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapInteractions {

    Game game;

    final public static int NO_TOWER = -1;
    final public static int WIZARD_TOWER = 0;
    final public static int BOMB_TOWER = 1;
    final public static int ARCHER_TOWER = 2;
    final public static int WALL_TOWER = 3;
    final public static int TROOP_TOWER = 4;

    final public static List<Tower> TOWER = Arrays.asList( 
        new WizardTower(),
        new BombTower(),
        new ArcherTower(),
        new WallTower(),
        new TroopTower()
    );

    private int selected;

    public MapInteractions( Game game ) {
        this.game = game;

        selected = NO_TOWER;
    }

    public void selectTowerPlacement( int type ) {
        selected = type;
    }

    public void drawHighlightedRegion( Graphics g, int mouseX, int mouseY ) {
        if ( selected == NO_TOWER ) return;

        float tileSize = game.map.getTileSize();
        int graphicSize = (int) ( tileSize * TOWER.get(selected).getSize() );

        int towerColumn = getColumn( mouseX - (int)( graphicSize*0.95 - 15 - tileSize ) / 2 );
        int towerRow = getRow( mouseY - ( graphicSize - ( int )( tileSize ) ) / 2 );

        g.setColor( new Color( 255, 255, 255, 100 ) );
        g.fillRect(
                ( int )( towerColumn * tileSize - game.camera.getX() ),
                ( int )( towerRow * tileSize - game.camera.getY() ),
                graphicSize,
                graphicSize
        );
    }

    public void interactWithMap( int mouseX, int mouseY ) {
        if ( selected == NO_TOWER ) return;

        // Get tower dimensions
        float tileSize = game.map.getTileSize();
        int towerSize = TOWER.get(selected).getSize();
        int tileOffset = ( int )( tileSize * ( towerSize - 1 ) / 2 );

        // Get the furthest left column of the tower
        int towerColumn = getColumn( mouseX - tileOffset );

        // Get the furthest up row of the tower
        int towerRow = getRow( mouseY - tileOffset );

        boolean SPACE_IS_NOT_AVAIABLE = game.map.isObstructed( towerColumn, towerRow, towerSize );
        if ( SPACE_IS_NOT_AVAIABLE ) return;

        game.map.towers.add( TOWER.get(selected).createNew( game, towerColumn, towerRow ) );

        selected = NO_TOWER;
    }

    private int getColumn( int x ) {
        return (int) ( ( x + game.camera.getX() ) / game.map.getTileSize() );
    }

    private int getRow( int y ) {
        return (int) ( ( y + game.camera.getY() ) / game.map.getTileSize() );
    }
    
}
