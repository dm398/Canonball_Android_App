package djbdevelopment.cannonball;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;

import java.util.ArrayList;

import static djbdevelopment.cannonball.Constants.blockerSpeed;
import static djbdevelopment.cannonball.Constants.cannonBallSpeed;
import static djbdevelopment.cannonball.Constants.targetSpeed;


public class GameModel {

    ArrayList<Target> targets;
    Cannon cannon;
    CannonBall cb;
    Blocker blocker;
    HighScoreSave highScoreSave;
    ArrayList<Integer> highScores;
    Difficulty difficulty;
    Context context;
    int noTargets;
    int score;
    int timeElapsed = 0;
    int timeRemaining = 10000;
    int shotsFired = 0;

    static Paint blockerPaint;

    static {
        blockerPaint = new Paint();
        blockerPaint.setStrokeWidth(25);
        blockerPaint.setColor(Color.BLACK);
    }

    public void update(Rect rect, int delay) {
        if (targets.size() == 0) {
            saveHighScore();
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
                saveHighScore();
                showGameOverDialog(R.string.win);
            } else {
                saveHighScore();
                showGameOverDialog(R.string.lose);
            }

        }
    }


    public void adjustTargetSpeed() {
        targetSpeed += (difficulty.getValue() - 1);
    }

    public void adjustDifficultySettings() {
        switch (difficulty) {
            case VERY_EASY:
                for (Target t : targets) {
                    t.rad += 30;
                }
                blockerSpeed = difficulty.getValue();
                break;
            case EASY:
                for (Target t : targets) {
                    t.rad += 20;
                }
                blockerSpeed = difficulty.getValue();
                break;
            case MODERATE:
                blockerSpeed = difficulty.getValue();
                break;
            case HARD:
                for (Target t : targets) {
                    t.rad -= 5;
                }
                blockerSpeed = difficulty.getValue();
                break;
            case VERY_HARD:
                for (Target t : targets) {
                    t.rad -= 10;
                }
                blockerSpeed = difficulty.getValue();
                break;
        }
    }

    public void resetConstants() {
        targetSpeed = 2;
        blockerSpeed = 3;
        cannonBallSpeed = CannonActivity.getScreenHeight() / 35;
    }

    private void showGameOverDialog(final int messageId) {
        final CannonActivity c = (CannonActivity) this.context;
        c.view.stopGame();
        final DialogFragment gameResult = new DialogFragment() {
            @Override
            public Dialog onCreateDialog(Bundle bundle) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(messageId));
                builder.setMessage(getResources().getString(R.string.end_game_message, score, shotsFired, timeElapsed / 1000, highScores.get(0), highScores.get(1), highScores.get(2), highScores.get(3), highScores.get(4)));
                builder.setPositiveButton(R.string.reset_game,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                newGame();
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

    public void newGame() {
        final CannonActivity c = (CannonActivity) this.context;

        c.view = new SpriteView(c, null);
        c.view.noTargets = difficulty.getValue() * 2;
        c.view.difficulty = difficulty;
        c.setContentView(c.view);
    }

    public GameModel(int noTargets, Context context, Difficulty difficulty) {
        this.noTargets = noTargets;
        this.context = context;
        final CannonActivity c = (CannonActivity) this.context;
        if (highScoreSave == null) {
            highScoreSave = new HighScoreSave(c);
        }

        System.out.println("New game! Current high scores:" + highScoreSave.getScores());
        this.difficulty = difficulty;
        initSprites();
        adjustDifficultySettings();

        score = 0;
    }


    public void saveHighScore() {
        highScores = highScoreSave.saveHighScores(score);
    }


    void initTargets() {
        targets = new ArrayList<Target>();
        int Xcount = 0;
        int YCount = 0;

        for (int i = 0; i < noTargets; i++) {
            System.out.println((i + 1) + " targets created");
            Target tar = new Target();
            tar.s.add(Xcount, YCount);
            targets.add(tar);
            Xcount += 200;
            if (i % 3 == 0) {
                // distribute three target per row
                YCount += 90;
                // reset x index
                Xcount = 0;
            }
        }
    }

    void initCannonBall() {
        this.cb = new CannonBall(this.context);
    }

    void initCannon() {
        this.cannon = new Cannon(this.context);
    }

    void initBlocker() {
        this.blocker = new Blocker(blockerPaint);
        Target lastTarget = targets.get(targets.size() - 1);

        float blockerY = lastTarget.s.y + 80;
        blocker.start.y = blockerY;
        blocker.stop.y = blockerY;
    }

    void initSprites() {
        resetConstants();
        adjustTargetSpeed();
        initTargets();
        initCannonBall();
        initCannon();
        initBlocker();
    }
}
