package towerdefense.func;

public class Calc {

    // Find the hypotenuse ( a^2 + b^2 = c^2 )
    public static float pythag(float a, float b) {
        return (float) Math.sqrt(a * a + b * b);
    }

    // Get the interior angle using opposite and adjacent
    public static double getAngle(float x, float y) {
        double angle = x == 0 ? Math.PI / 2 : Math.atan(y / x);
        if (x > 0)
            return angle + Math.PI;
        return angle;
    }

    // Convert angle to dimensions
    public static float xFromAngle(double angle, float hyp) {
        return (float) (hyp * Math.cos(angle));
    }

    public static float xFromAngle(double angle) {
        return (float) Math.cos(angle);
    }

    public static float yFromAngle(double angle, float hyp) {
        return (float) (hyp * Math.sin(angle));
    }

    public static float yFromAngle(double angle) {
        return (float) Math.sin(angle);
    }

}
