package cz.cuni.mff.domanyoa.magicmaze.model;

import java.util.List;

/// The settings a player can choose at the initial screen.
/// They are then used inside Logic.
/// @param heroes heroes
/// @param boardHeight height of board
/// @param boardWidth width of board
/// @param initialTime available time between time resets
public record GameSettings(
        List<Hero> heroes,
        int boardWidth,
        int boardHeight,
        double initialTime
) {}