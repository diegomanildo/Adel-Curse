package gameUtilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

public abstract class GameAnimation extends GameObject {
    private float stateTime;
    private int index;
    private float frameDuration;

    private int columns;
    private int rows;

    private TextureRegion[] frames;

    public GameAnimation(String textureAtlasPath, float x, float y, float width, float height, int columns, int rows, float frameDuration) {
        frames = getFrames(textureAtlasPath, columns, rows);
        this.frameDuration = frameDuration;

        setPosition(x, y);
        setSize(width, height);

        this.columns = columns;
        this.rows = rows;

        stateTime = 0f;
        setAnimation(0);
    }

    public GameAnimation(String textureAtlasPath, float x, float y, int columns, int rows, float frameDuration) {
        this(textureAtlasPath, x, y, -1f, -1f, columns, rows, frameDuration);
    }

    public GameAnimation(String textureAtlasPath, int columns, int rows, float frameDuration) {
        this(textureAtlasPath, 0f, 0f, columns, rows, frameDuration);
    }

    private static TextureRegion[] getFrames(String textureAtlasPath, int columns, int rows) {
        Texture texture = new Texture(textureAtlasPath);

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
    public void draw(Batch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

        int frameNumber = ((int)(stateTime / frameDuration)) % frames.length;
        frameNumber %= columns;

        TextureRegion currentFrame = frames[index + frameNumber];

        float width = getWidth();
        float height = getHeight();

        if (width == -1 && height == -1) {
            batch.draw(currentFrame, getX(), getY());
        } else {
            batch.draw(currentFrame, getX(), getY(), width, height);
        }
    }
}
