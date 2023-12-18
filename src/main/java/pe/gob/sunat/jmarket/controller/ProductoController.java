/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package pe.gob.sunat.jmarket.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.remixicon.RemixiconAL;
import org.kordamp.ikonli.remixicon.RemixiconMZ;
import pe.gob.sunat.jmarket.App;
import pe.gob.sunat.jmarket.dao.ProductoDao;
import pe.gob.sunat.jmarket.idao.IProductoDao;
import pe.gob.sunat.jmarket.model.Estado;
import pe.gob.sunat.jmarket.model.Producto;
import pe.gob.sunat.jmarket.model.UnidadMedida;

/**
 * FXML Controller class
 *
 * @author anthonyponte
 */
public class ProductoController implements Initializable {
  @FXML private Label lblTitulo;
  @FXML private TextField txtFiltro;
  @FXML private Button btnRefrescar;
  @FXML private Button btnAgregar;
  @FXML private TableView<Producto> table;
  @FXML private TableColumn<Producto, String> tcCodigo;
  @FXML private TableColumn<Producto, String> tcDescripcion;
  @FXML private TableColumn<Producto, String> tcUnidadMedida;
  @FXML private TableColumn<Producto, BigDecimal> tcPrecioUnitario;
  @FXML private TableColumn<Producto, String> tcEstado;
  private ObservableList<Producto> observableList;
  private ProductoDao dao;

  public ProductoController() {
    observableList = FXCollections.observableArrayList();
    dao = new IProductoDao();
  }

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    initUI();
    setList();

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

    btnRefrescar.setOnAction(
        (ActionEvent t) -> {
          List<Producto> list = dao.read();
          observableList.clear();
          observableList.setAll(list);
        });

    btnAgregar.setOnAction(
        (ActionEvent t) -> {
          try {
            FXMLLoader fxmlLoader = App.loadFXML("ProductoDialog");
            Stage stage = getStage(fxmlLoader);
            stage.showAndWait();
          } catch (IOException ex) {
            Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
          }
        });

    table.setRowFactory(
        tv -> {
          TableRow<Producto> tableRow = new TableRow<>();
          tableRow.setOnMouseClicked(
              event -> {
                if (event.getClickCount() == 2 && (!tableRow.isEmpty())) {
                  try {
                    Producto producto = table.getSelectionModel().getSelectedItem();
                    FXMLLoader fxmlLoader = App.loadFXML("ProductoDialog");
                    Stage stage = getStage(fxmlLoader);
                    ProductoDialogController controller =
                        fxmlLoader.<ProductoDialogController>getController();
                    controller.setProducto(producto);
                    stage.showAndWait();
                  } catch (IOException ex) {
                    Logger.getLogger(ProductoController.class.getName())
                        .log(Level.SEVERE, null, ex);
                  }
                }
              });
          return tableRow;
        });

    table.setOnKeyPressed(
        new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent event) {
            if (event.getCode().equals(KeyCode.DELETE)) {
              Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
              alert.setTitle("Eliminar");
              alert.setHeaderText(null);
              alert.setContentText("Seguro que desea eliminar este registro?");

              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK) {
                Producto producto = (Producto) table.getSelectionModel().getSelectedItem();
                dao.delete(producto.getId());
                observableList.remove(producto);
              }
            }
          }
        });
  }

  private void initUI() {
    lblTitulo.setGraphic(FontIcon.of(RemixiconAL.BARCODE_BOX_LINE, 32));
    btnRefrescar.setGraphic(FontIcon.of(RemixiconMZ.REFRESH_LINE, 16));
    btnAgregar.setGraphic(FontIcon.of(RemixiconAL.ADD_LINE, 16));
  }

  private void setList() {
    List<Producto> list = dao.read();
    observableList.setAll(list);
    table.setItems(observableList);
  }

  private Stage getStage(FXMLLoader fxmlLoader) {
    Stage stage = null;
    try {
      Parent parent = fxmlLoader.load();
      Scene scene = new Scene(parent);
      stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setScene(scene);
      stage.setTitle("Producto");
      stage.setResizable(false);
    } catch (IOException ex) {
      Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
    }
    return stage;
  }
}
