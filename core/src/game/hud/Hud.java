package game.hud;

import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import game.entities.characters.playables.Playable;
import game.utilities.map.GameMap;
import utilities.Label;
import utilities.SubScreen;
import utilities.Timer;

public class Hud extends SubScreen {
    private final Playable player;
    private final Label hpLabel;
    private final Label timerLabel;
    private final Timer timer;

    public Hud(Playable player, GameMap map) {
        super();
        this.player = player;
        hpLabel = new Label();
        timerLabel = new Label();
        timer = new Timer();
        HpBar hpBar = new HpBar(player);
        MiniMap miniMap = new MiniMap(map, 25f);

        Table table = new Table();
        table.setFillParent(true);
        table.top().left();

        Stack stack = new Stack();
        stack.add(hpBar);
        stack.add(hpLabel);

        table.add(stack).left().top().width(hpBar.getWidth()).height(hpBar.getHeight()).pad(10f).expandX();
        table.add(timerLabel).left().top().pad(10f).expandX();
        table.add(miniMap).right().top().pad(10f).expandX();

        stage.addActor(table);
    }

    @Override
    public void show() {
        super.show();
        timer.start();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        hpLabel.setText(player.getHp() + "/" + player.getMaxHp() + " HP");

        long hours = (long) timer.getHours();
        long minutes = (long) timer.getMinutes();
        long seconds = (long) timer.getSeconds();

        timerLabel.setText(String.format("Time: %02d:%02d:%02d", hours, minutes, seconds));
    }

    public Timer getTimer() {
        return timer;
    }
}
