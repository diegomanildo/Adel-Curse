package game.utilities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import utilities.Label;
import utilities.Render;
import utilities.ShapeRenderer;
import utilities.audio.Sound;

public class ChatBox extends Label {
    private String text;
    private final Sound typing;
    private float transitionTime;
    private boolean inTransition;
    private String displayedText;
    private float elapsedTime;

    public ChatBox(String text) {
        super();
        this.text = text;
        typing = new Sound("Sfx", "typing.mp3");
        inTransition = false;
        displayedText = "";
        elapsedTime = 0f;
    }

    public ChatBox() {
        this("");
    }

    @Override
    public void setText(CharSequence newText) {
        this.text = (String) newText;
    }

    public void startTransition(float transitionTime, boolean reproduceSound) {
        inTransition = true;
        this.transitionTime = transitionTime;
        if (reproduceSound) {
            typing.play(true);
        }
    }

    public void startTransition(float transitionTime) {
        startTransition(transitionTime, true);
    }

    private void endTransition() {
        inTransition = false;
        typing.stop();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (inTransition) {
            update(delta);
        }
    }

    private void update(float delta) {
        if (displayedText.length() < text.length()) {
            int charsToShow = (int) ((elapsedTime / transitionTime) * text.length());

            displayedText = text.substring(0, charsToShow);
            super.setText(displayedText);
            elapsedTime += delta;
        }

        if (equals(displayedText, text)) {
            endTransition();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

        Render.sr.begin(ShapeRenderer.ShapeType.Filled);

        // Fondo negro
        Render.sr.setColor(Color.BLACK);
        Render.sr.rect(getX(), getY(), getWidth(), getHeight());

        Render.sr.end();

        Render.sr.begin(ShapeRenderer.ShapeType.Line);

        // Borde blanco
        Render.sr.setColor(Color.WHITE);
        Render.sr.rect(getX(), getY(), getWidth(), getHeight());

        Render.sr.end();

        batch.begin();

        super.draw(batch, parentAlpha);
    }

    public boolean isInTransition() {
        return inTransition;
    }

    private static boolean equals(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }

        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean remove() {

        return super.remove();
    }
}
