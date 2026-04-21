package cz.cuni.mff.domanyoa.magicmaze.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

/// Class to control the timer in the game.
public class Timer {
    private double totalTime;
    private double remainingTime;
    private Label label;
    private final Runnable onFinished;
    Timeline timeline;

    /// Constructs the class.
    /// @param totalTime the total time to finish the game
    /// @param label label of the timer in GameScreen
    /// @param onFinished what to do if time runs out
    public Timer(double totalTime, Label label, Runnable onFinished) {
        this.totalTime = totalTime;
        this.remainingTime = totalTime;
        this.label = label;
        this.onFinished = onFinished;

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            remainingTime -= 0.1;
            if (remainingTime <= 0) {
                remainingTime = 0;
                updateLabel();
                timeline.stop();
                onFinished.run();
                return;
            }
            updateLabel();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /// Updates the label in GameScreen.
    private void updateLabel(){
        label.setText("Time Remaining: " + String.format("%.2g%n", remainingTime));
    }

    /// Start the timer.
    public void start(){
        timeline.play();
    }
    /// Reset the timer, so it starts from totalTime again.
    public void reset(){
        remainingTime = totalTime;
        updateLabel();
        timeline.playFromStart();
    }
}
