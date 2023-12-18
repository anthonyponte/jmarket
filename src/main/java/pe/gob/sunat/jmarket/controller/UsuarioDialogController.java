/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package pe.gob.sunat.jmarket.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.remixicon.RemixiconMZ;
import pe.gob.sunat.jmarket.dao.PersonaDao;
import pe.gob.sunat.jmarket.dao.UsuarioDao;
import pe.gob.sunat.jmarket.idao.IPersonaDao;
import pe.gob.sunat.jmarket.idao.IUsuarioDao;
import pe.gob.sunat.jmarket.model.Estado;
import pe.gob.sunat.jmarket.model.Persona;
import pe.gob.sunat.jmarket.model.TipoDocumento;
import pe.gob.sunat.jmarket.model.TipoUsuario;
import pe.gob.sunat.jmarket.model.Usuario;
import pe.gob.sunat.jmarket.util.MyTextFieldFormat;

/**
 * FXML Controller class
 *
 * @author anthonyponte
 */
public class UsuarioDialogController implements Initializable {
  @FXML private ComboBox<TipoDocumento> cbxTipoDocumento;
  @FXML private TextField txtNumeroDocumento;
  @FXML private TextField txtPrimerNombre;
  @FXML private TextField txtSegundoNombre;
  @FXML private TextField txtApellidoPaterno;
  @FXML private TextField txtApellidoMaterno;
  @FXML private ComboBox<TipoUsuario> cbxTipoUsuario;
  @FXML private TextField txtNombreUsuario;
  @FXML private PasswordField txtContrasena;
  @FXML private Button btnGuardar;
  private Usuario usuario;
  private UsuarioDao usuarioDao;
  private PersonaDao personaDao;

  public UsuarioDialogController() {
    usuarioDao = new IUsuarioDao();
    personaDao = new IPersonaDao();
  }

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    initUI();

    btnGuardar.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent t) {
            int tipoDocumento = cbxTipoDocumento.getValue().getCodigo();
            String numeroDocumento = txtNumeroDocumento.getText().trim();
            String primerNombre = txtPrimerNombre.getText().trim();
            String segundoNombre = txtSegundoNombre.getText().trim();
            String apellidoPaterno = txtApellidoPaterno.getText().trim();
            String apellidoMaterno = txtApellidoMaterno.getText().trim();
            int tipoUsuario = cbxTipoUsuario.getValue().getCodigo();
            String nombreUsuario = txtNombreUsuario.getText().trim();
            String contrasena = txtContrasena.getText().trim();

            if (usuario == null) {
              Persona persona =
                  new Persona(
                      tipoDocumento,
                      numeroDocumento,
                      primerNombre,
                      segundoNombre,
                      apellidoPaterno,
                      apellidoMaterno,
                      Estado.ACTIVO.getCodigo());

              Long idPersona = personaDao.create(persona);

              if (idPersona > 0) {
                persona.setId(idPersona);

                usuario =
                    new Usuario(
                        tipoUsuario, nombreUsuario, contrasena, Estado.ACTIVO.getCodigo(), persona);

                Long idUsuario = usuarioDao.create(usuario);
                if (idUsuario > 0) {
                  cbxTipoDocumento.getSelectionModel().clearSelection();
                  txtNumeroDocumento.clear();
                  txtPrimerNombre.clear();
                  txtSegundoNombre.clear();
                  txtApellidoPaterno.clear();
                  txtApellidoMaterno.clear();
                  cbxTipoUsuario.getSelectionModel().clearSelection();
                  txtNombreUsuario.clear();
                  txtContrasena.clear();

                  Alert alert = new Alert(AlertType.INFORMATION);
                  alert.setTitle("Guardado");
                  alert.setHeaderText(null);
                  alert.setContentText("Se guardo el registro correctamente");
                  alert.show();
                }
              }
            } else {
              usuario.setTipoUsuario(tipoUsuario);
              usuario.setNombreUsuario(nombreUsuario);
              usuario.setContrasena(contrasena);

              usuarioDao.update(usuario);

              Alert alert = new Alert(AlertType.INFORMATION);
              alert.setTitle("Actualizado");
              alert.setHeaderText(null);
              alert.setContentText("Se actualizo el registro correctamente");
              alert.show();
            }
          }
        });
  }

  private void initUI() {
    btnGuardar.setGraphic(FontIcon.of(RemixiconMZ.SAVE_3_LINE, 16));

    cbxTipoDocumento.getItems().addAll(TipoDocumento.values());
    cbxTipoUsuario.getItems().addAll(TipoUsuario.values());

    MyTextFieldFormat.toUpperCase(txtPrimerNombre);
    MyTextFieldFormat.toUpperCase(txtSegundoNombre);
    MyTextFieldFormat.toUpperCase(txtApellidoPaterno);
    MyTextFieldFormat.toUpperCase(txtApellidoMaterno);
    MyTextFieldFormat.toUpperCase(txtNombreUsuario);

    cbxTipoDocumento.requestFocus();
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;

    cbxTipoDocumento.setDisable(true);
    txtNumeroDocumento.setEditable(false);
    txtPrimerNombre.setEditable(false);
    txtSegundoNombre.setEditable(false);
    txtApellidoPaterno.setEditable(false);
    txtApellidoMaterno.setEditable(false);
    cbxTipoUsuario.setDisable(false);
    txtNombreUsuario.setEditable(true);
    txtContrasena.setEditable(true);

    cbxTipoDocumento.getSelectionModel().select(this.usuario.getPersona().getTipoDocumento());
    txtNumeroDocumento.setText(this.usuario.getPersona().getNumeroDocumento());
    txtPrimerNombre.setText(this.usuario.getPersona().getPrimerNombre());
    txtSegundoNombre.setText(this.usuario.getPersona().getSegundoNombre());
    txtApellidoPaterno.setText(this.usuario.getPersona().getApellidoPaterno());
    txtApellidoMaterno.setText(this.usuario.getPersona().getApellidoMaterno());
    cbxTipoUsuario.getSelectionModel().select(this.usuario.getTipoUsuario());
    txtNombreUsuario.setText(this.usuario.getNombreUsuario());
    txtContrasena.setText(this.usuario.getContrasena());

    cbxTipoUsuario.requestFocus();
  }
}
