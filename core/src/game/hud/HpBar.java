package game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import game.entities.characters.playables.Playable;
import utilities.Render;
import utilities.ShapeRenderer;

public class HpBar extends Widget {
    private final Playable player;
    public float width, height;

    private static final Color BACKGROUND_COLOR = Color.GRAY;
    private static final Color HEALTH_COLOR = Color.RED;
    private static final Color ARMOR_COLOR = Color.BLUE;

    public HpBar(Playable player) {
        super();
        this.player = player;
        setSize(400f, 20f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.end();

        Render.sr.setProjectionMatrix(getStage().getCamera().combined);
        Render.sr.begin(ShapeRenderer.ShapeType.Filled);

        // Draw background
        Render.sr.setColor(BACKGROUND_COLOR);
        Render.sr.rect(getX(), getY(), width, height);

        float max = player.getMaxHp() + player.getArmor();

        // Draw health bar
        float healthWidth = ((float) player.getHp() / max) * width;
        Render.sr.setColor(HEALTH_COLOR);
        Render.sr.rect(getX(), getY(), healthWidth, height);

        float endOfHealth = getX() + healthWidth;
        float armorWidth = ((float) player.getArmor() / max) * width;

        Render.sr.setColor(ARMOR_COLOR);
        Render.sr.rect(endOfHealth, getY(), armorWidth, height);

        // Draw outline
        Render.sr.set(ShapeRenderer.ShapeType.Line);
        Render.sr.setColor(Color.BLACK);
        Render.sr.rect(getX(), getY(), width, height, 2f);

        Render.sr.end();

        batch.begin();
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        this.width = width;
        this.height = height;
    }
}
