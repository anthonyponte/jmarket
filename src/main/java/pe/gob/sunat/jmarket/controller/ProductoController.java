package pe.gob.sunat.jmarket.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import pe.gob.sunat.jmarket.model.num.Estado;
import pe.gob.sunat.jmarket.model.Producto;
import pe.gob.sunat.jmarket.model.num.UnidadMedida;

public class ProductoController implements Initializable {
  @FXML private TextField tfId;
  @FXML private TextField tfCodigo;
  @FXML private TextField tfDescripcion;
  @FXML private ComboBox<UnidadMedida> cbUnidadMedida;
  @FXML private TextField tfPrecioUnitario;
  @FXML private Button btnGuardar;

  @FXML private TextField tfFiltro;
  @FXML private TableView<Producto> table;
  @FXML private TableColumn<Producto, String> tcId;
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
    cbUnidadMedida.getItems().addAll(UnidadMedida.values());

    btnGuardar
        .disableProperty()
        .bind(
            Bindings.isEmpty(tfCodigo.textProperty())
                .or(Bindings.isEmpty(tfDescripcion.textProperty()))
                .or(Bindings.isEmpty(tfPrecioUnitario.textProperty())));

    initTable();
  }

  @FXML
  private void onActionBtnGuardar(ActionEvent event) {
    String id = tfId.getText().trim();
    String codigo = tfCodigo.getText().trim();
    String descripcion = tfDescripcion.getText().trim();
    int unidadMedida = cbUnidadMedida.getValue().getId();
    double precioUnitario = Double.parseDouble(tfPrecioUnitario.getText().trim());

    if (id.equals("")) {
      producto = new Producto();
      producto.setCodigo(codigo);
      producto.setDescripcion(descripcion);
      producto.setUnidadMedida(unidadMedida);
      producto.setPrecioUnitario(precioUnitario);
      producto.setEstado(Estado.ACTIVO.getCodigo());

      Long idProducto = dao.create(producto);

      if (idProducto > 0) {
        producto.setId(idProducto);

        observableList.add(producto);

        clearUI();
      }
    } else {
      producto.setUnidadMedida(unidadMedida);
      producto.setPrecioUnitario(precioUnitario);
      producto.setEstado(Estado.ACTIVO.getCodigo());
      dao.update(producto);

      table.refresh();

      clearUI();
    }
  }

  @FXML
  private void onActionBtnLimpiar(ActionEvent event) {
    clearUI();
  }

  @FXML
  private void onKeyPressedTable(KeyEvent event) {
    if (event.getCode().equals(KeyCode.DELETE)) {
      Producto producto = (Producto) table.getSelectionModel().getSelectedItem();

      if (producto == null) return;

      dao.delete(producto.getId());
      observableList.remove(producto);
    }
  }

  @FXML
  private void onMouseClickedTable(MouseEvent event) {
    if (event.getClickCount() == 2) {
      producto = (Producto) table.getSelectionModel().getSelectedItem();

      if (producto == null) return;

      tfCodigo.setDisable(true);

      tfId.setText(producto.getId().toString());
      tfCodigo.setText(producto.getCodigo());
      tfDescripcion.setText(producto.getDescripcion());
      cbUnidadMedida.getSelectionModel().select(producto.getUnidadMedida());
      tfPrecioUnitario.setText(String.valueOf(producto.getPrecioUnitario()));
    }
  }

  private void initTable() {
    tcId.setCellValueFactory(c -> new SimpleObjectProperty(c.getValue().getId()));
    tcCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
    tcDescripcion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescripcion()));
    tcUnidadMedida.setCellValueFactory(
        c ->
            new SimpleStringProperty(
                UnidadMedida.values()[c.getValue().getUnidadMedida()].getDescripcion()));
    tcPrecioUnitario.setCellValueFactory(
        c -> new SimpleObjectProperty<>(c.getValue().getPrecioUnitario()));
    tcEstado.setCellValueFactory(
        c -> new SimpleStringProperty(Estado.values()[c.getValue().getEstado()].getDescripcion()));

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
                    } else if (producto.getDescripcion().toLowerCase().contains(lowerCaseFilter)) {
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
  }

  private void clearUI() {
    producto = null;

    tfCodigo.setDisable(false);

    tfId.clear();
    tfCodigo.clear();
    tfDescripcion.clear();
    cbUnidadMedida.getSelectionModel().clearSelection();
    tfPrecioUnitario.clear();
  }
}
