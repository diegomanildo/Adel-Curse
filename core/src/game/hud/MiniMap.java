package game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import game.map.GameMap;
import game.rooms.Room;
import utilities.Actor;
import utilities.Render;
import utilities.ShapeRenderer;

public class MiniMap extends Actor {
    private final GameMap gameMap;

    private final float cellSize;
    private static final float PADDING = 4f;

    public MiniMap(GameMap gameMap, float cellSize) {
        this.gameMap = gameMap;
        this.cellSize = cellSize;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end(); // End the batch to start ShapeRenderer

        Render.sr.begin(ShapeRenderer.ShapeType.Filled);

        // Initialize the bounds of the occupied area
        int minRow = gameMap.getRows();
        int maxRow = 0;

        int minCol = gameMap.getColumns();
        int maxCol = 0;

        // Find the occupied bounds
        for (int row = 0; row < gameMap.getRows(); row++) {
            for (int col = 0; col < gameMap.getColumns(); col++) {
                Room room = gameMap.getRoomAt(row, col);
                if (room != null) {
                    if (row < minRow) minRow = row;
                    if (row > maxRow) maxRow = row;
                    if (col < minCol) minCol = col;
                    if (col > maxCol) maxCol = col;
                }
            }
        }

        // Draw the rooms within the occupied bounds
        for (int row = minRow; row <= maxRow; row++) {
            for (int col = minCol; col <= maxCol; col++) {
                Room room = gameMap.getRoomAt(row, col);
                if (room != null) {
                    float x = getX() + (col - minCol) * (cellSize + PADDING);
                    float y = getY() + (row - minRow) * (cellSize + PADDING);

                    // Draw room with black border
                    Render.sr.setColor(Color.BLACK);
                    Render.sr.rect(x, y, cellSize + PADDING, cellSize + PADDING);

                    // Draw room's interior
                    Render.sr.setColor(room.getKind().getColor());
                    Render.sr.rect(x + PADDING / 2f, y + PADDING / 2f, cellSize, cellSize);
                }
            }
        }



        // Calculate and set the size of the actor based on the occupied map area
        float width = (maxCol - minCol + 1) * (cellSize + PADDING);
        float height = (maxRow - minRow + 1) * (cellSize + PADDING);
        setSize(width, height); // Set the actor's size to the size of the occupied area
        Render.sr.set(ShapeRenderer.ShapeType.Line);
        Render.sr.setColor(Color.BLACK);
        Render.sr.rect(getX(), getY(), getWidth(), getHeight());

        Render.sr.end();
        batch.begin(); // Resume the batch
    }
}
