package utilities;

public class Timer {
    private long startTime;
    private long endTime;
    private boolean running;

    public Timer() {
        this.running = false;
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    public void stop() {
        this.endTime = System.currentTimeMillis();
        this.running = false;
    }

    public void reset() {
        this.running = false;
        this.startTime = 0L;
        this.endTime = 0L;
    }

    // Get milliseconds elapsed
    public long getElapsedTime() {
        if (running) {
            return System.currentTimeMillis() - startTime;
        } else {
            return endTime - startTime;
        }
    }

    public long getSeconds() {
        return getElapsedTime() / 1000L;
    }

    public long getMinutes() {
        return getSeconds() / 60L;
    }

    public long getHours() {
        return getMinutes() / 60L;
    }

    public boolean isRunning() {
        return this.running;
    }
}
