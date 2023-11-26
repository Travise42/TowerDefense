package towerdefense.game.towers;

import java.awt.Graphics;

import towerdefense.game.Game;

public class WizardTower extends Tower {

    final private static String IMG_PATH = "map/towers/wizard_tower/example.png";

    public WizardTower( Game game, int column, int row ) {
        super( game, column, row, IMG_PATH );
    }

    public WizardTower() { super(); }

    @Override
    public void draw( Graphics g ) {
        drawTower( g );
    }

    @Override
    public WizardTower copy( Game game, int column, int row ) {
        return new WizardTower( game, column, row );
    }

    @Override
    public int size() {
        return 2;
    }
    
}
