package game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import game.Game;
import game.entities.characters.Character;
import game.entities.characters.enemies.Enemy;
import game.net.GameData;
import game.net.Server;
import game.screens.MultiplayerGameScreen;
import game.screens.OnePlayerGameScreen;
import game.utilities.Camera2D;
import game.utilities.Direction;
import game.utilities.Hitbox;
import game.utilities.MovableObject;

import java.util.ArrayList;

public final class Bullet extends MovableObject {
    private final Character owner;
    private final ArrayList<Bullet> bullets;
    private boolean impacted;
    private float impactTime;

    private final Direction direction;

    public Bullet(Character owner, ArrayList<Bullet> bullets, String texturePath, Direction direction, float frameDuration) {
        super(texturePath, 2, 5, frameDuration);
        this.owner = owner;
        this.bullets = bullets;
        this.impacted = false;
        this.impactTime = 0f;
        this.direction = direction;
        setHitbox(10f, 10f);
    }

    public void impact() {
        impacted = true;
        impactTime = 0f;
        setAnimation(4);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (impacted) {
            impactTime += Gdx.graphics.getDeltaTime();
            if (impactTime >= getFrameDuration()) {
                bullets.remove(this);
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

    public boolean collidesWithWall() {
        Hitbox roomHitbox = Game.game.getLevel().getHitbox();

        return (getX() + getWidth() < roomHitbox.x ||
                getX() > roomHitbox.x + roomHitbox.width ||
                getY() + getHeight() < roomHitbox.y ||
                getY() > roomHitbox.y + roomHitbox.height);
    }

    private boolean canReceiveDamage() {
        return Game.game instanceof OnePlayerGameScreen || (MultiplayerGameScreen.client != null && GameData.clientNumber == Server.OWNER);
    }

    // Check if the parent is not the same entity and if collides with the bullet
    public boolean collidesWithEnemy(int damageReceived) {
        for (Enemy e : Game.game.getEntities().getEnemies()) {
            if (e.collidesWith(this) && e != owner && !impacted) {
                if (canReceiveDamage()) {
                    e.damage(damageReceived);
                }
                return true;
            }
        }

        return false;
    }

    public boolean collidesWithPlayer(int damageReceived) {
        for (Character c : Game.game.getEntities().getPlayers()) {
            if (c.collidesWith(this) && c != owner && !impacted) {
                if (canReceiveDamage()) {
                    c.damage(damageReceived);
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    public boolean impacted() {
        return impacted;
    }
}
