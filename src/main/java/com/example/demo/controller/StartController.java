package com.example.demo.controller;


import com.example.demo.HelloApplication;
import com.example.demo.service.ServiceImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController implements Controller{
    private ServiceImpl service;

    @Override
    public void setService(ServiceImpl service) {
        this.service = service;
        service.addObserver(this);
    }

    @Override
    public void update() {}

    public void newWindowClicked() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/gui/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LoginController controller = fxmlLoader.getController();
        controller.setService(service);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
