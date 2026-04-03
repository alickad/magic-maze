package cz.cuni.mff.domanyoa.magicmaze.model;

import java.util.*;

public class Logic {
    private int BOARD_HEIGHT = 20;
    private int BOARD_WIDTH = 20;
    Board board = new Board(BOARD_HEIGHT, BOARD_WIDTH);
    List<Hero> heroes;
    List<Exit> exits;
    List<TimeReset> timeResets;
    
    public Logic(List<Hero> heroes) {
        this.heroes = heroes;
        // Simple board initialization for now - all tiles are walkable
        initializeSimpleBoard();
        
        // Mark initial hero positions as occupied
        for (Hero hero : heroes) {
            board.tileAt(hero.getX(), hero.getY()).setOccupied(true);
        }
    }
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

    public boolean canMove(Hero hero, Direction d){
        int x = hero.getX();
        int y = hero.getY();
        switch (d){
            case UP -> {
                if (y <= 0 || board.tileAt(y,x).isWall(Direction.UP)) return false;
                if (board.tileAt(y-1,x).isEmpty() || board.tileAt(y-1, x).isOccupied()) return false;
                if (board.tileAt(y-1,x).isWall(Direction.DOWN)) return false;
                return true;
            }
            case DOWN -> {
                if (y >= BOARD_HEIGHT-1 || board.tileAt(y,x).isWall(Direction.DOWN)) return false;
                if (board.tileAt(y + 1, x).isEmpty() || board.tileAt(y + 1, x).isOccupied()) return false;
                if (board.tileAt(y + 1, x).isWall(Direction.UP))  return false;
                return true;
            }
            case LEFT -> {
                if (x <= 0 || board.tileAt(y,x).isWall(Direction.LEFT)) return false;
                if (board.tileAt(y, x-1).isEmpty() || board.tileAt(y, x-1).isOccupied()) return false;
                if (board.tileAt(y, x-1).isWall(Direction.RIGHT))  return false;
                return true;
            }
            case RIGHT -> {
                if (x >= BOARD_WIDTH-1 || board.tileAt(y,x).isWall(Direction.RIGHT)) return false;
                if (board.tileAt(y, x+1).isEmpty() || board.tileAt(y, x+1).isOccupied()) return false;
                if (board.tileAt(y, x+1).isWall(Direction.LEFT))  return false;
                return true;
            }
        }
        return false;
    }
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

    public boolean gameEndedCheck() {
        for (int i = 0; i < 4; i++){
            Hero h = heroes.get(i);
            Exit e =  exits.get(i);
            if (h.getX() != e.getX() || h.getY() != e.getY()) return false;
        }
        return true;
    }

    public boolean timeResetsCheck() {
        for (Hero h : heroes) {
            for  (TimeReset t : timeResets) {
                if (t.isActive() && h.getX() == t.getX() && h.getY() == t.getY()) return true;
            }
        }
        return false;
    }
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
    public void disableReset(TimeReset timeReset) {
        timeReset.disable();
    }

    public List<Hero> getHeroes() {
        return heroes;
    }
    public Board getBoard() {
        return board;
    }
    public List<Exit> getExits() {return exits;}
    public List<TimeReset> getTimeResets() {return timeResets;}
    public void removeReset(TimeReset timeReset) {
        timeResets.remove(timeReset);
    }
}
