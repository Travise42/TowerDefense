package towerdefense.game.env;

import java.util.Arrays;

import towerdefense.game.Game;

public class Map {

    final public static int INITIAL_OPEN_COLUMNS = 10;
    final public static int INITIAL_OPEN_ROWS = 6;
    
    final public static int COLUMNS = 40;
    final public static int ROWS = 26;

    // true: open
    // false: obstructed
    private boolean[][] gridOccupancy;

    public int stage;
    public int openColumns;
    public int openRows;

    public Map() {
        reset();
    }

    private void setup() {
        fill(
            ( COLUMNS - INITIAL_OPEN_COLUMNS ) / 2, // leftmost colummn
            ( ROWS - INITIAL_OPEN_ROWS ) / 2, // upmost row
            INITIAL_OPEN_COLUMNS,
            INITIAL_OPEN_ROWS,
            true
        );
        openColumns = INITIAL_OPEN_COLUMNS;
        openRows = INITIAL_OPEN_ROWS;
    }
    
    public void fill( int column, int row, int columnspan, int rowspan, boolean open ) {
        for ( int c = column; c < column + columnspan; c++ ) {
            Arrays.fill( gridOccupancy[c], row, row + rowspan, open );
        }
    }
    
    // return if the given region is obstructed
    public boolean check( int column, int row, int columnspan, int rowspan ) {
        for ( int c = column; c < column + columnspan; c++ ) {
            for ( int r = row; r < row + rowspan; r++ ) {
                if ( !gridOccupancy[c][r] ) return true;
            }
        }
        return false;
    }

    public void nextStage() {
        stage++;

        int COLUMNS_FROM_CENTER = INITIAL_OPEN_COLUMNS/2 + stage;
        int ROWS_FROM_CENTER = (int)( INITIAL_OPEN_ROWS/2 + stage*0.7f );

        int INITIAL_COLUMN = COLUMNS/2 - COLUMNS_FROM_CENTER;
        int FINAL_COLUMN = COLUMNS/2 + COLUMNS_FROM_CENTER - 1;

        int INITIAL_ROW = ROWS/2 - ROWS_FROM_CENTER;
        int FINAL_ROW = ROWS/2 + ROWS_FROM_CENTER - 1;

        // Top and Bottom
        if ( stage*0.7f % 1 < 0.7f ) {
            for ( int column = INITIAL_COLUMN; column <= FINAL_COLUMN; column++ ) {
                gridOccupancy[ column ][ INITIAL_ROW ] = true;
                gridOccupancy[ column ][ FINAL_ROW ] = true;
            }
            openRows += 2;
        }

        // Left and Right
        for ( int row = INITIAL_ROW; row <= FINAL_ROW; row++ ) {
            gridOccupancy[ INITIAL_COLUMN ][ row ] = true;
            gridOccupancy[ FINAL_COLUMN ][ row ] = true;
        }
        openColumns += 2;

        Game.instance.em.generatePath();
        Game.instance.camera.expand();
    }

    public void reset() {
        gridOccupancy = new boolean[COLUMNS][ROWS];
        stage = 0;
        setup();
    }

    // Print a string representing the open (_) and closed (F) spaces
    public String toString() {
        String string = "";
        for ( int r = 0; r < ROWS; r++ ) {
            for ( int c = 0; c < COLUMNS; c++ ) {
                string += gridOccupancy[c][r] ? "_ " : "F ";
            }
            string += "\n";
        }
        return string;
    }

    public boolean isOpen( int column, int row ) {
        return gridOccupancy[ column ][ row ];
    }

}
