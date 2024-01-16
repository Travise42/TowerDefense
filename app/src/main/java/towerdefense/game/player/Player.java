package towerdefense.game.player;

public class Player {

    final private static int STARTING_GOLD = 600;
    final private static int STARTING_HEALTH = 600;

    private int gold = 0;
    private int health = 0;

    public Player() {

    }

    public void newGame() {
        resetGold();
        resetHealth();
    }

    // Reset

    /**
     * Reset player gold
     */
    public void resetGold() {
        gold = STARTING_GOLD;
    }

    /**
     * Reset player health
     */
    public void resetHealth() {
        health = STARTING_HEALTH;
    }

    // Increase

    /**
     * Increase player gold
     * 
     * @param amount gold
     */
    public void earn(float amount) {
        gold += amount;
    }

    /**
     * Increase player health
     * 
     * @param amount lives
     */
    public void heal(float amount) {
        health += amount;
    }

    // Decrease

    /**
     * Decrease player gold
     * 
     * @param amount gold
     */
    public void spend(float amount) {
        gold -= amount;
    }

    /**
     * Decrease player health
     * 
     * @param amount lives
     * @return true if alive, false if dead
     */
    public boolean damage(float amount) {
        return (health -= amount) > 0;
    }

    // Calculations

    /**
     * Check whether the player has enough gold
     * 
     * @param amount value to check
     * @return true if the player has enough gold, false if the player does not have
     *         enough gold
     */
    public boolean canAfford(int amount) {
        return gold >= amount;
    }

    // Getters

    public int getGold() {
        return gold;
    }

    public int getHealth() {
        return health;
    }

}
