package utilities;

import com.badlogic.gdx.Gdx;
import game.Game;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SaveFile {
    private final Preferences pref;
    private final int index;
    private final Game game;

    public SaveFile(int index) {
        pref = Gdx.app.getPreferences("save_file_" + index);
        this.index = index;
        game = load();
    }

    public void save(Game game) {
        Map<String, Game> gamePref = new HashMap<>();
        gamePref.put("Game", game);
        pref.put(gamePref);
    }

    private Game load() {
        ArrayList<Game> games = new ArrayList<>();
        pref.get().forEach((p, obj) -> {
            if (obj instanceof Game) {
                games.add((Game) obj);
            }
        });

        if (games.size() > 1) {
            throw new RuntimeException("Two games or more were found");
        } else if (games.size() == 1) {
            return games.get(0);
        } else {
            System.out.println("Juego " + index + " no existe");
            return null;
        }
    }

    public boolean hasGame() {
        return game != null;
    }

    public Game getGame() {
        return game;
    }
}
