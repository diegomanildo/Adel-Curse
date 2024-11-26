package game.entities;
import game.screens.MultiplayerGameScreen;
import game.utilities.Direction;
import game.utilities.MovableObject;

public abstract class GameEntity extends MovableObject {
    private static int ids = 0;

    private int id = ids++;

    public GameEntity(String texturePath, int columns, int rows, float frameDuration) {
        super(texturePath, columns, rows, frameDuration);
    }

    @Override
    public void move(Direction direction) {
        super.move(direction);

        if (MultiplayerGameScreen.client != null) {
            MultiplayerGameScreen.client.updateEntityPosition(getId(), getX(), getY(), getDirection());
        }
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

    public void setId(int id) {
        if (id < 0 || id > ids) {
            this.id = id;
        } else {
            throw new RuntimeException("Id " + id + " is not valid");
        }
    }
}
