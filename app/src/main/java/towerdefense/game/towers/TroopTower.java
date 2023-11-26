package towerdefense.game.towers;

import java.awt.Graphics;

import towerdefense.game.Game;

public class TroopTower extends Tower {

    final private static String IMG_PATH = "map/towers/troop_tower/example.png";

    public TroopTower( Game game, int column, int row ) {
        super( game, column, row, IMG_PATH );
    }

    public TroopTower() { super(); }

    @Override
    public void draw( Graphics g ) {
        drawTower( g );
    }

    @Override
    public TroopTower copy( Game game, int column, int row ) {
        return new TroopTower( game, column, row );
    }

    @Override
    public int size() {
        return 2;
    }
    
}
