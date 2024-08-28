package utilities.audio;

import java.util.function.Consumer;

public final class Song extends Audio {
    private final Sound intro;
    private final Sound song;
    private boolean isInIntro;

    public Song(String channel, String introFilePath, String songFilePath) {
        super(channel);
        song = new Sound(null, songFilePath);
        isInIntro = false;

        if (introFilePath == null) {
            intro = null;
        } else {
            isInIntro = true;
            intro = new Sound(null, introFilePath);
            intro.setOnCompletionListener(music -> {
                isInIntro = false;
                intro.stop();
                song.play();
            });
        }

        update(channel);
    }

    public Song(String channel, String songFilePath) {
        this(channel, null, songFilePath);
    }

    private void forEach(Consumer<Sound> action) {
        if (intro != null) {
            action.accept(intro);
        }
        if (song != null) {
            action.accept(song);
        }
    }

    public boolean introValid() {
        return intro != null && isInIntro;
    }

    @Override
    public void play(boolean loop) {
        setLooping(loop);
        play();
    }

    @Override
    public void play() {
        if (introValid()) {
            intro.play();
        } else {
            song.play();
        }
    }

    @Override
    public void pause() {
        if (introValid()) {
            intro.pause();
        } else {
            song.pause();
        }
    }

    @Override
    public void stop() {
        if (introValid()) {
            intro.stop();
        } else {
            song.stop();
        }
    }

    @Override
    public boolean isPlaying() {
        if (introValid()) {
            return intro.isPlaying() || song.isPlaying();
        } else {
            return song.isPlaying();
        }
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
        return song.getVolume();
    }

    @Override
    public void setPan(float pan, float volume) {
        forEach(s -> s.setPan(pan, volume));
    }

    @Override
    public void setPosition(float position) {
        forEach(s -> s.setPosition(position));
    }

    @Override
    public float getPosition() {
        return song.getPosition();
    }

    @Override
    public void dispose() {
        forEach(Sound::dispose);
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener listener) {
        song.setOnCompletionListener(listener);
    }
}
