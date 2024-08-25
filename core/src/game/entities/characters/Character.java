package game.entities.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import game.entities.GameEntity;
import game.levels.Level;
import game.utilities.Bullet;
import game.utilities.Camera2D;
import game.utilities.Direction;
import utilities.FilePaths;
import utilities.Log;
import utilities.Timer;
import utilities.io.Sound;

import java.util.ArrayList;

public abstract class Character extends GameEntity {
    private final ArrayList<Bullet> bullets;
    private final Sound shootSound;
    private final String bulletTexturePath;
    private final Timer timer;
    private boolean firstShoot = false;

    public Character(Stats stats, String texturePath, String bulletTexturePath, int columns, int rows) {
        super(stats, FilePaths.CHARACTERS + texturePath, columns, rows, 0.4f);
        setSize(24f, 29f);
        setHitbox(getWidth() / 2f, getHeight());
        setVelocity(80f);
        bullets = new ArrayList<>();
        shootSound = new Sound("Sfx", "game/shoot.mp3");
        this.bulletTexturePath = bulletTexturePath;
        timer = new Timer();
    }

    public Character(Stats stats, String texturePath, String bulletTexturePath) {
        this(stats, texturePath, bulletTexturePath, 2, 8);
    }

    // Creates a bullet and then shoots it
    protected void shoot(Direction bulletDirection) {
        timer.start();

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

        if (timer.getSeconds() > 0.5f || !firstShoot) {
            createShoot(moveIndex - 4, bulletDirection);

            if(!firstShoot) {
                firstShoot = true;
            } else {
                timer.reset();
            }
        }
    }

    // Creates a shoot
    private void createShoot(int animationIndex, Direction bulletDirection) {
        Bullet b = new Bullet(FilePaths.CHARACTERS + bulletTexturePath, bulletDirection, 0.2f);
        b.setAnimation(animationIndex);
        float bulletSize = getHeight() / 2f;
        b.setSize(bulletSize, bulletSize);
        b.setPosition(getX() + b.getWidth() / 2f, getY() + b.getHeight() / 2f);
        b.setVelocity(getVelocity() * 2f);
        bullets.add(b);

        shootSound.play();
    }

    private void updateBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).update(Gdx.graphics.getDeltaTime());

            if (bullets.get(i).outOfBounds((Camera2D) Level.camera) || bullets.get(i).collidesWithEnemy(getDamage())) {
                bullets.get(i).impact();
                bullets.remove(i);
                Log.debug("Bullet remove, bullets in screen: " + bullets.size());
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