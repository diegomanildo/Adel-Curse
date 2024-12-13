package menu.config;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import menu.BasicMainMenuScreen;
import utilities.Label;
import utilities.Render;
import utilities.Screen;
import utilities.TextButton;

public abstract class BasicOptionsScreen extends BasicMainMenuScreen {
    private final Screen backScreen;

    public BasicOptionsScreen(Screen backScreen) {
        super();
        this.backScreen = backScreen;
        Table table = new Table();
        table.setFillParent(true);
        TextButton backButton = new TextButton("<", this::back);
        Label titleLabel = new Label(getTitleScreen());
        table.add(titleLabel).top().padTop(10f).expand();
        table.add(backButton).top().right().width(45f).height(45f).padTop(10f).padRight(10f);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (stage.isKeyPressed(Input.Keys.ESCAPE)) {
            back();
        }
    }

    protected void back() {
        Render.setScreen(backScreen);
    }

    protected abstract String getTitleScreen();
}
