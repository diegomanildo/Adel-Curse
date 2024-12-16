package game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Array;
import game.Game;
import game.items.Item;
import utilities.Image;
import utilities.Label;
import utilities.Render;
import utilities.TextButton;

public class Store extends WidgetGroup {
    public static final float SIZE_MULTIPLIER = 3f;
    public static final int ITEMS_QUANTITY = 3;

    private final Array<Item> items;

    public Store(Runnable exit) {
        items = new Array<>();

        Image backgroundImage = new Image("backgrounds/chatbox.png");
        backgroundImage.setFillParent(true);
        addActor(backgroundImage);

        Table table = new Table();
        table.setFillParent(true);
        table.top();

        Label shopLabel = new Label("Shop");
        shopLabel.setFontScale(1.5f);
        table.add(shopLabel).center().expandX();

        TextButton exitButton = new TextButton("X", exit);
        table.add(exitButton).right();

        generateItems(table);

        addActor(table);
    }

    private void generateItems(Table table) {
        for (int i = 0; i < ITEMS_QUANTITY; i++) {
            Item item = getRandomUniqueItem();

            item.setSize(item.getWidth() * SIZE_MULTIPLIER, item.getHeight() * SIZE_MULTIPLIER);

            Image coin = new Image("icons/coin.png");
            TextButton itemButton = createItemButton(item);

            table.row().padTop(10f);
            table.add(coin);
            table.add(itemButton);
        }
    }

    private Item getRandomUniqueItem() {
        Item item;
        do {
            item = Item.ITEMS.getRandomItem();
        } while (items.contains(item, false));

        items.add(item);
        return item;
    }

    private TextButton createItemButton(Item item) {
        TextButton itemButton = new TextButton("");
        TextButton finalItemButton = itemButton;
        itemButton = new TextButton(item.getName(), () -> {
            item.changeOwnerTo(Game.game.getPlayer());
            item.addToOwner();
            items.removeValue(item, false);
            finalItemButton.remove();
        });
        return itemButton;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Render.sr.begin(ShapeRenderer.ShapeType.Line);
        Render.sr.setColor(Color.WHITE);
        Render.sr.rect(getX(), getY(), getWidth(), getHeight());
        Render.sr.end();
    }
}
