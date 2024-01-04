package pe.gob.sunat.jmarket.dao;

import java.sql.SQLException;

public interface DashboardDao {
  Integer getTotalUsuarios() throws SQLException;
  Integer getTotalProductos() throws SQLException;
  Double getTotalVentas() throws SQLException;
}
