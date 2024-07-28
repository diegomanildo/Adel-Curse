package game.entities;

import com.badlogic.gdx.graphics.Color;
import game.utilities.MovableObject;
import utilities.Render;

public abstract class GameEntity extends MovableObject {
    public GameEntity(String texturePath, int columns, int rows, float frameDuration) {
        super(texturePath, columns, rows, frameDuration);
    }

    @Override
    public void draw() {
        draw(Render.b);
        if (isShowingHitbox()) {
            drawHitbox(Color.BLUE);
        }
    }
}
