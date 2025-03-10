package game.entities.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import game.Game;
import game.entities.Bullet;
import game.entities.GameEntity;
import game.entities.Statistics;
import game.entities.characters.enemies.Enemy;
import game.entities.characters.playables.Playable;
import game.levels.Level;
import game.screens.MultiplayerGameScreen;
import game.utilities.Direction;
import game.utilities.Hitbox;
import utilities.Actor;
import utilities.FilePaths;
import utilities.Label;
import utilities.Timer;
import utilities.audio.Sound;

import java.util.ArrayList;

public abstract class Character extends GameEntity implements Statistics {
    private final Stats stats;

    private final ArrayList<Bullet> bullets;
    private String bulletTexturePath;

    private final Sound deathSound;
    private final Sound shootSound;

    private final Timer shootTime;
    private boolean firstShoot;

    protected final Label hpLabel;

    public Character(Stats stats, String texturePath, String bulletTexturePath, int columns, int rows) {
        super(FilePaths.CHARACTERS + texturePath, columns, rows, 0.4f);
        this.stats = stats;
        this.bullets = new ArrayList<>();
        this.bulletTexturePath = bulletTexturePath;
        this.deathSound = new Sound("Sfx", "game/death.mp3");
        this.shootSound = new Sound("Sfx", getShootSoundPath());

        this.shootTime = new Timer();
        this.firstShoot = false;

        this.hpLabel = new Label();
        this.hpLabel.setFontScale(0.5f);

        setSize(24f, 29f);
        setHitbox(getWidth() / 2f, getHeight());
        setVelocity(80f);
    }

    public Character(Stats stats, String texturePath, String bulletTexturePath) {
        this(stats, texturePath, bulletTexturePath, 2, 8);
    }

    protected abstract String getShootSoundPath();

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!isStopped()) {
            if (!bullets.isEmpty()) {
                updateBullets();
            }

            hpLabel.setText("Hp: " + getHp());
            update(delta);
        }
    }

    protected abstract void update(float delta);

    // Creates a bullet and then shoots it
    public void shoot(Direction bulletDirection, boolean threwByServer) {
        shootTime.start();

        int bulletIndex = getBulletIndex(bulletDirection);

        setAnimation(bulletIndex);

        if (threwByServer || shootTime.getSeconds() > 0.5f || !firstShoot) {
            createBullet(bulletIndex - 4, bulletDirection, threwByServer);

            if (!firstShoot) {
                firstShoot = true;
            } else {
                shootTime.reset();
            }
        }
    }

    public void shoot(Direction bulletDirection) {
        shoot(bulletDirection, false);
    }

    private int getBulletIndex(Direction bulletDirection) {
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
        return moveIndex;
    }

    // Creates a shoot
    private void createBullet(int animationIndex, Direction bulletDirection, boolean threwByServer) {
        Bullet b = new Bullet(this, bullets, FilePaths.CHARACTERS + bulletTexturePath, bulletDirection, 0.2f);
        b.setAnimation(animationIndex);
        float bulletSize = getHeight() / 2f;
        b.setSize(bulletSize, bulletSize);
        b.setPosition(getX() + b.getWidth() / 2f, getY() + b.getHeight() / 2f);
        b.setVelocity(getVelocity() * 2f);
        bullets.add(b);

        if (!threwByServer && MultiplayerGameScreen.client != null) {
            MultiplayerGameScreen.client.createShoot(getId(), bulletDirection);
        }

        shootSound.play();
    }

    private void updateBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.update(Gdx.graphics.getDeltaTime());
            boolean collides;

            if (this instanceof Playable) {
                collides = bullet.collidesWithEnemy(getDamage());
            } else if (this instanceof Enemy) {
                collides = bullet.collidesWithPlayer(getDamage());
            } else {
                throw new RuntimeException("Invalid instance of Character: " + this.getClass().getSimpleName());
            }

            if (bullet.outOfBounds(Level.camera)) {
                bullets.remove(bullet);
            } else if (collides || bullet.collidesWithWall()) {
                if (!bullet.impacted()) {
                    bullet.impact();
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // First draw down bullets
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            if (b != null && b.getDirection() != Direction.DOWN) {
                b.draw(batch, parentAlpha);
            }
        }

        super.draw(batch, parentAlpha);
        hpLabel.setPosition(getX(), getY() + getHeight());
        hpLabel.draw(batch, parentAlpha);

        // Then others
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            if (b != null && b.getDirection() == Direction.DOWN) {
                b.draw(batch, parentAlpha);
            }
        }
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        super.drawDebug(shapes);
        bullets.forEach(b -> b.drawDebug(shapes));
    }

    public void correctPosition(Hitbox roomHitbox) {
        if (getX() < roomHitbox.getLeft()) {
            setPosition(roomHitbox.getLeft(), getY());
        } else if (getX() + getWidth() > roomHitbox.getRight()) {
            setPosition(roomHitbox.getRight() - getWidth(), getY());
        }

        if (getY() < roomHitbox.getBottom()) {
            setPosition(getX(), roomHitbox.getBottom());
        } else if (getY() + getHeight() > roomHitbox.getTop()) {
            setPosition(getX(), roomHitbox.getTop() - getHeight());
        }
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public String getBulletTexturePath() {
        return bulletTexturePath;
    }

    public void setBulletTexturePath(String bulletTexturePath, boolean sendToServer) {
        this.bulletTexturePath = bulletTexturePath;

        if (MultiplayerGameScreen.client != null && sendToServer) {
            MultiplayerGameScreen.client.changeTexturePath(getId(), bulletTexturePath);
        }
    }

    public void setBulletTexturePath(String bulletTexturePath) {
        setBulletTexturePath(bulletTexturePath, false);
    }

    @Override
    public int getHp() {
        return stats.hp;
    }

    @Override
    public void setHp(int hp) {
        stats.hp = hp;

        if (getHp() > getMaxHp()) {
            setMaxHp(getHp());
        }

        if (isDeath()) {
            onDeath();
            stats.hp = 0;
        }

        if (MultiplayerGameScreen.client != null && !MultiplayerGameScreen.client.isSendingData()) {
            MultiplayerGameScreen.client.updateHp(getId(), hp);
        }
    }

    @Override
    public int getMaxHp() {
        return stats.maxHp;
    }

    @Override
    public void setMaxHp(int maxHp) {
        stats.maxHp = maxHp;
        if (MultiplayerGameScreen.client != null && !MultiplayerGameScreen.client.isSendingData()) {
            MultiplayerGameScreen.client.updateMaxHp(getId(), maxHp);
        }
    }

    @Override
    public int getDamage() {
        return stats.damage;
    }

    @Override
    public void setDamage(int damage) {
        stats.damage = damage;
        if (MultiplayerGameScreen.client != null && !MultiplayerGameScreen.client.isSendingData()) {
            MultiplayerGameScreen.client.updateDamage(getId(), damage);
        }
    }

    @Override
    public int getArmor() {
        return stats.armor;
    }

    @Override
    public void setArmor(int armor) {
        stats.armor = armor;
        if (MultiplayerGameScreen.client != null && !MultiplayerGameScreen.client.isSendingData()) {
            MultiplayerGameScreen.client.updateArmor(getId(), armor);
        }
    }

    protected void onDeath() {
        deathSound.play();
        remove();
        Game.game.getEntities().remove(this);
    }

    @Override
    public void dispose() {
        super.dispose();
        deathSound.dispose();
        shootSound.dispose();
        bullets.forEach(Actor::dispose);
    }
}