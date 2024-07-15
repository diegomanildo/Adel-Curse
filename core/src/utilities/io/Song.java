package utilities.io;

import utilities.exceptions.NullAudioException;

public class Song {
    private final Audio intro;
    private final Audio song;

    public Song(String introPath, String songPath, float volumeIntro, float volumeSong) {
        intro = introPath == null ? null : new Audio(introPath, volumeIntro);
        song = new Audio(songPath, volumeSong);
    }

    public Song(String introPath, String songPath) {
        this(introPath, songPath, 1f, 1f);
    }

    public Song(String songPath, float volumeSong) {
        this(null, songPath, 1f, volumeSong);
    }

    public Song(String songPath) {
        this(songPath, 1f);
    }

    public boolean hasIntro() {
        return intro != null;
    }

    public void playIntro() {
        if (hasIntro()) {
            intro.play();
        } else {
            throw new NullAudioException("This song has not intro");
        }
    }

    public void playIntro(boolean loop) {
        setIntroLooping(loop);
        playIntro();
    }

    public void playSong() {
        song.play();
    }

    public void playSong(boolean loop) {
        setSongLooping(loop);
        playSong();
    }

    public void pauseIntro() {
        intro.pause();
    }

    public void pauseSong() {
        song.pause();
    }

    public void stopIntro() {
        intro.stop();
    }

    public void stopSong() {
        song.stop();
    }

    public boolean isPlayingIntro() {
        return intro.isPlaying();
    }

    public boolean isPlayingSong() {
        return song.isPlaying();
    }

    public void setIntroLooping(boolean b) {
        intro.setLooping(b);
    }

    public void setSongLooping(boolean b) {
        song.setLooping(b);
    }

    public boolean isIntroLooping() {
        return intro.isLooping();
    }

    public boolean isSongLooping() {
        return song.isLooping();
    }

    public void setIntroVolume(float v) {
        intro.setVolume(v);
    }

    public void setSongVolume(float v) {
        song.setVolume(v);
    }

    public float getIntroVolume() {
        return intro.getVolume();
    }

    public float getSongVolume() {
        return song.getVolume();
    }

    public void setIntroPan(float v, float v1) {
        intro.setPan(v, v1);
    }

    public void setSongPan(float v, float v1) {
        song.setPan(v, v1);
    }

    public void setIntroPosition(float v) {
        intro.setPosition(v);
    }

    public void setSongPosition(float v) {
        song.setPosition(v);
    }

    public float getIntroPosition() {
        return intro.getPosition();
    }

    public float getSongPosition() {
        return song.getPosition();
    }

    public void dispose() {
        if (intro != null) {
            intro.dispose();
        }
        song.dispose();
    }
}
