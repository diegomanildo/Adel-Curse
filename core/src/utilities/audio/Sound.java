package utilities.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import utilities.FilePaths;

public final class Sound extends Audio {
    private final Music sound;

    public Sound(String channel, String musicPath) {
        super(channel);
        this.sound = Gdx.audio.newMusic(Gdx.files.internal(FilePaths.AUDIO + musicPath));
        update(channel);
    }

    @Override
    public void play(boolean loop) {
        setLooping(loop);
        play();
    }

    @Override
    public void play() {
        Gdx.app.postRunnable(sound::play);
    }

    @Override
    public void pause() {
        sound.pause();
    }

    @Override
    public void stop() {
        sound.stop();
    }

    @Override
    public boolean isPlaying() {
        return sound.isPlaying();
    }

    @Override
    public void setLooping(boolean loop) {
        sound.setLooping(loop);
    }

    @Override
    public boolean isLooping() {
        return sound.isLooping();
    }

    @Override
    public void setVolume(float volume) {
        try {
            sound.setVolume(volume);
        } catch (Exception ignored) {

        }
    }

    @Override
    public float getVolume() {
        return sound.getVolume();
    }

    @Override
    public void setPan(float pan, float volume) {
        sound.setPan(pan, volume);
    }

    @Override
    public void setPosition(float position) {
        sound.setPosition(position);
    }

    @Override
    public float getPosition() {
        return sound.getPosition();
    }

    @Override
    public void dispose() {
        sound.dispose();
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener listener) {
        sound.setOnCompletionListener(listener);
    }
}
