package managers;

import com.badlogic.gdx.audio.Music;

public class AudioManager extends Manager<Music> {
    public void setVolume(float volume) {
        objects.forEach(o -> o.setVolume(volume));
    }
}
