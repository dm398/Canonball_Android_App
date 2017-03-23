package djbdevelopment.cannonball;


public class Vector2d {

    public static void main(String[] args) {
         Vector2d v = new Vector2d(10, 10);
        System.out.println(v.mag());
        v.normalise();
        System.out.println(v.mag());
    }

    public float x, y;

    public Vector2d() {
        this(0, 0);
    }

    public boolean equals(Object o) {
        if (o instanceof Vector2d) {
            Vector2d v = (Vector2d) o;
            return x == v.x && y == v.y;
        } else {
            return false;
        }
    }

    public Vector2d(float x, float y) {
        this.x = x;
        this.y = y;
    }



    public Vector2d copy() {
        return new Vector2d(x, y);
    }



    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }



    public String toString() {
        return x + " : " + y;
    }

    public Vector2d add(Vector2d v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vector2d add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2d add(Vector2d v, double w) {
        // weighted addition
        this.x += w * v.x;
        this.y += w * v.y;
        return this;
    }


    public static float sqr(float x) {
        return x * x;
    }


    public float mag() {
        return (float) Math.sqrt(sqr(x) + sqr(y));
    }


    public float dist(float vx, float vy) {
        return (float) Math.sqrt(sqr(x - vx) + sqr(y - vy));
    }



    public void normalise() {
        double mag = mag();
        x /= mag;
        y /= mag;
    }
}

