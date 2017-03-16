package djbdevelopment.cannonball;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

import static djbdevelopment.cannonball.Constants.cannonBallSpeed;
import static djbdevelopment.cannonball.Constants.velocityScale;

/**
 * Created by danmacduff on 09/03/2017.
 */

public class CannonBall {
    Vector2d s, v;
    float rad;
    Paint fg;
    boolean backfiring = false;

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
        rad = 20;
        s.x = CannonActivity.getScreenWidth() /2;
        s.y = CannonActivity.getScreenHeight();
        v.set(0,0);
        backfiring = false;
    }

    public int getScore() {
        return 10;
    }

    public int fire(float x, float y) {

        if (s.x != CannonActivity.getScreenWidth() / 2 || s.y != CannonActivity.getScreenHeight()) {
            // once the cannonball has been fired we don't want to change
            // its velocity
            return 0;
        }
        double theta = Math.atan2(y - CannonActivity.getScreenHeight(), x - (CannonActivity.getScreenWidth()/2));
        v.x = cannonBallSpeed * (float) Math.cos(theta);
        v.y = cannonBallSpeed * (float) Math.sin(theta);
        return 1;
    }

    public void backfire() {
        backfiring = true;
        System.out.println("initial v.y : " + v.y);
        v.y *= -1;
        System.out.println("new v.y : " + v.y);
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
         c.drawCircle(s.x, s.y, rad, fg);
    }


}
