package towerdefense.game.towers;

public class TowerUpgrade {

    private int paths;
    private int tiers;

    private String[][] names;
    private int[][] costs;
    private int[][] healths;

    private int[][] damages;
    private int[][] pierces;
    private int[][] reloadTimes;
    private int[][] projectileSpeeds;
    private int[][] projectileLifetimes;
    private int[][] ranges;

    public TowerUpgrade(String tower_id, int paths, int tiers, String[][] upgrade_names, int[][] cost, int[][] healths,
            int[][] damages, int[][] pierces, int[][] reload_times, int[][] projectile_speeds, int[][] projectile_lifetimes, int[][] ranges) {
        this.paths = paths;
        this.tiers = tiers;

        this.names = upgrade_names;
        this.costs = cost;
        this.healths = healths;

        this.damages = damages;
        this.pierces = pierces;
        this.reloadTimes = reload_times;
        this.projectileSpeeds = projectile_speeds;
        this.projectileLifetimes = projectile_lifetimes;
        this.ranges = ranges;
    }

    public int getTiers() {
        return tiers;
    }

    public int getPaths() {
        return paths;
    }

    public String getName(int path, int tier) {
        if ( tier == 0 ) return names[0][0];
        if ( tier > tiers ) return "Invalid Tier";
        if ( path > paths ) return "Invalid Path";
        return names[path][tier-1];
    }

    public int getCost(int path, int tier) {
        if ( tier == 0 ) return costs[0][0];
        if ( tier > tiers ) return 999999999;
        if ( path > paths ) return 999999999;
        return costs[path][tier-1];
    }

    public int getHealth(int path, int tier) {
        if ( tier == 0 ) return costs[0][0];
        if ( tier > tiers ) return 999999999;
        if ( path > paths ) return 999999999;
        return healths[path][tier-1];
    }

    public int getDamage(int path, int tier) {
        if ( tier == 0 ) return damages[0][0];
        if ( tier > tiers ) return 999999999;
        if ( path > paths ) return 999999999;
        return damages[path][tier-1];
    }

    public int getPierce(int path, int tier) {
        if ( tier == 0 ) return pierces[0][0];
        if ( tier > tiers ) return 999999999;
        if ( path > paths ) return 999999999;
        return pierces[path][tier-1];
    }

    public int getReloadTime(int path, int tier) {
        if ( tier == 0 ) return reloadTimes[0][0];
        if ( tier > tiers ) return 999999999;
        if ( path > paths ) return 999999999;
        return reloadTimes[path][tier-1];
    }

    public int getProjectileSpeed(int path, int tier) {
        if ( tier == 0 ) return projectileSpeeds[0][0];
        if ( tier > tiers ) return 999999999;
        if ( path > paths ) return 999999999;
        return projectileSpeeds[path][tier-1];
    }

    public int getProjectileLifetime(int path, int tier) {
        if ( tier == 0 ) return projectileLifetimes[0][0];
        if ( tier > tiers ) return 999999999;
        if ( path > paths ) return 999999999;
        return projectileLifetimes[path][tier-1];
    }

    public int getRange(int path, int tier) {
        if ( tier == 0 ) return ranges[0][0];
        if ( tier > tiers ) return 999999999;
        if ( path > paths ) return 999999999;
        return ranges[path][tier-1];
    }

}
