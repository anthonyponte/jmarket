/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pe.gob.sunat.jmarket.idao;

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

/**
 * @author Anthony Ponte
 */
public class IUsuarioDao implements UsuarioDao {
  private final MyHsqldbConnection database;

  public IUsuarioDao() {
    this.database = new MyHsqldbConnection();
  }

  @Override
  public Long create(Usuario usuario) {
    Long id = 0L;

    database.connect();

    String query =
        "INSERT INTO USUARIO (TIPO_USUARIO, NOMBRE_USUARIO, CONTRASENA, ESTADO, PERSONA_ID) VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement ps =
        database.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

      ps.setInt(1, usuario.getTipoUsuario());
      ps.setString(2, usuario.getNombreUsuario());
      ps.setString(3, usuario.getContrasena());
      ps.setInt(4, usuario.getEstado());
      ps.setLong(5, usuario.getPersona().getId());
      ps.executeUpdate();

      try (ResultSet rs = ps.getGeneratedKeys()) {
        while (rs.next()) {
          id = rs.getLong(1);
        }
      }

    } catch (SQLException ex) {
      Logger.getLogger(IUsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();

    return id;
  }

  @Override
  public Usuario read(Long id) {
    Usuario usuario = null;

    database.connect();

    String query =
        "SELECT A.ID, A.NOMBRE_USUARIO, A.CONTRASENA, A.ESTADO, "
            + "B.TIPO_DOCUMENTO, B.NUMERO_DOCUMENTO, B.PRIMER_NOMBRE, B.SEGUNDO_NOMBRE, B.APELLIDO_PATERNO, B.APELLIDO_MATERNO, B.ESTADO "
            + "FROM USUARIO A "
            + "INNER JOIN PERSONA B ON A.PERSONA_ID = B.ID "
            + "WHERE A.ID = ? "
            + "ORDER BY A.ID DESC";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setLong(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          usuario = new Usuario();
          usuario.setId(rs.getLong(1));
          usuario.setNombreUsuario(rs.getString(2));
          usuario.setContrasena(rs.getString(3));
          usuario.setEstado(rs.getInt(4));

          Persona persona = new Persona();
          persona.setTipoDocumento(rs.getInt(5));
          persona.setNumeroDocumento(rs.getString(6));
          persona.setPrimerNombre(rs.getString(7));
          persona.setSegundoNombre(rs.getString(8));
          persona.setApellidoPaterno(rs.getString(9));
          persona.setApellidoMaterno(rs.getString(10));
          persona.setEstado(rs.getInt(11));

          usuario.setPersona(persona);
        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(IUsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();

    return usuario;
  }

  @Override
  public List<Usuario> read() {
    List<Usuario> list = new ArrayList<>();

    database.connect();

    String query =
        "SELECT A.ID, A.NOMBRE_USUARIO, A.CONTRASENA, A.ESTADO, "
            + "B.TIPO_DOCUMENTO, B.NUMERO_DOCUMENTO, B.PRIMER_NOMBRE, B.SEGUNDO_NOMBRE, B.APELLIDO_PATERNO, B.APELLIDO_MATERNO, B.ESTADO "
            + "FROM USUARIO A "
            + "INNER JOIN PERSONA B ON A.PERSONA_ID = B.ID "
            + "ORDER BY A.ID DESC";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          Usuario usuario = new Usuario();
          usuario.setId(rs.getLong(1));
          usuario.setNombreUsuario(rs.getString(2));
          usuario.setContrasena(rs.getString(3));
          usuario.setEstado(rs.getInt(4));

          Persona persona = new Persona();
          persona.setTipoDocumento(rs.getInt(5));
          persona.setNumeroDocumento(rs.getString(6));
          persona.setPrimerNombre(rs.getString(7));
          persona.setSegundoNombre(rs.getString(8));
          persona.setApellidoPaterno(rs.getString(9));
          persona.setApellidoMaterno(rs.getString(10));
          persona.setEstado(rs.getInt(11));

          usuario.setPersona(persona);

          list.add(usuario);
        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(IUsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();

    return list;
  }

  @Override
  public void update(Usuario usuario) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from
    // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public void delete(Long id) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from
    // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
      Logger.getLogger(IUsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();

    return id;
  }
}
