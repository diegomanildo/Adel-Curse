package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import utilities.*;
import utilities.io.Audio;
import utilities.io.Song;

public class MainMenuScreen implements Screen {
    private static final int NONE = -1;
    private static final int NONE_PREVIOUS = -2;

    private final Image background;
    private final Song menuSong;
    private final Audio mouseHover;
    private final Button[] menuOptions;

    private int optionSelected;
    private int previousOptionSelected;

    public MainMenuScreen() {
        background = new Image(FilePaths.BACKGROUNDS + "loadingScreen.png");
        menuSong = new Song("menuMusic.mp3", 0.1f);
        mouseHover = new Audio("mouseHover.mp3");

        menuOptions = new Button[] {
                new Button(Fonts.GOHU_FONT, "1 PLAYER", () -> {
                    Render.app.setScreen(new GameScreen());
                    menuSong.stopSong();
                }),
                new Button(Fonts.GOHU_FONT, "2 PLAYER", () -> {
                    Render.app.setScreen(new GameScreen());
                    menuSong.stopSong();
                }),
                new Button(Fonts.GOHU_FONT, "OPTIONS", () -> {}),
                new Button(Fonts.GOHU_FONT, "QUIT", () -> Gdx.app.exit())
        };

        optionSelected = NONE;
        previousOptionSelected = NONE_PREVIOUS;
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
            if (menuOptions[i].isHovered()) {
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
    }

    @Override
    public void resize(int w, int h) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {}
}
