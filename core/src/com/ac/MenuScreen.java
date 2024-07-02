package com.ac;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import utilities.*;

public class MenuScreen implements Screen {
    private Image background;
    private Font gohuFont;
    private float space;
    private int optionSelected;

    private Button[] menuOptions;
    private float time;
    private static final float WAIT_TIME = 0.09f;

    public MenuScreen() {
        background = new Image(FilePaths.BACKGROUNDS + "loadingScreen.png");
        gohuFont = new Font("Gohu.ttf", 12, Color.WHITE, 4);
        space = gohuFont.getSize() * 8;
        optionSelected = 0;
        menuOptions = new Button[] {
                new Button(gohuFont, "1 PLAYER", () -> Render.app.setScreen(new GameScreen())),
                new Button(gohuFont, "2 PLAYER", () -> Render.app.setScreen(new GameScreen())),
                new Button(gohuFont, "OPTIONS", () -> {}),
                new Button(gohuFont, "QUIT", () -> Gdx.app.exit())
        };
        time = 0f;
    }

    @Override
    public void show() {
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {

        final float middleX = Render.getMiddleX() - gohuFont.getWidth() / 2;
        final float middleY = Render.getMiddleY() + gohuFont.getHeight() / 2;

        Render.batch.begin();

        background.draw();

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

        for (int i = 0; i < menuOptions.length; i++) {
            menuOptions[i].setPosition(middleX, middleY - space * i);
            menuOptions[i].font.setFontColor(i == optionSelected ? Color.YELLOW : Color.WHITE);
            menuOptions[i].draw();
        }

        Render.batch.end();
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
