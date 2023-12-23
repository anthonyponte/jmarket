package pe.gob.sunat.jmarket.dao;

import pe.gob.sunat.jmarket.model.Producto;

public interface ProductoDao extends ObjectDao<Producto> {
  Producto read(String codigo);
}
