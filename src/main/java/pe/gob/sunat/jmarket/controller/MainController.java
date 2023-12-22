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
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.remixicon.RemixiconAL;
import org.kordamp.ikonli.remixicon.RemixiconMZ;
import pe.gob.sunat.jmarket.App;
import pe.gob.sunat.jmarket.model.Usuario;

public class MainController implements Initializable {
  @FXML private Menu menu;
  @FXML private MenuItem miPersona;
  @FXML private MenuItem miUsuario;
  @FXML private MenuItem miProducto;
  @FXML private BorderPane bpMain;
  @FXML private MenuItem miVenta;
  @FXML private Menu menuUsuario;
  @FXML private MenuItem miSalir;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    initUI();

    miPersona.setOnAction(
        (ActionEvent t) -> {
          try {
            FXMLLoader fXMLLoader = App.loadFXML("PersonaView");
            Parent parent = fXMLLoader.load();
            fXMLLoader.<UsuarioController>getController();
            bpMain.setCenter(parent);
          } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
          }
        });

    miUsuario.setOnAction(
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

    miProducto.setOnAction(
        (ActionEvent t) -> {
          try {
            FXMLLoader fXMLLoader = App.loadFXML("ProductoView");
            Parent parent = fXMLLoader.load();
            fXMLLoader.<ProductoController>getController();
            bpMain.setCenter(parent);
          } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
          }
        });

    miVenta.setOnAction(
        (ActionEvent t) -> {
          try {
            FXMLLoader fXMLLoader = App.loadFXML("VentaView");
            Parent parent = fXMLLoader.load();
            fXMLLoader.<ProductoController>getController();
            bpMain.setCenter(parent);
          } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
          }
        });
  }

  private void initUI() {
    menu.setGraphic(FontIcon.of(RemixiconMZ.MENU_LINE, 16));
    miPersona.setGraphic(FontIcon.of(RemixiconMZ.USER_LINE, 16));
    miUsuario.setGraphic(FontIcon.of(RemixiconAL.LOCK_PASSWORD_LINE, 16));
    miProducto.setGraphic(FontIcon.of(RemixiconAL.BARCODE_BOX_LINE, 16));
    miVenta.setGraphic(FontIcon.of(RemixiconAL.BILL_LINE, 16));
    miSalir.setGraphic(FontIcon.of(RemixiconAL.LOGOUT_BOX_LINE, 16));
  }

  public void setUsuario(Usuario usuario) {
    String nombre =
        usuario.getPersona().getPrimerNombre() + " " + usuario.getPersona().getApellidoPaterno();
    menuUsuario.setText(nombre);
  }
}
