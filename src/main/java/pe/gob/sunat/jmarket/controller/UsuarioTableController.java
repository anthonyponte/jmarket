/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package pe.gob.sunat.jmarket.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import pe.gob.sunat.jmarket.dao.UsuarioDao;
import pe.gob.sunat.jmarket.idao.IUsuarioDao;
import pe.gob.sunat.jmarket.model.Estado;
import pe.gob.sunat.jmarket.model.TipoDocumento;
import pe.gob.sunat.jmarket.model.Usuario;

/**
 * FXML Controller class
 *
 * @author anthonyponte
 */
public class UsuarioTableController implements Initializable {

  @FXML private TableView<Usuario> tblUsuario;
  @FXML private TableColumn<Usuario, String> tcNombreUsuario;
  @FXML private TableColumn<Usuario, String> tcEstado;
  @FXML private TableColumn<Usuario, String> tcTipoDocumento;
  @FXML private TableColumn<Usuario, String> tcNumeroDocumento;
  @FXML private TableColumn<Usuario, String> tcPrimerNombre;
  @FXML private TableColumn<Usuario, String> tcSegundoNombre;
  @FXML private TableColumn<Usuario, String> tcApellidoPaterno;
  @FXML private TableColumn<Usuario, String> tcApellidoMaterno;
  private ObservableList<Usuario> observableList;
  private UsuarioDao dao;

  public UsuarioTableController() {
    observableList = FXCollections.observableArrayList();
    dao = new IUsuarioDao();
  }

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    List<Usuario> list = dao.read();
    observableList.setAll(list);

    tcNombreUsuario.setCellValueFactory(
        c -> new SimpleStringProperty(c.getValue().getNombreUsuario()));

    tcEstado.setCellValueFactory(
        c -> new SimpleStringProperty(Estado.values()[c.getValue().getEstado()].getDescripcion()));

    tcTipoDocumento.setCellValueFactory(
        c ->
            new SimpleStringProperty(
                TipoDocumento.values()[c.getValue().getPersona().getTipoDocumento()]
                    .getDescripcion()));

    tcNumeroDocumento.setCellValueFactory(
        c -> new SimpleStringProperty(c.getValue().getPersona().getNumeroDocumento()));

    tcPrimerNombre.setCellValueFactory(
        c -> new SimpleStringProperty(c.getValue().getPersona().getPrimerNombre()));

    tcSegundoNombre.setCellValueFactory(
        c -> new SimpleStringProperty(c.getValue().getPersona().getSegundoNombre()));

    tcApellidoPaterno.setCellValueFactory(
        c -> new SimpleStringProperty(c.getValue().getPersona().getApellidoPaterno()));

    tcApellidoMaterno.setCellValueFactory(
        c -> new SimpleStringProperty(c.getValue().getPersona().getApellidoMaterno()));

    tcNombreUsuario.setCellFactory(TextFieldTableCell.forTableColumn());

    tcNombreUsuario.setOnEditCommit(
        e ->
            e.getTableView()
                .getItems()
                .get(e.getTablePosition().getRow())
                .setNombreUsuario(e.getNewValue()));

    tblUsuario.setItems(observableList);

    /*
    btnAgregar.setOnAction((ActionEvent t) -> {
          try {
            FXMLLoader fxmlLoader = App.loadFXML("UsuarioDialogView");
            Parent parent = fxmlLoader.load();
            UsuarioController dialogController =
                fxmlLoader.<UsuarioController>getController();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();

            dialogController.setObservableList(observableList);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Usuario");
            stage.setResizable(false);
            stage.showAndWait();
          } catch (IOException ex) {
            Logger.getLogger(UsuarioTableController.class.getName()).log(Level.SEVERE, null, ex);
          }
        });
*/
  }
}
