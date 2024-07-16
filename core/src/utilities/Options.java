package utilities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import gameUtilities.GameObject;
import utilities.io.Audio;

public final class Options extends GameObject {
    private final Button[] buttons;
    private static final Audio MOUSE_HOVER = new Audio("mouseHover.mp3");
    private final float buttonsSpace;

    public static final int NONE = -1;
    public static final int NONE_PREVIOUS = -2;

    private int optionSelected;
    private int previousOptionSelected;

    private AlignMode align;

    public Options(float buttonsSpace, Button... buttons) {
        this.buttons = buttons;
        this.buttonsSpace = buttonsSpace;
        optionSelected = NONE;
        previousOptionSelected = NONE_PREVIOUS;
        align = AlignMode.LEFT;
        setPosition(Render.getMiddleX(), Render.getMiddleY());
    }

    public Options(Button... buttons) {
        this(0f, buttons);
    }

    public void setAlign(AlignMode align) {
        this.align = align;
    }

    @Override
    public void setX(float x) {
        super.setX(x);

        if (buttons == null || align == null) {
            return;
        }

        for (Button button : buttons) {
            float posX;
            switch (align) {
                case LEFT:
                    posX = x;
                    break;
                case CENTERED:
                    posX = getX() + (getWidth() - button.getWidth()) / 2f;
                    break;
                case RIGHT:
                    posX = getX() + getWidth() - button.getWidth();
                    break;
                default:
                    throw new RuntimeException("Invalid align mode: " + align);
            }

            button.setX(posX);
        }


    }

    @Override
    public void setY(float y) {
        super.setY(y);

        if (buttons == null || align == null) {
            return;
        }

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setY(y - i * (buttons[i].getHeight() + buttonsSpace));
        }
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
        return buttons[buttons.length - 1].getY() - buttons[0].getY() - buttons[buttons.length - 1].getHeight();
    }

    @Override
    public void draw(Batch batch) {
        for (int i = 0; i < buttons.length; i++) {
            // If is in mouse is in any option paint it of yellow and play the hover sound
            if (buttons[i].isHovered()) {
                optionSelected = i;
                buttons[i].font.setColor(Color.YELLOW);
                if (optionSelected != previousOptionSelected) {
                    MOUSE_HOVER.play();
                }
            } else {
                buttons[i].font.setColor(Color.WHITE);
                if (optionSelected != NONE || previousOptionSelected == NONE_PREVIOUS) {
                    previousOptionSelected = optionSelected;
                }
                optionSelected = NONE;
            }

            // LeftClick pressed and is any option selected
            if (Render.io.isLeftPressed() && optionSelected != NONE) {
                buttons[optionSelected].execute();
            }
            buttons[i].draw(batch);
        }
    }

    @Override
    public void draw() {
        super.draw();
        if (isShowingHitbox()) {
            for (Button button : buttons) {
                button.drawHitbox();
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        for (Button button : buttons) button.dispose();
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
