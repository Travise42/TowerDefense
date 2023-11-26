package towerdefense.game.towers;

import java.awt.Graphics;

import towerdefense.game.Game;

public class BombTower extends Tower {

    final private static String IMG_PATH = "map/towers/bomb_tower/example.png";

    public BombTower( Game game, int column, int row ) {
        super( game, column, row, IMG_PATH );
    }

    public BombTower() { super(); }

    @Override
    public void draw( Graphics g ) {
        drawTower( g );
    }

    @Override
    public BombTower copy( Game game, int column, int row ) {
        return new BombTower( game, column, row );
    }

    @Override
    public int size() {
        return 2;
    }
    
}
