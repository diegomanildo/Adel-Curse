package game.states.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import game.entities.characters.playables.Playable;

public class HpBar extends Widget {
    private final Playable player;
    private final ShapeRenderer shapeRenderer;
    public float width, height;

    private static final Color BACKGROUND_COLOR = Color.GRAY;
    private static final Color HEALTH_COLOR = Color.RED;

    public HpBar(Playable player) {
        super();
        this.player = player;
        shapeRenderer = new ShapeRenderer();
        setSize(400f, 20f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.end();

        shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Dibujar el fondo de la barra de vida
        shapeRenderer.setColor(BACKGROUND_COLOR);
        shapeRenderer.rect(getX(), getY(), width, height);

        // Dibujar la salud
        shapeRenderer.setColor(HEALTH_COLOR);
        float healthWidth = ((float) player.getHp() / player.getMaxHp()) * width;
        shapeRenderer.rect(getX(), getY(), healthWidth, height);

        shapeRenderer.end();

        batch.begin();
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        this.width = width;
        this.height = height;
    }
}
