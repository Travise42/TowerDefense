package towerdefense.game.env;

import static towerdefense.func.ImageHandler.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import towerdefense.game.Game;
import towerdefense.game.Panel;

public class MapHandler {

    final private static int TILE_SIZE = 32;

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
        float dx = ( Panel.WIDTH - Map.COLUMNS * getTileSize() ) / 2;
        float dy = ( Panel.HEIGHT - Map.ROWS * getTileSize() ) / 2;

        for ( int r = 0; r < Map.ROWS; r++ ) {
            for ( int c = 0; c < Map.COLUMNS; c++ ) {
                g.drawImage( tiles[ map.getMap()[c][r] ? T_TILE : T_TREE ],
                    (int)( dx + c * getTileSize() ), (int)( dy + r * getTileSize() ), game.panel );
            }
        }
    }

    public void update() {
        for ( int i = 0; i < tiles.length; i++ ) {
            tiles[i] = resizeImage( tiles[i], (int) getTileSize(), (int) getTileSize() );
        }
    }

    public int getTileSize() {
        return (int)( Panel.HEIGHT / game.camera.viewy );
    }

}
