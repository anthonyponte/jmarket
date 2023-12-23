package pe.gob.sunat.jmarket.model;

import java.math.BigDecimal;

public class VentaDetalle {
  private Long id;
  private int numeroFila;
  private BigDecimal precioUnitario;
  private BigDecimal cantidad;
  private BigDecimal subtotal;
  private int estado;
  private Producto producto;
  private Venta venta;

  public VentaDetalle() {}

  public VentaDetalle(
      Long id,
      int numeroFila,
      BigDecimal precioUnitario,
      BigDecimal cantidad,
      BigDecimal subtotal,
      int estado,
      Producto producto,
      Venta venta) {
    this.id = id;
    this.numeroFila = numeroFila;
    this.precioUnitario = precioUnitario;
    this.cantidad = cantidad;
    this.subtotal = subtotal;
    this.estado = estado;
    this.producto = producto;
    this.venta = venta;
  }

  public VentaDetalle(
      int numeroFila,
      BigDecimal precioUnitario,
      BigDecimal cantidad,
      BigDecimal subtotal,
      int estado,
      Producto producto,
      Venta venta) {
    this.numeroFila = numeroFila;
    this.precioUnitario = precioUnitario;
    this.cantidad = cantidad;
    this.subtotal = subtotal;
    this.estado = estado;
    this.producto = producto;
    this.venta = venta;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getNumeroFila() {
    return numeroFila;
  }

  public void setNumeroFila(int numeroFila) {
    this.numeroFila = numeroFila;
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

  public Producto getProducto() {
    return producto;
  }

  public void setProducto(Producto producto) {
    this.producto = producto;
  }

  public Venta getVenta() {
    return venta;
  }

  public void setVenta(Venta venta) {
    this.venta = venta;
  }

  @Override
  public String toString() {
    return "VentaDetalle{"
        + "id="
        + id
        + ", numeroFila="
        + numeroFila
        + ", precioUnitario="
        + precioUnitario
        + ", cantidad="
        + cantidad
        + ", subtotal="
        + subtotal
        + ", estado="
        + estado
        + ", producto="
        + producto
        + ", venta="
        + venta
        + '}';
  }
}
