package pe.gob.sunat.jmarket.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.gob.sunat.jmarket.dao.ProductoDao;
import pe.gob.sunat.jmarket.model.Producto;
import pe.gob.sunat.jmarket.util.MyHsqldbConnection;

public class ProductoDaoImpl implements ProductoDao {
  private final MyHsqldbConnection database;

  public ProductoDaoImpl() {
    this.database = new MyHsqldbConnection();
  }

  @Override
  public Long create(Producto o) throws SQLException {
    Long id = 0L;

    database.connect();

    String query =
        "INSERT INTO PRODUCTO (CODIGO, DESCRIPCION, UNIDAD_MEDIDA, PRECIO_UNITARIO, ESTADO) VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement ps =
        database.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

      ps.setString(1, o.getCodigo());
      ps.setString(2, o.getDescripcion());
      ps.setInt(3, o.getUnidadMedida());
      ps.setDouble(4, o.getPrecioUnitario());
      ps.setInt(5, o.getEstado());
      ps.executeUpdate();

      try (ResultSet rs = ps.getGeneratedKeys()) {
        while (rs.next()) {
          id = rs.getLong(1);
        }
      }
    }

    database.disconnect();

    return id;
  }

  @Override
  public Producto read(Long id) throws SQLException {
    Producto producto = null;

    database.connect();

    String query =
        "SELECT ID, CODIGO, DESCRIPCION, UNIDAD_MEDIDA, PRECIO_UNITARIO, ESTADO "
            + "FROM PRODUCTO "
            + "WHERE ID = ? "
            + "ORDER BY ID DESC";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setLong(1, id);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          producto =
              new Producto(
                  rs.getLong(1),
                  rs.getString(2),
                  rs.getString(3),
                  rs.getInt(4),
                  rs.getDouble(5),
                  rs.getInt(6));
        }
      }
    }

    database.disconnect();

    return producto;
  }

  @Override
  public List<Producto> read() throws SQLException {
    List<Producto> list = new ArrayList<>();

    database.connect();

    String query =
        "SELECT ID, CODIGO, DESCRIPCION, UNIDAD_MEDIDA, PRECIO_UNITARIO, ESTADO "
            + "FROM PRODUCTO "
            + "ORDER BY ID DESC";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          Producto producto =
              new Producto(
                  rs.getLong(1),
                  rs.getString(2),
                  rs.getString(3),
                  rs.getInt(4),
                  rs.getDouble(5),
                  rs.getInt(6));

          list.add(producto);
        }
      }
    }

    database.disconnect();

    return list;
  }

  @Override
  public void update(Producto o) throws SQLException {
    database.connect();

    String query = "UPDATE PRODUCTO SET PRECIO_UNITARIO = ?, ESTADO = ? WHERE ID = ?";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setDouble(1, o.getPrecioUnitario());
      ps.setInt(2, o.getEstado());
      ps.setLong(3, o.getId());

      ps.executeUpdate();
    }

    database.disconnect();
  }

  @Override
  public void delete(Long id) throws SQLException {
    database.connect();

    String query = "DELETE FROM PRODUCTO WHERE ID = ?";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setLong(1, id);
      ps.executeUpdate();
    }

    database.disconnect();
  }

  @Override
  public Producto read(String codigo) throws SQLException {
    Producto producto = null;

    database.connect();

    String query =
        "SELECT ID, CODIGO, DESCRIPCION, UNIDAD_MEDIDA, PRECIO_UNITARIO, ESTADO "
            + "FROM PRODUCTO "
            + "WHERE CODIGO = ? AND ESTADO =  1";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setString(1, codigo);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          producto =
              new Producto(
                  rs.getLong(1),
                  rs.getString(2),
                  rs.getString(3),
                  rs.getInt(4),
                  rs.getDouble(5),
                  rs.getInt(6));
        }
      }
    }

    database.disconnect();

    return producto;
  }
}
