package utilities.io;

import utilities.exceptions.NullAudioException;

public class Song {
    private Audio intro;
    private Audio music;

    public Song(String introPath, String musicPath) {
        intro = introPath == null ? null : new Audio(introPath);
        music = new Audio(musicPath);
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

    public void playMusic() {
        music.play();
    }

    public void playMusic(boolean loop) {
        setMusicLooping(loop);
        playMusic();
    }

    public void pauseIntro() {
        intro.pause();
    }

    public void pauseMusic() {
        music.pause();
    }

    public void stopIntro() {
        intro.stop();
    }

    public void stopMusic() {
        music.stop();
    }

    public boolean isPlayingIntro() {
        return intro.isPlaying();
    }

    public boolean isPlayingMusic() {
        return music.isPlaying();
    }

    public void setIntroLooping(boolean b) {
        intro.setLooping(b);
    }

    public void setMusicLooping(boolean b) {
        music.setLooping(b);
    }

    public boolean isIntroLooping() {
        return intro.isLooping();
    }

    public boolean isMusicLooping() {
        return music.isLooping();
    }

    public void setIntroVolume(float v) {
        intro.setVolume(v);
    }

    public void setMusicVolume(float v) {
        music.setVolume(v);
    }

    public float getIntroVolume() {
        return intro.getVolume();
    }

    public float getMusicVolume() {
        return music.getVolume();
    }

    public void setIntroPan(float v, float v1) {
        intro.setPan(v, v1);
    }

    public void setMusicPan(float v, float v1) {
        music.setPan(v, v1);
    }

    public void setIntroPosition(float v) {
        intro.setPosition(v);
    }

    public void setMusicPosition(float v) {
        music.setPosition(v);
    }

    public float getIntroPosition() {
        return intro.getPosition();
    }

    public float getMusicPosition() {
        return music.getPosition();
    }

    public void dispose() {
        intro.dispose();
        music.dispose();
    }
}
