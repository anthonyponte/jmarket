package pe.gob.sunat.jmarket.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Venta {
  private Long id;
  private LocalDate fechaEmision;
  private BigDecimal total;
  private int estado;
  private Persona persona;
  private List<VentaDetalle> detalles;

  public Venta() {}

  public Venta(
      Long id,
      LocalDate fechaEmision,
      BigDecimal total,
      int estado,
      Persona persona,
      List<VentaDetalle> detalles) {
    this.id = id;
    this.fechaEmision = fechaEmision;
    this.total = total;
    this.estado = estado;
    this.persona = persona;
    this.detalles = detalles;
  }

  public Venta(
      LocalDate fechaEmision,
      BigDecimal total,
      int estado,
      Persona persona,
      List<VentaDetalle> detalles) {
    this.fechaEmision = fechaEmision;
    this.total = total;
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

  public LocalDate getFechaEmision() {
    return fechaEmision;
  }

  public void setFechaEmision(LocalDate fechaEmision) {
    this.fechaEmision = fechaEmision;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
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
        + ", fechaEmision="
        + fechaEmision
        + ", total="
        + total
        + ", estado="
        + estado
        + ", persona="
        + persona
        + ", detalles="
        + detalles
        + '}';
  }
}
