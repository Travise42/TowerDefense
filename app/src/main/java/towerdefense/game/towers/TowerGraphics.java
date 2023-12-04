package towerdefense.game.towers;

import static towerdefense.func.ImageHandler.loadImage;
import static towerdefense.func.ImageHandler.loadImages;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;

import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.awt.image.BufferedImage;

public class TowerGraphics {

    final public static int LEFT = 0;
    final public static int RIGHT = 1;

    final public static int HIGHEST_TIER = 3; // 0, 1, 2, [3]

    final public static int IDLE = 0;
    final public static int ATTACKING = 1;
    final public static int BROKEN = 2;
    final public static int DAMAGED = 3; // unlikely to develop
    final public static int BUILT = 4; // unlikely to develop
    final public static int UPGRADED = 5; // unlikely to develop

    private BufferedImage baseImage;
    private BufferedImage purchaseImage;
    private BufferedImage[][] towerImages; // [path][tier]
    private BufferedImage[][] upgradeImages; // [path][tier]

    public TowerGraphics( String tower_id ) {
        init( tower_id );
    }

    public TowerGraphics( String tower_id, boolean hasTiers ) {
        if ( hasTiers ) init( tower_id );
        else loadBaseImages( tower_id );
    }

    private void init( String tower_id ) {
        loadBaseImages( tower_id );
        loadUpgradeImages( tower_id );
    }

    private void loadBaseImages( String tower_id ) {
        String map_dir = "map/towers/" + tower_id + "/";
        String gui_dir = "gui/towers/" + tower_id + "/";

        // Load 0-0 Tower Aniamtion
        baseImage = loadImage( map_dir + "tier_0.png" );
        purchaseImage = loadImage( gui_dir + "icon.png" );
    }

    private void loadUpgradeImages( String tower_id ) {
        String map_dir = "map/towers/" + tower_id + "/";
        String gui_dir = "gui/towers/" + tower_id + "/";
        String[] paths = { "left_path/", "right_path/" };
        
        // Tower images
        towerImages = new BufferedImage[2][4];
        upgradeImages = new BufferedImage[2][4];

        for ( int path = 0; path < 2; path++ ) {
            for ( int tier = 0; tier < 4; tier++ ) {
                towerImages[path][tier] = loadImage( map_dir + paths[ path ] + "tier_" + ( tier + 1 ) + ".png" );
                upgradeImages[path][tier] = loadImage( gui_dir + "upgrades/" + paths[ path ] + "tier_" + ( tier + 1 ) + ".png" );
            }
        }
    }

    public BufferedImage getUpgradeImage( int path, int tier ) {
        return upgradeImages[ path ][ tier ];
    }

    public BufferedImage getTowerImage( int path, int tier, int animation_stage ) {
        return towerImages[ path ][ tier ];
    }

    public BufferedImage getPurchaseImage() {
        return purchaseImage;
    }

    public BufferedImage getTowerImage() {
        return baseImage;
    }
    
}
