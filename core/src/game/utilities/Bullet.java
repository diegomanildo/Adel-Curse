package game.utilities;

import game.GameScreen;
import game.entities.characters.enemies.Enemy;
import utilities.Render;

public final class Bullet extends MovableObject {
    private static final float PIXELS_DELAY = 100f;

    private final Direction direction;

    public Bullet(String texturePath, Direction direction, float frameDuration) {
        super(texturePath, 2, 4, frameDuration);
        this.direction = direction;
        setHitbox(10f, 10f);
    }

    // Update bullet position
    public void update(float deltaTime) {
        float x = getX();
        float y = getY();
        float velocity = getVelocity();

        switch (direction) {
            case DOWN:
                y -= velocity * deltaTime;
                break;
            case UP:
                y += velocity * deltaTime;
                break;
            case RIGHT:
                x += velocity * deltaTime;
                break;
            case LEFT:
                x -= velocity * deltaTime;
                break;
            default:
                throw new RuntimeException("The direction " + direction + " is not valid");
        }

        setPosition(x, y);
    }

    // Bullet is no more in the screen, and you should have not rendered it
    public boolean outOfBounds() {
        return (getX() < Camera2D.getLeft(Render.camera) - PIXELS_DELAY || getX() > Camera2D.getRight(Render.camera) + PIXELS_DELAY
                || getY() < Camera2D.getBottom(Render.camera) - PIXELS_DELAY || getY() > Camera2D.getTop(Render.camera) + PIXELS_DELAY);
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean collidesWithEnemy(int damageReceived) {
        for (Enemy e : GameScreen.enemies) {
            if (e.collidesWith(this)) {
                e.damage(damageReceived);
                return true;
            }
        }

        return false;
    }
}
