package utilities;

import utilities.io.Audio;

public class Button extends Text {
    private ButtonAction action;
    private static final Audio ON_PRESSED_SOUND = new Audio("buttonClicked.mp3");

    public Button(Font font, String text, float x, float y, ButtonAction action) {
        super(font, text, x, y);
        this.action = action;
    }

    public Button(Font font, String text, ButtonAction action) {
        this(font, text, 0f, 0f, action);
    }

    public void execute() {
        ON_PRESSED_SOUND.play();
        action.onPressed();
    }
}
