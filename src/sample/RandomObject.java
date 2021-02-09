package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.Random;

public class RandomObject extends Pane {
    private int score;
    public final int WIDTH = 1000;
    public final int HEIGHT = 600;
    private Label tScore = new Label();
    private Random random = new Random();
    private Timeline loop1;

    RandomObject() {
        tScore.setFont(Font.font(25));
        tScore.relocate(WIDTH / 4.0, 10);
        tScore.setText("Score:");
        getChildren().add(tScore);
    }

    public void start() {
        int gameTime = 10000;
        StopWatch stopWatch = new StopWatch();
        Label time = new Label();
        time.relocate(WIDTH / 1.25, 10);
        time.setFont(Font.font(25));
        getChildren().add(time);
        stopWatch.start();
        loop1 = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> {
            if (stopWatch.getElapsedTime() > gameTime) {
                end();
            }
            time.setText("Time left: " + ((gameTime - 1) / 1000 - stopWatch.getElapsedTime() / 1000) + "." + (1000 - stopWatch.getElapsedTime() % 1000));

        }
        ));
        loop1.setCycleCount(Timeline.INDEFINITE);
        loop1.play();
    }

    public void createObject() {


        int object = random.nextInt(4);
        int xAxis = random.nextInt(500) + 100;
        int yAxis = random.nextInt(400) + 100;

        Shape shape;

        switch (object) {
            case 0:
                shape = new Circle(20);
                shape.setLayoutX(xAxis);
                shape.setLayoutY(yAxis);
                getChildren().add(shape);
                moveObject(shape);
                break;
            case 1:
                shape = new Rectangle(50, 50);
                shape.setLayoutX(xAxis);
                shape.setLayoutY(yAxis);
                getChildren().add(shape);
                moveObject(shape);
                break;
            case 2:
                shape = new Rectangle(35, 20);
                shape.setLayoutX(xAxis);
                shape.setLayoutY(yAxis);
                getChildren().add(shape);
                moveObject(shape);
                break;
            case 3:
                shape = new Ellipse(25, 30);
                shape.setLayoutX(xAxis);
                shape.setLayoutY(yAxis);
                getChildren().add(shape);
                moveObject(shape);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + object);
        }
    }

    private Paint randomColour(int score) {
        if (score == 1)
            return Color.GREEN;
        else if (score == 2)
            return Color.YELLOW;
        else return Color.RED;
    }

    private void moveObject(Shape e) {

        double objHeight = 0, objWidth = 0;
        if (e instanceof Circle) {
            objHeight = ((Circle) e).getRadius();
        } else if (e instanceof Ellipse) {
            objHeight = ((Ellipse) e).getRadiusY();
            objWidth = ((Ellipse) e).getRadiusX();
        } else if (e instanceof Rectangle) {
            objHeight = ((Rectangle) e).getHeight();
            objWidth = ((Rectangle) e).getWidth();
        }

        double finalObjWidth = objWidth;
        double finalObjHeight = objHeight;
        double vx = Math.random() * 2;
        double vy = Math.random() * 2;
        e.setId("" + ((int) (Math.sqrt(vx * vx + vy * vy)) + 1));
        e.setFill(randomColour(Integer.parseInt(e.getId())));
        Timeline loop = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<>() {
            double deltaX = vx;
            double deltaY = vy;

            public void handle(final ActionEvent t) {
                e.setLayoutX(e.getLayoutX() + deltaX);
                e.setLayoutY(e.getLayoutY() + deltaY);

                boolean atRightBorder = e.getLayoutX() >= (WIDTH - finalObjWidth);
                boolean atLeftBorder = e.getLayoutX() <= (0 + finalObjWidth);
                boolean atBottomBorder = e.getLayoutY() >= (HEIGHT - finalObjHeight);
                boolean atTopBorder = e.getLayoutY() <= (0 + finalObjHeight);

                if (atRightBorder || atLeftBorder) {
                    deltaX *= -1;
                }
                if (atBottomBorder || atTopBorder) {
                    deltaY *= -1;
                }
            }
        }
        )
        );
        loop.setCycleCount(Animation.INDEFINITE);
        loop.play();

        e.setOnMouseClicked(t -> {
            getChildren().remove(e);
            score += Integer.parseInt(e.getId());
            tScore.setText("Score: " + score);
            try {
                createObject();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void end() {
        loop1.stop();
        Main m = new Main();
        try {
            Main.setScore(score);
            m.EndScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
