package towerdefense.game.env;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import towerdefense.game.Game;
import towerdefense.game.env.obstacles.Obstacle;

public class Map {

    final public static int INITIAL_OPEN_COLUMNS = 12;
    final public static int INITIAL_OPEN_ROWS = 8;

    final public static int COLUMNS = 30;
    final public static int ROWS = 20;

    // true: open
    // false: obstructed
    private boolean[][] gridOccupancy;

    private List<Obstacle> obstacles;

    public int stage;

    public Map() {
        obstacles = new ArrayList<>();

        reset();
    }

    /// Grid Occupancy ///
    /// ------------------------------------------------------------ ///

    public void reset() {
        gridOccupancy = new boolean[COLUMNS][ROWS];
        setup();
    }

    private void setup() {
        stage = 0;

        final int LEFT_COLUMN = (COLUMNS - INITIAL_OPEN_COLUMNS) / 2;
        final int TOP_ROW = (ROWS - INITIAL_OPEN_ROWS) / 2;

        fill(LEFT_COLUMN, TOP_ROW, INITIAL_OPEN_COLUMNS, INITIAL_OPEN_ROWS, true);
    }

    public void nextStage() {
        stage++;

        int COLUMNS_FROM_CENTER = INITIAL_OPEN_COLUMNS / 2 + stage;
        int ROWS_FROM_CENTER = COLUMNS_FROM_CENTER * 2 / 3;

        int INITIAL_COLUMN = COLUMNS / 2 - COLUMNS_FROM_CENTER;
        int FINAL_COLUMN = COLUMNS / 2 + COLUMNS_FROM_CENTER - 1;

        int INITIAL_ROW = ROWS / 2 - ROWS_FROM_CENTER;
        int FINAL_ROW = ROWS / 2 + ROWS_FROM_CENTER - 1;

        // Top and Bottom
        if ((stage - 1) % 3 != 0) {
            for (int column = INITIAL_COLUMN; column <= FINAL_COLUMN; column++) {
                gridOccupancy[column][INITIAL_ROW] = true;
                gridOccupancy[column][FINAL_ROW] = true;
            }
        }

        // Left and Right
        for (int row = INITIAL_ROW; row <= FINAL_ROW; row++) {
            gridOccupancy[INITIAL_COLUMN][row] = true;
            gridOccupancy[FINAL_COLUMN][row] = true;
        }

        Game.instance.em.generatePath();
        Game.instance.camera.expand();
    }

    public void fill(int column, int row, int columnspan, int rowspan, boolean open) {
        for (int c = column; c < column + columnspan; c++)
            Arrays.fill(gridOccupancy[c], row, row + rowspan, open);
    }

    public boolean isOpen(int column, int row, int columnspan, int rowspan) {
        for (int c = column; c < column + columnspan; c++)
            for (int r = row; r < row + rowspan; r++)
                if (!isOpen(c, r))
                    return false;
        return true;
    }

    public boolean isOpen(int column, int row) {
        return gridOccupancy[column][row];
    }

    public int getOpenColumns() {
        return INITIAL_OPEN_COLUMNS + stage * 2;
    }

    public int getOpenRows() {
        return getOpenColumns() * 2 / 3;
    }

    /// Obstacles /// ------------------------------------------------------------
    /// ///

    // TODO
    public void createNewTree() {

    }

}
