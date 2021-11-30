package gui;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class ClockController {

    private AnimationTimer timer;
    private GraphicsContext gc;
    private final float brushSize = 30;

    private final long workDuration = 1;
    private final long breakDuration = 1;
    private long currentDuration = workDuration;
    private boolean isBreak = false;

    private long startNano;
    private boolean newStart;

    @FXML
    private Canvas canvas;

    @FXML TextField breakTimeText;

    @FXML TextField workTimeText;



    @FXML
    public void initialize() {

        gc = canvas.getGraphicsContext2D();
        gc.setTextAlign(TextAlignment.CENTER);

        gc.setLineWidth(brushSize);
        setWork(gc);

        timer = new AnimationTimer() {

            @Override
            public void handle(long now) {

                if(newStart) {
                    startNano = now;
                    newStart = false;
                }


                double elapsedTime = (double)(now - startNano);
                double angle = elapsedTime / TimeUnit.NANOSECONDS.convert(currentDuration, TimeUnit.MINUTES) * 360;

                String timeText = String.valueOf(TimeUnit.SECONDS.convert(now - startNano, TimeUnit.NANOSECONDS));

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                drawTimer(gc, timeText);
                gc.strokeArc(brushSize, brushSize, canvas.getWidth() - brushSize * 2,
                        canvas.getHeight() - brushSize * 2, 90, -angle, ArcType.OPEN);

                if(elapsedTime >= TimeUnit.NANOSECONDS.convert(currentDuration, TimeUnit.MINUTES)) {
                    switchCircle(gc);
                }
            }
        };
    }

    private void setWork(GraphicsContext gc) {
        isBreak = false;
        gc.setStroke(Color.LIGHTGREEN);
        currentDuration = workDuration;
        newStart = true;
    }

    private void setBreak(GraphicsContext gc) {
        isBreak = true;
        gc.setStroke(Color.TEAL);
        currentDuration = breakDuration;
        newStart = true;
    }

    private void switchCircle(GraphicsContext gc) {
        isBreak = !isBreak;
        if(isBreak) {
            setBreak(gc);
        } else {
            setWork(gc);
        }
    }

    private void drawTimer(GraphicsContext gc, String time) {

        gc.setFont(Font.font("Castellar", 100));
        gc.fillText(time, canvas.getWidth() / 2, canvas.getHeight() / 2, canvas.getWidth() * 0.5);
    }

    @FXML
    void updateBreakTime(InputMethodEvent event) {
        // Update breakDuration
    }

    @FXML
    void updateWorkTime(InputMethodEvent event) {
        // Update workDuration
    }


    @FXML
    void startClock(MouseEvent event) {
        newStart = true;
        timer.start();
    }

    @FXML
    void resetClock(MouseEvent event) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        timer.stop();
    }

}
