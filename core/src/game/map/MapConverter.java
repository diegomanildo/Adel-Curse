package game.map;

import game.rooms.*;

public class MapConverter {
    private static String flat(String[][] stringMap) {
        StringBuilder builder = new StringBuilder();

        for (int row = 0; row < stringMap.length; row++) {
            for (int column = 0; column < stringMap[row].length; column++) {
                builder.append(stringMap[row][column] == null ? " " : stringMap[row][column]);
                if (column < stringMap[row].length - 1) {
                    builder.append(",");
                }
            }
            if (row < stringMap.length - 1) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    private static String[][] unflat(String flatString) {
        String[] rows = flatString.split("\n");
        int numRows = rows.length;
        String[][] stringMap = new String[numRows][];

        for (int row = 0; row < numRows; row++) {
            stringMap[row] = rows[row].split(",");
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
                } else if (room instanceof BossRoom) {
                    traducedCharacter = "B";
                } else if (room instanceof ShopRoom) {
                    traducedCharacter = "S";
                } else if (room instanceof StoneRoom) {
                    traducedCharacter = "I";
                } else if (room instanceof EnemyRoom) {
                    traducedCharacter = "E";
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
        Room[][] roomsMap = new Room[stringMap.length][maxLength(stringMap)];

        for (int row = 0; row < roomsMap.length; row++) {
            for (int column = 0; column < roomsMap[row].length; column++) {
                String character = stringMap[row][column];
                Class<? extends Room> roomClass;

                switch (character) {
                    case " ":
                        roomClass = null;
                        break;
                    case "B":
                        roomClass = BossRoom.class;
                        break;
                    case "S":
                        roomClass = ShopRoom.class;
                        break;
                    case "I":
                        roomClass = StoneRoom1.class;
                        break;
                    case "E":
                        roomClass = EnemyRoom.class;
                        break;
                    default:
                        throw new RuntimeException("Unexpected character: \"" + character + "\"");
                }

                try {
                    roomsMap[row][column] = roomClass == null ? null : roomClass.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return roomsMap;
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
