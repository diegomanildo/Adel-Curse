package game.entities;

public interface Statistics {
    class Stats {
        public int hp;
        public int maxHp;
        public int damage;

        public Stats(int hp, int maxHp, int damage) {
            this.hp = hp;
            this.maxHp = maxHp;
            this.damage = damage;
        }

        public Stats(int hp, int damage) {
            this(hp, hp, damage);
        }
    }

    int getHp();
    void setHp(int hp);

    default void addHp(int hp) {
        setHp(getHp() + hp);
    }

    default void removeHp(int hp) {
        damage(hp);
    }

    int getMaxHp();
    void setMaxHp(int maxHp);

    default void addMaxHp(int hp) {
        setMaxHp(getMaxHp() + hp);
    }

    int getDamage();
    void setDamage(int damage);
    default void damage(int damageReceived) {
        setHp(getHp() - damageReceived);
    }

    default boolean isDeath() {
        return getHp() <= 0;
    }
}
