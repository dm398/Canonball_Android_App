package djbdevelopment.cannonball;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Student 630 022 295 on 08/03/2017.
 */

public class Cannon {
    Bitmap image;
    //Vector2d v;
    Matrix m;

    public Cannon() {

    }

    Cannon(Context c) {
         this.image = BitmapFactory.decodeResource(c.getResources(), R.drawable.cannon);
         m = new Matrix();
    }


    public void alignCannon(float x, float y, Canvas c){
        Point point1 = new Point(CannonActivity.getScreenWidth() / 2, CannonActivity.getScreenHeight());
        Point point2 = new Point((int)x, (int)y);
        float angle = (float) getAngleFromPoint(point1, point2);
        m.reset();
        m.setTranslate(CannonActivity.getScreenWidth()/2 - (this.image.getWidth() / 2), CannonActivity.getScreenHeight() - this.image.getHeight());
        m.postRotate(angle, CannonActivity.getScreenWidth()/2 , CannonActivity.getScreenHeight()) ;

        c.drawBitmap(this.image, m, null);
    }


    /**
     * Method taken from user2288580 on StackOverFlow, code snippet
     * available at http://stackoverflow.com/a/30162305/7154283
     *
     * @param firstPoint
     * @param secondPoint
     * @return
     */
    public double getAngleFromPoint(Point firstPoint, Point secondPoint) {

        if((secondPoint.x > firstPoint.x)) {//above 0 to 180 degrees

            return (Math.atan2((secondPoint.x - firstPoint.x), (firstPoint.y - secondPoint.y)) * 180 / Math.PI);

        }
        else if((secondPoint.x < firstPoint.x)) {//above 180 degrees to 360/0

            return 360 - (Math.atan2((firstPoint.x - secondPoint.x), (firstPoint.y - secondPoint.y)) * 180 / Math.PI);

        }//End if((secondPoint.x > firstPoint.x) && (secondPoint.y <= firstPoint.y))

        return Math.atan2(0 ,0);

    }//End public float getAngleFromPoint(Point firstPoint, Point secondPoint)



}
