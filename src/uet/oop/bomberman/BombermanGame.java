package uet.oop.bomberman;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import uet.oop.bomberman.Character.Sound;
import uet.oop.bomberman.Character.Vector;
import uet.oop.bomberman.Character.Couting;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Item.*;
import uet.oop.bomberman.entities.Enemies.Enemy1;
import uet.oop.bomberman.entities.Enemies.Enemy2;
import uet.oop.bomberman.entities.Enemies.EnemyAbs;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.Camera.GameCamera;
import uet.oop.bomberman.notEntity.Bomb;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.*;
import java.util.List;

public class BombermanGame extends Application {

    public static final int WIDTH = 24;
    public static final int HEIGHT = 16;
    private static int realHeight;
    private static int realWidth;
    private long lastTime = 0;
    private long lastTime2 = 0;
    private long lastTime3 = 0;
    private boolean isRunning = false;
    private boolean gameOver = false;

    private int level = 1;
    private boolean upLevel = false;
    private Label LevelUp;

    private GraphicsContext gc;
    private GraphicsContext gc2;
    private Canvas canvas;
    private Canvas canvas2;
    private Group root;
    private static int enemiesNeedingKill;

    private List<Item> items = new ArrayList<>();
    public static List<EnemyAbs> enemies = new ArrayList<>();
    private List<Entity> walls = new ArrayList<>();
    private static Map<Vector, Brick> bricks = new HashMap<>();
    public static String input = "";
    public static char[][] map;

