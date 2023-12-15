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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.remixicon.RemixiconAL;
import pe.gob.sunat.jmarket.App;
import pe.gob.sunat.jmarket.dao.UsuarioDao;
import pe.gob.sunat.jmarket.idao.IUsuarioDao;
import pe.gob.sunat.jmarket.model.Usuario;
import pe.gob.sunat.jmarket.util.TextFieldFormat;

/**
 * FXML Controller class
 *
 * @author Anthony Ponte
 */
public class LoginController implements Initializable {
  @FXML private TextField txtNombreUsuario;
  @FXML private PasswordField txtContrasena;
  @FXML private Button btnEntrar;
  private UsuarioDao dao;

  public LoginController() {
    dao = new IUsuarioDao();
  }

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    initUI();

    btnEntrar.setOnAction(
        (ActionEvent t) -> {
          String nombreUsuario = txtNombreUsuario.getText().toUpperCase();
          String contrasena = txtContrasena.getText();

          Long id = dao.validate(nombreUsuario, contrasena);

          if (id > 0) {
            try {
              Stage old = (Stage) btnEntrar.getScene().getWindow();
              old.close();
              System.out.println("id " + id);
              Usuario usuario = dao.read(id);
              FXMLLoader fxmlLoader = App.loadFXML("MainView");
              Parent parent = fxmlLoader.load();
              MainController mainController = fxmlLoader.<MainController>getController();
              Scene scene = new Scene(parent);
              Stage stage = new Stage();

              mainController.setUsuario(usuario);
              stage.setScene(scene);
              stage.setTitle("JMarket");
              stage.setResizable(true);
              stage.show();
            } catch (IOException ex) {
              Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        });
  }

  private void initUI() {
    TextFieldFormat.toUpperCase(txtNombreUsuario);
    btnEntrar.setGraphic(FontIcon.of(RemixiconAL.LOGIN_BOX_LINE, 16));
  }
}
