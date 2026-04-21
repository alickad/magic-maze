package cz.cuni.mff.domanyoa.magicmaze.view;
import cz.cuni.mff.domanyoa.magicmaze.model.GameEndReason;

/// Interface, so that the classes of different game stages can "communicate"
public interface GameEndListener {
    void onGameEnd(GameEndReason reason);
}
