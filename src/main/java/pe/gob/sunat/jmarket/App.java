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
    Scene scene = new Scene(loadFXML("LoginView"));
    stage.setScene(scene);
    stage.setTitle("JMarkey");
    stage.setResizable(false);
    stage.show();
  }

  public static void setScene(Stage stage, String fxml, String title, boolean isResizable)
      throws IOException {
    Scene scene = new Scene(loadFXML(fxml));
    stage.setScene(scene);
    stage.setTitle(title);
    stage.setResizable(isResizable);
    stage.show();
  }

  private static Parent loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    return fxmlLoader.load();
  }

  public static void main(String[] args) {
    launch();
  }
}