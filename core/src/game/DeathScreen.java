package game;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import utilities.Render;
import utilities.SubScreen;
import utilities.TextButton;
import game.GameScreen;

public class DeathScreen extends SubScreen {
    public DeathScreen(Runnable backFunction){
        super();
        setShow(false);

        Table table = new Table();
        table.setFillParent(true);

        TextButton backButton = new TextButton(Render.currentLanguage.backToMenuBtn(), backFunction);

        table.add(backButton).center().width(200f).height(45f).padBottom(10f);
        table.row();

        stage.addActor(table);
    }

    public void playerDead(){
        if(GameScreen.game.getPlayer().isDeath()){
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

    @Override
    public void show(){
    }
}
