package towerdefense.game.env;

import towerdefense.game.Game;

public class Map {

    final public static int INITIAL_OPEN_COLUMNS = 10;
    final public static int INITIAL_OPEN_ROWS = 6;
    
    final public static int COLUMNS = 30;
    final public static int ROWS = 26;
    
    private Game game;

    // true: open
    // false: obstructed
    private final boolean[][] grid = new boolean[COLUMNS][ROWS];

    public int stage;

    public Map( Game gameInstance ) {
        game = gameInstance;

        stage = 0;
        setup();
    }

    private void setup() {
        fill(
            ( COLUMNS - INITIAL_OPEN_COLUMNS ) / 2, // leftmost colummn
            ( ROWS - INITIAL_OPEN_ROWS ) / 2, // upmost row
            INITIAL_OPEN_COLUMNS,
            INITIAL_OPEN_ROWS,
            true
        );

        /*
        // Create an entrence
        for( int c = 0; c < COLUMNS; c++ ) {
            for ( int r = 0; r < 2; r++ ) {
                grid[c][r + ROWS / 2 - 1] = true;
            }
        }
        */
    }
    
    public void fill( int column, int row, int columnspan, int rowspan, boolean open ) {
        for ( int c = column; c < column + columnspan; c++ ) {
            for ( int r = row; r < row + rowspan; r++ ) {
                grid[c][r] = open;
            }
        }
    }
    
    // return if the given region is obstructed
    public boolean check( int column, int row, int columnspan, int rowspan ) {
        for ( int c = column; c < column + columnspan; c++ ) {
            for ( int r = row; r < row + rowspan; r++ ) {
                if ( !grid[c][r] ) return true;
            }
        }
        return false;
    }

    public void nextStage() {
        stage++;

        int COLUMNS_FROM_CENTER = INITIAL_OPEN_COLUMNS/2 + stage;
        int ROWS_FROM_CENTER = (int)( INITIAL_OPEN_ROWS/2 + stage*0.7f );

        fill(
            COLUMNS/2 - COLUMNS_FROM_CENTER, // leftmost column
            ROWS/2 - ROWS_FROM_CENTER, // upmost row
            2*COLUMNS_FROM_CENTER,
            2*ROWS_FROM_CENTER,
            true
        );

        game.camera.expand();
    }

    // Print a string representing the open (F) and closed (_) spaces
    public String toString() {
        String string = "";
        for ( int r = 0; r < ROWS; r++ ) {
            for ( int c = 0; c < COLUMNS; c++ ) {
                string += grid[c][r] ? "_ " : "F ";
            }
            string += "\n";
        }
        return string;
    }

    public boolean[][] getGrid() {
        return grid;
    }

}
