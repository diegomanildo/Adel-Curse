package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import game.utilities.ChatBox;
import utilities.Render;
import utilities.SubScreen;

public class ChatScreen extends SubScreen {
    private static final float PADDING = 200f;

    private static class BigChatBox extends ChatBox {
        public BigChatBox() {
            setFontScale(1.25f);
            setWrap(true);
            setAlignment(Align.topLeft);
        }

        @Override
        public void act(float delta) {
            super.act(delta);
            if (!isInTransition() && (Gdx.input.justTouched())) {
                remove();
            }
        }
    }

    private static class TinyChatBox extends ChatBox {
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
        big.setText(text);
        big.setSize(Render.screenSize.width - PADDING * 2f, 150f);
        big.setPosition((Render.screenSize.width - big.getWidth()) / 2f, 40f);
        stage.addActor(big);
        big.startTransition(1f);
    }

    public void createTiny(String text) {
        tiny.setText(text);
        tiny.setSize(text.length() * 15f, 40f);
        tiny.setPosition((Render.screenSize.width - tiny.getWidth()) / 2f, 40f);
        stage.addActor(tiny);
        tiny.startTransition(1f, false);
    }

    public void removeBig() {
        big.remove();
    }

    public void removeTiny() {
        tiny.remove();
    }
}
