package menu.config;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import game.GameScreen;
import utilities.Render;
import utilities.SaveFile;
import utilities.Screen;
import utilities.TextButton;

public class SaveFilesScreen extends BasicOptionsScreen {
    public static final int SAVE_FILES = 3;

    private final SaveFile[] saveFiles = new SaveFile[SAVE_FILES];
    private final TextButton[] buttons = new TextButton[saveFiles.length];
    private final boolean[] buttonsEnabled = new boolean[buttons.length];

    public SaveFilesScreen(Screen backScreen) {
        super(backScreen);
        Table table = new Table();
        table.setFillParent(true);

        for (int i = 0; i < saveFiles.length; i++) {
            String text = saveFiles[i] == null ? "Vacio" : "Archivo " + (i + 1);
            int finalI = i;
            buttons[i] = new TextButton(text, () -> SaveFilePressed(finalI));
            table.add(buttons[i]).center().width(300f).height(45f).pad(10f);
        }

        stage.addActor(table);
    }

    private void SaveFilePressed(int index) {
        buttons[index].setText("Pulsa para crear nueva partida");

        buttons[index].addChangeListener(() -> LoadSaveFile(index));
        buttonsEnabled[index] = true;
    }

    private void LoadSaveFile(int index) {
        Render.setScreen(new GameScreen(saveFiles[index]));
    }

    @Override
    protected String getTitleScreen() {
        return "";
    }
}
