package game;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import menu.config.SettingsScreen;
import utilities.Render;
import utilities.SubScreen;
import utilities.TextButton;

public class PauseScreen extends SubScreen {
    public PauseScreen(Runnable backFunction) {
        super();

        setShow(false);

        Table table = new Table();
        table.setFillParent(true);

        Array<TextButton> buttons = new Array<>();

        buttons.addAll(
                new TextButton(Render.currentLanguage.resumeBtn(), () -> setShow(false)),
                new TextButton(Render.currentLanguage.settingsBtn(), () -> {
                    backFunction.run();
                    Render.setScreen(new SettingsScreen(new GameScreen()));
                }),
                new TextButton(Render.currentLanguage.backToMenuBtn(), backFunction)
        );

        buttons.forEach(b -> {
            table.add(b).center().width(200f).height(45f).padBottom(10f);
            table.row();
        });

        stage.addActor(table);
    }

    @Override
    public void setShow(boolean show) {
        super.setShow(show);
        if (show) {
            GameScreen.game.pause();
        } else {
            GameScreen.game.resume();
        }
    }
}
