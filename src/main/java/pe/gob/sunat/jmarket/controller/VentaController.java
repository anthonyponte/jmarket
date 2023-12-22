/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package pe.gob.sunat.jmarket.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pe.gob.sunat.jmarket.App;
import pe.gob.sunat.jmarket.dao.PersonaDao;
import pe.gob.sunat.jmarket.impl.PersonaDaoImpl;
import pe.gob.sunat.jmarket.model.Persona;
import pe.gob.sunat.jmarket.model.num.UnidadMedida;
import pe.gob.sunat.jmarket.model.VentaDetalle;

/**
 * FXML Controller class
 *
 * @author anthonyponte
 */
public class VentaController implements Initializable {
  @FXML private Button btnBuscar;
  @FXML private TextField txtNumeroDocumento;
  @FXML private TextField txtNombre;
  @FXML private Button btnGuardar;
  @FXML private TableView<VentaDetalle> table;
  @FXML private Button btnAgregar;
  @FXML private TableColumn<VentaDetalle, String> tcCodigo;
  @FXML private TableColumn<VentaDetalle, String> tcDescripcion;
  @FXML private TableColumn<VentaDetalle, String> tcUnidadMedida;
  @FXML private TableColumn<VentaDetalle, BigDecimal> tcCantidad;
  @FXML private TableColumn<VentaDetalle, BigDecimal> tcPrecioUnitario;
  @FXML private TableColumn<VentaDetalle, BigDecimal> tcSubtotal;
  @FXML private TextField txtTotal;
  private ObservableList<VentaDetalle> observableList;
  private PersonaDao personaDao;

  public VentaController() {
    personaDao = new PersonaDaoImpl();
    observableList = FXCollections.observableArrayList();
  }

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    btnBuscar.setOnAction(
        (ActionEvent t) -> {
          String numeroDocumento = txtNumeroDocumento.getText().toUpperCase();

          if (numeroDocumento.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Numero Documento vacio");
            alert.show();
            return;
          }

          Persona persona = personaDao.read(numeroDocumento);

          if (persona != null) {
            String nombre =
                persona.getPrimerNombre()
                    + " "
                    + persona.getSegundoNombre()
                    + " "
                    + persona.getApellidoPaterno()
                    + " "
                    + persona.getApellidoMaterno();

            txtNombre.setText(nombre);
          } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("La persona no existe");
            alert.show();
          }
        });

    btnAgregar.setOnAction(
        (ActionEvent t) -> {
          try {
            FXMLLoader fxmlLoader = App.loadFXML("VentaDetalleDialog");
            Parent parent = fxmlLoader.load();
            VentaDetalleDialogController controller =
                fxmlLoader.<VentaDetalleDialogController>getController();
            controller.setObservableList(observableList);

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Producto");
            stage.setResizable(false);
            stage.showAndWait();
          } catch (IOException ex) {
            Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
          }
        });

    tcCodigo.setCellValueFactory(
        c -> new SimpleStringProperty(c.getValue().getProducto().getCodigo()));

    tcDescripcion.setCellValueFactory(
        c -> new SimpleStringProperty(c.getValue().getProducto().getDescripcion()));

    tcUnidadMedida.setCellValueFactory(
        c ->
            new SimpleStringProperty(
                UnidadMedida.values()[c.getValue().getProducto().getUnidadMedida()]
                    .getDescripcion()));

    tcCantidad.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getCantidad()));

    tcPrecioUnitario.setCellValueFactory(
        c -> new SimpleObjectProperty<>(c.getValue().getPrecioUnitario()));

    tcSubtotal.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getSubtotal()));

    table.setItems(observableList);
  }
}
