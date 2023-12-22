/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pe.gob.sunat.jmarket.model;

/**
 * @author anthonyponte
 */
public class Persona {
  private Long id;
  private int tipoDocumento;
  private String numeroDocumento;
  private String primerNombre;
  private String segundoNombre;
  private String apellidoPaterno;
  private String apellidoMaterno;
  private int estado;

  public Persona() {}

  public Persona(
      Long id,
      int tipoDocumento,
      String numeroDocumento,
      String primerNombre,
      String segundoNombre,
      String apellidoPaterno,
      String apellidoMaterno,
      int estado) {
    this.id = id;
    this.tipoDocumento = tipoDocumento;
    this.numeroDocumento = numeroDocumento;
    this.primerNombre = primerNombre;
    this.segundoNombre = segundoNombre;
    this.apellidoPaterno = apellidoPaterno;
    this.apellidoMaterno = apellidoMaterno;
    this.estado = estado;
  }

  public Persona(
      int tipoDocumento,
      String numeroDocumento,
      String primerNombre,
      String segundoNombre,
      String apellidoPaterno,
      String apellidoMaterno,
      int estado) {
    this.tipoDocumento = tipoDocumento;
    this.numeroDocumento = numeroDocumento;
    this.primerNombre = primerNombre;
    this.segundoNombre = segundoNombre;
    this.apellidoPaterno = apellidoPaterno;
    this.apellidoMaterno = apellidoMaterno;
    this.estado = estado;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(int tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public String getNumeroDocumento() {
    return numeroDocumento;
  }

  public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }

  public String getPrimerNombre() {
    return primerNombre;
  }

  public void setPrimerNombre(String primerNombre) {
    this.primerNombre = primerNombre;
  }

  public String getSegundoNombre() {
    return segundoNombre;
  }

  public void setSegundoNombre(String segundoNombre) {
    this.segundoNombre = segundoNombre;
  }

  public String getApellidoPaterno() {
    return apellidoPaterno;
  }

  public void setApellidoPaterno(String apellidoPaterno) {
    this.apellidoPaterno = apellidoPaterno;
  }

  public String getApellidoMaterno() {
    return apellidoMaterno;
  }

  public void setApellidoMaterno(String apellidoMaterno) {
    this.apellidoMaterno = apellidoMaterno;
  }

  public int getEstado() {
    return estado;
  }

  public void setEstado(int estado) {
    this.estado = estado;
  }

  public String getNombreCompleto() {
    return primerNombre + " " + segundoNombre + " " + apellidoPaterno + " " + apellidoMaterno;
  }

  @Override
  public String toString() {
    return "Persona{"
        + "id="
        + id
        + ", tipoDocumento="
        + tipoDocumento
        + ", numeroDocumento="
        + numeroDocumento
        + ", primerNombre="
        + primerNombre
        + ", segundoNombre="
        + segundoNombre
        + ", apellidoPaterno="
        + apellidoPaterno
        + ", apellidoMaterno="
        + apellidoMaterno
        + ", estado="
        + estado
        + '}';
  }
}
