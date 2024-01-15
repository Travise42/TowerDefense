package towerdefense.game.env;

import towerdefense.game.Game;

public class MapConv {

    /// X/Y <-> COLUMN/ROW /// ------------------------------------------------------------ ///

    public static int cordToGrid(float cord) {
        return (int) ( cord / Map.TILE_SIZE );
    }

    public static float cordToSoftGrid(float cord) {
        return cord / Map.TILE_SIZE;
    }


    public static int gridToCord(int grid) {
        return (int) ( grid * Map.TILE_SIZE );
    }

    public static int gridToCord(float grid) {
        return (int) ( grid * Map.TILE_SIZE );
    }

    /// X/Y <-> VIEWX/VIEWY /// ------------------------------------------------------------ ///

    public static int xToViewX(float x) {
        return (int) (cordToScreenCord(x) - Game.instance.camera.getX());
    }

    public static int yToViewY(float y) {
        return (int) (cordToScreenCord(y) - Game.instance.camera.getY());
    }


    public static int viewXToX(float viewX) {
        return (int) screenCordToCord(viewX + Game.instance.camera.getX());
    }

    public static int viewYToY(float viewY) {
        return (int) screenCordToCord(viewY + Game.instance.camera.getY());
    }


    public static float cordToScreenCord( float cord ) {
        return cord * Game.instance.map.getTileSize() / Map.TILE_SIZE;
    }

    public static float screenCordToCord( float cord ) {
        return cord * Map.TILE_SIZE / Game.instance.map.getTileSize();
    }

    /// COLUMN/ROW <-> OPEN_COLUMN/OPEN_ROW /// ------------------------------------------------------------ ///

    public static int columnToOpenColumn(int column) {
        return column - ( Map.COLUMNS - Game.instance.map.map.getOpenColumns() ) / 2;
    }

    public static float columnToOpenColumn(float column) {
        return column - ( Map.COLUMNS - Game.instance.map.map.getOpenColumns() ) / 2;
    }

    public static int rowToOpenRow(int row) {
        return row - ( Map.ROWS - Game.instance.map.map.getOpenRows() ) / 2;
    }

    public static float rowToOpenRow(float row) {
        return row - ( Map.ROWS - Game.instance.map.map.getOpenRows() ) / 2;
    }


    public static int openColumnToColumn(int openColumn) {
        return openColumn - ( Map.COLUMNS - Game.instance.map.map.getOpenColumns() ) / 2;
    }

    public static float openColumnToColumn(float openColumn) {
        return openColumn - ( Map.COLUMNS - Game.instance.map.map.getOpenColumns() ) / 2;
    }

    public static int openRowToRow(int openRow) {
        return openRow - ( Map.ROWS - Game.instance.map.map.getOpenRows() ) / 2;
    }

    public static float openRowToRow(float openRow) {
        return openRow - ( Map.ROWS - Game.instance.map.map.getOpenRows() ) / 2;
    }

    /// COLUMN/ROW <-> VIEWX/VIEWY /// ------------------------------------------------------------ ///

    public static int columnToViewX( float column ) {
        return xToViewX(gridToCord(column));
    }

    public static int rowToViewY( float row ) {
        return yToViewY(gridToCord(row));
    }

    public static int viewXToColumn( float viewX ) {
        return cordToGrid(viewXToX(viewX));
    }

    public static int viewYToRow( float viewY ) {
        return cordToGrid(viewYToY(viewY));
    }

}
