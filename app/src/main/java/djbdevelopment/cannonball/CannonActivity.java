package djbdevelopment.cannonball;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

/**
 * Created by danmacduff on 07/03/2017.
 */
public class CannonActivity extends Activity {
    static String tag = "Cannon: ";
    public SpriteView view;

    Difficulty difficulty;

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

        view = new SpriteView(this, null);
        view.noTargets = difficulty.getValue() * 2;
        view.difficulty = difficulty;
        setContentView(view);
    }
     @Override
    public void onPause() {
        super.onPause();
        view.stopGame();  // terminates the game
    }

     @Override
    public void onDestroy() {
        super.onDestroy();
        view.stopGame();
     }
}