package gameUtilities;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class Camera2D extends OrthographicCamera {
    public Camera2D() {
        super();
    }

    public Camera2D(float viewportWidth, float viewportHeight) {
        super(viewportWidth, viewportHeight);
    }
}
