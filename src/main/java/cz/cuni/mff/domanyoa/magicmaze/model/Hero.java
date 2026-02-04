package cz.cuni.mff.domanyoa.magicmaze.model;

import javafx.scene.input.KeyCode;

public class Hero {
    private String name;
    private int x,y;
    private KeyCode moveUpKey;
    private KeyCode moveDownKey;
    private KeyCode moveLeftKey;
    private KeyCode moveRightKey;
    public Hero(String name, int x, int y, KeyCode moveUp, KeyCode moveDown, KeyCode moveLeft, KeyCode moveRight) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.moveUpKey = moveUp;
        this.moveDownKey = moveDown;
        this.moveLeftKey = moveLeft;
        this.moveRightKey = moveRight;
    }
    public Hero(){
        this.name = "blue";
        this.x = 100;
        this.y = 100;
        this.moveUpKey = KeyCode.UP;
        this.moveDownKey = KeyCode.DOWN;
        this.moveLeftKey = KeyCode.LEFT;
        this.moveRightKey = KeyCode.RIGHT;
    }

    public int getX() {return x;}
    public int getY() {return y;}

    public void move(Direction d) {
         switch (d) {
             case UP -> this.y -= 1;
             case DOWN -> this.y += 1;
             case LEFT -> this.x -= 1;
             case RIGHT -> this.x += 1;
         }
    }

    public void setKey(Direction d, KeyCode k) {
        switch (d) {
            case UP -> this.moveUpKey = k;
            case DOWN -> this.moveDownKey = k;
            case LEFT -> this.moveLeftKey = k;
            case RIGHT -> this.moveRightKey = k;
        }
    }
}
