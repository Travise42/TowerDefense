package towerdefense.game.towers;

import static towerdefense.func.ImageHandler.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import towerdefense.game.Game;

public abstract class Tower {

    protected int column;
    protected int row;

    protected BufferedImage image;

    private Game game;

    protected int[] upgrades = { 0, 0 };

    // Empty tower
    protected Tower() {
        upgrades = null;
    }

    protected Tower( Game game, int column, int row, String img_path ) {
        this.game = game;
        
        this.column = column;
        this.row = row;

        this.image = loadImage( img_path );

        game.map.editGrid( column, row, getSize(), getSize(), false );

        resize();
    }

    public abstract void draw( Graphics g );

    protected void drawTower( Graphics g ) {
        int x = (int)( column * game.map.getTileSize() - game.camera.getX() );
        int y = (int)( row * game.map.getTileSize() - game.camera.getY() );

        g.drawImage( image, x, y, game.panel );
    }

    public void resize() {
        int graphicSize = ( int )( getSize() * game.map.getTileSize() );
        image = resizeImage( image, graphicSize, graphicSize );
    }

    public void upgrade( int path ) {
        if ( path < 0 || 1 < path ) {
            System.out.println( "Invalid Path: " + path + "!" );
            return;
        }
        if ( upgrades[ path ] == 4 ) {
            System.out.println( "Invalid Tier: 5!" );
            return;
        }
        upgrades[ path ] += 1;
    }

    public int getUpgrade( int path ) {
        if ( path < 0 || 1 < path ) {
            System.out.println( "Invalid Path: " + path + "!" );
            return 0;
        }
        return upgrades[ path ];
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public abstract Tower createNew( Game game, int column, int row );

    public abstract int getSize();
    
}
