package cz.cuni.mff.domanyoa.magicmaze.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Timer {
    private double totalTime;
    private Label label;
    private final Runnable onFinished;
    Timeline timeline;

    public Timer(double totalTime, Label label, Runnable onFinished) {
        this.totalTime = totalTime;
        this.label = label;
        this.onFinished = onFinished;

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            timeRemaining -= 0.1;
            if (timeRemaining <= 0) {
                timeRemaining = 0;
                updateLabel();
                timeline.stop();
                onFinished.run();
                return;
            }
            updateLabel();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    double timeRemaining = totalTime;

    public double getTimeRemaining() {
        return timeRemaining;
    }

    private void updateLabel(){
        label.setText("Time Remaining: " + timeRemaining);
    }

    public void start(){
        timeline.play();
    }
    public void pause(){
        timeline.pause();
    }
    public void reset(){
        timeRemaining = totalTime;
        updateLabel();
        timeline.playFromStart();
    }
}
