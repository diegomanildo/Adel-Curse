package utilities.io;

import com.badlogic.gdx.audio.Music;

import java.util.function.Consumer;

public final class Song implements Music {
    private final Audio intro;
    private final Audio song;

    private Audio getNotNull() {
        if (intro != null) {
            return intro;
        } else {
            return song;
        }
    }

    private Audio getPlaying() {
        if (intro != null && intro.isPlaying()) {
            return intro;
        } else {
            return song;
        }
    }

    private void forEach(Consumer<Audio> action) {
        if (intro != null) {
            action.accept(intro);
        }
        if (song != null) {
            action.accept(song);
        }
    }

    public Song(String introFilePath, String songFilePath) {
        song = new Audio(songFilePath);

        if (introFilePath != null) {
            intro = new Audio(introFilePath);
            intro.setOnCompletionListener(music -> {
                intro.stop();
                song.play();
            });
        } else {
            intro = null;
        }
    }

    public Song(String songFilePath) {
        this(null, songFilePath);
    }

    @Override
    public void play() {
        play(false);
    }

    public void play(boolean loop) {
        getNotNull().play();
        song.setLooping(loop);
    }

    @Override
    public void pause() {
        getPlaying().pause();
    }

    @Override
    public void stop() {
        getPlaying().stop();
    }

    @Override
    public boolean isPlaying() {
        return getPlaying().isPlaying();
    }

    @Override
    public void setLooping(boolean loop) {
        song.setLooping(loop);
    }

    @Override
    public boolean isLooping() {
        return song.isLooping();
    }

    @Override
    public void setVolume(float volume) {
        forEach(s -> s.setVolume(volume));
    }

    @Override
    public float getVolume() {
        return getPlaying().getVolume();
    }

    @Override
    public void setPan(float v, float v1) {
        forEach(s -> s.setPan(v, v1));
    }

    @Override
    public void setPosition(float position) {
        forEach(s -> s.setPosition(position));
    }

    @Override
    public float getPosition() {
        return getPlaying().getPosition();
    }

    @Override
    public void dispose() {
        forEach(Audio::dispose);
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        song.setOnCompletionListener(onCompletionListener);
    }

    public void fadeIn(float duration) {
        getNotNull().fadeIn(duration, false);
    }

    public void fadeIn(float duration, boolean loop) {
        setLooping(loop);
        getNotNull().fadeIn(duration);
    }

    public void fadeOut(float duration) {
        getPlaying().fadeOut(duration);
    }
}
