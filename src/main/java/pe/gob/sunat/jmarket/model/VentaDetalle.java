package pe.gob.sunat.jmarket.model;

import java.math.BigDecimal;

public class VentaDetalle {
  private Long id;
  private int fila;
  private Venta venta;
  private Producto producto;
  private BigDecimal precioUnitario;
  private BigDecimal cantidad;
  private BigDecimal subtotal;
  private int estado;

  public VentaDetalle() {}

  public VentaDetalle(
      Long id,
      int fila,
      Venta venta,
      Producto producto,
      BigDecimal precioUnitario,
      BigDecimal cantidad,
      BigDecimal subtotal,
      int estado) {
    this.id = id;
    this.fila = fila;
    this.venta = venta;
    this.producto = producto;
    this.precioUnitario = precioUnitario;
    this.cantidad = cantidad;
    this.subtotal = subtotal;
    this.estado = estado;
  }

  public VentaDetalle(
      int fila,
      Venta venta,
      Producto producto,
      BigDecimal precioUnitario,
      BigDecimal cantidad,
      BigDecimal subtotal,
      int estado) {
    this.fila = fila;
    this.venta = venta;
    this.producto = producto;
    this.precioUnitario = precioUnitario;
    this.cantidad = cantidad;
    this.subtotal = subtotal;
    this.estado = estado;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getFila() {
    return fila;
  }

  public void setFila(int fila) {
    this.fila = fila;
  }

  public Venta getVenta() {
    return venta;
  }

  public void setVenta(Venta venta) {
    this.venta = venta;
  }

  public Producto getProducto() {
    return producto;
  }

  public void setProducto(Producto producto) {
    this.producto = producto;
  }

  public BigDecimal getPrecioUnitario() {
    return precioUnitario;
  }

  public void setPrecioUnitario(BigDecimal precioUnitario) {
    this.precioUnitario = precioUnitario;
  }

  public BigDecimal getCantidad() {
    return cantidad;
  }

  public void setCantidad(BigDecimal cantidad) {
    this.cantidad = cantidad;
  }

  public BigDecimal getSubtotal() {
    return subtotal;
  }

  public void setSubtotal(BigDecimal subtotal) {
    this.subtotal = subtotal;
  }

  public int getEstado() {
    return estado;
  }

  public void setEstado(int estado) {
    this.estado = estado;
  }

  @Override
  public String toString() {
    return "VentaDetalle{"
        + "id="
        + id
        + ", fila="
        + fila
        + ", venta="
        + venta
        + ", producto="
        + producto
        + ", precioUnitario="
        + precioUnitario
        + ", cantidad="
        + cantidad
        + ", subtotal="
        + subtotal
        + ", estado="
        + estado
        + '}';
  }
}
