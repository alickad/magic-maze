package cz.cuni.mff.domanyoa.magicmaze.model;

/// A logical class holding coordinates of an exit.
public class Exit {
    private int x;
    private int y;

    /// Contructor of the class.
    /// @param y the y-coordinate (vertical) of exit on board
    /// @param x the x-coordinate (horizontal) of exit on board
    public Exit(int y, int x) {
        this.x = x;
        this.y = y;
    }
    /// Get x-coordinate (horizontal) of exit.
    public int getX() {
        return x;
    }
    /// Get y-coordinate (vertical) of exit.
    public int getY() {
        return y;
    }

}
