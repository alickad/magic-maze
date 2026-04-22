package cz.cuni.mff.domanyoa.magicmaze.model;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

/// Class that represents logic of a hero (one play piece).
public class Hero {
    private int x,y;
    private KeyCode moveUpKey;
    private KeyCode moveDownKey;
    private KeyCode moveLeftKey;
    private KeyCode moveRightKey;

    /// Constructor of a hero.
    /// @param x the x-coordinate (horizontal) of hero
    /// @param y the y-coordinate (vertical) of hero
    /// @param moveUp using which keypress can we move hero up
    /// @param moveDown using which keypress can we move hero down
    /// @param moveLeft using which keypress can we move hero left
    /// @param moveRight using which keypress can we move hero right
    public Hero(int y, int x, KeyCode moveUp, KeyCode moveDown, KeyCode moveLeft, KeyCode moveRight) {
        this.x = x;
        this.y = y;
        this.moveUpKey = moveUp;
        this.moveDownKey = moveDown;
        this.moveLeftKey = moveLeft;
        this.moveRightKey = moveRight;
    }

    /// A simple constructor used mainly for debugging.
    public Hero(int y, int x){
        this.x = x;
        this.y = y;
        this.moveUpKey = KeyCode.UP;
        this.moveDownKey = KeyCode.DOWN;
        this.moveLeftKey = KeyCode.LEFT;
        this.moveRightKey = KeyCode.RIGHT;
    }

    /// Get the x-coordinate (horizontal) of a hero
    /// @return x-coordinate
    public int getX() {return x;}
    /// Get the y-coordinate (vertical) of a hero
    /// @return y-coordinate
    public int getY() {return y;}

    ///  Move the hero, if it can be moved.
    /// @param d direction to move the hero
    public void move(Direction d) {
         switch (d) {
             case UP -> this.y -= 1;
             case DOWN -> this.y += 1;
             case LEFT -> this.x -= 1;
             case RIGHT -> this.x += 1;
         }
    }

    /// Set the code of keypress that moves a hero.
    /// @param d direction to set
    /// @param k the keypress code
    public void setKey(Direction d, KeyCode k) {
        switch (d) {
            case UP -> this.moveUpKey = k;
            case DOWN -> this.moveDownKey = k;
            case LEFT -> this.moveLeftKey = k;
            case RIGHT -> this.moveRightKey = k;
        }
    }

    /// Get the code of keypress that moves a hero.
    /// @param d the direction of movement
    /// @return key that moves the hero in direction d.
    public KeyCode getKey(Direction d) {
        switch (d) {
            case UP: return moveUpKey;
            case DOWN: return moveDownKey;
            case LEFT: return moveLeftKey;
            case RIGHT: return moveRightKey;
        }
        return null;
    }

}
