/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pe.gob.sunat.jmarket.model;

/**
 * @author Anthony Ponte
 */
public class CompraDetalle {
  private Long id;
  private Producto producto;
  private double precio;
  private int cantidad;
  private double total;
  private int estado;

  public CompraDetalle() {}

  public CompraDetalle(
      Long id, Producto producto, double precio, int cantidad, double total, int estado) {
    this.id = id;
    this.producto = producto;
    this.precio = precio;
    this.cantidad = cantidad;
    this.total = total;
    this.estado = estado;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Producto getProducto() {
    return producto;
  }

  public void setProducto(Producto producto) {
    this.producto = producto;
  }

  public double getPrecio() {
    return precio;
  }

  public void setPrecio(double precio) {
    this.precio = precio;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public int getEstado() {
    return estado;
  }

  public void setEstado(int estado) {
    this.estado = estado;
  }

  @Override
  public String toString() {
    return "CompraDetalle{"
        + "id="
        + id
        + ", producto="
        + producto
        + ", precio="
        + precio
        + ", cantidad="
        + cantidad
        + ", total="
        + total
        + ", estado="
        + estado
        + '}';
  }
}
