package cz.cuni.mff.domanyoa.magicmaze.model;

/// Class holding a time-resetter
public class TimeReset {
    private int x;
    private int y;
    private boolean active;

    /// Constructor.
    /// @param y the y-coordinate (vertical)
    /// @param x the x-coordinate (horizontal)
    public TimeReset(int y, int x) {
        this.x = x;
        this.y = y;
        active = true;
    }
    /// Get the x-coordinate (horizontal).
    /// @return x-coordinate
    public int getX() {
        return x;
    }
    /// Get the y-coordinate (vertical)
    /// @return y-coordinate
    public int getY() {
        return y;
    }
    /// Return true iff time resetter is active (it has not been used).
    /// @return bool, true iff time resetter has not been used (is active)
    public boolean isActive() { return active; }
    /// Set time resetter not active (it has been used).
    public void disable() { active = false; }
}
