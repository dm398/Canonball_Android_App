package djbdevelopment.cannonball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static android.R.attr.centerX;
import static android.R.attr.centerY;
import static android.R.attr.x;

public class SpriteView extends SurfaceView implements SurfaceHolder.Callback {
    private CanonThread canonThread; // controls the game loop
    GameModel model;
    Paint textPaint;
    Paint targetPaint;

    Target target;

    Cannon cannon;

    private float targetVelocity;
    int screenHeight = CannonActivity.getScreenHeight();
    int screenWidth = CannonActivity.getScreenWidth();
    int initTargetVelocity = -screenHeight / 4;

    int noPieces = 7;


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

    // called when surface is changed
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        model = new GameModel(); // set up and start a new game
        target = model.target;
        cannon = new Cannon(((screenWidth / 8) *  5), screenHeight / 18, screenHeight);
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
        g.drawLine(screenWidth, screenHeight / 2, cannon.barrelEnd.x, cannon.barrelEnd.y, cannon.cannonPaint);
        g.drawCircle(screenWidth, (int) screenHeight / 2, (int) cannon.cannonBaseRadius, cannon.cannonPaint);
    }

    public void drawGameElements(Canvas g) {
        super.draw(g);
        g.drawColor(0xFF255D6B);
        drawTarget(g);
        drawGameInfo(g);
        drawCanon(g);
    }

    // aligns the cannon in response to a user touch
    private double alignCannon(MotionEvent event) {
         Point touchPoint = new Point((int)event.getX(), (int)event.getY());

        // compute the touch's distance from center of the screen
        // on the y-axis
        double centerMinusY = (screenHeight / 2 - touchPoint.y);

        double angle = 0; // initialize angle to 0
//        angle += Math.PI; // adjust the angle
//
//        angle += Math.PI; // adjust the angle



        // calculate the angle the barrel makes with the horizontal
        if(centerMinusY != 0)   // prevent division by 0
            angle = Math.atan((double)touchPoint.x / centerMinusY);

        // if the touch is on the lower half of the screen
        if(touchPoint.y > screenHeight / 2) {
            angle += Math.PI; // adjust the angle
        }

         cannon.barrelEnd.x = (int)(cannon.cannonLength * Math.sin(angle));
        cannon.barrelEnd.y = (int)(-cannon.cannonLength * Math.cos(angle) + screenHeight / 2);

        return angle; // return the computed angle
    }

    public void fireCannonball(MotionEvent event) {
//        if(mCannonballOnScreen)
//        {
//            return; // do nothing
//        }


        double angle = alignCannon(event);  // get the cannon barrel's angle

        // move the cannonball to be inside the cannon
//        mCannonball.x = mCannonballRadius;  // align x-coordinate with cannot
//        mCannonball.y = screenHeight / 2;  // centers ball vertically
//
//        // get the x-component of the total velocity
//        mCannonballVelocityX = (int)(mCannonballSpeed * Math.sin(angle));
//
//        // get the y-component of the total velocity
//        mCannonballVelocityY = (int)(-mCannonballSpeed * Math.cos(angle));
//        mCannonballOnScreen = true; // the cannonball is on the screen
//        ++mShotsFired;  // increment shotsFired
//
//        // play cannon fired sound
//        mSoundPool.play(mSoundMap.get(CANNON_SOUND_ID), 1, 1, 1, 0, 1f);
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
//       Target targets = model.targets;
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            for (Target t : targets) {
////                if (t.contains(x, y)) {
////                    targets.remove(t);
////                    break;
////                }
//            }
        fireCannonball(event);
        return super.onTouchEvent(event);

    }


}


