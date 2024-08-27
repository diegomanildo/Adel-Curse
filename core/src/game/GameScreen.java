package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import game.hud.Hud;
import menu.BasicMainMenuScreen;
import menu.MainMenuScreen;
import utilities.Render;
import utilities.Screen;

public final class GameScreen extends Screen {
    public static ChatScreen chat = new ChatScreen();
    public static Game game = new Game();
    public static Hud hud = new Hud();
    public static PauseScreen pause = new PauseScreen(GameScreen::exit);
    public static ShopScreen shopScreen = new ShopScreen();

    public GameScreen() {
        super();

        addSubScreen(game);
        addSubScreen(hud);
        addSubScreen(chat);
        addSubScreen(shopScreen);
        addSubScreen(pause);
    }

    @Override
    public void show() {
        super.show();
        BasicMainMenuScreen.backgroundSong.fadeOut(FADE_TIME);
    }

    @Override
    protected void handleInput() {
        super.handleInput();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pause.setShow(true);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
            game.getPlayer().setVelocity(game.getPlayer().getVelocity() * 2f);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            game.getPlayer().damage(1);
        }
    }

    public static void exit() {
        game.getSong().fadeOut(FADE_TIME);
        BasicMainMenuScreen.backgroundSong.fadeIn(FADE_TIME, true);
        Render.setScreen(new MainMenuScreen());
    }
}
