package pe.gob.sunat.jmarket.dao;

import java.sql.SQLException;
import java.util.List;
import pe.gob.sunat.jmarket.model.VentaDetalle;

public interface VentaDetalleDao {
  List<VentaDetalle> read(Long idVenta) throws SQLException;
}
