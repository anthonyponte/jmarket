/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pe.gob.sunat.jmarket.idao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.gob.sunat.jmarket.dao.ProductoDao;
import pe.gob.sunat.jmarket.model.Producto;
import pe.gob.sunat.jmarket.util.MyHsqldbConnection;

/**
 * @author anthonyponte
 */
public class IProductoDao implements ProductoDao {
  private final MyHsqldbConnection database;

  public IProductoDao() {
    this.database = new MyHsqldbConnection();
  }

  @Override
  public Long create(Producto producto) {
    Long id = 0L;

    database.connect();

    String query =
        "INSERT INTO PRODUCTO (CODIGO, DESCRIPCION, UNIDAD_MEDIDA, PRECIO_UNITARIO, ESTADO) VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement ps =
        database.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

      ps.setString(1, producto.getCodigo());
      ps.setString(2, producto.getDescripcion());
      ps.setInt(3, producto.getUnidadMedida());
      ps.setBigDecimal(4, producto.getPrecioUnitario());
      ps.setInt(5, producto.getEstado());
      ps.executeUpdate();

      try (ResultSet rs = ps.getGeneratedKeys()) {
        while (rs.next()) {
          id = rs.getLong(1);
        }
      }

    } catch (SQLException ex) {
      Logger.getLogger(IProductoDao.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();

    return id;
  }

  @Override
  public Producto read(Long id) {
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
          producto = new Producto();
          producto.setId(rs.getLong(1));
          producto.setCodigo(rs.getString(2));
          producto.setDescripcion(rs.getString(3));
          producto.setUnidadMedida(rs.getInt(4));
          producto.setPrecioUnitario(rs.getBigDecimal(5));
          producto.setEstado(rs.getInt(6));
        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(IProductoDao.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();

    return producto;
  }

  @Override
  public List<Producto> read() {
    List<Producto> list = new ArrayList<>();

    database.connect();

    String query =
        "SELECT ID, CODIGO, DESCRIPCION, UNIDAD_MEDIDA, PRECIO_UNITARIO, ESTADO "
            + "FROM PRODUCTO "
            + "ORDER BY ID DESC";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          Producto producto = new Producto();
          producto.setId(rs.getLong(1));
          producto.setCodigo(rs.getString(2));
          producto.setDescripcion(rs.getString(3));
          producto.setUnidadMedida(rs.getInt(4));
          producto.setPrecioUnitario(rs.getBigDecimal(5));
          producto.setEstado(rs.getInt(6));

          list.add(producto);
        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(IProductoDao.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();

    return list;
  }

  @Override
  public void update(Producto producto) {
    database.connect();

    String query =
        "UPDATE PRODUCTO SET DESCRIPCION = ?, UNIDAD_MEDIDA = ?, PRECIO_UNITARIO = ? WHERE ID = ?";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setString(1, producto.getDescripcion());
      ps.setInt(2, producto.getUnidadMedida());
      ps.setBigDecimal(3, producto.getPrecioUnitario());
      ps.setLong(4, producto.getId());

      ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(IProductoDao.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();
  }

  @Override
  public void delete(Long id) {
    database.connect();

    String query = "DELETE FROM PRODUCTO WHERE ID = ?";

    try (PreparedStatement ps = database.getConnection().prepareStatement(query)) {
      ps.setLong(1, id);
      ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(IProductoDao.class.getName()).log(Level.SEVERE, null, ex);
    }

    database.disconnect();
  }
}
