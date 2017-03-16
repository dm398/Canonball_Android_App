package djbdevelopment.cannonball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

import static djbdevelopment.cannonball.Constants.bonusScore;
import static djbdevelopment.cannonball.Constants.punishScore;
import static djbdevelopment.cannonball.Constants.velocityScale;

public class SpriteView extends SurfaceView implements SurfaceHolder.Callback {
    private CanonThread canonThread; // controls the game loop
    GameModel model;
    Paint textPaint;
    Paint targetPaint;

    Random random;

    Cannon cannon;
    CannonBall cb;
    Blocker blocker;
    int CannonLength;



    int noTargets;

    private float targetVelocity;
    int screenHeight = CannonActivity.getScreenHeight();
    int screenWidth = CannonActivity.getScreenWidth();
    int initTargetVelocity = -screenHeight / 4;

    Rect rect; // drawing rectangle
    static String tag = "Cannon Sprite View: ";

    public SpriteView(Context context, AttributeSet attrs) {
        super(context, attrs); // call superclass constructor
        getHolder().addCallback(this);

        targetVelocity = initTargetVelocity;
        textPaint = new Paint();
        targetPaint = new Paint();
        targetPaint.setStrokeWidth(25);
        textPaint.setTextSize((int) (30));
        textPaint.setAntiAlias(true); // smooth the text
        textPaint.setARGB(255, 255, 255, 255);
        rect = new Rect(0, 0, screenWidth, screenHeight);

        System.out.println("rect: " + rect.width() + " " + rect.height());
    }

     @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        model = new GameModel(noTargets); // set up and start a new game
        random = new Random();
        CannonLength = ((screenWidth / 8) *  5);

        this.cannon = new Cannon(CannonLength, screenHeight / 18, screenHeight);
        this.cb = new CannonBall(targetPaint);
        this.blocker = new Blocker(targetPaint);
        Target lastTarget = model.targets.get(model.targets.size() - 1);

        float blockerY = lastTarget.s.y + 50;

        blocker.start.y = blockerY;
        blocker.stop.y = blockerY;


        canonThread = new CanonThread(holder);
        canonThread.setRunning(true);
        canonThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        canonThread.setRunning(false); // terminate Thread
        while (retry) {
            try {
                canonThread.join();
                retry = false;
            } catch (InterruptedException e) {
                Log.e(tag, "Thread interrupted", e);
            }
        }
    }

     public void stopGame() {
        if (canonThread != null)
            canonThread.setRunning(false);
    }

     public void releaseResources() {

    }

    private void updatePositions(double elapsedTimeMs) {
        double interval = elapsedTimeMs / 1000.0;
        for (Target t : model.targets) {
            t.update(rect);
            if (t.contains(cb.s.x, cb.s.y)) {
                // ball has hit the target
                model.targets.remove(t);
                model.score += t.getScore();
                model.timeRemaining += bonusScore;
                cb.reSpawn();
                break;
            }
            if (blocker.contains(cb.s.x, cb.s.y)) {
                // ball has hit the blocker

                if (cb.backfiring == false) {
                    // we only call this if the cannonball
                    // isn't already in the process of backfiring
                    model.timeRemaining += punishScore;
                    // increase blocker's length by 5%
                    float addedLength = blocker.getLength() * (float) 0.05;

                    if (blocker.stop.x + addedLength < screenWidth) {
                        // add the extra length to the end if we can get it to fit
                        blocker.stop.x += addedLength;
                    } else {
                        blocker.start.x -= addedLength;
                    }
                    cb.backfire();
                }
            }
        }
        cb.update(rect);
        blocker.update(rect);
    }


    public void drawTarget(Canvas g) {
         for (Target t : model.targets) {
             t.draw(g);
         }
    }


    public void drawGameInfo(Canvas g) {
        g.drawText(getResources().getString(
                R.string.time_remaining_format, model.timeRemaining / 1000), 50, 25, textPaint);
        g.drawText(getResources().getString(
                R.string.score_format, model.score), 50, 60, textPaint);
    }

    public void drawCanon(Canvas c) {
        cannon.draw(c);
    }

    public void drawCannonBall(Canvas c) {
         cb.draw(c);
    }

    public void drawBlocker(Canvas c) {
        blocker.draw(c);
    }


    public void drawGameElements(Canvas c) {
         super.draw(c);
        c.drawColor(0xFF255D6B);
        drawTarget(c);
        drawGameInfo(c);
        drawCanon(c);
         drawCannonBall(c);
        drawBlocker(c);


    }

    Point getLineEnd(Point lineStart, Point touchPoint, int length) {
        double rad = Math.atan((touchPoint.y - lineStart.y) / (touchPoint.x - lineStart.x));
        Point lineEnd = new Point(lineStart);
        lineEnd.offset((int) length * (int) Math.cos(rad), (int) length * (int) Math.sin(rad) );

        return lineEnd;
    }

    public void fireCannonball(MotionEvent event) {
        Point touchPoint = new Point((int)event.getX(), (int)event.getY());
        cb.fire(touchPoint.x, touchPoint.y);
    }


    private class CanonThread extends Thread {
        private SurfaceHolder surfaceHolder; // for manipulating canvas
        private boolean threadIsRunning = true; // running by default

        public CanonThread(SurfaceHolder holder) {
            surfaceHolder = holder;
            setName("CanonThread");
        }

        public void setRunning(boolean running) {
            threadIsRunning = running;
        }

        @Override
        public void run() {
            Canvas canvas = null;
            long previousFrameTime = System.currentTimeMillis();
            while (threadIsRunning) {
                try {
                    canvas = surfaceHolder.lockCanvas(null); // lock the surfaceHolder for drawing
                    synchronized (surfaceHolder) {
                        long currentTime = System.currentTimeMillis();
                        double elapsedTimeMS = currentTime - previousFrameTime;
                        model.update(rect, (int) elapsedTimeMS); // update game state
                        drawGameElements(canvas); // draw using the canvas
                        previousFrameTime = currentTime; // update previous time
                        updatePositions(elapsedTimeMS);
                    }
                } finally {
                    if (canvas != null)
                        surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
         ArrayList<Target> targets = model.targets;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            fireCannonball(event);

        }
        return super.onTouchEvent(event);
    }
}


