package djbdevelopment.cannonball;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.Random;

import static android.R.attr.bitmap;
import static djbdevelopment.cannonball.Constants.cannonBallSpeed;

/**
 * Created by danmacduff on 09/03/2017.
 */

public class CannonBall {
    Vector2d s, v;
    float rad;
    boolean backfiring = false;
    Bitmap image;

    static Random random = new Random();

    public CannonBall(Context c) {
         this.image = BitmapFactory.decodeResource(c.getResources(), R.drawable.cannonball);


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

    public int fire(float x, float y) {

        if (s.x != CannonActivity.getScreenWidth() / 2 || s.y != CannonActivity.getScreenHeight()) {
            // once the cannonball has been fired we
            // don't want to change its velocity
            return 0;
        }
        double theta = Math.atan2(y - CannonActivity.getScreenHeight(), x - (CannonActivity.getScreenWidth()/2));
        v.x = cannonBallSpeed * (float) Math.cos(theta);
        v.y = cannonBallSpeed * (float) Math.sin(theta);
        return 1;
    }

    public void backfire() {
        backfiring = true;
        v.y *= -1;
     }

    public void update() {
       s.add(v);
        if (s.y <= 0 || s.x <= 0 || s.x >= CannonActivity.getScreenWidth() || s.y >= CannonActivity.getScreenHeight()){
            // cannon ball off the screen, so we reset
            s.x = CannonActivity.getScreenWidth() /2;
            s.y = CannonActivity.getScreenHeight();
           reSpawn();
        }

     }

    public void draw(Canvas c) {
        c.drawBitmap(image, s.x, s.y, null);
    }



}
