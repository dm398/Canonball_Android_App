package djbdevelopment.cannonball;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;


public class GameModel {

    ArrayList<Target> targets;
    Cannon cannon;
    int targetPieces = 10;
    int score;
    int timeRemaining = 100000;

    static Paint paintBlue, paintGreen, targetPaint;

    static {
        paintBlue = new Paint();
        paintBlue.setColor(Color.BLUE);
        paintBlue.setStyle(Paint.Style.FILL);
        paintBlue.setAntiAlias(true);

        paintGreen = new Paint();
        paintGreen.setColor(Color.GREEN);
        paintGreen.setStyle(Paint.Style.FILL);
        paintGreen.setAntiAlias(true);

        targetPaint = new Paint();
        targetPaint.setStrokeWidth(25);
        targetPaint.setColor(Color.WHITE);
    }

    public void update(Rect rect, int delay) {
        // check that the drawing rectangle is valid
        if (rect.width() <= 0 || rect.height() <= 0) {
            return;
        }

        if (!gameOver()) {
            timeRemaining -= delay;
        }
    }

    public boolean gameOver() {
        return timeRemaining <= 0;
    }

    public GameModel() {
         initSprites();
        score = 0;
     }

    public void click(float x, float y) {
        for (Target t : targets)
        if (t.contains(x, y)) {
            score += t.getScore();
            return;
        }
    }

    void initSprites() {

        targets = new ArrayList<Target>();
        int Xcount = 0;
        int YCount = 0;

        for (int i = 0; i < targetPieces; i++) {
            Target tar = new Target(targetPaint);
            tar.s.add(Xcount, YCount);
            targets.add(tar);
            Xcount += 200;
            if ( i % 3 == 0 ) {
                YCount += 80;
            }

        }
    }
}