    public static Bomber bomberman;
    public static List<Bomb> bombs = new ArrayList<>();
    private GameCamera gameCamera = new GameCamera(0, 0);
    private Couting counting = new Couting();


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {


        Scene scene = makeLevelScene(level);
        // Them scene vao stage
        stage.setScene(scene);
        stage.setHeight((HEIGHT + 3) * Sprite.SCALED_SIZE);
        stage.setWidth(WIDTH * Sprite.SCALED_SIZE);
        stage.show();

        bomberman = new Bomber(new Vector(1,1), Sprite.player_right.getFxImage());
        isRunning = true;

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (upLevel) {
                    LevelUp();
                    if (l - lastTime > 3 * Animation.NANO){
                        stage.setScene(makeLevelScene(++level));
                        update();
                        upLevel = false;
                    }
                } else if (!gameOver) {
                    if (l - lastTime > Animation.NANO / 10 / bomberman.getMaxSpeed() && !bomberman.isDead()) {
                        update();
                        setLastTime(l);
                    }

                    if (l - lastTime2 > Animation.NANO / 10 / 3) {
                        if (bomberman.isDead()) {
                            update();
                            setLastTime(l);
                        }
                        updateOther(l);
                        lastTime2 = l;
                    }

                    if (l - lastTime3 > Animation.NANO) {
                        counting.update();
                        bomberman.updateItem();
                        lastTime3 = l;
                    }

                    if (bomberman.isCompletelyDead()) {
                        gameOver = true;
                    }

                    handleCollision();
                }
            }
        };

        render();
        timer.start();
    }

    public void createMap(String filePath) {
        try {
            ClassLoader cl = getClass().getClassLoader();
            File myObj = new File(cl.getResource(filePath).getFile());
            Scanner myReader = new Scanner(myObj);
            int level = myReader.nextInt();
            int height = myReader.nextInt();
            realHeight = height;
            int width = myReader.nextInt();
            realWidth = width;
            enemiesNeedingKill = myReader.nextInt();
            map = new char[height][width];
            myReader.nextLine();
            for (int i = 0; i < height; i++) {
                String row = myReader.nextLine();
                for (int j = 0; j < width; j++) {
                    map[i][j] = row.charAt(j);
                    switch (map[i][j]) {
                        case '#':
                            Wall wall = new Wall(new Vector(j, i), Sprite.wall.getFxImage());
                            walls.add(wall);
                            break;
                        case '*':
                            Brick brick = new Brick(new Vector(j, i), Sprite.brick.getFxImage());
                            bricks.put(new Vector(j, i), brick);
                            break;
                        case '1':
                            Enemy1 enemy = new Enemy1(new Vector(j, i), Sprite.balloom_left1.getFxImage());
                            enemies.add(enemy);
                            map[i][j] = ' ';
                            break;
                        case '2':
                            Enemy2 enemy2 = new Enemy2(new Vector(j, i), Sprite.balloom_left1.getFxImage());
                            enemies.add(enemy2);
                            map[i][j] = ' ';
                            break;
                        case 's':
                            Brick brick1 = new Brick(new Vector(j, i), Sprite.brick.getFxImage());
                            bricks.put(new Vector(j, i), brick1);
                            SpeedItem speedItem = new SpeedItem(bricks.get(new Vector(j, i)));
                            bricks.get(new Vector(j, i)).setContain(speedItem);
                            items.add(speedItem);
                            map[i][j] = '*';
                            break;
                        case 'b':
                            Brick brick2 = new Brick(new Vector(j, i), Sprite.brick.getFxImage());
                            bricks.put(new Vector(j, i), brick2);
                            BombItem bombItem = new BombItem(bricks.get(new Vector(j, i)));
                            bricks.get(new Vector(j, i)).setContain(bombItem);
                            items.add(bombItem);
                            map[i][j] = '*';
                            break;
                        case 'f':
                            Brick brick3 = new Brick(new Vector(j, i), Sprite.brick.getFxImage());
                            bricks.put(new Vector(j, i), brick3);
                            FlameItem flameItem = new FlameItem(bricks.get(new Vector(j, i)));
                            bricks.get(new Vector(j, i)).setContain(flameItem);
                            items.add(flameItem);
                            map[i][j] = '*';
                            break;
                        case '5':
                            Brick brick4 = new Brick(new Vector(j, i), Sprite.brick.getFxImage());
                            bricks.put(new Vector(j, i), brick4);
                            Portal portal = new Portal(bricks.get(new Vector(j, i)));
                            bricks.get(new Vector(j, i)).setContain(portal);
                            items.add(portal);
                            map[i][j] = '*';
                            break;
                        default:
                            //Grass grass = new Grass(new Vector(j, i), Sprite.grass.getFxImage());
                            //stillObjects.put(grass.getPosition(), grass);
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public Scene makeLevelScene(int level) {
        items = new ArrayList<>();
        enemies = new ArrayList<>();
        walls = new ArrayList<>();
        bricks = new HashMap<>();
        bombs = new ArrayList<>();
        input = "";
        createMap("levels/Level" + level + ".txt");
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * realWidth, Sprite.SCALED_SIZE * realHeight);
        canvas2 = new Canvas(Sprite.SCALED_SIZE * realWidth, Sprite.SCALED_SIZE * 3);

        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.rgb(80, 160,0));
        gc2 = canvas2.getGraphicsContext2D();
        //gc2.clearRect(0, 0, WIDTH * Sprite.SCALED_SIZE, 3 * Sprite.SCALED_SIZE);
        gc2.setFill(Color.rgb(179,179,179));
        gc2.fillRect(0,0, Sprite.SCALED_SIZE * WIDTH,Sprite.SCALED_SIZE * 3);

        // Tao root container
        root = new Group();
        root.getChildren().add(canvas);
        root.getChildren().add(canvas2);
        counting = new Couting();
        root.getChildren().add(counting.getTimeLabel());
        root.getChildren().add(counting.getEnemyKilled());

        // Tao scene
        Scene scene = new Scene(root);
        scene.setFill(Color.rgb(80, 160,0));
        scene.setOnKeyPressed(
                (javafx.scene.input.KeyEvent event) -> {
                    String keyName = event.getCode().toString();
                    if ( !input.equals(keyName) ) {
                        input = keyName;
                    }
                }
        );
        scene.setOnKeyReleased(
                (javafx.scene.input.KeyEvent event) -> input = ""
        );

        //Them am thanh

        Sound.stage_theme.start();
        Sound.stage_theme.setLoop(2);


        bomberman = new Bomber(new Vector(1,1), Sprite.player_right.getFxImage());

        render();
        return scene;
    }

    public void LevelUp() {
        gc.clearRect(0, 0, Sprite.SCALED_SIZE * realWidth, Sprite.SCALED_SIZE * realHeight);
        Sound.stage_start.start();
        Sound.stage_theme.stop();
        Sound.stage_theme.reset();

        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,(int)WIDTH * Sprite.SCALED_SIZE, (int)HEIGHT * Sprite.SCALED_SIZE);
        LevelUp = new Label("STAGE " + (level + 1));
        LevelUp.setTextFill(Color.WHITE);
        LevelUp.setFont(Font.font("Verdana", 30));
        LevelUp.setTranslateX(WIDTH * Sprite.DEFAULT_SIZE - 100);
        LevelUp.setTranslateY(HEIGHT * Sprite.DEFAULT_SIZE);
        root.getChildren().add(LevelUp);

    }

    public void handleCollision() {
        bomberman.handleCollision();
        for (EnemyAbs enemy : enemies) {
            if (bomberman.handle_1_Collision(enemy)) {
                bomberman.setDead(true);
                break;
            }
        }

        int i = 0;
        while (i < items.size()) {
            if (bomberman.handle_1_Collision(items.get(i))) {
                if (items.get(i) instanceof BombItem) { bomberman.addBomb(items.get(i)); }
                else if (items.get(i) instanceof FlameItem) { bomberman.addFlame(items.get(i)); }

                else if (items.get(i) instanceof SpeedItem) { bomberman.addSpeed(items.get(i)); }
                else if (items.get(i) instanceof Portal) {
                    upLevel = true;
                }
                Sound.bling.start();
                items.get(i).clear(gc);
                items.remove(i);
            } else { i++; }
        }

        if (counting.getTimeInt() == -1) { gameOver = true; }
    }

    public void update() {
        gameCamera.update(bomberman);
        canvas.setTranslateX(-gameCamera.getxOffset());
        canvas.setTranslateY(-gameCamera.getyOffset() + Sprite.SCALED_SIZE * 3);
        bomberman.position.add(gameCamera.getxOffset() - gameCamera.getLastXOffset(),
                gameCamera.getyOffset() - gameCamera.getLastYOffset());
        bomberman.update(gc);
    }

    public void updateOther(long time) {
        enemies.forEach(g -> g.update(gc));
        int j = 0;
        while (j < enemies.size()) {
            if (enemies.get(j).isCompletelyDead()) {
                Sound.coin.start();
                counting.incEnemyKilled();
                enemies.get(j).clear(gc);
                enemies.remove(j);
            } else { j++; }
        }
        bombs.forEach(g -> g.update(time, gc));
        int i = 0;
        while (i < bombs.size()) {
            if (bombs.get(i).isExploded()) {
                bombs.remove(i);
            } else { i++; }
        }
    }

    public void render() {
        walls.forEach(g -> g.render(gc));
        for (Map.Entry<Vector, Brick> entry : bricks.entrySet()) {
            entry.getValue().render(gc);
        }
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

    public static Map<Vector, Brick> getBricks() {
        return bricks;
    }

    public static int getEnemiesNeedingKill() {
        return enemiesNeedingKill;
    }


}