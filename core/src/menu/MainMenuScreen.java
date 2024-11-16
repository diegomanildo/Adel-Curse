package menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import menu.config.ControlsScreen;
import menu.config.SettingsScreen;
import utilities.Render;
import utilities.TextButton;

public final class MainMenuScreen extends BasicMainMenuScreen {
    public MainMenuScreen() {
        super();
        Table table = new Table();
        table.setFillParent(true);
        Array<TextButton> buttons = new Array<>();
        buttons.addAll(
                new TextButton("Jugar", () -> Render.setScreen(new WaitingMenuScreen())),
                new TextButton("Controles", () -> Render.setScreen(new ControlsScreen(new MainMenuScreen()))),
                new TextButton("Configuracion", () -> Render.setScreen(new SettingsScreen(new MainMenuScreen()))),
                new TextButton("Salir", () -> Gdx.app.exit())
        );

        buttons.forEach(b -> {
            table.add(b).center().width(200f).height(45f).padBottom(10f);
            table.row();
        });

        stage.addActor(table);
    }
}
