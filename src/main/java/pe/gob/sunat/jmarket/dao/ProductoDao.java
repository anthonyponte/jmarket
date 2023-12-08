/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package pe.gob.sunat.jmarket.dao;

import java.util.List;
import pe.gob.sunat.jmarket.model.Producto;

/**
 * @author Anthony Ponte
 */
public interface ProductoDao {
  void create(Producto producto);

  Producto read(Long id);

  List<Producto> read();

  void update(Producto producto);

  void delete(Long id);
}
