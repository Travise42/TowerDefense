package towerdefense.game.enemies;

import towerdefense.game.Game;

public class Enemy {

    public static final float PHOTON = 0.2f;
    public static final float NINJA = 0.4f;
    public static final float FAST = 0.6f;

    public static final float NORMAL = 0.75f;

    public static final float STRONG = 0.9f;
    public static final float BEAST = 1.2f;
    public static final float TANK = 1.8f;

    protected Game game;

    protected int type;
    protected int x;
    protected int y;

    protected Enemy( Game gameInstance, int enemy_type ) {
        game = gameInstance;
        type = enemy_type;

        int[] pos = game.map.getEntrance();
        x = pos[0];
        y = pos[1];
    }
    
}
