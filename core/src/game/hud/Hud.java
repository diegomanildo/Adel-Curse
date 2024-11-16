package game.hud;

import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import game.Game;
import game.entities.characters.playables.Playable;
import utilities.Label;
import utilities.SubScreen;

public class Hud extends SubScreen {
    private final Playable player;
    private final Label hpLabel;
    private final Label timerLabel;
    private final ItemListShower itemListShower;

    public Hud() {
        super();
        this.player = Game.game.getPlayers().get(0);
        this.hpLabel = new Label();
        this.timerLabel = new Label();
        this.itemListShower = new ItemListShower(player.getItems());
        HpBar hpBar = new HpBar(player);
        MiniMap miniMap = new MiniMap(Game.game.getLevel().getMap(), 25f);

        Table table = new Table();
        table.setFillParent(true);
        table.top().left();

        Stack stack = new Stack();
        stack.add(hpBar);
        stack.add(hpLabel);

        table.add(stack).left().top().width(hpBar.getWidth()).height(hpBar.getHeight()).pad(10f);
        table.add(timerLabel).center().top().pad(10f).expandX();
        table.add(miniMap).right().top().pad(10f);
        table.row();
        table.add(itemListShower).colspan(3).center().top().pad(20f).padRight(30f).expandX();

        stage.addActor(table);
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
