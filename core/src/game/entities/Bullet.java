package game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import game.Game;
import game.entities.characters.Character;
import game.entities.characters.enemies.Enemy;
import game.utilities.Camera2D;
import game.utilities.Direction;

public final class Bullet extends GameEntity {
    private final Character owner;
    private boolean impacted;
    private float impactTime;

    private final Direction direction;
    private Runnable onImpactEnds;

    public Bullet(Character owner, String texturePath, Direction direction, float frameDuration) {
        super(texturePath, 2, 5, frameDuration);
        this.owner = owner;
        this.impacted = false;
        this.impactTime = 0f;
        this.direction = direction;
        setHitbox(10f, 10f);
    }

    public void impact(Runnable onImpactEnds) {
        this.onImpactEnds = onImpactEnds;
        impacted = true;
        impactTime = 0f;
        setAnimation(4);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (impacted) {
            impactTime += Gdx.graphics.getDeltaTime();
            if (impactTime >= getFrameDuration()) {
                onImpactEnds.run();
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
        if (impacted) {
            return;
        }

        float x = getX();
        float y = getY();
        float velocity = getVelocity() * deltaTime;

        switch (direction) {
            case DOWN:
                y -= velocity;
                break;
            case UP:
                y += velocity;
                break;
            case RIGHT:
                x += velocity;
                break;
            case LEFT:
                x -= velocity;
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
        for (Enemy e : Game.game.getEntities().getEnemies()) {
            if (e.collidesWith(this) && e != owner && !impacted) {
                e.damage(damageReceived);
                return true;
            }
        }

        return false;
    }

    public boolean collidesWithPlayer(int damageReceived) {
        for (Character c : Game.game.getEntities().getPlayers()) {
            if (c.collidesWith(this) && c != owner && !impacted) {
                c.damage(damageReceived);
                return true;
            }
        }

        return false;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }
}
