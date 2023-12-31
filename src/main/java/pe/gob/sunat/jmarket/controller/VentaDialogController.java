package pe.gob.sunat.jmarket.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pe.gob.sunat.jmarket.dao.VentaDetalleDao;
import pe.gob.sunat.jmarket.impl.VentaDetalleDaoImpl;
import pe.gob.sunat.jmarket.model.Venta;
import pe.gob.sunat.jmarket.model.VentaDetalle;
import pe.gob.sunat.jmarket.model.enums.Estado;

public class VentaDialogController implements Initializable {
  @FXML private TextField tfFiltro;
  @FXML private ListView<Venta> listView;
  private ObservableList<Venta> observableList;
  private VentaController controller;
  private VentaDetalleDao dao;

  public VentaDialogController() {
    observableList = FXCollections.observableArrayList();
    dao = new VentaDetalleDaoImpl();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    initList();
  }

  @FXML
  private void onMouseClickedListView(MouseEvent event) {
    if (event.getClickCount() == 2) {
      try {
        Venta venta = (Venta) listView.getSelectionModel().getSelectedItem();

        if (venta == null) return;

        List<VentaDetalle> list = dao.read(venta.getId());
        venta.setVentaDetalles(list);
        
        controller.setVenta(venta);

        Stage stage = (Stage) listView.getScene().getWindow();
        stage.close();
      } catch (SQLException ex) {
        Logger.getLogger(VentaDialogController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  private void initList() {
    FilteredList<Venta> filteredList = new FilteredList<>(observableList, p -> true);
    listView.setItems(filteredList);

    tfFiltro
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              filteredList.setPredicate(
                  venta -> {
                    if (newValue == null || newValue.isEmpty()) {
                      return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (venta.getId().toString().toLowerCase().contains(lowerCaseFilter)) {
                      return true;
                    } else if (venta
                        .getFechaEmision()
                        .toString()
                        .toLowerCase()
                        .contains(lowerCaseFilter)) {
                      return true;
                    } else if (String.valueOf(venta.getTotal())
                        .toLowerCase()
                        .contains(lowerCaseFilter)) {
                      return true;
                    } else if (Estado.values()[venta.getEstado()]
                        .getDescripcion()
                        .toLowerCase()
                        .contains(lowerCaseFilter)) {
                      return true;
                    }
                    return false;
                  });
            });
  }

  public void setObservableList(List<Venta> list) {
    observableList.setAll(list);
  }

  public void setController(VentaController controller) {
    this.controller = controller;
  }
}
