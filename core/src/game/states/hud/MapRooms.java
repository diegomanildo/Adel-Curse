package game.states.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import game.rooms.BossRoom;
import game.rooms.Room;
import game.rooms.ShopRoom;
import game.utilities.Map;
import utilities.Actor;

public class MapRooms extends Actor {
    private final MapRoomsTypes[][] types;
    private ShapeRenderer shapeRenderer;

    public MapRooms(Map map) {
        int rows = map.getMap().length;
        int cols = map.getMap()[0].length;

        types = new MapRoomsTypes[rows][cols];
        shapeRenderer = new ShapeRenderer();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Room room = map.get(i, j);

                if (room == null) {
                    types[i][j] = MapRoomsTypes.Nothing;
                    continue;
                }

                Class<?> roomClass = room.getClass();

                if (roomClass.equals(ShopRoom.class)) {
                    types[i][j] = MapRoomsTypes.ShopRoom;
                } else if (roomClass.equals(BossRoom.class)) {
                    types[i][j] = MapRoomsTypes.BossRoom;
                } else {
                    types[i][j] = MapRoomsTypes.CommonRoom;
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.end();
        float cellWidth = getWidth() / types[0].length;
        float cellHeight = getHeight() / types.length;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < types[i].length; j++) {
                switch (types[i][j]) {
                    case ShopRoom:
                        shapeRenderer.setColor(Color.GREEN);
                        break;
                    case BossRoom:
                        shapeRenderer.setColor(Color.RED);
                        break;
                    case CommonRoom:
                        shapeRenderer.setColor(Color.DARK_GRAY);
                        break;
                    case Nothing:
                    default:
                        shapeRenderer.setColor(Color.BLACK);
                        break;
                }

                shapeRenderer.rect(getX() + j * cellWidth, getY() + i * cellHeight, cellWidth, cellHeight);
            }
        }

        shapeRenderer.end();
        batch.begin();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        super.dispose();
    }
}
