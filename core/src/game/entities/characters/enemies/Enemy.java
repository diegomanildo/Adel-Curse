package game.entities.characters.enemies;

import game.GameScreen;
import game.entities.characters.Character;
import game.utilities.Direction;

public abstract class Enemy extends Character {
    private static final float SAFE_DISTANCE = 100f;

    public Enemy(Stats stats, String texturePath, String bulletTexturePath, int columns, int rows) {
        super(stats, texturePath, bulletTexturePath, columns, rows);
        setVelocity(getVelocity() / 2f);
    }

    public Enemy(Stats stats, String texturePath, String bulletTexturePath) {
        super(stats, texturePath, bulletTexturePath);
    }

    public void target(float playerX, float playerY, float enemyX, float enemyY){
        if(intoRangeX(playerY, enemyY)){
            if(leftOrRight(playerX, enemyX)) {
                shoot(Direction.RIGHT);
            } else{
                shoot(Direction.LEFT);
            }
        }
        if(intoRangeY(playerX, enemyX)){
            if(upOrDown(playerY, enemyY)){
                shoot(Direction.UP);
            }else{
                shoot(Direction.DOWN);
            }
        }
    }

    public boolean intoRangeY(float playerX, float enemyX){
        return enemyX > (playerX - 1.5f) && enemyX < (playerX + 1.5f); //true si esta en el rango de disparo X
    }

    public boolean intoRangeX(float playerY, float enemyY){
        return enemyY > (playerY - 1.5f) && enemyY < (playerY + 1.5f); //true si esta en el rango de disparo Y
    }

    public boolean leftOrRight(float playerX, float enemyX){
        return enemyX < playerX; //devuelve true si dispara a la derecha
    }

    public boolean upOrDown(float playerY, float enemyY){
        return enemyY < playerY; //devuelve true si dispara a arriba
    }

    @Override
    protected void update(float delta) {
        float playerX = GameScreen.game.getPlayer().getX();
        float playerY = GameScreen.game.getPlayer().getY();

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

        if (dx > 0 && dy > 0) {
            return Direction.DOWN_LEFT;
        } else if (dx > 0 && dy < 0) {
            return Direction.UP_LEFT;
        } else if (dx < 0 && dy > 0) {
            return Direction.DOWN_RIGHT;
        } else if (dx < 0 && dy < 0) {
            return Direction.UP_RIGHT;
        } else if (dx > 0) {
            return Direction.LEFT;
        } else if (dx < 0) {
            return Direction.RIGHT;
        } else if (dy > 0) {
            return Direction.DOWN;
        } else {
            return Direction.UP;
        }
    }
}
