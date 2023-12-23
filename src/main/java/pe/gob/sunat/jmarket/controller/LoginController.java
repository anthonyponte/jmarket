package pe.gob.sunat.jmarket.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
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
import pe.gob.sunat.jmarket.App;
import pe.gob.sunat.jmarket.dao.UsuarioDao;
import pe.gob.sunat.jmarket.impl.UsuarioDaoImpl;
import pe.gob.sunat.jmarket.model.Usuario;

public class LoginController implements Initializable {
  @FXML private TextField tfNombreUsuario;
  @FXML private PasswordField pfContrasena;
  @FXML private Button btnEntrar;

  private UsuarioDao dao;

  public LoginController() {
    dao = new UsuarioDaoImpl();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    btnEntrar
        .disableProperty()
        .bind(
            Bindings.isEmpty(tfNombreUsuario.textProperty())
                .or(Bindings.isEmpty(pfContrasena.textProperty())));
  }

  @FXML
  private void onActionBtnEntrar(ActionEvent event) {
    String nombreUsuario = tfNombreUsuario.getText().toUpperCase();
    String contrasena = pfContrasena.getText();

    Long id = dao.validate(nombreUsuario, contrasena);

    if (id > 0) {
      try {
        Stage old = (Stage) btnEntrar.getScene().getWindow();
        old.close();

        FXMLLoader fxmlLoader = App.loadFXML("MainView");
        Parent parent = fxmlLoader.load();

        Usuario usuario = dao.read(id);
        MainController controller = fxmlLoader.<MainController>getController();
        controller.setUsuario(usuario);

        Scene scene = new Scene(parent);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("JMarket");
        stage.setResizable(true);
        stage.show();
      } catch (IOException ex) {
        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
}
