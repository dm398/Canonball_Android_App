package djbdevelopment.cannonball;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Student 630 022 295 on 07/03/2017.
 */
public class CannonActivity extends Activity {
    static String tag = "Cannon: ";
    public SpriteView view;
    Difficulty difficulty;
    final static String highScores = "highScorePrefs";
    SharedPreferences SP;

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        difficulty = (Difficulty) getIntent().getExtras().getSerializable("Difficulty");
        SP = getSharedPreferences(highScores, 0);
        view = new SpriteView(this, null);
        view.noTargets = difficulty.getValue() * 2;
        view.difficulty = difficulty;
        setContentView(view);
    }
     @Override
    public void onPause() {
         System.out.println("paused");
        super.onPause();
        view.stopGame();  // terminates the game
    }

     @Override
    public void onDestroy() {
         System.out.println("destroyed");
         super.onDestroy();
        view.stopGame();
     }

    @Override
    public void onStop() {
        System.out.println("stopped");
        super.onDestroy();
        view.stopGame();
    }


}