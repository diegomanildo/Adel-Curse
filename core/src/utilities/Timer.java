package utilities;

public class Timer {
    private long startTime;
    private long endTime;
    private long pauseTime;
    private boolean running;
    private boolean paused;

    // Constructor
    public Timer() {
        reset();
    }

    public void start() {
        if (!running) {
            startTime = System.currentTimeMillis();
            running = true;
            paused = false;
        }
    }

    public void stop() {
        if (running) {
            endTime = System.currentTimeMillis();
            running = false;
        }
    }

    public void reset() {
        running = false;
        paused = false;
        startTime = 0L;
        endTime = 0L;
        pauseTime = 0L;
    }

    public void pause() {
        if (running && !paused) {
            this.pauseTime = System.currentTimeMillis();
            this.paused = true;
        }
    }

    public void resume() {
        if (running && paused) {
            startTime += (System.currentTimeMillis() - pauseTime);
            paused = false;
        }
    }

    public long getElapsedTime() {
        if (running) {
            if (paused) {
                return pauseTime - startTime;
            } else {
                return System.currentTimeMillis() - startTime;
            }
        } else {
            return endTime - startTime;
        }

    }

    public float getSeconds() {
        return (getElapsedTime() % 60000f) / 1000f;
    }

    public float getMinutes() {
        return (getElapsedTime() % 3600000f) / 60000f;
    }

    public float getHours() {
        return getElapsedTime() / 3600000f;
    }

    public boolean isRunning() {
        return this.running;
    }

    public boolean isPaused() {
        return this.paused;
    }
}

