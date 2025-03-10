package game.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import game.Game;
import utilities.SubScreen;
import utilities.TextButton;

public class PauseScreen extends SubScreen {
    public PauseScreen() {
        super();

        setShow(false);

        Table table = new Table();
        table.setFillParent(true);

        Array<TextButton> buttons = new Array<>();

        buttons.addAll(
                new TextButton("Reanudar", () -> setShow(false)),
                new TextButton("Volver al menu", Game::exit)
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
            Game.game.pause();
        } else {
            Game.game.resume();
        }
    }
}
