package pe.gob.sunat.jmarket.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.gob.sunat.jmarket.dao.VentaDetalleDao;
import pe.gob.sunat.jmarket.model.Producto;
import pe.gob.sunat.jmarket.model.VentaDetalle;
import pe.gob.sunat.jmarket.util.MyHsqldbConnection;

public class VentaDetalleDaoImpl implements VentaDetalleDao {
  private final MyHsqldbConnection database;

  public VentaDetalleDaoImpl() {
    this.database = new MyHsqldbConnection();
  }

  @Override
  public List<VentaDetalle> read(Long idVenta) throws SQLException {
    List<VentaDetalle> list = new ArrayList<>();

    database.connect();

    String query =
        "SELECT A.ID, A.PRECIO_UNITARIO, A.CANTIDAD, A.SUBTOTAL, A.ESTADO, "
            + "B.ID, B.CODIGO, B.DESCRIPCION, B.UNIDAD_MEDIDA, B.PRECIO_UNITARIO, "
            + "B.ESTADO "
            + "FROM VENTA_DETALLE A "
            + "INNER JOIN PRODUCTO B "
            + "ON A.PRODUCTO_ID = B.ID "
            + "WHERE A.VENTA_ID = ? "
            + "ORDER BY A.NUMERO_FILA DESC";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setLong(1, idVenta);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          VentaDetalle ventaDetalle =
              new VentaDetalle(
                  rs.getLong(1),
                  rs.getDouble(2),
                  rs.getDouble(3),
                  rs.getDouble(4),
                  rs.getInt(5),
                  new Producto(
                      rs.getLong(6),
                      rs.getString(7),
                      rs.getString(8),
                      rs.getInt(9),
                      rs.getDouble(10),
                      rs.getInt(11)));

          list.add(ventaDetalle);
        }
      }
    }

    database.disconnect();

    return list;
  }
}
