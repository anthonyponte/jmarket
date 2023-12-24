/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pe.gob.sunat.jmarket.model.num;

/**
 * @author anthonyponte
 */
public enum TipoUsuario {
  ADMINISTRADOR(0, "ADMINISTRADOR"),
  USUARIO(1, "USUARIO"),
  CLIENTE(2, "CLIENTE");

  private final int codigo;
  private final String descripcion;

  private TipoUsuario(int codigo, String descripcion) {
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
