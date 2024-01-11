package towerdefense.game.env;

import towerdefense.game.Game;
import towerdefense.game.Panel;

public class Camera {

    public float viewx, viewy;

    public Camera() {
        viewx = Map.INITIAL_OPEN_COLUMNS + 4;
        viewy = Map.INITIAL_OPEN_ROWS + 4;
    }

    public void expand() {
        viewx = Map.INITIAL_OPEN_COLUMNS + 4 + 2*Game.instance.getStage();
        viewy = Map.INITIAL_OPEN_ROWS + 4 + 2*0.7f*Game.instance.getStage();
    }

    public int getX() {
        return (int) ( Map.COLUMNS*Game.instance.map.getTileSize() - Panel.WIDTH ) / 2;
    }

    public int getY() {
        return (int) ( (Map.ROWS - 0.5f )*Game.instance.map.getTileSize() - Panel.HEIGHT ) / 2;
    }

    public void update() {

    }
    
}
