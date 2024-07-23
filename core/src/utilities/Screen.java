package utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.*;
import gameUtilities.GameObject;
import managers.AudioManager;
import managers.ObjectsManager;
import com.badlogic.gdx.audio.Music;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class Screen extends ScreenAdapter {
    private static final int INIT_WIDTH = Gdx.graphics.getWidth();
    private static final int INIT_HEIGHT = Gdx.graphics.getHeight();

    public static OrthographicCamera camera;
    private static ObjectsManager objectsManager;
    private static AudioManager audioManager;

    protected final Viewport viewport;

    protected Screen() {
        objectsManager = new ObjectsManager();
        audioManager = new AudioManager();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(INIT_WIDTH, INIT_HEIGHT, camera);
    }

    public void resize(int w, int h) {
        Render.screenSize = new Size(w, h);
        viewport.update(w, h);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f);
        camera.update();
    }

    public void render(float delta) {
        viewport.apply();
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
                try {
                    // If the variable is child of ObjectFunctions register it to objectManager
                    if (GameObject.class.isAssignableFrom(type)) {
                        objectsManager.register((GameObject) variable.get(this));
                    } else if (Music.class.isAssignableFrom(type)) {
                        // If music is not static register it
                        if (!Modifier.isStatic(variable.getModifiers())) {
                            audioManager.register((Music) variable.get(this));
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
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
}