package gm.tareas.view;

import gm.tareas.TareasApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class SistemaTareasFx extends Application {

    ConfigurableApplicationContext applicationContext;

    @Override
    public void init(){
        this.applicationContext = new SpringApplicationBuilder(TareasApplication.class).run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(TareasApplication.class.getResource("/templates/index.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }
}
