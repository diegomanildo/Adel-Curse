package utilities;

import com.ac.Render;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Font {
    private BitmapFont font;
    private int size;
    private GlyphLayout layout;

    public Font(String path, int size, Color fontColor, int shadowOffset) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(FilePaths.FONTS + path));
        FreeTypeFontParameter parameters = new FreeTypeFontParameter();

        this.size = size;
        parameters.size = size * 6;
        parameters.color = fontColor;
        parameters.shadowColor = Color.BLACK;
        parameters.shadowOffsetX = shadowOffset;
        parameters.shadowOffsetY = shadowOffset;

        font = generator.generateFont(parameters);
        layout = new GlyphLayout();
    }

    public Font(String path, int size, Color fontColor) {
        this(path, size, fontColor, 1);
    }

    public void draw(Batch batch, String text, float x, float y) {
        layout.setText(font, text);
        font.draw(batch, text, x, y);
    }

    public void draw(String text, float x, float y) {
        draw(Render.batch, text, x, y);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public float getWidth() {
        return layout.width;
    }

    public float getHeight() {
        return layout.height;
    }

    public Color getFontColor() {
        return font.getColor();
    }

    public void setFontColor(Color color) {
        font.setColor(color);
    }
}
