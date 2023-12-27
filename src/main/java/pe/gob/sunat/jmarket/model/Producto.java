package pe.gob.sunat.jmarket.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Producto {
  private SimpleObjectProperty<Long> id;
  private SimpleStringProperty codigo;
  private SimpleStringProperty descripcion;
  private SimpleObjectProperty<Integer> unidadMedida;
  private SimpleObjectProperty<Double> precioUnitario;
  private SimpleObjectProperty<Integer> estado;

  public Producto(
      Long id,
      String codigo,
      String descripcion,
      int unidadMedida,
      double precioUnitario,
      int estado) {
    this.id = new SimpleObjectProperty<>(id);
    this.codigo = new SimpleStringProperty(codigo);
    this.descripcion = new SimpleStringProperty(descripcion);
    this.unidadMedida = new SimpleObjectProperty<>(unidadMedida);
    this.precioUnitario = new SimpleObjectProperty<>(precioUnitario);
    this.estado = new SimpleObjectProperty<>(estado);
  }

  public Producto(
      String codigo, String descripcion, int unidadMedida, double precioUnitario, int estado) {
    this.codigo = new SimpleStringProperty(codigo);
    this.descripcion = new SimpleStringProperty(descripcion);
    this.unidadMedida = new SimpleObjectProperty<>(unidadMedida);
    this.precioUnitario = new SimpleObjectProperty<>(precioUnitario);
    this.estado = new SimpleObjectProperty<>(estado);
  }

  public Long getId() {
    return id.get();
  }

  public void setId(Long id) {
    this.id.set(id);
  }

  public String getCodigo() {
    return codigo.get();
  }

  public void setCodigo(String codigo) {
    this.codigo.set(codigo);
  }

  public String getDescripcion() {
    return descripcion.get();
  }

  public void setDescripcion(String descripcion) {
    this.descripcion.set(descripcion);
  }

  public int getUnidadMedida() {
    return unidadMedida.get();
  }

  public void setUnidadMedida(int unidadMedida) {
    this.unidadMedida.set(unidadMedida);
  }

  public double getPrecioUnitario() {
    return precioUnitario.get();
  }

  public void setPrecioUnitario(double precioUnitario) {
    this.precioUnitario.set(precioUnitario);
  }

  public int getEstado() {
    return estado.get();
  }

  public void setEstado(int estado) {
    this.estado.set(estado);
  }

  public SimpleObjectProperty<Long> getIdProperty() {
    return id;
  }

  public SimpleStringProperty getCodigoProperty() {
    return codigo;
  }

  public SimpleStringProperty getDescripcionProperty() {
    return descripcion;
  }

  public SimpleObjectProperty<Integer> getUnidadMedidaProperty() {
    return unidadMedida;
  }

  public SimpleObjectProperty<Double> getPrecioUnitarioProperty() {
    return precioUnitario;
  }

  public SimpleObjectProperty<Integer> getEstadoProperty() {
    return estado;
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
