package game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import game.Game;
import game.entities.characters.playables.Playable;
import utilities.Label;

public class StatsShower extends Table {
    private final Playable player;
    private final Label damageLabel;
    private final Label armorLabel;
    private final Label coinsLabel;

    public StatsShower(Playable player) {
        super();
        this.player = player;

        damageLabel = new Label();
        damageLabel.setColor(Color.YELLOW);
        armorLabel = new Label();
        armorLabel.setColor(new Color(0xadd8e6));
        coinsLabel = new Label();
        coinsLabel.setColor(Color.ORANGE);

        add(damageLabel).center().left();
        row();
        add(armorLabel).center().left();
        row();
        add(coinsLabel).left();
        row();
        row();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        damageLabel.setText("Damage: " + player.getDamage());
        armorLabel.setText("Armor: " + player.getArmor());
        coinsLabel.setText("Coins: " + Game.game.getCoins());
    }
}
