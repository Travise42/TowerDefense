package towerdefense.game.towers;

import static towerdefense.func.ImageHandler.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.BasicStroke;
import java.awt.Color;

import towerdefense.game.Game;

public abstract class Tower {

    final private static int HIGHLIGHT_BORDER_THICKNESS = 4;

    protected int column;
    protected int row;

    protected Game game;

    protected BufferedImage image;
    protected BufferedImage highlight;

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
        
        this.tier = 0;
        this.path = -1;

        resize();
    }

    public void draw( Graphics g) {
        drawTower( g );
    }

    public void select() {
        loadHighlight();
    }

    protected void drawTower( Graphics g ) {
        int x = (int)( column * game.map.getTileSize() - game.camera.getX() );
        int y = (int)( ( row + getSize() ) * game.map.getTileSize() - image.getHeight() - game.camera.getY() );

        g.drawImage( image, x, y, game.panel );
    }

    public void drawHighlight( Graphics g ) {
        int x = (int)( column * game.map.getTileSize() - game.camera.getX() );
        int y = (int)( ( row + getSize() ) * game.map.getTileSize() - image.getHeight() - game.camera.getY() );

        g.drawImage( highlight, x - HIGHLIGHT_BORDER_THICKNESS, y - HIGHLIGHT_BORDER_THICKNESS, game.panel );
    }

    protected void loadHighlight() {
        // Create scaled copy of tower
        highlight = new BufferedImage(
            image.getWidth() + HIGHLIGHT_BORDER_THICKNESS * 2,
            image.getHeight() + HIGHLIGHT_BORDER_THICKNESS * 2,
            BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g2d = highlight.createGraphics();
        g2d.setStroke( new BasicStroke( HIGHLIGHT_BORDER_THICKNESS ) );
        g2d.setColor( Color.WHITE );

        for ( int x = 0; x < image.getWidth(); x++ ) {
        for ( int y = 0; y < image.getHeight(); y++ ) {
            if ( isOpaque( x, y ) && isEdgePixel( x, y ) )
                g2d.drawRect( x + HIGHLIGHT_BORDER_THICKNESS, y + HIGHLIGHT_BORDER_THICKNESS, 1, 1 );
        } }
        g2d.dispose();
    }

    private boolean isOpaque( int x, int y ) {
        return ( image.getRGB( x, y ) >> 24 ) != 0x00;
    }

    private boolean isEdgePixel( int x, int y ) {
        return x == 0 || y == 0 || x == image.getWidth() - 1 || y == image.getHeight() - 1 ||
                (image.getRGB(x - 1, y) >> 24) == 0x00 || (image.getRGB(x + 1, y) >> 24) == 0x00 ||
                (image.getRGB(x, y - 1) >> 24) == 0x00 || (image.getRGB(x, y + 1) >> 24) == 0x00;
    }

    public void resize() {
        // Retrive image
        image = tier == 0 ? getGraphics().getTowerImage()
                            : getGraphics().getTowerImage( path, tier - 1, TowerGraphics.IDLE );

        // Scale image
        int size = ( int )( getSize() * game.map.getTileSize() );
        image = resizeImage( image, size, image.getHeight() * size / image.getWidth() );

        // Refresh highlight
        if ( this == game.mi.selectedTower ) loadHighlight();
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
        resize();
    }

    public void remove() {
        game.map.editGrid( column, row, getSize(), getSize(), true );
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
