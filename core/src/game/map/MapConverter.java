package game.map;

import game.rooms.BossRoom;
import game.rooms.EnemyRoom;
import game.rooms.Room;
import game.rooms.ShopRoom;

import java.util.Hashtable;
import java.util.Map;

public class MapConverter {
    private static final String SP1_C = "__";
    private static final String SP2_C = "\n";
    private static Hashtable<Class<? extends Room>, String> dictionary;

    private static void createDictionary() {
        dictionary = new Hashtable<>();
        dictionary.put(BossRoom.class, "B");
        dictionary.put(EnemyRoom.class, "E");
        dictionary.put(ShopRoom.class, "S");
    }

    private static String get(Class<? extends Room> clazz) {
        if (dictionary == null) {
            createDictionary();
        }

        if (dictionary.containsKey(clazz)) {
            return dictionary.get(clazz);
        } else {
            throw new RuntimeException("Invalid room: " + clazz);
        }
    }

    private static Class<? extends Room> get(String character) {
        if (dictionary == null) {
            createDictionary();
        }

        for (Map.Entry<Class<? extends Room>, String> entry : dictionary.entrySet()) {
            if (entry.getValue().equals(character)) {
                return entry.getKey();
            }
        }

        throw new RuntimeException("Invalid character: \"" + character + "\"");
    }

    public static String[][] convertToString(Room[][] roomsMap) {
        String[][] map = new String[roomsMap.length][roomsMap[0].length];

        for (int row = 0; row < roomsMap.length; row++) {
            for (int column = 0; column < roomsMap[row].length; column++) {
                Room room = roomsMap[row][column];
                String traducedCharacter = room == null ? "" : get(room.getClass());
                map[row][column] = traducedCharacter;
            }
        }

        return map;
    }

    public static Room[][] convertToMap(String[][] stringMap) {
        Room[][] roomsMap = new Room[stringMap.length][stringMap[0].length];

        for (int row = 0; row < roomsMap.length; row++) {
            for (int column = 0; column < roomsMap[row].length; column++) {
                String character = stringMap[row][column];
                Room room;
                Class<? extends Room> roomClass = get(character);

                try {
                    room = roomClass == null ? null : roomClass.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                roomsMap[row][column] = room;
            }
        }

        return roomsMap;
    }
}
