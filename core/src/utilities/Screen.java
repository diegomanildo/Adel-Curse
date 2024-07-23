package utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import game.utilities.GameObject;
import managers.AudioManager;
import managers.ObjectsManager;
import com.badlogic.gdx.audio.Music;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class Screen extends ScreenAdapter {
    private static final int INIT_WIDTH = Gdx.graphics.getWidth();
    private static final int INIT_HEIGHT = Gdx.graphics.getHeight();

    protected static final float FADE_TIME = 1f;

    private static ObjectsManager objectsManager;
    private static AudioManager audioManager;

    protected Screen() {
        objectsManager = new ObjectsManager();
        audioManager = new AudioManager();
    }

    public void resize(int w, int h) {
        Render.screenSize = new Size(w, h);
    }

    public void render(float delta) {
        Render.clear(new Color(0x800000FF));
        objectsManager.draw();
    }

    public void initializeManagers() {
        Class<?> currentClass = this.getClass();

        while (currentClass != null) {
            // Get all variables from child classes
            Field[] variables = currentClass.getDeclaredFields();

            for (Field variable : variables) {
                variable.setAccessible(true);
                Class<?> type = variable.getType();
                if (!Modifier.isStatic(variable.getModifiers())) {
                    try {
                        // If the variable is child of ObjectFunctions register it to objectManager
                        if (GameObject.class.isAssignableFrom(type)) {
                            objectsManager.register((GameObject) variable.get(this));
                        } else if (Music.class.isAssignableFrom(type)) {
                            // If music is not static register it
                            audioManager.register((Music) variable.get(this));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            // Search in children of child
            currentClass = currentClass.getSuperclass();
        }

        audioManager.setVolume(0.1f);
    }

    public void dispose() {
        objectsManager.dispose();
        audioManager.dispose();
    }

    protected static GameObject getScreenHitbox() {
        GameObject screen = new GameObject() {
            @Override
            public void draw(Batch batch) {}
        };

        screen.setX(0f);
        screen.setY(0f);
        screen.setWidth(Render.screenSize.width);
        screen.setHeight(Render.screenSize.height);

        return screen;
    }
}