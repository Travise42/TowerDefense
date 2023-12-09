package towerdefense.game.env;

import static towerdefense.func.ImageHandler.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import towerdefense.game.Game;
import towerdefense.game.Panel;
import towerdefense.game.enemies.Enemy;
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
    public List<Enemy> enemies;
    
    public MapHandler( Game gameInstance ) {
        game = gameInstance;
        map = new Map( game );

        String map_dir = "map/tiles/";
        tiles = loadImages( map_dir + "wall.png", map_dir + "tile.png" );

        towers = new ArrayList<>();
        enemies = new ArrayList<>();

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

        for ( int i = 0; i < enemies.size(); i++ ) {
            enemies.get( i ).draw( g );
        }
    }

    public void update() {
        for ( int i = 0; i < enemies.size(); i++ ) {
            enemies.get( i ).move();
        }
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
        enemies.clear();
    }

    public void nextStage() {
        map.nextStage();
        resize();
    }

    public void editGrid( int column, int row, int columnspan, int rowspan, boolean open ) {
        map.fill( column, row, columnspan, rowspan, open );
    }

    public void newEnemy( float enemy_type ) {
        enemies.add( new Enemy( game, enemy_type ) );
    }

    public int[] getEntrance() {
        return new int[]{ ( int )( game.camera.getX() - getTileSize() ), ( int )( Map.ROWS/2f * getTileSize() ) };
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
