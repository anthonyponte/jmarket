/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package pe.gob.sunat.jmarket.dao;

import java.util.List;

/**
 * @author anthonyponte
 */
public interface ObjectDao<O> {
  Long create(O o);

  O read(Long id);

  List<O> read();

  void update(O o);

  void delete(Long id);
}
