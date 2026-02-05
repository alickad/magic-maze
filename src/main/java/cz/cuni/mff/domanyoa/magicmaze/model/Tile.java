package cz.cuni.mff.domanyoa.magicmaze.model;

public class Tile {
    private int x, y;
    private boolean occupied;
    private boolean upWall;
    private boolean downWall = false;
    private boolean leftWall = false;
    private boolean rightWall = false;
    private boolean isEmpty = false;

    public Tile(int x, int y, boolean upWall, boolean downWall, boolean leftWall, boolean rightWall) {
        occupied = false;
        this.x = x;
        this.y = y;
        this.upWall = upWall;
        this.downWall = downWall;
        this.leftWall = leftWall;
        this.rightWall = rightWall;
    }
    public Tile(int x, int y) {
        occupied = false;
        this.x = x;
        this.y = y;
    }

    public boolean isEmpty() {return isEmpty;}
    public boolean isOccupied() {return occupied;}
    public void setOccupied(boolean b) {occupied = b;}
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
