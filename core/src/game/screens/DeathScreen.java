package game.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import game.Game;
import game.entities.GameEntity;
import game.entities.characters.playables.Playable;
import utilities.TextButton;

public class DeathScreen extends ImageSubScreen {
    public DeathScreen() {
        super("backgrounds/deathScreen.png");
        setShow(false);

        Table table = new Table();
        table.setFillParent(true);

        Array<TextButton> buttons = new Array<>();

        buttons.addAll(
                new TextButton("Reintentar", Game::restart), //playButton
                new TextButton("Volver al menu", Game::exit)//backButton
        );

        buttons.forEach(b -> {
            table.add(b).center().width(200f).height(45f).padBottom(10f);
            table.row();
        });

        stage.addActor(table);
    }

    public void playerDead() {
        for (Playable player : Game.game.getPlayers()) {
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
            Game.game.getEntities().forEach(GameEntity::pause);
            Game.game.getTimer().pause();
        } else {
            Game.game.getEntities().forEach(GameEntity::resume);
            Game.game.getTimer().resume();
        }
    }
}
