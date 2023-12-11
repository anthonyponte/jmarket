/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package pe.gob.sunat.jmarket.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pe.gob.sunat.jmarket.App;
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
public class UsuarioController implements Initializable {

  @FXML private Button btnAgregar;
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

  public UsuarioController() {
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
    tblUsuario.setItems(observableList);

    btnAgregar.setOnAction(
        (ActionEvent t) -> {
          try {
            FXMLLoader fxmlLoader = App.loadFXML("UsuarioDialogView");
            Parent parent = fxmlLoader.load();
            UsuarioDialogController dialogController =
                fxmlLoader.<UsuarioDialogController>getController();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();

            dialogController.setObservableList(observableList);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Usuario");
            stage.setResizable(false);
            stage.showAndWait();
          } catch (IOException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
          }
        });
  }
}
