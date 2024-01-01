package towerdefense.game.enemies;

import java.util.LinkedList;
import java.util.List;

import towerdefense.game.Game;
import towerdefense.game.env.Map;

public class EnemyMovement {

    final public static boolean BAT = false; // AI
    final public static boolean BIRD = true; // PATHFIND

    private boolean mode;

    private List<Integer> brain;
    public int[][] path;

    public EnemyMovement(boolean mode) {
        this.mode = mode;

        generatePath();
    }

    public void generatePath() {
        final int OPEN_COLUMNS = Game.instance.map.map.getOpenColumns();
        final int OPEN_ROWS = Game.instance.map.map.getOpenRows();

        final int LEFT_COLUMN = (Map.COLUMNS - OPEN_COLUMNS) / 2;
        final int TOP_ROW = (Map.ROWS - OPEN_ROWS) / 2;

        // Empty path data
        path = new int[OPEN_COLUMNS][OPEN_ROWS];

        LinkedList<int[]> POIs = new LinkedList<>();
        LinkedList<int[]> delayedPOIs = new LinkedList<>();

        // Start at entrance
        POIs.add(new int[] { OPEN_COLUMNS, OPEN_ROWS / 2 - 1 });
        POIs.add(new int[] { OPEN_COLUMNS, OPEN_ROWS / 2 });

        while (!(POIs.isEmpty() && delayedPOIs.isEmpty())) {
            int size = POIs.size();

            // Iterate through all points of interest that were created last iteration
            for (; 0 < size--;) {
                // Pull out the first point of interest
                int[] poi = POIs.poll();

                // Check all bounding squares
                for (int m = -1; m <= 1; m += 2) {
                    for (int x = 1; x >= 0; x--) {
                        int column = poi[0] + x * m;
                        int row = poi[1] + (1 - x) * m;

                        boolean OUT_OF_BOUNDS = column < 0 || column >= OPEN_COLUMNS || row < 0 || row >= OPEN_ROWS;
                        if (OUT_OF_BOUNDS)
                            continue;

                        boolean DUPLICATE = path[column][row] != 0;
                        if (DUPLICATE)
                            continue;

                        path[column][row] = m * (x + 1);

                        // Add the new point of interest if area open, otherwise, save it for later
                        if (Game.instance.map.map.isOpen(column + LEFT_COLUMN, row + TOP_ROW)
                                || !delayedPOIs.add(new int[] { column, row, 5 })) {
                            POIs.add(new int[] { column, row });
                        }
                    }
                }
            }
            // Move delayed points of interest to POIs if their delay is over
            delayedPOIs.removeIf(poi -> --poi[2] == 0 && POIs.add(poi));
        }
    }

}
