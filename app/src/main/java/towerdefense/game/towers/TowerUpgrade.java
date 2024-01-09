package towerdefense.game.towers;

public class TowerUpgrade {

    private String[][] upgradeNames, upgradeDescriptions;
    private int[][] upgradeCosts;

    public TowerUpgrade( String tower_id ) {
        upgradeNames = new String[2][4];
        upgradeDescriptions = new String[2][4];
        upgradeCosts = new int[2][4];
    }

    public void setUpgradeNames( int path, String... names ) {
        upgradeNames[ path ] = names;
    }

    public void setUpgradeDescriptions( int path, String... descriptions ) {
        upgradeDescriptions[ path ] = descriptions;
    }

    public void setUpgradeCosts( int path, int... costs ) {
        upgradeCosts[ path ] = costs;
    }

    public String getName( int path, int tier ) {
        return upgradeNames[ path ][ tier ];
    }

    public String getDescription( int path, int tier ) {
        return upgradeDescriptions[ path ][ tier ];
    }

    public int getCost( int path, int tier ) {
        return upgradeCosts[ path ][ tier ];
    }
    
}
