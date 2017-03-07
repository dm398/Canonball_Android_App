package djbdevelopment.canonball;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

/**
 * Created by danmacduff on 07/03/2017.
 */
public class CanonActivity extends Activity {
    static String tag = "Canon: ";
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
}