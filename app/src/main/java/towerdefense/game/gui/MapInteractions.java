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

    public void drawHighlightedRegion( Graphics g, int mousex, int mousey ) {
        float tileSize = game.map.getTileSize();
        float screenx = ( Map.COLUMNS*tileSize - Panel.WIDTH ) / 2;
        float screeny = ( Map.ROWS*tileSize - Panel.HEIGHT ) / 2;

        g.setColor( new Color( 255, 255, 255, 100 ) );

        g.fillRect(
                (int)( Math.floor( ( mousex + screenx ) / tileSize ) * tileSize - screenx ), //(int)( mousex - ( mousex + screenx ) % tileSize ),
                (int)( Math.floor( ( mousey + screeny ) / tileSize ) * tileSize - screeny ), //(int)( mousey - ( mousey + screeny ) % tileSize ),
                (int) tileSize * size,
                (int) tileSize * size
        );
    }
    
}
