package djbdevelopment.cannonball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;

import static djbdevelopment.cannonball.Constants.*;
import java.util.Random;



/**
 * Created by Student 630 022 295 on 15/03/2017.
 */

public class Target {

    Vector2d s, v;
    float rad;
    Paint colour;

    static Random random = new Random();

    public Target() {
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        this.colour = p;
        s = new Vector2d();
        v = new Vector2d();
        spawn();
    }

    public void spawn() {
        rad = 20;
        s.set(0,80);
        v.set(targetSpeed * (float) 1, 0);
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
        if (s.x >= CannonActivity.getScreenWidth() || s.x <= 0 ) {
            // we need to make it go in the opposite direction
            v.x *= -1;
            v.y *= -1;
        }
    }
    }


