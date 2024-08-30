package game.entities;

import game.utilities.MovableObject;

public abstract class GameEntity extends MovableObject {
    public GameEntity(String texturePath, int columns, int rows, float frameDuration) {
        super(texturePath, columns, rows, frameDuration);
    }

    public void pause() {
        setStop(true);
    }

    public void resume() {
        setStop(false);
    }
}
