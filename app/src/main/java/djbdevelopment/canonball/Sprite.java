package djbdevelopment.canonball;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

import static djbdevelopment.canonball.Constants.*;
public class Sprite {
    Vector2d s, v;
    float rad;
    Paint fg;

    public Sprite(Paint fg) {
        this();
        this.fg = fg;
    }

    static Random random = new Random();

    public Sprite() {
        s = new Vector2d();
        v = new Vector2d();
        reSpawn();
    }

    public void reSpawn() {
        rad = (float) CanonActivity.getScreenHeight()/50 + random.nextInt(40);
        s.set(0,0);
        v.set(velocityScale * (float) random.nextGaussian(),
                velocityScale * (float) random.nextGaussian());
    }

    public int getScore() {
        return fg == GameModel.paintGreen ? greenScore : blueScore;
    }

    public void update(Rect rect) {
        s.add(v);
        s.wrap(rect.width(), rect.height());
    }

    public boolean contains(float x, float y) {

        return s.dist(x,y) < rad;
    }

    public void draw(Canvas c) {
        c.drawCircle(s.x, s.y, rad, fg);
    }


}
