package screens;

import characters.Adel;
import com.badlogic.gdx.Input;
import utilities.FilePaths;
import utilities.Image;
import utilities.Render;

public final class GameScreen extends Screen {
    private Adel adel;
    private Image background;

    @Override
    public void show() {
        super.show();

        adel = new Adel();
        adel.center();

        background = new Image(FilePaths.BACKGROUNDS + "backgroundsAux.png");
        background.setSize(true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Render.clear();
        // Come back to menu
        if (Render.io.isKeyPressed(Input.Keys.ESCAPE)) {
            Render.setScreen(new MainMenuScreen());
        }

        Render.b.begin();
        background.draw();
        adel.move();
        adel.draw();
        Render.b.end();
    }
}
