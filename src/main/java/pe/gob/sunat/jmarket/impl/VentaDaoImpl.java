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
import pe.gob.sunat.jmarket.util.MyHsqldbConnection;

public class VentaDaoImpl implements VentaDao {
  private final MyHsqldbConnection database;

  public VentaDaoImpl() {
    this.database = new MyHsqldbConnection();
  }

  @Override
  public Long create(Venta o) {
    Long id = 0L;

    database.connect();

    String query =
        "INSERT INTO VENTA (TIPO_USUARIO, NOMBRE_USUARIO, CONTRASENA, ESTADO, PERSONA_ID) VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement ps =
        database.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

      ps.setDate(1, Date.valueOf(o.getFechaEmision()));
      ps.setInt(2, o.getEstado());
      ps.setLong(3, o.getPersona().getId());
      ps.executeUpdate();

      try (ResultSet rs = ps.getGeneratedKeys()) {
        while (rs.next()) {
          id = rs.getLong(1);
        }
      }

    } catch (SQLException ex) {
      Logger.getLogger(VentaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();

    return id;
  }

  @Override
  public Venta read(Long id) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from
    // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public List<Venta> read() {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from
    // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public void update(Venta o) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from
    // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public void delete(Long id) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from
    // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }
}
