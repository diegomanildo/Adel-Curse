package utilities;


import com.badlogic.gdx.ScreenAdapter;

import java.util.ArrayList;

public abstract class Screen extends SubScreen {
    private final ArrayList<SubScreen> subScreens;

    public Screen() {
        super();
        subScreens = new ArrayList<>();
    }

    @Override
    public void show() {
        super.show();
        subScreens.forEach(ScreenAdapter::show);
    }

    @Override
    public void render(float delta) {
        Render.clear();
        super.render(delta);
        subScreens.forEach(s -> s.render(delta));
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