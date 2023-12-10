package pe.gob.sunat.jmarket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** JavaFX App */
public class App extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    Parent parent = loadFXML("LoginView").load();
    Scene scene = new Scene(parent);
    stage.setScene(scene);
    stage.setTitle("JMarkey");
    stage.setResizable(false);
    stage.show();
  }

  public static FXMLLoader loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    return fxmlLoader;
  }

  public static void main(String[] args) {
    launch();
  }
}