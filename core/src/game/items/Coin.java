package game.items;

import game.Game;
import game.entities.GameEntity;
import game.utilities.Controls;
import game.utilities.GameAction;
import utilities.FilePaths;

public class Coin extends GameEntity {
    public Coin() {
        super(FilePaths.ICONS + "coin.png", 1, 1, 0.4f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (Controls.isJustPressed(GameAction.INTERACT) && collidesWith(Game.game.getPlayer().getBounds())) {
            remove();
            Game.game.addCoins(1);
        }

        if (getStage() == null || !collidesWith(Game.game.getPlayer().getBounds())) {
            Game.chat.removeChat(getCoinKey());
        } else {
            Game.chat.createTiny(getCoinKey(), "Press " + Controls.getCharacter(GameAction.INTERACT));
        }
    }

    private String getCoinKey() {
        return "Coin" + getId();
    }
}
