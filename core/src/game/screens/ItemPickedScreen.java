package game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;
import game.items.Item;
import utilities.Label;
import utilities.SubScreen;

public class ItemPickedScreen extends SubScreen {
    public static final float WIDTH = 400f;
    public static final float HEIGHT = 100f;

    private final Label titleLabel;
    private final Label descriptionLabel;
    private final ShapeRenderer shapeRenderer;
    private float elapsedTime;
    private boolean animationCompleted;
    private boolean started;

    public ItemPickedScreen() {
        setShow(false);
        titleLabel = new Label();
        descriptionLabel = new Label();
        shapeRenderer = new ShapeRenderer();
        elapsedTime = 0f;
        animationCompleted = false;
        started = false;

        stage.addActor(titleLabel);
        stage.addActor(descriptionLabel);
    }

    public void start(Item item) {
        setShow(true);
        elapsedTime = 0;
        animationCompleted = false;
        started = true;
        titleLabel.setText(item.getName());
        descriptionLabel.setText(item.getDescription());

        titleLabel.pack();
        descriptionLabel.pack();

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                setShow(false);
                animationCompleted = true;
                started = false;
            }
        }, 4f);
    }

    @Override
    public void render(float delta) {
        if (started) {
            elapsedTime += delta;

            float totalDuration = 4f;
            float stopDuration = 1f;
            float screenWidth = Gdx.graphics.getWidth();
            float screenHeight = Gdx.graphics.getHeight();
            float yPosition = screenHeight - HEIGHT - 100f;

            float xPosition = getXPosition(totalDuration, stopDuration, screenWidth);

            // Render the background rectangle
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.rect(xPosition, yPosition, WIDTH, HEIGHT);
            shapeRenderer.end();

            // Render the border
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.rect(xPosition, yPosition, WIDTH, HEIGHT);
            shapeRenderer.end();

            titleLabel.setPosition(xPosition + (WIDTH - titleLabel.getPrefWidth()) / 2f, yPosition + HEIGHT - titleLabel.getPrefHeight() - 10f);
            descriptionLabel.setPosition(xPosition + (WIDTH - descriptionLabel.getPrefWidth()) / 2f, yPosition + (HEIGHT - descriptionLabel.getPrefHeight()) / 2f);
        }
        super.render(delta);
    }

    private float getXPosition(float totalDuration, float stopDuration, float screenWidth) {
        float xPosition;
        float moveDuration = (totalDuration - stopDuration) / 2f;

        if (elapsedTime < moveDuration) { // Moving from left to center
            float progress = elapsedTime / moveDuration;
            xPosition = -WIDTH + progress * ((screenWidth - WIDTH) / 2f + WIDTH);
        } else if (elapsedTime < moveDuration + stopDuration) { // Stopped in the center
            xPosition = (screenWidth - WIDTH) / 2f;
        } else { // Moving from center to right
            float progress = (elapsedTime - moveDuration - stopDuration) / moveDuration;
            xPosition = (screenWidth - WIDTH) / 2f + progress * ((screenWidth + WIDTH) / 2f);
        }
        return xPosition;
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    public boolean isAnimationCompleted() {
        return animationCompleted;
    }
}
