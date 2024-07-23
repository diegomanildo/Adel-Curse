package utilities.io;

import com.badlogic.gdx.audio.Music;

import java.util.function.Consumer;

public class Song implements Music {
    private static class SongSaver {
        private final Audio intro;
        private final Audio song;

        public SongSaver(Audio intro, Audio song) {
            this.intro = intro;
            this.song = song;
        }

        public Audio getNotNull() {
            if (intro != null) {
                return intro;
            } else {
                return song;
            }
        }

        public Audio getPlaying() {
            if (intro != null && intro.isPlaying()) {
                return intro;
            } else {
                return song;
            }
        }

        public void forEach(Consumer<? super Audio> action) {
            if (intro != null) {
                action.accept(intro);
            }
            if (song != null) {
                action.accept(song);
            }
        }
    }

    private final SongSaver songSaver;

    public Song(String introFilePath, String songFilePath) {
        Audio song = new Audio(songFilePath);
        Audio intro;

        if (introFilePath != null) {
            intro = new Audio(introFilePath);
            intro.setOnCompletionListener(music -> {
                intro.stop();
                song.play();
            });
        } else {
            intro = null;
        }

        songSaver = new SongSaver(intro, song);
    }

    public Song(String songFilePath) {
        this(null, songFilePath);
    }

    @Override
    public void play() {
        play(false);
    }

    public void play(boolean loop) {
        songSaver.getNotNull().play();
        songSaver.song.setLooping(loop);
    }

    @Override
    public void pause() {
        songSaver.getPlaying().pause();
    }

    @Override
    public void stop() {
        songSaver.getPlaying().stop();
    }

    @Override
    public boolean isPlaying() {
        return songSaver.getPlaying().isPlaying();
    }

    @Override
    public void setLooping(boolean loop) {
        songSaver.song.setLooping(loop);
    }

    @Override
    public boolean isLooping() {
        return songSaver.song.isLooping();
    }

    @Override
    public void setVolume(float volume) {
        songSaver.forEach(s -> s.setVolume(volume));
    }

    @Override
    public float getVolume() {
        return songSaver.getPlaying().getVolume();
    }

    @Override
    public void setPan(float v, float v1) {
        songSaver.forEach(s -> s.setPan(v, v1));
    }

    @Override
    public void setPosition(float position) {
        songSaver.forEach(s -> s.setPosition(position));
    }

    @Override
    public float getPosition() {
        return songSaver.getPlaying().getPosition();
    }

    @Override
    public void dispose() {
        songSaver.forEach(Audio::dispose);
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        songSaver.song.setOnCompletionListener(onCompletionListener);
    }
}
