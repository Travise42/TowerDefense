package towerdefense.game.player;

import towerdefense.game.Game;

public class Player {

    Game game;
    
    public int gold = 0;
    public int health = 0;

    public Player( Game game ) {
        this.game = game;
    }

    public void newGame() {
        gold = 500;
        health = 100;
    }
    
}