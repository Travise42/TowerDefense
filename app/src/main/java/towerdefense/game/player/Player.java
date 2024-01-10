package towerdefense.game.player;

public class Player {

    public int gold = 0;
    public int health = 0;

    public Player() {

    }

    public void newGame() {
        gold = 999999999;
        health = 100;
    }

    public void spend(float amount) {
        gold -= amount;
    }

    public void damage(float damage) {
        health -= damage;
    }

}
