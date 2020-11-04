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
    public static final int HEIGHT = 10;
    private static int realHeight;
    private static int realWidth;

    private GraphicsContext gc;
    private Canvas canvas;
    private Group root;

    private List<Entity> entities = new ArrayList<>();
    private Map<Vector, Entity> stillObjects = new HashMap<Vector, Entity>();
    public static String inputLists = "";
    public static char[][] map;

    Bomber bomberman;
    GameCamera gameCamera = new GameCamera(0,0);


    public static void main(String[] args) {
        Application .launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        createMap("levels/Level2.txt");
        canvas = new Canvas(Sprite.SCALED_SIZE * 31, Sprite.SCALED_SIZE * 13);
        gc = canvas.getGraphicsContext2D();

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


        bomberman = new Bomber(new Vector(1,1), Sprite.player_right.getFxImage());

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
                handleEvent();
            }
        };

        timer.start();

        entities.add(bomberman);
    }

    public void handleEvent() {
        bomberman.handleCollision(stillObjects);
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
                                stillObjects.put(wall.getPosition(), wall);
                                break;
                            case '*' :
                                Brick brick = new Brick(new Vector(j, i), Sprite.brick.getFxImage());
                                stillObjects.put(brick.getPosition(), brick);
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

    public void update() {

        entities.forEach(Entity::update);
        gameCamera.update(bomberman);
        canvas.setTranslateX(-gameCamera.getxOffset());
        canvas.setTranslateY(-gameCamera.getyOffset());
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Map.Entry<Vector, Entity> entry : stillObjects.entrySet()) {
            entry.getValue().render(gc);
        }
        entities.forEach(g -> g.render(gc));
    }

    public static int getRealHeight() {
        return realHeight;
    }

    public static int getRealWidth() {
        return realWidth;
    }
}
