package towerdefense.game.env;

import towerdefense.game.Game;

public class Map {

    final static int INITIAL_OPEN_COLUMNS = 10;
    final static int INITIAL_OPEN_ROWS = 6;
    
    final static int COLUMNS = 36;
    final static int ROWS = 32;
    
    private Game game;
    private final boolean[][] map = new boolean[COLUMNS][ROWS];

    public int stage;

    public Map( Game gameInstance ) {
        game = gameInstance;

        stage = 0;
        setup();
    }

    private void setup() {
        int ic = (INITIAL_OPEN_COLUMNS - 1)/2;
        int ir = (INITIAL_OPEN_ROWS - 1)/2;

        for ( int c = COLUMNS/2 - ic; c < COLUMNS/2 + ic; c++ ) {
            for ( int r = ROWS/2 - ir; r < ROWS/2 + ir; r++ ) {
                map[c][r] = true;
            }
        }
    }

    public void nextStage() {
        stage++;

        int ic = (INITIAL_OPEN_COLUMNS - 1)/2;
        int ir = (INITIAL_OPEN_ROWS - 1)/2;

        for ( int c = -(ic+stage); c < ic+stage; c++ ) {
            System.out.println( "-----------------------------------" );
            for ( int r = -(int)(ir+stage*0.7f); r < ir + (int)(stage*0.7f); r++ ) {
                if ( 0.6 > Math.cos( 2*( (float)c / ( ic+stage ) + ( ic+stage )/2 - 1 ) )*Math.cos( 2*( (float)r / ( ir+stage*0.7f ) + ( ic+stage*0.7f )/2 - 1 ) ) )
                    map[c + COLUMNS/2][r + ROWS/2] = true;
                
                System.out.print( 2*( (float)(c + ic+stage) / ( ic+stage ) ) );
                System.out.print(" ");
                System.out.println( 2*( (float)(r + ir+stage*0.7f) / ( ir+stage*0.7f ) ) );
            }
        }

        game.camera.expand();
    }

    // Print a string representing the open (F) and closed (_) spaces
    public String toString() {
        String string = "";
        for ( int r = 0; r < ROWS; r++ ) {
            for ( int c = 0; c < COLUMNS; c++ ) {
                string += map[c][r] ? "_ " : "F ";
            }
            string += "\n";
        }
        return string;
    }

    public boolean[][] getMap() {
        return map;
    }

}
