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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import pe.gob.sunat.jmarket.dao.PersonaDao;
import pe.gob.sunat.jmarket.impl.PersonaDaoImpl;
import pe.gob.sunat.jmarket.model.Persona;
import pe.gob.sunat.jmarket.model.num.Estado;
import pe.gob.sunat.jmarket.model.num.TipoDocumento;

public class PersonaController implements Initializable {
  @FXML private TextField tfId;
  @FXML private ComboBox<TipoDocumento> cbTipoDocumento;
  @FXML private TextField tfNumeroDocumento;
  @FXML private TextField tfPrimerNombre;
  @FXML private TextField tfSegundoNombre;
  @FXML private TextField tfApellidoPaterno;
  @FXML private TextField tfApellidoMaterno;
  @FXML private Button btnGuardar;

  @FXML private TextField tfFiltro;
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
    cbTipoDocumento.getItems().addAll(TipoDocumento.values());

    btnGuardar
        .disableProperty()
        .bind(
            Bindings.isEmpty(tfNumeroDocumento.textProperty())
                .or(Bindings.isEmpty(tfPrimerNombre.textProperty()))
                .or(Bindings.isEmpty(tfSegundoNombre.textProperty()))
                .or(Bindings.isEmpty(tfSegundoNombre.textProperty()))
                .or(Bindings.isEmpty(tfApellidoPaterno.textProperty()))
                .or(Bindings.isEmpty(tfApellidoMaterno.textProperty())));

    initTable();
  }

  @FXML
  private void onActionBtnGuardar(ActionEvent event) {
    String id = tfId.getText().trim();
    int tipoDocumento = cbTipoDocumento.getValue().getCodigo();
    String numeroDocumento = tfNumeroDocumento.getText().trim();
    String primerNombre = tfPrimerNombre.getText().trim();
    String segundoNombre = tfSegundoNombre.getText().trim();
    String apellidoPaterno = tfApellidoPaterno.getText().trim();
    String apellidoMaterno = tfApellidoMaterno.getText().trim();

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
      
      if (persona == null) return;
      
      dao.delete(persona.getId());
      observableList.remove(persona);
    }
  }

  @FXML
  private void onMouseClickedTable(MouseEvent event) {
    if (event.getClickCount() == 2) {
      persona = (Persona) table.getSelectionModel().getSelectedItem();
      
      if (persona == null) return;

      cbTipoDocumento.setDisable(true);
      tfNumeroDocumento.setDisable(true);

      tfId.setText(persona.getId().toString());
      cbTipoDocumento.getSelectionModel().select(persona.getTipoDocumento());
      tfNumeroDocumento.setText(persona.getNumeroDocumento());
      tfPrimerNombre.setText(persona.getPrimerNombre());
      tfSegundoNombre.setText(persona.getSegundoNombre());
      tfApellidoPaterno.setText(persona.getApellidoPaterno());
      tfApellidoMaterno.setText(persona.getApellidoMaterno());
    }
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

    tfFiltro
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

    cbTipoDocumento.setDisable(false);
    tfNumeroDocumento.setDisable(false);

    tfId.clear();
    cbTipoDocumento.getSelectionModel().clearSelection();
    tfNumeroDocumento.clear();
    tfPrimerNombre.clear();
    tfSegundoNombre.clear();
    tfApellidoPaterno.clear();
    tfApellidoMaterno.clear();
  }
}
