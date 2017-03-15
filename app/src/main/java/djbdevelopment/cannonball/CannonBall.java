package djbdevelopment.cannonball;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

import static djbdevelopment.cannonball.Constants.velocityScale;

/**
 * Created by danmacduff on 09/03/2017.
 */

public class CannonBall {
    Vector2d s, v;
    float rad;
    Paint colour;


    static Paint whitePaint = new Paint();

    static Random random = new Random();

    public CannonBall() {
        whitePaint.setARGB(255, 255, 255, 255);
        rad = 20;
        this.colour = whitePaint;
        s = new Vector2d();
        v = new Vector2d();
        s.set(CannonActivity.getScreenWidth()/2,CannonActivity.getScreenHeight());

    }

    public void fire(int x, int y) {
        // v.set(velocityScale * (float) x, y);
        v.set(10, 300);
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
    public void update() {

       v.y += 20;
        s.add(v);
     }
}
