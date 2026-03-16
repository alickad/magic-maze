package cz.cuni.mff.domanyoa.magicmaze.model;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Hero {
    private Color color;
    private int x,y;
    private KeyCode moveUpKey;
    private KeyCode moveDownKey;
    private KeyCode moveLeftKey;
    private KeyCode moveRightKey;
    public Hero(Color color, int x, int y, KeyCode moveUp, KeyCode moveDown, KeyCode moveLeft, KeyCode moveRight) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.moveUpKey = moveUp;
        this.moveDownKey = moveDown;
        this.moveLeftKey = moveLeft;
        this.moveRightKey = moveRight;
    }
    public Hero(){
        this.color = Color.BLUE;
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

    public Color getColor() {return color;}
    public KeyCode getUP() {return moveUpKey;}
    public KeyCode getDOWN() {return moveDownKey;}
    public KeyCode getLEFT() {return moveLeftKey;}
    public KeyCode getRIGHT() {return moveRightKey;}

    public void setUP(KeyCode code){this.moveUpKey = code;}
    public void setDOWN(KeyCode code){this.moveDownKey = code;}
    public void setLEFT(KeyCode code){this.moveLeftKey = code;}
    public void setRIGHT(KeyCode code){this.moveRightKey = code;}
}
