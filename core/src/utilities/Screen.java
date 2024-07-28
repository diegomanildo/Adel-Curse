package utilities;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import game.utilities.Drawable;
import managers.ObjectsManager;
import utilities.io.Channels;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class Screen extends ScreenAdapter {
    protected static final float FADE_TIME = 1f;

    private static ObjectsManager objectsManager;

    protected Screen() {
        objectsManager = new ObjectsManager();
        Channels.setChannelVolume("Sfx", 0.5f);
        Channels.setChannelVolume("Music", 0f);
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
                        if (Drawable.class.isAssignableFrom(type)) {
                            objectsManager.register((Drawable) variable.get(this));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            // Search in children of child
            currentClass = currentClass.getSuperclass();
        }
    }

    public void dispose() {
        objectsManager.dispose();
    }
}