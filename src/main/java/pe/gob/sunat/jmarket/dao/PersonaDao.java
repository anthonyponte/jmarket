/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package pe.gob.sunat.jmarket.dao;

import java.util.List;
import pe.gob.sunat.jmarket.model.Persona;
import pe.gob.sunat.jmarket.model.Usuario;

/**
 * @author anthonyponte
 */
public interface PersonaDao {
  Long create(Persona persona);

  Usuario read(Long id);

  List<Persona> read();

  void update(Persona persona);

  void delete(Long id);
}
