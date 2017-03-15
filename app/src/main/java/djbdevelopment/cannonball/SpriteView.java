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

public class SpriteView extends SurfaceView implements SurfaceHolder.Callback {
    private CanonThread canonThread; // controls the game loop
    GameModel model;
    Paint textPaint;
    Paint targetPaint;

    Random random;

    Cannon cannon;
    int CannonLength;

    CannonBall cb;

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

        cannon = new Cannon(CannonLength, screenHeight / 18, screenHeight);
        this.cb = new CannonBall();

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
        for (Target t : model.targets) {
            t.update(rect);
        }
        cb.update();
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

    public void drawGameElements(Canvas c) {
             super.draw(c);
            c.drawColor(0xFF255D6B);
            drawTarget(c);
            drawGameInfo(c);
            drawCanon(c);
            drawCannonBall(c);
    }

    Point getLineEnd(Point lineStart, Point touchPoint, int length) {
        double rad = Math.atan((touchPoint.y - lineStart.y) / (touchPoint.x - lineStart.x));
        Point lineEnd = new Point(lineStart);
        lineEnd.offset((int) length * (int) Math.cos(rad), (int) length * (int) Math.sin(rad) );

        return lineEnd;
    }

    // aligns the cannon in response to a user touch
    private double alignCannon(MotionEvent event) {
//        Point touchPoint = new Point((int)event.getX(), (int)event.getY());
//        Point barrelOrigin = new Point(screenWidth, screenHeight / 2);
//        int prevX = cannon.originalBarrelEnd.x;
//        int prevY = cannon.originalBarrelEnd.y;
//
//        int diffX = prevX - touchPoint.x;
//        int diffY = prevY - touchPoint.y;
//
//        System.out.println("touch y" + touchPoint.y );
//        System.out.println("touch x" + touchPoint.x );
//
//        System.out.println("diff y" + diffX );
//        System.out.println("diff x" + diffY );
//
//
////
////        //if (diffX >= 0) {
////            touchPoint.x += diffX;
////      //  }
////
////       // if (diffY >= 0 ){
////            touchPoint.y += diffY;
////       // }
//
////        touchPoint.x = touchPoint.x / cannon.cannonLength;
////        touchPoint.y = touchPoint.y / cannon.cannonLength;
//        cannon.barrelEnd = getLineEnd(barrelOrigin, touchPoint, 70);
        return 0;
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
        model.click(x, y);
        ArrayList<Target> targets = model.targets;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (Target t : targets) {
                if (t.contains(x, y)) {
                    targets.remove(t);
                    break;
                }
            }

            fireCannonball(event);

        }
        return super.onTouchEvent(event);
    }
}


