package djbdevelopment.cannonball;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

/**
 * Created by danmacduff on 08/03/2017.
 */

public class Cannon {
    Bitmap bmp;
    int cannonLength;
    int cannonBaseRadius;
    Paint cannonPaint;
    Point barrelEnd;
    Point originalBarrelEnd;
    int originalCannonLength;

    public Cannon() {

    }

    Cannon(int cannonLength , int cannonBaseRadius, int screenHeight) {
        this.cannonLength = cannonLength;
        this.cannonBaseRadius = cannonBaseRadius;
        barrelEnd = new Point(cannonLength, screenHeight / 2);
        originalBarrelEnd = barrelEnd;
        originalCannonLength = cannonLength;
        cannonPaint = new Paint();
        cannonPaint.setARGB(255, 255, 255, 255);
        cannonPaint.setStrokeWidth(8);

    }

    public void draw(Canvas c){
        //c.drawBitmap(this.bmp,CannonActivity.getScreenWidth()/2, CannonActivity.getScreenHeight(), null);

        c.drawCircle(CannonActivity.getScreenWidth()/2, CannonActivity.getScreenHeight(),  this.cannonBaseRadius, this.cannonPaint);

    }


}
