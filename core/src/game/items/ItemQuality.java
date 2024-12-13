package game.items;

public enum ItemQuality {
    Common(50),
    Rare(24),
    Epic(12),
    Legendary(2);

    private final int percentage;

    ItemQuality(int percentage) {
        this.percentage = percentage;
    }

    public int getPercentage() {
        return percentage;
    }
}
