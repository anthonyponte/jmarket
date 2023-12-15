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
  private int tipoUsuario;
  private String nombreUsuario;
  private String contrasena;
  private int estado;
  private Persona persona;

  public Usuario() {}

  public Usuario(
      Long id,
      int tipoUsuario,
      String nombreUsuario,
      String contrasena,
      int estado,
      Persona persona) {
    this.id = id;
    this.tipoUsuario = tipoUsuario;
    this.nombreUsuario = nombreUsuario;
    this.contrasena = contrasena;
    this.estado = estado;
    this.persona = persona;
  }

  public Usuario(
      int tipoUsuario, String nombreUsuario, String contrasena, int estado, Persona persona) {
    this.tipoUsuario = tipoUsuario;
    this.nombreUsuario = nombreUsuario;
    this.contrasena = contrasena;
    this.estado = estado;
    this.persona = persona;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getTipoUsuario() {
    return tipoUsuario;
  }

  public void setTipoUsuario(int tipoUsuario) {
    this.tipoUsuario = tipoUsuario;
  }

  public String getNombreUsuario() {
    return nombreUsuario;
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
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

  public Persona getPersona() {
    return persona;
  }

  public void setPersona(Persona persona) {
    this.persona = persona;
  }

  @Override
  public String toString() {
    return "Usuario{"
        + "id="
        + id
        + ", tipoUsuario="
        + tipoUsuario
        + ", nombreUsuario="
        + nombreUsuario
        + ", contrasena="
        + contrasena
        + ", estado="
        + estado
        + ", persona="
        + persona
        + '}';
  }
}
