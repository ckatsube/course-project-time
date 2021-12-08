package gui;

import gui.utility.InstanceMapper;
import gui.utility.NavigationHelper;
import gui.view.AddTaskPageController;
import gui.view.MonthlyCalendarController;
import gui.view.TaskPageController;
import gui.view.TodoListPageController;
import gui.view.WeeklyCalendarController;
import gui.viewmodel.ViewModelFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.servicesfactory.BasicObservableRepositoryFactory;
import services.servicesfactory.NotificationServiceFactory;
import services.servicesfactory.ObservableRepositoryFactory;
import services.servicesfactory.ServicesFactory;

import java.io.IOException;
import java.util.Objects;

public class GUIDriver extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ViewModelFactory factory = configure();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getResource("/monthlyCalendar.fxml")));
        Parent root = loader.load();
        ((MonthlyCalendarController) loader.getController()).init(factory.getCalendarViewModel());

        primaryStage.setResizable(false);
        primaryStage.setTitle("Project Time");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private ViewModelFactory configure() {

        ObservableRepositoryFactory repositoryFactory = new BasicObservableRepositoryFactory();
        ServicesFactory servicesFactory = new NotificationServiceFactory(repositoryFactory);
        ViewModelFactory factory = new ViewModelFactory(repositoryFactory, servicesFactory);

        try {
            repositoryFactory.makeEventRepository().loadEvents("EventData.json");
            repositoryFactory.makeTaskRepository().loadTodo("TaskData.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        InstanceMapper instanceMapper = new InstanceMapper();
        instanceMapper.addMapping(MonthlyCalendarController.class, factory.getCalendarViewModel());
        instanceMapper.addMapping(WeeklyCalendarController.class, factory.getCalendarViewModel());
        instanceMapper.addMapping(TodoListPageController.class, factory.getTodoListPageViewModel());
        instanceMapper.addMapping(AddTaskPageController.class, factory.getAddTaskPageViewModel());
        instanceMapper.addMapping(TaskPageController.class, factory.getTaskPageViewModel());
        NavigationHelper.setInstanceMap(instanceMapper);

        return factory;
    }
}
