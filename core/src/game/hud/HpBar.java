package game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import game.entities.characters.playables.Playable;
import utilities.ShapeRenderer;

public class HpBar extends Widget {
    private final Playable player;
    private final ShapeRenderer shapeRenderer;
    public float width, height;

    private static final Color BACKGROUND_COLOR = Color.GRAY;
    private static final Color HEALTH_COLOR = Color.RED;
    private static final Color ARMOR_COLOR = Color.BLUE;

    public HpBar(Playable player) {
        super();
        this.player = player;
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        setSize(400f, 20f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.end();

        shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw background
        shapeRenderer.setColor(BACKGROUND_COLOR);
        shapeRenderer.rect(getX(), getY(), width, height);

        // Draw health bar
        float healthWidth = ((float) player.getHp() / player.getMaxHp()) * width;
        shapeRenderer.setColor(HEALTH_COLOR);
        shapeRenderer.rect(getX(), getY(), healthWidth, height);

        // Draw armor bar (on top of the health bar)
        float armorWidth = (float) Math.min(player.getArmor(), player.getMaxHp()) / player.getMaxHp() * width;
        shapeRenderer.setColor(ARMOR_COLOR);
        shapeRenderer.rect(getX(), getY(), armorWidth, height);

        // Draw outline
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(getX(), getY(), width, height, 2f);

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
