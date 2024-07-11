package screens;

import com.ac.GameState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import utilities.*;
import utilities.io.Audio;
import utilities.io.Song;

public class MainMenuScreen implements Screen {
    private Image background;
    private int optionSelected;
    private int previousOptionSelected;
    private Button[] menuOptions;

    private Song menuSong = new Song("menuMusic.mp3", 0.1f);
    private Audio mouseHover = new Audio("mouseHover.mp3");

    private static final int NONE = -1;
    private static final int NONE_PREVIOUS = -2;

    public MainMenuScreen() {
        background = new Image(FilePaths.BACKGROUNDS + "loadingScreen.png");
        optionSelected = NONE;
        previousOptionSelected = NONE_PREVIOUS;
        menuOptions = new Button[] {
                new Button(Fonts.GOHU_FONT, "1 PLAYER", () -> {
                    Render.app.setScreen(new GameScreen());
                    menuSong.stopSong();
                }),
                new Button(Fonts.GOHU_FONT, "2 PLAYER", () -> {
                    Render.app.setScreen(new GameScreen());
                    menuSong.stopSong();
                }),
                new Button(Fonts.GOHU_FONT, "OPTIONS",  () -> {}),
                new Button(Fonts.GOHU_FONT, "QUIT",     () -> Gdx.app.exit())
        };
    }

    @Override
    public void show() {
        background.setSize(true);
        centerButtons();
        menuSong.playSong(true);
    }

    private void centerButtons() {
        float menuWidth = 0f;
        float menuHeight = 0f;

        // Get middle point in screen
        final float middleX = Render.getMiddleX() - Fonts.GOHU_FONT.getWidth() / 2f;
        final float middleY = Render.getMiddleY() + Fonts.GOHU_FONT.getHeight() / 2f;
        final float space = Fonts.GOHU_FONT.getSize() * 8;

        // Get middle point options
        for (int i = 0; i < menuOptions.length; i++) {
            menuHeight -= menuOptions[i].getHeight() - (space * i);
            if (i == 0 || menuOptions[i].getWidth() > menuWidth) {
                menuWidth = menuOptions[i].getWidth();
            }
        }

        // Set position for buttons
        for (int i = 0; i < menuOptions.length; i++) {
            menuOptions[i].setPosition(middleX, middleY - space * i);
        }
    }

    @Override
    public void render(float delta) {
        centerButtons();
        Render.b.begin();

        background.draw();
        for (int i = 0; i < menuOptions.length; i++) {
            // If is in mouse is in any option paint it of yellow and play the hover sound
            if (menuOptions[i].isHovered(Render.io.getMouseX(), Render.io.getMouseY())) {
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
                menuOptions[optionSelected].execute();
            }

            menuOptions[i].font.setFontColor(i == optionSelected ? Color.YELLOW : Color.WHITE);
            menuOptions[i].draw();
        }


        Render.b.end();
        GameState.camera.update();
    }

    @Override
    public void resize(int w, int h) {
        GameState.viewport.update(w, h);
    }
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {}
}
