package pe.gob.sunat.jmarket.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pe.gob.sunat.jmarket.App;
import pe.gob.sunat.jmarket.dao.PersonaDao;
import pe.gob.sunat.jmarket.impl.PersonaDaoImpl;
import pe.gob.sunat.jmarket.model.Persona;
import pe.gob.sunat.jmarket.model.Venta;
import pe.gob.sunat.jmarket.model.num.UnidadMedida;
import pe.gob.sunat.jmarket.model.VentaDetalle;
import pe.gob.sunat.jmarket.model.num.TipoDocumento;

public class VentaController implements Initializable {
  @FXML private TextField tfId;
  @FXML private DatePicker dpFechaEmision;
  @FXML private ComboBox<TipoDocumento> cbTipoDocumento;
  @FXML private TextField tfNumeroDocumento;
  @FXML private TextField tfNombreCompleto;
  @FXML private Button btnGuardar;

  @FXML private TableView<VentaDetalle> table;
  @FXML private TableColumn<VentaDetalle, String> tcCodigo;
  @FXML private TableColumn<VentaDetalle, String> tcDescripcion;
  @FXML private TableColumn<VentaDetalle, String> tcUnidadMedida;
  @FXML private TableColumn<VentaDetalle, BigDecimal> tcCantidad;
  @FXML private TableColumn<VentaDetalle, BigDecimal> tcPrecioUnitario;
  @FXML private TableColumn<VentaDetalle, BigDecimal> tcSubtotal;

  private Venta venta;
  private Persona persona;
  private ObservableList<VentaDetalle> observableList;
  private PersonaDao personaDao;

  public VentaController() {
    personaDao = new PersonaDaoImpl();
    observableList = FXCollections.observableArrayList();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    cbTipoDocumento.getItems().addAll(TipoDocumento.values());
    cbTipoDocumento.getSelectionModel().selectFirst();

    btnGuardar
        .disableProperty()
        .bind(
            Bindings.isEmpty(dpFechaEmision.getEditor().textProperty())
                .or(Bindings.isEmpty(tfNumeroDocumento.textProperty()))
                .or(Bindings.isEmpty(tfNombreCompleto.textProperty())));

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
  private void onActionBtnAgregar(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = App.loadFXML("VentaDetalleDialog");
      Parent parent = fxmlLoader.load();
      VentaDetalleController controller = fxmlLoader.<VentaDetalleController>getController();
      controller.setObservableList(observableList);

      Scene scene = new Scene(parent);
      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setScene(scene);
      stage.setTitle("Producto");
      stage.setResizable(false);
      stage.showAndWait();
    } catch (IOException ex) {
      Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  private void onActionBtnGuardar(ActionEvent event) {}

  @FXML
  private void onActionBtnLimpiar(ActionEvent event) {}

  private void initTable() {
    tcCodigo.setCellValueFactory(
        c -> new SimpleStringProperty(c.getValue().getProducto().getCodigo()));
    tcDescripcion.setCellValueFactory(
        c -> new SimpleStringProperty(c.getValue().getProducto().getDescripcion()));
    tcUnidadMedida.setCellValueFactory(
        c ->
            new SimpleStringProperty(
                UnidadMedida.values()[c.getValue().getProducto().getUnidadMedida()]
                    .getDescripcion()));
    tcCantidad.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getCantidad()));
    tcPrecioUnitario.setCellValueFactory(
        c -> new SimpleObjectProperty<>(c.getValue().getPrecioUnitario()));
    tcSubtotal.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getSubtotal()));

    table.setItems(observableList);
  }
}
