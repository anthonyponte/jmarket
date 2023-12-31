package pe.gob.sunat.jmarket.model;

import java.time.LocalDate;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;

public class Venta {
  private SimpleObjectProperty<Long> id;
  private SimpleObjectProperty<LocalDate> fechaEmision;
  private SimpleObjectProperty<Double> total;
  private SimpleObjectProperty<Integer> estado;
  private Persona persona;
  private List<VentaDetalle> ventaDetalles;

  public Venta(
      Long id,
      LocalDate fechaEmision,
      double total,
      int estado,
      Persona persona,
      List<VentaDetalle> ventaDetalles) {
    this.id = new SimpleObjectProperty<>(id);
    this.fechaEmision = new SimpleObjectProperty<>(fechaEmision);
    this.total = new SimpleObjectProperty<>(total);
    this.estado = new SimpleObjectProperty<>(estado);
    this.persona = persona;
    this.ventaDetalles = ventaDetalles;
  }

  public Venta(Long id, LocalDate fechaEmision, double total, int estado, Persona persona) {
    this.id = new SimpleObjectProperty<>(id);
    this.fechaEmision = new SimpleObjectProperty<>(fechaEmision);
    this.total = new SimpleObjectProperty<>(total);
    this.estado = new SimpleObjectProperty<>(estado);
    this.persona = persona;
  }

  public Venta(
      LocalDate fechaEmision,
      double total,
      int estado,
      Persona persona,
      List<VentaDetalle> ventaDetalles) {
    this.fechaEmision = new SimpleObjectProperty<>(fechaEmision);
    this.total = new SimpleObjectProperty<>(total);
    this.estado = new SimpleObjectProperty<>(estado);
    this.persona = persona;
    this.ventaDetalles = ventaDetalles;
  }

  public Venta(LocalDate fechaEmision, double total, int estado, Persona persona) {
    this.fechaEmision = new SimpleObjectProperty<>(fechaEmision);
    this.total = new SimpleObjectProperty<>(total);
    this.estado = new SimpleObjectProperty<>(estado);
    this.persona = persona;
  }

  public Long getId() {
    return id.get();
  }

  public void setId(Long id) {
    this.id.set(id);
  }

  public LocalDate getFechaEmision() {
    return fechaEmision.get();
  }

  public void setFechaEmision(LocalDate fechaEmision) {
    this.fechaEmision.set(fechaEmision);
  }

  public double getTotal() {
    return total.get();
  }

  public void setTotal(double total) {
    this.total.set(total);
  }

  public int getEstado() {
    return estado.get();
  }

  public void setEstado(int estado) {
    this.estado.set(estado);
  }

  public Persona getPersona() {
    return persona;
  }

  public void setPersona(Persona persona) {
    this.persona = persona;
  }

  public List<VentaDetalle> getVentaDetalles() {
    return ventaDetalles;
  }

  public void setVentaDetalles(List<VentaDetalle> ventaDetalles) {
    this.ventaDetalles = ventaDetalles;
  }

  public SimpleObjectProperty<Long> getIdProperty() {
    return id;
  }

  public SimpleObjectProperty<LocalDate> getFechaEmisionProperty() {
    return fechaEmision;
  }

  public SimpleObjectProperty<Double> getTotalProperty() {
    return total;
  }

  public SimpleObjectProperty<Integer> getEstadoProperty() {
    return estado;
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
        + ", ventaDetalles="
        + ventaDetalles
        + '}';
  }
}
