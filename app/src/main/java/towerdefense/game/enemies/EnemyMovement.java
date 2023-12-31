package towerdefense.game.enemies;

import java.util.ArrayList;
import java.util.Arrays;
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
        path = new int[Game.instance.map.map.openColumns][Game.instance.map.map.openRows];

        List<int[]> POIs = new ArrayList<>();
        POIs.add( new int[] { path.length, path[0].length / 2 - 1 } );
        POIs.add( new int[] { path.length, path[0].length / 2 } );

        List<int[]> delayedPOIs = new ArrayList<>();

        final int LEFT_COLUMN = ( Map.COLUMNS - Game.instance.map.map.openColumns ) / 2;
        final int TOP_ROW = ( Map.ROWS - Game.instance.map.map.openRows ) / 2;

        while ( POIs.size() + delayedPOIs.size() > 0 ) {
            int size = POIs.size();

            for ( int i = 0; i < size; i++ ) {
                int[] poi = POIs.get( 0 );
                POIs.remove( 0 );

                for ( int m = -1; m <= 1; m += 2 ) {
                    for ( int x = 1; x > -1; x-- ) {
                        int c = poi[0] + x * m;
                        int r = poi[1] + ( 1 - x ) * m;

                        if ( Game.instance.map.map.openColumns <= c || c < 0 || Game.instance.map.map.openRows <= r || r < 0
                            || path[c][r] != 0 ) continue;

                        path[c][r] = m * ( x + 1 );

                        if ( !Game.instance.map.map.isOpen( c + LEFT_COLUMN, r + TOP_ROW ) ) {
                            delayedPOIs.add( new int[] { c, r, 5 } );
                            continue;
                        }

                        POIs.add( new int[] { c, r } );
                    }
                }
            }

            for ( int d = 0; d < delayedPOIs.size(); ) {
                if ( --delayedPOIs.get( d )[2] > 0 ) {
                    d++;
                    continue;
                }

                POIs.add( delayedPOIs.get( d ) );
                delayedPOIs.remove( d );
            }
        }

        // for ( int row = 0; row < path[0].length; row++ ) {
        //     for ( int[] column : path ) {
        //         System.out.print( column[ row ] + ",\t" );
        //     }
        //     System.out.println();
        // }
        // System.out.println();
        //          1: up
        // 2: left        -2: right
        //        -1: down
    }

}
