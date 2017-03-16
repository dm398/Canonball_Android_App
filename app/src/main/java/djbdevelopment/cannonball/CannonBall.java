package djbdevelopment.cannonball;

import android.content.res.Resources;
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
    Paint fg;

    public CannonBall(Paint fg) {
        this();
        this.fg = fg;
    }

    static Random random = new Random();

    public CannonBall() {
        s = new Vector2d();
        v = new Vector2d();
        s.x = CannonActivity.getScreenWidth() /2;
        s.y = CannonActivity.getScreenHeight();
        reSpawn();
    }

    public void reSpawn() {
        rad = 50;
        s.x = CannonActivity.getScreenWidth() /2;
        s.y = CannonActivity.getScreenHeight();
        v.set(0,0);
    }

    public int getScore() {
        return 10;
    }

    public void fire() {

        float a = (float) random.nextGaussian();
        float b = (float) random.nextGaussian();

        System.out.println("a float is " + a );
        System.out.println("b float is " + b );

        v.set(velocityScale * a, velocityScale * b);
//        v.set(velocityScale * (float) 200,
//           velocityScale * (float) 50);
    }

    public void update(Rect rect) {
       s.add(v);

        if (s.y <= 0 || s.x <= 0 || s.x >= CannonActivity.getScreenWidth() || s.y >= CannonActivity.getScreenHeight()){
            // cannon ball off the screen, so we reset
            s.x = CannonActivity.getScreenWidth() /2;
            s.y = CannonActivity.getScreenHeight();
           reSpawn();
        }

     }

    public boolean contains(float x, float y) {
        return s.dist(x,y) < rad;
    }

    public void draw(Canvas c) {
        //System.out.println("drawing at x: " + s.x + ", y at : " + s.y);
        c.drawCircle(s.x, s.y, rad, fg);
    }


}
