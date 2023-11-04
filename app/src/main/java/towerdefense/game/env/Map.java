package towerdefense.game.env;

import towerdefense.game.Game;

public class Map {

    final static int INITIAL_OPEN_COLUMNS = 10;
    final static int INITIAL_OPEN_ROWS = 6;
    
    final static int COLUMNS = 36;
    final static int ROWS = 32;
    
    private Game game;
    private final boolean[][] map = new boolean[COLUMNS][ROWS];

    private int stage = 8;

    public Map( Game gameInstance ) {
        game = gameInstance;
        nextStage();
    }

    public String toString() {
        String string = "";
        for ( int r = 0; r < ROWS; r++ ) {
            for ( int c = 0; c < COLUMNS; c++ ) {
                string += map[c][r] ? "  " : "F ";
            }
            string += "\n";
        }
        return string;
    }

    private void nextStage() {
        stage++;

        int ic = (INITIAL_OPEN_COLUMNS - 1)/2;
        int ir = (INITIAL_OPEN_ROWS - 1)/2;

        for ( int c = COLUMNS/2 - ic - stage; c < COLUMNS/2 + ic + stage; c++ ) {
            for ( int r = ROWS/2 - ir - stage; r < ROWS/2 + ir + stage; r++ ) {
                map[c][r] = true;
            }
        }
    }

}
