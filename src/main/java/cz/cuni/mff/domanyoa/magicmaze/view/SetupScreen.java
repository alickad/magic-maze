package cz.cuni.mff.domanyoa.magicmaze.view;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import cz.cuni.mff.domanyoa.magicmaze.model.Direction;
import cz.cuni.mff.domanyoa.magicmaze.model.GameSettings;
import cz.cuni.mff.domanyoa.magicmaze.model.Hero;
import cz.cuni.mff.domanyoa.magicmaze.model.Logic;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/// Initial screen, where controls can be chosen.
public class SetupScreen {
    List<Hero> heroes;
    private int height = 20;
    private int width = 20;
    private double totalTime = 60;
    private Consumer<GameSettings> onSetupComplete;
    private StackPane[][] grid;

    /// The initial screen with default values.
    public SetupScreen() {
        Hero hero1 = new Hero(height/2,width/2, KeyCode.Q, KeyCode.Z, KeyCode.DIGIT7, KeyCode.H);
        Hero hero2 = new Hero(height/2 + 1,width/2, KeyCode.W, KeyCode.X, KeyCode.DIGIT8, KeyCode.J);
        Hero hero3 = new Hero(height/2,width/2 + 1, KeyCode.E, KeyCode.C, KeyCode.DIGIT9, KeyCode.K);
        Hero hero4 = new Hero(height/2 + 1,width/2 + 1, KeyCode.R, KeyCode.V, KeyCode.DIGIT0, KeyCode.L);
        heroes = Arrays.asList(hero1, hero2, hero3, hero4);

        grid = new StackPane[4][4];
        for (int i = 0; i < 4; i++) {
            grid[0][i] = createStackPane(heroes.get(i).getKey(Direction.UP).getName());
            grid[1][i] = createStackPane(heroes.get(i).getKey(Direction.DOWN).getName());
            grid[2][i] = createStackPane(heroes.get(i).getKey(Direction.LEFT).getName());
            grid[3][i] = createStackPane(heroes.get(i).getKey(Direction.RIGHT).getName());
        }
        grid[0][0].setStyle("-fx-background-color: orange;");
    }

    /// One cell in initial controls table.
    private StackPane createStackPane(String key) {
        StackPane pane = new StackPane();
        Label label = new Label(key);

        pane.getChildren().add(label);
        pane.setStyle("-fx-border-color: black; -fx-background-color: white;");
        return pane;
    }

    /// Create the initial grid o controls.
    private GridPane initialGrid(){
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(new Label("up"), 4, 0);
        gridPane.add(new Label("down"), 4, 1);
        gridPane.add(new Label("left"), 4, 2);
        gridPane.add(new Label("right"), 4, 3);
        gridPane.add(new Label("RED") , 0, 4);
        gridPane.add(new Label("BLUE") , 1, 4);
        gridPane.add(new Label("GREEN") , 2, 4);
        gridPane.add(new Label("YELLOW") , 3, 4);
        for (int i = 0; i < heroes.size(); i++) {
            for (int j = 0; j < 4; j++) {
                gridPane.add(grid[i][j], j, i);
            }
        }

        return gridPane;
    }

    /// This makes the setup screen.
    /// When the setup is complete, game continues to GameScreen.
    /// @return scene of setupScreen
    public Scene createScene() {
        VBox root = new VBox(20);
        Label title = new Label("Setup");
        Label instructions = new Label("Select the control you want to change using arrows, then press the desired key");
        GridPane gridPane = initialGrid();

        Button startButton =  new Button("Start the game");

        startButton.setOnAction(e -> {
            GameSettings settings =  new GameSettings(heroes, width, height, totalTime);  // "Calling back" with the heroes
            onSetupComplete.accept(settings);
        });

        root.getChildren().addAll(title, instructions, gridPane, startButton);
        Scene scene = new Scene(root, 800, 600);

        AtomicInteger selectedRow = new AtomicInteger(0);
        AtomicInteger selectedColumn = new AtomicInteger(0);
        scene.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, keyEvent -> {
            KeyCode code = keyEvent.getCode();
            System.out.println("Is pressed: " + code);
            if (code.isArrowKey()){
                keyEvent.consume();
                grid[selectedRow.get()][selectedColumn.get()].setStyle("-fx-border-color: black; -fx-background-color: white;");
                if (code == KeyCode.UP && selectedRow.get() > 0) selectedRow.getAndDecrement();
                else if (code == KeyCode.DOWN && selectedRow.get() < 3) selectedRow.getAndIncrement();
                else if (code == KeyCode.LEFT && selectedColumn.get() > 0) selectedColumn.getAndDecrement();
                else if (code == KeyCode.RIGHT && selectedColumn.get() < 3) selectedColumn.getAndIncrement();
                grid[selectedRow.get()][selectedColumn.get()].setStyle("-fx-border-color: black; -fx-background-color: orange;");
            }

            if (code.isDigitKey() || code.isLetterKey()){
                Hero hero = heroes.get(selectedColumn.get());
                switch (selectedRow.get()){
                    case 0 -> hero.setKey(Direction.UP, code);
                    case 1 -> hero.setKey(Direction.DOWN, code);
                    case 2 -> hero.setKey(Direction.LEFT, code);
                    case 3 -> hero.setKey(Direction.RIGHT, code);
                }
                StackPane currentCell = grid[selectedRow.get()][selectedColumn.get()];
                Label label = (Label) currentCell.getChildren().get(0);  // Get the Label child
                label.setText(code.getName());  // Update text
            }
        });

        return scene;
    }
}
