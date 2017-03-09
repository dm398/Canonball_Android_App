package djbdevelopment.canonball;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.List;

public class SpriteView extends SurfaceView implements SurfaceHolder.Callback {
    private CanonThread canonThread; // controls the game loop
    GameModel model;
    Paint textPaint;
    Paint targetPaint;
    Paint cannonPaint;

    Target target;

    Canon cannon;

    private float targetVelocity;
    int screenHeight = CanonActivity.getScreenHeight();
    int screenWidth = CanonActivity.getScreenWidth();
    int initTargetVelocity = -screenHeight / 4;

    int noPieces = 7;


    Rect rect; // drawing rectangle
    static String tag = "Canon Sprite View: ";

    public SpriteView(Context context, AttributeSet attrs) {
        super(context, attrs); // call superclass constructor
        getHolder().addCallback(this);

        targetVelocity = initTargetVelocity;
        textPaint = new Paint();
        targetPaint = new Paint();
        cannonPaint = new Paint();
        cannonPaint.setARGB(255, 255, 255, 255);
        cannonPaint.setStrokeWidth(10);
        targetPaint.setStrokeWidth(25);
        textPaint.setTextSize((int) (30));
        textPaint.setAntiAlias(true); // smooth the text
        textPaint.setARGB(255, 255, 255, 255);
        rect = new Rect(0, 0, screenWidth, screenHeight);

        System.out.println("rect: " + rect.width() + " " + rect.height());
    }

    // called when surface is changed
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        model = new GameModel(); // set up and start a new game
        target = model.target;
        cannon = new Canon(((screenWidth / 8) * 7), screenHeight / 18, screenHeight);
        canonThread = new CanonThread(holder); // create thread
        canonThread.setRunning(true); // start game running
        canonThread.start(); // start the game loop thread
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

    // stops the game; called by CannonGameFragment's onPause method
    public void stopGame() {
        if (canonThread != null)
            canonThread.setRunning(false);    // tell thread to terminate
    }

    // releases resources; called by CannonGame's onDestroy method
    public void releaseResources() {
//        mSoundPool.release();   // release all resources used by the SoundPool
//        mSoundPool = null;
    }

    private void updatePositions(double elapsedTimeMs) {
        double interval = elapsedTimeMs / 1000.0;
        targetVelocity = target.update(rect, elapsedTimeMs, targetVelocity);
    }


    public void drawTarget(Canvas g) {
        Point currentPoint = new Point(); // start of current target section

        currentPoint.x = target.start.x;
        currentPoint.y = target.start.y;

        // draw the target
        for (int i = 0; i < 7; i++) {
            // if this target piece is not hit, draw it
            // if (!hitStates[i]) {
            // alternate coloring the pieces
            if (i % 2 != 0)
                targetPaint.setColor(Color.RED);
            else
                targetPaint.setColor(Color.WHITE);

            g.drawLine(currentPoint.x, currentPoint.y, target.end.x,
                    (int) (currentPoint.y + target.pieceLength), targetPaint);
            //}

            // move currentPoint to the start of the next piece
            currentPoint.y += target.pieceLength;
        }
    }

    public void drawGameInfo(Canvas g) {
        g.drawText(getResources().getString(
                R.string.time_remaining_format, model.timeRemaining / 1000), 50, 25, textPaint);
        g.drawText(getResources().getString(
                R.string.score_format, model.score), 50, 60, textPaint);

    }

    public void drawCanon(Canvas g) {
        // draw the cannon barrel
        g.drawLine(screenWidth, screenHeight / 2, cannon.barrelEnd.x, cannon.barrelEnd.y, cannonPaint);

        // draw the cannon base
        g.drawCircle(screenWidth, (int) screenHeight / 2, (int) cannon.cannonBaseRadius, cannonPaint);
    }

    public void drawGameElements(Canvas g) {
        super.draw(g);
        g.drawColor(0xFF255D6B);
        drawTarget(g);
        drawGameInfo(g);
        drawCanon(g);


    }


    private class CanonThread extends Thread {
        private SurfaceHolder surfaceHolder; // for manipulating canvas
        private boolean threadIsRunning = true; // running by default

        public CanonThread(SurfaceHolder holder) {
            surfaceHolder = holder;
            setName("CanonThread");
        }

        // changes running state
        public void setRunning(boolean running) {
            threadIsRunning = running;
        }

        // controls the game loop
        @Override
        public void run() {
            Canvas canvas = null; // used for drawing
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
        model.click(x, y);
//       Target targets = model.targets;
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            for (Target t : targets) {
////                if (t.contains(x, y)) {
////                    targets.remove(t);
////                    break;
////                }
//            }
        return super.onTouchEvent(event);

    }


}


