package utilities.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public abstract class Audio implements Music {
    protected Audio(String channel) {
        if (channel != null) {
            Channels.register(channel, this);
        }
    }

    public abstract void play(boolean loop);

    protected void update(String channel) {
        if (channel != null) {
            Channels.updateVolume();
        }
    }

    public void fadeIn(float duration, boolean loop) {
        float startVolume = 0.0f;
        float endVolume = getVolume();
        float step = 0.01f;
        final float stepTime = duration / ((endVolume - startVolume) / step);

        setVolume(startVolume);
        play(loop);

        final float[] currentVolume = {startVolume};
        final float[] elapsed = {0f};

        Runnable fadeInStep = new Runnable() {
            @Override
            public void run() {
                elapsed[0] += Gdx.graphics.getDeltaTime();
                if (elapsed[0] >= stepTime) {
                    currentVolume[0] += step;
                    if (currentVolume[0] > endVolume) {
                        currentVolume[0] = endVolume;
                    }
                    setVolume(currentVolume[0]);
                    elapsed[0] = 0f;
                }

                if (currentVolume[0] < endVolume) {
                    Gdx.app.postRunnable(this);
                }
            }
        };

        Gdx.app.postRunnable(fadeInStep);
    }

    public void fadeIn(float duration) {
        fadeIn(duration, false);
    }

    public void fadeOut(float duration, boolean pause) {
        float startVolume = getVolume();
        float endVolume = 0.0f;
        float step = 0.01f;
        final float stepTime = duration / ((startVolume - endVolume) / step);

        final float[] currentVolume = {startVolume};
        final float[] elapsed = {0f};

        Runnable fadeOutStep = new Runnable() {
            @Override
            public void run() {
                elapsed[0] += Gdx.graphics.getDeltaTime();
                if (elapsed[0] >= stepTime) {
                    currentVolume[0] -= step;
                    if (currentVolume[0] < endVolume) {
                        currentVolume[0] = endVolume;
                    }
                    setVolume(currentVolume[0]);
                    elapsed[0] = 0f;
                }

                if (currentVolume[0] > endVolume) {
                    Gdx.app.postRunnable(this);
                } else {
                    if (pause) {
                        pause();
                    } else {
                        stop();
                    }
                    setVolume(startVolume);
                }
            }
        };

        Gdx.app.postRunnable(fadeOutStep);
    }

    public void fadeOut(float duration) {
        fadeOut(duration, false);
    }
}
