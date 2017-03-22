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
 * Created by danmacduff on 07/03/2017.
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

    public static Context getContext() {
        return getContext();
    }

    public void showGameOverDialog() {

         AlertDialog.Builder builder = new AlertDialog.Builder(CannonActivity.this);

         builder.setMessage("Game over")
                .setTitle("End of game");

         AlertDialog dialog = builder.create();

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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

         if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}