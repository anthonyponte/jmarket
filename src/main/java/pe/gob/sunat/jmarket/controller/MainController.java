/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package pe.gob.sunat.jmarket.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import pe.gob.sunat.jmarket.App;
import pe.gob.sunat.jmarket.model.Usuario;

/**
 * FXML Controller class
 *
 * @author Anthony Ponte
 */
public class MainController implements Initializable {
  @FXML private BorderPane bpMain;
  @FXML private MenuItem miUsuario;
  @FXML private MenuItem miUsuarios;
  @FXML private Label lblUsuario;

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    miUsuario.setOnAction(
        (ActionEvent t) -> {
          try {
            FXMLLoader fxmlLoader = App.loadFXML("UsuarioView");
            Parent parent = fxmlLoader.load();
            fxmlLoader.<UsuarioController>getController();

            bpMain.setCenter(parent);
          } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
          }
        });

    miUsuarios.setOnAction(
        (ActionEvent t) -> {
          try {
            FXMLLoader fxmlLoader = App.loadFXML("UsuarioTableView");
            Parent parent = fxmlLoader.load();
            fxmlLoader.<UsuarioTableController>getController();

            bpMain.setCenter(parent);
          } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
          }
        });
  }

  public void setUsuario(Usuario usuario) {
    String nombre =
        usuario.getPersona().getPrimerNombre() + " " + usuario.getPersona().getApellidoPaterno();
    lblUsuario.setText("Bienvenido " + nombre + " !");
  }
}
