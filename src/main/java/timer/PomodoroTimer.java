package timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class PomodoroTimer extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //create HBox named pomodoroLayout
        HBox pomodoroLayout = new HBox();

        //create VBox named sessionButtons
        VBox sessionButtons = new VBox();
        Button startSession = new Button("Start Session");
        Button breakButton = new Button("Break");
        sessionButtons.setSpacing(10);
        sessionButtons.setAlignment(Pos.CENTER);

        //create VBox named timerDisplay
        VBox timerDisplay = new VBox();
        Text sessionNumber = new Text("Session 1");
        sessionNumber.setFont(new Font(13));
        final Label timer = new Label("25:00");
        timer.setFont(new Font(80));

        //create HBox named timerButtons
        HBox timerButtons = new HBox();
        Button play = new Button("Play");
        Button pause = new Button("Pause");
        Button stop = new Button("Stop");
        timerButtons.setSpacing(5);
        timerButtons.getChildren().addAll(play, pause, stop);
        timerButtons.setAlignment(Pos.CENTER);
        sessionButtons.getChildren().add(timerButtons);
        sessionButtons.getChildren().addAll(startSession, breakButton);

        //add sessionNumber, timer, and timerButtons to timerDisplay
        timerDisplay.setAlignment(Pos.CENTER);
        timerDisplay.setPadding(new Insets(10,10,10,20));
        timerDisplay.setMargin(timer, new Insets(0, 0, 20, 0));
        timerDisplay.getChildren().addAll(sessionNumber, timer);

        //add sessionButtons and timerDisplay to pomodoroLayout
        pomodoroLayout.setSpacing(15);
        pomodoroLayout.getChildren().addAll(timerDisplay, sessionButtons);

        primaryStage.setTitle("Pomodoro Timer");
        primaryStage.setResizable(false);

        Timer timerObject = new Timer("Timer");
        TimerTask task = new TimerTask() {
            int currentMinute = 24;
            int currentSecond = 59;
            public void run() {
                Platform.runLater(() -> {
                    timer.setText(currentMinute + ":" + currentSecond);
                    currentSecond--;
                    if(currentSecond == -1) {
                        currentSecond = 59;
                        currentMinute--;
                    }
                });
            }
        };

        startSession.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startSession.setDisable(true);
                long delay = 1000L;
                timerObject.scheduleAtFixedRate(task, 0, delay);
            }
        });

        primaryStage.setScene(new Scene(pomodoroLayout, 390, 140));
        primaryStage.show();
    }
}