package cz.cuni.mff.domanyoa.magicmaze;

import cz.cuni.mff.domanyoa.magicmaze.model.GameEndReason;
import cz.cuni.mff.domanyoa.magicmaze.model.Logic;
import cz.cuni.mff.domanyoa.magicmaze.model.GameSettings;
import cz.cuni.mff.domanyoa.magicmaze.view.EndScreen;
import cz.cuni.mff.domanyoa.magicmaze.view.GameScreen;
import cz.cuni.mff.domanyoa.magicmaze.view.SetupScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/// The class that sticks it all together. It controls the stages of the game.
public class Main extends Application {
    Logic logic;

    /// Start the setup stage (SetupScreen).
    @Override
    public void start(Stage primaryStage) {
        SetupScreen setupScreen = new SetupScreen();

        Scene scene = setupScreen.createScene(settings -> {
            System.out.println("Setup is done!");
            logic = new Logic(settings);
            System.out.println("2. Logic object created");
            startGame(logic, primaryStage);
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /// start the main stage (GameScreen).
    private void startGame(Logic logic, Stage primaryStage) {
        System.out.println("3. Entering startGame");
        GameScreen gameScreen = new GameScreen(logic, reason -> showEndScreen(primaryStage, reason));
        Scene gameScene = gameScreen.createScene();
        System.out.println("4. GameScene created");
        primaryStage.setScene(gameScene);
        System.out.println("5. Scene swapped!");
    }

    /// Start the last stage after the game ended (EndScreen).
    private void showEndScreen(Stage stage, GameEndReason gameEndReason) {
        EndScreen endScreen = new EndScreen(gameEndReason);
        stage.setScene(endScreen.createScene());
    }

    /// Launches the game.
    public static void main(String[] args) {
        launch(args);
    }
}
