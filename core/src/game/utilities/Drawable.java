package game.utilities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import utilities.Render;

public interface Drawable extends Disposable {
    void draw();

    default void drawHitbox(float x, float y, float middleX, float middleY, float width, float height, Color drawColor) {

        Render.b.end();
        // Draw the hitbox
        Render.sr.begin(ShapeRenderer.ShapeType.Line);
        Render.sr.setColor(drawColor);
        Render.sr.rect(x, y, width, height);
        Render.sr.end();

        // Draw middle of screen
        Render.sr.begin(ShapeRenderer.ShapeType.Line);
        Render.sr.line(Render.screenSize.width / 2f, 0f, Render.screenSize.width / 2f, Render.screenSize.height);
        Render.sr.line(0f, Render.screenSize.height / 2f, Render.screenSize.width, Render.screenSize.height / 2f);
        Render.sr.end();

        // Draw the circle in the middle
        Render.sr.begin(ShapeRenderer.ShapeType.Filled);
        Render.sr.circle(middleX, middleY, 1.5f);
        Render.sr.end();

        Render.b.begin();
    }

    default void drawHitbox(float x, float y, float middleX, float middleY, float width, float height) {
        drawHitbox(x, y, middleX, middleY, width, height, Color.GREEN);
    }

    @Override
    default void dispose() {}
}
