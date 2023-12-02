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

    protected int[] tiers = new int[2];
    public static TowerUpgrade upgradeInfo;

    // Empty tower
    protected Tower() {
        tiers = null;
    }

    protected Tower( Game game, int column, int row, String tower_id ) {
        this.game = game;
        
        this.column = column;
        this.row = row;

        this.image = loadImage( "map/towers/" + tower_id + "/example.png" );

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
        if ( tiers[ path ] == 4 ) {
            System.out.println( "Invalid Tier: 5!" );
            return;
        }
        tiers[ path ] += 1;
    }

    public int getUpgradeTier( int path ) {
        if ( path < 0 || 1 < path ) {
            System.out.println( "Invalid Path: " + path + "!" );
            return 0;
        }
        return tiers[ path ];
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
