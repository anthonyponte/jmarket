/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pe.gob.sunat.jmarket.model;

/**
 * @author Anthony Ponte
 */
public class Producto {
  private Long id;
  private String codigo;
  private String descripcion;
  private UnidadMedida unidadMedida;
  private int precio;
  private int estado;

  public Producto() {}

  public Producto(
      Long id,
      String codigo,
      String descripcion,
      UnidadMedida unidadMedida,
      int precio,
      int estado) {
    this.id = id;
    this.codigo = codigo;
    this.descripcion = descripcion;
    this.unidadMedida = unidadMedida;
    this.precio = precio;
    this.estado = estado;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public UnidadMedida getUnidadMedida() {
    return unidadMedida;
  }

  public void setUnidadMedida(UnidadMedida unidadMedida) {
    this.unidadMedida = unidadMedida;
  }

  public int getPrecio() {
    return precio;
  }

  public void setPrecio(int precio) {
    this.precio = precio;
  }

  public int getEstado() {
    return estado;
  }

  public void setEstado(int estado) {
    this.estado = estado;
  }

  @Override
  public String toString() {
    return "Producto{"
        + "id="
        + id
        + ", codigo="
        + codigo
        + ", descripcion="
        + descripcion
        + ", unidadMedida="
        + unidadMedida
        + ", precio="
        + precio
        + ", estado="
        + estado
        + '}';
  }
}
