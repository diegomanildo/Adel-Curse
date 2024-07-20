package utilities.io;

import com.badlogic.gdx.audio.Music;

public class Song implements Music {
    private final Audio intro;
    private final Audio song;
    private final Audio outro;

    public Song(String introFilePath, String songFilePath, String outroFilePath) {
        song = new Audio(songFilePath);

        if (introFilePath == null) {
            intro = null;
        } else {
            intro = new Audio(introFilePath);
            intro.setOnCompletionListener(music -> {
                intro.stop();
                song.play();
            });
        }

        if (outroFilePath == null) {
            outro = null;
        } else {
            outro = new Audio(outroFilePath);
        }
    }

    public Song(String songFilePath) {
        this(null, songFilePath, null);
    }

    @Override
    public void play() {
        play(false);
    }

    public void play(boolean loop) {
        if (intro != null) {
            intro.play(loop);
        } else {
            song.play(loop);
        }
    }

    @Override
    public void pause() {
        if (intro.isPlaying()) {
            intro.pause();
        } else if (song.isPlaying()) {
            song.pause();
        } else {
            outro.pause();
        }
    }

    @Override
    public void stop() {
        if (intro != null && intro.isPlaying()) {
            intro.stop();
        } else if (outro != null && outro.isPlaying()) {
            outro.stop();
        } else {
            song.stop();
        }
    }

    @Override
    public boolean isPlaying() {
        if (intro == null && outro == null) {
            return song.isPlaying();
        } else {
            return intro.isPlaying() || song.isPlaying() || outro.isPlaying();
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
        if (intro != null || outro != null) {
            intro.setVolume(volume);
            outro.setVolume(volume);
        }

        song.setVolume(volume);
    }

    @Override
    public float getVolume() {
        if (intro.isPlaying()) {
            return intro.getVolume();
        } else if (outro.isPlaying()) {
            return outro.getVolume();
        } else {
            return song.getVolume();
        }
    }

    @Override
    public void setPan(float v, float v1) {
        intro.setPan(v, v1);
        song.setPan(v, v1);
        outro.setPan(v, v1);
    }

    @Override
    public void setPosition(float position) {
        intro.setPosition(position);
        song.setPosition(position);
        outro.setPosition(position);
    }

    @Override
    public float getPosition() {
        if (intro.isPlaying()) {
            return intro.getPosition();
        } else if (outro.isPlaying()) {
            return outro.getPosition();
        } else {
            return song.getPosition();
        }
    }

    @Override
    public void dispose() {
        if (intro != null) {
            intro.dispose();
        }

        song.dispose();

        if (outro != null) {
            outro.dispose();
        }
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        if (outro == null) {
            song.setOnCompletionListener(onCompletionListener);
        } else {
            outro.setOnCompletionListener(onCompletionListener);
        }
    }
}
