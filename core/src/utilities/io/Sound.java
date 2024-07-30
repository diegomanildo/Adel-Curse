package utilities.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import utilities.FilePaths;

public final class Sound extends Audio {
    private final Music music;

    public Sound(String channel, String musicPath) {
        super(channel);
        this.music = Gdx.audio.newMusic(Gdx.files.internal(FilePaths.AUDIO + musicPath));
        update(channel);
    }

    public Sound(String musicPath) {
        this(Channels.DEFAULT_CHANNEL, musicPath);
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
        music.dispose();
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        music.setOnCompletionListener(onCompletionListener);
    }

    @Override
    public boolean isNull() {
        return music == null;
    }
}