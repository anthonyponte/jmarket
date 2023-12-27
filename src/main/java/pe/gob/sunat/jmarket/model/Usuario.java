package pe.gob.sunat.jmarket.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Usuario {
  private SimpleObjectProperty<Long> id;
  private SimpleObjectProperty<Integer> tipoUsuario;
  private SimpleStringProperty nombreUsuario;
  private SimpleStringProperty contrasena;
  private SimpleObjectProperty<Integer> estado;
  private Persona persona;

  public Usuario(
      Long id,
      int tipoUsuario,
      String nombreUsuario,
      String contrasena,
      int estado,
      Persona persona) {
    this.id = new SimpleObjectProperty<>(id);
    this.tipoUsuario = new SimpleObjectProperty<>(tipoUsuario);
    this.nombreUsuario = new SimpleStringProperty(nombreUsuario);
    this.contrasena = new SimpleStringProperty(contrasena);
    this.estado = new SimpleObjectProperty<>(estado);
    this.persona = persona;
  }

  public Usuario(
      int tipoUsuario, String nombreUsuario, String contrasena, int estado, Persona persona) {
    this.tipoUsuario = new SimpleObjectProperty<>(tipoUsuario);
    this.nombreUsuario = new SimpleStringProperty(nombreUsuario);
    this.contrasena = new SimpleStringProperty(contrasena);
    this.estado = new SimpleObjectProperty<>(estado);
    this.persona = persona;
  }

  public Long getId() {
    return id.get();
  }

  public void setId(Long id) {
    this.id.set(id);
  }

  public int getTipoUsuario() {
    return tipoUsuario.get();
  }

  public void setTipoUsuario(int tipoUsuario) {
    this.tipoUsuario.set(tipoUsuario);
  }

  public String getNombreUsuario() {
    return nombreUsuario.get();
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario.set(nombreUsuario);
  }

  public String getContrasena() {
    return contrasena.get();
  }

  public void setContrasena(String contrasena) {
    this.contrasena.set(contrasena);
  }

  public int getEstado() {
    return estado.get();
  }

  public void setEstado(int estado) {
    this.estado.set(estado);
  }

  public Persona getPersona() {
    return persona;
  }

  public void setPersona(Persona persona) {
    this.persona = persona;
  }

  public SimpleObjectProperty<Long> getIdProperty() {
    return id;
  }

  public SimpleObjectProperty<Integer> getTipoUsuarioProperty() {
    return tipoUsuario;
  }

  public SimpleStringProperty getNombreUsuarioProperty() {
    return nombreUsuario;
  }

  public SimpleStringProperty getContrasenaProperty() {
    return contrasena;
  }

  public SimpleObjectProperty<Integer> getEstadoProperty() {
    return estado;
  }

  @Override
  public String toString() {
    return "Usuario{"
        + "id="
        + id
        + ", tipoUsuario="
        + tipoUsuario
        + ", nombreUsuario="
        + nombreUsuario
        + ", contrasena="
        + contrasena
        + ", estado="
        + estado
        + ", persona="
        + persona
        + '}';
  }
}
