package game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Align;
import game.Game;
import game.utilities.ChatBox;
import utilities.Render;
import utilities.SubScreen;

import java.util.HashMap;

public class ChatScreen extends SubScreen {
    private static final float BIG_PADDING = 200f;
    private static final float FULL_PADDING = 100f;
    private static final float TYPING_TIME = 0.025f;

    private static abstract class CommonChatBox extends ChatBox {
        private String key;

        public CommonChatBox() {
            super();
            setFontScale(1.25f);
            setWrap(true);
            setAlignment(Align.topLeft);
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    private class BigChatBox extends CommonChatBox {
        @Override
        public void startTransition(float transitionTime, boolean reproduceSound) {
            super.startTransition(transitionTime, reproduceSound);
            Game.game.pause();
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
            Game.game.resume();
            chats.remove(getKey());
            return super.remove();
        }
    }

    private class FullChatBox extends BigChatBox {
        public FullChatBox() {
            super();
            setBounds(FULL_PADDING, FULL_PADDING, Render.screenSize.width - FULL_PADDING * 2f, Render.screenSize.height - FULL_PADDING * 2f);
        }
    }

    private static class TinyChatBox extends CommonChatBox {
        public TinyChatBox() {
            super();
            setAlignment(Align.center);
        }
    }

    private final HashMap<String, CommonChatBox> chats;

    public ChatScreen() {
        super();
        chats = new HashMap<>();
    }

    public void createFull(String key, String text) {
        FullChatBox chat = new FullChatBox();

        chat.setText(text);
        chat.setSize(Render.screenSize.width - FULL_PADDING * 2f, Render.screenSize.height - FULL_PADDING * 2f);
        chat.setPosition(FULL_PADDING, FULL_PADDING);

        createChat(key, chat, true);
    }

    public void createBig(String key, String text) {
        BigChatBox chat = new BigChatBox();

        chat.setText(text);
        chat.setSize(Render.screenSize.width - BIG_PADDING * 2f, 150f);
        chat.setPosition((Render.screenSize.width - chat.getWidth()) / 2f, 40f);

        createChat(key, chat, true);
    }

    public void createTiny(String key, String text) {
        TinyChatBox chat = new TinyChatBox();

        chat.setText(text);
        chat.setSize(text.length() * 15f, 40f);
        chat.setPosition((Render.screenSize.width - chat.getWidth()) / 2f, 40f);

        createChat(key, chat, false);
    }

    private void createChat(String key, CommonChatBox chat, boolean reproduceSound) {
        if (!chats.containsKey(key)) {
            chat.setKey(key);
            stage.addActor(chat);
            chat.startTransition(chat.text.length() * TYPING_TIME, reproduceSound);

            chats.put(key, chat);
        }
    }

    public void removeChat(String key) {
        if (chats.containsKey(key)) {
            chats.remove(key).remove();
        }
    }
}
