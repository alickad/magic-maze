package cz.cuni.mff.domanyoa.magicmaze.model;

/// Enum of reasons to end the game.
public enum GameEndReason {
    /// The game ended because of a win.
    SUCCESS,
    /// The game ended, because the players ran out of time.
    TIMEOUT
}
