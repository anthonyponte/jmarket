package pe.gob.sunat.jmarket.dao;

import java.sql.SQLException;
import pe.gob.sunat.jmarket.model.Producto;

public interface ProductoDao extends ObjectDao<Producto> {
  Producto read(String codigo) throws SQLException;
}
