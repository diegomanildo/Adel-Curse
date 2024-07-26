package utilities.io;

import java.util.function.Consumer;

public final class Song extends Audio {
    private final Sound intro;
    private final Sound song;

    private Sound getNotNull() {
        return intro != null ? intro : song;
    }

    private Sound getPlaying() {
        return intro != null && intro.isPlaying() ? intro : song;
    }

    private void forEach(Consumer<Sound> action) {
        if (intro != null) {
            action.accept(intro);
        }
        if (song != null) {
            action.accept(song);
        }
    }

    public Song(String channel, String introFilePath, String songFilePath) {
        super(channel);
        song = new Sound(null, songFilePath);

        if (introFilePath == null) {
            intro = null;
        } else {
            intro = new Sound(null, introFilePath);
            intro.setOnCompletionListener(music -> {
                intro.stop();
                song.play();
            });
        }

        update(channel);
    }

    public Song(String channel, String songFilePath) {
        this(channel, null, songFilePath);
    }

    public Song(String songFilePath) {
        this(Channels.DEFAULT_CHANNEL, songFilePath);
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
        forEach(Sound::dispose);
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        song.setOnCompletionListener(onCompletionListener);
    }

    @Override
    public boolean isNull() {
        return intro == null && song == null;
    }

    @Override
    public void fadeIn(float duration) {
        getNotNull().fadeIn(duration, false);
    }

    @Override
    public void fadeIn(float duration, boolean loop) {
        setLooping(loop);
        getNotNull().fadeIn(duration);
    }

    @Override
    public void fadeOut(float duration) {
        getPlaying().fadeOut(duration);
    }
}
