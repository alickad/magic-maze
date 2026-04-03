package cz.cuni.mff.domanyoa.magicmaze.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Timer {
    private double totalTime;
    private double remainingTime;
    private Label label;
    private final Runnable onFinished;
    Timeline timeline;

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


    // public double getTimeRemaining() {
    //    return remainingTime;
    //}

    private void updateLabel(){
        label.setText("Time Remaining: " + remainingTime);
    }

    public void start(){
        timeline.play();
    }
    public void pause(){
        timeline.pause();
    }
    public void reset(){
        remainingTime = totalTime;
        updateLabel();
        timeline.playFromStart();
    }
}
