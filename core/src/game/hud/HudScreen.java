package game.hud;

import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import game.Game;
import game.entities.characters.playables.Playable;
import utilities.Label;
import utilities.SubScreen;

public class HudScreen extends SubScreen {
    private final Playable player;
    private final Label hpLabel;
    private final Label timerLabel;

    public HudScreen() {
        super();
        player = Game.game.getPlayer();

        hpLabel = new Label();
        timerLabel = new Label();

        StatsShower statsShower = new StatsShower(player);
        ItemListShower itemListShower = new ItemListShower(player.getItems());
        HpBar hpBar = new HpBar(player);
        MiniMap miniMap = new MiniMap(Game.game.getLevel().getMap(), 30f);

        Table table1 = new Table();
        table1.pad(10f);
        table1.setFillParent(true);
        table1.top().left();

        Table table2 = new Table();
        table2.pad(10f);
        table2.setFillParent(true);
        table1.top().left();

        Stack hpStack = new Stack();
        hpStack.add(hpBar);
        hpStack.add(hpLabel);

        table1.add(hpStack).left().top().width(hpBar.getWidth()).height(hpBar.getHeight());
        table1.add(miniMap).right().top().expandX();
        table1.row();
        table1.add(statsShower).center().left();
        table1.add(itemListShower).colspan(3).right().top().pad(30f).expandX();

        table2.add(timerLabel).center().top().expandX().expandY();

        stage.addActor(table1);
        stage.addActor(table2);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        hpLabel.setText(player.getHp() + "/" + player.getMaxHp() + " HP");

        long hours = (long) Game.game.getTimer().getHours();
        long minutes = (long) Game.game.getTimer().getMinutes();
        long seconds = (long) Game.game.getTimer().getSeconds();

        timerLabel.setText(String.format("Time: %02d:%02d:%02d", hours, minutes, seconds));
    }
}
