package menu.config;

import com.badlogic.gdx.Input;
import menu.BasicMainMenuScreen;
import utilities.Screen;
import utilities.Button;
import utilities.Fonts;
import utilities.Render;
import utilities.Text;

public abstract class BasicOptionsScreen extends BasicMainMenuScreen {
    private final Button backButton;
    private final Text title;

    public BasicOptionsScreen() {
        super();
        backButton = new Button(Fonts.DEFAULT, "X", () -> Render.setScreen(getBackScreen()));
        title = new Text(Fonts.DEFAULT, getTitleScreen());
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (backButton.isClicked() || Render.io.isKeyPressed(Input.Keys.ESCAPE)){
            backButton.execute();
        }
    }

    @Override
    public void resize(int w, int h) {
        super.resize(w, h);
        backButton.setX(Render.screenSize.width - backButton.getWidth() - 20f);
        backButton.setY(Render.screenSize.height - 20f);

        title.centerX();
        title.setY(Render.screenSize.height - 40f);
    }

    protected abstract Screen getBackScreen();
    protected abstract String getTitleScreen();
}
