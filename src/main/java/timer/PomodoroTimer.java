package timer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;

public class PomodoroTimer extends Application {
    private Timeline timeline;
    private int currentMinute = 24;
    private int currentSecond = 59;
    private Text sessionNumber;

    private int sessionCount = 1;
    private Label timer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        File soundFile = new File("./src/main/java/timer/timer_tone.mp3"); // path to sound file
        Media sound = new Media(soundFile.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);

        sessionNumber = new Text("Session 1");
        sessionNumber.setFont(new Font(13));
        timer = new Label("25:00");
        timer.setFont(new Font(80));

        Button startSession = new Button("Start Session");

        Button breakButton = new Button("Break");
        HBox sessionButtons = new HBox(startSession, breakButton);
        sessionButtons.setSpacing(10);
        sessionButtons.setAlignment(Pos.CENTER);

        Button play = new Button("Play");
        Button pause = new Button("Pause");
        Button stop = new Button("Stop");
        HBox timerButtons = new HBox(play, pause, stop);
        timerButtons.setSpacing(5);
        timerButtons.setAlignment(Pos.CENTER);

        VBox timerDisplay = new VBox(sessionNumber, timer, timerButtons);
        timerDisplay.setAlignment(Pos.CENTER);
        timerDisplay.setPadding(new Insets(10, 10, 10, 20));

        HBox pomodoroLayout = new HBox(timerDisplay, sessionButtons);
        pomodoroLayout.setSpacing(15);

        primaryStage.setTitle("Pomodoro Timer");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(pomodoroLayout, 390, 140));
        primaryStage.show();

        boolean isSession = true;
        startSession.setOnAction(event -> {
            breakButton.setDisable(false);
            startSession.setDisable(true);
            startTimer();
        });
        breakButton.setOnAction(event -> {
            startSession.setDisable(false);
            breakButton.setDisable(true);
            breakTimer();
        });
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            String currentMinuteString = String.format("%02d", currentMinute);
            String currentSecondString = String.format("%02d", currentSecond);
            timer.setText(currentMinuteString + ":" + currentSecondString);
            if (currentMinute == 0 && currentSecond == 0) {
                mediaPlayer.play();
            }
            else {
                currentSecond--;
            }

            if (currentSecond == -1) {
                currentSecond = 59;
                currentMinute--;
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void startTimer() {
        sessionNumber.setText("Session " + sessionCount);
        currentMinute = 24;
        currentSecond = 59;
        timeline.play();
    }

    private void breakTimer() {
        timeline.stop();
        sessionNumber.setText("Break Time");
        currentMinute = 4;
        currentSecond = 59;
        timeline.play();
        sessionCount += 1;
    }

}