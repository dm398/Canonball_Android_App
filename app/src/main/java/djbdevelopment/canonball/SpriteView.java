package djbdevelopment.canonball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    Rect rect; // drawing rectangle
    static String tag = "Canon Sprite View: ";

    public SpriteView(Context context, AttributeSet attrs) {
        super(context, attrs); // call superclass constructor
         getHolder().addCallback(this);
        textPaint = new Paint();
        textPaint.setTextSize((int) (30));
        textPaint.setAntiAlias(true); // smooth the text
        textPaint.setARGB(255, 255, 255, 255);
        rect = new Rect(0, 0, CanonActivity.getScreenWidth(), CanonActivity.getScreenHeight());
        System.out.println("rect: " + rect.width() + " " + rect.height());
    }

    // called when surface is changed
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    // called when surface is first created
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        model = new GameModel(); // set up and start a new game
          canonThread = new CanonThread(holder); // create thread
        canonThread.setRunning(true); // start game running
        canonThread.start(); // start the game loop thread
    }

    // called when the surface is destroyed
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) { // ensure that thread terminates properly
        boolean retry = true;
        canonThread.setRunning(false); // terminate Thread
        while (retry) {
            try {
                canonThread.join(); // wait for Thread to finish
                retry = false;
            } catch (InterruptedException e) {
                Log.e(tag, "Thread interrupted", e);
            }
        }
    }

    public void drawGameElements(Canvas g) {
        super.draw(g);
         List<Sprite> sprites = model.sprites;
         for (Sprite sprite : sprites) {
            sprite.draw(g);
        }
// display time remaining
        g.drawText(getResources().getString(
                R.string.time_remaining_format, model.timeRemaining / 1000), 50, 25, textPaint);
        g.drawText(getResources().getString(
                R.string.score_format, model.score), 50, 60, textPaint);
    }


    private class CanonThread extends Thread {
        private SurfaceHolder surfaceHolder; // for manipulating canvas
        private boolean threadIsRunning = true; // running by default

        // initializes the surface holder
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
                    // get Canvas for exclusive drawing from this thread
                    canvas = surfaceHolder.lockCanvas(null); // lock the surfaceHolder for drawing
                    synchronized (surfaceHolder) {
                        long currentTime = System.currentTimeMillis();
                        double elapsedTimeMS = currentTime - previousFrameTime;
                        model.update(rect, (int) elapsedTimeMS); // update game state
                        drawGameElements(canvas); // draw using the canvas
                        previousFrameTime = currentTime; // update previous time
                    }
                } finally {
                    // display canvas‘s contents on the View
                    // and enable other threads to use the Canvas
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
        List<Sprite> sprites = model.sprites;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (Sprite s : sprites) {
                if (s.contains(x, y)) {
                    sprites.remove(s);
                    break;
                }
            }
        }
        return super.onTouchEvent(event);
    }

}


