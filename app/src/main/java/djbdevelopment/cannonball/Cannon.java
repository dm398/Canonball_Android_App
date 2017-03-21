package djbdevelopment.cannonball;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

/**
 * Created by danmacduff on 08/03/2017.
 */

public class Cannon {
    int cannonLength;
    int cannonBaseRadius;
    Paint cannonPaint;
    ImageView iv;


    Bitmap image;
    Vector2d v;

    public Cannon() {

    }

    Cannon(int cannonLength , int cannonBaseRadius, int screenHeight, Context c) {
        this.cannonLength = cannonLength;
        this.cannonBaseRadius = cannonBaseRadius;
        this.image = BitmapFactory.decodeResource(c.getResources(), R.drawable.cannon);
        iv = new ImageView(c);
        iv.setImageBitmap(image);
        cannonPaint = new Paint();
        cannonPaint.setARGB(255, 255, 255, 255);
        cannonPaint.setStrokeWidth(8);


        v = new Vector2d();
    }

    public void draw(Canvas c){
        //c.drawCircle(CannonActivity.getScreenWidth()/2, CannonActivity.getScreenHeight(),  this.cannonBaseRadius, this.cannonPaint);
        c.drawBitmap(this.image, CannonActivity.getScreenWidth()/2 - (  this.image.getWidth() / 2) , CannonActivity.getScreenHeight() - this.image.getHeight(), this.cannonPaint);

    }

    public void rotate(){

    }

}
