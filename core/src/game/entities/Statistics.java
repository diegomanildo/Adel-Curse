package game.entities;

public interface Statistics {
    class Stats {
        public int hp;
        public int maxHp;
        public int damage;
        public int armor;

        public Stats(int hp, int damage, int armor) {
            this.hp = hp;
            this.maxHp = hp;
            this.damage = damage;
            this.armor = armor;
        }

        public Stats(int hp, int damage) {
            this(hp, damage, 0);
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

    int getArmor();
    void setArmor(int armor);
    default void addArmor(int armor) {
        setArmor(getArmor() + armor);
    }

    int getDamage();
    void setDamage(int damage);
    default void damage(int damageReceived) {
        if (getArmor() > 0) {
            int remainingDamage = damageReceived - getArmor();
            setArmor(Math.max(getArmor() - damageReceived, 0));
            if (remainingDamage > 0) {
                setHp(getHp() - remainingDamage);
            }
        } else {
            setHp(getHp() - damageReceived);
        }
    }

    default boolean isDeath() {
        return getHp() <= 0;
    }
}
