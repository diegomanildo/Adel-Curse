package utilities.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import utilities.FilePaths;

public class Audio implements Music {
    private final Music music;

    public Audio(String musicPath) {
        music = Gdx.audio.newMusic(Gdx.files.internal(FilePaths.AUDIO + musicPath));
    }

    @Override
    public void play() {
        music.play();
    }

    public void play(boolean loop) {
        setLooping(loop);
        play();
    }

    @Override
    public void pause() {
        music.pause();
    }

    @Override
    public void stop() {
        music.stop();
    }

    @Override
    public boolean isPlaying() {
        return music.isPlaying();
    }

    @Override
    public void setLooping(boolean loop) {
        music.setLooping(loop);
    }

    @Override
    public boolean isLooping() {
        return music.isLooping();
    }

    @Override
    public void setVolume(float volume) {
        music.setVolume(volume);
    }

    @Override
    public float getVolume() {
        return music.getVolume();
    }

    @Override
    public void setPan(float v, float v1) {
        music.setPan(v, v1);
    }

    @Override
    public void setPosition(float v) {
        music.setPosition(v);
    }

    @Override
    public float getPosition() {
        return music.getPosition();
    }

    @Override
    public void dispose() {
        music.stop();
        music.dispose();
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        music.setOnCompletionListener(onCompletionListener);
    }

    public void fadeIn(float duration) {
        new Thread(() -> {
            float startVolume = 0.0f;
            float endVolume = getVolume();
            float step = 0.01f;
            float stepTime = duration / ((endVolume - startVolume) / step);
            float currentVolume = startVolume;
            play(true);
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
        }).start();
    }
}
