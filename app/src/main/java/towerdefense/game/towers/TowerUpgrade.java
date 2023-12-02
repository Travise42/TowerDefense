package towerdefense.game.towers;

import static towerdefense.func.ImageHandler.*;

import java.awt.image.BufferedImage;

public class TowerUpgrade {

    private String[][] upgradeNames, upgradeDescriptions;
    private int[][] upgradeCosts;

    private BufferedImage[][] upgradeImages;

    public TowerUpgrade( String tower_id ) {
        upgradeNames = new String[2][4];
        upgradeDescriptions = new String[2][4];
        upgradeCosts = new int[2][4];
        upgradeImages = new BufferedImage[2][4];

        upgradeImages = new BufferedImage[2][4];

        String left_path = "map/towers/" + tower_id + "/upgrades/left/";
        String right_path = "map/towers/" + tower_id + "/upgrades/right/";

        for ( int tier = 0; tier < 4; tier++ ) {
            upgradeImages[0][tier] = loadImage( left_path + ( tier + 1 ) + ".png" );
            upgradeImages[1][tier] = loadImage( right_path + ( tier + 1 ) + ".png" );
        }
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

    public BufferedImage getImage( int path, int tier ) {
        return upgradeImages[ path ][ tier ];
    }
    
}
