package game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import utilities.Label;
import utilities.Render;

public class Store extends WidgetGroup {
    public Store() {
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        Label shopLabel = new Label("Shop");
        shopLabel.setFontScale(1.5f);
        table.add(shopLabel).center();

        addActor(table);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        Render.sr.begin();

        Render.sr.set(ShapeRenderer.ShapeType.Filled);
        Render.sr.setColor(Color.BLACK);
        Render.sr.rect(getX(), getY(), getWidth(), getHeight());

        Render.sr.set(ShapeRenderer.ShapeType.Line);
        Render.sr.setColor(Color.WHITE);
        Render.sr.rect(getX(), getY(), getWidth(), getHeight());

        Render.sr.end();

        batch.begin();
        super.draw(batch, parentAlpha);
    }
}
