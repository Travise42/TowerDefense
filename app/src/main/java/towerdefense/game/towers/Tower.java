package towerdefense.game.towers;

import static towerdefense.func.ImageHandler.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import towerdefense.game.Game;

public abstract class Tower {

    protected int column;
    protected int row;

    protected int columnspan;
    protected int rowspan;

    protected BufferedImage image;

    protected Game game;

    protected Tower( Game game, int column, int row, int columnspan, int rowspan, String img_path ) {
        this.game = game;
        
        this.column = column;
        this.row = row;
        this.columnspan = columnspan;
        this.rowspan = rowspan;

        this.image = loadImage( img_path );

        updateScale();
    }

    public void updateScale() {
        int size = (int) ( columnspan * game.map.getTileSize() );
        image = resizeImage(image, size, size );
    }

    public abstract void draw( Graphics g );

    protected void drawTower( Graphics g ) {
        int x = (int)( column * game.map.getTileSize() - game.camera.getX() );
        int y = (int)( row * game.map.getTileSize() - game.camera.getY() );

        g.drawImage( image, x, y, game.panel );

        ////g.fillOval(
        ////        x, 
        ////        y, 
        ////        (int)( columnspan * game.map.getTileSize() ), 
        ////        (int)( rowspan * game.map.getTileSize() )
        ////);
    }
    
}
