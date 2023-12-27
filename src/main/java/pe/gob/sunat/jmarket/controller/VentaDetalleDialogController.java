package pe.gob.sunat.jmarket.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pe.gob.sunat.jmarket.dao.ProductoDao;
import pe.gob.sunat.jmarket.impl.ProductoDaoImpl;
import pe.gob.sunat.jmarket.model.Producto;
import pe.gob.sunat.jmarket.model.VentaDetalle;
import pe.gob.sunat.jmarket.model.enums.Estado;
import pe.gob.sunat.jmarket.model.enums.UnidadMedida;

public class VentaDetalleDialogController implements Initializable {
  @FXML private TextField tfCodigo;
  @FXML private TextField tfDescripcion;
  @FXML private TextField tfUnidadMedida;
  @FXML private TextField tfPrecioUnitario;
  @FXML private TextField tfCantidad;
  @FXML private Button btnBuscarProducto;
  @FXML private Button btnGuardar;

  private VentaDetalle detalle;
  private Producto producto;
  private VentaController controller;
  private ProductoDao dao;

  public VentaDetalleDialogController() {
    dao = new ProductoDaoImpl();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    btnGuardar
        .disableProperty()
        .bind(
            Bindings.isEmpty(tfCodigo.textProperty())
                .or(Bindings.isEmpty(tfDescripcion.textProperty()))
                .or(Bindings.isEmpty(tfUnidadMedida.textProperty()))
                .or(Bindings.isEmpty(tfPrecioUnitario.textProperty()))
                .or(Bindings.isEmpty(tfCantidad.textProperty())));
  }

  @FXML
  private void onActionBtnBuscarProducto(ActionEvent event) {
    try {
      String codigo = tfCodigo.getText().trim();

      if (codigo.equals("")) return;

      producto = dao.read(codigo);
      tfDescripcion.setText(producto.getDescripcion());
      tfUnidadMedida.setText(UnidadMedida.values()[producto.getUnidadMedida()].getDescripcion());
      tfPrecioUnitario.setText(String.valueOf(producto.getPrecioUnitario()));
    } catch (SQLException ex) {
      Logger.getLogger(VentaDetalleDialogController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @FXML
  private void onActionBtnGuardar(ActionEvent event) {
    double precioUnitario = Double.parseDouble(tfPrecioUnitario.getText().trim());
    double cantidad = Double.parseDouble(tfCantidad.getText().trim());
    double subtotal = precioUnitario * cantidad;
    if (detalle == null) {
      detalle =
          new VentaDetalle(precioUnitario, cantidad, subtotal, Estado.ACTIVO.getCodigo(), producto);

      controller.add(detalle);
    } else {
      detalle.setCantidad(cantidad);
      detalle.setSubtotal(subtotal);

      controller.refresh();
    }

    Stage stage = (Stage) btnGuardar.getScene().getWindow();
    stage.close();
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

  public void setController(VentaController controller) {
    this.controller = controller;
  }
}
