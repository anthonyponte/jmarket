package pe.gob.sunat.jmarket.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
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
import pe.gob.sunat.jmarket.dao.PersonaDao;
import pe.gob.sunat.jmarket.dao.UsuarioDao;
import pe.gob.sunat.jmarket.impl.PersonaDaoImpl;
import pe.gob.sunat.jmarket.impl.UsuarioDaoImpl;
import pe.gob.sunat.jmarket.model.num.Estado;
import pe.gob.sunat.jmarket.model.Persona;
import pe.gob.sunat.jmarket.model.num.TipoDocumento;
import pe.gob.sunat.jmarket.model.num.TipoUsuario;
import pe.gob.sunat.jmarket.model.Usuario;

public class UsuarioController implements Initializable {
  @FXML private TextField tfId;
  @FXML private ComboBox<TipoDocumento> cbTipoDocumento;
  @FXML private TextField tfNumeroDocumento;
  @FXML private TextField tfNombreCompleto;
  @FXML private ComboBox<TipoUsuario> cbTipoUsuario;
  @FXML private TextField tfNombreUsuario;
  @FXML private PasswordField pfContrasena;
  @FXML private Button btnBuscar;
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
    cbTipoDocumento.getItems().addAll(TipoDocumento.values());
    cbTipoUsuario.getItems().addAll(TipoUsuario.values());
    cbTipoDocumento.getSelectionModel().selectFirst();
    cbTipoUsuario.getSelectionModel().selectFirst();

    btnGuardar
        .disableProperty()
        .bind(
            Bindings.isEmpty(tfNumeroDocumento.textProperty())
                .or(Bindings.isEmpty(tfNombreCompleto.textProperty()))
                .or(Bindings.isEmpty(tfNombreUsuario.textProperty()))
                .or(Bindings.isEmpty(pfContrasena.textProperty())));

    initTable();
  }

  @FXML
  private void onActionBtnBuscar(ActionEvent event) {
    String numeroDocumento = tfNumeroDocumento.getText().trim();
    if (numeroDocumento.equals("")) return;
    persona = personaDao.read(numeroDocumento);
    tfNombreCompleto.setText(persona.getNombreCompleto());
  }

  @FXML
  private void onActionBtnGuardar(ActionEvent event) {
    String id = tfId.getText().trim();
    int tipoUsuario = cbTipoUsuario.getValue().getCodigo();
    String nombreUsuario = tfNombreUsuario.getText().trim();
    String contrasena = pfContrasena.getText().trim();

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

      cbTipoDocumento.setDisable(true);
      tfNumeroDocumento.setDisable(true);
      btnBuscar.setDisable(true);

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
  }

  private void clearUI() {
    usuario = null;
    persona = null;

    cbTipoDocumento.setDisable(false);
    tfNumeroDocumento.setDisable(false);
    btnBuscar.setDisable(false);

    tfId.clear();
    cbTipoDocumento.getSelectionModel().clearSelection();
    tfNumeroDocumento.clear();
    tfNombreCompleto.clear();
    cbTipoUsuario.getSelectionModel().clearSelection();
    tfNombreUsuario.clear();
    pfContrasena.clear();
  }
}
