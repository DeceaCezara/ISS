package com.example.demo.controller;


import com.example.demo.model.Admin;
import com.example.demo.model.Worker;
import com.example.demo.service.ServiceImpl;
import com.example.demo.utils.enums.LoginResponseType;
import com.example.demo.utils.functions.Functions;
import com.example.demo.utils.responses.LoginResponse;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController implements Controller {
    public Label addedText;
    public TextField usernameText;
    public PasswordField passwordText;

    private ServiceImpl service;

    @Override
    public void update() {}

    public void setService(ServiceImpl service) {
        this.service = service;
        service.addObserver(this);
    }

    public void onLoginButtonClick() throws IOException {
        String username = usernameText.getText();
        String password = passwordText.getText();

        LoginResponse loginResponse = service.login(username, password);
        if (loginResponse.getType() == LoginResponseType.FAILED) {
            addedText.setText("Credentials are invalid");
        } else if (loginResponse.getType() == LoginResponseType.ADMIN) {
            Stage stage = new Stage();
            Controller ctrl = Functions.fxmlLoad(stage, "/gui/admin-window.fxml");
            ctrl.setService(service);
            ((AdminController) ctrl).loggedAdmin = (Admin) loginResponse.getBody();
            closeWindow();
        } else if (loginResponse.getType() == LoginResponseType.WORKER) {
            Stage stage = new Stage();
            Controller ctrl = Functions.fxmlLoad(stage, "/gui/worker-window.fxml");
            Worker worker = (Worker) loginResponse.getBody();
            ctrl.setService(service);
            ((WorkerController) ctrl).setLoggedWorker(worker);
            service.startWorking(worker.getId());
            stage.onCloseRequestProperty().setValue(event -> service.stopWorking(worker.getId()));
            closeWindow();
        }
    }

    private void closeWindow() {
        service.removeObserver(this);
        Stage stage = (Stage) addedText.getScene().getWindow();
        stage.close();
    }
}
