package pe.gob.sunat.jmarket.dao;

import pe.gob.sunat.jmarket.model.Usuario;

public interface UsuarioDao extends ObjectDao<Usuario> {
  Long validate(String nombreUsuario, String contrasena);
}
