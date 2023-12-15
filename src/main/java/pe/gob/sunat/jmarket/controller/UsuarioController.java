/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package pe.gob.sunat.jmarket.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.remixicon.RemixiconMZ;
import pe.gob.sunat.jmarket.App;
import pe.gob.sunat.jmarket.dao.UsuarioDao;
import pe.gob.sunat.jmarket.idao.IUsuarioDao;
import pe.gob.sunat.jmarket.model.Estado;
import pe.gob.sunat.jmarket.model.TipoDocumento;
import pe.gob.sunat.jmarket.model.TipoUsuario;
import pe.gob.sunat.jmarket.model.Usuario;

/**
 * FXML Controller class
 *
 * @author anthonyponte
 */
public class UsuarioController implements Initializable {
  @FXML private Button btnRefrescar;
  @FXML private Button btnAgregar;
  @FXML private TableView<Usuario> tblUsuario;
  @FXML private TableColumn<Usuario, String> tcNombreUsuario;
  @FXML private TableColumn<Usuario, String> tcTipoUsuario;
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
    initUI();

    tcNombreUsuario.setCellValueFactory(
        c -> new SimpleStringProperty(c.getValue().getNombreUsuario()));

    tcTipoUsuario.setCellValueFactory(
        c ->
            new SimpleStringProperty(
                TipoUsuario.values()[c.getValue().getTipoUsuario()].getDescripcion()));

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

    btnRefrescar.setOnAction(
        (ActionEvent t) -> {
          List<Usuario> list = dao.read();
          observableList.clear();
          observableList.setAll(list);
        });

    tblUsuario.setRowFactory(
        tv -> {
          TableRow<Usuario> tlUsuario = new TableRow<>();
          tlUsuario.setOnMouseClicked(
              event -> {
                if (event.getClickCount() == 2 && (!tlUsuario.isEmpty())) {
                  try {
                    Usuario usuario = tblUsuario.getSelectionModel().getSelectedItem();
                    FXMLLoader fxmlLoader = App.loadFXML("UsuarioDialog");
                    Stage stage = getStage(fxmlLoader);
                    UsuarioDialogController controller =
                        fxmlLoader.<UsuarioDialogController>getController();
                    controller.setUsuario(usuario);
                    stage.showAndWait();
                  } catch (IOException ex) {
                    Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                  }
                }
              });
          return tlUsuario;
        });

    tblUsuario.setOnKeyPressed(
        new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent event) {
            if (event.getCode().equals(KeyCode.DELETE)) {
              Alert alert = new Alert(AlertType.CONFIRMATION);
              alert.setTitle("Eliminar");
              alert.setHeaderText(null);
              alert.setContentText("Seguro que desea eliminar este registro?");

              Optional<ButtonType> result = alert.showAndWait();
              if (result.get() == ButtonType.OK) {
                Usuario usuario = (Usuario) tblUsuario.getSelectionModel().getSelectedItem();
                dao.delete(usuario.getId());

                setList();
              }
            }
          }
        });

    btnAgregar.setOnAction(
        (ActionEvent t) -> {
          try {
            FXMLLoader fxmlLoader = App.loadFXML("UsuarioDialog");
            Stage stage = getStage(fxmlLoader);
            stage.showAndWait();
          } catch (IOException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
          }
        });
  }

  private void initUI() {
    btnRefrescar.setGraphic(FontIcon.of(RemixiconMZ.REFRESH_LINE, 16));
    btnAgregar.setGraphic(FontIcon.of(RemixiconMZ.USER_ADD_LINE, 16));

    setList();
  }

  private void setList() {
    List<Usuario> list = dao.read();
    observableList.setAll(list);
    tblUsuario.setItems(observableList);
  }

  private Stage getStage(FXMLLoader fxmlLoader) {
    Stage stage = null;
    try {
      Parent parent = fxmlLoader.load();
      Scene scene = new Scene(parent);
      stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setScene(scene);
      stage.setTitle("Usuario");
      stage.setResizable(false);
    } catch (IOException ex) {
      Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
    }
    return stage;
  }
}
