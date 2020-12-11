package screen;

import moving.Bomber;
import moving.Enemy;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import moving.Oneal;
import sample.Images;
import sample.Musics;
import staticObj.Bomb;
import staticObj.Brick;
import staticObj.Item;
import staticObj.Wall;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    Bomber bomber;
    List<Enemy> aEnemy = new ArrayList<>();
    List<Oneal> aOneal = new ArrayList<>();
    List<Wall> aWall = new ArrayList<>();
    List<Brick> aBrick = new ArrayList<>();
    List<Item> aItem = new ArrayList<>();
    Bomb bomb = new Bomb();
    boolean isWin = false;
    public static int[][] dataMap = new int[20][20];
    int score = 0;

    Label labelScore = new Label("Score: " + this.score);

    /**
     * Screen.
     *
     * @param stage Stage
     * @return stage
     */
    public Scene getScene(Stage stage) {
        initGameScr();
        Musics.game.stop();
        Musics.game.play();
        Canvas canvas = new Canvas(600, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        AnchorPane anchorPane = new AnchorPane(canvas);

        AnchorPane paneInfo = new AnchorPane();
        paneInfo.setMaxSize(600, 30);
        paneInfo.setMinSize(600, 30);
        paneInfo.setLayoutY(600);
        paneInfo.setStyle("-fx-background-color:black");

        Label buttonRS = new Label("Reset");

        buttonRS.setFont(new Font(20));
        buttonRS.setLayoutX(549);
        buttonRS.setStyle("-fx-text-fill: white");

        //Diem
        labelScore.setFont(new Font(20));
        labelScore.setStyle("-fx-text-fill: white;");
        paneInfo.getChildren().addAll(buttonRS, labelScore);
        anchorPane.getChildren().addAll(paneInfo);

        // die or win
        StackPane stackPane = new StackPane();
        stackPane.setMaxSize(200, 200);
        stackPane.setMinSize(200, 200);
        stackPane.setLayoutX(200);
        stackPane.setLayoutY(200);
        stackPane.setStyle("-fx-background-color:white; -fx-background-radius:30");
        Label label = new Label("You die!");
        label.setFont(new Font(30));
        label.setTranslateY(-35);
        Button buttonOK = new Button("OK");
        buttonOK.setFont(new Font(30));
        buttonOK.setTranslateY(50);
        buttonOK.setMaxWidth(200);
        buttonOK.setStyle("-fx-background-color:transparent; -fx-background-radius:20");
        stackPane.getChildren().addAll(label, buttonOK);


        AnimationTimer timer = new AnimationTimer() {
            int gameTicks = 0;

            @Override
            public void handle(long now) {
                gameTicks++;

                if (bomb.timeBomb > 0) {
                    bomb.timeBomb--;
                    if (bomb.timeBomb < 50) {
                        if(bomb.timeBomb == 49){
                            Musics.bombang.stop();
                            Musics.bombang.play();
                        }
                        bomb.image = Images.bombang[4];

                        for (int i = 0; i < aBrick.size(); i++) {
                            Brick brick = aBrick.get(i);
                            if (bomb.checkBangBrick(brick)) {
                                score += 1;
                                brick.canClear = true;
                                Game.dataMap[brick.y / 30][brick.x / 30] = 5;

                                if (new Random().nextBoolean()) {
                                    int random = new Random().nextInt(3);
                                    Item item = new Item(Images.item[random], brick.x, brick.y, random);
                                    aItem.add(item);
                                }
                                aBrick.remove(i);

                                i--;
                            }
                        }

                        for (int i = 0; i < aEnemy.size(); i++) {
                            Enemy enemy = aEnemy.get(i);
                            if (enemy.bombBangDie(bomb)) {
                                score += 5;
                                enemy.isDie = true;
                                aEnemy.remove(i);
                                i--;
                                Musics.enemy_die.stop();
                                Musics.enemy_die.play();
                            }
                        }
                        for (int i = 0; i < aOneal.size(); i++) {
                            Oneal oneal = aOneal.get(i);
                            if (oneal.bombBangDie(bomb)) {
                                score += 10;
                                oneal.isDie = true;
                                aOneal.remove(i);
                                i--;
                                Musics.enemy_die.stop();
                                Musics.enemy_die.play();
                            }
                        }
                        if (bomb.timeBomb > 5 && !bomber.isDie && bomber.bombBangDie(bomb)) {
                            Musics.bomber_die.stop();
                            Musics.bomber_die.play();
                            anchorPane.getChildren().add(stackPane);
                            bomber.speed = 0;
                            bomber.image = Images.bomber[4];
                            bomber.isDie = true;
                        }

                    }
                }

                for (int i = 0; i < aItem.size(); i++) {
                    Item item = aItem.get(i);
                    if (bomber.pickItem(item) && item.type != 3) {
                        Musics.pickItem.stop();
                        Musics.pickItem.play();
                        if (item.type == 1) {
                            if(bomber.speed < 15) {
                                bomber.speed = bomber.speed + 5;
                            } else {
                                bomber.speed = 15;
                            }
                        }
                        if (item.type == 2) {

                        }
                        bomber.x = item.x;
                        bomber.y = item.y;
                        aItem.remove(i);
                        i--;
                    }
                    if (bomber.pickItem(item) && item.type == 3 && !isWin && aEnemy.size() == 0 && aOneal.size() == 0) {
                        Musics.win.stop();
                        Musics.win.play();
                        isWin = true;
                        label.setText("You win!");
                        anchorPane.getChildren().add(stackPane);
                    }

                }

                if (gameTicks % 3 == 0) {
                    for (int i = 0; i < aEnemy.size(); i++) {
                        aEnemy.get(i).autoMove(bomb);
                    }
                    for (int i = 0; i < aOneal.size(); i++) {
                        aOneal.get(i).autoMove(bomb);
                    }
                }
                for (int i = 0; i < aEnemy.size(); i++) {
                    if (!bomber.isDie && bomber.enemyAttack(aEnemy.get(i))) {
                        anchorPane.getChildren().add(stackPane);
                        bomber.speed = 0;
                        bomber.image = Images.bomber[4];
                        bomber.isDie = true;
                    }
                }
                for (int i = 0; i < aOneal.size(); i++) {
                    if (!bomber.isDie && bomber.onealAttack(aOneal.get(i))) {
                        anchorPane.getChildren().add(stackPane);
                        bomber.speed = 0;
                        bomber.image = Images.bomber[4];
                        bomber.isDie = true;
                    }
                }
                gc.clearRect(0, 0, 600, 600);
                paint(gc);


            }
        };
        timer.start();


        anchorPane.setStyle("-fx-background-color:green");
        Scene scene = new Scene(anchorPane, 600, 630);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case RIGHT:
                        bomber.move(bomber.x + bomber.speed, bomber.y);
                        if (!bomber.isDie) {
                            bomber.image = Images.bomber[0];
                        }
                        break;
                    case LEFT:
                        bomber.move(bomber.x - bomber.speed, bomber.y);
                        if (!bomber.isDie) {
                            bomber.image = Images.bomber[1];
                        }
                        break;
                    case UP:
                        bomber.move(bomber.x, bomber.y - bomber.speed);
                        if (!bomber.isDie) {
                            bomber.image = Images.bomber[2];
                        }
                        break;
                    case DOWN:
                        bomber.move(bomber.x, bomber.y + bomber.speed);
                        if (!bomber.isDie) {
                            bomber.image = Images.bomber[3];
                        }
                        break;
                    case SPACE:
                        if (bomb.timeBomb <= 0) {
                            Musics.new_bom.stop();
                            Musics.new_bom.play();
                            bomb.image = Images.bomb;
                            bomb.x = bomb.getX(bomber.x);
                            bomb.y = bomb.getY(bomber.y);
                            bomb.timeBomb = 200;
                        }
                        break;
                }

            }
        });

        buttonOK.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent12 -> {
            timer.stop();
            stage.setScene(new Game().getScene(stage));
        });

        buttonRS.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent12 -> {
            timer.stop();
            stage.setScene(new Game().getScene(stage));
        });

        return scene;
    }

    /**
     * Paint.
     *
     * @param gc GraphicsContext
     */
    public void paint(GraphicsContext gc) {
        labelScore.setText("Score: " + this.score);

        for (Wall wall : aWall) {
            wall.paint(gc);
        }

        for (Item item : aItem) {
            item.paint(gc);
        }

        for (Brick brick : aBrick) {
            brick.paint(gc);
        }

        for (Enemy enemy : aEnemy) {
            enemy.paint(gc);
        }

        for (Oneal oneal : aOneal) {
            oneal.paint(gc);
        }

        bomber.paint(gc);
        if (bomb.timeBomb > 0) {
            bomb.paint(gc);
        }
    }

    public void initGameScr() {
        bomb = new Bomb(Images.bomb, 0, 0);
        bomb.timeBomb = -1;
        createMap();

    }


    /**
     * Tạo map từ file.
     */
    public void createMap() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                dataMap[i][j] = 0;
            }
        }
        try {
            String text = Game.class.getResource("/res/map/map").toString();
            FileReader fr = new FileReader(text.substring(6, text.length()));
            BufferedReader br = new BufferedReader(fr);
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                for (int j = 0; j < line.length(); j++) {
                    if (line.codePointAt(j) == 35) {
                        dataMap[i][j] = 1;
                        Wall wall = new Wall(Images.wall, j * 30, i * 30);
                        aWall.add(wall);
                    }
                    if (line.codePointAt(j) == 80) {
                        dataMap[i][j] = 2;
                        bomber = new Bomber(Images.bomber[3], j * 30, i * 30);
                    }
                    if (line.codePointAt(j) == 69) {
                        dataMap[i][j] = 3;
                        Enemy enemy = new Enemy(Images.enemy, j * 30, i * 30);
                        aEnemy.add(enemy);
                    }
                    if (line.codePointAt(j) == 79) {
                        dataMap[i][j] = 3;
                        Oneal oneal = new Oneal(Images.oneal, j * 30, i * 30, bomber);
                        aOneal.add(oneal);
                    }

                    if (line.codePointAt(j) == 66) {
                        dataMap[i][j] = 4;
                        Brick brick = new Brick(Images.brick, j * 30, i * 30);
                        aBrick.add(brick);
                    }
                    if (line.codePointAt(j) == 73) {
                        dataMap[i][j] = 5;
                        Item item = new Item(Images.item[1], j * 30, i * 30, 1);
                        aItem.add(item);
                    }
                    if (line.codePointAt(j) == 87) {
                        dataMap[i][j] = 6;
                        Item item = new Item(Images.win, j * 30, i * 30, 3);
                        aItem.add(item);
                    }
                }
                i++;
                if (i > 19) {
                    break;
                }
            }
            fr.close();
            br.close();
        } catch (Exception ex) {
            System.out.println("error when load map");
        }

    }

}
