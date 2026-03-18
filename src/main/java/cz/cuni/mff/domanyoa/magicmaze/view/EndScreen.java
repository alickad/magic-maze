package cz.cuni.mff.domanyoa.magicmaze.view;

import cz.cuni.mff.domanyoa.magicmaze.model.GameEndReason;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class EndScreen {
    GameEndReason endReason;

    public EndScreen(GameEndReason gameEndReason) {
        this.endReason = gameEndReason;
    }

    public Scene createScene() {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        if (endReason == GameEndReason.SUCCESS){
            Label yay = new Label();
            yay.setText("Congratulations! You have successfully finished the game!"); // maybe add the time later
            yay.setFont(new Font(40));
            yay.setWrapText(true);
            root.getChildren().add(yay);
        }
        else{
            Label nah =  new Label();
            nah.setText("You have failed to finish the game. The time is up!"); // add option to continue the game without timer
            nah.setFont(new Font(40));
            nah.setWrapText(true);
            root.getChildren().add(nah);
        }
        Scene scene = new Scene(root, 800, 600);

        return scene;
    }
}
