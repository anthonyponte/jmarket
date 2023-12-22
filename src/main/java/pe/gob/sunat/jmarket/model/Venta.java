package pe.gob.sunat.jmarket.model;

import java.time.LocalDate;
import java.util.List;

public class Venta {
  private Long id;
  private Long numero;
  private LocalDate fechaEmision;
  private int estado;
  private Persona persona;
  private List<VentaDetalle> detalles;

  public Venta() {}

  public Venta(
      Long id,
      Long numero,
      LocalDate fechaEmision,
      int estado,
      Persona persona,
      List<VentaDetalle> detalles) {
    this.id = id;
    this.numero = numero;
    this.fechaEmision = fechaEmision;
    this.estado = estado;
    this.persona = persona;
    this.detalles = detalles;
  }

  public Venta(
      Long numero,
      LocalDate fechaEmision,
      int estado,
      Persona persona,
      List<VentaDetalle> detalles) {
    this.numero = numero;
    this.fechaEmision = fechaEmision;
    this.estado = estado;
    this.persona = persona;
    this.detalles = detalles;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getNumero() {
    return numero;
  }

  public void setNumero(Long numero) {
    this.numero = numero;
  }

  public LocalDate getFechaEmision() {
    return fechaEmision;
  }

  public void setFechaEmision(LocalDate fechaEmision) {
    this.fechaEmision = fechaEmision;
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

  public List<VentaDetalle> getDetalles() {
    return detalles;
  }

  public void setDetalles(List<VentaDetalle> detalles) {
    this.detalles = detalles;
  }

  @Override
  public String toString() {
    return "Venta{"
        + "id="
        + id
        + ", numero="
        + numero
        + ", fechaEmision="
        + fechaEmision
        + ", estado="
        + estado
        + ", persona="
        + persona
        + ", detalles="
        + detalles
        + '}';
  }
}
