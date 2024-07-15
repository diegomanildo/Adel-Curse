package screens;

import characters.Adel;
import com.badlogic.gdx.Input;
import utilities.FilePaths;
import utilities.Image;
import utilities.Render;

public final class GameScreen extends Screen {
    private Adel adel;
    private Image background;

    public GameScreen() {
        super();
        adel = new Adel(0f, 0f);
        adel.setPosition(Render.getMiddleX(), Render.getMiddleY());
        background = new Image(FilePaths.BACKGROUNDS + "backgroundsAux.png");
        background.setSize(true);

        camera.position.set(adel.getX(), adel.getY(), 0);
        camera.update();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Render.clear();
        // Come back to menu
        if (Render.io.isKeyPressed(Input.Keys.ESCAPE)) {
            Render.app.setScreen(new MainMenuScreen());
        }
        camera.position.set(adel.getX(), adel.getY(), 0);
        camera.update();

        Render.b.setProjectionMatrix(camera.combined);

        Render.b.begin();
        background.draw();
        adel.move();
        adel.draw();
        Render.b.end();
    }

    @Override
    public void resize(int w, int h) {
        super.resize(w, h);
    }
}
