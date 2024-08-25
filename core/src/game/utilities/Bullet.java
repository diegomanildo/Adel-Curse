package game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import game.Game;
import game.entities.characters.enemies.Enemy;

public final class Bullet extends MovableObject {
    private static final float PIXELS_DELAY = 100f;

    private boolean impacted;
    private float impactTime;
    private final float impactDuration;

    private final Direction direction;

    public Bullet(String texturePath, Direction direction, float frameDuration) {
        super(texturePath, 2, 5, frameDuration);
        this.impacted = false;
        this.impactTime = 0f;
        this.impactDuration = 0.5f;
        this.direction = direction;
        setHitbox(10f, 10f);
    }

    public void impact() {
        impacted = true;
        impactTime = 0f;
        setAnimation(4);
        setFrameDuration(getFrameDuration() - getFrameDuration() / 10f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (impacted) {
            impactTime += Gdx.graphics.getDeltaTime();
            if (impactTime >= impactDuration) {
                this.remove();
            } else {
                super.draw(batch, parentAlpha);
            }
        } else {
            super.draw(batch, parentAlpha);
        }
    }

    // Update bullet position
    public void update(float deltaTime) {
        super.act(deltaTime);
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
    public boolean outOfBounds(Camera2D camera) {
        return (getX() < camera.getLeft() - getWidth() || getX() > camera.getRight()
                || getY() < camera.getBottom() - getHeight()  || getY() > camera.getTop());
    }

    // Check if the parent is not the same entity and if collides with the bullet
    public boolean collidesWithEnemy(int damageReceived) {
        for (Enemy e : Game.entities.getEnemies()) {
            if (e.collidesWith(this)) {
                e.damage(damageReceived);
                return true;
            }
        }

        return false;
    }

    public Direction getDirection() {
        return direction;
    }
}
