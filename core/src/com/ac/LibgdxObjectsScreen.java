package com.ac;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import utilities.*;

public class LibgdxObjectsScreen extends Screen {
    public LibgdxObjectsScreen() {
        super();
        Table table = new Table();
        table.setFillParent(true);

        Slider s = new Slider(0, 100, 1, false);
        Label l = new Label("abcdefghijklmnñopqrstuvwxyz ABCDEFGHIJKLMNÑOPQRSTUVWXYZ 1234567890");
        TextButton button = new TextButton("Boton");
        CheckBox checkBox = new CheckBox("Checkbox");
        TextField textField = new TextField("Campo de texto");
        TextArea textArea = new TextArea("Area de texto");
        SelectBox<String> selectBox = new SelectBox<>();
        selectBox.setItems("Select box1", "Select box2", "Select box3");
        ProgressBar progressBar = new ProgressBar(0, 100, 1, false);
        Touchpad touchpad = new Touchpad(10f);

        addToTable(table, s, l, textField, textArea, selectBox, progressBar, touchpad);

        table.add(new Label(button.getClass().getSimpleName() + ": "))
                .left();

        table.add(button)
                .center()
                .padLeft(10f)
                .padBottom(10f);
        table.row();

        table.add(new Label(checkBox.getClass().getSimpleName() + ": "))
                .left();

        table.add(checkBox)
                .center()
                .padLeft(10f)
                .padBottom(10f);

        stage.addActor(table);
    }

    private void addToTable(Table t, Widget... widgets) {
        for (int i = 0; i < widgets.length; i++) {
            t.add(new Label(widgets[i].getClass().getSimpleName() + ": "))
                    .left();

            t.add(widgets[i])
                    .center()
                    .padLeft(10f)
                    .padBottom(10f);

            t.row();
        }
    }
}
