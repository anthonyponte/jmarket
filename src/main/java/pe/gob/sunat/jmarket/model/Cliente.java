/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pe.gob.sunat.jmarket.model;

/**
 * @author Anthony Ponte
 */
public class Cliente {
  private Long id;
  private String dni;
  private String nombre;
  private int estado;

  public Cliente() {}

  public Cliente(Long id, String dni, String nombre, int estado) {
    this.id = id;
    this.dni = dni;
    this.nombre = nombre;
    this.estado = estado;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public int getEstado() {
    return estado;
  }

  public void setEstado(int estado) {
    this.estado = estado;
  }

  @Override
  public String toString() {
    return "Cliente{"
        + "id="
        + id
        + ", dni="
        + dni
        + ", nombre="
        + nombre
        + ", estado="
        + estado
        + '}';
  }
}
