package cz.cuni.mff.domanyoa.magicmaze.view;
import cz.cuni.mff.domanyoa.magicmaze.model.GameEndReason;

public interface GameEndListener {
    void onGameEnd(GameEndReason reason);
}
