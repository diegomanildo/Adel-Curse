package game.entities.characters.enemies;

import com.badlogic.gdx.Gdx;
import game.Game;
import game.entities.characters.Character;
import game.entities.characters.playables.Playable;
import game.net.GameData;
import game.net.Server;
import game.utilities.Direction;
import game.utilities.Hitbox;

public abstract class Enemy extends Character {
    private static final float SAFE_DISTANCE = 100f;

    public Enemy(Stats stats, String texturePath, String bulletTexturePath, int columns, int rows) {
        super(stats, texturePath, bulletTexturePath, columns, rows);
        setVelocity(getVelocity() / 2f);
    }

    public Enemy(Stats stats, String texturePath, String bulletTexturePath) {
        super(stats, texturePath, bulletTexturePath);
    }

    @Override
    protected String getShootSoundPath() {
        return "game/enemyShoot.mp3";
    }

    @Override
    protected boolean online_canSendToServer() {
        return super.online_canSendToServer() && GameData.clientNumber == Server.OWNER;
    }

    public void target(float playerX, float playerY, float enemyX, float enemyY){
        if (intoRangeX(playerY, enemyY)) {
            if (leftOrRight(playerX, enemyX)) {
                shoot(Direction.RIGHT);
            } else {
                shoot(Direction.LEFT);
            }
        }
        if (intoRangeY(playerX, enemyX)) {
            if (upOrDown(playerY, enemyY)) {
                shoot(Direction.UP);
            } else {
                shoot(Direction.DOWN);
            }
        }
    }

    public boolean intoRangeY(float playerX, float enemyX) {
        return enemyX > (playerX - 1.5f) && enemyX < (playerX + 1.5f); // true si esta en el rango de disparo X
    }

    public boolean intoRangeX(float playerY, float enemyY) {
        return enemyY > (playerY - 1.5f) && enemyY < (playerY + 1.5f); // true si esta en el rango de disparo Y
    }

    public boolean leftOrRight(float playerX, float enemyX) {
        return enemyX < playerX; // devuelve true si dispara a la derecha
    }

    public boolean upOrDown(float playerY, float enemyY) {
        return enemyY < playerY; // devuelve true si dispara a arriba
    }

    @Override
    protected void update(float delta) {
        Playable player = Game.game.getPlayers().random();
        if (player == null) {
            return;
        }

        float playerX = player.getX();
        float playerY = player.getY();

        float enemyX = getX();
        float enemyY = getY();

        float distanceToPlayer = calculateDistance(playerX, playerY, enemyX, enemyY);

        target(playerX, playerY, enemyX, enemyY);

        Direction direction;

        if (distanceToPlayer < SAFE_DISTANCE) {
            direction = calculateOppositeDirection(playerX, playerY, enemyX, enemyY);
        } else {
            direction = calculateDirection(playerX, playerY, enemyX, enemyY);
        }

        move(direction);
    }

    private float calculateDistance(float playerX, float playerY, float enemyX, float enemyY) {
        return (float) Math.sqrt(Math.pow(playerX - enemyX, 2) + Math.pow(playerY - enemyY, 2));
    }

    private Direction calculateDirection(float playerX, float playerY, float enemyX, float enemyY) {
        float dx = playerX - enemyX;
        float dy = playerY - enemyY;

        if (Math.abs(dx) < 1 && Math.abs(dy) < 1) {
            return Direction.NONE;
        }

        if (dx > 0 && dy > 0) {
            return Direction.UP_RIGHT;
        } else if (dx > 0 && dy < 0) {
            return Direction.DOWN_RIGHT;
        } else if (dx < 0 && dy > 0) {
            return Direction.UP_LEFT;
        } else if (dx < 0 && dy < 0) {
            return Direction.DOWN_LEFT;
        } else if (dx > 0) {
            return Direction.RIGHT;
        } else if (dx < 0) {
            return Direction.LEFT;
        } else if (dy > 0) {
            return Direction.UP;
        } else {
            return Direction.DOWN;
        }
    }

    private Direction calculateOppositeDirection(float playerX, float playerY, float enemyX, float enemyY) {
        float dx = playerX - enemyX;
        float dy = playerY - enemyY;

        Direction oppositeDirection;

        if (dx > 0 && dy > 0) {
            oppositeDirection = Direction.DOWN_LEFT;
        } else if (dx > 0 && dy < 0) {
            oppositeDirection = Direction.UP_LEFT;
        } else if (dx < 0 && dy > 0) {
            oppositeDirection = Direction.DOWN_RIGHT;
        } else if (dx < 0 && dy < 0) {
            oppositeDirection = Direction.UP_RIGHT;
        } else if (dx > 0) {
            oppositeDirection = Direction.LEFT;
        } else if (dx < 0) {
            oppositeDirection = Direction.RIGHT;
        } else if (dy > 0) {
            oppositeDirection = Direction.DOWN;
        } else {
            oppositeDirection = Direction.UP;
        }

        return canMove(oppositeDirection) ? oppositeDirection : Direction.NONE;
    }

    private boolean canMove(Direction direction) {
        Hitbox levelHitbox = Game.game.getLevel().getHitbox();

        float nextX = getX();
        float nextY = getY();
        float speed = getVelocity() * Gdx.graphics.getDeltaTime();

        switch (direction) {
            case UP:
                nextY += speed;
                break;
            case DOWN:
                nextY -= speed;
                break;
            case LEFT:
                nextX -= speed;
                break;
            case RIGHT:
                nextX += speed;
                break;
            case UP_LEFT:
                nextX -= speed;
                nextY += speed;
                break;
            case UP_RIGHT:
                nextX += speed;
                nextY += speed;
                break;
            case DOWN_LEFT:
                nextX -= speed;
                nextY -= speed;
                break;
            case DOWN_RIGHT:
                nextX += speed;
                nextY -= speed;
                break;
            default:
                return true;
        }

        return levelHitbox.contains(nextX, nextY, getWidth(), getHeight());
    }
}
