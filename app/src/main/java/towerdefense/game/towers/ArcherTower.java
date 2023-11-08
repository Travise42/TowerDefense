package towerdefense.game.towers;

import java.awt.Graphics;

import towerdefense.game.Game;

public class ArcherTower extends Tower {

    final private static int width = 2;
    final private static int height = 2;

    public ArcherTower( Game game, int column, int row ) {
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
