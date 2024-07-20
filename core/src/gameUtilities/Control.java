package gameUtilities;

public class Control {
    private final GameAction action;
    private int key;

    public Control(GameAction action, int key) {
        this.action = action;
        this.key = key;
    }

    public GameAction getAction() {
        return action;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
