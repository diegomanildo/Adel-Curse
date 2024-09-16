package game.entities.characters.enemies.bosses;

public class DeadEye extends Boss {
    public DeadEye() {
        super(new Stats(200, 2), "deadEye/deadEye.png", "deadEye/bullet.png", 2, 8);
        setSize(32f, 32f);
    }
}
