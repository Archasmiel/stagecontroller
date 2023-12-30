package net.archasmiel.stagecontroller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class StageController {

    private final Stage stage;

    private final Map<String, SceneWrapper> scenes;
    private final List<Supplier<Map<String, FXMLLoader>>> suppliers;

    public StageController(Stage stage) {
        this.stage = stage;
        this.scenes = new HashMap<>();
        this.suppliers = new ArrayList<>();
    }

    public void setScene(String name, boolean resizable) {
        SceneWrapper wrapper = scenes.getOrDefault(name, null);
        if (wrapper != null) {
            hideStage();
            this.stage.setResizable(resizable);
            setStageName(wrapper.getName());
            setStageSize(wrapper.getWidth(), wrapper.getHeight());
            this.stage.setScene(wrapper.getScene());
            showStage();
        }
    }

    public Scene getScene() {
        return this.stage.getScene();
    }

    public void clearSuppliers() {
        suppliers.clear();
    }

    public void addSupplier(Supplier<Map<String, FXMLLoader>> supp) {
        suppliers.add(supp);
    }

    public void updateScenes() {
        scenes.clear();
        suppliers.forEach(supp -> {
            Map<String, FXMLLoader> suppl = supp.get();
            if (suppl.isEmpty()) {
                return;
            }
            for (Map.Entry<String, FXMLLoader> entry : suppl.entrySet()) {
                if (!this.scenes.containsKey(entry.getKey())) {
                    SceneWrapper scene = new SceneWrapper(entry.getValue());
                    this.scenes.put(entry.getKey(), scene);
                } else {
                    System.err.printf("Scene [%s] exists in controller.", entry.getKey());
                }
            }
        });
    }

    public void showStage() {
        this.stage.show();
    }

    public void hideStage() {
        this.stage.hide();
    }

    public void setStageName(String stageName) {
        this.stage.setTitle(stageName);
    }

    public void setStageSize(int stageWidth, int stageHeight) {
        this.stage.setWidth(stageWidth);
        this.stage.setHeight(stageHeight);
    }
}
