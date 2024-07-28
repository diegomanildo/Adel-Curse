package game.entities.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import game.entities.GameEntity;
import game.utilities.Bullet;
import game.utilities.Direction;
import game.utilities.GameObject;
import utilities.FilePaths;
import utilities.io.Sound;

import java.util.ArrayList;

public abstract class Character extends GameEntity {
    private final ArrayList<Bullet> bullets;
    private final Sound shootSound;
    private final String bulletTexturePath;

    public Character(String texturePath, String bulletTexturePath) {
        super(FilePaths.CHARACTERS + texturePath, 2, 8, 0.4f);
        setSize(25.0f, 29.0f);
        setVelocity((getWidth() + getHeight()) / 30f);
        bullets = new ArrayList<>();
        shootSound = new Sound("Sfx", "game/shoot.mp3");
        this.bulletTexturePath = bulletTexturePath;
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
        b.setPosition(getMiddleX() - b.getWidth() / 2f, getMiddleY() - b.getHeight() / 2f);
        b.setVelocity(getVelocity() * 100f);
        bullets.add(b);
        shootSound.play();
    }

    private void updateBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).update(Gdx.graphics.getDeltaTime());

            if (bullets.get(i).outOfBounds()) {
                bullets.remove(i);
            }
        }
    }

    @Override
    public void draw(Batch batch) {
        if (!bullets.isEmpty()) {
            updateBullets();
            // First draw down bullets
            bullets.stream().filter(b -> b.getDirection() != Direction.DOWN).forEach(GameObject::draw);
        }
        super.draw(batch);

        // Then others
        bullets.stream().filter(b -> b.getDirection() == Direction.DOWN).forEach(GameObject::draw);
    }
}
