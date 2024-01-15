package towerdefense.game.env;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;

import towerdefense.game.Game;
import towerdefense.game.towers.ArcherTower;
import towerdefense.game.towers.CannonTower;
import towerdefense.game.towers.Tower;
import towerdefense.game.towers.TroopTower;
import towerdefense.game.towers.WallTower;
import towerdefense.game.towers.WizardTower;

public class MapInteractions {

    final public static int NO_TOWER = -1;
    final public static int WIZARD_TOWER = 0;
    final public static int CANNON_TOWER = 1;
    final public static int ARCHER_TOWER = 2;
    final public static int WALL_TOWER = 3;
    final public static int TROOP_TOWER = 4;

    // For ease of access to tower information
    final public static List<Tower> TOWER = Arrays.asList(
            new WizardTower(),
            new CannonTower(),
            new ArcherTower(),
            new WallTower(),
            new TroopTower());

    private int selectedToPlace;
    private Tower selectedTower;

    public MapInteractions() {
        selectedToPlace = NO_TOWER;
        selectedTower = null;
    }

    // Start placing tower
    public void selectTowerPlacement(int type) {
        selectedToPlace = type;
        deselectTower();
    }

    // Show the player where the tower will place
    public void drawHighlightedRegion(Graphics g, int mouseX, int mouseY) {
        if (selectedToPlace == NO_TOWER)
            return;

        final int TILE_SIZE = (int) Game.instance.map.getTileSize();
        final int TOWER_SIZE = TOWER.get(selectedToPlace).getSize() * TILE_SIZE;

        // Offset mouse to center of selections
        final int OFFSET = (TOWER_SIZE - TILE_SIZE) / 2;

        // Get floor view cords to grid
        int x = MapConv.columnToViewX(MapConv.viewXToColumn(mouseX - OFFSET));
        int y = MapConv.rowToViewY(MapConv.viewYToRow(mouseY - OFFSET));

        // Draw transparent rectangle
        g.setColor(new Color(255, 255, 255, 100));
        g.fillRect(x, y, TOWER_SIZE, TOWER_SIZE);
    }

    // Handle selecting and placing towers
    public void interactWithMap(int mouseX, int mouseY) {
        if (selectedToPlace == NO_TOWER) {
            selectHoveredTower(mouseX, mouseY);
            return;
        }

        placeTower(mouseX, mouseY, TOWER.get(selectedToPlace));
        selectedToPlace = NO_TOWER;
    }

    private void placeTower(int mouseX, int mouseY, Tower tower) {
        final int TILE_SIZE = (int) Game.instance.map.getTileSize();
        final int TOWER_SIZSE = tower.getSize() * TILE_SIZE;

        // Offset mouse to center of selections
        final int OFFSET = (TOWER_SIZSE - TILE_SIZE) / 2;

        int towerColumn = MapConv.viewXToColumn(mouseX - OFFSET);
        int towerRow = MapConv.viewYToRow(mouseY - OFFSET);

        boolean SPACE_IS_NOT_AVAIABLE = !Game.instance.map.map.isOpen(towerColumn, towerRow, tower.getSize(),
                tower.getSize());
        if (SPACE_IS_NOT_AVAIABLE)
            return;

        int cost = tower.getUpgradeInfo().getCost(0, 0);
        boolean NOT_ENOUGH_GOLD = Game.instance.player.gold < cost;
        if ( NOT_ENOUGH_GOLD )
            return;
        Game.instance.player.spend( cost );

        Game.instance.map.addTower(tower.createNew( towerColumn, towerRow));
    }

    private void selectHoveredTower(int mouseX, int mouseY) {
        final int HOVERED_COLUMN = MapConv.viewXToColumn(mouseX);
        final int HOVERED_ROW = MapConv.viewYToRow(mouseY);

        // Check if mouse is touching any of the towers
        for (Tower tower : Game.instance.map.towers) {
            boolean ABOVE_MOUSE = HOVERED_ROW < tower.getRow();
            if (ABOVE_MOUSE)
                break;

            final int BOTTOM_ROW = tower.getRow() + tower.getSize();
            final int RIGHT_COLUMN = tower.getColumn() + tower.getSize();

            boolean NOT_UNDER_MOUSE = HOVERED_COLUMN < tower.getColumn()
                    || RIGHT_COLUMN <= HOVERED_COLUMN
                    || BOTTOM_ROW <= HOVERED_ROW;
            if (NOT_UNDER_MOUSE)
                continue;

            boolean ALREADY_SELECTED = selectedTower == tower;
            if (ALREADY_SELECTED)
                break;

            selectTower(tower);
            return;
        }
        deselectTower();
    }

    @Deprecated
    public void selectHoveredTowerInBinary(int mouseX, int mouseY) {
        if (Game.instance.map.towers.size() == 0)
            return;

        final int HOVERED_COLUMN = MapConv.viewXToColumn(mouseX);
        final int HOVERED_ROW = MapConv.viewYToRow(mouseY);
        int pointer = (Game.instance.map.towers.size() - 1) / 2;
        boolean leftmost = false;
        Tower tower;
        while (true) {
            tower = Game.instance.map.towers.get(pointer);
            if (tower.getRow() + tower.getSize() <= HOVERED_ROW) {
                leftmost = true;
                pointer += 1;
                if (Game.instance.map.towers.size() <= pointer) {
                    deselectTower();
                    return;
                }
                continue;
            }
            if (leftmost || pointer == 0)
                break;
            pointer /= 2;
        }
        if (HOVERED_ROW < tower.getRow()) {
            deselectTower();
            return;
        }
        while (true) {
            if (tower.getColumn() <= HOVERED_COLUMN && HOVERED_COLUMN < tower.getColumn() + tower.getSize()) {
                if (selectedTower == tower)
                    break;
                selectTower(tower);
                return;
            }
            pointer++;
            if (Game.instance.map.towers.size() <= pointer)
                break;
            tower = Game.instance.map.towers.get(pointer);
            if (HOVERED_ROW < tower.getRow())
                break;
        }
        deselectTower();
    }

    private void selectTower(Tower tower) {
        selectedTower = tower;
        selectedTower.select();
    }

    public void deselectTower() {
        selectedTower = null;
    }

    public Tower getSelectedTower() {
        return selectedTower;
    }

    public void upgradeSelectedTower(int path) {
        boolean NO_TOWER_SELECTED = selectedTower == null;
        if (NO_TOWER_SELECTED)
            return;

        boolean INVALID_PATH = path > selectedTower.getUpgradeInfo().getPaths();
        if (INVALID_PATH)
            return;

        boolean MAX_TIER = selectedTower.getTier() >= selectedTower.getUpgradeInfo().getTiers();
        if (MAX_TIER)
            return;

        int cost = selectedTower.getUpgradeInfo().getCost(path, selectedTower.getTier());
        boolean NOT_ENOUGH_GOLD = Game.instance.player.gold < cost;
        if (NOT_ENOUGH_GOLD)
            return;

        Game.instance.player.spend(cost);

        selectedTower.upgrade(path);
    }

    public void deleteSelectedTower() {
        boolean NO_TOWER_SELECTED = selectedTower == null;
        if (NO_TOWER_SELECTED)
            return;
            
        selectedTower.remove();
    }

}
