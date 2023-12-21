/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pe.gob.sunat.jmarket.idao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.gob.sunat.jmarket.dao.PersonaDao;
import pe.gob.sunat.jmarket.model.Persona;
import pe.gob.sunat.jmarket.util.MyHsqldbConnection;

/**
 * @author anthonyponte
 */
public class IPersonaDao implements PersonaDao {
  private final MyHsqldbConnection database;

  public IPersonaDao() {
    this.database = new MyHsqldbConnection();
  }

  @Override
  public Long create(Persona persona) {
    Long id = 0L;

    database.connect();

    String query =
        "INSERT INTO PERSONA (TIPO_DOCUMENTO, NUMERO_DOCUMENTO, PRIMER_NOMBRE, SEGUNDO_NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, ESTADO) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement ps =
        database.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

      ps.setInt(1, persona.getTipoDocumento());
      ps.setString(2, persona.getNumeroDocumento());
      ps.setString(3, persona.getPrimerNombre());
      ps.setString(4, persona.getSegundoNombre());
      ps.setString(5, persona.getApellidoPaterno());
      ps.setString(6, persona.getApellidoMaterno());
      ps.setInt(7, persona.getEstado());
      ps.executeUpdate();

      try (ResultSet rs = ps.getGeneratedKeys()) {
        while (rs.next()) {
          id = rs.getLong(1);
        }
      }

    } catch (SQLException ex) {
      Logger.getLogger(IPersonaDao.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();

    return id;
  }

  @Override
  public Persona read(Long id) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from
    // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public List<Persona> read() {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from
    // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public void update(Persona persona) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from
    // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public void delete(Long id) {
    database.connect();

    String query = "DELETE FROM PERSONA WHERE ID = ?";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setLong(1, id);
      ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(IPersonaDao.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();
  }

  @Override
  public Persona read(String numeroDocumento) {
    Persona persona = null;

    database.connect();

    String query =
        "SELECT ID, TIPO_DOCUMENTO, NUMERO_DOCUMENTO, PRIMER_NOMBRE, SEGUNDO_NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, ESTADO "
            + "FROM PERSONA "
            + "WHERE NUMERO_DOCUMENTO = ?";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setString(1, numeroDocumento);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          persona = new Persona();
          persona.setId(rs.getLong(1));
          persona.setTipoDocumento(rs.getInt(2));
          persona.setNumeroDocumento(rs.getString(3));
          persona.setPrimerNombre(rs.getString(4));
          persona.setSegundoNombre(rs.getString(5));
          persona.setApellidoPaterno(rs.getString(6));
          persona.setApellidoMaterno(rs.getString(7));
          persona.setEstado(rs.getInt(8));
        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(IPersonaDao.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();

    return persona;
  }
}
