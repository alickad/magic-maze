package cz.cuni.mff.domanyoa.magicmaze.model;

import java.util.*;

import static java.lang.Math.max;

/// Class generating and holding the playing board.
public class Board {
    private int BOARD_HEIGHT;
    private int BOARD_WIDTH;
    private Tile[][] board;

    /// Constructor that generates a random board of tiles with walls with given size.
    /// @param board_height height of board
    /// @param board_width width of board
    public Board(int board_height, int board_width) {
        BOARD_HEIGHT = board_height;
        BOARD_WIDTH = board_width;

        boolean[][] boardVisited  = new boolean[BOARD_HEIGHT][BOARD_WIDTH];
        boolean[][] verticalWallVisited = new boolean[BOARD_HEIGHT+1][BOARD_WIDTH+1];
        boolean[][] horizontalWallVisited = new boolean[BOARD_HEIGHT+1][BOARD_WIDTH+1];
        Stack<int[]> dfsStack = new Stack<>();
        dfsStack.add(new int[]{1, 1});
        boardVisited[1][1] = true;

        int[][] dir0 = {{0,-1}, {-1,0}, {1,0}, {0,1}};
        List<int[]> dir = Arrays.asList(dir0);
        while (!dfsStack.isEmpty()){
            int[] pos = dfsStack.pop();
            int x = pos[1];
            int y = pos[0];
            Collections.shuffle(dir);
            for (int i = 0; i< 4; i++){
                int[] d = dir.get(i);
                int newX = x + d[1];
                int newY = y + d[0];
                if (newX < 0 || newX >= BOARD_WIDTH || newY < 0 || newY >= BOARD_HEIGHT){continue;}
                if (boardVisited[newY][newX]){continue;}
                if (d[0] == 0) verticalWallVisited[max(y, newY)][max(x, newX)] = true;
                if (d[1] == 0) horizontalWallVisited[max(y, newY)][max(x, newX)] = true;
                boardVisited[newY][newX] = true;
                dfsStack.add(new int[]{newY, newX});
            }
            System.out.println("Building the board");
        }
        for (int i = 0; i < BOARD_HEIGHT*BOARD_WIDTH/7; i++) {
            Random rand = new Random();
            int randY = rand.nextInt(BOARD_HEIGHT);
            int randX = rand.nextInt(BOARD_WIDTH);
            verticalWallVisited[randY][randX] = true;
            randY = rand.nextInt(BOARD_HEIGHT);
            randX = rand.nextInt(BOARD_WIDTH);
            horizontalWallVisited[randY][randX] = true;
        }
        board = new Tile[BOARD_HEIGHT][BOARD_WIDTH];
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                board[i][j] = new Tile(i, j, !horizontalWallVisited[i][j], !horizontalWallVisited[i+1][j],
                                            !verticalWallVisited[i][j], !verticalWallVisited[i][j + 1]);
            }
            System.out.println("Board has been built");
        }
    }

    /// Returns a Tile from given coordinates on playing board.
    /// @param y the y-coordinate (vertical)
    /// @param x the x-coordinate (horizontal)
    /// @return tile on coordinates x,y on the board
    public Tile tileAt(int y, int x) {
        return board[y][x];  // board[row][col] = board[y][x]
    }

    /// Returns the width of playing board.
    /// @return width of board
    public int width(){
        return BOARD_WIDTH;
    }
    /// Returns the height of the playing board.
    /// @return height of board
    public int height(){
        return BOARD_HEIGHT;
    }


}
