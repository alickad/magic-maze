package cz.cuni.mff.domanyoa.magicmaze.model;

public class TimeReset {
    private int x;
    private int y;
    private boolean active;
    public TimeReset(int x, int y) {
        this.x = x;
        this.y = y;
        active = true;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean isActive() { return active; }
    public void disable() { active = false; }
}
