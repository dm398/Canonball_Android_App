package djbdevelopment.cannonball;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by danmacduff on 19/03/2017.
 */

public class AudioPlayer {

    private MediaPlayer mp;

    public void stop() {
        if (mp != null) {
            mp.release();
            mp = null;
        }
    }

    public void play(Context c, int resid) {
        stop();
        mp = MediaPlayer.create(c, resid);
        mp.start();
    }

    public void playFireSound(Context c) {
        play(c, R.raw.cannon);
    }

    public void playBounceSound(Context c) {
        play(c, R.raw.bounce);
    }

    public void playGlassSound(Context c) {
        play(c, R.raw.glass);
    }
}
