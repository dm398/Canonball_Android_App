package djbdevelopment.cannonball;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static djbdevelopment.cannonball.Constants.*;


public class SpriteView extends SurfaceView implements SurfaceHolder.Callback {
    private CannonThread CannonThread; // controls the game loop
    Context context;
    GameModel model;
    Paint textPaint;
    Paint targetPaint;
    AudioPlayer ap;
    Difficulty difficulty;
    int noTargets;
    int screenHeight = CannonActivity.getScreenHeight();
    int screenWidth = CannonActivity.getScreenWidth();
    private float lastTouchX = screenWidth / 2; /* default */
    private float lastTouchY = screenHeight / 2; /* default */
    Rect rect;
    static String tag = "Cannon Sprite View: ";

    public SpriteView(Context context, AttributeSet attrs) {
        super(context, attrs); // call superclass constructor
        getHolder().addCallback(this);
        this.context = context;
        ap = new AudioPlayer();
        initPaints();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }


    public void setupCanon() {

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        model = new GameModel(noTargets, this.context, difficulty);



        CannonThread = new CannonThread(holder);
        CannonThread.setRunning(true);
        CannonThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        ap.stop();
        boolean retry = true;
        CannonThread.setRunning(false); // terminate Thread
        while (retry) {
            try {
                CannonThread.join();
                retry = false;
            } catch (InterruptedException e) {
                Log.e(tag, "Thread interrupted", e);
            }
        }
    }

    public void stopGame() {
        if (CannonThread != null) {
            CannonThread.setRunning(false);
        }
        ap.stop();
    }


    private void updatePositions(double elapsedTimeMs) {
         for (Target t : model.targets) {
            t.update(rect);
            if (t.contains(model.cb.s.x, model.cb.s.y)) {
                ap.playGlassSound(context);
                // ball has hit the target
                model.targets.remove(t);
                // increase score by 10 x the current difficulty value
                model.score += difficulty.getValue() * 10;
                model.timeRemaining += bonusScore;
                model.cb.reSpawn();

                break;
            }
            if (model.blocker.contains(model.cb.s.x, model.cb.s.y)) {
                // ball has hit the blocker

                if (model.cb.backfiring == false) {
                    model.score -= difficulty.getValue() * 15;

                    // we only call this if the cannonball
                    // isn't already in the process of backfiring
                    model.timeRemaining += punishScore;
                    // increase blocker's length by 5%
                    float addedLength = model.blocker.getLength() * (float) 0.05;

                    if (model.blocker.stop.x + addedLength < screenWidth) {
                        // add the extra length to the end if we can get it to fit
                        model.blocker.stop.x += addedLength;
                    } else {
                        model.blocker.start.x -= addedLength;
                    }
                    model.cb.backfire();
                    ap.playBounceSound(context);
                }
            }
        }
        model.cb.update();
        model.blocker.update(rect);
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
        model.cannon.alignCannon(lastTouchX, lastTouchY, c);
    }

    public void drawCannonBall(Canvas c) {
        model.cb.draw(c);
    }

    public void drawBlocker(Canvas c) {
        model.blocker.draw(c);
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


    public void fireCannonball(MotionEvent event) {
        Point touchPoint = new Point((int) event.getX(), (int) event.getY());
        int res = model.cb.fire(touchPoint.x, touchPoint.y);
        model.shotsFired += res;

        if (res == 1) {
            ap.playFireSound(context);
        }
    }


    private class CannonThread extends Thread {
        private SurfaceHolder surfaceHolder; // for manipulating canvas
        private boolean threadIsRunning = true; // running by default

        public CannonThread(SurfaceHolder holder) {
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
        lastTouchX = x;
        lastTouchY = y;
         if (event.getAction() == MotionEvent.ACTION_DOWN) {
            fireCannonball(event);
        }
        return super.onTouchEvent(event);
    }

    public void initPaints() {
        textPaint = new Paint();
        targetPaint = new Paint();
        targetPaint.setStrokeWidth(25);
        textPaint.setTextSize((int) (30));
        textPaint.setAntiAlias(true); // smooth the text
        textPaint.setARGB(255, 255, 255, 255);
        rect = new Rect(0, 0, screenWidth, screenHeight);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this.context, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this.context, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}


