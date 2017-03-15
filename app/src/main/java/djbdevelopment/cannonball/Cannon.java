package djbdevelopment.cannonball;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by danmacduff on 08/03/2017.
 */

public class Cannon {
    Bitmap image;
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


}
