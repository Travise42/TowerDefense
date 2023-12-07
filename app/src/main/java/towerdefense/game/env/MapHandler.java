package towerdefense.game.env;

import static towerdefense.func.ImageHandler.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import towerdefense.game.Game;
import towerdefense.game.Panel;
import towerdefense.game.gui.MapInteractions;
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

        String map_dir = "map/tiles/";
        tiles = loadImages( map_dir + "wall.png", map_dir + "tile.png" );

        towers = new ArrayList<>();

        resize();
    }

    public void draw( Graphics g ) {
        float size = getTileSize();
        float dx = ( Panel.WIDTH - Map.COLUMNS*size ) / 2;
        float dy = ( Panel.HEIGHT - Map.ROWS*size ) / 2;

        for ( int row = 0; row < Map.ROWS; row++ ) {
            for ( int column = 0; column < Map.COLUMNS; column++ ) {
                boolean isPath = map.isOpen( column, row );
                
                g.drawImage( 
                        tiles[ isPath ? T_TILE : T_WALL ],
                        (int)( column*size + dx ),
                        (int)( row*size + dy ),
                        game.panel
                );
            }
        }

        for ( int i = towers.size() - 1; 0 <= i; i-- ) {
            towers.get( i ).draw( g, towers.get( i ) == game.mi.selectedTower );
        }
    }

    public void update() {
        System.out.println(map.getOpenRows());
    }

    public void resize() {
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

    public void newGame() {
        map.reset();
        towers.clear();
    }

    public void nextStage() {
        map.nextStage();
        resize();
    }

    public void editGrid( int column, int row, int columnspan, int rowspan, boolean open ) {
        map.fill( column, row, columnspan, rowspan, open );
    }

    public int[] getEntrance() {
        return new int[]{ ( Map.INITIAL_OPEN_COLUMNS - map.COLUMNS ) / 2 };
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
