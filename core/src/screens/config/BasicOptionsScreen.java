package screens.config;

import screens.Screen;
import utilities.Button;
import utilities.Fonts;
import utilities.Render;
import utilities.Text;

public abstract class BasicOptionsScreen extends Screen {
    private Button backButton;
    private Text title;

    @Override
    public void show() {
        super.show();
        backButton = new Button(Fonts.DEFAULT, "BACK", () -> Render.setScreen(getBackScreen()));
        backButton.setX(10f);
        backButton.setY(backButton.getHeight() + 10f);

        title = new Text(Fonts.DEFAULT, getTitleScreen());
        title.centerX();
        title.setY(Render.screenSize.height - 10f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (backButton.isClicked()){
            backButton.execute();
        }
    }

    protected abstract Screen getBackScreen();
    protected abstract String getTitleScreen();
}
