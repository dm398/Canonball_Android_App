package djbdevelopment.cannonball;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

import static djbdevelopment.cannonball.Constants.velocityScale;

/**
 * Created by danmacduff on 07/03/2017.
 */

public class Blocker {

    Vector2d start, stop, v;
    float rad;
    Paint colour;

    public Blocker(Paint colour) {
        this();
        this.colour = colour;
    }


    static Random random = new Random();

    public Blocker() {
        start = new Vector2d();
        stop = new Vector2d();
        v = new Vector2d();
        spawn();
    }

    public void spawn() {
        rad = stop.x - start.x;
        start.set(0, CannonActivity.getScreenHeight() - 100);
        stop.set((CannonActivity.getScreenWidth() / 8 ) * 3, CannonActivity.getScreenHeight() - 100);
        v.set(velocityScale * (float) 1, 0);
    }


    public boolean contains(float x, float y) {
       if (x > start.x && x < stop.x && start.y >= y) {
           return true;
       }
        else {
           return false;
       }
    }

    public float getLength() {
        return  stop.x - start.x;
    }

    public void draw(Canvas c) {

        c.drawLine(start.x, start.y, stop.x, stop.y, this.colour);
    }

    public int getScore() {
        return 10;
    }
    public void update(Rect rect) {
        start.add(v);
        stop.add(v);
        if (stop.x >= CannonActivity.getScreenWidth() || start.x <= 0 ) {
            // we need to make it go in the opposite direction
            v.x *= -1;
            v.y *= -1;
        }
    }
}
