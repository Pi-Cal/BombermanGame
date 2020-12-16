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
import uet.oop.bomberman.entities.Enemies.*;
import uet.oop.bomberman.entities.Item.*;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.Camera.GameCamera;
import uet.oop.bomberman.Bomb.Bomb;

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
    private boolean gameOver = false;

    private int level = 1;
    private boolean upLevel = false;

    private GraphicsContext gc;
    private Canvas canvas;
    private Group root;
    private static int enemiesNeedingKill;

    private List<Item> items = new ArrayList<>();
    public static List<EnemyAbs> enemies;
    private static List<Brick> explodingBricks;
    private static Map<Vector, Brick> bricks;
    public static String input = "";
    public static char[][] map;

    public static Bomber bomberman;
    public static List<Bomb> bombs;
    public static List<Bomb> enemyBombs;

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

//        bomberman = new Bomber(new Vector(1,1), Sprite.player_right.getFxImage());

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
                        bomberman.updateTimeLimitedItem();
                        lastTime3 = l;
                    }

                    if (bomberman.isCompletelyDead()) {
                        gameOver = true;
                    }

                    handleCollision();
                } else {
                    gameOver();
                    if (input.equals("SPACE")) {
                        stage.setScene(makeLevelScene(level));
                        update();
                        gameOver = false;
                    }
                }
            }
        };

        render();
        timer.start();
    }

    public void createMap(Scanner myReader) {
        for (int i = 0; i < realHeight; i++) {
            String row = myReader.nextLine();
            for (int j = 0; j < realWidth; j++) {
                map[i][j] = row.charAt(j);
                switch (map[i][j]) {
                    case '#':
                        Wall wall = new Wall(new Vector(j, i), Sprite.wall.getFxImage());
                        wall.render(gc);
                        break;
                    case '*':
                        Brick brick = new Brick(new Vector(j, i), Sprite.brick.getFxImage());
                        bricks.put(new Vector(j, i), brick);
                        break;
                    case '1':
                        Balloom enemy = new Balloom(new Vector(j, i), Sprite.balloom_left1.getFxImage());
                        enemies.add(enemy);
                        map[i][j] = ' ';
                        break;
                    case '2':
                        Oneal enemy2 = new Oneal(new Vector(j, i), Sprite.oneal_left1.getFxImage());
                        enemies.add(enemy2);
                        map[i][j] = ' ';
                        break;
                    case '3':
                        Doll enemy3 = new Doll(new Vector(j, i), Sprite.doll_left1.getFxImage());
                        enemies.add(enemy3);
                        map[i][j] = ' ';
                        break;
                    case '4':
                        Kondoria enemy4 = new Kondoria(new Vector(j,i), Sprite.kondoria_right1.getFxImage());
                        enemies.add(enemy4);
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
                }
            }
        }
        myReader.close();
    }

    public Scene makeLevelScene(int level) {
        items = new ArrayList<>();
        enemies = new ArrayList<>();
        explodingBricks = new ArrayList<>();
        bricks = new HashMap<>();
        bombs = new ArrayList<>();
        enemyBombs = new ArrayList<>();
        input = "";
        String filePath = "levels/Level" + (1 + (level - 1) % 3) + ".txt";
        try {
            ClassLoader cl = getClass().getClassLoader();
            File myObj = new File(Objects.requireNonNull(cl.getResource(filePath)).getFile());
            Scanner myReader = new Scanner(myObj);
            myReader.nextInt();
            realHeight = myReader.nextInt();
            realWidth = myReader.nextInt();
            enemiesNeedingKill = myReader.nextInt();
            map = new char[realHeight][realWidth];
            myReader.nextLine();

            // Tao Canvas
            canvas = new Canvas(Sprite.SCALED_SIZE * realWidth, Sprite.SCALED_SIZE * realHeight);
            Canvas canvas2 = new Canvas(Sprite.SCALED_SIZE * realWidth, Sprite.SCALED_SIZE * 3);

            gc = canvas.getGraphicsContext2D();
            gc.setFill(Color.rgb(80, 160,0));
            GraphicsContext gc2 = canvas2.getGraphicsContext2D();
            //gc2.clearRect(0, 0, WIDTH * Sprite.SCALED_SIZE, 3 * Sprite.SCALED_SIZE);
            gc2.setFill(Color.rgb(179,179,179));
            gc2.fillRect(0,0, Sprite.SCALED_SIZE * WIDTH,Sprite.SCALED_SIZE * 3);
            createMap(myReader);
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
            int maxBomb = 1;
            int flameLength = 1;
            if (bomberman != null) {
                maxBomb = bomberman.getMaxBomb();
                flameLength = bomberman.getMaxBombLength();
            }
            bomberman = new Bomber(new Vector(1,1), Sprite.player_right.getFxImage(), flameLength, maxBomb);
            gameCamera = new GameCamera(0, 0);
            render();
            return scene;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void LevelUp() {
        gc.clearRect(0, 0, Sprite.SCALED_SIZE * realWidth, Sprite.SCALED_SIZE * realHeight);
        Sound.stage_start.start();
        Sound.stage_theme.stop();
        Sound.stage_theme.reset();

        gc.setFill(Color.BLACK);
        gc.fillRect(0,0, WIDTH * Sprite.SCALED_SIZE, HEIGHT * Sprite.SCALED_SIZE);
        Label levelUp = new Label("STAGE " + (level + 1));
        levelUp.setTextFill(Color.WHITE);
        levelUp.setFont(Font.font("Verdana", 30));
        levelUp.setTranslateX(WIDTH * Sprite.DEFAULT_SIZE - 100);
        levelUp.setTranslateY(HEIGHT * Sprite.DEFAULT_SIZE);
        root.getChildren().add(levelUp);

    }

    public void gameOver() {
        gc.clearRect(0, 0, Sprite.SCALED_SIZE * realWidth, Sprite.SCALED_SIZE * realHeight);
        Label gameOver = new Label("\tGAME OVER\nPress SPACE to play again");
        gameOver.setTextFill(Color.WHITE);
        gameOver.setFont(Font.font("Verdana", 40));
        gameOver.setTranslateX(WIDTH * Sprite.DEFAULT_SIZE - 270);
        gameOver.setTranslateY(HEIGHT * Sprite.DEFAULT_SIZE);
        root.getChildren().add(gameOver);
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
            Item item = items.get(i);
            if (bomberman.collidedWithItem(item)) {
                if (item instanceof BombItem) { bomberman.addBomb(item); }
                else if (item instanceof FlameItem) { bomberman.addFlame(item); }
                else if (item instanceof SpeedItem) { bomberman.addSpeed((TimeLimitedItem) item); }
                else if (item instanceof Portal) {
                    upLevel = true;
                }
                Sound.bling.start();
                item.clear(gc);
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
        removeEnemy(enemies);
        items.forEach(g -> g.render(gc));
        bombs.forEach(g -> g.update(time, gc));
        removeBomb(bombs);
        int i = 0;
        while (i < explodingBricks.size()) {
            Brick b = explodingBricks.get(i);
            b.update(time, gc);
            if (b.isDone()) {
                explodingBricks.remove(i);
            } else {
                i++;
            }
        }
        enemyBombs.forEach(g -> g.update(time, gc));
        removeBomb(enemyBombs);
        enemies.forEach(g -> g.render(gc));
        bomberman.render(gc);
    }

    public void render() {
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

    public static List<Brick> getExplodingBricks() {
        return explodingBricks;
    }

    private void removeBomb(List<Bomb> bombs) {
        int i = 0;
        while (i < bombs.size()) {
            if (bombs.get(i).isExploded()) {
                bombs.remove(i);
            } else { i++; }
        }
    }

    private void removeEnemy(List<EnemyAbs> enemies) {
        int j = 0;
        while (j < enemies.size()) {
            EnemyAbs temp = enemies.get(j);
            if (temp.isCompletelyDead()) {
                if (temp instanceof Doll) {
                    enemies.add(new Ghost2(temp.position.toNormal().round(), Sprite.ghost_left1.getFxImage()));
                }
                Sound.coin.start();
                counting.incEnemyKilled();
                enemies.get(j).clear(gc);
                enemies.remove(j);
            } else { j++; }
        }
    }


}