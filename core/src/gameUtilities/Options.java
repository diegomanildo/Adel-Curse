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

    public static final int NONE = -1;
    public static final int NONE_PREVIOUS = -2;

    private int optionSelected;
    private int previousOptionSelected;

    public Options(Audio mouseHover, Button... buttons) {
        this.buttons = buttons;
        this.mouseHover = mouseHover;
        optionSelected = NONE;
        previousOptionSelected = NONE_PREVIOUS;

        final int space = this.buttons[0].font.getSize() * 8;

        final float startY = (Gdx.graphics.getHeight() + (this.buttons.length - 1) * space) / 2f;

        for (int i = 0; i < this.buttons.length; i++) {
            this.buttons[i].setPosition((Gdx.graphics.getWidth() - this.buttons[i].font.getWidth()) / 2f, startY - i * space);
        }

        super.setPosition(this.buttons[0].getX(), this.buttons[0].getY());
    }

    @Override
    public float getWidth() {
        float maxWidth = 0f;

        for (int i = 0; i < buttons.length; i++) {
            if (i == 0 || maxWidth > buttons[i].font.getWidth()) {
                maxWidth = buttons[i].font.getWidth();
            }
        }

        return maxWidth;
    }

    @Override
    public float getHeight() {
        final int space = buttons[0].font.getSize() * 8;
        float height = 0f;

        for (int i = 0; i < buttons.length; i++) {
            height += buttons[i].getHeight() - i * space;
        }

        return height;
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
