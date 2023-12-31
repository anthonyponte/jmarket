package pe.gob.sunat.jmarket.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.gob.sunat.jmarket.dao.VentaDao;
import pe.gob.sunat.jmarket.model.Persona;
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
            for (int i = 0; i < o.getVentaDetalles().size(); i++) {
              VentaDetalle d = o.getVentaDetalles().get(i);
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
    List<Venta> list = new ArrayList<>();

    database.connect();

    String query =
        "SELECT A.ID, A.FECHA_EMISION, A.TOTAL, A.ESTADO, "
            + "B.ID, B.TIPO_DOCUMENTO, B.NUMERO_DOCUMENTO, B.PRIMER_NOMBRE, "
            + "B.SEGUNDO_NOMBRE, B.APELLIDO_PATERNO, B.APELLIDO_MATERNO, B.ESTADO "
            + "FROM VENTA A "
            + "INNER JOIN PERSONA B "
            + "ON A.PERSONA_ID = B.ID "
            + "ORDER BY A.ID DESC";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          Venta venta =
              new Venta(
                  rs.getLong(1),
                  rs.getDate(2).toLocalDate(),
                  rs.getDouble(3),
                  rs.getInt(4),
                  new Persona(
                      rs.getLong(5),
                      rs.getInt(6),
                      rs.getString(7),
                      rs.getString(8),
                      rs.getString(9),
                      rs.getString(10),
                      rs.getString(11),
                      rs.getInt(12)));

          list.add(venta);
        }
      }
    }

    database.disconnect();

    return list;
  }

  @Override
  public void update(Venta o) throws SQLException {
    database.connect();

    String queryCabecera = "UPDATE VENTA SET ESTADO = ? WHERE ID = ?";

    String queryDetalle = "UPDATE VENTA_DETALLE SET ESTADO = ? WHERE VENTA_ID = ?";

    try (PreparedStatement psCabecera = database.getConnection().prepareStatement(queryCabecera)) {
      psCabecera.setInt(1, o.getEstado());
      psCabecera.setLong(2, o.getId());

      int i = psCabecera.executeUpdate();

      if (i > 0) {
        try (PreparedStatement psDetalle =
            database.getConnection().prepareStatement(queryDetalle)) {
          psDetalle.setInt(1, o.getEstado());
          psDetalle.setLong(2, o.getId());
          
          psDetalle.executeUpdate();
        }
      }
    }

    database.disconnect();
  }

  @Override
  public void delete(Long id) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from
    // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }
}
