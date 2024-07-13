package screens;

import characters.Adel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import utilities.FilePaths;
import utilities.Image;
import utilities.Render;

public class GameScreen implements Screen {
    private Adel adel;
    private Image background;

    public GameScreen() {
        adel = new Adel(0f, 0f);
        adel.setPosition(Render.getMiddleX(), Render.getMiddleY());
        background = new Image(FilePaths.BACKGROUNDS + "backgroundsAux.png");
    }

    @Override
    public void show() {
        background.setSize(true);
    }

    @Override
    public void render(float delta) {
        Render.clear();
        // Come back to menu
        if (Render.io.isKeyPressed(Input.Keys.ESCAPE)) {
            Render.app.setScreen(new MainMenuScreen());
        }

        Render.b.begin();

        background.draw();
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
