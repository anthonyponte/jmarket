package pe.gob.sunat.jmarket.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Persona {
  private SimpleObjectProperty<Long> id;
  private SimpleObjectProperty<Integer> tipoDocumento;
  private SimpleStringProperty numeroDocumento;
  private SimpleStringProperty primerNombre;
  private SimpleStringProperty segundoNombre;
  private SimpleStringProperty apellidoPaterno;
  private SimpleStringProperty apellidoMaterno;
  private SimpleObjectProperty<Integer> estado;

  public Persona(
      Long id,
      int tipoDocumento,
      String numeroDocumento,
      String primerNombre,
      String segundoNombre,
      String apellidoPaterno,
      String apellidoMaterno,
      int estado) {
    this.id = new SimpleObjectProperty<>(id);
    this.tipoDocumento = new SimpleObjectProperty<>(tipoDocumento);
    this.numeroDocumento = new SimpleStringProperty(numeroDocumento);
    this.primerNombre = new SimpleStringProperty(primerNombre);
    this.segundoNombre = new SimpleStringProperty(segundoNombre);
    this.apellidoPaterno = new SimpleStringProperty(apellidoPaterno);
    this.apellidoMaterno = new SimpleStringProperty(apellidoMaterno);
    this.estado = new SimpleObjectProperty<>(estado);
  }

  public Persona(
      int tipoDocumento,
      String numeroDocumento,
      String primerNombre,
      String segundoNombre,
      String apellidoPaterno,
      String apellidoMaterno,
      int estado) {
    this.tipoDocumento = new SimpleObjectProperty<>(tipoDocumento);
    this.numeroDocumento = new SimpleStringProperty(numeroDocumento);
    this.primerNombre = new SimpleStringProperty(primerNombre);
    this.segundoNombre = new SimpleStringProperty(segundoNombre);
    this.apellidoPaterno = new SimpleStringProperty(apellidoPaterno);
    this.apellidoMaterno = new SimpleStringProperty(apellidoMaterno);
    this.estado = new SimpleObjectProperty<>(estado);
  }

  public Long getId() {
    return id.get();
  }

  public void setId(Long id) {
    this.id.set(id);
  }

  public int getTipoDocumento() {
    return tipoDocumento.get();
  }

  public void setTipoDocumento(int tipoDocumento) {
    this.tipoDocumento.set(tipoDocumento);
  }

  public String getNumeroDocumento() {
    return numeroDocumento.get();
  }

  public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento.set(numeroDocumento);
  }

  public String getPrimerNombre() {
    return primerNombre.get();
  }

  public void setPrimerNombre(String primerNombre) {
    this.primerNombre.set(primerNombre);
  }

  public String getSegundoNombre() {
    return segundoNombre.get();
  }

  public void setSegundoNombre(String segundoNombre) {
    this.segundoNombre.set(segundoNombre);
  }

  public String getApellidoPaterno() {
    return apellidoPaterno.get();
  }

  public void setApellidoPaterno(String apellidoPaterno) {
    this.apellidoPaterno.set(apellidoPaterno);
  }

  public String getApellidoMaterno() {
    return apellidoMaterno.get();
  }

  public void setApellidoMaterno(String apellidoMaterno) {
    this.apellidoMaterno.set(apellidoMaterno);
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

  public SimpleObjectProperty<Integer> getTipoDocumentoProperty() {
    return tipoDocumento;
  }

  public SimpleStringProperty getNumeroDocumentoProperty() {
    return numeroDocumento;
  }

  public SimpleStringProperty getPrimerNombreProperty() {
    return primerNombre;
  }

  public SimpleStringProperty getSegundoNombreProperty() {
    return segundoNombre;
  }

  public SimpleStringProperty getApellidoPaternoProperty() {
    return apellidoPaterno;
  }

  public SimpleStringProperty getApellidoMaternoProperty() {
    return apellidoMaterno;
  }

  public SimpleObjectProperty<Integer> getEstadoProperty() {
    return estado;
  }

  public String getNombreCompleto() {
    return primerNombre.get()
        + " "
        + segundoNombre.get()
        + " "
        + apellidoPaterno.get()
        + " "
        + apellidoMaterno.get();
  }

  @Override
  public String toString() {
    return "Persona{"
        + "id="
        + id
        + ", tipoDocumento="
        + tipoDocumento
        + ", numeroDocumento="
        + numeroDocumento
        + ", primerNombre="
        + primerNombre
        + ", segundoNombre="
        + segundoNombre
        + ", apellidoPaterno="
        + apellidoPaterno
        + ", apellidoMaterno="
        + apellidoMaterno
        + ", estado="
        + estado
        + '}';
  }
}
