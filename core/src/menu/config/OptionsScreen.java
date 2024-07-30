package menu.config;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import menu.MainMenuScreen;
import utilities.Render;
import utilities.Screen;

public final class OptionsScreen extends BasicOptionsScreen {
    public OptionsScreen() {
        super();

        Table table = new Table();
        table.setFillParent(true);
        Array<TextButton> buttons = new Array<>();
        TextButton controlsButton = new TextButton("CONTROLS", Render.skin);
        TextButton resolutionButton = new TextButton("RESOLUTION", Render.skin);

        controlsButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                Render.setScreen(new ControlsScreen());
            }
        });

        resolutionButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                Render.setScreen(new ResolutionScreen());
            }
        });

        buttons.addAll(
                controlsButton,
                resolutionButton
        );

        buttons.forEach(b -> {
            table.add(b).center().minWidth(100f).minHeight(25f).padBottom(10f);
            table.row();
        });

        stage.addActor(table);
    }

    @Override
    protected Screen getBackScreen() {
        return new MainMenuScreen();
    }

    @Override
    protected String getTitleScreen() {
        return "OPTIONS";
    }
}
