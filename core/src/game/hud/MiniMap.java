package game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import game.map.GameMap;
import game.rooms.Room;
import utilities.FilePaths;
import utilities.Group;
import utilities.Render;
import utilities.ShapeRenderer;

public class MiniMap extends Group {
    private static final float PADDING = 4f;

    public static class Icon extends Texture {
        public Icon(String fileName) {
            super(FilePaths.ICONS + fileName);
        }
    }

    private final GameMap gameMap;
    private final float cellSize;
    private final Icon adelIcon;
    private final Icon coinIcon;
    private final Icon skullIcon;

    public MiniMap(GameMap gameMap, float cellSize) {
        this.gameMap = gameMap;
        this.cellSize = cellSize;
        this.adelIcon = new Icon("adel.png");
        this.coinIcon = new Icon("coin.png");
        this.skullIcon = new Icon("skull.png");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.end();

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

        // Draw the rooms
        for (int row = minRow; row <= maxRow; row++) {
            for (int col = minCol; col <= maxCol; col++) {
                Room room = gameMap.getRoomAt(row, col);
                if (room != null) {
                    float x = getX() + (col - minCol) * (cellSize + PADDING);
                    float y = getY() + (row - minRow) * (cellSize + PADDING);

                    // Draw black border
                    Render.sr.set(ShapeRenderer.ShapeType.Line);
                    Render.sr.setColor(Color.BLACK);
                    Render.sr.rect(x, y, cellSize + PADDING, cellSize + PADDING);
                    Render.sr.set(ShapeRenderer.ShapeType.Filled);
                    Render.sr.setColor(room.getKind().getColor());
                    Render.sr.rect(x + PADDING / 2f, y + PADDING / 2f, cellSize, cellSize);
                }
            }
        }

        // Calculate and set the size of the actor based on the occupied map area
        float width = (maxCol - minCol + 1) * (cellSize + PADDING);
        float height = (maxRow - minRow + 1) * (cellSize + PADDING);
        setSize(width, height);
        Render.sr.set(ShapeRenderer.ShapeType.Line);
        Render.sr.setColor(Color.BLACK);
        Render.sr.rect(getX(), getY(), getWidth(), getHeight());

        Render.sr.end();

        // Now begin the batch again to draw the icons
        batch.begin();

        // Draw the icons
        for (int row = minRow; row <= maxRow; row++) {
            for (int col = minCol; col <= maxCol; col++) {
                Room room = gameMap.getRoomAt(row, col);
                if (room != null) {
                    float x = getX() + (col - minCol) * (cellSize + PADDING);
                    float y = getY() + (row - minRow) * (cellSize + PADDING);

                    switch (room.getKind()) {
                        case BOSS:
                            batch.draw(skullIcon, x + PADDING / 2f, y + PADDING / 2f, cellSize, cellSize);
                            break;
                        case SHOP:
                            batch.draw(coinIcon, x + PADDING / 2f, y + PADDING / 2f, cellSize, cellSize);
                            break;
                        case CURRENT:
                            batch.draw(adelIcon, x + PADDING / 2f, y + PADDING / 2f, cellSize, cellSize);
                            break;
                        case OTHER:
                            break;
                        default:
                            throw new RuntimeException("Room kind " + room.getKind() + " not recognized");
                    }
                }
            }
        }
    }
}
