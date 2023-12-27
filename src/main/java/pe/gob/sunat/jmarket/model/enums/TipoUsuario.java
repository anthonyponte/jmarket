package pe.gob.sunat.jmarket.model.enums;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public enum TipoUsuario {
  ADMINISTRADOR(0, "ADMINISTRADOR"),
  USUARIO(1, "USUARIO"),
  CLIENTE(2, "CLIENTE");

  private final SimpleObjectProperty<Integer> codigo;
  private final SimpleStringProperty descripcion;

  private TipoUsuario(int codigo, String descripcion) {
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
