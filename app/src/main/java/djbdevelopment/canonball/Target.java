package djbdevelopment.canonball;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import static djbdevelopment.canonball.Constants.velocityScale;

/**
 * Created by danmacduff on 07/03/2017.
 */

public class Target extends Sprite {
    public Point start;
    public Point end;
    double pieceLength;
    double targetBeginning;
    double targetEnd;
    double targetDistance;
    int noPieces;



    public Target(double pieceLength, double targetBeginning, double targetEnd, double targetDistance, int noPieces ) {
        start = new Point();
        end = new Point();
//        start.y = (int) targetBeginning;
//        end.y = (int) targetEnd;
        this.pieceLength = pieceLength;
        this.targetBeginning = targetBeginning;
        this.targetEnd = targetEnd;
        this.targetDistance = targetDistance;
        this.noPieces = noPieces;

        start = (new Point((int)targetDistance, (int)targetBeginning));
        end = (new Point((int)targetDistance, (int)targetEnd));

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
    public int getScore() {
        return 10;
    }
    public boolean contains(float x, float y) {
        return s.dist(x,y) < targetDistance;
    }
}
