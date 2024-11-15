package menu.config;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import game.GameScreen;
import utilities.*;

public class SaveFilesScreen extends BasicOptionsScreen {
    public static final int SAVE_FILES = 3;

    private final SaveFile[] saveFiles = new SaveFile[SAVE_FILES];
    private final TextButton[] buttons = new TextButton[saveFiles.length];
    private final boolean[] buttonsPressed = new boolean[buttons.length];

    public SaveFilesScreen(Screen backScreen) {
        super(backScreen);
        Table table = new Table();
        table.setFillParent(true);

        for (int i = 0; i < saveFiles.length; i++) {
            saveFiles[i] = new SaveFile(i);
            String text = !saveFiles[i].hasGame() ? "Vacio" : "Archivo " + (i + 1);
            int finalI = i;
            buttons[i] = new TextButton(text, () -> SaveFilePressed(finalI));
            table.add(buttons[i]).center().width(300f).height(45f).pad(10f);
        }

        stage.addActor(table);
    }

    private void SaveFilePressed(int index) {
        buttons[index].setText("Pulsa para crear nueva partida");

        for (int i = 0; i < buttonsPressed.length; i++) {
            if (buttonsPressed[i]) {
                buttonsPressed[i] = false;
                String text = !saveFiles[i].hasGame() ? "Vacio" : "Archivo " + (i + 1);
                int finalI = i;

                buttons[i].setText(text);
                buttons[i].addChangeListener(() -> SaveFilePressed(finalI));
            }
        }

        buttons[index].addChangeListener(() -> LoadSaveFile(index));
        buttonsPressed[index] = true;
    }

    private void LoadSaveFile(int index) {
        if (!buttonsPressed[index]) {
            return;
        }

        GameScreen gameScreen = new GameScreen(saveFiles[index]);

        Render.setScreen(gameScreen);
    }

    @Override
    protected String getTitleScreen() {
        return "";
    }
}
