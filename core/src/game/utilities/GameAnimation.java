package game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import game.entities.GameEntity;
import game.screens.MultiplayerGameScreen;
import utilities.Actor;
import utilities.Render;

public abstract class GameAnimation extends Actor {
    private float stateTime;
    private int index;
    private float frameDuration;

    private int columns;
    private int rows;

    private TextureRegion[] frames;

    private boolean stopped;

    public GameAnimation(String texturePath, int columns, int rows, float frameDuration) {
        super();
        setFrames(texturePath, columns, rows, frameDuration);
    }

    public void setFrames(String texturePath, int columns, int rows, float frameDuration) {
        if (MultiplayerGameScreen.client != null && !MultiplayerGameScreen.client.isSendingData() && this instanceof GameEntity) {
            GameEntity e = (GameEntity)this;
            MultiplayerGameScreen.client.changeFrames(e.getId(), texturePath, columns, rows, frameDuration);
        }

        this.stateTime = 0f;
        this.index = 0;
        this.frameDuration = frameDuration;
        this.columns = columns;
        this.rows = rows;
        this.frames = getFrames(texturePath, columns, rows);
        this.stopped = false;
        setAnimation(0);
    }

    private static TextureRegion[] getFrames(String texturePath, int columns, int rows) {
        Texture texture = Render.assetManager.get(texturePath);

        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / columns, texture.getHeight() / rows);
        TextureRegion[] frames = new TextureRegion[columns * rows];

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        return frames;
    }

    public void setAnimation(int index) {
        if (index >= 0 && index < frames.length) {
            this.index = index * columns;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        stateTime += Gdx.graphics.getDeltaTime();

        int frameNumber;

        if (stopped) {
            frameNumber = 0;
        } else {
            frameNumber = ((int)(stateTime / frameDuration)) % frames.length;
            frameNumber %= columns;
        }

        TextureRegion currentFrame;

        try {
            int i = index + frameNumber;
            if (i < frames.length) {
                currentFrame = frames[i];
            } else {
                currentFrame = frames[frames.length - 1];
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Class: " + getClass() + " " + e.getMessage(), e);
        }

        batch.draw(currentFrame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    public boolean isStopped() {
        return stopped;
    }

    public float getFrameDuration() {
        return frameDuration;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setFrameDuration(float frameDuration) {
        this.frameDuration = frameDuration;
    }

    public void setStop(boolean stop) {
        this.stopped = stop;
    }
}
