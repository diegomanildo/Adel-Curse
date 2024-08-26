package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import game.utilities.ChatBox;
import utilities.Render;
import utilities.SubScreen;

public class ChatScreen extends SubScreen {
    private static final float PADDING = 200f;
    private static final float TYPE_SPEED = 0.2f;
    public final ChatBox big;
    public final ChatBox tiny;

    public ChatScreen() {
        super();
        big = new ChatBox() {
            @Override
            public void act(float delta) {
                super.act(delta);
                if (!isInTransition() && Gdx.input.isTouched()) {
                    remove();
                }
            }
        };
        big.setFontScale(1.25f);
        big.setPosition(PADDING, 40f);
        big.setSize(Render.screenSize.width - PADDING * 2f, 150f);
        big.setWrap(true);
        big.setAlignment(Align.topLeft);

        tiny = new ChatBox();
        tiny.setFontScale(1.25f);
        tiny.setWrap(true);
        tiny.setAlignment(Align.center);
    }

    public void createBig(String text) {
        big.setText(text);

        stage.addActor(big);
        big.startTransition(text.length() * TYPE_SPEED);
    }

    public void createTiny(String text) {
        float padding = 15f;
        tiny.setText(text);
        tiny.setSize(text.length() * padding, 40f);
        tiny.setPosition((Render.screenSize.width - tiny.getWidth()) / 2f, 40f);
        stage.addActor(tiny);
        tiny.startTransition(text.length() * TYPE_SPEED, false);
    }

    public void remove() {
        big.remove();
        tiny.remove();
    }
}
