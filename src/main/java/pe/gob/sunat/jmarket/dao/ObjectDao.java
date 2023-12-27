package pe.gob.sunat.jmarket.dao;

import java.sql.SQLException;
import java.util.List;

public interface ObjectDao<O> {
  Long create(O o) throws SQLException;

  O read(Long id) throws SQLException;

  List<O> read() throws SQLException;

  void update(O o) throws SQLException;

  void delete(Long id) throws SQLException;
}
