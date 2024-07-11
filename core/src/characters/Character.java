package characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import gameUtilities.Bullet;
import utilities.Direction;
import utilities.FilePaths;
import gameUtilities.GameAnimation;
import utilities.exceptions.DirectionNotValidException;
import utilities.io.Audio;

import java.util.ArrayList;

public abstract class Character extends GameAnimation {
    private static final float WIDTH = 150f;
    private static final float HEIGHT = WIDTH;

    private final float velocity;
    private final ArrayList<Bullet> bullets;
    private final Audio shootSound;
    private final String bulletTexturePath;


    public Character(String texturePath, String bulletTexturePath, float x, float y) {
        super(FilePaths.CHARACTERS + texturePath, x, y, WIDTH, HEIGHT, 2, 8, 0.5f);
        velocity = WIDTH / 12;
        bullets = new ArrayList<>();
        shootSound = new Audio("shoot.mp3", 0.1f);
        this.bulletTexturePath = bulletTexturePath;
    }

    protected void move(Direction direction) {
        int moveIndex;
        float x = getX();
        float y = getY();

        switch (direction) {
            case None:
                moveIndex = 0;
                break;
            case Down:
                moveIndex = 0;
                y -= velocity;
                break;
            case Up:
                moveIndex = 1;
                y += velocity;
                break;
            case Right:
                moveIndex = 2;
                x += velocity;
                break;
            case Left:
                moveIndex = 3;
                x -= velocity;
                break;
            default:
                throw new DirectionNotValidException("The direction " + direction + " is not valid");
        }

        setAnimation(moveIndex);
        setPosition(x, y);
    }

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

    private void createShoot(int animationIndex, Direction bulletDirection) {
        Bullet b = new Bullet(FilePaths.CHARACTERS + bulletTexturePath, bulletDirection, getX(), getY(), WIDTH/2, HEIGHT/2, 0.5f);
        b.setAnimation(animationIndex);
        bullets.add(b);
        shootSound.play();
    }

    private void updateBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).update(Gdx.graphics.getDeltaTime());
            bullets.get(i).draw();

            if (bullets.get(i).outOfBounds()) {
                bullets.remove(i);
            }
        }
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        updateBullets();
        System.out.println("Bullets " + bullets.size());
    }
}
