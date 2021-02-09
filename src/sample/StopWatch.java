package sample;

public class StopWatch {

    protected long startTime = 0;
    protected long stopTime = 0;
    private boolean running = false;

    public void start() {
        startTime = System.currentTimeMillis();
        running = true;
    }

    public long getElapsedTime() {
        long elapsed;
        if (running) {
            elapsed = System.currentTimeMillis() - startTime;
        } else {
            elapsed = stopTime - startTime;
        }
        return elapsed;
    }
}