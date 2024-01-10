package towerdefense.game.towers;

import static towerdefense.func.ImageHandler.loadImage;

import java.awt.image.BufferedImage;

public class TowerGraphics {

    final public static int LEFT = 0;
    final public static int RIGHT = 1;

    final public static int HIGHEST_TIER = 3; // 0, 1, 2, [3]

    //// final public static int IDLE = 0;
    //// final public static int ATTACKING = 1;
    //// final public static int BROKEN = 2;
    //// final public static int DAMAGED = 3; // unlikely to develop
    //// final public static int BUILT = 4; // unlikely to develop
    //// final public static int UPGRADED = 5; // unlikely to develop

    private BufferedImage baseImage;
    private BufferedImage purchaseImage;
    private BufferedImage[][] towerImages; // [path][tier]
    private BufferedImage[][] upgradeImages; // [path][tier]
    private BufferedImage[] entityImages;

    private String towerId;
    private String map_dir;
    private String gui_dir;

    private int paths;
    private int tiers;

    public TowerGraphics(String tower_id, int paths, int tiers, String[] entities) {
        towerId = tower_id;

        map_dir = "map/towers/" + towerId + "/";
        gui_dir = "gui/towers/" + towerId + "/";

        this.paths = paths;
        this.tiers = tiers;

        loadUpgradeImages();
        if (entities != null)
            loadEntities(entities);
    }

    private void loadBaseImages() {
        // Load 0-0 Tower
        baseImage = loadImage(map_dir + "tier_0.png");
        purchaseImage = loadImage(gui_dir + "icon.png");
    }

    private void loadUpgradeImages() {
        loadBaseImages();

        towerImages = new BufferedImage[paths][tiers];
        upgradeImages = new BufferedImage[paths][tiers];

        for (int path = 1; path <= paths; path++) {
            for (int tier = 1; tier <= tiers; tier++) {
                towerImages[path - 1][tier - 1] = loadImage(map_dir + "path_" + path + "/tier_" + tier + ".png");
                upgradeImages[path - 1][tier - 1] = loadImage(
                        gui_dir + "upgrades/" + "path_" + path + "/tier_" + tier + ".png");
            }
        }
    }

    private void loadEntities(String[] entities) {
        entityImages = new BufferedImage[entities.length];

        for (int i = 0; i < entities.length; i++) {
            entityImages[i] = loadImage(map_dir + "entities/" + entities[i] + ".png");
        }
    }

    public BufferedImage getUpgradeImage(int path, int tier) {
        return upgradeImages[path - 1][tier];
    }

    public BufferedImage getTowerImage(int path, int tier) {
        return towerImages[path - 1][tier];
    }

    public BufferedImage getEntityImage(int id) {
        return entityImages[id];
    }

    public BufferedImage getPurchaseImage() {
        return purchaseImage;
    }

    public BufferedImage getTowerImage() {
        return baseImage;
    }

}
