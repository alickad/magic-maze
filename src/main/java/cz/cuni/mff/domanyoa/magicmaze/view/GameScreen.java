package cz.cuni.mff.domanyoa.magicmaze.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;

import cz.cuni.mff.domanyoa.magicmaze.model.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/// The UI of the main game.
public class GameScreen {
    private Logic logic;
    private List<Hero> heroes;
    private List<Exit> exits;
    private Board board;
    private StackPane[][] grid;
    private final GameEndListener endListener;
    private Map<Hero, Group> heroNodes = new HashMap<>();
    private Map<Exit, Group> exitNodes = new HashMap<>();
    private Timer timer;
    private List<TimeReset> timeResets;
    private Map<TimeReset, Group> hourglasses = new HashMap<>();
    private final int cellSize = 40;

    /// Get the color of item in Group.
    /// @return color
    private Color getShapeColor(Group shape) {
        Node firstChild = shape.getChildren().get(0);

        if (firstChild instanceof Shape sh) {
            Paint fill = sh.getFill();

            if (fill instanceof Color color) {
                return color;
            }
        }
        return null;
    }

    /// Constructor using logic and endListener.
    /// @param logic the game logic to be used
    /// @param endListener interface, so that we can switch to EndScreen at the end of the game
    public  GameScreen(Logic logic, GameEndListener endListener) {
        this.logic = logic;
        this.endListener = endListener;
        heroes = logic.getHeroes();
        board = logic.getBoard();
        exits = logic.getExits();
        timeResets = logic.getTimeResets();

        // we can make graphics nicer later
        SVGPath RedHero = getHeroPath(Color.RED);
        SVGPath GreenHero = getHeroPath(Color.GREEN);
        SVGPath BlueHero = getHeroPath(Color.BLUE);
        SVGPath YellowHero = getHeroPath(Color.YELLOW);

        heroNodes.put(heroes.get(0), new Group(RedHero));
        heroNodes.put(heroes.get(1), new Group(BlueHero));
        heroNodes.put(heroes.get(2), new Group(GreenHero));
        heroNodes.put(heroes.get(3), new Group(YellowHero));

        SVGPath RedExit = getExitPath(Color.RED);
        SVGPath BlueExit = getExitPath(Color.BLUE);
        SVGPath GreenExit = getExitPath(Color.GREEN);
        SVGPath YellowExit = getExitPath(Color.YELLOW);

        exitNodes.put(exits.get(0), new Group(RedExit));
        exitNodes.put(exits.get(1), new Group(BlueExit));
        exitNodes.put(exits.get(2), new Group(GreenExit));
        exitNodes.put(exits.get(3), new Group(YellowExit));

        for (TimeReset timeReset : timeResets) {
            SVGPath timeResetShape = getHourglassPath();
            Group hourglass = new Group(timeResetShape);
            hourglasses.put(timeReset, hourglass);
        }

    }

    /// Generates a SVG path for hero icon.
    /// @return svg path
    private static SVGPath getHeroPath(Color color) {
        SVGPath heroShape = new SVGPath();
        heroShape.setContent("M184.74-104.74v-177.29q96.15-67.59 133.01-134.38 36.86-66.79 51.53-118.96H262.59v-47.89h105.26q-22.08-21.47-34.51-50.09-12.43-28.61-12.43-62.28 0-66.61 46.5-113.12 46.5-46.51 113.09-46.51 65.59 0 112.72 46.51 47.13 46.51 47.13 113.12 0 33.67-12.81 62.28-12.81 28.62-34.89 50.09h105.64v47.89h-106.3q13.89 51.79 50.26 119 36.38 67.2 133.01 134.34v177.29H184.74Z");
        heroShape.setFill(color);
        heroShape.setScaleX(0.04);
        heroShape.setScaleY(0.04);
        return heroShape;
    }

    /// Generates a SVG path for hourglass icon.
    /// @return svg path
    private static SVGPath getHourglassPath() {
        SVGPath timeResetShape = new SVGPath();
        timeResetShape.setContent("M180-100v-50.26h79.95v-123.38q0-72.67 42.58-130.19Q345.1-461.36 413.54-480q-68.44-19.44-111.01-77.03-42.58-57.58-42.58-129.74v-122.97H180V-860h600v50.26h-79.95v122.97q0 72.16-42.58 129.74Q614.9-499.44 546.46-480q68.44 18.64 111.01 76.17 42.58 57.52 42.58 130.19v123.38H780V-100H180Z");
        timeResetShape.setFill(Color.DARKRED);
        timeResetShape.setScaleX(0.037);
        timeResetShape.setScaleY(0.037);
        return timeResetShape;
    }

    /// Generates a SVG path for exit icon.
    /// @return svg path
    private static SVGPath getExitPath(Color color) {
        SVGPath exitShape = new SVGPath();
        exitShape.setContent("M189.06-113.3q-31 0-53.38-22.38-22.38-22.38-22.38-53.38v-186.4h75.76v186.4h581.88v-581.88H189.06v186.4H113.3v-186.4q0-31.06 22.38-53.49 22.38-22.43 53.38-22.43h581.88q31.06 0 53.49 22.43 22.43 22.43 22.43 53.49v581.88q0 31-22.43 53.38Q802-113.3 770.94-113.3H189.06ZM419-276.7l-55.04-55.47 109.79-109.95H113.3v-75.76h360.45L363.96-627.83 419-683.3 622.3-480 419-276.7Z");
        exitShape.setFill(color);
        exitShape.setScaleX(0.037);
        exitShape.setScaleY(0.037);
        return exitShape;
    }

