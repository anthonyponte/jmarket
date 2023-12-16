package pe.gob.sunat.jmarket.model;

import java.time.LocalDate;
import java.util.List;

public class Venta {
	private Long id;
	private Long numero;
	private String clienteNombre;
	private LocalDate fecEmision;	
	private List<VentaDetalle> detalle;
	
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
	public String getClienteNombre() {
		return clienteNombre;
	}
	public void setClienteNombre(String clienteNombre) {
		this.clienteNombre = clienteNombre;
	}
	public LocalDate getFecEmision() {
		return fecEmision;
	}
	public void setFecEmision(LocalDate fecEmision) {
		this.fecEmision = fecEmision;
	}
	public List<VentaDetalle> getDetalle() {
		return detalle;
	}
	public void setDetalle(List<VentaDetalle> detalle) {
		this.detalle = detalle;
	}
	
	
}
