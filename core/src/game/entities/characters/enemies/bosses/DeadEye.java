package game.entities.characters.enemies.bosses;

import game.Game;
import game.entities.characters.enemies.Skeleton;
import utilities.Utils;

public class DeadEye extends Boss {
    private static final int MAX_CHILDREN = 3;

    public DeadEye() {
        super(new Stats(200, 2), "deadEye/deadEye.png", "deadEye/bullet.png", 2, 8);
        setSize(32f, 32f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (getChildren().size() < MAX_CHILDREN && Utils.r.nextInt(0, 100) == 0) {
            createChild(new Skeleton());
        }
    }

    @Override
    protected void onDeath() {
        super.onDeath();
        Game.winScreen.setShow(true);
    }
}
