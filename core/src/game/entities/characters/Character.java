package game.entities.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import game.entities.GameEntity;
import game.utilities.Bullet;
import game.utilities.Direction;
import utilities.FilePaths;
import utilities.io.Sound;

import java.util.ArrayList;

public abstract class Character extends GameEntity {
    private final ArrayList<Bullet> bullets;
    private final Sound shootSound;
    private final String bulletTexturePath;

    public Character(String texturePath, String bulletTexturePath, int columns, int rows) {
        super(FilePaths.CHARACTERS + texturePath, columns, rows, 0.4f);
        setSize(100f, 116f);
        setHitbox(40f, getHeight() - 10f);
        setVelocity((getWidth() + getHeight()) / 30f);
        bullets = new ArrayList<>();
        shootSound = new Sound("Sfx", "game/shoot.mp3");
        this.bulletTexturePath = bulletTexturePath;
    }

    public Character(String texturePath, String bulletTexturePath) {
        this(texturePath, bulletTexturePath, 2, 8);
    }

    // Creates a bullet and then shoots it
    protected void shoot(Direction bulletDirection) {
        int moveIndex;

        switch (bulletDirection) {
            case DOWN:
                moveIndex = 4;
                break;
            case UP:
                moveIndex = 5;
                break;
            case RIGHT:
                moveIndex = 6;
                break;
            case LEFT:
                moveIndex = 7;
                break;
            default:
                throw new RuntimeException("The direction " + bulletDirection + " is not valid");
        }

        setAnimation(moveIndex);
        int animationIndex = moveIndex - 4;

        if (!shootSound.isPlaying()) {
            createShoot(animationIndex, bulletDirection);
        }
    }

    // Creates a shoot
    private void createShoot(int animationIndex, Direction bulletDirection) {
        Bullet b = new Bullet(FilePaths.CHARACTERS + bulletTexturePath, bulletDirection, 0.2f);
        b.setAnimation(animationIndex);
        b.setSize(getWidth() / 2f, getHeight() / 2f);
        b.setPosition(getX() + b.getWidth() / 2f, getY() + b.getHeight() / 2f);
        b.setVelocity(getVelocity() * 100f);
        bullets.add(b);
        getStage().addActor(b);
        shootSound.play();
    }

    private void updateBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).update(Gdx.graphics.getDeltaTime());

            if (bullets.get(i).outOfBounds() || bullets.get(i).collidesWithEnemy(getDamage())) {
                bullets.get(i).remove();
                bullets.remove(i);
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!bullets.isEmpty()) {
            updateBullets();
        }

        // First draw down bullets
        bullets.stream().filter(b -> b.getDirection() != Direction.DOWN).forEach(b -> b.draw(batch, parentAlpha));

        super.draw(batch, parentAlpha);

        // Then others
        bullets.stream().filter(b -> b.getDirection() == Direction.DOWN).forEach(b -> b.draw(batch, parentAlpha));
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        super.drawDebug(shapes);
        bullets.forEach(b -> b.drawDebug(shapes));
    }
}
