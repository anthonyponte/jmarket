/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package pe.gob.sunat.jmarket.dao;

import java.util.List;
import pe.gob.sunat.jmarket.model.Usuario;

/**
 * @author Anthony Ponte
 */
public interface UsuarioDao {
  Long create(Usuario usuario);

  Usuario read(Long id);

  List<Usuario> read();

  void update(Usuario usuario);

  void delete(Long id);
}
