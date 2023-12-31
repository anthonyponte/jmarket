package pe.gob.sunat.jmarket.model.enums;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public enum Estado {
  INACTIVO(0, "INACTIVO"),
  ACTIVO(1, "ACTIVO"),
  ANULADO(2, "ANULADO");

  private final SimpleObjectProperty<Integer> codigo;
  private final SimpleStringProperty descripcion;

  private Estado(int codigo, String descripcion) {
    this.codigo = new SimpleObjectProperty<>(codigo);
    this.descripcion = new SimpleStringProperty(descripcion);
  }

  public int getCodigo() {
    return codigo.get();
  }

  public String getDescripcion() {
    return descripcion.get();
  }

  public SimpleObjectProperty<Integer> getCodigoProperty() {
    return codigo;
  }

  public SimpleStringProperty getDescripcionProperty() {
    return descripcion;
  }

  @Override
  public String toString() {
    return descripcion.get();
  }
}
