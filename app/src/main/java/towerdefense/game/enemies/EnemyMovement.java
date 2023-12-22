package towerdefense.game.enemies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import towerdefense.game.Game;
import towerdefense.game.env.Map;

public class EnemyMovement {

    final public static boolean BAT = false; // AI
    final public static boolean BIRD = true; // PATHFIND

    private Game game;
    
    private boolean mode;

    private List<Integer> brain;
    private int[][] path;
    
    public EnemyMovement( Game game, boolean mode ) {
        this.game = game;
        this.mode = mode;

        generatePath();
    }

    public void generatePath() {
        path = new int[game.map.map.openColumns][game.map.map.openRows];

        List<int[]> POIs = new ArrayList<>();
        POIs.add( new int[] { path.length, path[0].length / 2 - 1 } );
        POIs.add( new int[] { path.length, path[0].length / 2 } );

        final int LEFT_COLUMN = ( Map.COLUMNS - game.map.map.openColumns ) / 2;
        final int TOP_ROW = ( Map.ROWS - game.map.map.openRows ) / 2;

        while ( POIs.size() != 0 ) {
            int size = POIs.size();

            for ( int i = 0; i < size; i++ ) {
                int[] poi = POIs.get( 0 );
                POIs.remove( 0 );

                for ( int m = -1; m <= 1; m += 2 ) {
                    for ( int x = 1; x >= 0; x-- ) {
                        int c = poi[0] + x * m;
                        int r = poi[1] + ( 1 - x ) * m;

                        if ( game.map.map.openColumns <= c || c < 0 || game.map.map.openRows <= r || r < 0
                            || path[c][r] != 0 || !game.map.map.isOpen( c + LEFT_COLUMN, r + TOP_ROW ) ) continue;

                        POIs.add( new int[] { c, r } );
                        path[c][r] = m * ( x + 1 );
                    }
                }
            }
        }

        for ( int[] column : path ) {
            System.out.println( Arrays.toString( column ) );
        }
        System.out.println();
    }

}
