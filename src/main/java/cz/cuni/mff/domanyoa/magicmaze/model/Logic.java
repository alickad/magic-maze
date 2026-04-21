package cz.cuni.mff.domanyoa.magicmaze.model;

import java.util.*;

/// Class that takes all the logic of the game into one place.
public class Logic {
    private int BOARD_HEIGHT = 20;
    private int BOARD_WIDTH = 20;
    Board board = new Board(BOARD_HEIGHT, BOARD_WIDTH);
    List<Hero> heroes;
    List<Exit> exits;
    List<TimeReset> timeResets;

    /// Constructor based on heroes. Mainly for debugging.
    /// @param heroes List of heroes
    public Logic(List<Hero> heroes) {
        this.heroes = heroes;
        // Simple board initialization for now - all tiles are walkable
        initializeSimpleBoard();
        
        // Mark initial hero positions as occupied
        for (Hero hero : heroes) {
            board.tileAt(hero.getX(), hero.getY()).setOccupied(true);
        }
    }

    /// Constructor from heroes and board size. Generates a random board.
    /// @param heroes list of heroes
    /// @param board_width the width of playing board
    /// @param board_height the height of playing board
    public Logic(List<Hero> heroes, int board_height, int board_width) {
        this.BOARD_WIDTH = board_width;
        this.BOARD_HEIGHT = board_height;
        this.heroes = heroes;
        generateBoard();
        // Mark initial hero positions as occupied
        for (Hero hero : heroes) {
            board.tileAt(hero.getX(), hero.getY()).setOccupied(true);
        }
    }

    /// This is a simple example of a board. Used mainly for debugging.
    private void initializeSimpleBoard() {
        Exit exit1 = new Exit(15, 4);
        Exit exit2 = new Exit(5,10);
        Exit exit3 = new Exit(4, 5);
        Exit exit4 = new Exit(10, 3);
        exits = Arrays.asList(exit1, exit2, exit3, exit4);
        // placeholder

        TimeReset timeReset1 = new TimeReset(1, 1);
        TimeReset timeReset2 = new TimeReset(18, 18);
        timeResets =  Arrays.asList(timeReset1, timeReset2);
        // dalsi placeholder
    }
    /// This generates a board ???
    private void generateBoard() {
        Random rand = new Random();
        int randomNum = rand.nextInt(4);
        int[][] directions = {{1,1}, {1,-1}, {-1,1}, {-1,-1}};
        boolean[][] visited = new boolean[BOARD_HEIGHT][BOARD_WIDTH];
        Queue<int[]> bfsQueue = new LinkedList<>();
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                visited[i][j] = false;
                for (Hero h : heroes){
                    visited[h.getY()][h.getX()] = true;
                    bfsQueue.add(new int[]{h.getY(), h.getX()});
                }
            }
        }

    }

    /// True iff hero can move in given direction based on logic.
    /// @param d direction
    /// @param hero hero
    /// @return true iff hero can move in direction d
    public boolean canMove(Hero hero, Direction d){
        int x = hero.getX();
        int y = hero.getY();
        switch (d){
            case UP -> {
                if (y <= 0 || board.tileAt(y,x).isWall(Direction.UP)) return false;
                if (board.tileAt(y-1, x).isOccupied()) return false;
                if (board.tileAt(y-1,x).isWall(Direction.DOWN)) return false;
                return true;
            }
            case DOWN -> {
                if (y >= BOARD_HEIGHT-1 || board.tileAt(y,x).isWall(Direction.DOWN)) return false;
                if (board.tileAt(y + 1, x).isOccupied()) return false;
                if (board.tileAt(y + 1, x).isWall(Direction.UP))  return false;
                return true;
            }
            case LEFT -> {
                if (x <= 0 || board.tileAt(y,x).isWall(Direction.LEFT)) return false;
                if (board.tileAt(y, x-1).isOccupied()) return false;
                if (board.tileAt(y, x-1).isWall(Direction.RIGHT))  return false;
                return true;
            }
            case RIGHT -> {
                if (x >= BOARD_WIDTH-1 || board.tileAt(y,x).isWall(Direction.RIGHT)) return false;
                if (board.tileAt(y, x+1).isOccupied()) return false;
                if (board.tileAt(y, x+1).isWall(Direction.LEFT))  return false;
                return true;
            }
        }
        return false;
    }

    /// If the hero can be moved in a given direction, move it.
    /// @param hero hero
    /// @param d direction
    public void move(Hero hero, Direction d){
        if (!canMove(hero, d)) return;
        int x = hero.getX();
        int y = hero.getY();
        System.out.println("Moving from (" + y + "," + x + ") direction " + d);
        System.out.println("Tile before: occupied=" + board.tileAt(y,x).isOccupied());
        switch (d) {
            case UP -> {
                board.tileAt(y,x).setOccupied(false);
                hero.move(Direction.UP);
                board.tileAt(hero.getY(), hero.getX()).setOccupied(true);
                System.out.println("New position (" + hero.getX() + "," + hero.getY() + ") occupied=" + board.tileAt(hero.getX(),hero.getY()).isOccupied());
            }
            case DOWN -> {
                board.tileAt(y,x).setOccupied(false);
                hero.move(Direction.DOWN);
                board.tileAt(hero.getY(), hero.getX()).setOccupied(true);
            }
            case LEFT -> {
                board.tileAt(y,x).setOccupied(false);
                hero.move(Direction.LEFT);
                board.tileAt(hero.getY(), hero.getX()).setOccupied(true);
            }
            case RIGHT -> {
                board.tileAt(y,x).setOccupied(false);
                hero.move(Direction.RIGHT);
                board.tileAt(hero.getY(), hero.getX()).setOccupied(true);
            }
        }
    }

    /// True iff the game has ended (all missions are complete or the time is out).
    /// @return boolean, true iff the game has ended
    public boolean gameEndedCheck() {
        for (int i = 0; i < 4; i++){
            Hero h = heroes.get(i);
            Exit e =  exits.get(i);
            if (h.getX() != e.getX() || h.getY() != e.getY()) return false;
        }
        return true;
    }

    /// True iff there is a hero standing on an active time resetter.
    /// @return boolean, true iff some hero is standing on an active time-resetter
    public boolean timeResetsCheck() {
        for (Hero h : heroes) {
            for  (TimeReset t : timeResets) {
                if (t.isActive() && h.getX() == t.getX() && h.getY() == t.getY()) return true;
            }
        }
        return false;
    }

    /// Get the time resetter object some hero is standing on.
    /// @return a time resetter, on which is a hero standing.
    public TimeReset getTimeReset() {
        for (Hero h : heroes) {
            for  (TimeReset t : timeResets) {
                if (t.isActive() && h.getX() == t.getX() && h.getY() == t.getY()){
                    return t;
                }
            }
        }
        return null;
    }

    /// After the resetter has been used, it should be disabled.
    /// @param timeReset the timeReset to be disabled
    public void disableReset(TimeReset timeReset) {
        timeReset.disable();
    }

    /// Get list of all heroes.
    /// @return list of all heroes
    public List<Hero> getHeroes() {
        return heroes;
    }

    /// Get the board.
    /// @return the playing board
    public Board getBoard() {
        return board;
    }

    /// get list of all exits.
    /// @return list of all exits
    public List<Exit> getExits() {return exits;}

    /// get list of all time resets
    /// @return list of all time resetters
    public List<TimeReset> getTimeResets() {return timeResets;}

    /// remove time reset from logic
    /// @param timeReset timeReset to be removed
    public void removeReset(TimeReset timeReset) {
        timeResets.remove(timeReset);
    }
}
