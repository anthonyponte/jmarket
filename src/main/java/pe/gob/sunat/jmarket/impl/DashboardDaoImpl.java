package pe.gob.sunat.jmarket.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.gob.sunat.jmarket.dao.DashboardDao;
import pe.gob.sunat.jmarket.util.MyHsqldbConnection;

public class DashboardDaoImpl implements DashboardDao {
  private final MyHsqldbConnection database;

  public DashboardDaoImpl() {
    this.database = new MyHsqldbConnection();
  }

  @Override
  public Integer getTotalUsuarios() throws SQLException {
    int count = 0;

    database.connect();

    String query = "SELECT COUNT(*) FROM USUARIO WHERE ESTADO = 1";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          count = rs.getInt(1);
        }
      }
    }

    database.disconnect();

    return count;
  }

  @Override
  public Integer getTotalProductos() throws SQLException {
    int count = 0;

    database.connect();

    String query = "SELECT COUNT(*) FROM PRODUCTO WHERE ESTADO = 1";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          count = rs.getInt(1);
        }
      }
    }

    database.disconnect();

    return count;
  }

  @Override
  public Double getTotalVentas() throws SQLException {
    double count = 0.0;

    database.connect();

    String query = "SELECT SUM(TOTAL) FROM VENTA WHERE ESTADO = 1";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          count = rs.getDouble(1);
        }
      }
    }

    database.disconnect();

    return count;
  }
}
