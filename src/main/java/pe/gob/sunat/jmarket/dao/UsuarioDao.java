package pe.gob.sunat.jmarket.dao;

import java.sql.SQLException;
import pe.gob.sunat.jmarket.model.Usuario;

public interface UsuarioDao extends ObjectDao<Usuario> {
  Long validate(String nombreUsuario, String contrasena) throws SQLException;
}
