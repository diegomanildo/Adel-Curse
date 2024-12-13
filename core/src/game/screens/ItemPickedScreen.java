package game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Timer;
import game.items.Item;
import utilities.Label;
import utilities.Render;
import utilities.ShapeRenderer;
import utilities.SubScreen;

import java.util.LinkedList;
import java.util.Queue;

public class ItemPickedScreen extends SubScreen {
    public static final float WIDTH = 400f;
    public static final float HEIGHT = 100f;

    public static final float TOTAL_DURATION = 3f;
    public static final float STOP_DURATION = 1f;


    private final Label titleLabel;
    private final Label descriptionLabel;
    private float elapsedTime;
    private boolean started;

    private final Queue<Item> itemQueue; // Cola de espera para items que no han sido mostrados

    public ItemPickedScreen() {
        setShow(false);
        titleLabel = new Label();
        titleLabel.setFontScale(1.5f);
        descriptionLabel = new Label();
        elapsedTime = 0f;
        started = false;

        itemQueue = new LinkedList<>();

        stage.addActor(titleLabel);
        stage.addActor(descriptionLabel);
    }

    public void start(Item item) {
        itemQueue.add(item);

        if (started) {
            return;
        }

        processNextItem();
    }

    private void processNextItem() {
        if (itemQueue.isEmpty()) {
            return;
        }

        Item currentItem = itemQueue.poll(); // Obtener el primer item de la cola

        setShow(true);
        elapsedTime = 0f;
        started = true;
        titleLabel.setText(currentItem.getName());
        titleLabel.setColor(currentItem.getItemColor());
        descriptionLabel.setText(currentItem.getDescription());
//        descriptionLabel.setColor(currentItem.getItemColor());

        titleLabel.pack();
        descriptionLabel.pack();

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                setShow(false);
                started = false;
                processNextItem(); // Procesar el siguiente item
            }
        }, TOTAL_DURATION);
    }

    @Override
    public void render(float delta) {
        if (started) {
            elapsedTime += delta;

            float screenWidth = Gdx.graphics.getWidth();
            float screenHeight = Gdx.graphics.getHeight();
            float yPosition = screenHeight - HEIGHT - 100f;

            float xPosition = getXPosition(screenWidth);

            // Render the background rectangle
            Render.sr.begin(ShapeRenderer.ShapeType.Filled);
            Render.sr.setColor(Color.BLACK);
            Render.sr.rect(xPosition, yPosition, WIDTH, HEIGHT);
            Render.sr.end();

            // Render the border
            Render.sr.begin(ShapeRenderer.ShapeType.Line);
            Render.sr.setColor(Color.WHITE);
            Render.sr.rect(xPosition, yPosition, WIDTH, HEIGHT);
            Render.sr.end();

            titleLabel.setPosition(xPosition + (WIDTH - titleLabel.getPrefWidth()) / 2f, yPosition + HEIGHT - titleLabel.getPrefHeight() - 10f);
            descriptionLabel.setPosition(xPosition + (WIDTH - descriptionLabel.getPrefWidth()) / 2f, yPosition + (HEIGHT - descriptionLabel.getPrefHeight()) / 2f);
        }
        super.render(delta);
    }

    private float getXPosition(float screenWidth) {
        float xPosition;
        float moveDuration = (TOTAL_DURATION - STOP_DURATION) / 2f;

        if (elapsedTime < moveDuration) { // Moving from left to center
            float progress = elapsedTime / moveDuration;
            xPosition = -WIDTH + progress * ((screenWidth - WIDTH) / 2f + WIDTH);
        } else if (elapsedTime < moveDuration + STOP_DURATION) { // Stopped in the center
            xPosition = (screenWidth - WIDTH) / 2f;
        } else { // Moving from center to right
            float progress = (elapsedTime - moveDuration - STOP_DURATION) / moveDuration;
            xPosition = (screenWidth - WIDTH) / 2f + progress * ((screenWidth + WIDTH) / 2f);
        }
        return xPosition;
    }
}
