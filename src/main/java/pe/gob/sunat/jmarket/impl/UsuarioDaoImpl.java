package pe.gob.sunat.jmarket.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
  public Long create(Usuario o) {
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

    } catch (SQLException ex) {
      Logger.getLogger(UsuarioDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();

    return id;
  }

  @Override
  public Usuario read(Long id) {
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
          usuario = new Usuario();
          usuario.setId(rs.getLong(1));
          usuario.setTipoUsuario(rs.getInt(2));
          usuario.setNombreUsuario(rs.getString(3));
          usuario.setContrasena(rs.getString(4));
          usuario.setEstado(rs.getInt(5));

          Persona persona = new Persona();
          persona.setId(rs.getLong(6));
          persona.setTipoDocumento(rs.getInt(7));
          persona.setNumeroDocumento(rs.getString(8));
          persona.setPrimerNombre(rs.getString(9));
          persona.setSegundoNombre(rs.getString(10));
          persona.setApellidoPaterno(rs.getString(11));
          persona.setApellidoMaterno(rs.getString(12));
          persona.setEstado(rs.getInt(13));

          usuario.setPersona(persona);
        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(UsuarioDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();

    return usuario;
  }

  @Override
  public List<Usuario> read() {
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
          Usuario usuario = new Usuario();
          usuario.setId(rs.getLong(1));
          usuario.setTipoUsuario(rs.getInt(2));
          usuario.setNombreUsuario(rs.getString(3));
          usuario.setContrasena(rs.getString(4));
          usuario.setEstado(rs.getInt(5));

          Persona persona = new Persona();
          persona.setId(rs.getLong(6));
          persona.setTipoDocumento(rs.getInt(7));
          persona.setNumeroDocumento(rs.getString(8));
          persona.setPrimerNombre(rs.getString(9));
          persona.setSegundoNombre(rs.getString(10));
          persona.setApellidoPaterno(rs.getString(11));
          persona.setApellidoMaterno(rs.getString(12));
          persona.setEstado(rs.getInt(13));

          usuario.setPersona(persona);

          list.add(usuario);
        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(UsuarioDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();

    return list;
  }

  @Override
  public void update(Usuario o) {
    database.connect();

    String query =
        "UPDATE USUARIO SET TIPO_USUARIO = ?, NOMBRE_USUARIO = ?, CONTRASENA = ? WHERE ID = ?";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setInt(1, o.getTipoUsuario());
      ps.setString(2, o.getNombreUsuario());
      ps.setString(3, o.getContrasena());
      ps.setLong(4, o.getId());

      ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(UsuarioDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();
  }

  @Override
  public void delete(Long id) {
    database.connect();

    String query = "DELETE FROM USUARIO WHERE ID = ?";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setLong(1, id);
      ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(UsuarioDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();
  }

  @Override
  public Long validate(String nombreUsuario, String contrasena) {
    Long id = 0L;

    database.connect();

    String query = "SELECT ID FROM USUARIO WHERE NOMBRE_USUARIO = ? AND CONTRASENA = ?";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setString(1, nombreUsuario);
      ps.setString(2, contrasena);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          id = rs.getLong(1);
        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(UsuarioDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();

    return id;
  }
}
