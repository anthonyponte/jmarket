/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */

package pe.gob.sunat.jmarket.model;

/**
 * @author anthonyponte
 */
public enum Estado {
  INACTIVO(0, "INACTIVO"),
  ACTIVO(1, "ACTIVO");

  private final int codigo;
  private final String descripcion;

  private Estado(int codigo, String descripcion) {
    this.codigo = codigo;
    this.descripcion = descripcion;
  }

  public int getCodigo() {
    return codigo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  @Override
  public String toString() {
    return descripcion;
  }
}
