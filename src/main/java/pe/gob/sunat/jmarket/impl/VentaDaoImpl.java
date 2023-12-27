package pe.gob.sunat.jmarket.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.gob.sunat.jmarket.dao.VentaDao;
import pe.gob.sunat.jmarket.model.Venta;
import pe.gob.sunat.jmarket.model.VentaDetalle;
import pe.gob.sunat.jmarket.util.MyHsqldbConnection;

public class VentaDaoImpl implements VentaDao {
  private final MyHsqldbConnection database;

  public VentaDaoImpl() {
    this.database = new MyHsqldbConnection();
  }

  @Override
  public Long create(Venta o) throws SQLException {
    Long id = 0L;

    database.connect();

    String queryCabecera =
        "INSERT INTO VENTA (FECHA_EMISION, TOTAL, ESTADO, PERSONA_ID) VALUES (?, ?, ?, ?)";

    String queryDetalle =
        "INSERT INTO VENTA_DETALLE (NUMERO_FILA, PRECIO_UNITARIO, CANTIDAD, SUBTOTAL, "
            + "ESTADO, PRODUCTO_ID, VENTA_ID) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement psCabecera =
        database
            .getConnection()
            .prepareStatement(queryCabecera, PreparedStatement.RETURN_GENERATED_KEYS)) {

      psCabecera.setDate(1, Date.valueOf(o.getFechaEmision()));
      psCabecera.setDouble(2, o.getTotal());
      psCabecera.setInt(3, o.getEstado());
      psCabecera.setLong(4, o.getPersona().getId());
      psCabecera.executeUpdate();

      try (ResultSet rsCabecera = psCabecera.getGeneratedKeys()) {
        while (rsCabecera.next()) {
          id = rsCabecera.getLong(1);

          try (PreparedStatement psDetalle =
              database.getConnection().prepareStatement(queryDetalle)) {
            for (int i = 0; i < o.getDetalles().size(); i++) {
              VentaDetalle d = o.getDetalles().get(i);
              psDetalle.setInt(1, i + 1);
              psDetalle.setDouble(2, d.getPrecioUnitario());
              psDetalle.setDouble(3, d.getCantidad());
              psDetalle.setDouble(4, d.getSubtotal());
              psDetalle.setInt(5, d.getEstado());
              psDetalle.setLong(6, d.getProducto().getId());
              psDetalle.setLong(7, id);

              psDetalle.addBatch();
            }

            psDetalle.executeBatch();
          }
        }
      }

    } catch (SQLException ex) {
      Logger.getLogger(VentaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();

    return id;
  }

  @Override
  public Venta read(Long id) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from
    // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public List<Venta> read() throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from
    // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public void update(Venta o) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from
    // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public void delete(Long id) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from
    // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }
}
