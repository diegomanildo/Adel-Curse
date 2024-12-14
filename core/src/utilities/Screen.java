package utilities;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

// CLASE PANTALLA, RENDERIZA EL STAGE Y MUESTRA LAS SUB-PANTALLAS
public abstract class Screen extends SubScreen {
    private final ArrayList<SubScreen> subScreens;

    public Screen() {
        super();
        subScreens = new ArrayList<>();
    }

    @Override
    public void show() {
        super.show();
        subScreens.forEach(s -> {
            if (s.isShowing()) {
                s.show();
            }
        });
    }

    @Override
    public void render(float delta) {
        Render.clear(new Color(0));
        super.render(delta);
        subScreens.forEach(s -> {
            if (s.isShowing()) {
                s.render(delta);
            }
        });
    }

    @Override
    public void resize(int w, int h) {
        super.resize(w, h);
        subScreens.forEach(s -> s.resize(w, h));
    }

    @Override
    public void dispose() {
        super.dispose();
        subScreens.forEach(ScreenAdapter::dispose);
    }

    protected void addSubScreen(SubScreen screen) {
        subScreens.add(screen);
    }
}