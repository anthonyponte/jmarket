/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package pe.gob.sunat.jmarket.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anthony Ponte
 */
public class MainController implements Initializable {

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  private void setRoot() throws IOException {
    Stage stage = new Stage();
    Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("JMarket");
    stage.show();
  }
}
