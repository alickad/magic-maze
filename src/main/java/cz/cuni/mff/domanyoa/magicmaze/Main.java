package cz.cuni.mff.domanyoa.magicmaze;

import cz.cuni.mff.domanyoa.magicmaze.model.Hero;
import cz.cuni.mff.domanyoa.magicmaze.model.Logic;
import cz.cuni.mff.domanyoa.magicmaze.view.SetupScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {
    List<Hero> heroes;

    @Override
    public void start(Stage primaryStage) {
        SetupScreen setupScreen = new SetupScreen();
        Logic logic = new Logic();

        Scene scene = setupScreen.createScene(heroes -> {
            System.out.println("Setup is done! I received these heroes:");
            this.heroes = heroes;
            //System.out.println(heroes.get(0).getDOWN().name());
        });

        primaryStage.setScene(scene);  // ← Missing!
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
