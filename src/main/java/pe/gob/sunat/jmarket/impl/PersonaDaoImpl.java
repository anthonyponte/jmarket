package pe.gob.sunat.jmarket.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.gob.sunat.jmarket.dao.PersonaDao;
import pe.gob.sunat.jmarket.model.Persona;
import pe.gob.sunat.jmarket.util.MyHsqldbConnection;

public class PersonaDaoImpl implements PersonaDao {
  private final MyHsqldbConnection database;

  public PersonaDaoImpl() {
    this.database = new MyHsqldbConnection();
  }

  @Override
  public Long create(Persona o) throws SQLException {
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
    }

    database.disconnect();

    return id;
  }

  @Override
  public Persona read(Long id) throws SQLException {
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
          persona =
              new Persona(
                  rs.getLong(1),
                  rs.getInt(2),
                  rs.getString(3),
                  rs.getString(4),
                  rs.getString(5),
                  rs.getString(6),
                  rs.getString(7),
                  rs.getInt(8));
        }
      }
    }

    database.disconnect();

    return persona;
  }

  @Override
  public List<Persona> read() throws SQLException {
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
          Persona persona =
              new Persona(
                  rs.getLong(1),
                  rs.getInt(2),
                  rs.getString(3),
                  rs.getString(4),
                  rs.getString(5),
                  rs.getString(6),
                  rs.getString(7),
                  rs.getInt(8));

          list.add(persona);
        }
      }
    }

    database.disconnect();

    return list;
  }

  @Override
  public void update(Persona o) throws SQLException {
    database.connect();

    String query = "UPDATE PERSONA SET ESTADO = ? WHERE ID = ?";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setInt(1, o.getEstado());
      ps.setLong(2, o.getId());

      ps.executeUpdate();
    }

    database.disconnect();
  }

  @Override
  public void delete(Long id) throws SQLException {
    database.connect();

    String query = "DELETE FROM PERSONA WHERE ID = ?";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setLong(1, id);
      ps.executeUpdate();
    }

    database.disconnect();
  }

  @Override
  public Persona read(String numeroDocumento) throws SQLException {
    Persona persona = null;

    database.connect();

    String query =
        "SELECT ID, TIPO_DOCUMENTO, NUMERO_DOCUMENTO, PRIMER_NOMBRE, "
            + "SEGUNDO_NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, ESTADO "
            + "FROM PERSONA  "
            + "WHERE NUMERO_DOCUMENTO = ? AND ESTADO = 1";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setString(1, numeroDocumento);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          persona =
              new Persona(
                  rs.getLong(1),
                  rs.getInt(2),
                  rs.getString(3),
                  rs.getString(4),
                  rs.getString(5),
                  rs.getString(6),
                  rs.getString(7),
                  rs.getInt(8));
        }
      }
    }

    database.disconnect();

    return persona;
  }
}
