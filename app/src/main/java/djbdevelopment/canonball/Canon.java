package djbdevelopment.canonball;

import android.graphics.Bitmap;
import android.graphics.Point;

/**
 * Created by danmacduff on 08/03/2017.
 */

public class Canon {
    Bitmap image;
    int cannonLength;
    int cannonBaseRadius;
    Point barrelEnd;



    public Canon() {

    }

    Canon(int cannonLength , int cannonBaseRadius, int screenHeight) {
        this.cannonLength = cannonLength;
        this.cannonBaseRadius = cannonBaseRadius;
        barrelEnd = new Point(cannonLength, screenHeight / 2);
    }
}
