package pe.gob.sunat.jmarket.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pe.gob.sunat.jmarket.dao.ProductoDao;
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
  @FXML private TextField tfSubtotal;
  @FXML private Button btnGuardar;

  private VentaDetalle detalle;
  private Producto producto;
  private ObservableList<VentaDetalle> observableList;
  private ProductoDao dao;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    btnGuardar
        .disableProperty()
        .bind(
            Bindings.isEmpty(tfCodigo.textProperty())
                .and(Bindings.isEmpty(tfDescripcion.textProperty()))
                .and(Bindings.isEmpty(tfUnidadMedida.textProperty()))
                .and(Bindings.isEmpty(tfCantidad.textProperty()))
                .and(Bindings.isEmpty(tfPrecioUnitario.textProperty()))
                .and(Bindings.isEmpty(tfSubtotal.textProperty())));
  }

  @FXML
  private void onActionBtnBuscar(ActionEvent event) {
    String codigo = tfCodigo.getText().trim();
    if (codigo.equals("")) return;
    producto = dao.read(codigo);
    tfDescripcion.setText(producto.getDescripcion());
    tfUnidadMedida.setText(UnidadMedida.values()[producto.getUnidadMedida()].getDescripcion());
    tfPrecioUnitario.setText(String.valueOf(producto.getPrecioUnitario()));
  }

  @FXML
  private void onActionBtnGuardar(ActionEvent event) {
    String codigo = tfCodigo.getText().toUpperCase();
    String descripcion = tfDescripcion.getText().toUpperCase();
    String unidadMedida = tfUnidadMedida.getText().toUpperCase();
    BigDecimal precioUnitario = new BigDecimal(tfPrecioUnitario.getText().trim());
    BigDecimal cantidad = new BigDecimal(tfCantidad.getText().trim());
    BigDecimal subtotal = precioUnitario.multiply(cantidad);

    detalle = new VentaDetalle();
    detalle.setPrecioUnitario(precioUnitario);
    detalle.setCantidad(cantidad);
    detalle.setSubtotal(subtotal);
    detalle.setEstado(Estado.ACTIVO.getCodigo());
    detalle.setProducto(producto);

    observableList.add(detalle);

    Stage stage = (Stage) btnGuardar.getScene().getWindow();
    stage.close();
  }

  public void setObservableList(ObservableList<VentaDetalle> observableList) {
    this.observableList = observableList;
  }
}
