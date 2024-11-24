package game.entities.characters.playables;

import game.Game;
import game.entities.characters.Character;
import game.entities.items.Item;
import game.utilities.Controls;
import game.utilities.Direction;
import game.utilities.GameAction;

import java.util.ArrayList;

public abstract class Playable extends Character {
    private final ArrayList<Item> items = new ArrayList<>();

    public Playable(Stats stats, String texturePath, String bulletTexturePath, int columns, int rows) {
        super(stats, texturePath, bulletTexturePath, columns, rows);
    }

    public Playable(Stats stats, String texturePath, String bulletTexturePath) {
        super(stats, texturePath, bulletTexturePath);
    }

    @Override
    public void update(float delta) {
        moveCharacter();
        shoot();
    }

    private void shoot() {
        Direction direction = Direction.NONE;

        boolean shootEnabled = true;

        if (Controls.isPressed(GameAction.SHOOT_UP)) {
            direction = Direction.UP;
        } else if (Controls.isPressed(GameAction.SHOOT_DOWN)) {
            direction = Direction.DOWN;
        } else if (Controls.isPressed(GameAction.SHOOT_RIGHT)) {
            direction = Direction.RIGHT;
        } else if (Controls.isPressed(GameAction.SHOOT_LEFT)) {
            direction = Direction.LEFT;
        } else {
            shootEnabled = false;
        }

        if (shootEnabled) {
            shoot(direction);
        }
    }

    private void moveCharacter() {
        direction = Direction.NONE;

        if (Controls.isPressed(GameAction.UP)) {
            if (Controls.isPressed(GameAction.LEFT)) {
                direction = Direction.UP_LEFT;
            } else if (Controls.isPressed(GameAction.RIGHT)) {
                direction = Direction.UP_RIGHT;
            } else {
                direction = Direction.UP;
            }
        } else if (Controls.isPressed(GameAction.DOWN)) {
            if (Controls.isPressed(GameAction.LEFT)) {
                direction = Direction.DOWN_LEFT;
            } else if (Controls.isPressed(GameAction.RIGHT)) {
                direction = Direction.DOWN_RIGHT;
            } else {
                direction = Direction.DOWN;
            }
        } else if (Controls.isPressed(GameAction.RIGHT)) {
            direction = Direction.RIGHT;
        } else if (Controls.isPressed(GameAction.LEFT)) {
            direction = Direction.LEFT;
        }

        move(direction);
    }

    @Override
    protected void onDeath() {
        Game.deathScreen.playerDead();
        super.onDeath();
    }

    public void addItem(Item i) {
        if (i == null) {
            return;
        }

        if (i.getOwner() == null) {
            i.changeOwnerTo(this);
            i.addToOwner();
        }
        items.add(i);
    }

    public void removeItem(Item i) {
        items.remove(i);
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
