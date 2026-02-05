package cz.cuni.mff.domanyoa.magicmaze.view;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import cz.cuni.mff.domanyoa.magicmaze.model.Color;
import cz.cuni.mff.domanyoa.magicmaze.model.Hero;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SetupScreen {
    List<Hero> heroes;
    private Consumer<List<Hero>> onSetupComplete;  // Storage for the "phone number"
    private StackPane[][] grid;

    public SetupScreen() {
        Hero redHero = new Hero(Color.RED, 25, 25, KeyCode.Q, KeyCode.Z, KeyCode.DIGIT7, KeyCode.H);
        Hero blueHero = new Hero(Color.BLUE, 24, 25, KeyCode.W, KeyCode.X, KeyCode.DIGIT8, KeyCode.J);
        Hero greenHero = new Hero(Color.GREEN, 24, 24, KeyCode.E, KeyCode.C, KeyCode.DIGIT9, KeyCode.K);
        Hero yellowHero = new Hero(Color.YELLOW, 25, 24, KeyCode.R, KeyCode.V, KeyCode.DIGIT0, KeyCode.L);
        heroes = Arrays.asList(redHero, blueHero, greenHero, yellowHero);

        grid = new StackPane[4][4];
        for (int i = 0; i < 4; i++) {
            grid[0][i] = createStackPane(heroes.get(i).getUP().getName());
            grid[1][i] = createStackPane(heroes.get(i).getDOWN().getName());
            grid[2][i] = createStackPane(heroes.get(i).getLEFT().getName());
            grid[3][i] = createStackPane(heroes.get(i).getRIGHT().getName());
        }
        grid[0][0].setStyle("-fx-background-color: orange;");
    }

    private StackPane createStackPane(String key) {
        StackPane pane = new StackPane();
        Label label = new Label(key);

        pane.getChildren().add(label);
        pane.setStyle("-fx-border-color: black; -fx-background-color: white;");
        return pane;
    }
    private GridPane initialGrid(){
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(new Label("up"), 0, 1);
        gridPane.add(new Label("down"), 0, 2);
        gridPane.add(new Label("left"), 0, 3);
        gridPane.add(new Label("right"), 0, 4);
        gridPane.add(new Label("RED") , 1, 0);
        gridPane.add(new Label("BLUE") , 2, 0);
        gridPane.add(new Label("GREEN") , 3, 0);
        gridPane.add(new Label("YELLOW") , 4, 0);
        for (int i = 0; i < heroes.size(); i++) {
            for (int j = 0; j < 4; j++) {
                gridPane.add(grid[i][j], i+1, j+1);
            }
        }

        return gridPane;
    }

    public Scene createScene(Consumer<List<Hero>> onSetupComplete) {
        this.onSetupComplete = onSetupComplete;
        VBox root = new VBox(20);
        Label title = new Label("Setup");
        Label instructions = new Label("Instructions for selection");
        GridPane gridPane = initialGrid();
        Button startButton =  new Button("Start the game");

        startButton.setOnAction(e -> {
            onSetupComplete.accept(heroes);  // "Calling back" with the heroes
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
                
                grid[selectedColumn.get()][selectedRow.get()].setStyle("-fx-border-color: black; -fx-background-color: white;");
                if (code == KeyCode.UP && selectedRow.get() > 0) selectedRow.getAndDecrement();
                else if (code == KeyCode.DOWN && selectedRow.get() < 3) selectedRow.getAndIncrement();
                else if (code == KeyCode.LEFT && selectedColumn.get() > 0) selectedColumn.getAndDecrement();
                else if (code == KeyCode.RIGHT && selectedColumn.get() < 3) selectedColumn.getAndIncrement();
                grid[selectedColumn.get()][selectedRow.get()].setStyle("-fx-border-color: black; -fx-background-color: orange;");
            }

            if (code.isDigitKey() || code.isLetterKey()){
                Hero hero = heroes.get(selectedColumn.get());
                switch (selectedRow.get()){
                    case 0 -> hero.setUP(code);
                    case 1 -> hero.setDOWN(code);
                    case 2 -> hero.setLEFT(code);
                    case 3 -> hero.setRIGHT(code);
                }
                StackPane currentCell = grid[selectedColumn.get()][selectedRow.get()];
                Label label = (Label) currentCell.getChildren().get(0);  // Get the Label child
                label.setText(code.getName());  // Update text
            }
        });

        return scene;
    }
}
