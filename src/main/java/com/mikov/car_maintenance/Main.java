package com.mikov.car_maintenance;

import datamodel.DataSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public DataSource dataSource = new DataSource();

    @Override
    public void start(Stage stage) throws IOException {

        if (!dataSource.open()) {
            System.out.println("Can't open database!");
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 400);
        stage.setTitle("Car Maintenance");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        stage.setMinWidth(stage.getWidth());
        stage.setMinHeight(stage.getHeight());
        stage.setMaxWidth(stage.getWidth());
        stage.setMaxHeight(stage.getHeight());
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception {
        dataSource.close();
    }
}