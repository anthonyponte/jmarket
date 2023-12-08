/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package pe.gob.sunat.jmarket.dao;

import java.util.List;
import pe.gob.sunat.jmarket.model.Compra;

/**
 * @author Anthony Ponte
 */
public interface CompraDao {
  void create(Compra compra);

  Compra read(Long id);

  List<Compra> read();

  void update(Compra compra);

  void delete(Long id);
}
