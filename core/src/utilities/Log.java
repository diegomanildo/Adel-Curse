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

    private static int messageIndex = 1;

    private static void print(Colors color, String prefix, String msg) {
        System.out.println("" + color + messageIndex++ + " [" + prefix + "] " + msg + Colors.RESET);
    }

    public static void log(String msg) {
        print(Colors.GREEN, "LOG", msg);
    }

    public static void debug(String msg) {
        if (Render.isDebugging()) {
            print(Colors.BLUE, "DEBUG", msg);
        }
    }

    public static void error(String msg) {
        print(Colors.RED, "ERROR", msg);
    }
}
