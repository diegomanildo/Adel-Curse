package game.utilities;

import com.badlogic.gdx.graphics.g2d.Batch;
import game.screens.ChatScreen;
import utilities.Image;
import utilities.Label;
import utilities.audio.Sound;

public class ChatBox extends Label {
    private static final float EXTENSION = 70f;

    private final Image image;
    private final Sound typing;
    public String text;
    private String displayedText;
    private float transitionTime;
    private float elapsedTime;
    private boolean inTransition;

    public ChatBox(String text) {
        super();
        this.text = text;
        this.displayedText = "";
        this.image = new Image("backgrounds/chatbox.png");
        this.typing = new Sound("Sfx", "typing.mp3");
        this.inTransition = false;
        this.elapsedTime = 0f;
    }

    public ChatBox() {
        this("");
    }

    @Override
    public void setText(CharSequence newText) {
        this.text = newText.toString();
        this.displayedText = "";
        super.setText("");
    }

    public void complete() {
        elapsedTime = transitionTime;
        displayedText = text;
        super.setText(displayedText);
        endTransition();
    }

    public void startTransition(float transitionTime, boolean reproduceSound) {
        this.transitionTime = transitionTime;
        this.elapsedTime = 0f;
        this.displayedText = "";
        this.inTransition = true;

        if (reproduceSound && !typing.isPlaying()) {
            typing.play(true);
        }
    }

    public void startTransition(float transitionTime) {
        startTransition(transitionTime, true);
    }

    private void endTransition() {
        if (inTransition) {
            inTransition = false;
            typing.stop();
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (inTransition) {
            update(delta);
        }
    }

    private void update(float delta) {
        elapsedTime += delta;

        int charsToShow = (int) ((elapsedTime / transitionTime) * text.length());
        charsToShow = Math.min(charsToShow, text.length());

        displayedText = text.substring(0, charsToShow);
        super.setText(displayedText);

        if (displayedText.equals(text)) {
            endTransition();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (this instanceof ChatScreen.BigChatBox) {
            image.setPosition(getX() - EXTENSION, getY() - EXTENSION);
            image.setSize(getWidth() + EXTENSION * 2, getHeight() + EXTENSION * 2);
        } else {
            image.setPosition(getX(), getY());
            image.setSize(getWidth(), getHeight());
        }

        image.draw(batch, parentAlpha);
        super.draw(batch, parentAlpha);
    }

    public boolean isInTransition() {
        return inTransition;
    }

    public boolean isShowing() {
        return getStage() != null;
    }
}
