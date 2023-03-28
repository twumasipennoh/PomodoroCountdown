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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PomodoroCountdown extends Application {

    private static final int WORK_TIME_MINUTES = 25;
    private static final int REST_TIME_MINUTES = 5;

    private int minutes = WORK_TIME_MINUTES;
    private int seconds = 0;
    private boolean isWorking = true;

    private Timeline timeline;

    private Label timeLabel;
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Pomodoro Timer");

        timeLabel = new Label("25:00");
        timeLabel.setFont(new Font("Arial", 50));

        statusLabel = new Label("Work");
        statusLabel.setFont(new Font("Arial", 20));

        Button startButton = new Button("Start");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startTimer();
            }
        });

        Button stopButton = new Button("Stop");
        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stopTimer();
            }
        });

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(startButton, stopButton);

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(timeLabel, statusLabel, buttonBox);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateTime();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void stopTimer() {
        timeline.stop();
    }

    private void updateTime() {
        if (seconds == 0) {
            if (minutes == 0) {
                if (isWorking) {
                    isWorking = false;
                    minutes = REST_TIME_MINUTES;
                    statusLabel.setText("Rest");
                } else {
                    isWorking = true;
                    minutes = WORK_TIME_MINUTES;
                    statusLabel.setText("Work");
                }
            } else {
                minutes--;
                seconds = 59;
            }
        } else {
            seconds--;
        }
        updateTimeLabel();
    }

    private void updateTimeLabel() {
        String minutesString = String.format("%02d", minutes);
        String secondsString = String.format("%02d", seconds);
        timeLabel.setText(minutesString + ":" + secondsString);
    }

    public static void main(String[] args) {
        launch(args);
    }

}

