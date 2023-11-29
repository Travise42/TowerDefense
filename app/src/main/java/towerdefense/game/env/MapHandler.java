package towerdefense.game.env;

import static towerdefense.func.ImageHandler.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import towerdefense.game.Game;
import towerdefense.game.Panel;

import towerdefense.game.towers.Tower;

import java.util.List;
import java.util.ArrayList;

public class MapHandler {

    final private static int T_WALL = 0;
    final private static int T_TILE = 1;

    private static BufferedImage[] tiles;

    private Game game;
    public Map map;

    public List<Tower> towers;
    
    public MapHandler( Game gameInstance ) {
        game = gameInstance;
        map = new Map( game );

        tiles = loadImages( "map/terrain/wall.png", "map/terrain/tile.png" );

        towers = new ArrayList<>();
    }

    public void draw( Graphics g ) {
        float size = getTileSize();
        float dx = ( Panel.WIDTH - Map.COLUMNS*size ) / 2;
        float dy = ( Panel.HEIGHT - Map.ROWS*size ) / 2;

        for ( int row = 0; row < Map.ROWS; row++ ) {
            for ( int column = 0; column < Map.COLUMNS; column++ ) {
                boolean isPath = map.getGrid()[ column ][ row ];
                
                g.drawImage( 
                        tiles[ isPath ? T_TILE : T_WALL ],
                        (int)( column*size + dx ),
                        (int)( row*size + dy ),
                        game.panel
                );
            }
        }
    }

    public void update() {
        for ( int i = 0; i < tiles.length; i++ ) {
            tiles[i] = resizeImage( 
                tiles[i], 
                (int) Math.ceil( getTileSize() ), 
                (int) Math.ceil( getTileSize() ) );
        }
        for ( int i = 0; i < towers.size(); i++ ) {
            towers.get(i).resize();
        }
    }

    public void editGrid( int column, int row, int columnspan, int rowspan, boolean open ) {
        map.fill( column, row, columnspan, rowspan, open );
    }

    public boolean isObstructed( int column, int row, int size ) {
        return map.check( column, row, size, size );
    }

    public boolean isObstructed( int column, int row, int columnspan, int rowspan ) {
        return map.check( column, row, columnspan, rowspan );
    }

    public float getTileSize() {
        return Panel.WIDTH / (game.camera.viewx);
    }

}
