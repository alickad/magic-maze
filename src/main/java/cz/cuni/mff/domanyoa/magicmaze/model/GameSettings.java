package cz.cuni.mff.domanyoa.magicmaze.model;

import java.util.List;

public record GameSettings(
        List<Hero> heroes,
        int boardWidth,
        int boardHeight,
        double initialTime
) {}