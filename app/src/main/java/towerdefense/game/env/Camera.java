package towerdefense.game.env;

import towerdefense.game.Game;

public class Camera {

    private Game game;

    public float viewx, viewy;

    public Camera( Game gameInstance ) {
        game = gameInstance;

        viewx = Map.INITIAL_OPEN_COLUMNS + 4;
        viewy = Map.INITIAL_OPEN_ROWS + 4;
    }

    public void expand() {
        viewx = Map.INITIAL_OPEN_COLUMNS + 4 + 2*game.getStage();
        viewy = Map.INITIAL_OPEN_ROWS + 4 + 2*0.7f*game.getStage();
    }

    public void update() {

    }
    
}
