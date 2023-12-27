package pe.gob.sunat.jmarket.model;

import javafx.beans.property.SimpleObjectProperty;

public class VentaDetalle {
  private SimpleObjectProperty<Long> id;
  private SimpleObjectProperty<Integer> numeroFila;
  private SimpleObjectProperty<Double> precioUnitario;
  private SimpleObjectProperty<Double> cantidad;
  private SimpleObjectProperty<Double> subtotal;
  private SimpleObjectProperty<Integer> estado;
  private Producto producto;
  private Venta venta;

  public VentaDetalle(
      Long id,
      int numeroFila,
      double precioUnitario,
      double cantidad,
      double subtotal,
      int estado,
      Producto producto,
      Venta venta) {
    this.id = new SimpleObjectProperty<>(id);
    this.numeroFila = new SimpleObjectProperty<>(numeroFila);
    this.precioUnitario = new SimpleObjectProperty<>(precioUnitario);
    this.cantidad = new SimpleObjectProperty<>(cantidad);
    this.subtotal = new SimpleObjectProperty<>(subtotal);
    this.estado = new SimpleObjectProperty<>(estado);
    this.producto = producto;
    this.venta = venta;
  }

  public VentaDetalle(
      int numeroFila,
      double precioUnitario,
      double cantidad,
      double subtotal,
      int estado,
      Producto producto,
      Venta venta) {
    this.numeroFila = new SimpleObjectProperty<>(numeroFila);
    this.precioUnitario = new SimpleObjectProperty<>(precioUnitario);
    this.cantidad = new SimpleObjectProperty<>(cantidad);
    this.subtotal = new SimpleObjectProperty<>(subtotal);
    this.estado = new SimpleObjectProperty<>(estado);
    this.producto = producto;
    this.venta = venta;
  }

  public VentaDetalle(
      double precioUnitario, double cantidad, double subtotal, int estado, Producto producto) {
    this.precioUnitario = new SimpleObjectProperty<>(precioUnitario);
    this.cantidad = new SimpleObjectProperty<>(cantidad);
    this.subtotal = new SimpleObjectProperty<>(subtotal);
    this.estado = new SimpleObjectProperty<>(estado);
    this.producto = producto;
  }

  public Long getId() {
    return id.get();
  }

  public void setId(Long id) {
    this.id.set(id);
  }

  public int getNumeroFila() {
    return numeroFila.get();
  }

  public void setNumeroFila(int numeroFila) {
    this.numeroFila.set(numeroFila);
  }

  public double getPrecioUnitario() {
    return precioUnitario.get();
  }

  public void setPrecioUnitario(double precioUnitario) {
    this.precioUnitario.set(precioUnitario);
  }

  public double getCantidad() {
    return cantidad.get();
  }

  public void setCantidad(double cantidad) {
    this.cantidad.set(cantidad);
  }

  public double getSubtotal() {
    return subtotal.get();
  }

  public void setSubtotal(double subtotal) {
    this.subtotal.set(subtotal);
  }

  public int getEstado() {
    return estado.get();
  }

  public void setEstado(int estado) {
    this.estado.set(estado);
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

  public SimpleObjectProperty<Long> getIdProperty() {
    return id;
  }

  public SimpleObjectProperty<Integer> getNumeroFilaProperty() {
    return numeroFila;
  }

  public SimpleObjectProperty<Double> getPrecioUnitarioProperty() {
    return precioUnitario;
  }

  public SimpleObjectProperty<Double> getCantidadProperty() {
    return cantidad;
  }

  public SimpleObjectProperty<Double> getSubtotalProperty() {
    return subtotal;
  }

  public SimpleObjectProperty<Integer> getEstadoProperty() {
    return estado;
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
