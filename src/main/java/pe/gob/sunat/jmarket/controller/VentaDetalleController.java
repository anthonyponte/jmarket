package pe.gob.sunat.jmarket.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pe.gob.sunat.jmarket.dao.ProductoDao;
import pe.gob.sunat.jmarket.impl.ProductoDaoImpl;
import pe.gob.sunat.jmarket.model.Producto;
import pe.gob.sunat.jmarket.model.VentaDetalle;
import pe.gob.sunat.jmarket.model.num.Estado;
import pe.gob.sunat.jmarket.model.num.UnidadMedida;

public class VentaDetalleController implements Initializable {
  @FXML private TextField tfCodigo;
  @FXML private TextField tfDescripcion;
  @FXML private TextField tfUnidadMedida;
  @FXML private TextField tfPrecioUnitario;
  @FXML private TextField tfCantidad;
  @FXML private Button btnBuscarProducto;
  @FXML private Button btnGuardar;

  private VentaDetalle detalle;
  private Producto producto;
  private ObservableList<VentaDetalle> observableList;
  private TableView<VentaDetalle> table;
  private ProductoDao dao;

  public VentaDetalleController() {
    dao = new ProductoDaoImpl();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    btnGuardar
        .disableProperty()
        .bind(
            Bindings.isEmpty(tfCodigo.textProperty())
                .and(Bindings.isEmpty(tfDescripcion.textProperty()))
                .and(Bindings.isEmpty(tfUnidadMedida.textProperty()))
                .and(Bindings.isEmpty(tfCantidad.textProperty()))
                .and(Bindings.isEmpty(tfPrecioUnitario.textProperty())));
  }

  @FXML
  private void onActionBtnBuscarProducto(ActionEvent event) {
    String codigo = tfCodigo.getText().trim();

    if (codigo.equals("")) return;

    producto = dao.read(codigo);
    tfDescripcion.setText(producto.getDescripcion());
    tfUnidadMedida.setText(UnidadMedida.values()[producto.getUnidadMedida()].getDescripcion());
    tfPrecioUnitario.setText(String.valueOf(producto.getPrecioUnitario()));
  }

  @FXML
  private void onActionBtnGuardar(ActionEvent event) {
    double precioUnitario = Double.parseDouble(tfPrecioUnitario.getText().trim());
    double cantidad = Double.parseDouble(tfCantidad.getText().trim());
    double subtotal = precioUnitario * cantidad;

    if (detalle == null) {
      detalle = new VentaDetalle();
      detalle.setPrecioUnitario(precioUnitario);
      detalle.setCantidad(cantidad);
      detalle.setSubtotal(subtotal);
      detalle.setEstado(Estado.ACTIVO.getCodigo());
      detalle.setProducto(producto);

      observableList.add(detalle);
    } else {
      detalle.setCantidad(cantidad);
      detalle.setSubtotal(subtotal);

      table.refresh();
    }

    Stage stage = (Stage) btnGuardar.getScene().getWindow();
    stage.close();
  }

  public void setObservableList(ObservableList<VentaDetalle> observableList) {
    this.observableList = observableList;
  }

  public void setDetalle(VentaDetalle detalle) {
    this.detalle = detalle;

    tfCodigo.setDisable(true);
    btnBuscarProducto.setDisable(true);

    tfCodigo.setText(detalle.getProducto().getCodigo());
    tfDescripcion.setText(detalle.getProducto().getDescripcion());
    tfUnidadMedida.setText(
        UnidadMedida.values()[detalle.getProducto().getUnidadMedida()].getDescripcion());
    tfPrecioUnitario.setText(String.valueOf(detalle.getPrecioUnitario()));
    tfCantidad.setText(String.valueOf(detalle.getCantidad()));
  }

  public void setTable(TableView<VentaDetalle> table) {
    this.table = table;
  }
}
