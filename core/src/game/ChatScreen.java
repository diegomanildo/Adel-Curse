package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Align;
import game.utilities.ChatBox;
import utilities.Render;
import utilities.SubScreen;

public class ChatScreen extends SubScreen {
    private static final float BIG_PADDING = 200f;
    private static final float FULL_PADDING = 100f;
    private static final float TYPING_TIME = 0.025f;

    private static abstract class CommonChatBox extends ChatBox {
        public CommonChatBox() {
            setFontScale(1.25f);
            setWrap(true);
            setAlignment(Align.topLeft);
        }
    }

    private static class BigChatBox extends CommonChatBox {
        @Override
        public void startTransition(float transitionTime, boolean reproduceSound) {
            super.startTransition(transitionTime, reproduceSound);
            GameScreen.game.pause();
        }

        @Override
        public void act(float delta) {
            super.act(delta);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
                if (isInTransition()) {
                    complete();
                } else {
                    remove();
                }
            }
        }

        @Override
        public boolean remove() {
            GameScreen.game.resume();
            return super.remove();
        }
    }

    private static class TinyChatBox extends CommonChatBox {
        public TinyChatBox() {
            super();
            setAlignment(Align.center);
        }
    }

    public final ChatBox full;
    public final ChatBox big;
    public final ChatBox tiny;

    public ChatScreen() {
        super();
        full = new BigChatBox();
        big = new BigChatBox();
        tiny = new TinyChatBox();
    }

    public void createFull(String text) {
        if (full.isShowing()) {
            return;
        }

        full.setText(text);
        full.setSize(Render.screenSize.width - FULL_PADDING * 2f, Render.screenSize.height - FULL_PADDING * 2f);
        full.setPosition(FULL_PADDING, FULL_PADDING);
        stage.addActor(full);
        full.startTransition(text.length() * TYPING_TIME);
    }

    public void createBig(String text) {
        if (big.isShowing()) {
            return;
        }

        big.setText(text);
        big.setSize(Render.screenSize.width - BIG_PADDING * 2f, 150f);
        big.setPosition((Render.screenSize.width - big.getWidth()) / 2f, 40f);
        stage.addActor(big);
        big.startTransition(text.length() * TYPING_TIME);
    }

    public void createTiny(String text) {
        if (tiny.isShowing()) {
            return;
        }

        tiny.setText(text);
        tiny.setSize(text.length() * 15f, 40f);
        tiny.setPosition((Render.screenSize.width - tiny.getWidth()) / 2f, 40f);
        stage.addActor(tiny);
        tiny.startTransition(text.length() * TYPING_TIME, false);
    }

    public void removeTiny() {
        tiny.remove();
    }
}
