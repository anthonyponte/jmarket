/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package pe.gob.sunat.jmarket.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.remixicon.RemixiconMZ;
import pe.gob.sunat.jmarket.dao.ProductoDao;
import pe.gob.sunat.jmarket.idao.IProductoDao;
import pe.gob.sunat.jmarket.model.Estado;
import pe.gob.sunat.jmarket.model.Producto;
import pe.gob.sunat.jmarket.model.UnidadMedida;
import pe.gob.sunat.jmarket.util.MyTextFieldFormat;

/**
 * FXML Controller class
 *
 * @author anthonyponte
 */
public class ProductoDialogController implements Initializable {
  @FXML private TextField txtCodigo;
  @FXML private TextField txtDescripcion;
  @FXML private ComboBox<UnidadMedida> cbxUnidadMedida;
  @FXML private TextField txtPrecioUnitario;
  @FXML private Button btnGuardar;
  private Producto producto;
  private ProductoDao dao;

  public ProductoDialogController() {
    dao = new IProductoDao();
  }
  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    initUI();

    btnGuardar.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent t) {
            String codigo = txtCodigo.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            int unidadMedida = cbxUnidadMedida.getValue().getId();
            BigDecimal precioUnitario = new BigDecimal(txtPrecioUnitario.getText().trim());

            if (producto == null) {
              producto =
                  new Producto(
                      codigo, descripcion, unidadMedida, precioUnitario, Estado.ACTIVO.getCodigo());

              Long id = dao.create(producto);

              if (id > 0) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Guardado");
                alert.setHeaderText(null);
                alert.setContentText("Se guardo el registro correctamente");
                alert.show();
              }
            } else {
              producto.setDescripcion(descripcion);
              producto.setUnidadMedida(unidadMedida);
              producto.setPrecioUnitario(precioUnitario);
              producto.setEstado(Estado.ACTIVO.getCodigo());
              dao.update(producto);

              Alert alert = new Alert(AlertType.INFORMATION);
              alert.setTitle("Actualizado");
              alert.setHeaderText(null);
              alert.setContentText("Se actualizo el registro correctamente");
              alert.show();
            }
          }
        });
  }

  private void initUI() {
    btnGuardar.setGraphic(FontIcon.of(RemixiconMZ.SAVE_3_LINE, 16));

    cbxUnidadMedida.getItems().addAll(UnidadMedida.values());

    MyTextFieldFormat.toUpperCase(txtCodigo);
    MyTextFieldFormat.toUpperCase(txtDescripcion);
    MyTextFieldFormat.toUpperCase(txtPrecioUnitario);

    txtCodigo.requestFocus();
  }

  public void setProducto(Producto producto) {
    this.producto = producto;

    txtCodigo.setEditable(false);
    txtDescripcion.setEditable(true);
    cbxUnidadMedida.setDisable(false);
    txtPrecioUnitario.setEditable(true);

    txtCodigo.setText(this.producto.getCodigo());
    txtDescripcion.setText(this.producto.getDescripcion());
    cbxUnidadMedida.getSelectionModel().select(this.producto.getUnidadMedida());
    txtPrecioUnitario.setText(this.producto.getPrecioUnitario().toString());

    txtDescripcion.requestFocus();
  }
}
