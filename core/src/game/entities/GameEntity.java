package game.entities;
import game.utilities.MovableObject;

public abstract class GameEntity extends MovableObject {
    private static int ids = 0;

    private final int id = ids++;

    public GameEntity(String texturePath, int columns, int rows, float frameDuration) {
        super(texturePath, columns, rows, frameDuration);
    }

    public int getId() {
        return id;
    }

    public void pause() {
        setStop(true);
    }

    public void resume() {
        setStop(false);
    }
}
