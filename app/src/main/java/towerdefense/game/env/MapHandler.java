package towerdefense.game.env;

import static towerdefense.func.ImageHandler.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import towerdefense.game.Game;
import towerdefense.game.Panel;

public class MapHandler {

    final private static int T_TREE = 0;
    final private static int T_TILE = 1;

    private static BufferedImage[] tiles;

    private Game game;
    public Map map;
    
    public MapHandler( Game gameInstance ) {
        game = gameInstance;
        map = new Map( game );

        tiles = loadImages( "map/tree.png", "map/tile.png" );
    }

    public void draw( Graphics g ) {
        float size = getTileSize();
        float dx = ( Panel.WIDTH - Map.COLUMNS*size ) / 2;
        float dy = ( Panel.HEIGHT - Map.ROWS*size ) / 2;

        for ( int row = 0; row < Map.ROWS; row++ ) {
            for ( int column = 0; column < Map.COLUMNS; column++ ) {
                boolean isPath = map.getMap()[column][row];
                
                g.drawImage( 
                    tiles[ isPath ? T_TILE : T_TREE ],
                    (int)( column*size + dx ),
                    (int)( row*size + dy ),
                    game.panel );
            }
        }
    }

    public void update() {
        for ( int i = 0; i < tiles.length; i++ ) {
            tiles[i] = resizeImage( 
                tiles[i], 
                getTileSize(), 
                getTileSize() );
        }
    }

    public int getTileSize() {
        return (int)( Panel.HEIGHT / game.camera.viewy );
    }

}
