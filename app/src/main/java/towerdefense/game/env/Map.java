package towerdefense.game.env;

import towerdefense.game.Game;

public class Map {

    final public static int INITIAL_OPEN_COLUMNS = 10;
    final public static int INITIAL_OPEN_ROWS = 6;
    
    final public static int COLUMNS = 36;
    final public static int ROWS = 32;
    
    private Game game;
    private final boolean[][] grid = new boolean[COLUMNS][ROWS];

    public int stage;

    public Map( Game gameInstance ) {
        game = gameInstance;

        stage = 0;
        setup();
    }

    private void setup() {
        int ic = INITIAL_OPEN_COLUMNS/2;
        int ir = INITIAL_OPEN_ROWS/2;

        for ( int c = COLUMNS/2 - ic; c < COLUMNS/2 + ic; c++ ) {
            for ( int r = ROWS/2 - ir; r < ROWS/2 + ir; r++ ) {
                grid[c][r] = true;
            }
        }
    }

    public void nextStage() {
        stage++;

        int ic = INITIAL_OPEN_COLUMNS/2 + stage;
        int ir = (int)( INITIAL_OPEN_ROWS/2 + stage*0.7f );

        for ( int c = -ic; c < ic; c++ ) {
            for ( int r = -ir; r < ir; r++ ) {
                grid[c + COLUMNS/2][r + ROWS/2] = true;

                //// double sx = (float)( c + ic + stage ) * Math.PI / ( INITIAL_OPEN_COLUMNS + stage*2 - 3 );
                //// double sy = (float)( r + ir + stage*0.7f ) * Math.PI / ( INITIAL_OPEN_ROWS + stage*1.4f - 3 );
                //// if ( 0.5 < Math.sin( sx ) + Math.pow( Math.sin( sy ), 3 ) ) {...}
            }
        }

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
