package screens;

import characters.Adel;
import com.badlogic.gdx.Input;
import utilities.Render;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
    private Adel adel;

    public GameScreen() {
        adel = new Adel(0f, 0f);
        float middleX = (float) Gdx.graphics.getWidth() / 2 - adel.getWidth() / 2;
        float middleY = (float) Gdx.graphics.getHeight() / 2 - adel.getHeight() / 2;
        adel = new Adel(middleX, middleY);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        if (Render.io.isKeyPressed(Input.Keys.ESCAPE)) {
            Render.app.setScreen(new MainMenuScreen());
        }

        Render.clear();
        Render.b.begin();

        adel.move();
        adel.draw();

        Render.b.end();
    }

    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {}
}
