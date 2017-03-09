package djbdevelopment.canonball;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;


public class GameModel {
    Target target;
    Canon canon;
    int targetPieces = 7;
    int score;
    int timeRemaining = 100000;

    static Paint paintBlue, paintGreen;

    static {
        paintBlue = new Paint();
        paintBlue.setColor(Color.BLUE);
        paintBlue.setStyle(Paint.Style.FILL);
        paintBlue.setAntiAlias(true);

        paintGreen = new Paint();
        paintGreen.setColor(Color.GREEN);
        paintGreen.setStyle(Paint.Style.FILL);
        paintGreen.setAntiAlias(true);
    }

    public void update(Rect rect, int delay) {
        // check that the drawing rectangle is valid
        if (rect.width() <= 0 || rect.height() <= 0) {
            return;
        }

        if (!gameOver()) {
//            for (Target t : targets)
//            {
//              //  t.update(rect);
//            }
            timeRemaining -= delay;
        }
    }

    public boolean gameOver() {
        return timeRemaining <= 0;
    }

    public GameModel() {
        System.out.println("Bubble GameModel: GameModel()");
        initSprites();
        score = 0;
        System.out.println("Bubble GameModel:  finished in ()");
    }

    public void click(float x, float y) {
        if (target.contains(x, y)) {
            score += target.getScore();
            return;
        }

    }

    void initSprites() {
        double targetBeginning = CanonActivity.getScreenHeight() / 8;
        double targetEnd = CanonActivity.getScreenHeight() * 7 / 8;
        double targetDistance = CanonActivity.getScreenWidth() * 1 / 8; // place the target in the first 1/8 of screen

        double pieceLength = (targetEnd - targetBeginning) / targetPieces;
        this.target = new Target(pieceLength, targetBeginning, targetEnd, targetDistance, targetPieces);
    }
}
