package utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import utilities.io.Channels;

public class Settings {
    private static final Preferences PREFS = Gdx.app.getPreferences("settings");

    public static class SettingPack {
        public Size resolution;
        public int fps;
        public boolean fullscreen;
        public float globalVolume;
        public float musicVolume;
        public float sfxVolume;

        public SettingPack(Size resolution, int fps, boolean fullscreen, float globalVolume, float musicVolume, float sfxVolume) {
            this.resolution = resolution;
            this.fps = fps;
            this.fullscreen = fullscreen;
            this.globalVolume = globalVolume;
            this.musicVolume = musicVolume;
            this.sfxVolume = sfxVolume;
        }

        public SettingPack() {
            this(new Size(), 0, false, 1.0f, 1.0f, 1.0f);
        }
    }

    public static void add(SettingPack settings) {
        PREFS.putString("resolution", settings.resolution.toString());
        PREFS.putInteger("fps", settings.fps);
        PREFS.putBoolean("fullscreen", settings.fullscreen);
        PREFS.putFloat("globalVolume", settings.globalVolume);
        PREFS.putFloat("musicVolume", settings.musicVolume);
        PREFS.putFloat("sfxVolume", settings.sfxVolume);
        PREFS.flush();
    }

    public static SettingPack getSettings() {
        SettingPack settings = new SettingPack();
        String resolution = PREFS.getString("resolution", "1280x720");
        settings.resolution = Size.getResolution(resolution);
        settings.fps = PREFS.getInteger("fps", 60);
        settings.fullscreen = PREFS.getBoolean("fullscreen", false);
        settings.globalVolume = PREFS.getFloat("globalVolume", 1.0f);
        settings.musicVolume = PREFS.getFloat("musicVolume", 1.0f);
        settings.sfxVolume = PREFS.getFloat("sfxVolume", 1.0f);

        return settings;
    }

    public static void applySettings(SettingPack settings) {
        if (settings.fullscreen) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        } else {
            Gdx.graphics.setWindowedMode((int) settings.resolution.width, (int) settings.resolution.height);
        }

        Gdx.graphics.setForegroundFPS(settings.fps);
        Render.fps = settings.fps;

        Channels.setChannelVolume(Channels.GLOBAL_CHANNEL, settings.globalVolume);
        Channels.setChannelVolume("Music", settings.musicVolume);
        Channels.setChannelVolume("Sfx", settings.sfxVolume);
    }
}
