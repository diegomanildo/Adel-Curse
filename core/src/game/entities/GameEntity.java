package game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import game.GameScreen;
import game.utilities.MovableObject;
import utilities.Label;
import utilities.Log;

public abstract class GameEntity extends MovableObject {
    private int hp;
    private int maxHp;
    private int damage;

    public GameEntity(String texturePath, int columns, int rows, float frameDuration) {
        super(texturePath, columns, rows, frameDuration);
        maxHp = getInitMaxHp();
        hp = maxHp;
        damage = getInitDamage();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Label hp = new Label("Hp: " + getHp());
        hp.setPosition(getX() + hp.getWidth() / 2f, getY() + getHeight() + 10f);
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
        if (this.hp <= 0) {
            this.hp = 0;
            Log.debug(getClass().getSimpleName() + " death in x " + getX() + " y " + getY());
            remove();
            GameScreen.enemies.remove(this);
            Log.debug("Enemies: " + GameScreen.enemies.size());
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
}
