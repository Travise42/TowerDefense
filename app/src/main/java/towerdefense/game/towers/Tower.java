package towerdefense.game.towers;

import static towerdefense.func.ImageHandler.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import towerdefense.game.Game;

public abstract class Tower {

    protected int column;
    protected int row;

    protected Game game;

    protected BufferedImage image;

    protected int path;
    protected int tier;

    // Empty tower
    protected Tower() {
        tier = 0;
        path = -1;
    }

    protected Tower( Game game, int column, int row, String tower_id ) {
        this.game = game;
        
        this.column = column;
        this.row = row;

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
        image = tier == 0 ? getGraphics().getTowerImage()
                            : getGraphics().getTowerImage( path, tier - 1, TowerGraphics.IDLE );

        int graphicSize = ( int )( getSize() * game.map.getTileSize() );
        image = resizeImage( image, graphicSize, graphicSize );
    }

    public void upgrade( int path ) {
        if ( path < 0 || 1 < path ) {
            System.out.println( "Invalid Path: " + path + "!" );
            return;
        }

        if ( this.path == -1 ) this.path = path;
        if ( this.path != path ) return;

        if ( tier == 4 ) {
            System.out.println( "Invalid Tier: 5!" );
            return;
        }
        tier += 1;
    }

    public int getUpgradeTier() {
        return tier;
    }

    public int getUpgradePath() {
        return path;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public abstract Tower createNew( Game game, int column, int row );

    public abstract int getSize();

    public abstract TowerUpgrade getUpgradeInfo();

    public abstract TowerGraphics getGraphics();
    
}
