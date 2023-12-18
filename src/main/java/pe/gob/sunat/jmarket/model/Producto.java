package pe.gob.sunat.jmarket.model;

import java.math.BigDecimal;

public class Producto {
  private Long id;
  private String codigo;
  private String descripcion;
  private int unidadMedida;
  private BigDecimal precioUnitario;
  private int estado;

  public Producto() {}

  public Producto(
      Long id,
      String codigo,
      String descripcion,
      int unidadMedida,
      BigDecimal precioUnitario,
      int estado) {
    this.id = id;
    this.codigo = codigo;
    this.descripcion = descripcion;
    this.unidadMedida = unidadMedida;
    this.precioUnitario = precioUnitario;
    this.estado = estado;
  }

  public Producto(
      String codigo, String descripcion, int unidadMedida, BigDecimal precioUnitario, int estado) {
    this.codigo = codigo;
    this.descripcion = descripcion;
    this.unidadMedida = unidadMedida;
    this.precioUnitario = precioUnitario;
    this.estado = estado;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public int getUnidadMedida() {
    return unidadMedida;
  }

  public void setUnidadMedida(int unidadMedida) {
    this.unidadMedida = unidadMedida;
  }

  public BigDecimal getPrecioUnitario() {
    return precioUnitario;
  }

  public void setPrecioUnitario(BigDecimal precioUnitario) {
    this.precioUnitario = precioUnitario;
  }

  public int getEstado() {
    return estado;
  }

  public void setEstado(int estado) {
    this.estado = estado;
  }

  @Override
  public String toString() {
    return "ItemVenta{"
        + "id="
        + id
        + ", codigo="
        + codigo
        + ", descripcion="
        + descripcion
        + ", unidadMedida="
        + unidadMedida
        + ", precioUnitario="
        + precioUnitario
        + ", estado="
        + estado
        + '}';
  }
}
