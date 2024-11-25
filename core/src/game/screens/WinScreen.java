package game.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import game.Game;
import utilities.Render;
import utilities.TextButton;

public class WinScreen extends ImageSubScreen {
    public WinScreen() {
        super("backgrounds/winScreen.png");
        setShow(false);

        Table table = new Table();
        table.setFillParent(true);

        Array<TextButton> buttons = new Array<>();

        buttons.addAll(
                new TextButton("Reintentar", Render::setScreenToGame), //playButton
                new TextButton("Volver al menu", Game::exit)//backButton
        );

        buttons.forEach(b -> {
            table.add(b).center().width(200f).height(45f).padBottom(10f);
            table.row();
        });

        stage.addActor(table);
    }
}
