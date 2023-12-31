package pe.gob.sunat.jmarket.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
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
import pe.gob.sunat.jmarket.model.enums.UnidadMedida;
import pe.gob.sunat.jmarket.model.VentaDetalle;
import pe.gob.sunat.jmarket.model.enums.Estado;
import pe.gob.sunat.jmarket.model.enums.TipoDocumento;

public class VentaController implements Initializable {
  @FXML private ComboBox<Estado> cbEstado;
  @FXML private TextField tfId;
  @FXML private DatePicker dpFechaEmision;
  @FXML private Button btnBuscarPersona;
  @FXML private ComboBox<TipoDocumento> cbTipoDocumento;
  @FXML private TextField tfNumeroDocumento;
  @FXML private TextField tfNombreCompleto;
  @FXML private Button btnGuardar;

  @FXML private Button btnAgregar;
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
    observableList =
        FXCollections.observableArrayList(
            (VentaDetalle p) ->
                new Observable[] {
                  p.getCantidadProperty(), p.getPrecioUnitarioProperty(), p.getSubtotalProperty()
                });

    ventaDao = new VentaDaoImpl();
    personaDao = new PersonaDaoImpl();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    initUI();

    bind();
  }

  @FXML
  private void onActionBtnBuscarPersona(ActionEvent event) {
    try {
      String numeroDocumento = tfNumeroDocumento.getText().trim();
      if (numeroDocumento.equals("")) return;
      persona = personaDao.read(numeroDocumento);
      tfNombreCompleto.setText(persona.getNombreCompleto());
    } catch (SQLException ex) {
      Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  private void onActionBtnAgregar(ActionEvent event) {
    try {
      FXMLLoader fxmlLoader = App.loadFXML("VentaDetalleDialog");
      Parent parent = fxmlLoader.load();
      VentaDetalleDialogController controller =
          fxmlLoader.<VentaDetalleDialogController>getController();
      controller.setController(this);

      showDialog(parent, "Venta Detalle");
    } catch (IOException ex) {
      Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  private void onActionBtnGuardar(ActionEvent event) {
    String id = tfId.getText().trim();

    if (id.equals("")) {
      try {
        LocalDate fechaEmision = dpFechaEmision.getValue();
        double total = Double.parseDouble(tfTotal.getText().trim());

        venta = new Venta(fechaEmision, total, Estado.ACTIVO.getCodigo(), persona, observableList);

        Long idVenta = ventaDao.create(venta);
        if (idVenta > 0) {
          clearUI();
        }
      } catch (SQLException ex) {
        Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
      }
    } else {
      try {
        Estado estado = cbEstado.getValue();
        venta.setEstado(estado.getCodigo());

        ventaDao.update(venta);

        clearUI();
      } catch (SQLException ex) {
        Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  @FXML
  private void onActionBtnBuscar(ActionEvent event) {
    try {
      List<Venta> list = ventaDao.read();

      FXMLLoader fxmlLoader = App.loadFXML("VentaDialog");
      Parent parent = fxmlLoader.load();
      VentaDialogController controller = fxmlLoader.<VentaDialogController>getController();
      controller.setObservableList(list);
      controller.setController(this);

      showDialog(parent, "Venta");
    } catch (SQLException | IOException ex) {
      Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  private void onActionBtnLimpiar(ActionEvent event) {
    clearUI();

    bind();
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
        VentaDetalle ventaDetalle = (VentaDetalle) table.getSelectionModel().getSelectedItem();

        if (ventaDetalle == null) return;

        FXMLLoader fxmlLoader = App.loadFXML("VentaDetalleDialog");
        Parent parent = fxmlLoader.load();
        VentaDetalleDialogController controller =
            fxmlLoader.<VentaDetalleDialogController>getController();
        controller.setController(this);
        controller.setVentaDetalle(ventaDetalle);

        showDialog(parent, "Venta Detalle");
      } catch (IOException ex) {
        Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  private void initUI() {
    cbEstado.getItems().addAll(Estado.values());
    cbTipoDocumento.getItems().addAll(TipoDocumento.values());

    tcCodigo.setCellValueFactory(c -> c.getValue().getProducto().getCodigoProperty());
    tcDescripcion.setCellValueFactory(c -> c.getValue().getProducto().getDescripcionProperty());
    tcUnidadMedida.setCellValueFactory(
        c ->
            UnidadMedida.values()[c.getValue().getProducto().getUnidadMedida()]
                .getDescripcionProperty());
    tcCantidad.setCellValueFactory(c -> c.getValue().getCantidadProperty());
    tcPrecioUnitario.setCellValueFactory(c -> c.getValue().getPrecioUnitarioProperty());
    tcSubtotal.setCellValueFactory(c -> c.getValue().getSubtotalProperty());

    observableList.addListener(
        (Change<? extends VentaDetalle> change) -> {
          double total =
              observableList.stream().collect(Collectors.summingDouble(VentaDetalle::getSubtotal));
          tfTotal.setText(String.valueOf(total));
        });

    table.setItems(observableList);
  }

  private void clearUI() {
    venta = null;
    persona = null;

    cbEstado.setDisable(true);
    dpFechaEmision.setDisable(false);
    btnBuscarPersona.setDisable(false);
    cbTipoDocumento.setDisable(false);
    tfNumeroDocumento.setDisable(false);
    tfNombreCompleto.setDisable(true);
    btnAgregar.setDisable(false);
    table.setDisable(false);

    cbEstado.getSelectionModel().clearSelection();
    tfId.clear();
    dpFechaEmision.setValue(null);
    cbTipoDocumento.getSelectionModel().clearSelection();
    tfNumeroDocumento.clear();
    tfNombreCompleto.clear();
    observableList.clear();
  }

  private void bind() {
    btnBuscarPersona
        .disableProperty()
        .bind(
            Bindings.isNull(cbTipoDocumento.valueProperty())
                .or(Bindings.isEmpty(tfNumeroDocumento.textProperty())));

    btnAgregar
        .disableProperty()
        .bind(
            Bindings.isEmpty(dpFechaEmision.getEditor().textProperty())
                .or(Bindings.isNull(cbTipoDocumento.valueProperty()))
                .or(Bindings.isEmpty(tfNumeroDocumento.textProperty()))
                .or(Bindings.isEmpty(tfNombreCompleto.textProperty())));

    btnGuardar
        .disableProperty()
        .bind(
            Bindings.isEmpty(dpFechaEmision.getEditor().textProperty())
                .or(Bindings.isNull(cbTipoDocumento.valueProperty()))
                .or(Bindings.isEmpty(tfNumeroDocumento.textProperty()))
                .or(Bindings.isEmpty(tfNombreCompleto.textProperty()))
                .or(Bindings.isEmpty(observableList)));
  }

  private void showDialog(Parent parent, String titulo) {
    Scene scene = new Scene(parent);
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(scene);
    stage.setTitle(titulo);
    stage.setResizable(false);
    stage.showAndWait();
  }

  public void add(VentaDetalle ventaDetalle) {
    observableList.add(ventaDetalle);
  }

  public void refresh() {
    table.refresh();
  }

  public void setVenta(Venta venta) {
    this.venta = venta;

    btnBuscarPersona.disableProperty().unbind();
    btnAgregar.disableProperty().unbind();

    cbEstado.setDisable(false);
    dpFechaEmision.setDisable(true);
    btnBuscarPersona.setDisable(true);
    cbTipoDocumento.setDisable(true);
    tfNumeroDocumento.setDisable(true);
    tfNombreCompleto.setDisable(true);
    btnAgregar.setDisable(true);
    table.setDisable(true);

    cbEstado.getSelectionModel().select(venta.getEstado());
    tfId.setText(venta.getId().toString());
    dpFechaEmision.setValue(venta.getFechaEmision());
    cbTipoDocumento.getSelectionModel().select(venta.getPersona().getTipoDocumento());
    tfNumeroDocumento.setText(venta.getPersona().getNumeroDocumento());
    tfNombreCompleto.setText(venta.getPersona().getNombreCompleto());

    observableList.clear();
    observableList.setAll(venta.getVentaDetalles());
  }
}
