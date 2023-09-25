package com.example.adventure;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.function.Consumer;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 350);
        stage.setScene(scene);
        EventHandler<? super KeyEvent> handler = e -> {
            if(e.getCode() == KeyCode.SPACE && !HelloController.jump)
                HelloController.jump = e.getEventType().equals(KeyEvent.KEY_PRESSED);
            if(e.getCode() == KeyCode.A)
                HelloController.left = e.getEventType().equals(KeyEvent.KEY_PRESSED);
            if(e.getCode() == KeyCode.D)
                HelloController.right = e.getEventType().equals(KeyEvent.KEY_PRESSED);
            if(e.getCode() == KeyCode.ESCAPE)
                HelloController.isPause =
                        e.getEventType().equals(KeyEvent.KEY_RELEASED) ? !HelloController.isPause : HelloController.isPause;
        };
        scene.setOnKeyPressed(handler);
        scene.setOnKeyReleased(handler);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
