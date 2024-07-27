package game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import game.utilities.Bullet;
import game.utilities.GameObject;
import game.utilities.MovableObject;
import utilities.Direction;
import utilities.FilePaths;
import utilities.exceptions.DirectionNotValidException;
import utilities.io.Sound;

import java.util.ArrayList;

public abstract class Character extends MovableObject {
    private final ArrayList<Bullet> bullets;
    private final Sound shootSound;
    private final String bulletTexturePath;

    public Character(String texturePath, String bulletTexturePath) {
        super(FilePaths.CHARACTERS + texturePath, 2, 8, 0.4f);
        setSize(78.0f, 96.0f);
        setVelocity((getWidth() + getHeight()) / 30f);
        bullets = new ArrayList<>();
        shootSound = new Sound("Sfx", "game/shoot.mp3");
        this.bulletTexturePath = bulletTexturePath;
    }

    // Creates a bullet and then shoots it
    protected void shoot(Direction bulletDirection) {
        int moveIndex;

        switch (bulletDirection) {
            case Down:
                moveIndex = 4;
                break;
            case Up:
                moveIndex = 5;
                break;
            case Right:
                moveIndex = 6;
                break;
            case Left:
                moveIndex = 7;
                break;
            default:
                throw new DirectionNotValidException("The direction " + bulletDirection + " is not valid");
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
            bullets.stream().filter(b -> b.getDirection() != Direction.Down).forEach(GameObject::draw);
        }
        super.draw(batch);

        // Then others
        bullets.stream().filter(b -> b.getDirection() == Direction.Down).forEach(GameObject::draw);
    }
}
