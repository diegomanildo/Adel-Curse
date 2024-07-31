package game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import utilities.Actor;

public abstract class GameAnimation extends Actor {
    private float stateTime;
    private int index;
    private final float frameDuration;

    private final int columns;
    private final int rows;

    private final TextureRegion[] frames;

    public GameAnimation(String texturePath, int columns, int rows, float frameDuration) {
        frames = getFrames(texturePath, columns, rows);
        this.frameDuration = frameDuration;

        this.columns = columns;
        this.rows = rows;

        stateTime = 0f;
        setAnimation(0);
    }

    private static TextureRegion[] getFrames(String texturePath, int columns, int rows) {
        Texture texture = new Texture(texturePath);

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
        if (index < 0) {
            throw new NegativeArraySizeException("Index " + index + " must be greather than 0");
        }

        if (index >= frames.length) {
            throw new ArrayIndexOutOfBoundsException("Index " + index + " is out of bounds, frames.length is " + frames.length);
        }

        this.index = index * columns;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        stateTime += Gdx.graphics.getDeltaTime();

        int frameNumber = ((int)(stateTime / frameDuration)) % frames.length;
        frameNumber %= columns;

        TextureRegion currentFrame = frames[index + frameNumber];

        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }
}
