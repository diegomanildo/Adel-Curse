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

    private OrthographicCamera camera;
    private Image background;

    public GameScreen() {
        adel = new Adel(0f, 0f);
        float middleX = Render.getMiddleX() - adel.getWidth() / 2f;
        float middleY = Render.getMiddleY() - adel.getHeight() / 2f;
        adel = new Adel(middleX, middleY);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background = new Image(FilePaths.BACKGROUNDS + "backgroundsAux.png");
    }
//a
    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        if (Render.io.isKeyPressed(Input.Keys.ESCAPE)) {
            Render.app.setScreen(new MainMenuScreen());
        }
        camera.position.set(adel.getX(), adel.getY(), 0);
        camera.update();

        Render.b.setProjectionMatrix(camera.combined);

        Render.clear();
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
