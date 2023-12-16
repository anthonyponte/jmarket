package pe.gob.sunat.jmarket.model;

import java.math.BigDecimal;

public class VentaDetalle {
	private Long id;
	private Venta venta;
	private int fila;
	private ItemVenta item;
	private BigDecimal precioUnitario;
	private BigDecimal cantidad;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Venta getVenta() {
		return venta;
	}
	public void setVenta(Venta venta) {
		this.venta = venta;
	}
	public int getFila() {
		return fila;
	}
	public void setFila(int fila) {
		this.fila = fila;
	}
	public ItemVenta getItem() {
		return item;
	}
	public void setItem(ItemVenta item) {
		this.item = item;
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
	
}
