package com.ac;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import utilities.Label;
import utilities.Screen;

public class ServerChatScreen extends Screen {
    private final Table table;
    private final ScrollPane scrollPane;

    public ServerChatScreen() {
        table = new Table();
        table.setFillParent(true);
        table.top().left();

        scrollPane = new ScrollPane(table);
        scrollPane.setFillParent(true);
        stage.addActor(scrollPane);
    }

    private void addMessageToTable(String message) {
        Label label = new Label(message);
        if (message.contains("[INFO]")) {
            label.setColor(new Color(0xadd8e6));
        } else if (message.contains("[WARNING]")) {
            label.setColor(new Color(0xefcc00ff));
        } else if (message.contains("[ERROR]")) {
            label.setColor(new Color(0xa52a2aff));
        }
        label.setWrap(true);
        label.setAlignment(Align.left);
        table.add(label).growX().align(Align.left).pad(5);
        table.row();
    }

    public void addMessage(String message) {
        synchronized (this) {
            Gdx.app.postRunnable(() -> {
                addMessageToTable(message);
                scrollPane.layout();
                scrollPane.scrollTo(0, 0, 0, 0); // Auto-scroll to bottom
            });
        }
    }

    @Override
    public void hide() {
        super.hide();
        Gdx.input.setInputProcessor(null);
    }
}

