package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Align;
import game.utilities.ChatBox;
import utilities.Render;
import utilities.SubScreen;

public class ChatScreen extends SubScreen {
    private static final float PADDING = 200f;
    private static final float TYPING_TIME = 0.025f;

    private class BigChatBox extends ChatBox {
        public BigChatBox() {
            setFontScale(1.25f);
            setWrap(true);
            setAlignment(Align.topLeft);
        }

        @Override
        public void act(float delta) {
            super.act(delta);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
                if (isInTransition()) {
                    complete();
                } else {
                    removeBig();
                }
            }
        }
    }

    private class TinyChatBox extends ChatBox {
        public TinyChatBox() {
            setFontScale(1.25f);
            setWrap(true);
            setAlignment(Align.center);
        }
    }

    private final ChatBox big;
    private final ChatBox tiny;

    public ChatScreen() {
        super();
        big = new BigChatBox();
        tiny = new TinyChatBox();
    }

    public void createBig(String text) {
        GameScreen.game.pause();
        big.setText(text);
        big.setSize(Render.screenSize.width - PADDING * 2f, 150f);
        big.setPosition((Render.screenSize.width - big.getWidth()) / 2f, 40f);
        stage.addActor(big);
        big.startTransition(text.length() * TYPING_TIME);
    }

    public void createTiny(String text) {
        tiny.setText(text);
        tiny.setSize(text.length() * 15f, 40f);
        tiny.setPosition((Render.screenSize.width - tiny.getWidth()) / 2f, 40f);
        stage.addActor(tiny);
        tiny.startTransition(text.length() * TYPING_TIME, false);
    }

    public void removeBig() {
        GameScreen.game.resume();
        big.remove();
    }

    public void removeTiny() {
        tiny.remove();
    }
}
