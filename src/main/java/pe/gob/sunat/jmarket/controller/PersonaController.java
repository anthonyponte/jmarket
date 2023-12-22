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
import pe.gob.sunat.jmarket.impl.PersonaDaoImpl;
import pe.gob.sunat.jmarket.model.Persona;
import pe.gob.sunat.jmarket.model.num.Estado;
import pe.gob.sunat.jmarket.model.num.TipoDocumento;
import pe.gob.sunat.jmarket.util.MyTextFieldFormat;

public class PersonaController implements Initializable {
  @FXML private Button btnGuardar;
  @FXML private Button btnLimpiar;
  @FXML private TextField txtId;
  @FXML private ComboBox<TipoDocumento> cbxTipoDocumento;
  @FXML private TextField txtNumeroDocumento;
  @FXML private TextField txtPrimerNombre;
  @FXML private TextField txtSegundoNombre;
  @FXML private TextField txtApellidoPaterno;
  @FXML private TextField txtApellidoMaterno;
  @FXML private TextField txtFiltro;
  @FXML private TableView<Persona> table;
  @FXML private TableColumn<Persona, Long> tcId;
  @FXML private TableColumn<Persona, String> tcTipoDocumento;
  @FXML private TableColumn<Persona, String> tcNumeroDocumento;
  @FXML private TableColumn<Persona, String> tcPrimerNombre;
  @FXML private TableColumn<Persona, String> tcSegundoNombre;
  @FXML private TableColumn<Persona, String> tcApellidoPaterno;
  @FXML private TableColumn<Persona, String> tcApellidoMaterno;
  @FXML private TableColumn<Persona, String> tcEstado;

  private Persona persona;
  private ObservableList<Persona> observableList;
  private PersonaDao dao;

  public PersonaController() {
    observableList = FXCollections.observableArrayList();
    dao = new PersonaDaoImpl();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    initUI();

    initTable();
  }

  @FXML
  private void onActionBtnGuardar(ActionEvent event) {
    String id = txtId.getText().trim();
    int tipoDocumento = cbxTipoDocumento.getValue().getCodigo();
    String numeroDocumento = txtNumeroDocumento.getText().trim();
    String primerNombre = txtPrimerNombre.getText().trim();
    String segundoNombre = txtSegundoNombre.getText().trim();
    String apellidoPaterno = txtApellidoPaterno.getText().trim();
    String apellidoMaterno = txtApellidoMaterno.getText().trim();

    if (id.equals("")) {
      persona = new Persona();
      persona.setTipoDocumento(tipoDocumento);
      persona.setNumeroDocumento(numeroDocumento);
      persona.setPrimerNombre(primerNombre);
      persona.setSegundoNombre(segundoNombre);
      persona.setApellidoPaterno(apellidoPaterno);
      persona.setApellidoMaterno(apellidoMaterno);
      persona.setEstado(Estado.ACTIVO.getCodigo());

      Long idPersona = dao.create(persona);

      if (idPersona > 0) {
        persona.setId(idPersona);
        observableList.add(persona);

        clearUI();
      }
    } else {
      persona.setPrimerNombre(primerNombre);
      persona.setSegundoNombre(segundoNombre);
      persona.setApellidoPaterno(apellidoPaterno);
      persona.setApellidoMaterno(apellidoMaterno);

      dao.update(persona);

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
      Persona persona = (Persona) table.getSelectionModel().getSelectedItem();
      dao.delete(persona.getId());
      observableList.remove(persona);
    }
  }

  @FXML
  private void onMouseClickedTable(MouseEvent event) {
    if (event.getClickCount() == 2) {
      persona = (Persona) table.getSelectionModel().getSelectedItem();

      cbxTipoDocumento.setDisable(true);
      txtNumeroDocumento.setDisable(true);

      txtId.setText(this.persona.getId().toString());
      cbxTipoDocumento.getSelectionModel().select(this.persona.getTipoDocumento());
      txtNumeroDocumento.setText(this.persona.getNumeroDocumento());
      txtPrimerNombre.setText(this.persona.getPrimerNombre());
      txtSegundoNombre.setText(this.persona.getSegundoNombre());
      txtApellidoPaterno.setText(this.persona.getApellidoPaterno());
      txtApellidoMaterno.setText(this.persona.getApellidoMaterno());
    }
  }

  private void initUI() {
    btnGuardar.setGraphic(FontIcon.of(RemixiconMZ.SAVE_LINE, 16));
    btnLimpiar.setGraphic(FontIcon.of(RemixiconAL.ERASER_LINE, 16));

    cbxTipoDocumento.getItems().addAll(TipoDocumento.values());

    MyTextFieldFormat.toUpperCase(txtPrimerNombre);
    MyTextFieldFormat.toUpperCase(txtSegundoNombre);
    MyTextFieldFormat.toUpperCase(txtApellidoPaterno);
    MyTextFieldFormat.toUpperCase(txtApellidoMaterno);
  }

  private void initTable() {
    tcId.setCellValueFactory(c -> new SimpleObjectProperty(c.getValue().getId()));
    tcTipoDocumento.setCellValueFactory(
        c ->
            new SimpleStringProperty(
                TipoDocumento.values()[c.getValue().getTipoDocumento()].getDescripcion()));
    tcNumeroDocumento.setCellValueFactory(
        c -> new SimpleStringProperty(c.getValue().getNumeroDocumento()));
    tcPrimerNombre.setCellValueFactory(
        c -> new SimpleStringProperty(c.getValue().getPrimerNombre()));
    tcSegundoNombre.setCellValueFactory(
        c -> new SimpleStringProperty(c.getValue().getSegundoNombre()));
    tcApellidoPaterno.setCellValueFactory(
        c -> new SimpleStringProperty(c.getValue().getApellidoPaterno()));
    tcApellidoMaterno.setCellValueFactory(
        c -> new SimpleStringProperty(c.getValue().getApellidoMaterno()));
    tcEstado.setCellValueFactory(
        c -> new SimpleStringProperty(Estado.values()[c.getValue().getEstado()].getDescripcion()));

    FilteredList<Persona> filteredList = new FilteredList<>(observableList, p -> true);
    table.setItems(filteredList);

    txtFiltro
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              filteredList.setPredicate(
                  p -> {
                    if (newValue == null || newValue.isEmpty()) {
                      return true;
                    }

                    String value = newValue.toLowerCase();

                    if (p.getNumeroDocumento().toLowerCase().contains(value)) {
                      return true;
                    } else if (p.getPrimerNombre().toLowerCase().contains(value)) {
                      return true;
                    } else if (p.getSegundoNombre().toLowerCase().contains(value)) {
                      return true;
                    } else if (p.getApellidoPaterno().toLowerCase().contains(value)) {
                      return true;
                    } else if (p.getApellidoMaterno().toLowerCase().contains(value)) {
                      return true;
                    } else if (Estado.values()[p.getEstado()]
                        .getDescripcion()
                        .toLowerCase()
                        .contains(value)) {
                      return true;
                    }

                    return false;
                  });
            });

    List<Persona> list = dao.read();
    observableList.clear();
    observableList.setAll(list);
  }

  private void clearUI() {
    persona = null;

    cbxTipoDocumento.setDisable(false);
    txtNumeroDocumento.setDisable(false);

    txtId.clear();
    cbxTipoDocumento.getSelectionModel().clearSelection();
    txtNumeroDocumento.clear();
    txtPrimerNombre.clear();
    txtSegundoNombre.clear();
    txtApellidoPaterno.clear();
    txtApellidoMaterno.clear();
  }
}
