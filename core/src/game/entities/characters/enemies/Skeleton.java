package game.entities.characters.enemies;

public final class Skeleton extends Enemy {
    public Skeleton() {
        super("skeleton/skeleton.png", "adel/bullet.png", 2, 4);
    }

    @Override
    protected int getInitMaxHp() {
        return 10;
    }

    @Override
    protected int getInitDamage() {
        return 1;
    }
}
