/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package pe.gob.sunat.jmarket.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pe.gob.sunat.jmarket.dao.PersonaDao;
import pe.gob.sunat.jmarket.dao.UsuarioDao;
import pe.gob.sunat.jmarket.idao.IPersonaDao;
import pe.gob.sunat.jmarket.idao.IUsuarioDao;
import pe.gob.sunat.jmarket.model.Estado;
import pe.gob.sunat.jmarket.model.Persona;
import pe.gob.sunat.jmarket.model.TipoDocumento;
import pe.gob.sunat.jmarket.model.TipoUsuario;
import pe.gob.sunat.jmarket.model.Usuario;
import pe.gob.sunat.jmarket.util.TextFieldFormat;

/**
 * FXML Controller class
 *
 * @author anthonyponte
 */
public class UsuarioDialogController implements Initializable {
  @FXML private ComboBox<TipoDocumento> cbxTipoDocumento;
  @FXML private TextField txtNumeroDocumento;
  @FXML private Button btnBuscar;
  @FXML private TextField txtPrimerNombre;
  @FXML private TextField txtSegundoNombre;
  @FXML private TextField txtApellidoPaterno;
  @FXML private TextField txtApellidoMaterno;
  @FXML private ComboBox<TipoUsuario> cbxTipoUsuario;
  @FXML private TextField txtNombreUsuario;
  @FXML private PasswordField txtContrasena;
  @FXML private Button btnGuardar;
  private ObservableList<Usuario> observableList;
  private UsuarioDao usuarioDao;
  private PersonaDao personaDao;

  public UsuarioDialogController() {
    usuarioDao = new IUsuarioDao();
    personaDao = new IPersonaDao();
  }

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    cbxTipoDocumento.getItems().addAll(TipoDocumento.values());
    cbxTipoUsuario.getItems().addAll(TipoUsuario.values());

    TextFieldFormat.toUpperCase(txtPrimerNombre);
    TextFieldFormat.toUpperCase(txtSegundoNombre);
    TextFieldFormat.toUpperCase(txtApellidoPaterno);
    TextFieldFormat.toUpperCase(txtApellidoMaterno);
    TextFieldFormat.toUpperCase(txtNombreUsuario);

    btnBuscar.setOnAction(
        (ActionEvent t) -> {
          String numeroDocumento = txtNumeroDocumento.getText().trim();
          Persona persona = personaDao.read(numeroDocumento);
        });

    btnGuardar.setOnAction(
        (ActionEvent t) -> {
          int tipoDocumento = cbxTipoDocumento.getValue().getCodigo();
          String numeroDocumento = txtNumeroDocumento.getText().trim();
          String primerNombre = txtPrimerNombre.getText().trim();
          String segundoNombre = txtSegundoNombre.getText().trim();
          String apellidoPaterno = txtApellidoPaterno.getText().trim();
          String apellidoMaterno = txtApellidoMaterno.getText().trim();
          int tipoUsuario = cbxTipoUsuario.getValue().getCodigo();
          String nombreUsuario = txtNombreUsuario.getText().trim();
          String contrasena = txtContrasena.getText().trim();

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

            Usuario usuario =
                new Usuario(
                    tipoUsuario, nombreUsuario, contrasena, Estado.ACTIVO.getCodigo(), persona);
            Long idUsuario = usuarioDao.create(usuario);
            if (idUsuario > 0) {
              observableList.add(usuario);

              Stage stage = (Stage) btnGuardar.getScene().getWindow();
              stage.close();
            }
          }
        });
  }

  public void setObservableList(ObservableList<Usuario> observableList) {
    this.observableList = observableList;
  }
}
