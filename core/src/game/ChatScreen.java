package game;

import com.badlogic.gdx.Gdx;
import game.utilities.ChatBox;
import utilities.Render;
import utilities.SubScreen;

public class ChatScreen extends SubScreen {
    private static final float PADDING = 200f;
    private final ChatBox chat;

    public ChatScreen() {
        super();
        chat = new ChatBox() {
            @Override
            public void act(float delta) {
                super.act(delta);
                if (!isInTransition() && Gdx.input.isTouched()) {
                    remove();
                }
            }
        };
    }

    public void create(String text) {
        chat.setText(text);
        chat.setFontScale(1.25f);
        chat.setPosition(PADDING, 40f);
        chat.setSize(Render.screenSize.width - PADDING * 2f, 150f);
        stage.addActor(chat);
        chat.startTransition(4f);
    }
}
