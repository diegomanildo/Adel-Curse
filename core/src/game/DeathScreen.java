package game;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import menu.config.SettingsScreen;
import utilities.Image;
import utilities.Render;
import utilities.SubScreen;
import utilities.TextButton;
import game.GameScreen;

public class DeathScreen extends SubScreen {
    public DeathScreen(Runnable backFunction) {
        super();
        setShow(false);

        Table table = new Table();
        table.setFillParent(true);

        Array<TextButton> buttons = new Array<>();

        buttons.addAll(
                new TextButton(Render.currentLanguage.playBtn()), //playButton
                new TextButton(Render.currentLanguage.backToMenuBtn(), backFunction)//backButton
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
        if(GameScreen.game.getPlayer().isDeath()) {
            setShow(true);
        }
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
