/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pe.gob.sunat.jmarket.model;

/**
 * @author Anthony Ponte
 */
public class UnidadMedida {
  private Long id;
  private String codigo;
  private String descripcion;
  private int estado;

  public UnidadMedida() {}

  public UnidadMedida(Long id, String codigo, String descripcion, int estado) {
    this.id = id;
    this.codigo = codigo;
    this.descripcion = descripcion;
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

  public int getEstado() {
    return estado;
  }

  public void setEstado(int estado) {
    this.estado = estado;
  }

  @Override
  public String toString() {
    return "UnidadMedida{"
        + "id="
        + id
        + ", codigo="
        + codigo
        + ", descripcion="
        + descripcion
        + ", estado="
        + estado
        + '}';
  }
}
