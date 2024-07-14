package gameUtilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import utilities.Button;
import utilities.Render;
import utilities.io.Audio;

public final class Options extends GameObject {
    private final Button[] buttons;
    private final Audio mouseHover;
    private final int buttonsSpace;

    public static final int NONE = -1;
    public static final int NONE_PREVIOUS = -2;

    private int optionSelected;
    private int previousOptionSelected;

    public Options(Button... buttons) {
        this.buttons = buttons;
        this.mouseHover = new Audio("mouseHover.mp3");
        optionSelected = NONE;
        previousOptionSelected = NONE_PREVIOUS;

        buttonsSpace = this.buttons[0].font.getSize() * 8;

        for (int i = 0; i < this.buttons.length; i++) {
            this.buttons[i].setPosition(Gdx.graphics.getWidth() / 2f, (Gdx.graphics.getHeight() / 2f) - i * buttonsSpace);
        }

        super.setPosition(this.buttons[0].getX(), this.buttons[0].getY());
    }

    @Override
    public float getWidth() {
        float maxWidth = 0f;

        for (int i = 0; i < buttons.length; i++) {
            if (i == 0 || buttons[i].getWidth() > maxWidth) {
                maxWidth = buttons[i].getWidth();
            }
        }

        return maxWidth;
    }

    @Override
    public float getHeight() {
        final float buttonHeight = buttons[0].getHeight();
        float height = 0f;

        for (int i = 0; i < buttons.length; i++) {
            height -= buttonHeight - i * buttonsSpace;
        }

        return -height;
    }

    @Override
    public void draw(Batch batch) {
        isAnyHovered();
        for (Button b : buttons) {
            b.draw(batch);
        }
    }

    private void isAnyHovered() {
        for (int i = 0; i < buttons.length; i++) {
            // If is in mouse is in any option paint it of yellow and play the hover sound
            if (buttons[i].isHovered()) {
                optionSelected = i;
                if (optionSelected != previousOptionSelected) {
                    mouseHover.play();
                }
            } else {
                if (optionSelected != NONE || previousOptionSelected == NONE_PREVIOUS) {
                    previousOptionSelected = optionSelected;
                }
                optionSelected = NONE;
            }

            // LeftClick pressed and is any option selected
            if (Render.io.isLeftPressed() && optionSelected != NONE) {
                buttons[optionSelected].execute();
            }

            buttons[i].font.setFontColor(i == optionSelected ? Color.YELLOW : Color.WHITE);
        }
    }

    public int getOptionSelected() {
        return optionSelected;
    }

    public void setOptionSelected(int optionSelected) {
        this.optionSelected = optionSelected;
    }

    public int getPreviousOptionSelected() {
        return previousOptionSelected;
    }

    public void setPreviousOptionSelected(int previousOptionSelected) {
        this.previousOptionSelected = previousOptionSelected;
    }
}
