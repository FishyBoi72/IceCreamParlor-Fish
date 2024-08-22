package org.example;

import org.example.utils.DataSeeder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.utils.DatabaseInitializer;
import org.example.utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize the database
        DatabaseInitializer.initialize();

        // Establish a connection to the database
        try (Connection conn = DatabaseConnection.connect()) {
            // Seed the database
            DataSeeder.seedRoles(conn);  // Pass the connection object
            DataSeeder.seedUsers(conn);  // Pass the connection object
            // Other seed methods can also be called here with the same connection object
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Load the main FXML and show the primary stage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Restaurant Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
