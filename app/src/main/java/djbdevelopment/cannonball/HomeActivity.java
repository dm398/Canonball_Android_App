package djbdevelopment.cannonball;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioDifficulty);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent i = null;

        switch (id) {
            case R.id.action_about:
                  i = new Intent(this, SimpleContentActivity.class).putExtra(SimpleContentActivity.EXTRA_FILE,
                        "file:///android_asset/misc/about.html");
                startActivity(i);
                return (true);

            case R.id.action_help:
                  i = new Intent(this, SimpleContentActivity.class).putExtra(SimpleContentActivity.EXTRA_FILE,
                        "file:///android_asset/misc/help.html");
                startActivity(i);
                return (true);
        }
        return super.onOptionsItemSelected(item);

    }



    public void btnClicked(View view) {
        Intent i;
        switch(view.getId()) {
            case R.id.btnVEasy:
                i = new Intent(this, CannonActivity.class).putExtra("Difficulty", Difficulty.VERY_EASY);
                    startActivity(i);

                     break;
            case R.id.btnEasy:
                i = new Intent(this, CannonActivity.class).putExtra("Difficulty",  Difficulty.EASY);
                    startActivity(i);
                            break;
            case R.id.btnModerate:
                i = new Intent(this, CannonActivity.class).putExtra("Difficulty",  Difficulty.MODERATE);
                    startActivity(i);

                break;
            case R.id.btnHard:

                i = new Intent(this, CannonActivity.class).putExtra("Difficulty",  Difficulty.HARD);
                startActivity(i);

                break;
            case R.id.btnVHard:
                 i = new Intent(this, CannonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Difficulty", Difficulty.VERY_HARD);
                i.putExtras(bundle);
                startActivity(i);

                break;
            case R.id.btnHighScores:
               Activity a = this;
                HighScoreSave highScoreSave = new HighScoreSave(a);
                final ArrayList<Integer> highScores = highScoreSave.getScores();
                final DialogFragment gameResult = new DialogFragment() {
                    @Override
                    public Dialog onCreateDialog(Bundle bundle) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("High Scores");
                        builder.setMessage(getResources().getString(R.string.high_scores, highScores.get(0), highScores.get(1), highScores.get(2), highScores.get(3), highScores.get(4) ));
                        builder.setPositiveButton(R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dismiss();
                                    }
                                });

                        return builder.create();
                    }
                };
                runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                gameResult.setCancelable(false);
                                gameResult.show(getFragmentManager(), "results");
                            }
                        }
                );
                break;

            default:
                System.out.println(view.getId());
                break;

        }
    }
}
