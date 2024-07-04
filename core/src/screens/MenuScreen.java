package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import utilities.*;

public class MenuScreen implements Screen {
    private Image background;
    private float space;
    private int optionSelected;

    private Button[] menuOptions;
    private float time;
    private static final float WAIT_TIME = 0.09f;

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
        time = 0f;
        Render.debug.setPosition(100, 100);
    }

    @Override
    public void show() {
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        final float middleX = Render.getMiddleX() - Render.gohuFont.getWidth() / 2;
        final float middleY = Render.getMiddleY() + Render.gohuFont.getHeight() / 2;

        catchInput(delta);

        Render.b.begin();

        background.draw();

        for (int i = 0; i < menuOptions.length; i++) {
            menuOptions[i].setPosition(middleX, middleY - space * i);
            menuOptions[i].font.setFontColor(i == optionSelected ? Color.YELLOW : Color.WHITE);
            menuOptions[i].draw();

            if (Render.io.getMouseX() >= menuOptions[i].getX() && Render.io.getMouseX() <= menuOptions[i].getX() + menuOptions[i].getWidth()
            && Render.io.getMouseY() >= menuOptions[i].getY() && Render.io.getMouseY() <= menuOptions[i].getY() + menuOptions[i].getHeight()) {
                Render.debug.setText("Is Touching " + menuOptions[i].getText());
            } else {
                Render.debug.setText("Nothing");
            }
            Render.debug.draw();
        }

        Render.b.end();
    }

    private void catchInput(float delta) {
        time += delta;

        if (time > WAIT_TIME) {
            time = 0f;

            if (Render.io.isKeyPressed(Input.Keys.UP)) {
                optionSelected--;
                if (optionSelected < 0) {
                    optionSelected = 0;
                }
            }
            if (Render.io.isKeyPressed(Input.Keys.DOWN)) {
                optionSelected++;
                if (optionSelected > menuOptions.length - 1) {
                    optionSelected = menuOptions.length - 1;
                }
            }
        }

        if (Render.io.isKeyPressed(Input.Keys.ENTER)) {
            menuOptions[optionSelected].execute();
        }
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
