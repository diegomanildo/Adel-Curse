package game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import game.utilities.MovableObject;
import utilities.Label;

public abstract class GameEntity extends MovableObject {
    private int hp;
    private int damage;

    public GameEntity(String texturePath, int columns, int rows, float frameDuration) {
        super(texturePath, columns, rows, frameDuration);
        hp = getInitHp();
        damage = getInitDamage();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Label hp = new Label("Hp: " + getHp());
        hp.setPosition(getX(), getY() + getHeight() + 10f);
        hp.draw(batch, parentAlpha);
    }

    protected abstract int getInitHp();
    protected abstract int getInitDamage();

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public void setHp(int hp) {
        this.hp = hp;
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
