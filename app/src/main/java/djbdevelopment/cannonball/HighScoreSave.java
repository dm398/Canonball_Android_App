package djbdevelopment.cannonball;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by danmacduff on 20/03/2017.
 */

public class HighScoreSave {

    Activity activity;
    SharedPreferences sharedPref;
    ArrayList<Integer> scores;

    public HighScoreSave(Activity activity) {
        this.activity = activity;
        this.scores = new ArrayList<Integer>();
        this.sharedPref = activity.getSharedPreferences(CannonActivity.highScores, 0);
        getScores();
    }

    /**
     * Returns top five scores in Shared Preferences
     * in descending order. If a value doesn't exist, the default
     * return value is set to 0.
     */
    public ArrayList<Integer> getScores() {
        scores.removeAll(scores);
        scores.add(sharedPref.getInt(activity.getResources().getString(R.string.high_score_first), 0));
        scores.add(sharedPref.getInt(activity.getResources().getString(R.string.high_score_second), 0));
        scores.add(sharedPref.getInt(activity.getResources().getString(R.string.high_score_third), 0));
        scores.add(sharedPref.getInt(activity.getResources().getString(R.string.high_score_fourth), 0));
        scores.add(sharedPref.getInt(activity.getResources().getString(R.string.high_score_fifth), 0));
        Collections.sort(scores);
        Collections.reverse(scores);
        return scores;
    }


    /**
     * Overwrite the current set of scores in shared preferences
     * with the values stored in the local scores array
     */
    public ArrayList<Integer> saveHighScores(int score) {
        SharedPreferences.Editor editor = sharedPref.edit();
        scores.add(score);
        Collections.sort(scores);
        Collections.reverse(scores);
        removeEndScores(scores);
        editor.putInt(activity.getResources().getString(R.string.high_score_first), scores.get(0));
        editor.putInt(activity.getResources().getString(R.string.high_score_second), scores.get(1));
        editor.putInt(activity.getResources().getString(R.string.high_score_third), scores.get(2));
        editor.putInt(activity.getResources().getString(R.string.high_score_fourth), scores.get(3));
        editor.putInt(activity.getResources().getString(R.string.high_score_fifth), scores.get(4));
        editor.commit();

        return scores;
    }


    /**
     *  Ensures that we only retain the top five high
     *  scores. Any trailing scores are removed from the array
     */
    public void removeEndScores(ArrayList scores) {


        if (scores.size() > 5) {
            for (int i = 6 ; i < scores.size(); i ++) {
                System.out.println("removed " + scores.get(i));
                scores.remove(i);
            }
        }
    }

}
