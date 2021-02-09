package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.util.*;

public class Main extends Application {
    ArrayList<PlayerInfo> playerInfos;
    Scene currentScene;
    RandomObject x = new RandomObject();
    private static String name;
    private static Stage window;
    private static int score;

    //--------FXML--------\\
    public TextField NameTf;
    public Label ErrorNoNameLbl;
    public Label PlayLbl;
    public Label ExitLbl;
    public Label showScoreLbl;
    public Label scoreNumLbl;
    public Label GoToMenuLbl;
    public Label TopLbl;
    public Label topPlayer1;
    public Label topPlayer2;
    public Label topPlayer3;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.initStyle(StageStyle.TRANSPARENT);
        FirstScene();
    }

    //--------First Scene will be called when starting the Application--------\\
    public void FirstScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        window.setTitle("Fast Click Game");
        window.setScene(new Scene(root, x.WIDTH, x.HEIGHT));
        window.show();
    }

    //--------instructionScene will explain the game and its called after FirstScene--------\\
    public void instructionScene() throws IOException {
        String n = NameTf.getText().trim();
        if (n.length() == 0) {
            ErrorNoNameLbl.setVisible(true);
        } else if (n.contains(" ")) {
            ErrorNoNameLbl.setVisible(true);
        } else {

            Parent root = FXMLLoader.load(getClass().getResource("Instruction.fxml"));
            name = n;
            currentScene = new Scene(root, x.WIDTH, x.HEIGHT);
            window.setScene(currentScene);
        }
    }

    //--------This is the game scene we called it after instructionScene--------\\
    public void GameScene() {
        x.start();
        for (int i = 0; i < 5; i++) {                                                         // we may put it random
            x.createObject();
        }
        currentScene = new Scene(x, x.WIDTH, x.HEIGHT);
        window.setScene(currentScene);
    }

    //--------The end game will show the scores and ask if the player want to play again--------\\
    public void EndScene() throws Exception {

        FileOutputStream outputStream = new FileOutputStream("SCORES.txt", true);
        PrintWriter writer = new PrintWriter(outputStream);

        writer.println(name + "   " + score);
        writer.close();
        Parent root = FXMLLoader.load(getClass().getResource("End.fxml"));
        Scene scene = new Scene(root, x.WIDTH, x.HEIGHT);
        window.setScene(scene);

    }

    //--------This Method will close the application--------\\
    public void Exit() {
        window.close();
    }

    //--------This Method will make the word Red when hover--------\\
    public void MouseHoverIn(MouseEvent mouseEvent) {
        if (mouseEvent.getSource().equals(PlayLbl))
            PlayLbl.setTextFill(Color.RED);
        if (mouseEvent.getSource().equals(ExitLbl))
            ExitLbl.setTextFill(Color.RED);
        if (mouseEvent.getSource().equals(showScoreLbl))
            showScoreLbl.setTextFill(Color.RED);
        if (mouseEvent.getSource().equals(GoToMenuLbl))
            GoToMenuLbl.setTextFill(Color.RED);
    }

    //--------This Method will make the word white when hover is over--------\\
    public void MouseHoverOut(MouseEvent mouseEvent) {
        if (mouseEvent.getSource().equals(PlayLbl))
            PlayLbl.setTextFill(Color.WHITE);
        if (mouseEvent.getSource().equals(ExitLbl))
            ExitLbl.setTextFill(Color.WHITE);
        if (mouseEvent.getSource().equals(showScoreLbl))
            showScoreLbl.setTextFill(Color.WHITE);
        if (mouseEvent.getSource().equals(GoToMenuLbl))
            GoToMenuLbl.setTextFill(Color.WHITE);
    }

    //--------This Method will read the SCORES.txt and set the array list--------\\
    public void scoreBoard() throws Exception {
        playerInfos = new ArrayList<>();
        File file = new File("SCORES.txt");
        Scanner sc = new Scanner(file);
        String name;
        int score;
        while (sc.hasNextLine()) {
            name = sc.next();
            score = sc.nextInt();
            playerInfos.add(new PlayerInfo(name, score));
            sc.nextLine();
        }
        playerInfos.sort(PlayerInfo.scoreComp);
        FileOutputStream outputStream = new FileOutputStream("SCORES.txt");
        PrintWriter writer = new PrintWriter(outputStream);
        for (PlayerInfo playerInfo : playerInfos) {
            writer.println(playerInfo);
        }
        writer.close();
        sc.close();
    }

    //--------This Method will set the top 3 Scores from the array list--------\\
    public void Scores() throws Exception {
        scoreBoard();
        showScoreLbl.setVisible(false);
        scoreNumLbl.setText(scoreNumLbl.getText() + " " + score);
        scoreNumLbl.setVisible(true);
        GoToMenuLbl.setVisible(true);
        ExitLbl.setVisible(true);
        TopLbl.setVisible(true);
        try {
            if (playerInfos.get(0) != null) {
                topPlayer1.setVisible(true);
                topPlayer1.setText(playerInfos.get(0).toString());
            }
            if (playerInfos.get(1) != null) {
                topPlayer2.setVisible(true);
                topPlayer2.setText(playerInfos.get(1).toString());
            }
            if (playerInfos.get(2) != null) {
                topPlayer3.setVisible(true);
                topPlayer3.setText(playerInfos.get(2).toString());
            }
        } catch (IndexOutOfBoundsException e) {
        }
    }

    //--------This setter we use it at Random Object class--------\\
    public static void setScore(int score) {
        Main.score = score;
    }
}