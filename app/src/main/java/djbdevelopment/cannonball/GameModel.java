package djbdevelopment.cannonball;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;


public class GameModel {

    ArrayList<Target> targets;
    Cannon cannon;
    int noTargets;
    int score;
    int timeRemaining = 10000;

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
        if (rect.width() <= 0 || rect.height() <= 0) {
            return;
        }
        if (!gameOver()) {
            timeRemaining -= delay;
        } else {
            // end of game - put some logic here!!
           // CannonActivity.view.stopGame();
           // CannonActivity.showGameOverDialog();
        }
    }

    public boolean gameOver() {
        return timeRemaining <= 0;
    }

    public GameModel(int noTargets) {
        this.noTargets = noTargets;
         initSprites();
        score = 0;
     }



    void initSprites() {

        targets = new ArrayList<Target>();
        int Xcount = 0;
        int YCount = 0;

        for (int i = 0; i < noTargets; i++) {
            System.out.println ( (i+1) + " targets created");
            Target tar = new Target();
            tar.s.add(Xcount, YCount);
            targets.add(tar);
            Xcount += 200;
            if ( i % 3 == 0 ) {
                // distribute three target per row
                YCount += 80;
                // reset x index
                Xcount = 0;
            }

        }
    }
}
