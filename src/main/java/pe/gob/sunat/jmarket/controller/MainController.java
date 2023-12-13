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
import javafx.scene.layout.BorderPane;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.remixicon.RemixiconAL;
import org.kordamp.ikonli.remixicon.RemixiconMZ;
import pe.gob.sunat.jmarket.App;
import pe.gob.sunat.jmarket.model.Usuario;

/**
 * FXML Controller class
 *
 * @author Anthony Ponte
 */
public class MainController implements Initializable {
  @FXML private BorderPane bpMain;
  @FXML private Button btnUsuario;
  @FXML private Button btnProducto;

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    initUI();

    btnUsuario.setOnAction(
        (ActionEvent t) -> {
          try {
            FXMLLoader fXMLLoader = App.loadFXML("UsuarioView");
            Parent parent = fXMLLoader.load();
            fXMLLoader.<UsuarioController>getController();
            bpMain.setCenter(parent);
          } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
          }
        });
  }

  private void initUI() {
    btnUsuario.setGraphic(FontIcon.of(RemixiconMZ.USER_LINE, 16));
    btnProducto.setGraphic(FontIcon.of(RemixiconAL.BARCODE_BOX_LINE, 16));
  }

  public void setUsuario(Usuario usuario) {
    String nombre =
        usuario.getPersona().getPrimerNombre() + " " + usuario.getPersona().getApellidoPaterno();
    // lblUsuario.setText("Bienvenido " + nombre + " !");
  }
}
