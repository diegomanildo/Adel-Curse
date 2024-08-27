package game.hud;

import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import game.Game;
import game.entities.characters.playables.Playable;
import utilities.Label;
import utilities.SubScreen;

public class Hud extends SubScreen {
    private final Game game;
    private final Playable player;
    private final Label hpLabel;
    private final Label timerLabel;
    private final ItemListShower itemListShower;

    public Hud(Game game) {
        super();
        this.game = game;
        this.player = game.getPlayer();
        this.hpLabel = new Label();
        this.timerLabel = new Label();
        HpBar hpBar = new HpBar(player);
        MiniMap miniMap = new MiniMap(game.getLevel().getMap(), 25f);
        itemListShower = new ItemListShower(player.getItems());

        Table table = new Table();
        table.setFillParent(true);
        table.top().left();

        Stack stack = new Stack();
        stack.add(hpBar);
        stack.add(hpLabel);

        table.add(stack).left().top().width(hpBar.getWidth()).height(hpBar.getHeight()).pad(10f).expandX();
        table.add(timerLabel).left().top().pad(10f).expandX();
        table.add(miniMap).right().top().pad(10f).expandX();
        table.row();
        table.add(itemListShower).colspan(3).right().top().pad(20f).padRight(30f).expandX();

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        hpLabel.setText(player.getHp() + "/" + player.getMaxHp() + " HP");

        long hours = (long) game.getTimer().getHours();
        long minutes = (long) game.getTimer().getMinutes();
        long seconds = (long) game.getTimer().getSeconds();

        timerLabel.setText(String.format("Time: %02d:%02d:%02d", hours, minutes, seconds));
    }
}
