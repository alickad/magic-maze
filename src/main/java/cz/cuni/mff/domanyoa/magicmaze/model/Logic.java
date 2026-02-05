package cz.cuni.mff.domanyoa.magicmaze.model;

import java.util.ArrayList;
import java.util.List;

public class Logic {
    public final int BOARD_HEIGHT = 50;
    public final int BOARD_WIDTH = 50;
    Board board = new Board(BOARD_HEIGHT, BOARD_WIDTH);
    List<Hero> heroes;
    
    public Logic() {
        // Simple board initialization for now - all tiles are walkable
        initializeSimpleBoard();
    }

    private void initializeSimpleBoard() {
        // TODO: Implement proper board with tiles and walls
        // For now, this is a placeholder for future tile setup
    }

    public void initializeHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    public boolean canMove(Hero hero, Direction d){
        int x = hero.getX();
        int y = hero.getY();
        switch (d){
            case UP -> {
                if (y <= 0 || board.tileAt(x,y).isWall(Direction.UP)) return false;
                if (board.tileAt(x,y-1).isEmpty() || board.tileAt(x, y-1).isOccupied()) return false;
                if (board.tileAt(x, y-1).isWall(Direction.DOWN))  return false;
                return true;
            }
            case DOWN -> {
                if (y >= BOARD_HEIGHT-1 || board.tileAt(x,y).isWall(Direction.DOWN)) return false;
                if (board.tileAt(x,y+1).isEmpty() || board.tileAt(x, y+1).isOccupied()) return false;
                if (board.tileAt(x, y+1).isWall(Direction.UP))  return false;
                return true;
            }
            case LEFT -> {
                if (x <= 0 || board.tileAt(x,y).isWall(Direction.LEFT)) return false;
                if (board.tileAt(x-1, y).isEmpty() || board.tileAt(x-1, y).isOccupied()) return false;
                if (board.tileAt(x - 1, y).isWall(Direction.RIGHT))  return false;
                return true;
            }
            case RIGHT -> {
                if (x >= BOARD_WIDTH-1 || board.tileAt(x, y).isWall(Direction.RIGHT)) return false;
                if (board.tileAt(x + 1,  y).isEmpty() || board.tileAt(x + 1, y).isOccupied()) return false;
                if (board.tileAt(x + 1, y).isWall(Direction.LEFT))  return false;
                return true;
            }
        }
        return false;
    }
    public void move(Hero hero, Direction d){
        if (!canMove(hero, d)) return;
        int x = hero.getX();
        int y = hero.getY();
        switch (d) {
            case UP -> {
                hero.move(Direction.UP);
                board.tileAt(x,y).setOccupied(false);
                board.tileAt(x,y - 1).setOccupied(true);
            }
            case DOWN -> {
                hero.move(Direction.DOWN);
                board.tileAt(x,y).setOccupied(false);
                board.tileAt(x,y + 1).setOccupied(true);
            }
            case LEFT -> {
                hero.move(Direction.LEFT);
                board.tileAt(x,y).setOccupied(false);
                board.tileAt(x-1,y).setOccupied(true);
            }
            case RIGHT -> {
                hero.move(Direction.RIGHT);
                board.tileAt(x,y).setOccupied(false);
                board.tileAt(x+1,  y).setOccupied(true);
            }
        }
    }
}
