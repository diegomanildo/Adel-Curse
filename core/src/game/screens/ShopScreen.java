package game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import game.Game;
import game.hud.Store;
import utilities.Render;
import utilities.SubScreen;

public class ShopScreen extends SubScreen {
    private static final float PADDING = 100f;
    private final Table table;

    public ShopScreen() {
        super();
        setShow(false);
        Store store = new Store(() -> setShow(false));

        table = new Table();
        table.setFillParent(true);
        table.add(store).prefSize(Render.screenSize.width - PADDING, Render.screenSize.height - PADDING);
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

    @Override
    protected void handleInput() {
        super.handleInput();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            setShow(false);
        }
    }
}
