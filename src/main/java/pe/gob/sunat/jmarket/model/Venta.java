package pe.gob.sunat.jmarket.model;

import java.time.LocalDate;

public class Venta {
  private Long id;
  private LocalDate fechaEmision;
  private int estado;
  private Persona persona;

  public Venta() {}

  public Venta(Long id, LocalDate fechaEmision, int estado, Persona persona) {
    this.id = id;
    this.fechaEmision = fechaEmision;
    this.estado = estado;
    this.persona = persona;
  }

  public Venta(LocalDate fechaEmision, int estado, Persona persona) {
    this.fechaEmision = fechaEmision;
    this.estado = estado;
    this.persona = persona;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  @Override
  public String toString() {
    return "Venta{"
        + "id="
        + id
        + ", fechaEmision="
        + fechaEmision
        + ", estado="
        + estado
        + ", persona="
        + persona
        + '}';
  }
}
