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
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import pe.gob.sunat.jmarket.App;
import pe.gob.sunat.jmarket.model.Usuario;

public class MainController implements Initializable {
  @FXML private BorderPane bpMain;
  @FXML private Menu mnUsuario;

  @Override
  public void initialize(URL url, ResourceBundle rb) {}

  @FXML
  private void onActionMiPersona(ActionEvent event) {
    try {
      FXMLLoader fXMLLoader = App.loadFXML("PersonaView");
      Parent parent = fXMLLoader.load();
      fXMLLoader.<UsuarioController>getController();
      bpMain.setCenter(parent);
    } catch (IOException ex) {
      Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  private void onActionMiUsuario(ActionEvent event) {
    try {
      FXMLLoader fXMLLoader = App.loadFXML("UsuarioView");
      Parent parent = fXMLLoader.load();
      fXMLLoader.<UsuarioController>getController();
      bpMain.setCenter(parent);
    } catch (IOException ex) {
      Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  private void onActionMiProducto(ActionEvent event) {
    try {
      FXMLLoader fXMLLoader = App.loadFXML("ProductoView");
      Parent parent = fXMLLoader.load();
      fXMLLoader.<ProductoController>getController();
      bpMain.setCenter(parent);
    } catch (IOException ex) {
      Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  private void onActionMiVenta(ActionEvent event) {
    try {
      FXMLLoader fXMLLoader = App.loadFXML("VentaView");
      Parent parent = fXMLLoader.load();
      fXMLLoader.<ProductoController>getController();
      bpMain.setCenter(parent);
    } catch (IOException ex) {
      Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void setUsuario(Usuario usuario) {
    String nombre =
        usuario.getPersona().getPrimerNombre() + " " + usuario.getPersona().getApellidoPaterno();
    mnUsuario.setText(nombre);
  }
}
