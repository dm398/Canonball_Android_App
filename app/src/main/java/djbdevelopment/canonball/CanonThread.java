package djbdevelopment.canonball;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.List;

/**
 * Created by danmacduff on 07/03/2017.
 */

//public class CanonThread extends Thread {
//    private SurfaceHolder surfaceHolder; // for manipulating canvas
//    private boolean threadIsRunning = true; // running by default
//
//    // initializes the surface holder
//    public CanonThread(SurfaceHolder holder) {
//        surfaceHolder = holder;
//        setName("CanonThread");
//    }
//
//    // changes running state
//    public void setRunning(boolean running) {
//        threadIsRunning = running;
//    }
//
//    // controls the game loop
//    @Override
//    public void run() {
//        Canvas canvas = null; // used for drawing
//        long previousFrameTime = System.currentTimeMillis();
//        while (threadIsRunning) {
//            try {
//                // get Canvas for exclusive drawing from this thread
//                canvas = surfaceHolder.lockCanvas(null); // lock the surfaceHolder for drawing
//                synchronized (surfaceHolder) {
//                    long currentTime = System.currentTimeMillis();
//                    double elapsedTimeMS = currentTime - previousFrameTime;
//                    model.update(rect, (int) elapsedTimeMS); // update game state
//                    drawGameElements(canvas); // draw using the canvas
//                    previousFrameTime = currentTime; // update previous time
//                }
//            } finally {
//                // display canvas‘s contents on the View
//                // and enable other threads to use the Canvas
//                if (canvas != null)
//                    surfaceHolder.unlockCanvasAndPost(canvas);
//            }
//        }
//    }
//
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        float x = event.getX();
//        float y = event.getY();
//        model.click(x, y);
//        List<Sprite> sprites = model.sprites;
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            for (Sprite s : sprites) {
//                if (s.contains(x, y)) {
//                    sprites.remove(s);
//                    break;
//                }
//            }
//        }
//        return super.onTouchEvent(event);
//    }
//}
//}

