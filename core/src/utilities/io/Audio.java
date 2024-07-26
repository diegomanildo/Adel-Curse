package utilities.io;

import com.badlogic.gdx.audio.Music;

public abstract class Audio implements Music {
    protected Audio(String channel) {
        if (channel != null) {
            Channels.register(channel, this);
        }
    }

    public abstract boolean isNull();

    public abstract void play(boolean loop);

    protected void update(String channel) {
        if (channel != null) {
            Channels.updateVolume(channel);
        }
    }

    public void fadeIn(float duration) {
        fadeIn(duration, false);
    }

    public void fadeIn(float duration, boolean loop) {
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
                try {
                    Thread.sleep((long) (stepTime * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void fadeOut(float duration) {
        new Thread(() -> {
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
                try {
                    Thread.sleep((long) (stepTime * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            stop();
            setVolume(startVolume);
        }).start();
    }
}
