package pe.gob.sunat.jmarket.model.enums;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public enum UnidadMedida {
  BALDE(0, "BJ", "BALDE"),
  BOLSA(1, "BG", "BOLSA"),
  BOTELLAS(2, "BO", "BOTELLAS"),
  CAJA(3, "BX", "CAJA"),
  CARTONES(4, "CT", "CARTONES"),
  CILINDRO(5, "CY", "CILINDRO"),
  CONOS(6, "CJ", "CONOS"),
  GRAMO(7, "GRM", "GRAMO"),
  JUEGO(8, "SET", "JUEGO"),
  KILOGRAMO(9, "KGM", "KILOGRAMO"),
  KILOMETRO(10, "KTM", "KILOMETRO"),
  KIT(11, "KT", "KIT"),
  LATAS(12, "CA", "LATAS"),
  LIBRAS(13, "LBR", "LIBRAS"),
  LITRO(14, "LTR", "LITRO"),
  METRO(15, "MTR", "METRO"),
  MILLARES(16, "MLL", "MILLARES"),
  MILLON_UNIDADES(17, "UM", "MILLON DE UNIDADES"),
  UNIDAD_BIENES(18, "NIU", "UNIDAD (BIENES)"),
  UNIDAD_SERVICIOS(19, "ZZ", "UNIDAD (SERVICIOS)");

  private final SimpleObjectProperty<Integer> id;
  private final SimpleStringProperty codigo;
  private final SimpleStringProperty descripcion;

  private UnidadMedida(int id, String codigo, String descripcion) {
    this.id = new SimpleObjectProperty<>(id);
    this.codigo = new SimpleStringProperty(codigo);
    this.descripcion = new SimpleStringProperty(descripcion);
  }

  public int getId() {
    return id.get();
  }

  public String getCodigo() {
    return codigo.get();
  }

  public String getDescripcion() {
    return descripcion.get();
  }

  public SimpleObjectProperty<Integer> getIdProperty() {
    return id;
  }

  public SimpleStringProperty getCodigoProperty() {
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
