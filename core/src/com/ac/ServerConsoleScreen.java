package com.ac;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import utilities.Label;
import utilities.Screen;

public class ServerConsoleScreen extends Screen {
    private final Table table;
    private final ScrollPane scrollPane;

    public ServerConsoleScreen() {
        Table container = new Table();
        container.setFillParent(true);
        container.setBackground(createSolidColorDrawable(new Color(0x141414ff)));

        Label titleLabel = new Label("Server Console");
        titleLabel.setFontScale(1.5f);
        titleLabel.setAlignment(Align.center);
        container.add(titleLabel).growX().pad(10f);
        container.row();

        table = new Table();
        table.top().left();

        scrollPane = new ScrollPane(table);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        Table scrollContainer = new Table();
        scrollContainer.add(scrollPane).grow().pad(10f);
        container.add(scrollContainer).grow();

        stage.addActor(container);
    }

    private void addMessageToTable(String message) {
        Label label = new Label(message);
        label.setWrap(true);
        label.setAlignment(Align.left);

        if (message.contains("[INFO]")) {
            label.setColor(Color.SKY);
            table.add(createMessageContainer(label, Color.valueOf("1e2a38"))).growX().pad(5);
        } else if (message.contains("[WARNING]")) {
            label.setColor(Color.ORANGE);
            table.add(createMessageContainer(label, Color.valueOf("3e2a18"))).growX().pad(5);
        } else if (message.contains("[ERROR]")) {
            label.setColor(Color.SALMON);
            table.add(createMessageContainer(label, Color.valueOf("4e1a1a"))).growX().pad(5);
        } else {
            table.add(createMessageContainer(label, Color.valueOf("2e2e2e"))).growX().pad(5);
        }

        table.row();
    }

    private Table createMessageContainer(Label label, Color backgroundColor) {
        Table container = new Table();
        container.setBackground(createSolidColorDrawable(backgroundColor));
        container.add(label).growX().pad(8f);
        return container;
    }

    private Drawable createSolidColorDrawable(Color color) {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new Image(texture).getDrawable();
    }

    public void addMessage(String message) {
        synchronized (this) {
            Gdx.app.postRunnable(() -> {
                addMessageToTable(message);
                scrollPane.layout();
                scrollToBottom();
            });
        }
    }

    private void scrollToBottom() {
        scrollPane.scrollTo(0f, 0f, 0f, 0f);
    }
}
