package cz.cuni.mff.domanyoa.magicmaze.model;

public class Board {
    private int BOARD_HEIGHT;
    private int BOARD_WIDTH;
    private Tile[][] board;

    public Board(int board_height, int board_width) {
        BOARD_HEIGHT = board_height;
        BOARD_WIDTH = board_width;
        board = new Tile[BOARD_HEIGHT][BOARD_WIDTH];
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                board[i][j] = new Tile(i, j);
            }
        }
    }

    Tile tileAt(int y, int x) {
        return board[y][x];  // board[row][col] = board[y][x]
    }

    public int width(){
        return BOARD_WIDTH;
    }
    public int height(){
        return BOARD_HEIGHT;
    }


}
