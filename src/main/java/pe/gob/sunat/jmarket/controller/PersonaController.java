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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import pe.gob.sunat.jmarket.dao.PersonaDao;
import pe.gob.sunat.jmarket.impl.PersonaDaoImpl;
import pe.gob.sunat.jmarket.model.Persona;
import pe.gob.sunat.jmarket.model.enums.Estado;
import pe.gob.sunat.jmarket.model.enums.TipoDocumento;

public class PersonaController implements Initializable {
  @FXML private ComboBox<Estado> cbEstado;
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
    cbEstado.getItems().addAll(Estado.values());
    cbTipoDocumento.getItems().addAll(TipoDocumento.values());

    btnGuardar
        .disableProperty()
        .bind(
            Bindings.isNull(cbTipoDocumento.valueProperty())
                .or(Bindings.isEmpty(tfNumeroDocumento.textProperty()))
                .or(Bindings.isNull(cbTipoDocumento.valueProperty()))
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
    if (id.equals("")) {
      try {
        int tipoDocumento = cbTipoDocumento.getValue().getCodigo();
        String numeroDocumento = tfNumeroDocumento.getText().trim();
        String primerNombre = tfPrimerNombre.getText().trim();
        String segundoNombre = tfSegundoNombre.getText().trim();
        String apellidoPaterno = tfApellidoPaterno.getText().trim();
        String apellidoMaterno = tfApellidoMaterno.getText().trim();

        persona =
            new Persona(
                tipoDocumento,
                numeroDocumento,
                primerNombre,
                segundoNombre,
                apellidoPaterno,
                apellidoMaterno,
                Estado.ACTIVO.getCodigo());

        Long idPersona = dao.create(persona);

        if (idPersona > 0) {
          persona.setId(idPersona);
          observableList.add(persona);

          clearUI();
        }
      } catch (SQLException ex) {
        Logger.getLogger(PersonaController.class.getName()).log(Level.SEVERE, null, ex);
      }
    } else {
      try {
        int estado = cbEstado.getValue().getCodigo();
        persona.setEstado(estado);

        dao.update(persona);

        table.refresh();

        clearUI();
      } catch (SQLException ex) {
        Logger.getLogger(PersonaController.class.getName()).log(Level.SEVERE, null, ex);
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
        Persona persona = (Persona) table.getSelectionModel().getSelectedItem();

        if (persona == null) return;

        dao.delete(persona.getId());
        observableList.remove(persona);
      } catch (SQLException ex) {
        Logger.getLogger(PersonaController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  @FXML
  private void onMouseClickedTable(MouseEvent event) {
    if (event.getClickCount() == 2) {
      persona = (Persona) table.getSelectionModel().getSelectedItem();

      if (persona == null) return;

      cbEstado.setDisable(false);
      cbTipoDocumento.setDisable(true);
      tfNumeroDocumento.setDisable(true);
      tfPrimerNombre.setDisable(true);
      tfSegundoNombre.setDisable(true);
      tfApellidoPaterno.setDisable(true);
      tfApellidoMaterno.setDisable(true);

      cbEstado.getSelectionModel().select(persona.getEstado());
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
    try {
      tcId.setCellValueFactory(c -> c.getValue().getIdProperty());
      tcTipoDocumento.setCellValueFactory(
          c -> TipoDocumento.values()[c.getValue().getTipoDocumento()].getDescripcionProperty());
      tcNumeroDocumento.setCellValueFactory(c -> c.getValue().getNumeroDocumentoProperty());
      tcPrimerNombre.setCellValueFactory(c -> c.getValue().getPrimerNombreProperty());
      tcSegundoNombre.setCellValueFactory(c -> c.getValue().getSegundoNombreProperty());
      tcApellidoPaterno.setCellValueFactory(c -> c.getValue().getApellidoPaternoProperty());
      tcApellidoMaterno.setCellValueFactory(c -> c.getValue().getApellidoMaternoProperty());
      tcEstado.setCellValueFactory(
          c -> Estado.values()[c.getValue().getEstado()].getDescripcionProperty());

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
    } catch (SQLException ex) {
      Logger.getLogger(PersonaController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void clearUI() {
    persona = null;

    cbEstado.setDisable(true);
    cbTipoDocumento.setDisable(false);
    tfNumeroDocumento.setDisable(false);
    tfPrimerNombre.setDisable(false);
    tfSegundoNombre.setDisable(false);
    tfApellidoPaterno.setDisable(false);
    tfApellidoMaterno.setDisable(false);

    tfId.clear();
    cbEstado.getSelectionModel().clearSelection();
    cbTipoDocumento.getSelectionModel().clearSelection();
    tfNumeroDocumento.clear();
    tfPrimerNombre.clear();
    tfSegundoNombre.clear();
    tfApellidoPaterno.clear();
    tfApellidoMaterno.clear();
  }
}