    /// Sets up the right panel with controls and timer.
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
            h1.getChildren().add(new Label(heroes.get(i).getKey(Direction.UP).getName()));
            h2.getChildren().add(new Label(heroes.get(i).getKey(Direction.DOWN).getName()));
            h3.getChildren().add(new Label(heroes.get(i).getKey(Direction.LEFT).getName()));
            h4.getChildren().add(new Label(heroes.get(i).getKey(Direction.RIGHT).getName()));
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

        Label TimeLabel = new Label();
        TimeLabel.setStyle("-fx-text-fill: red;");
        this.timer = new Timer(60.0, TimeLabel, () ->
                endListener.onGameEnd(GameEndReason.TIMEOUT));

        panel.getChildren().add(TimeLabel);
    }

    /// One graphics tile on the board.
    /// @return one graphics tile on the board
    private  StackPane BoardStackPane(){
        StackPane cell = new StackPane();
        cell.setPrefSize(cellSize, cellSize);
        Rectangle floor = new  Rectangle(cellSize,cellSize);
        floor.setFill(Color.web("#E8D8B0"));
        floor.setStroke(Color.DARKGRAY);

        cell.getChildren().add(floor);
        return cell;
    }

    /// Makes the initial grid with tiles, heroes, exits and time resets.
    private void setupGrid(GridPane gridPane, StackPane[][] grid){
        System.out.println("setupGrid");
        for (int i = 0; i < board.height(); i++){
            for (int j = 0; j < board.width(); j++){
                StackPane cell = BoardStackPane();
                if (board.tileAt(i,j).isWall(Direction.UP)){
                    Rectangle topWall = new Rectangle(cellSize, 2, Color.BLACK);
                    cell.getChildren().add(topWall);
                    StackPane.setAlignment(topWall, Pos.TOP_CENTER);
                }
                if (board.tileAt(i,j).isWall(Direction.DOWN)){
                    Rectangle bottomWall = new Rectangle(cellSize, 2, Color.BLACK);
                    cell.getChildren().add(bottomWall);
                    StackPane.setAlignment(bottomWall, Pos.BOTTOM_CENTER);
                }
                if (board.tileAt(i,j).isWall(Direction.LEFT)){
                    Rectangle leftWall = new Rectangle(2, cellSize, Color.BLACK);
                    cell.getChildren().add(leftWall);
                    StackPane.setAlignment(leftWall, Pos.CENTER_LEFT);
                }
                if (board.tileAt(i,j).isWall(Direction.RIGHT)){
                    Rectangle rightWall = new Rectangle(2, cellSize, Color.BLACK);
                    cell.getChildren().add(rightWall);
                    StackPane.setAlignment(rightWall, Pos.CENTER_RIGHT);
                }
                grid[i][j] = cell;
                gridPane.add(grid[i][j], j,i);
                System.out.println("Setting up...");
            }
        }
        for (Hero hero : heroes){
            int x =  hero.getX();
            int y = hero.getY();
            //grid[y][x].setStyle("-fx-background-color:" + hero.getColor().name() + ";");
            grid[y][x].getChildren().add(heroNodes.get(hero));
        }
        for (Exit exit : exits){
            int x = exit.getX();
            int y = exit.getY();
            grid[y][x].getChildren().add(exitNodes.get(exit));
        }
        for (TimeReset timeReset : timeResets){
            int x = timeReset.getX();
            int y = timeReset.getY();
            grid[y][x].getChildren().add(hourglasses.get(timeReset));
        }
        System.out.println("setup complete");
    }

    /// Moves a hero.
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

    /// The main thing that makes the game playable.
    /// @return scene
    public Scene  createScene() {
        HBox root = new HBox();
        GridPane gridPane = new GridPane();
        grid = new StackPane[board.height()][board.width()];

        setupGrid(gridPane, grid);
        VBox rightPanel = new VBox();
        setupRightPanel(rightPanel);
        root.getChildren().addAll(gridPane, rightPanel);
        Scene scene = new Scene(root);
        timer.start();

        scene.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            KeyCode code = event.getCode();
            System.out.println("Is pressed: " + code);
            for  (Hero hero : heroes){
                if (code == hero.getKey(Direction.UP) && logic.canMove(hero, Direction.UP)){
                    System.out.println("moving up");
                    graphicMove(hero, Direction.UP);
                    logic.move(hero, Direction.UP);
                }
                if (code == hero.getKey(Direction.DOWN) && logic.canMove(hero, Direction.DOWN)){
                    System.out.println("moving down");
                    graphicMove(hero, Direction.DOWN);
                    logic.move(hero, Direction.DOWN);
                }
                if (code == hero.getKey(Direction.LEFT) && logic.canMove(hero, Direction.LEFT)){
                    System.out.println("moving left");
                    graphicMove(hero, Direction.LEFT);
                    logic.move(hero, Direction.LEFT);
                }
                if (code == hero.getKey(Direction.RIGHT) && logic.canMove(hero, Direction.RIGHT)){
                    System.out.println("moving right");
                    graphicMove(hero, Direction.RIGHT);
                    logic.move(hero, Direction.RIGHT);
                }

                if (logic.gameEndedCheck()){
                    endListener.onGameEnd(GameEndReason.SUCCESS);
                }
                if (logic.timeResetsCheck()){
                    timer.reset();
                    TimeReset timeReset = logic.getTimeReset();

                    Group visualHourglass = hourglasses.get(timeReset);
                    if (visualHourglass != null && visualHourglass.getParent() instanceof Pane) {
                        ((Pane)visualHourglass.getParent()).getChildren().remove(visualHourglass);
                    }
                    hourglasses.remove(timeReset);
                    logic.disableReset(timeReset);
                }
            }
        });


        return scene;
    }
}
