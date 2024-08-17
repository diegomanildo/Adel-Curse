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
    int getMaxHp();
    int getDamage();

    void setHp(int hp);
    void setDamage(int damage);

    default void damage(int damageReceived) {
        setHp(getHp() - damageReceived);
    }

    default boolean isDeath() {
        return getHp() <= 0;
    }
}
