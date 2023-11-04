package towerdefense.game.env;

import towerdefense.game.Game;

public class Camera {

    private Game game;

    public float viewx, viewy;

    public Camera( Game gameInstance ) {
        game = gameInstance;

        viewy = Map.INITIAL_OPEN_COLUMNS + 2;
        viewy = Map.INITIAL_OPEN_ROWS + 2;
    }

    public void expand() {
        viewx = Map.INITIAL_OPEN_COLUMNS + 2 + 2*game.getStage();
        viewy = Map.INITIAL_OPEN_ROWS + 2 + 2*game.getStage();
    }

    public void update() {

    }

    public float getWidth() {
        return viewx * game.map.getTileSize();
    }

    public float getHeight() {
        return viewy * game.map.getTileSize();
    }
    
}
