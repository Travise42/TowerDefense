package towerdefense.game.towers;

import java.awt.Graphics;

import towerdefense.game.Game;

public class WallTower extends Tower {

    final private static int width = 1;
    final private static int height = 1;

    public WallTower( Game game, int column, int row ) {
        super( game, column, row, width, height );
    }

    @Override
    public void draw( Graphics g ) {
        g.fillRect(
                (int)( column * game.map.getTileSize() ), 
                (int)( row * game.map.getTileSize() ), 
                (int)( columnspan * game.map.getTileSize() ), 
                (int)( rowspan * game.map.getTileSize() )
        );
    }
    
}
