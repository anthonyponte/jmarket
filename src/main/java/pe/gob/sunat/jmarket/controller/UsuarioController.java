package pe.gob.sunat.jmarket.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import pe.gob.sunat.jmarket.dao.PersonaDao;
import pe.gob.sunat.jmarket.dao.UsuarioDao;
import pe.gob.sunat.jmarket.impl.PersonaDaoImpl;
import pe.gob.sunat.jmarket.impl.UsuarioDaoImpl;
import pe.gob.sunat.jmarket.model.enums.Estado;
import pe.gob.sunat.jmarket.model.Persona;
import pe.gob.sunat.jmarket.model.enums.TipoDocumento;
import pe.gob.sunat.jmarket.model.enums.TipoUsuario;
import pe.gob.sunat.jmarket.model.Usuario;

public class UsuarioController implements Initializable {
  @FXML private ComboBox<Estado> cbEstado;
  @FXML private TextField tfId;
  @FXML private ComboBox<TipoDocumento> cbTipoDocumento;
  @FXML private TextField tfNumeroDocumento;
  @FXML private TextField tfNombreCompleto;
  @FXML private ComboBox<TipoUsuario> cbTipoUsuario;
  @FXML private TextField tfNombreUsuario;
  @FXML private PasswordField pfContrasena;
  @FXML private Button btnBuscarPersona;
  @FXML private Button btnGuardar;

  @FXML private TextField tfFiltro;
  @FXML private TableView<Usuario> table;
  @FXML private TableColumn<Usuario, Long> tcId;
  @FXML private TableColumn<Usuario, String> tcTipoUsuario;
  @FXML private TableColumn<Usuario, String> tcNombreUsuario;
  @FXML private TableColumn<Usuario, String> tcEstado;

  private Usuario usuario;
  private Persona persona;
  private ObservableList<Usuario> observableList;
  private UsuarioDao usuarioDao;
  private PersonaDao personaDao;

  public UsuarioController() {
    observableList = FXCollections.observableArrayList();
    usuarioDao = new UsuarioDaoImpl();
    personaDao = new PersonaDaoImpl();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    cbEstado.getItems().addAll(Estado.values());
    cbTipoDocumento.getItems().addAll(TipoDocumento.values());
    cbTipoUsuario.getItems().addAll(TipoUsuario.values());

    btnGuardar
        .disableProperty()
        .bind(
            Bindings.isNull(cbTipoDocumento.valueProperty())
                .or(Bindings.isEmpty(tfNumeroDocumento.textProperty()))
                .or(Bindings.isEmpty(tfNombreCompleto.textProperty()))
                .or(Bindings.isNull(cbTipoUsuario.valueProperty()))
                .or(Bindings.isEmpty(tfNombreUsuario.textProperty()))
                .or(Bindings.isEmpty(pfContrasena.textProperty())));

    initTable();
  }

