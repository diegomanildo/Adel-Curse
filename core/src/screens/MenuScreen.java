package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import utilities.*;

public class MenuScreen implements Screen {
    private Image background;
    private float space;
    private int optionSelected;

    private Button[] menuOptions;

    private static final int NONE = -1;

    public MenuScreen() {
        background = new Image(FilePaths.BACKGROUNDS + "loadingScreen.png");
        Render.gohuFont = new Font("Gohu.ttf", 12, Color.WHITE, 4);
        space = Render.gohuFont.getSize() * 8;
        optionSelected = 0;
        menuOptions = new Button[] {
                new Button(Render.gohuFont, "1 PLAYER", () -> Render.app.setScreen(new GameScreen())),
                new Button(Render.gohuFont, "2 PLAYER", () -> Render.app.setScreen(new GameScreen())),
                new Button(Render.gohuFont, "OPTIONS", () -> {}),
                new Button(Render.gohuFont, "QUIT", () -> Gdx.app.exit())
        };
    }

    @Override
    public void show() {
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Get middle point in screen
        final float middleX = Render.getMiddleX() - Render.gohuFont.getWidth() / 2;
        final float middleY = Render.getMiddleY() + Render.gohuFont.getHeight() / 2;

        // Set position for buttons
        for (int i = 0; i < menuOptions.length; i++) {
            menuOptions[i].setPosition(middleX, middleY - space * i);
        }
    }

    @Override
    public void render(float delta) {
        drawMenu();
    }

    private void drawMenu() {
        Render.b.begin();

        background.draw();
        for (int i = 0; i < menuOptions.length; i++) {
            if (Render.io.getMouseX() >= menuOptions[i].getX() && Render.io.getMouseX() <= menuOptions[i].getX() + menuOptions[i].getWidth()
                    && Render.io.getMouseY() >= menuOptions[i].getY() - menuOptions[i].getHeight() && Render.io.getMouseY() <= menuOptions[i].getY()) {
                optionSelected  = i;
            } else {
                optionSelected = NONE;
            }

            if (Render.io.isLeftPressed() && optionSelected != NONE) {
                menuOptions[optionSelected].execute();
            }

            menuOptions[i].font.setFontColor(i == optionSelected ? Color.YELLOW : Color.WHITE);
            menuOptions[i].draw();
        }

        Render.b.end();
    }

    @Override
    public void resize(int i, int i1) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {}
}
