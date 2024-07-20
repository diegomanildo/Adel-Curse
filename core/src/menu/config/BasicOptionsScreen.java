package menu.config;

import menu.BasicMainMenuScreen;
import utilities.Screen;
import utilities.Button;
import utilities.Fonts;
import utilities.Render;
import utilities.Text;

public abstract class BasicOptionsScreen extends BasicMainMenuScreen {
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
        title.setY(Render.screenSize.height - 40f);
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
