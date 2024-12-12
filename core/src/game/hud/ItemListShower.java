package game.hud;

import game.entities.items.Item;
import utilities.Group;

import java.util.ArrayList;

public class ItemListShower extends Group {
    private static final int MAX_ITEMS_PER_ROW = 5;
    public static final float ITEM_SIZE = 32f;

    private final ArrayList<Item> items;

    public ItemListShower(ArrayList<Item> items) {
        this.items = items;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        update(items);
    }

    private void update(ArrayList<Item> items) {
        clear();
        for (Item item : items) {
            if (item != null) {
                item.setStop(true);
                item.setSize(ITEM_SIZE, ITEM_SIZE);
                item.setRotation(0f);
                addActor(item);
            }
        }
        arrangeItems();
    }

    private void arrangeItems() {
        float xOffset = 10f;
        float yOffset = 10f;
        float currentY = 0f;
        float currentX = 0f;

        for (int i = getChildren().size - 1; i >= 0; i--) {
            getChildren().get(i).setPosition(currentX, currentY);
            currentX -= getChildren().get(i).getWidth() + xOffset;
            if (i % MAX_ITEMS_PER_ROW == 0) {
                currentX = 0f;
                currentY -= getChildren().get(i).getHeight() + yOffset;
            }
        }
    }
}
