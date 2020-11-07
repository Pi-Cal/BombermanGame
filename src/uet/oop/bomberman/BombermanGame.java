package uet.oop.bomberman;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.Camera.GameCamera;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.*;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 20;
    public static final int HEIGHT = 16;
    private static int realHeight;
    private static int realWidth;
    private long lastTime = 0;
    private int gameTime = 0;

    private GraphicsContext gc;
    private Canvas canvas;
    private Group root;

    private List<Enemy> enemies = new ArrayList<>();
    private List<Entity> walls = new ArrayList<>();
    private List<Entity> bricks = new ArrayList<>();
    private Map<Vector, Entity> stillObjects = new HashMap<Vector, Entity>();
    public static String inputLists = "";
    public static char[][] map;

    Bomber bomberman;
    Bomb bomb;
    GameCamera gameCamera = new GameCamera(0,0);


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        createMap("levels/Level2.txt");
        canvas = new Canvas(Sprite.SCALED_SIZE * realWidth, Sprite.SCALED_SIZE * realHeight);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.rgb(80, 160,0));

        // Tao root container
        root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);
        scene.setFill(Color.rgb(80, 160,0));

        // Them scene vao stage
        stage.setScene(scene);
        stage.setHeight(HEIGHT * Sprite.SCALED_SIZE);
        stage.setWidth(WIDTH * Sprite.SCALED_SIZE);
        stage.show();

        scene.setOnKeyPressed(
                (javafx.scene.input.KeyEvent event) -> {
                    String keyName = event.getCode().toString();
                    if ( !inputLists.equals(keyName) ) {
                        inputLists = keyName;
                    }
                }
        );

        scene.setOnKeyReleased(
                (javafx.scene.input.KeyEvent event) -> {
                    String keyName = event.getCode().toString();
                    inputLists = "";
                }
        );

        gameTime = 0;
        bomberman = new Bomber(new Vector(1,1), Sprite.player_right.getFxImage());

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (l - lastTime > 100000000/3) {
                    setLastTime(l);
                    update(l);
                }

                for (Enemy e : enemies) {
                    if (l - lastTime > 2000000000) {
                        e.update(gc);
                    }
                }
                handleEvent();
            }
        };

        render();
        timer.start();
    }

    public void handleEvent() {
        bomberman.handleCollision();
    }

    public void createMap(String filePath) {
        try {
            ClassLoader cl = getClass().getClassLoader();
            File myObj = new File(cl.getResource(filePath).getFile());
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                int level = myReader.nextInt();
                int height= myReader.nextInt();
                realHeight = height;
                int width = myReader.nextInt();
                realWidth = width;
                map = new char[height][width];
                String row = myReader.nextLine();
                for (int i = 0; i < height; i++ ) {
                    row = myReader.nextLine();
                    for (int j = 0; j < width; j++) {
                        map[i][j] = row.charAt(j);
                        switch (map[i][j]) {
                            case '#' :
                                Wall wall = new Wall(new Vector(j, i), Sprite.wall.getFxImage());
                                walls.add(wall);
                                break;
                            case '*' :
                                Brick brick = new Brick(new Vector(j, i), Sprite.brick.getFxImage());
                                bricks.add(brick);
                                break;
                            case '1' :
                                Enemy enemy = new Enemy(new Vector(j, i), Sprite.balloom_left1.getFxImage());
                                enemies.add(enemy);
                                map[i][j] = ' ';
                                break;
                            default:
                                //Grass grass = new Grass(new Vector(j, i), Sprite.grass.getFxImage());
                                //stillObjects.put(grass.getPosition(), grass);
                        }
                    }
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void update(long time) {
        gameCamera.update(bomberman);
        canvas.setTranslateX(-gameCamera.getxOffset());
        canvas.setTranslateY(-gameCamera.getyOffset());
        bomberman.position.add(gameCamera.getxOffset() - gameCamera.getLastXOffset(),
                gameCamera.getyOffset() - gameCamera.getLastYOffset());
        bomberman.update(gc);
        enemies.forEach(g -> g.update(gc));
    }

    public void render() {
        walls.forEach(g -> g.render(gc));
        bricks.forEach(g -> g.render(gc));
    }

    public static int getRealHeight() {
        return realHeight;
    }

    public static int getRealWidth() {
        return realWidth;
    }

    public void setLastTime(long l) {
        lastTime = l;
    }
}
