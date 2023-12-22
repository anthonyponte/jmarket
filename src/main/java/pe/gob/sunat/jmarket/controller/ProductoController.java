package pe.gob.sunat.jmarket.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.remixicon.RemixiconAL;
import org.kordamp.ikonli.remixicon.RemixiconMZ;
import pe.gob.sunat.jmarket.dao.ProductoDao;
import pe.gob.sunat.jmarket.impl.IProductoDao;
import pe.gob.sunat.jmarket.model.num.Estado;
import pe.gob.sunat.jmarket.model.Producto;
import pe.gob.sunat.jmarket.model.Usuario;
import pe.gob.sunat.jmarket.model.num.UnidadMedida;
import pe.gob.sunat.jmarket.util.MyTextFieldFormat;

public class ProductoController implements Initializable {
  @FXML private Button btnGuardar;
  @FXML private Button btnLimpiar;
  @FXML private TextField txtId;
  @FXML private TextField txtCodigo;
  @FXML private TextField txtDescripcion;
  @FXML private ComboBox<UnidadMedida> cbxUnidadMedida;
  @FXML private TextField txtPrecioUnitario;
  @FXML private TextField txtFiltro;
  @FXML private TableView<Producto> table;
  @FXML private TableColumn<Producto, String> tcId;
  @FXML private TableColumn<Producto, String> tcCodigo;
  @FXML private TableColumn<Producto, String> tcDescripcion;
  @FXML private TableColumn<Producto, String> tcUnidadMedida;
  @FXML private TableColumn<Producto, BigDecimal> tcPrecioUnitario;
  @FXML private TableColumn<Producto, String> tcEstado;
  private Producto producto;
  private ObservableList<Producto> observableList;
  private ProductoDao dao;

  public ProductoController() {
    observableList = FXCollections.observableArrayList();
    dao = new IProductoDao();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    initUI();

    initTable();

    FilteredList<Producto> filteredList = new FilteredList<>(observableList, p -> true);
    table.setItems(filteredList);

    txtFiltro
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
  }

  @FXML
  private void onActionBtnGuardar(ActionEvent event) {
    String id = txtId.getText().trim();
    String codigo = txtCodigo.getText().trim();
    String descripcion = txtDescripcion.getText().trim();
    int unidadMedida = cbxUnidadMedida.getValue().getId();
    BigDecimal precioUnitario = new BigDecimal(txtPrecioUnitario.getText().trim());

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
      dao.delete(producto.getId());

      observableList.remove(producto);
    }
  }

  @FXML
  private void onMouseClickedTable(MouseEvent event) {}

  private void initUI() {
    btnGuardar.setGraphic(FontIcon.of(RemixiconMZ.SAVE_LINE, 16));
    btnLimpiar.setGraphic(FontIcon.of(RemixiconAL.ERASER_LINE, 16));

    cbxUnidadMedida.getItems().addAll(UnidadMedida.values());

    MyTextFieldFormat.toUpperCase(txtCodigo);
    MyTextFieldFormat.toUpperCase(txtDescripcion);

    txtCodigo.requestFocus();
  }

  private void initTable() {
    List<Producto> list = dao.read();
    observableList.clear();
    observableList.setAll(list);
  }

  private void clearUI() {
    producto = null;

    txtId.clear();
    txtCodigo.clear();
    txtDescripcion.clear();
    cbxUnidadMedida.getSelectionModel().clearSelection();
    txtPrecioUnitario.clear();
  }
}
