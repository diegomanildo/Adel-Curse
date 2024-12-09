package game.hud;

import com.badlogic.gdx.graphics.Color;
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
    private final Label armorLabel;
    private final Label damageLabel;

    public HudScreen() {
        super();
        player = Game.game.getPlayer();

        hpLabel = new Label();
        timerLabel = new Label();

        armorLabel = new Label();
        armorLabel.setColor(new Color(0xadd8e6));
        damageLabel = new Label();
        damageLabel.setColor(Color.YELLOW);

        ItemListShower itemListShower = new ItemListShower(player.getItems());
        HpBar hpBar = new HpBar(player);
        MiniMap miniMap = new MiniMap(Game.game.getLevel().getMap(), 30f);

        Table table = new Table();
        table.pad(10f);
        table.setFillParent(true);
        table.top().left();

        Stack stack = new Stack();
        stack.add(hpBar);
        stack.add(hpLabel);

        table.add(stack).left().top().width(hpBar.getWidth()).height(hpBar.getHeight());
        table.add(timerLabel).center().top().expandX();
        table.add(miniMap).right().top();
        table.row();
        table.add(damageLabel).center().left();
        table.row();
        table.add(armorLabel).center().left();
        table.row();
        table.row();
        table.add(itemListShower).colspan(3).right().top().pad(30f).expandX();

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        hpLabel.setText(player.getHp() + "/" + player.getMaxHp() + " HP");
        damageLabel.setText("Damage: " + player.getDamage());
        armorLabel.setText("Armor: " + player.getArmor());

        long hours = (long) Game.game.getTimer().getHours();
        long minutes = (long) Game.game.getTimer().getMinutes();
        long seconds = (long) Game.game.getTimer().getSeconds();

        timerLabel.setText(String.format("Time: %02d:%02d:%02d", hours, minutes, seconds));
    }
}
