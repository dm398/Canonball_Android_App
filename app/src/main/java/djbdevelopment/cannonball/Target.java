package djbdevelopment.cannonball;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

import static djbdevelopment.cannonball.Constants.velocityScale;

/**
 * Created by danmacduff on 15/03/2017.
 */

public class Target {

    Vector2d s, v;
    float rad;
    Paint colour;

    public Target(Paint colour) {
        this();
        this.colour = colour;
    }


    static Random random = new Random();

    public Target() {
        s = new Vector2d();
        v = new Vector2d();
        spawn();
    }

    public void spawn() {
        rad = 20;
        s.set(0,50);
        v.set(velocityScale * (float) 1, 0);
    }

    public boolean contains(float x, float y) {
        return s.dist(x,y) < rad;
    }

    public void draw(Canvas c) {
        c.drawCircle(s.x, s.y, rad, colour);
    }

    public int getScore() {
        return 10;
    }
    public void update(Rect rect) {
        s.add(v);
       s.wrap(rect.width(), rect.height());
    }
    }


