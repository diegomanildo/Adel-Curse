package game.map;

import game.rooms.BossRoom;
import game.rooms.Room;
import game.rooms.ShopRoom;
import game.rooms.StoneRoom;

public class MapHelper {
    private static final String SPLIT_CHAR1 = ",";
    private static final String SPLIT_CHAR2 = "\n";
    private static final String SPLIT_CHAR3 = "__";

    private static String flat(String[][] stringMap) {
        StringBuilder builder = new StringBuilder();

        for (int row = 0; row < stringMap.length; row++) {
            for (int column = 0; column < stringMap[row].length; column++) {
                builder.append(stringMap[row][column] == null ? " " : stringMap[row][column]);
                if (column < stringMap[row].length - 1) {
                    builder.append(SPLIT_CHAR1);
                }
            }
            if (row < stringMap.length - 1) {
                builder.append(SPLIT_CHAR2);
            }
        }

        return builder.toString();
    }

    private static String[][] unflat(String flatString) {
        String[] rows = flatString.split(SPLIT_CHAR2);
        int numRows = rows.length;
        String[][] stringMap = new String[numRows][];

        for (int row = 0; row < numRows; row++) {
            stringMap[row] = rows[row].split(SPLIT_CHAR1);
        }

        return stringMap;
    }

    public static String convertToString(Room[][] roomsMap) {
        String[][] stringMap = new String[roomsMap.length][roomsMap[0].length];

        for (int row = 0; row < roomsMap.length; row++) {
            for (int column = 0; column < roomsMap[row].length; column++) {
                Room room = roomsMap[row][column];
                String traducedCharacter;

                if (room == null) {
                    traducedCharacter = " ";
                } else if (room.getKind() == RoomKinds.BOSS) {
                    traducedCharacter = "B";
                } else if (room.getKind() == RoomKinds.SHOP) {
                    traducedCharacter = "S";
                } else if (room.getKind() == RoomKinds.OTHER || room.getKind() == RoomKinds.CURRENT) {
                    traducedCharacter = "E" + SPLIT_CHAR3 + ((StoneRoom)room).getStyleNumber(); // Enemy Room
                } else {
                    throw new RuntimeException("Unexpected room " + room.getClass().getSimpleName());
                }

                stringMap[row][column] = traducedCharacter;
            }
        }

        return flat(stringMap);
    }

    public static Room[][] convertToMap(String map) {
        String[][] stringMap = unflat(map);

        int rows = stringMap.length;
        int columns = maxLength(stringMap);

        Room[][] roomsMap = new Room[rows][columns];

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < stringMap[row].length; column++) {
                char character = stringMap[row][column].charAt(0);
                Class<? extends Room> roomClass;

                switch (character) {
                    case ' ':
                        roomClass = null;
                        break;
                    case 'B':
                        roomClass = BossRoom.class;
                        break;
                    case 'S':
                        roomClass = ShopRoom.class;
                        break;
                    case 'E':
                        roomClass = StoneRoom.class;
                        break;
                    default:
                        throw new RuntimeException("Unexpected character: \"" + character + "\"");
                }

                try {
                    roomsMap[row][column] = roomClass == null ? null : roomClass.newInstance();

                    if (roomClass != null && roomClass.equals(StoneRoom.class)) {
                        String[] parts = stringMap[row][column].split(SPLIT_CHAR3);
                        int styleNumber = Integer.parseInt(parts[1]);

                        ((StoneRoom)roomsMap[row][column]).setStyleNumber(styleNumber);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        createDoorsForMap(roomsMap);

        return roomsMap;
    }

    public static void createDoorsForMap(Room[][] map) {
        int rows = map.length;
        int columns = map[0].length;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Room currentRoom = map[row][column];
                if (currentRoom == null) continue;

                Room leftRoom = (column > 0) ? map[row][column - 1] : null;
                Room rightRoom = (column < columns - 1) ? map[row][column + 1] : null;
                Room upRoom = (row > 0) ? map[row + 1][column] : null;
                Room downRoom = (row < rows - 1) ? map[row - 1][column] : null;

                currentRoom.createDoors(leftRoom, rightRoom, upRoom, downRoom);
            }
        }
    }

    private static int maxLength(String[][] stringMap) {
        int maxLength = 0;

        for (String[] row : stringMap) {
            if (row != null) {
                maxLength = Math.max(maxLength, row.length);
            }
        }

        return maxLength;
    }
}
