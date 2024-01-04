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
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import pe.gob.sunat.jmarket.App;
import pe.gob.sunat.jmarket.model.Usuario;

public class MainController implements Initializable {
  @FXML private MenuItem miDashboard;
  @FXML private MenuItem miPersona;
  @FXML private MenuItem miUsuario;
  @FXML private MenuItem miProducto;
  @FXML private MenuItem miVenta;
  @FXML private Menu mnUsuario;
  @FXML private BorderPane bpMain;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    try {
      FXMLLoader fXMLLoader = App.loadFXML("DashboardView");
      Parent parent = fXMLLoader.load();
      fXMLLoader.<DashboardController>getController();
      bpMain.setCenter(parent);
    } catch (IOException ex) {
      Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  private void onActionMenuItem(ActionEvent event) {
    try {
      MenuItem menuItem = (MenuItem) event.getSource();
      Parent parent = null;
      if (miDashboard == menuItem) {
        FXMLLoader fXMLLoader = App.loadFXML("DashboardView");
        parent = fXMLLoader.load();
        fXMLLoader.<DashboardController>getController();
      } else if (miPersona == menuItem) {
        FXMLLoader fXMLLoader = App.loadFXML("PersonaView");
        parent = fXMLLoader.load();
        fXMLLoader.<UsuarioController>getController();
      } else if (miUsuario == menuItem) {
        FXMLLoader fXMLLoader = App.loadFXML("UsuarioView");
        parent = fXMLLoader.load();
        fXMLLoader.<UsuarioController>getController();
      } else if (miProducto == menuItem) {
        FXMLLoader fXMLLoader = App.loadFXML("ProductoView");
        parent = fXMLLoader.load();
        fXMLLoader.<ProductoController>getController();
      } else if (miVenta == menuItem) {
        FXMLLoader fXMLLoader = App.loadFXML("VentaView");
        parent = fXMLLoader.load();
        fXMLLoader.<ProductoController>getController();
      }
      bpMain.setCenter(parent);
    } catch (IOException ex) {
      Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void setUsuario(Usuario usuario) {
    mnUsuario.setText(usuario.getPersona().getNombreApellido());
  }
}