/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package pe.gob.sunat.jmarket.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.remixicon.RemixiconMZ;
import pe.gob.sunat.jmarket.dao.ProductoDao;
import pe.gob.sunat.jmarket.idao.IProductoDao;
import pe.gob.sunat.jmarket.model.Estado;
import pe.gob.sunat.jmarket.model.Producto;
import pe.gob.sunat.jmarket.model.UnidadMedida;
import pe.gob.sunat.jmarket.model.VentaDetalle;

/**
 * FXML Controller class
 *
 * @author anthonyponte
 */
public class VentaDetalleDialogController implements Initializable {

  @FXML private TextField txtCodigo;
  @FXML private Button btnBuscar;
  @FXML private TextField txtDescripcion;
  @FXML private TextField txtUnidadMedida;
  @FXML private TextField txtCantidad;
  @FXML private TextField txtPrecioUnitario;
  @FXML private TextField txtSubtotal;
  @FXML private Button btnGuardar;
  private ObservableList<VentaDetalle> observableList;
  private VentaDetalle ventaDetalle;
  private ProductoDao dao;

  public VentaDetalleDialogController() {
    dao = new IProductoDao();
  }

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    initUI();

    btnBuscar.setOnAction(
        (ActionEvent t) -> {
          String codigo = txtCodigo.getText().toUpperCase();

          if (codigo.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Codigo vacio");
            alert.show();
            return;
          }

          Producto producto = dao.read(codigo);

          if (producto != null) {
            txtDescripcion.setText(producto.getDescripcion());
            txtUnidadMedida.setText(
                UnidadMedida.values()[producto.getUnidadMedida()].getDescripcion());
            txtPrecioUnitario.setText(String.valueOf(producto.getPrecioUnitario()));

            ventaDetalle = new VentaDetalle();
            ventaDetalle.setProducto(producto);
          } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("El producto no existe");
            alert.show();
          }
        });

    btnGuardar.setOnAction(
        (ActionEvent t) -> {
          String codigo = txtCodigo.getText().toUpperCase();
          String descripcion = txtDescripcion.getText().toUpperCase();
          String unidadMedida = txtUnidadMedida.getText().toUpperCase();
          BigDecimal cantidad = new BigDecimal(txtCantidad.getText().trim());
          BigDecimal precioUnitario = new BigDecimal(txtPrecioUnitario.getText().trim());

          if (codigo.equals("")
              && descripcion.equals("")
              && unidadMedida.equals("")
              && cantidad.equals("")
              && precioUnitario.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Campos vacios");
            alert.show();

            return;
          }

          BigDecimal subtotal = precioUnitario.multiply(cantidad);
          ventaDetalle.setCantidad(cantidad);
          ventaDetalle.setPrecioUnitario(precioUnitario);
          ventaDetalle.setSubtotal(subtotal);
          ventaDetalle.setEstado(Estado.ACTIVO.getCodigo());

          observableList.add(ventaDetalle);

          Stage stage = (Stage) btnGuardar.getScene().getWindow();
          stage.close();
        });
  }

  private void initUI() {
    btnBuscar.setGraphic(FontIcon.of(RemixiconMZ.SEARCH_LINE, 16));
    btnGuardar.setGraphic(FontIcon.of(RemixiconMZ.SAVE_3_LINE, 16));
  }

  public void setObservableList(ObservableList<VentaDetalle> observableList) {
    this.observableList = observableList;
  }
}
