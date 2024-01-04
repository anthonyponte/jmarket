package pe.gob.sunat.jmarket.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pe.gob.sunat.jmarket.dao.DashboardDao;
import pe.gob.sunat.jmarket.impl.DashboardDaoImpl;

public class DashboardController implements Initializable {
  @FXML private Label lblTotalUsuarios;
  @FXML private Label lblTotalProductos;
  @FXML private Label lblTotalVentas;
  private DashboardDao dao;

  public DashboardController() {
    dao = new DashboardDaoImpl();
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    try {
      int totalUsuarios = dao.getTotalUsuarios();
      int totalProductos = dao.getTotalProductos();
      double totalVentas = dao.getTotalVentas();
      lblTotalUsuarios.setText(String.valueOf(totalUsuarios));
      lblTotalProductos.setText(String.valueOf(totalProductos));
      lblTotalVentas.setText(String.valueOf(totalVentas));
    } catch (SQLException ex) {
      Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
