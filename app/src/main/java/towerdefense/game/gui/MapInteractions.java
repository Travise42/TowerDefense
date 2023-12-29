package towerdefense.game.gui;

import towerdefense.game.Game;
import towerdefense.game.Panel;
import towerdefense.game.env.Map;
import towerdefense.game.towers.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapInteractions {

    Game game;

    final public static int NO_TOWER = -1;
    final public static int WIZARD_TOWER = 0;
    final public static int BOMB_TOWER = 1;
    final public static int ARCHER_TOWER = 2;
    final public static int WALL_TOWER = 3;
    final public static int TROOP_TOWER = 4;

    final public static List<Tower> TOWER = Arrays.asList( 
        new WizardTower(),
        new BombTower(),
        new ArcherTower(),
        new WallTower(),
        new TroopTower()
    );

    private int selectedToPlace;
    public Tower selectedTower;

    public MapInteractions( Game game ) {
        this.game = game;

        selectedToPlace = NO_TOWER;
        selectedTower = null;
    }

    public void selectTowerPlacement( int type ) {
        selectedToPlace = type;
    }

    public void drawHighlightedRegion( Graphics g, int mouseX, int mouseY ) {
        if ( selectedToPlace == NO_TOWER ) return;

        // Get tower dimensions
        float tileSize = game.map.getTileSize();
        int towerSize = TOWER.get( selectedToPlace ).getSize();
        int tileOffset = ( int )( tileSize * ( towerSize - 1 ) / 2 );

        // Get the furthest left column of the tower
        int towerColumn = getColumn( mouseX - tileOffset );

        // Get the furthest up row of the tower
        int towerRow = getRow( mouseY - tileOffset );

        g.setColor( new Color( 255, 255, 255, 100 ) );
        g.fillRect(
                ( int )( towerColumn * tileSize - game.camera.getX() ),
                ( int )( towerRow * tileSize - game.camera.getY() ),
                ( int )( towerSize * tileSize ),
                ( int )( towerSize * tileSize )
        );
    }

    public void interactWithMap( int mouseX, int mouseY ) {
        if ( selectedToPlace == NO_TOWER ) {
            selectedTower = null;
            selectHoveredTower( mouseX, mouseY );
            return;
        };

        placeTower( mouseX, mouseY, TOWER.get( selectedToPlace ) );

        selectedToPlace = NO_TOWER;
    }

    private void selectHoveredTower( int mouseX, int mouseY ) {
        int hoveredColumn = getColumn( mouseX );
        int hoveredRow = getRow( mouseY );

        deselectTower();
        for ( Tower tower : game.map.towers ) {
            if ( tower.getColumn() <= hoveredColumn && hoveredColumn < tower.getColumn() + tower.getSize()
                    && tower.getRow() <= hoveredRow && hoveredRow < tower.getRow() + tower.getSize() ) {
                selectTower( tower );
                return;
            }
        }
    }

    private void deselectTower() {
        selectedTower = null;
    }

    private void selectTower( Tower tower ) {
        selectedTower = tower;
        selectedTower.select();

        // Move tower to front of tower list
        // int index1 = game.map.towers.indexOf( selectedTower );
        // game.map.towers.set(index1, game.map.towers.get(0));
        // game.map.towers.set(0, selectedTower);
    }

    private void placeTower( int mouseX, int mouseY, Tower tower ) {
        // Get tower dimensions
        float tileSize = game.map.getTileSize();
        int towerSize = tower.getSize();
        int tileOffset = ( int )( tileSize * ( towerSize - 1 ) / 2 );

        // Get the furthest left column of the tower
        int towerColumn = getColumn( mouseX - tileOffset );

        // Get the furthest up row of the tower
        int towerRow = getRow( mouseY - tileOffset );

        boolean SPACE_IS_NOT_AVAIABLE = game.map.isObstructed( towerColumn, towerRow, towerSize );
        if ( SPACE_IS_NOT_AVAIABLE ) return;

        game.map.addTower( tower.createNew( game, towerColumn, towerRow ) );
    }

    public void upgradeSelectedTower( int path ) {
        if ( path < 0 || 1 < path ) {
            System.out.println( path + " is not a valid path to upgrade!" );
            return;
        }
        if ( selectedTower.getUpgradeInfo() == null ) return;
        if ( 4 <= selectedTower.getUpgradeTier() ) return;
        if ( game.player.gold < selectedTower.getUpgradeInfo().getCost( path, selectedTower.getUpgradeTier() ) ) return;
        selectedTower.upgrade( path );
    }

    public void deleteSelectedTower() {
        selectedTower.remove();
        game.map.towers.remove( selectedTower );
    }

    private int getColumn( int x ) {
        return (int) ( ( x + game.camera.getX() ) / game.map.getTileSize() );
    }

    private int getRow( int y ) {
        return (int) ( ( y + game.camera.getY() ) / game.map.getTileSize() );
    }
    
}
