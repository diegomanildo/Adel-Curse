package game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import game.map.GameMap;
import game.rooms.Room;
import utilities.Group;
import utilities.Render;
import utilities.ShapeRenderer;

public class MiniMap extends Group {
    private static final float PADDING = 4f;

    private final GameMap gameMap;
    private final float cellSize;
    private final Texture adelIcon;
    private final Texture shopIcon;
    private final Texture skullIcon;

    public MiniMap(GameMap gameMap, float cellSize) {
        this.gameMap = gameMap;
        this.cellSize = cellSize;
        this.adelIcon = new Texture("imgs/icons/adel.png");
        this.shopIcon = new Texture("imgs/icons/shop.png");
        this.skullIcon = new Texture("imgs/icons/skull.png");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // End the batch before starting ShapeRenderer
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

        // Draw the rooms within the occupied bounds
        for (int row = minRow; row <= maxRow; row++) {
            for (int col = minCol; col <= maxCol; col++) {
                Room room = gameMap.getRoomAt(row, col);
                if (room != null) {
                    float x = getX() + (col - minCol) * (cellSize + PADDING);
                    float y = getY() + (row - minRow) * (cellSize + PADDING);

                    // Draw room with black border
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
                            drawIcon(batch, skullIcon, x + PADDING / 2f, y + PADDING / 2f, cellSize, cellSize);
                            break;
                        case SHOP:
                            drawIcon(batch, shopIcon, x + PADDING / 2f, y + PADDING / 2f, cellSize, cellSize);
                            break;
                        case CURRENT:
                            drawIcon(batch, adelIcon, x + PADDING / 2f, y + PADDING / 2f, cellSize, cellSize);
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

    private static void drawIcon(Batch batch, Texture icon, float x, float y, float width, float height) {
        batch.draw(icon, x, y, width, height);
    }
}
