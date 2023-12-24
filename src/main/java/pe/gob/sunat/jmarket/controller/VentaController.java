package pe.gob.sunat.jmarket.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pe.gob.sunat.jmarket.App;
import pe.gob.sunat.jmarket.dao.PersonaDao;
import pe.gob.sunat.jmarket.dao.VentaDao;
import pe.gob.sunat.jmarket.impl.PersonaDaoImpl;
import pe.gob.sunat.jmarket.impl.VentaDaoImpl;
import pe.gob.sunat.jmarket.model.Persona;
import pe.gob.sunat.jmarket.model.Venta;
import pe.gob.sunat.jmarket.model.num.UnidadMedida;
import pe.gob.sunat.jmarket.model.VentaDetalle;
import pe.gob.sunat.jmarket.model.num.Estado;
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
  @FXML private TableColumn<VentaDetalle, Double> tcCantidad;
  @FXML private TableColumn<VentaDetalle, Double> tcPrecioUnitario;
  @FXML private TableColumn<VentaDetalle, Double> tcSubtotal;
  @FXML private TextField tfTotal;

  private Venta venta;
  private Persona persona;
  private ObservableList<VentaDetalle> observableList;
  private VentaDao ventaDao;
  private PersonaDao personaDao;

  public VentaController() {
    observableList = FXCollections.observableArrayList();
    ventaDao = new VentaDaoImpl();
    personaDao = new PersonaDaoImpl();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    cbTipoDocumento.getItems().addAll(TipoDocumento.values());

    btnGuardar
        .disableProperty()
        .bind(
            Bindings.isEmpty(dpFechaEmision.getEditor().textProperty())
                .or(Bindings.isEmpty(tfNumeroDocumento.textProperty()))
                .or(Bindings.isEmpty(tfNombreCompleto.textProperty()))
                .or(Bindings.isEmpty(observableList)));

    initTable();
  }

  @FXML
  private void onActionBtnBuscarPersona(ActionEvent event) {
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
  private void onActionBtnGuardar(ActionEvent event) {
    String id = tfId.getText().trim();
    LocalDate fechaEmision = dpFechaEmision.getValue();
    BigDecimal total = new BigDecimal(tfTotal.getText().trim());

    if (id.equals("")) {
      venta = new Venta();
      venta.setFechaEmision(fechaEmision);
      venta.setTotal(total);
      venta.setEstado(Estado.ACTIVO.getCodigo());
      venta.setPersona(persona);
      venta.setDetalles(observableList);

      Long idVenta = ventaDao.create(venta);
      if (idVenta > 0) {
        clearUI();
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
      VentaDetalle detalle = (VentaDetalle) table.getSelectionModel().getSelectedItem();

      if (detalle == null) return;

      observableList.remove(detalle);
    }
  }

  @FXML
  private void onMouseClickedTable(MouseEvent event) {
    if (event.getClickCount() == 2) {
      try {
        VentaDetalle detalle = (VentaDetalle) table.getSelectionModel().getSelectedItem();

        if (detalle == null) return;

        FXMLLoader fxmlLoader = App.loadFXML("VentaDetalleDialog");
        Parent parent = fxmlLoader.load();
        VentaDetalleController controller = fxmlLoader.<VentaDetalleController>getController();
        controller.setDetalle(detalle);
        controller.setTable(table);

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
  }

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

    observableList.addListener(
        (ListChangeListener.Change<? extends VentaDetalle> change) -> {
          double total =
              observableList.stream().collect(Collectors.summingDouble(VentaDetalle::getSubtotal));
          tfTotal.setText(String.valueOf(total));
        });

    table.setItems(observableList);
  }

  private void clearUI() {
    venta = null;
    persona = null;

    tfId.clear();
    dpFechaEmision.setValue(null);
    cbTipoDocumento.getSelectionModel().clearSelection();
    tfNumeroDocumento.clear();
    tfNombreCompleto.clear();

    observableList.clear();
  }
}
