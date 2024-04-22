package com.example.demo.controller;


import com.example.demo.model.Worker;
import com.example.demo.model.dtos.TaskDto;
import com.example.demo.service.ServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class WorkerController implements Controller {
    ObservableList<TaskDto> tasks = FXCollections.observableArrayList();

    public TableView<TaskDto> tasksTable;
    public TableColumn<TaskDto, String> titleColumn;
    public TableColumn<TaskDto, String> descriptionColumn;
    public TableColumn<TaskDto, String> typeColumn;
    public TableColumn<TaskDto, String> deadlineColumn;

    private Worker loggedWorker;
    private ServiceImpl service;

    @Override
    public void update() {
        populateTasksTable();
    }

    public void setService(ServiceImpl service) {
        this.service = service;
        service.addObserver(this);
        initializeTasksTable();
    }

    private void populateTasksTable() {
        tasks.clear();
        tasks.setAll(service.getTasksForWorker(loggedWorker.getId())
                .stream()
                .map(TaskDto::new)
                .toList());
    }

    private void initializeTasksTable() {
        tasksTable.setItems(tasks);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
    }

    public void setLoggedWorker(Worker loggedWorker) {
        this.loggedWorker = loggedWorker;
        populateTasksTable();
    }

    public void logoutClicked() {
        service.removeObserver(this);
        service.stopWorking(loggedWorker.getId());
        tasksTable.getScene().getWindow().hide();
    }

    public void markAsSolvedClicked() {
        TaskDto selectedTask = tasksTable.getSelectionModel().getSelectedItem();
        if (selectedTask == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No task selected");
            alert.showAndWait();
            return;
        }
        service.markAsDone(selectedTask.getId());
        populateTasksTable();
    }
}
