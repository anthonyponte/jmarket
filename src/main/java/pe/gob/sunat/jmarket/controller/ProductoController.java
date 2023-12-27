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
import pe.gob.sunat.jmarket.dao.ProductoDao;
import pe.gob.sunat.jmarket.impl.ProductoDaoImpl;
import pe.gob.sunat.jmarket.model.enums.Estado;
import pe.gob.sunat.jmarket.model.Producto;
import pe.gob.sunat.jmarket.model.enums.UnidadMedida;

public class ProductoController implements Initializable {
  @FXML private ComboBox<Estado> cbEstado;
  @FXML private TextField tfId;
  @FXML private TextField tfCodigo;
  @FXML private TextField tfDescripcion;
  @FXML private ComboBox<UnidadMedida> cbUnidadMedida;
  @FXML private TextField tfPrecioUnitario;
  @FXML private Button btnGuardar;

  @FXML private TextField tfFiltro;
  @FXML private TableView<Producto> table;
  @FXML private TableColumn<Producto, Long> tcId;
  @FXML private TableColumn<Producto, String> tcCodigo;
  @FXML private TableColumn<Producto, String> tcDescripcion;
  @FXML private TableColumn<Producto, String> tcUnidadMedida;
  @FXML private TableColumn<Producto, Double> tcPrecioUnitario;
  @FXML private TableColumn<Producto, String> tcEstado;

  private Producto producto;
  private ObservableList<Producto> observableList;
  private ProductoDao dao;

  public ProductoController() {
    observableList = FXCollections.observableArrayList();
    dao = new ProductoDaoImpl();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    cbEstado.getItems().addAll(Estado.values());
    cbUnidadMedida.getItems().addAll(UnidadMedida.values());

    btnGuardar
        .disableProperty()
        .bind(
            Bindings.isEmpty(tfCodigo.textProperty())
                .or(Bindings.isEmpty(tfDescripcion.textProperty()))
                .or(Bindings.isNull(cbUnidadMedida.valueProperty()))
                .or(Bindings.isEmpty(tfPrecioUnitario.textProperty())));

    initTable();
  }

  @FXML
  private void onActionBtnGuardar(ActionEvent event) {
    String id = tfId.getText().trim();

    if (id.equals("")) {
      try {
        String codigo = tfCodigo.getText().trim();
        String descripcion = tfDescripcion.getText().trim();
        int unidadMedida = cbUnidadMedida.getValue().getId();
        double precioUnitario = Double.parseDouble(tfPrecioUnitario.getText().trim());

        producto =
            new Producto(
                codigo, descripcion, unidadMedida, precioUnitario, Estado.ACTIVO.getCodigo());

        Long idProducto = dao.create(producto);

        if (idProducto > 0) {
          producto.setId(idProducto);

          observableList.add(producto);

          clearUI();
        }
      } catch (SQLException ex) {
        Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
      }
    } else {
      try {

        int estado = cbEstado.getValue().getCodigo();
        double precioUnitario = Double.parseDouble(tfPrecioUnitario.getText().trim());

        producto.setPrecioUnitario(precioUnitario);
        producto.setEstado(estado);
        dao.update(producto);

        table.refresh();

        clearUI();
      } catch (SQLException ex) {
        Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
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
        Producto producto = (Producto) table.getSelectionModel().getSelectedItem();

        if (producto == null) return;

        dao.delete(producto.getId());
        observableList.remove(producto);
      } catch (SQLException ex) {
        Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  @FXML
  private void onMouseClickedTable(MouseEvent event) {
    if (event.getClickCount() == 2) {
      producto = (Producto) table.getSelectionModel().getSelectedItem();

      if (producto == null) return;

      cbEstado.setDisable(false);
      tfCodigo.setDisable(true);
      tfDescripcion.setDisable(true);
      cbUnidadMedida.setDisable(true);

      cbEstado.getSelectionModel().select(producto.getEstado());
      tfId.setText(producto.getId().toString());
      tfCodigo.setText(producto.getCodigo());
      tfDescripcion.setText(producto.getDescripcion());
      cbUnidadMedida.getSelectionModel().select(producto.getUnidadMedida());
      tfPrecioUnitario.setText(String.valueOf(producto.getPrecioUnitario()));
    }
  }

  private void initTable() {
    try {
      tcId.setCellValueFactory(c -> c.getValue().getIdProperty());
      tcCodigo.setCellValueFactory(c -> c.getValue().getCodigoProperty());
      tcDescripcion.setCellValueFactory(c -> c.getValue().getDescripcionProperty());
      tcUnidadMedida.setCellValueFactory(
          c -> UnidadMedida.values()[c.getValue().getUnidadMedida()].getDescripcionProperty());
      tcPrecioUnitario.setCellValueFactory(c -> c.getValue().getPrecioUnitarioProperty());
      tcEstado.setCellValueFactory(
          c -> Estado.values()[c.getValue().getEstado()].getDescripcionProperty());

      FilteredList<Producto> filteredList = new FilteredList<>(observableList, p -> true);
      table.setItems(filteredList);

      tfFiltro
          .textProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                filteredList.setPredicate(
                    producto -> {
                      if (newValue == null || newValue.isEmpty()) {
                        return true;
                      }

                      String lowerCaseFilter = newValue.toLowerCase();

                      if (producto.getCodigo().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                      } else if (producto
                          .getDescripcion()
                          .toLowerCase()
                          .contains(lowerCaseFilter)) {
                        return true;
                      } else if (UnidadMedida.values()[producto.getUnidadMedida()]
                          .getDescripcion()
                          .toLowerCase()
                          .contains(lowerCaseFilter)) {
                        return true;
                      } else if (Estado.values()[producto.getEstado()]
                          .getDescripcion()
                          .toLowerCase()
                          .contains(lowerCaseFilter)) {
                        return true;
                      }
                      return false;
                    });
              });

      List<Producto> list = dao.read();
      observableList.clear();
      observableList.setAll(list);
    } catch (SQLException ex) {
      Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private void clearUI() {
    producto = null;

    cbEstado.setDisable(true);
    tfCodigo.setDisable(false);
    tfDescripcion.setDisable(false);
    cbUnidadMedida.setDisable(false);

    tfId.clear();
    tfCodigo.clear();
    tfDescripcion.clear();
    cbUnidadMedida.getSelectionModel().clearSelection();
    tfPrecioUnitario.clear();
  }
}
