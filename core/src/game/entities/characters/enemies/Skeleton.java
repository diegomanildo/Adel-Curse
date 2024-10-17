package game.entities.characters.enemies;

public final class Skeleton extends Enemy {
    public Skeleton() {
        super(new Stats(6, 1),"skeleton/skeleton.png", "skeleton/bullet.png", 2, 4);
    }
}
