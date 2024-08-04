package utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {
    private static final Preferences PREFS = Gdx.app.getPreferences("settings");

    public static class SettingPack {
        public Size resolution;
        public int fps;
        public boolean fullscreen;

        public SettingPack(Size resolution, int fps, boolean fullscreen) {
            this.resolution = resolution;
            this.fps = fps;
            this.fullscreen = fullscreen;
        }

        public SettingPack() {
            this(new Size(), 0, false);
        }
    }

    public static void add(SettingPack settings) {
        PREFS.putString("resolution", settings.resolution.toString());
        PREFS.putInteger("fps", settings.fps);
        PREFS.putBoolean("fullscreen", settings.fullscreen);
        PREFS.flush();
    }

    public static SettingPack getSettings() {
        SettingPack settings = new SettingPack();
        String resolution = PREFS.getString("resolution", "1280x720");
        settings.resolution = Size.getResolution(resolution);
        settings.fps = PREFS.getInteger("fps", 60);
        settings.fullscreen = PREFS.getBoolean("fullscreen", false);

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
    }
}
