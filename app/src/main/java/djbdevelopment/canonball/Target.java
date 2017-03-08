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
    public Point start;
    public Point end;
    double pieceLength;
    double targetBeginning;
    double targetEnd;
    int noPieces = 10;

//
//    Vector2d s, v;
//    float rad;
//

    public Target(double pieceLength, double targetBeginning, double targetEnd, int noPieces ) {
        start = new Point();
        end = new Point();
        start.y = (int) targetBeginning;
        end.y = (int) targetEnd;
        this.pieceLength = pieceLength;
        this.targetBeginning = targetBeginning;
        this.targetEnd = targetEnd;
        this.noPieces = noPieces;
     }
//
    public float update(Rect rect, double elapsedTime, float targetVelocity) {
        double interval = elapsedTime / 1000.0;
        double targetUpdate = interval * targetVelocity;
        this.start .y += targetUpdate;
        this.end.y += targetUpdate;
        System.out.println("start of target " + this.start.y);
        System.out.println("end of target " + this.end.y);
        System.out.println("rect height " + rect.height());
        if(this.start.y < 0 || this.end.y > rect.height()) {
            return targetVelocity *= -1;
        }
        else {
            return targetVelocity;
        }
    }
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
