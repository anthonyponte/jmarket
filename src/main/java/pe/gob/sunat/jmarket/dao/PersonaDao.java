package pe.gob.sunat.jmarket.dao;

import pe.gob.sunat.jmarket.model.Persona;

public interface PersonaDao extends ObjectDao<Persona> {
  Persona read(String numeroDocumento);
}
