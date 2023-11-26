package towerdefense.game.towers;

import static towerdefense.func.ImageHandler.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import towerdefense.game.Game;

public abstract class Tower {

    protected int column;
    protected int row;

    protected BufferedImage image;

    public Game game;

    protected Tower( Game game, int column, int row, String img_path ) {
        this.game = game;
        
        this.column = column;
        this.row = row;

        this.image = loadImage( img_path );

        game.map.editGrid( column, row, size(), size(), false );

        resize();
    }

    protected Tower() {}

    public void resize() {
        int graphicSize = (int) ( size() * game.map.getTileSize() );
        image = resizeImage(image, graphicSize, graphicSize );
    }

    public abstract void draw( Graphics g );

    protected void drawTower( Graphics g ) {
        int x = (int)( column * game.map.getTileSize() - game.camera.getX() );
        int y = (int)( row * game.map.getTileSize() - game.camera.getY() );

        g.drawImage( image, x, y, game.panel );
    }

    public abstract Tower copy( Game game, int column, int row );

    abstract public int size();
    
}
