package pe.gob.sunat.jmarket.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.gob.sunat.jmarket.dao.PersonaDao;
import pe.gob.sunat.jmarket.model.Persona;
import pe.gob.sunat.jmarket.util.MyHsqldbConnection;

public class PersonaDaoImpl implements PersonaDao {
  private final MyHsqldbConnection database;

  public PersonaDaoImpl() {
    this.database = new MyHsqldbConnection();
  }

  @Override
  public Long create(Persona o) {
    Long id = 0L;

    database.connect();

    String query =
        "INSERT INTO PERSONA "
            + "(TIPO_DOCUMENTO, NUMERO_DOCUMENTO, PRIMER_NOMBRE, SEGUNDO_NOMBRE, "
            + "APELLIDO_PATERNO, APELLIDO_MATERNO, ESTADO) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement ps =
        database.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

      ps.setInt(1, o.getTipoDocumento());
      ps.setString(2, o.getNumeroDocumento());
      ps.setString(3, o.getPrimerNombre());
      ps.setString(4, o.getSegundoNombre());
      ps.setString(5, o.getApellidoPaterno());
      ps.setString(6, o.getApellidoMaterno());
      ps.setInt(7, o.getEstado());
      ps.executeUpdate();

      try (ResultSet rs = ps.getGeneratedKeys()) {
        while (rs.next()) {
          id = rs.getLong(1);
        }
      }

    } catch (SQLException ex) {
      Logger.getLogger(PersonaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();

    return id;
  }

  @Override
  public Persona read(Long id) {
    Persona persona = null;

    database.connect();

    String query =
        "SELECT ID, TIPO_DOCUMENTO, NUMERO_DOCUMENTO, PRIMER_NOMBRE, "
            + "SEGUNDO_NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, ESTADO "
            + "FROM PERSONA "
            + "WHERE ID = ?";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setLong(1, id);
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
      Logger.getLogger(PersonaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();

    return persona;
  }

  @Override
  public List<Persona> read() {
    List<Persona> list = new ArrayList<>();

    database.connect();

    String query =
        "SELECT ID, TIPO_DOCUMENTO, NUMERO_DOCUMENTO, PRIMER_NOMBRE, "
            + "SEGUNDO_NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, ESTADO "
            + "FROM PERSONA "
            + "ORDER BY ID DESC";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          Persona persona = new Persona();
          persona.setId(rs.getLong(1));
          persona.setTipoDocumento(rs.getInt(2));
          persona.setNumeroDocumento(rs.getString(3));
          persona.setPrimerNombre(rs.getString(4));
          persona.setSegundoNombre(rs.getString(5));
          persona.setApellidoPaterno(rs.getString(6));
          persona.setApellidoMaterno(rs.getString(7));
          persona.setEstado(rs.getInt(8));

          list.add(persona);
        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(PersonaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();

    return list;
  }

  @Override
  public void update(Persona o) {
    database.connect();

    String query =
        "UPDATE PERSONA SET PRIMER_NOMBRE = ?, SEGUNDO_NOMBRE = ?, APELLIDO_PATERNO = ?, "
            + "APELLIDO_MATERNO = ? "
            + "WHERE ID = ?";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setString(1, o.getPrimerNombre());
      ps.setString(2, o.getSegundoNombre());
      ps.setString(3, o.getApellidoPaterno());
      ps.setString(4, o.getApellidoMaterno());
      ps.setLong(5, o.getId());

      ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(PersonaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();
  }

  @Override
  public void delete(Long id) {
    database.connect();

    String query = "DELETE FROM PERSONA WHERE ID = ?";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setLong(1, id);
      ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(PersonaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();
  }

  @Override
  public Persona read(String numeroDocumento) {
    Persona persona = null;

    database.connect();

    String query =
        "SELECT ID, TIPO_DOCUMENTO, NUMERO_DOCUMENTO, PRIMER_NOMBRE, "
            + "SEGUNDO_NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, ESTADO "
            + "FROM PERSONA  "
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
      Logger.getLogger(PersonaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();

    return persona;
  }
}