  @FXML
  private void onActionBtnBuscarPersona(ActionEvent event) {
    try {
      String numeroDocumento = tfNumeroDocumento.getText().trim();
      if (numeroDocumento.equals("")) return;
      persona = personaDao.read(numeroDocumento);
      tfNombreCompleto.setText(persona.getNombreCompleto());
    } catch (SQLException ex) {
      Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  private void onActionBtnGuardar(ActionEvent event) {
    String id = tfId.getText().trim();
    if (id.equals("")) {
      try {
        int tipoUsuario = cbTipoUsuario.getValue().getCodigo();
        String nombreUsuario = tfNombreUsuario.getText().trim();
        String contrasena = pfContrasena.getText().trim();

        usuario =
            new Usuario(tipoUsuario, nombreUsuario, contrasena, Estado.ACTIVO.getCodigo(), persona);

        Long idUsuario = usuarioDao.create(usuario);
        if (idUsuario > 0) {
          usuario.setId(idUsuario);

          observableList.add(usuario);

          clearUI();
        }
      } catch (SQLException ex) {
        Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
      }
    } else {
      try {
        int estado = cbEstado.getValue().getCodigo();
        int tipoUsuario = cbTipoUsuario.getValue().getCodigo();
        String nombreUsuario = tfNombreUsuario.getText().trim();
        String contrasena = pfContrasena.getText().trim();

        usuario.setEstado(estado);
        usuario.setTipoUsuario(tipoUsuario);
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setContrasena(contrasena);
        usuarioDao.update(usuario);

        table.refresh();

        clearUI();
      } catch (SQLException ex) {
        Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  @FXML
  private void onActionBtnLimpiar(ActionEvent event) {
    clearUI();
  }

  @FXML
  private void onKeyPressedTable(KeyEvent event) {
    if (event.getCode().equals(KeyCode.DELETE)) {
      try {
        Usuario usuario = (Usuario) table.getSelectionModel().getSelectedItem();

        if (usuario == null) return;

        usuarioDao.delete(usuario.getId());
        observableList.remove(usuario);
      } catch (SQLException ex) {
        Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  @FXML
  private void onMouseClickedTable(MouseEvent event) {
    if (event.getClickCount() == 2) {
      usuario = (Usuario) table.getSelectionModel().getSelectedItem();

      if (usuario == null) return;

      cbEstado.setDisable(false);
      cbTipoDocumento.setDisable(true);
      tfNumeroDocumento.setDisable(true);
      btnBuscarPersona.setDisable(true);

      cbEstado.getSelectionModel().select(usuario.getEstado());
      tfId.setText(usuario.getId().toString());
      cbTipoDocumento.getSelectionModel().select(usuario.getPersona().getTipoDocumento());
      tfNumeroDocumento.setText(usuario.getPersona().getNumeroDocumento());
      tfNombreCompleto.setText(usuario.getPersona().getNombreCompleto());
      cbTipoUsuario.getSelectionModel().select(usuario.getTipoUsuario());
      tfNombreUsuario.setText(usuario.getNombreUsuario());
      pfContrasena.setText(usuario.getContrasena());
    }
  }

  private void initTable() {
    try {
      tcId.setCellValueFactory(c -> c.getValue().getIdProperty());
      tcTipoUsuario.setCellValueFactory(
          c -> TipoUsuario.values()[c.getValue().getTipoUsuario()].getDescripcionProperty());
      tcNombreUsuario.setCellValueFactory(c -> c.getValue().getNombreUsuarioProperty());
      tcEstado.setCellValueFactory(
          c -> Estado.values()[c.getValue().getEstado()].getDescripcionProperty());

      FilteredList<Usuario> filteredList = new FilteredList<>(observableList, p -> true);
      table.setItems(filteredList);

      tfFiltro
          .textProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                filteredList.setPredicate(
                    u -> {
                      if (newValue == null || newValue.isEmpty()) {
                        return true;
                      }

                      String value = newValue.toLowerCase();

                      if (TipoUsuario.values()[u.getTipoUsuario()]
                          .getDescripcion()
                          .toLowerCase()
                          .contains(value)) {
                        return true;
                      } else if (u.getNombreUsuario().toLowerCase().contains(value)) {
                        return true;
                      } else if (Estado.values()[u.getEstado()]
                          .getDescripcion()
                          .toLowerCase()
                          .contains(value)) {
                        return true;
                      }

                      return false;
                    });
              });

      List<Usuario> list = usuarioDao.read();
      observableList.clear();
      observableList.setAll(list);
    } catch (SQLException ex) {
      Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void clearUI() {
    usuario = null;
    persona = null;

    cbEstado.setDisable(true);
    cbTipoDocumento.setDisable(false);
    tfNumeroDocumento.setDisable(false);
    btnBuscarPersona.setDisable(false);

    cbEstado.getSelectionModel().clearSelection();
    tfId.clear();
    cbTipoDocumento.getSelectionModel().clearSelection();
    tfNumeroDocumento.clear();
    tfNombreCompleto.clear();
    cbTipoUsuario.getSelectionModel().clearSelection();
    tfNombreUsuario.clear();
    pfContrasena.clear();
  }
}
