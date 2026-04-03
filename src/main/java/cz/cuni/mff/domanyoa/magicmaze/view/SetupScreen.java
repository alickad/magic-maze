package cz.cuni.mff.domanyoa.magicmaze.view;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

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

public class SetupScreen {
    List<Hero> heroes;
    private Consumer<List<Hero>> onSetupComplete;
    private StackPane[][] grid;

    public SetupScreen() {
        Hero hero1 = new Hero(Logic.BOARD_HEIGHT/2,Logic.BOARD_WIDTH/2, KeyCode.Q, KeyCode.Z, KeyCode.DIGIT7, KeyCode.H);
        Hero hero2 = new Hero(Logic.BOARD_HEIGHT/2 + 1,Logic.BOARD_WIDTH/2, KeyCode.W, KeyCode.X, KeyCode.DIGIT8, KeyCode.J);
        Hero hero3 = new Hero(Logic.BOARD_HEIGHT/2,Logic.BOARD_WIDTH/2 + 1, KeyCode.E, KeyCode.C, KeyCode.DIGIT9, KeyCode.K);
        Hero hero4 = new Hero(Logic.BOARD_HEIGHT/2 + 1,Logic.BOARD_WIDTH/2 + 1, KeyCode.R, KeyCode.V, KeyCode.DIGIT0, KeyCode.L);
        heroes = Arrays.asList(hero1, hero2, hero3, hero4);

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
                gridPane.add(grid[i][j], j, i);  // Swap: column=j, row=i
            }
        }

        return gridPane;
    }

    public Scene createScene(Consumer<List<Hero>> onSetupComplete) {
        this.onSetupComplete = onSetupComplete;
        VBox root = new VBox(20);
        Label title = new Label("Setup");
        Label instructions = new Label("Select the control you want to change using arrows, then press the desired key");
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
                    case 0 -> hero.setUP(code);
                    case 1 -> hero.setDOWN(code);
                    case 2 -> hero.setLEFT(code);
                    case 3 -> hero.setRIGHT(code);
                }
                StackPane currentCell = grid[selectedRow.get()][selectedColumn.get()];
                Label label = (Label) currentCell.getChildren().get(0);  // Get the Label child
                label.setText(code.getName());  // Update text
            }
        });

        return scene;
    }
}
