package djbdevelopment.cannonball;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

/**
 * Created by danmacduff on 07/03/2017.
 */
public class CannonActivity extends Activity {
    static String tag = "Cannon: ";
    SpriteView view;
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new SpriteView(this, null); setContentView(view);
    }
    // when MainActivity is paused, CannonGameFragment terminates the game
    @Override
    public void onPause() {
        super.onPause();
        view.stopGame();  // terminates the game
    }

    // when MainActivity is destroyed, CannonGameFragment releases resources
    @Override
    public void onDestroy() {
        super.onDestroy();
        view.releaseResources();
    }
}