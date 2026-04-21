package cz.cuni.mff.domanyoa.magicmaze.view;
import cz.cuni.mff.domanyoa.magicmaze.model.GameEndReason;

/// Interface, so that the classes of different game stages can "communicate"
public interface GameEndListener {
    /// What to do when game ends based on the reason
    /// @param reason why the game ended
    void onGameEnd(GameEndReason reason);
}
