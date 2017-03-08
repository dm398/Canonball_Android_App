package djbdevelopment.canonball;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import static djbdevelopment.canonball.Constants.velocityScale;

/**
 * Created by danmacduff on 07/03/2017.
 */

public class Target {
    public Point start = new Point();
    public Point end = new Point();

//
//    Vector2d s, v;
//    float rad;
//
    public Target() {
        start = new Point();
        end = new Point();
       // Spawn();
    }
//
//    public void update(Rect rect) {
//        s.add(v);
//        s.wrap(rect.width(), rect.height());
//    }
//
//    public void Spawn() {
//        rad = (float) CanonActivity.getScreenHeight()/50;
//        s.set(100,100);
//        v.set(200,200);
//    }
//    public int getScore() {
//        return 10;
//    }
//    public boolean contains(float x, float y) {
//
//        return s.dist(x,y) < rad;
//    }
//    public void draw(Canvas c) {
//
//
//        Paint red = new Paint();
//        red.setARGB(255, 255, 0 ,0);
//
//        Paint white = new Paint();
//        white.setARGB(255, 255, 255, 255);
//
//        int w = c.getWidth()/10, h = c.getHeight()/10;
//
//        for (int i = 0; i < 5 ; i ++ ) {
//            c.drawOval(w*i/10, h*i/10, w*(10-i)/10, h*(10-i)/10, i % 2 == 0? red: white);
//
//         }
//    }
}
