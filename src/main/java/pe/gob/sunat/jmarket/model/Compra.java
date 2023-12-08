/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pe.gob.sunat.jmarket.model;

import java.util.List;

/**
 * @author Anthony Ponte
 */
public class Compra {
  private Long id;
  private Cliente cliente;
  private List<CompraDetalle> compraDetalles;
  private int estado;

  public Compra() {}

  public Compra(Long id, Cliente cliente, List<CompraDetalle> compraDetalles, int estado) {
    this.id = id;
    this.cliente = cliente;
    this.compraDetalles = compraDetalles;
    this.estado = estado;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public List<CompraDetalle> getCompraDetalles() {
    return compraDetalles;
  }

  public void setCompraDetalles(List<CompraDetalle> compraDetalles) {
    this.compraDetalles = compraDetalles;
  }

  public int getEstado() {
    return estado;
  }

  public void setEstado(int estado) {
    this.estado = estado;
  }

  @Override
  public String toString() {
    return "Compra{"
        + "id="
        + id
        + ", cliente="
        + cliente
        + ", compraDetalles="
        + compraDetalles
        + ", estado="
        + estado
        + '}';
  }
}
