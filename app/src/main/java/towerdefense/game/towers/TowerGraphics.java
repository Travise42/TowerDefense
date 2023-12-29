package towerdefense.game.towers;

import static towerdefense.func.ImageHandler.loadImage;

import java.awt.image.BufferedImage;

public class TowerGraphics {

    final public static int LEFT = 0;
    final public static int RIGHT = 1;

    final public static int HIGHEST_TIER = 3; // 0, 1, 2, [3]

    ////final public static int IDLE = 0;
    ////final public static int ATTACKING = 1;
    ////final public static int BROKEN = 2;
    ////final public static int DAMAGED = 3; // unlikely to develop
    ////final public static int BUILT = 4; // unlikely to develop
    ////final public static int UPGRADED = 5; // unlikely to develop
    
    final private static String[] PATH = { "left_path/", "right_path/" };

    private BufferedImage baseImage;
    private BufferedImage purchaseImage;
    private BufferedImage[][] towerImages; // [path][tier]
    private BufferedImage[][] upgradeImages; // [path][tier]
    private BufferedImage[] entityImages;

    private String towerId;
    private String map_dir;
    private String gui_dir;

    public TowerGraphics( String tower_id, String[] entities ) {
        init( tower_id, entities, true );
    }

    public TowerGraphics( String tower_id, String[] entities, boolean hasTiers ) {
        init( tower_id, entities, hasTiers );
    }

    private void init( String tower_id, String[] entities, boolean hasTiers ) {
        towerId = tower_id;

        map_dir = "map/towers/" + towerId + "/";
        gui_dir = "gui/towers/" + towerId + "/";

        loadBaseImages();
        if ( hasTiers ) loadUpgradeImages();
        if ( entities != null ) loadEntities( entities );
    }

    private void loadBaseImages() {
        // Load 0-0 Tower
        baseImage = loadImage( map_dir + "tier_0.png" );
        purchaseImage = loadImage( gui_dir + "icon.png" );
    }

    private void loadUpgradeImages() {
        towerImages = new BufferedImage[2][4];
        upgradeImages = new BufferedImage[2][4];

        for ( int path = 0; path < 2; path++ ) {
            for ( int tier = 0; tier < 4; tier++ ) {
                towerImages[path][tier] = loadImage( map_dir + PATH[ path ] + "tier_" + ( tier + 1 ) + ".png" );
                upgradeImages[path][tier] = loadImage( gui_dir + "upgrades/" + PATH[ path ] + "tier_" + ( tier + 1 ) + ".png" );
            }
        }
    }

    private void loadEntities( String[] entities ) {
        entityImages = new BufferedImage[ entities.length ];
        
        for ( int i = 0; i < entities.length; i++ ) {
            entityImages[ i ] = loadImage( map_dir + "entities/" + entities[ i ] + ".png" );
        }
    }

    public BufferedImage getUpgradeImage( int path, int tier ) {
        return upgradeImages[ path ][ tier ];
    }

    public BufferedImage getTowerImage( int path, int tier ) {
        return towerImages[ path ][ tier ];
    }

    public BufferedImage getEntityImage( int id ) {
        return entityImages[ id ];
    }

    public BufferedImage getPurchaseImage() {
        return purchaseImage;
    }

    public BufferedImage getTowerImage() {
        return baseImage;
    }
    
}
