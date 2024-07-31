package utilities.io;

import com.badlogic.gdx.audio.Music;
import utilities.Utils;

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

    public synchronized void fadeIn(float duration) {
        fadeIn(duration, false);
    }

    public synchronized void fadeIn(float duration, boolean loop) {
        new Thread(() -> {
            float startVolume = 0.0f;
            float endVolume = getVolume();
            float step = 0.01f;
            float stepTime = duration / ((endVolume - startVolume) / step);
            float currentVolume = startVolume;
            play(loop);
            while (currentVolume < endVolume) {
                currentVolume += step;
                if (currentVolume > endVolume) {
                    currentVolume = endVolume;
                }
                setVolume(currentVolume);
                Utils.sleep(stepTime * 1000);
            }
        }).start();
    }

    public synchronized void fadeOut(float duration) {
        new Thread(() -> {
            try {
                float startVolume = getVolume();
                float endVolume = 0.0f;
                float step = 0.01f;
                float stepTime = duration / ((startVolume - endVolume) / step);
                float currentVolume = startVolume;
                while (currentVolume > endVolume) {
                    currentVolume -= step;
                    if (currentVolume < endVolume) {
                        currentVolume = endVolume;
                    }
                    setVolume(currentVolume);
                    Utils.sleep(stepTime * 1000f);
                }
                setVolume(startVolume);
            } finally {
                stop();
            }
        }).start();
    }
}
