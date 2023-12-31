package towerdefense.game.env;

import towerdefense.game.Game;

public class MapCalculations {

    public static int getColumnOf(int x) {
        return (int) ((x + Game.instance.camera.getX()) / Game.instance.map.getTileSize());
    }

    public static int getRowOf(int y) {
        return (int) ((y + Game.instance.camera.getY()) / Game.instance.map.getTileSize());
    }

    //TODO
    public static int getXOf(int column) {
        return 0;
    }

    //TODO
    public static int getYOf(int row) {
        return 0;
    }

    public static int getCameraXOf(int column) {
        return (int) (column * Game.instance.map.getTileSize() - Game.instance.camera.getX());
    }

    public static int getCameraYOf(int row) {
        return (int) (row * Game.instance.map.getTileSize() - Game.instance.camera.getY());
    }

    public static int covertToCameraX(int x) {
        return x - Game.instance.camera.getX();
    }

    public static int covertToCameraY(int y) {
        return y - Game.instance.camera.getY();
    }
    
}
