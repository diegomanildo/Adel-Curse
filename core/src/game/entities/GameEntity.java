package game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import game.Game;
import game.utilities.MovableObject;
import utilities.Label;
import utilities.Log;
import utilities.io.Sound;

public abstract class GameEntity extends MovableObject {
    private int hp;
    private int maxHp;
    private int damage;
    private final Sound deathSound;

    public GameEntity(String texturePath, int columns, int rows, float frameDuration) {
        super(texturePath, columns, rows, frameDuration);
        maxHp = getInitMaxHp();
        hp = maxHp;
        damage = getInitDamage();
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

    protected abstract int getInitMaxHp();
    protected abstract int getInitDamage();

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getDamage() {
        return damage;
    }

    public void setHp(int hp) {
        this.hp = hp;
        if (isDeath()) {
            onDeath();
        }
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void damage(int damageReceived) {
        setHp(getHp() - damageReceived);
    }

    public boolean isDeath() {
        return hp <= 0;
    }

    protected void onDeath() {
        this.hp = 0;
        deathSound.play();
        Log.debug(getClass().getSimpleName() + " death in x " + getX() + " y " + getY());
        remove();
        Game.entities.remove(this);
        Log.debug("Enemies: " + Game.entities.size());
    }
}
