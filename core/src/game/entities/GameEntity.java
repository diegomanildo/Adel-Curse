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
    public void setSize(float width, float height) {
        super.setSize(width, height);
        if (MultiplayerGameScreen.client != null && !MultiplayerGameScreen.client.isSendingData()) {
            MultiplayerGameScreen.client.updateEntitySize(getId(), width, height);
        }
    }

    @Override
    public void move(Direction direction) {
        super.move(direction);

        if (MultiplayerGameScreen.client != null && online_canSendToServer()) {
            MultiplayerGameScreen.client.updateEntityPosition(getId(), getX(), getY(), getDirection());
        }
    }

    protected boolean online_canSendToServer() {
        return true;
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
        this.id = id;
    }

    public static final String SP_C = "¿";

    @Override
    public boolean remove() {
//        if (MultiplayerGameScreen.client != null && GameData.clientNumber == Server.OWNER) {
//            MultiplayerGameScreen.client.removeEntity(getId());
//        }
        return super.remove();
    }

    @Override
    public String toString() {
        return getClass().getName() + SP_C + getX() + SP_C + getY() + SP_C + getId();
    }

    public static GameEntity parseEntity(String character) {
        String[] parts = character.split(SP_C);
        GameEntity g;

        try {
            Class<?> clazz = Class.forName(parts[0]);
            Object ent = clazz.getDeclaredConstructor().newInstance();
            if (ent instanceof GameEntity) {
                g = (GameEntity) ent;
            } else {
                throw new RuntimeException("Unexpected class: " + clazz);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        float x = Float.parseFloat(parts[1]);
        float y = Float.parseFloat(parts[2]);

        int id = Integer.parseInt(parts[3]);

        g.setPosition(x, y);
        g.setId(id);
        return g;
    }
}
