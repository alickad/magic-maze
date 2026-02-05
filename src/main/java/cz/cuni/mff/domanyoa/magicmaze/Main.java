package cz.cuni.mff.domanyoa.magicmaze;

import cz.cuni.mff.domanyoa.magicmaze.model.Logic;
import cz.cuni.mff.domanyoa.magicmaze.view.GameScreen;
import cz.cuni.mff.domanyoa.magicmaze.view.SetupScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    Logic logic;

    @Override
    public void start(Stage primaryStage) {
        SetupScreen setupScreen = new SetupScreen();

        Scene scene = setupScreen.createScene(heroes -> {
            System.out.println("Setup is done!");
            logic = new Logic(heroes);
            startGame(logic, primaryStage);
        });

        primaryStage.setScene(scene);  // ← Missing!
        primaryStage.show();
    }

    private void startGame(Logic logic, Stage primaryStage) {
        GameScreen gameScreen = new GameScreen(logic);
        Scene gameScene = gameScreen.createScene();
        primaryStage.setScene(gameScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
