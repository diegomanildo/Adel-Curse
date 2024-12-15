package game.items;

import com.badlogic.gdx.graphics.Color;
import game.Game;
import game.entities.characters.playables.Playable;

public class Crucifix extends Item {
    public Crucifix() {
        super(ItemQuality.Legendary, "Revive a random death player", "crucifix.png", 3, 1);
    }

    @Override
    protected void applyEffect() {
        if (atLeastOneDeath()) {
            reviveRandomPlayer();
        }
    }

    private boolean atLeastOneDeath() {
        for (Playable player : Game.game.allPlayers) {
            if (player.isDeath()) {
                return true;
            }
        }

        return false;
    }

    private void reviveRandomPlayer() {
        Playable player;

        do {
            player = Game.game.allPlayers.random();
        } while (!player.isDeath());

        Game.game.revivePlayer(player.getId());
    }

    @Override
    public Color getItemColor() {
        return Color.WHITE;
    }
}
