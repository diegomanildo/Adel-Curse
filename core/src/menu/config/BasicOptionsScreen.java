package menu.config;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import menu.BasicMainMenuScreen;
import utilities.Label;
import utilities.Render;
import utilities.Screen;
import utilities.TextButton;

public abstract class BasicOptionsScreen extends BasicMainMenuScreen {
    public BasicOptionsScreen() {
        super();
        Table table = new Table();
        table.setFillParent(true);
        TextButton backButton = new TextButton("X", () -> Render.setScreen(getBackScreen()));
        Label titleLabel = new Label(getTitleScreen());

        table.add(titleLabel)
                .top()
                .padTop(10f)
                .expand();

        table.add(backButton)
                .top()
                .right()
                .minWidth(30f)
                .minHeight(30f)
                .padTop(10f)
                .padRight(10f);

        stage.addActor(table);
    }

    protected abstract Screen getBackScreen();
    protected abstract String getTitleScreen();
}
