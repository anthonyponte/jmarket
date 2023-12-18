/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pe.gob.sunat.jmarket.model;

/**
 * @author anthonyponte
 */
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

  private final int id;
  private final String codigo;
  private final String descripcion;

  private UnidadMedida(int id, String codigo, String descripcion) {
    this.id = id;
    this.codigo = codigo;
    this.descripcion = descripcion;
  }

  public int getId() {
    return id;
  }

  public String getCodigo() {
    return codigo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  @Override
  public String toString() {
    return descripcion;
  }
}
