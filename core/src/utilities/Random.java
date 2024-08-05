package utilities;

public final class Random {
    private final java.util.Random r;

    public Random() {
        r = new java.util.Random();
    }

    public int nextInt(int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("The 'from' parameter must be less than or equal to the 'to' parameter.");
        }
        return r.nextInt(to - from + 1) + from;
    }

    public int nextInt(int n) {
        return r.nextInt(n);
    }
}
