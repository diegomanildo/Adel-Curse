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
    }

    @Override
    public void render(float delta) {
        super.render(delta);
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
}
