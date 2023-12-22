package pe.gob.sunat.jmarket.dao;

import java.util.List;

public interface ObjectDao<O> {
  Long create(O o);

  O read(Long id);

  List<O> read();

  void update(O o);

  void delete(Long id);
}
