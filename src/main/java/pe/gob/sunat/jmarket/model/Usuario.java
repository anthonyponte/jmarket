/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pe.gob.sunat.jmarket.model;

/**
 * @author Anthony Ponte
 */
public class Usuario {
  private Long id;
  private String usuario;
  private String contrasena;
  private int estado;

  public Usuario() {}

  public Usuario(Long id, String usuario, String contrasena, int estado) {
    this.id = id;
    this.usuario = usuario;
    this.contrasena = contrasena;
    this.estado = estado;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getContrasena() {
    return contrasena;
  }

  public void setContrasena(String contrasena) {
    this.contrasena = contrasena;
  }

  public int getEstado() {
    return estado;
  }

  public void setEstado(int estado) {
    this.estado = estado;
  }

  @Override
  public String toString() {
    return "Usuario{"
        + "id="
        + id
        + ", usuario="
        + usuario
        + ", contrasena="
        + contrasena
        + ", estado="
        + estado
        + '}';
  }
}
