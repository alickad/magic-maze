package cz.cuni.mff.domanyoa.magicmaze.model;

/// Class of logic representing a single tile on the board.
public class Tile {
    private int x, y;
    private boolean occupied;
    private boolean upWall;
    private boolean downWall = false;
    private boolean leftWall = false;
    private boolean rightWall = false;

    /// Constructor of tile on board.
    /// @param x the x-coordinate (horizontal) on the board
    /// @param y the y-coordinate (vertical) on the board
    /// @param upWall true, iff there is a wall in direction UP
    /// @param downWall true, iff there is a wall in direction DOWN
    /// @param leftWall true, iff there is a wall in direction LEFT
    /// @param rightWall true, iff there is a wall in direction RIGHT
    public Tile(int y, int x, boolean upWall, boolean downWall, boolean leftWall, boolean rightWall) {
        occupied = false;
        this.x = x;
        this.y = y;
        this.upWall = upWall;
        this.downWall = downWall;
        this.leftWall = leftWall;
        this.rightWall = rightWall;
    }

    /// A simple constructor mainly for debugging.
    public Tile(int x, int y) {
        occupied = false;
        this.x = x;
        this.y = y;
    }

    /// Return true iff there is a hero on this tile.
    public boolean isOccupied() {return occupied;}
    /// Set the tile occupied (there is a hero standing).
    public void setOccupied(boolean b) {occupied = b;}
    ///  Return true iff there is a wall in Diretion d.
    /// @param d the direction in question
    public boolean isWall(Direction d){
        switch (d){
            case UP -> {return upWall;}
            case DOWN -> {return downWall;}
            case LEFT -> {return leftWall;}
            case RIGHT -> {return rightWall;}
        }
        return true;
    }
}
