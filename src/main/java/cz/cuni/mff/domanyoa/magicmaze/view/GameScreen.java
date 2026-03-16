package cz.cuni.mff.domanyoa.magicmaze.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.cuni.mff.domanyoa.magicmaze.model.Board;
import cz.cuni.mff.domanyoa.magicmaze.model.Direction;
import cz.cuni.mff.domanyoa.magicmaze.model.Hero;
import cz.cuni.mff.domanyoa.magicmaze.model.Logic;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;

public class GameScreen {
    private Logic logic;
    private List<Hero> heroes;
    private Board board;
    private StackPane[][] grid;
    //private final String defaultStyle = "-fx-border-color: lightgray; -fx-background-color: white;";
    Map<Hero, Shape> heroNodes = new HashMap<>();

    private Color getShapeColor(Shape shape) {
        Paint fill = shape.getFill();
        if (fill instanceof Color color) {
            return color;
        }
        return null; // unknown type (gradient, pattern, etc.)
    }

    public  GameScreen(Logic logic) {
        this.logic = logic;
        heroes = logic.getHeroes();
        board = logic.getBoard();

        // we can make graphics nicer later
        Shape RedHero = new Circle(10);
        RedHero.setFill(Color.RED);
        Shape BlueHero = new Circle(10);
        BlueHero.setFill(Color.BLUE);
        Shape GreenHero = new Circle(10);
        GreenHero.setFill(Color.GREEN);
        Shape YellowHero = new Circle(10);
        YellowHero.setFill(Color.YELLOW);

        heroNodes.put(heroes.get(0), RedHero);
        heroNodes.put(heroes.get(1), BlueHero);
        heroNodes.put(heroes.get(2), GreenHero);
        heroNodes.put(heroes.get(3), YellowHero);
    }

    private void setupRightPanel(VBox panel){
        VBox p1 = new VBox();
        VBox p2 = new VBox();
        VBox p3 = new VBox();
        VBox p4 = new VBox();

        Label l1 = new Label("Player 1, Direction UP");
        Label l2 = new Label("Player 2, Direction DOWN");
        Label l3 = new Label("Player 3, Direction LEFT");
        Label l4 = new Label("Player 4, Direction RIGHT");

        HBox h1 = new HBox();
        HBox h2 = new HBox();
        HBox h3 = new HBox();
        HBox h4 = new HBox();

        for (int i = 0; i < this.heroes.size(); i++) {
            Color colorC = getShapeColor(heroNodes.get(heroes.get(i)));
            String color = String.format("rgba(%d,%d,%d,%.2f)",
                    (int)(colorC.getRed() * 255),
                    (int)(colorC.getGreen() * 255),
                    (int)(colorC.getBlue() * 255),
                    colorC.getOpacity());
            h1.getChildren().add(new Label(heroes.get(i).getUP().getName()));
            h2.getChildren().add(new Label(heroes.get(i).getDOWN().getName()));
            h3.getChildren().add(new Label(heroes.get(i).getLEFT().getName()));
            h4.getChildren().add(new Label(heroes.get(i).getRIGHT().getName()));
            h1.getChildren().get(i).setStyle("-fx-background-color: " + color + "; -fx-padding: 10px;");
            h2.getChildren().get(i).setStyle("-fx-background-color: " + color + "; -fx-padding: 10px;");
            h3.getChildren().get(i).setStyle("-fx-background-color: " + color + "; -fx-padding: 10px;");
            h4.getChildren().get(i).setStyle("-fx-background-color: " + color + "; -fx-padding: 10px;");
        }

        p1.getChildren().addAll(l1,h1);
        p2.getChildren().addAll(l2,h2);
        p3.getChildren().addAll(l3,h3);
        p4.getChildren().addAll(l4,h4);

        panel.getChildren().addAll(p1,p2,p3,p4);
    }
    private  StackPane BoardStackPane(){
        int cellSize = 40;
        StackPane cell = new StackPane();
        cell.setPrefSize(cellSize, cellSize);
        Rectangle floor = new  Rectangle(40,40);
        floor.setFill(Color.web("#E8D8B0"));
        floor.setStroke(Color.DARKGRAY);

        cell.getChildren().add(floor);
        return cell;
    }

    private void setupGrid(GridPane gridPane, StackPane[][] grid){
        for (int i = 0; i < board.height(); i++){
            for (int j = 0; j < board.width(); j++){
                StackPane cell = BoardStackPane();
                grid[i][j] = cell;
                gridPane.add(grid[i][j], j,i);
            }
        }
        for (Hero hero : heroes){
            int x =  hero.getX();
            int y = hero.getY();
            //grid[y][x].setStyle("-fx-background-color:" + hero.getColor().name() + ";");
            grid[y][x].getChildren().add(heroNodes.get(hero));
        }
    }

    private void graphicMove(Hero hero, Direction d){
        int x = hero.getX();
        int y = hero.getY();
        grid[y][x].getChildren().remove(heroNodes.get(hero));
        switch (d){
            case UP -> grid[y-1][x].getChildren().add(heroNodes.get(hero));
            case DOWN -> grid[y+1][x].getChildren().add(heroNodes.get(hero));
            case LEFT -> grid[y][x-1].getChildren().add(heroNodes.get(hero));
            case RIGHT -> grid[y][x+1].getChildren().add(heroNodes.get(hero));
        }
    }

    public Scene  createScene() {
        HBox root = new HBox();
        GridPane gridPane = new GridPane();
        grid = new StackPane[board.height()][board.width()];

        setupGrid(gridPane, grid);
        VBox rightPanel = new VBox();
        setupRightPanel(rightPanel);
        root.getChildren().addAll(gridPane, rightPanel);
        Scene scene = new Scene(root);

        scene.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            KeyCode code = event.getCode();
            System.out.println("Is pressed: " + code);
            for  (Hero hero : heroes){
                if (code == hero.getUP() && logic.canMove(hero, Direction.UP)){
                    System.out.println("moving up");
                    graphicMove(hero, Direction.UP);
                    logic.move(hero, Direction.UP);
                }
                if (code == hero.getDOWN() && logic.canMove(hero, Direction.DOWN)){
                    System.out.println("moving down");
                    graphicMove(hero, Direction.DOWN);
                    logic.move(hero, Direction.DOWN);
                }
                if (code == hero.getLEFT() && logic.canMove(hero, Direction.LEFT)){
                    System.out.println("moving left");
                    graphicMove(hero, Direction.LEFT);
                    logic.move(hero, Direction.LEFT);
                }
                if (code == hero.getRIGHT() && logic.canMove(hero, Direction.RIGHT)){
                    System.out.println("moving right");
                    graphicMove(hero, Direction.RIGHT);
                    logic.move(hero, Direction.RIGHT);
                }
            }
        });

        return scene;
    }
}
