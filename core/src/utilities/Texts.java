package utilities;

import com.badlogic.gdx.graphics.g2d.Batch;
import game.utilities.GameObject;

public final class Texts extends GameObject {
    public final Text[] texts;
    private final float textSpace;

    private AlignMode align;

    public Texts(float textSpace, Text... texts) {
        this.texts = texts;
        this.textSpace = textSpace;
        align = AlignMode.LEFT;
        setPosition(Render.getMiddleX(), Render.getMiddleY());
    }

    public Texts(Text... texts) {
        this(0f, texts);
    }

    public void setAlign(AlignMode align) {
        this.align = align;
    }

    public Text getLastText() {
        return texts[texts.length - 1];
    }

    public float getTextSpace() {
        return textSpace;
    }

    @Override
    public void setX(float x) {
        super.setX(x);

        if (texts == null || align == null) {
            return;
        }

        for (int i = 0; i < texts.length; i++) {
            float posX;
            switch (align) {
                case LEFT:
                    posX = x;
                    break;
                case CENTERED:
                    posX = getX() + (getWidth() - texts[i].getWidth()) / 2f;
                    break;
                case RIGHT:
                    posX = getX() + getWidth() - texts[i].getWidth();
                    break;
                default:
                    throw new RuntimeException("Invalid align mode: " + align);
            }

            texts[i].setX(posX);
        }
    }

    @Override
    public void setY(float y) {
        super.setY(y);

        if (texts == null || align == null) {
            return;
        }

        for (int i = 0; i < texts.length; i++) {
            texts[i].setY(y - i * (texts[i].getHeight() + textSpace));
        }
    }

    @Override
    public float getWidth() {
        float maxWidth = 0f;

        for (int i = 0; i < texts.length; i++) {
            if (i == 0 || texts[i].getWidth() > maxWidth) {
                maxWidth = texts[i].getWidth();
            }
        }

        return maxWidth;
    }

    @Override
    public float getHeight() {
        return texts[texts.length - 1].getY() - texts[0].getY() - texts[texts.length - 1].getHeight();
    }

    @Override
    public void draw(Batch batch) {
        for (Text text : texts) {
            text.draw(batch);
        }
    }

    @Override
    public void draw() {
        super.draw();
        if (isShowingHitbox()) {
            for (Text text : texts) {
                text.drawHitbox();
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        for (Text text : texts) text.dispose();
    }
}