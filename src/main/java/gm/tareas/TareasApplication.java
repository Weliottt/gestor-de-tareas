package gm.tareas;

import gm.tareas.view.SistemaTareasFx;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class TareasApplication {



	public static void main(String[] args) {
//		SpringApplication.run(TareasApplication.class, args);
        Application.launch(SistemaTareasFx.class,args);
	}



}
