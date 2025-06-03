package org.example.service;

import org.example.dao.PersonaDAOImpl;
import org.example.model.Domicilio;
import org.example.model.Persona;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PersonaServiceImpl implements GenericService<Persona> {
    private final PersonaDAOImpl personaDAO;
    private final DomicilioServiceImpl domicilioService;

    public PersonaServiceImpl(PersonaDAOImpl personaDAO, DomicilioServiceImpl domicilioService) {
        this.personaDAO = personaDAO;
        this.domicilioService = domicilioService;
    }


    @Override
    public int crear(Persona persona) throws Exception {
        validarPersona(persona);

        Domicilio domicilio = persona.getDomicilio();

        domicilioService.validarDomicilio(domicilio);

        if (domicilio.getId() == null || domicilio.getId() <= 0) {
            if (!domicilioService.existe(domicilio)) {
                int idDomicilio = domicilioService.crear(domicilio);
                domicilio.setId(idDomicilio);
            } else {
                Integer idExistente = domicilioService.obtenerId(domicilio);
                if (idExistente == null) {
                    throw new Exception("El domicilio ya existe pero no se pudo recuperar el ID.");
                }
                domicilio.setId(idExistente);
            }
        }

        if (this.existe(persona)) {
            throw new Exception("La persona ya existe en la base de datos.");
        }

        int idGenerado = personaDAO.crear(persona);
        if (idGenerado == -1) {
            throw new Exception("No se pudo generar el ID de la persona.");
        }

        return idGenerado;
    }

    @Override
    public Persona leer(int id) throws Exception {

        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }

        Persona persona = personaDAO.leer(id);

        if (persona == null) {
            throw new Exception("Persona no encontrada");
        }

        return persona;
    }

    @Override
    public boolean actualizar(Persona persona) throws Exception {

        validarPersona(persona);

        Domicilio domicilio = persona.getDomicilio();

        domicilioService.validarDomicilio(domicilio);


        if (domicilio.getId() == null || domicilio.getId() <= 0) {
            if (!domicilioService.existe(domicilio)) {
                int idDomicilio = domicilioService.crear(domicilio);
                domicilio.setId(idDomicilio);
            } else {
                Integer idExistente = domicilioService.obtenerId(domicilio);
                if (idExistente == null) {
                    throw new Exception("El domicilio ya existe pero no se pudo recuperar el ID.");
                }
                domicilio.setId(idExistente);
            }
        }else {

            Domicilio domicilioExistente = domicilioService.leer(domicilio.getId());

            if (!domicilio.equals(domicilioExistente)) {
                domicilioService.actualizar(domicilio);
            }
        }

        Persona personaExistente = personaDAO.leer(persona.getId());
        if (personaExistente == null) {
            throw new Exception("No se puede actualizar una persona que no existe.");
        }

        return personaDAO.actualizar(persona);
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }

        Persona personaExistente = personaDAO.leer(id);

        if (personaExistente == null) {
            throw new Exception("No se puede eliminar una persona que no existe.");
        }

        boolean eliminadoPersona = personaDAO.eliminar(id);
        if (!eliminadoPersona) {
            return false;
        }

        Domicilio domicilio = personaExistente.getDomicilio();

        if (domicilio.getId() != null) {
            int cantidadPersonas = personaDAO.contarPorDomicilio(domicilio.getId());
            if (cantidadPersonas == 0) {
                domicilioService.eliminar(domicilio.getId());
            }
        }

        return true;
    }

    @Override
    public List<Persona> listar() throws Exception {
        List<Persona> personas = personaDAO.listar();

        if (personas.isEmpty()) {
            throw new Exception("No se encontraron personas en la base de datos.");
        }
        return personas;
    }

    @Override
    public boolean existe(Persona persona) throws SQLException {
        if (persona == null || persona.getDomicilio() == null || persona.getDomicilio().getId() == null) {
            return false;
        }

        return personaDAO.existe(persona);
    }

    private void validarPersona(Persona persona) {
        if (persona == null) {
            throw new IllegalArgumentException("La persona no puede ser nula.");
        }

        if (persona.getNombre() == null || persona.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        if (persona.getNombre().matches("\\d+")) {
            throw new IllegalArgumentException("Uno o más campos contienen solo números, pero deberían ser texto.");
        }

        if (persona.getApellido() == null || persona.getApellido().isBlank()) {
            throw new IllegalArgumentException("El apellido es obligatorio.");
        }

        if (persona.getApellido().matches("\\d+")) {
            throw new IllegalArgumentException("Uno o más campos contienen solo números, pero deberían ser texto.");
        }

        if (persona.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria.");
        }

        if (persona.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura.");
        }

        if (persona.getDomicilio() == null) {
            throw new IllegalArgumentException("La persona debe tener un domicilio.");
        }
    }
}
