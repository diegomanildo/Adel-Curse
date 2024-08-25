package game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import game.Game;
import game.utilities.MovableObject;
import utilities.Label;
import utilities.Log;
import utilities.io.Sound;

public abstract class GameEntity extends MovableObject implements Statistics {
    private final Stats stats;
    private final Sound deathSound;

    public GameEntity(Stats stats, String texturePath, int columns, int rows, float frameDuration) {
        super(texturePath, columns, rows, frameDuration);
        this.stats = stats;
        deathSound = new Sound("Sfx", "game/death.mp3");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!isStopped()) {
            update(delta);
        }
    }

    protected abstract void update(float delta);

    public void pause() {
        setStop(true);
    }

    public void resume() {
        setStop(false);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Label hp = new Label("Hp: " + getHp());
        hp.setFontScale(0.5f);
        hp.setPosition(getX(), getY() + getHeight());
        hp.draw(batch, parentAlpha);
    }

    @Override
    public int getHp() {
        return stats.hp;
    }

    @Override
    public int getMaxHp() {
        return stats.maxHp;
    }

    @Override
    public int getDamage() {
        return stats.damage;
    }

    @Override
    public void setHp(int hp) {
        stats.hp = hp;
        if (getHp() > getMaxHp()) {
            setMaxHp(getHp());
        }
        if (isDeath()) {
            onDeath();
        }
    }

    @Override
    public void setMaxHp(int maxHp) {
        stats.maxHp = maxHp;
    }

    @Override
    public void setDamage(int damage) {
        stats.damage = damage;
    }

    protected void onDeath() {
        stats.hp = 0;
        deathSound.play();
        Log.debug(getClass().getSimpleName() + " death in x " + getX() + " y " + getY());
        remove();
        Game.entities.remove(this);
        Log.debug("Enemies: " + Game.entities.size());
    }
}
