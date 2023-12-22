package pe.gob.sunat.jmarket.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.remixicon.RemixiconAL;
import org.kordamp.ikonli.remixicon.RemixiconMZ;
import pe.gob.sunat.jmarket.dao.PersonaDao;
import pe.gob.sunat.jmarket.dao.UsuarioDao;
import pe.gob.sunat.jmarket.impl.PersonaDaoImpl;
import pe.gob.sunat.jmarket.impl.UsuarioDaoImpl;
import pe.gob.sunat.jmarket.model.num.Estado;
import pe.gob.sunat.jmarket.model.Persona;
import pe.gob.sunat.jmarket.model.num.TipoDocumento;
import pe.gob.sunat.jmarket.model.num.TipoUsuario;
import pe.gob.sunat.jmarket.model.Usuario;
import pe.gob.sunat.jmarket.util.MyTextFieldFormat;

public class UsuarioController implements Initializable {
  @FXML private Button btnGuardar;
  @FXML private Button btnLimpiar;
  @FXML private TextField txtId;
  @FXML private ComboBox<TipoDocumento> cbxTipoDocumento;
  @FXML private TextField txtNumeroDocumento;
  @FXML private Button btnBuscar;
  @FXML private TextField txtNombreCompleto;
  @FXML private ComboBox<TipoUsuario> cbxTipoUsuario;
  @FXML private TextField txtNombreUsuario;
  @FXML private PasswordField txtContrasena;
  @FXML private TextField txtFiltro;
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
    initUI();

    initTable();

    FilteredList<Usuario> filteredList = new FilteredList<>(observableList, p -> true);
    table.setItems(filteredList);
  }

  @FXML
  private void onActionBtnGuardar(ActionEvent event) {
    String id = txtId.getText().trim();
    int tipoUsuario = cbxTipoUsuario.getValue().getCodigo();
    String nombreUsuario = txtNombreUsuario.getText().trim();
    String contrasena = txtContrasena.getText().trim();

    if (id.equals("")) {
      usuario = new Usuario();
      usuario.setTipoUsuario(tipoUsuario);
      usuario.setNombreUsuario(nombreUsuario);
      usuario.setContrasena(contrasena);
      usuario.setEstado(Estado.ACTIVO.getCodigo());
      usuario.setPersona(persona);

      Long idUsuario = usuarioDao.create(usuario);
      if (idUsuario > 0) {
        usuario.setId(idUsuario);

        observableList.add(usuario);

        clearUI();
      }
    } else {
      usuario.setTipoUsuario(tipoUsuario);
      usuario.setNombreUsuario(nombreUsuario);
      usuario.setContrasena(contrasena);

      usuarioDao.update(usuario);

      table.refresh();

      clearUI();
    }
  }

  @FXML
  private void onActionBtnLimpiar(ActionEvent event) {
    clearUI();
  }

  @FXML
  private void onActionBtnBuscar(ActionEvent event) {
    String numeroDocumento = txtNumeroDocumento.getText().trim();
    persona = personaDao.read(numeroDocumento);
    txtNombreCompleto.setText(persona.getNombreCompleto());
  }

  @FXML
  private void onKeyPressedTable(KeyEvent event) {
    if (event.getCode().equals(KeyCode.DELETE)) {
      Usuario usuario = (Usuario) table.getSelectionModel().getSelectedItem();
      usuarioDao.delete(usuario.getId());
      observableList.remove(usuario);
    }
  }

  @FXML
  private void onMouseClickedTable(MouseEvent event) {
    if (event.getClickCount() == 2) {
      usuario = (Usuario) table.getSelectionModel().getSelectedItem();
      table.getSelectionModel().getSelectedIndex();

      cbxTipoDocumento.setDisable(true);
      txtNumeroDocumento.setDisable(true);
      btnBuscar.setDisable(true);

      txtId.setText(usuario.getId().toString());
      cbxTipoDocumento.getSelectionModel().select(usuario.getPersona().getTipoDocumento());
      txtNumeroDocumento.setText(usuario.getPersona().getNumeroDocumento());
      txtNombreCompleto.setText(usuario.getPersona().getNombreCompleto());
      cbxTipoUsuario.getSelectionModel().select(usuario.getTipoUsuario());
      txtNombreUsuario.setText(usuario.getNombreUsuario());
      txtContrasena.setText(usuario.getContrasena());
    }
  }

  private void initUI() {
    btnGuardar.setGraphic(FontIcon.of(RemixiconMZ.SAVE_LINE, 16));
    btnLimpiar.setGraphic(FontIcon.of(RemixiconAL.ERASER_LINE, 16));

    cbxTipoDocumento.getItems().addAll(TipoDocumento.values());
    cbxTipoUsuario.getItems().addAll(TipoUsuario.values());

    MyTextFieldFormat.toUpperCase(txtNombreCompleto);
    MyTextFieldFormat.toUpperCase(txtNombreUsuario);
  }

  private void initTable() {
    tcId.setCellValueFactory(c -> new SimpleObjectProperty(c.getValue().getId()));
    tcTipoUsuario.setCellValueFactory(
        c ->
            new SimpleStringProperty(
                TipoUsuario.values()[c.getValue().getTipoUsuario()].getDescripcion()));
    tcNombreUsuario.setCellValueFactory(
        c -> new SimpleStringProperty(c.getValue().getNombreUsuario()));
    tcEstado.setCellValueFactory(
        c -> new SimpleStringProperty(Estado.values()[c.getValue().getEstado()].getDescripcion()));

    FilteredList<Usuario> filteredList = new FilteredList<>(observableList, p -> true);
    table.setItems(filteredList);

    txtFiltro
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
  }

  private void clearUI() {
    usuario = null;
    persona = null;

    cbxTipoDocumento.setDisable(false);
    txtNumeroDocumento.setDisable(false);
    btnBuscar.setDisable(false);

    txtId.clear();
    cbxTipoDocumento.getSelectionModel().clearSelection();
    txtNumeroDocumento.clear();
    txtNombreCompleto.clear();
    cbxTipoUsuario.getSelectionModel().clearSelection();
    txtNombreUsuario.clear();
    txtContrasena.clear();
  }
}
