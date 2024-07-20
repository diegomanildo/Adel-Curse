package menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.*;
import gameUtilities.ObjectFunctions;
import managers.ObjectsManager;
import utilities.Render;
import utilities.Size;

import java.lang.reflect.Field;

public abstract class Screen implements com.badlogic.gdx.Screen {
    protected Viewport viewport;
    public static OrthographicCamera camera;
    private ObjectsManager objectsManager;

    private static final int INIT_WIDTH = Gdx.graphics.getWidth();
    private static final int INIT_HEIGHT = Gdx.graphics.getHeight();

    public void show() {
        objectsManager = new ObjectsManager();
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
        if (objectsManager.isEmpty()) {
            initializeObjectManager();
        }
        Render.clear();
        viewport.apply();
        Render.b.begin();
        objectsManager.draw();
        Render.b.end();
    }

    private void initializeObjectManager() {
        Class<?> currentClass = this.getClass();

        while (currentClass != null) {
            // Get all variables from child classes
            Field[] variables = currentClass.getDeclaredFields();

            for (Field variable : variables) {
                variable.setAccessible(true);
                Class<?> type = variable.getType();
                // If the variable is child of ObjectFunctions register it to objectManager
                if (ObjectFunctions.class.isAssignableFrom(type)) {
                    try {
                        objectsManager.register((ObjectFunctions) variable.get(this));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            // Search in children of child
            currentClass = currentClass.getSuperclass();
        }
    }

    public void pause() {}
    public void resume() {}
    public void hide() {}

    public void dispose() {
        objectsManager.dispose();
    }
}
