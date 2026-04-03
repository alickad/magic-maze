package cz.cuni.mff.domanyoa.magicmaze.model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Board {
    private int BOARD_HEIGHT;
    private int BOARD_WIDTH;
    private Tile[][] board;

    public Board(int board_height, int board_width) {
        BOARD_HEIGHT = board_height;
        BOARD_WIDTH = board_width;
        boolean[][] boardVisited  = new boolean[BOARD_HEIGHT][BOARD_WIDTH];
        boolean[][] verticalWallVisited = new boolean[BOARD_HEIGHT+1][BOARD_WIDTH+1];
        boolean[][] horizontalWallVisited = new boolean[BOARD_HEIGHT+1][BOARD_WIDTH+1];
        Queue<int[]> bfsQueue = new LinkedList<>();
        int[][] initPos = {{(BOARD_HEIGHT-1)/2, (BOARD_WIDTH-1)/2},
                                    {(BOARD_HEIGHT-1)/2, (BOARD_WIDTH+1)/2},
                                    {(BOARD_HEIGHT+1)/2, (BOARD_WIDTH-1)/2},
                                    {(BOARD_HEIGHT+1)/2, (BOARD_WIDTH+1)/2} };
        for (int i = 0; i < 4; i++){
            bfsQueue.add(new int[]{initPos[i][0], initPos[i][1]});
            boardVisited[initPos[i][0]][initPos[i][1]] = true;
        }
        Random rand = new Random();
        int[][] dir = {{0,-1}, {-1,0}, {1,0}, {0,1}};
        for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH && !bfsQueue.isEmpty(); i++){    // TODO: test the constant
            int[] pos = bfsQueue.poll();
            int x = pos[1];
            int y = pos[0];
            int[] d =  dir[rand.nextInt(4)];
            int newX = x + d[1];
            int newY = y + d[0];
            if (d[0] == 0) verticalWallVisited[(y+newY)/2][(x+newX)/2] = true;
            if (d[1] == 0) horizontalWallVisited[(y+newY)/2][(x+newX)/2] = true;
            if (!boardVisited[newY][newX]){
                boardVisited[newY][newX] = true;
                bfsQueue.add(new int[]{newY, newX});
            }
        }

        board = new Tile[BOARD_HEIGHT][BOARD_WIDTH];
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                board[i][j] = new Tile(i, j, !horizontalWallVisited[i][j], !horizontalWallVisited[i+1][j],
                                            !verticalWallVisited[i][j], !verticalWallVisited[i][j + 1]);
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
