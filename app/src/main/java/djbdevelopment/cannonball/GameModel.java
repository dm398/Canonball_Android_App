package djbdevelopment.cannonball;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class GameModel {

    ArrayList<Target> targets;
    Context context;
    Cannon cannon;
    int noTargets;
    int score;
    int timeRemaining = 10000;
    int shotsFired = 0;

    static Paint paintBlue, paintGreen, targetPaint;

    static {
        paintBlue = new Paint();
        paintBlue.setColor(Color.BLUE);
        paintBlue.setStyle(Paint.Style.FILL);
        paintBlue.setAntiAlias(true);

        paintGreen = new Paint();
        paintGreen.setColor(Color.GREEN);
        paintGreen.setStyle(Paint.Style.FILL);
        paintGreen.setAntiAlias(true);

        targetPaint = new Paint();
        targetPaint.setStrokeWidth(25);
        targetPaint.setColor(Color.WHITE);
    }

    public void update(Rect rect, int delay) {
        if (rect.width() <= 0 || rect.height() <= 0) {
            return;
        }
        if (!gameOver()) {
            timeRemaining -= delay;
        } else {


            // end of game - put some logic here!!

            if (targets.size() == 0) {
                showGameOverDialog(R.string.win);

            }
            else {
                showGameOverDialog(R.string.lose);

            }
           // CannonActivity.showGameOverDialog();
        }
    }


    // display an AlertDialog when the game ends
    private void showGameOverDialog(final int messageId) {
        final CannonActivity c = (CannonActivity) this.context;
               c.view.stopGame();
        // DialogFragment to display quiz stats and start new quiz
        final DialogFragment gameResult = new DialogFragment() {
             @Override
            public Dialog onCreateDialog(Bundle bundle) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(messageId));
                 builder.setMessage(getResources().getString(R.string.results_format, shotsFired));
                builder.setPositiveButton(R.string.reset_game,
                        new DialogInterface.OnClickListener() {
                            // called when "Reset Game" Button is pressed
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
//                                mDialogIsDisplayed = false;
//                                newGame();  // set up and start a new game
                            }
                        });

                return builder.create();    // return the AlertDialog
            }
        };

        // in GUI thread, use FragmentManager to display the DialogFragment
        c.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        //mDialogIsDisplayed = true;

                        gameResult.setCancelable(false);    // modal dialog
                        gameResult.show(c.getFragmentManager(), "results");
                    }
                }
        );
    }
    public boolean gameOver() {
        return timeRemaining <= 0;
    }

    public GameModel(int noTargets, Context context) {
        this.noTargets = noTargets;
        this.context = context;
         initSprites();
        score = 0;
     }



    void initSprites() {

        targets = new ArrayList<Target>();
        int Xcount = 0;
        int YCount = 0;

        for (int i = 0; i < noTargets; i++) {
            System.out.println ( (i+1) + " targets created");
            Target tar = new Target();
            tar.s.add(Xcount, YCount);
            targets.add(tar);
            Xcount += 200;
            if ( i % 3 == 0 ) {
                // distribute three target per row
                YCount += 80;
                // reset x index
                Xcount = 0;
            }

        }
    }
}
