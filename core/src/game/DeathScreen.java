package game;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import game.entities.characters.playables.Playable;
import utilities.Image;
import utilities.Render;
import utilities.SubScreen;
import utilities.TextButton;

public class DeathScreen extends SubScreen {
    public DeathScreen(Runnable backFunction) {
        super();
        setShow(false);

        Table table = new Table();
        table.setFillParent(true);

        Array<TextButton> buttons = new Array<>();

        buttons.addAll(
                new TextButton("Reintentar", () -> Render.setScreen(new GameScreen())), //playButton
                new TextButton("Volver al menu", backFunction)//backButton
        );

        buttons.forEach(b -> {
            table.add(b).center().width(200f).height(45f).padBottom(10f);
            table.row();
        });

        Image image = new Image("backgrounds/deathScreen.png");

        stage.addActor(image);
        stage.addActor(table);
    }

    public void playerDead() {
        for (Playable player : GameScreen.game.getPlayers()) {
            if (!player.isDeath()) {
                return;
            }
        }

        setShow(true);
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
