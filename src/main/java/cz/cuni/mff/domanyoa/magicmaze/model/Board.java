package cz.cuni.mff.domanyoa.magicmaze.model;

public class Board {
    private int BOARD_HEIGHT;
    private int BOARD_WIDTH;
    private Tile[][] board;

    public Board(int board_height, int board_width) {
        BOARD_HEIGHT = board_height;
        BOARD_WIDTH = board_width;
        board = new Tile[BOARD_HEIGHT][BOARD_WIDTH];
        // board tiles initialization
    }

    Tile tileAt(int x, int y) {
        return board[x][y];
    }


}
