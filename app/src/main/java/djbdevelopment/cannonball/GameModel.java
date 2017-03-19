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

import java.util.ArrayList;

import static djbdevelopment.cannonball.Constants.blockerSpeed;
import static djbdevelopment.cannonball.Constants.targetSpeed;


public class GameModel {

    ArrayList<Target> targets;

    Difficulty difficulty;
    Context context;
    Cannon cannon;
    int noTargets;
    int score;
    int timeElapsed = 0;
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
        if (targets.size() == 0) {
            showGameOverDialog(R.string.win);
        }
        if (rect.width() <= 0 || rect.height() <= 0) {
            return;
        }
        if (!gameOver()) {
            timeRemaining -= delay;
            timeElapsed += delay;
        } else {
             if (targets.size() == 0) {
                showGameOverDialog(R.string.win);

            }
            else {
                showGameOverDialog(R.string.lose);
            }
         }
    }


    public void adjustTargetSpeed() {
        targetSpeed += (difficulty.getValue() - 1);
    }
    public void adjustDifficultySettings() {
        System.out.println("--- adjusting difficulty");
        System.out.println("difficulty rating " + difficulty.getValue());

        if (difficulty == null) {
            System.out.println("difficulty is null");

        }
         switch (difficulty) {
            case VERY_EASY :
                for (Target t : targets) {
                    t.rad += 30;
                }
                blockerSpeed = difficulty.getValue() ;
                break;
            case EASY:
                for (Target t : targets) {
                    t.rad += 20;
                }
                blockerSpeed = difficulty.getValue() ;
                break;
            case MODERATE:
                blockerSpeed = difficulty.getValue() ;
                break;
            case HARD:
                for (Target t : targets) {
                    t.rad -= 5;
                }
                blockerSpeed = difficulty.getValue() ;
                break;
            case VERY_HARD:
                for (Target t : targets) {
                    t.rad -= 10;
                }
                blockerSpeed = difficulty.getValue() ;
                break;
        }
    }

    public void resetConstants () {
          targetSpeed = 2;
         blockerSpeed = 3;
    }

     private void showGameOverDialog(final int messageId) {
        final CannonActivity c = (CannonActivity) this.context;
               c.view.stopGame();
         final DialogFragment gameResult = new DialogFragment() {
             @Override
            public Dialog onCreateDialog(Bundle bundle) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(messageId));
                 builder.setMessage(getResources().getString(R.string.results_format, score, shotsFired, timeElapsed/1000));
                builder.setPositiveButton(R.string.reset_game,
                        new DialogInterface.OnClickListener() {
                             @Override
                            public void onClick(DialogInterface dialogInterface, int which) {

                             c.view = new SpriteView(c, null);
                                 c.view.noTargets = difficulty.getValue() * 2;
                              c.view.difficulty = difficulty;
                                 c.setContentView(c.view);
                            }
                        });

                return builder.create();
            }
        };

         c.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                         gameResult.setCancelable(false);
                        gameResult.show(c.getFragmentManager(), "results");
                    }
                }
        );
    }
    public boolean gameOver() {
        return timeRemaining <= 0;
    }

    public GameModel(int noTargets, Context context, Difficulty difficulty) {
        this.noTargets = noTargets;
        this.context = context;
        this.difficulty = difficulty;
         initSprites();
        adjustDifficultySettings();

        score = 0;
     }



    void initSprites() {
        resetConstants();
        adjustTargetSpeed();
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
                YCount += 90;
                // reset x index
                Xcount = 0;
            }
        }
    }
}
