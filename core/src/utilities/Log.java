package utilities;

public class Log {
    private enum Colors {
        BLACK("\u001B[30m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        PURPLE("\u001B[35m"),
        CYAN("\u001B[36m"),
        WHITE("\u001B[37m"),
        RESET("\u001B[0m");

        private final String ansi;

        Colors(String ansi) {
            this.ansi = ansi;
        }

        @Override
        public String toString() {
            return ansi;
        }
    }

    public static String logPrefix = "LOG";
    public static String debugPrefix = "DEBUG";
    public static String errorPrefix = "ERROR";

    private static void print(Colors color, String prefix, String msg) {
        System.out.println(color + "[" + prefix + "] " + msg + Colors.RESET);
    }

    public static void log(String msg) {
        print(Colors.GREEN, logPrefix, msg);
    }

    public static void debug(String msg) {
        print(Colors.BLUE, debugPrefix, msg);
    }

    public static void error(String msg) {
        print(Colors.RED, errorPrefix, msg);
    }
}
