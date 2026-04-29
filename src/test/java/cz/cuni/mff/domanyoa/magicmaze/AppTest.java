package cz.cuni.mff.domanyoa.magicmaze;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import cz.cuni.mff.domanyoa.magicmaze.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class HeroMovementTest {
    @Test
    void testHeroMovementUP() {
        Hero hero1 = new Hero(5,6);
        Hero hero2 = new Hero(5,7);
        Hero hero3 = new Hero(8,8);
        Hero hero4 = new Hero(5,9);
        List<Hero> heroes = Arrays.asList(hero1, hero2, hero3, hero4);
        Logic logic = new Logic(heroes);

        if (logic.canMove(heroes.get(0), Direction.UP)) {
            logic.move(heroes.get(0), Direction.UP);
            assertEquals(4, heroes.get(0).getY());
            assertEquals(6, heroes.get(0).getX());
        }
        else{
            logic.move(heroes.get(0), Direction.UP);
            assertEquals(5, heroes.get(0).getY());
            assertEquals(6, heroes.get(0).getX());
        }
    }
    @Test
    void testHeroMovementDOWN() {
        Hero hero1 = new Hero(5,6);
        Hero hero2 = new Hero(5,7);
        Hero hero3 = new Hero(8,8);
        Hero hero4 = new Hero(5,9);
        List<Hero> heroes = Arrays.asList(hero1, hero2, hero3, hero4);
        Logic logic = new Logic(heroes);


        if (logic.canMove(heroes.get(2), Direction.DOWN)) {
            logic.move(heroes.get(2), Direction.DOWN);
            assertEquals(9, heroes.get(2).getY());
            assertEquals(8, heroes.get(2).getX());
        }
        else{
            logic.move(heroes.get(2), Direction.DOWN);
            assertEquals(8, heroes.get(2).getY());
            assertEquals(8, heroes.get(2).getX());
        }
    }
    @Test
    void testHeroMovementLEFT() {
        Hero hero1 = new Hero(5,6);
        Hero hero2 = new Hero(5,7);
        Hero hero3 = new Hero(8,8);
        Hero hero4 = new Hero(5,9);
        List<Hero> heroes = Arrays.asList(hero1, hero2, hero3, hero4);
        Logic logic = new Logic(heroes);

        if (logic.canMove(heroes.get(2), Direction.LEFT)) {
            logic.move(heroes.get(2), Direction.LEFT);
            assertEquals(8, heroes.get(2).getY());
            assertEquals(7, heroes.get(2).getX());
        }
        else{
            logic.move(heroes.get(2), Direction.LEFT);
            assertEquals(8, heroes.get(2).getY());
            assertEquals(8, heroes.get(2).getX());
        }
    }
    @Test
    void testHeroMovementRIGHT() {
        Hero hero1 = new Hero(5,6);
        Hero hero2 = new Hero(5,7);
        Hero hero3 = new Hero(8,8);
        Hero hero4 = new Hero(5,9);
        List<Hero> heroes = Arrays.asList(hero1, hero2, hero3, hero4);
        Logic logic = new Logic(heroes);

        if (logic.canMove(heroes.get(2), Direction.RIGHT)) {
            logic.move(heroes.get(2), Direction.RIGHT);
            assertEquals(8, heroes.get(2).getY());
            assertEquals(9, heroes.get(2).getX());
        }
        else{
            logic.move(heroes.get(2), Direction.RIGHT);
            assertEquals(8, heroes.get(2).getY());
            assertEquals(8, heroes.get(2).getX());
        }
    }
    @Test
    void testHeroMovementToOccupied() {
        Hero hero1 = new Hero(5,6);
        Hero hero2 = new Hero(5,7);
        Hero hero3 = new Hero(8,8);
        Hero hero4 = new Hero(5,9);
        List<Hero> heroes = Arrays.asList(hero1, hero2, hero3, hero4);
        Logic logic = new Logic(heroes);

        logic.move(heroes.get(0), Direction.RIGHT);
        assertEquals(5, heroes.get(0).getY());
        assertEquals(6, heroes.get(0).getX());
    }
    @Test
    void testHeroMovementToBorder() {
        Hero hero1 = new Hero(5,6);
        Hero hero2 = new Hero(5,7);
        Hero hero3 = new Hero(0,0);
        Hero hero4 = new Hero(5,9);
        List<Hero> heroes = Arrays.asList(hero1, hero2, hero3, hero4);
        Logic logic = new Logic(heroes);

        logic.move(heroes.get(2), Direction.LEFT);
        assertEquals(0, heroes.get(2).getY());
        assertEquals(0, heroes.get(2).getX());
    }
}