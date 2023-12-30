package net.archasmiel.stagecontroller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SceneWrapper {

    private final Scene scene;
    private String name;
    private final int width;
    private final int height;

    public SceneWrapper(FXMLLoader loader) {
        this.name = "";
        Scene scene;
        try {
            AnchorPane pane = loader.load();
            pane.getChildren().forEach(e -> {
                if (e instanceof Label label
                        && !label.isVisible()
                        && label.getId().equals("windowName")) {
                    this.name = label.getText();
                }
            });
            this.width = (int) pane.getPrefWidth();
            this.height = (int) pane.getPrefHeight();
            scene = new Scene(pane, this.width, this.height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
