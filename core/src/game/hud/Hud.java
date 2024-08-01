package game.hud;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import game.entities.characters.playables.Playable;
import utilities.Label;
import utilities.SubScreen;

public class Hud extends SubScreen {
    private final Playable player;

    public Hud(Playable player) {
        super(new OrthographicCamera());
        this.player = player;

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        HpBar hpBar = new HpBar(player);
        table.add(new Label("Hp: ")).center().bottom().padLeft(10f).padTop(10f).padRight(5f);
        table.add(hpBar).expandX().width(hpBar.width).height(hpBar.height).left().bottom();

        table.left();
        

        stage.addActor(table);
    }
}
