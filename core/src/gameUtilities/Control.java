package gameUtilities;

public class Control {
    private final CAction action;
    private int key;

    public Control(CAction action, int key) {
        this.action = action;
        this.key = key;
    }

    public CAction getAction() {
        return action;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
