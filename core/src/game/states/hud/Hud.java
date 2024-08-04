package game.states.hud;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import game.entities.characters.playables.Playable;
import utilities.Label;
import utilities.SubScreen;
import utilities.Timer;

public class Hud extends SubScreen {
    private final Playable player;
    private final Label hpLabel;
    private final Label timerLabel;
    private final Timer timer;

    public Hud(Playable player) {
        super(new OrthographicCamera());
        this.player = player;
        hpLabel = new Label();
        timerLabel = new Label();
        timer = new Timer();

        Table table = new Table();
        table.top().left();
        table.setFillParent(true);

        HpBar hpBar = new HpBar(player);

        Stack stack = new Stack();
        stack.add(hpBar);
        stack.add(hpLabel);

        table.add(stack).width(hpBar.width).height(hpBar.height).padTop(10f).padLeft(10f).padRight(5f);

        table.add(timerLabel).center();

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

        timerLabel.setText(String.format("Time: %02d:%02d:%02d", timer.getHours(), timer.getMinutes(), timer.getSeconds()));
    }

    public Timer getTimer() {
        return timer;
    }
}
