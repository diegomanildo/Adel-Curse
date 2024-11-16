package game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Array;
import game.GameScreen;
import game.entities.items.Item;
import utilities.Image;
import utilities.Label;
import utilities.Render;
import utilities.TextButton;

public class Store extends WidgetGroup {
    public static final float SIZE_MULTIPLIER = 3f;
    public static final int ITEMS_QUANTITY = 3;
    private final Array<Item> items;

    public Store(Runnable exit) {

        Table table = new Table();
        table.setFillParent(true);
        table.top();
        Label shopLabel = new Label("Shop");
        shopLabel.setFontScale(1.5f);
        table.add(shopLabel).center().expandX();

        addActor(table);
        TextButton exitButton = new TextButton("X", exit);
        table.add(exitButton).right();

        items = new Array<>();

        for (int i = 0; i < ITEMS_QUANTITY; i++) {
            Item item;

            do {
                item = Item.ITEMS.getRandomItem();
            } while (items.contains(item, false));

            items.add(item);

            Image coin = new Image("icons/shop.png");
            TextButton itemButton = new TextButton(item);
            Item finalItem = item;
            item.setSize(finalItem.getWidth() * SIZE_MULTIPLIER, finalItem.getHeight() * SIZE_MULTIPLIER);
            itemButton.addChangeListener(() -> {
                finalItem.setSize(finalItem.getWidth() / SIZE_MULTIPLIER, finalItem.getHeight() / SIZE_MULTIPLIER);
                finalItem.changeOwnerTo(GameScreen.game.getPlayers().get(0));
                finalItem.addToOwner();
                itemButton.remove();
            });

            table.row().padTop(10f);
            table.add(coin);
            table.add(itemButton);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        Render.sr.begin(ShapeRenderer.ShapeType.Filled);

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
