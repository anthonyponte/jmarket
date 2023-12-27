package pe.gob.sunat.jmarket.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.gob.sunat.jmarket.dao.UsuarioDao;
import pe.gob.sunat.jmarket.model.Persona;
import pe.gob.sunat.jmarket.model.Usuario;
import pe.gob.sunat.jmarket.util.MyHsqldbConnection;

public class UsuarioDaoImpl implements UsuarioDao {
  private final MyHsqldbConnection database;

  public UsuarioDaoImpl() {
    this.database = new MyHsqldbConnection();
  }

  @Override
  public Long create(Usuario o) throws SQLException {
    Long id = 0L;

    database.connect();

    String query =
        "INSERT INTO USUARIO (TIPO_USUARIO, NOMBRE_USUARIO, CONTRASENA, ESTADO, "
            + "PERSONA_ID) "
            + "VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement ps =
        database.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

      ps.setInt(1, o.getTipoUsuario());
      ps.setString(2, o.getNombreUsuario());
      ps.setString(3, o.getContrasena());
      ps.setInt(4, o.getEstado());
      ps.setLong(5, o.getPersona().getId());
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
  public Usuario read(Long id) throws SQLException {
    Usuario usuario = null;

    database.connect();

    String query =
        "SELECT A.ID, A.TIPO_USUARIO, A.NOMBRE_USUARIO, A.CONTRASENA, "
            + "A.ESTADO, B.ID, B.TIPO_DOCUMENTO, B.NUMERO_DOCUMENTO, "
            + "B.PRIMER_NOMBRE, B.SEGUNDO_NOMBRE, B.APELLIDO_PATERNO, B.APELLIDO_MATERNO, "
            + "B.ESTADO "
            + "FROM USUARIO A "
            + "INNER JOIN PERSONA B "
            + "ON A.PERSONA_ID = B.ID "
            + "WHERE A.ID = ?";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setLong(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          usuario =
              new Usuario(
                  rs.getLong(1),
                  rs.getInt(2),
                  rs.getString(3),
                  rs.getString(4),
                  rs.getInt(5),
                  new Persona(
                      rs.getLong(6),
                      rs.getInt(7),
                      rs.getString(8),
                      rs.getString(9),
                      rs.getString(10),
                      rs.getString(11),
                      rs.getString(12),
                      rs.getInt(13)));
        }
      }
    }

    database.disconnect();

    return usuario;
  }

  @Override
  public List<Usuario> read() throws SQLException {
    List<Usuario> list = new ArrayList<>();

    database.connect();

    String query =
        "SELECT A.ID, A.TIPO_USUARIO, A.NOMBRE_USUARIO, A.CONTRASENA, "
            + "A.ESTADO, B.ID, B.TIPO_DOCUMENTO, B.NUMERO_DOCUMENTO, "
            + "B.PRIMER_NOMBRE, B.SEGUNDO_NOMBRE, B.APELLIDO_PATERNO, B.APELLIDO_MATERNO, "
            + "B.ESTADO "
            + "FROM USUARIO A "
            + "INNER JOIN PERSONA B "
            + "ON A.PERSONA_ID = B.ID "
            + "ORDER BY A.ID DESC";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          Usuario usuario =
              new Usuario(
                  rs.getLong(1),
                  rs.getInt(2),
                  rs.getString(3),
                  rs.getString(4),
                  rs.getInt(5),
                  new Persona(
                      rs.getLong(6),
                      rs.getInt(7),
                      rs.getString(8),
                      rs.getString(9),
                      rs.getString(10),
                      rs.getString(11),
                      rs.getString(12),
                      rs.getInt(13)));

          list.add(usuario);
        }
      }
    }

    database.disconnect();

    return list;
  }

  @Override
  public void update(Usuario o) throws SQLException {
    database.connect();

    String query =
        "UPDATE USUARIO SET TIPO_USUARIO = ?, NOMBRE_USUARIO = ?, CONTRASENA = ?, ESTADO = ? WHERE ID = ?";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setInt(1, o.getTipoUsuario());
      ps.setString(2, o.getNombreUsuario());
      ps.setString(3, o.getContrasena());
      ps.setInt(4, o.getEstado());
      ps.setLong(5, o.getId());

      ps.executeUpdate();
    }

    database.disconnect();
  }

  @Override
  public void delete(Long id) throws SQLException {
    database.connect();

    String query = "DELETE FROM USUARIO WHERE ID = ?";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setLong(1, id);
      ps.executeUpdate();
    }

    database.disconnect();
  }

  @Override
  public Long validate(String nombreUsuario, String contrasena) throws SQLException {
    Long id = 0L;

    database.connect();

    String query =
        "SELECT ID FROM USUARIO WHERE NOMBRE_USUARIO = ? AND CONTRASENA = ? AND ESTADO = 1";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setString(1, nombreUsuario);
      ps.setString(2, contrasena);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          id = rs.getLong(1);
        }
      }
    }

    database.disconnect();

    return id;
  }
}
