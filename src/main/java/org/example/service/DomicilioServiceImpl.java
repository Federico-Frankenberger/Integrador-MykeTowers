package org.example.service;

import org.example.dao.DomicilioDAOImpl;
import org.example.model.Domicilio;

import java.util.List;

public class DomicilioServiceImpl implements GenericService<Domicilio> {
    private final DomicilioDAOImpl domicilioDAO;

    public DomicilioServiceImpl(DomicilioDAOImpl domicilioDAO) {
        this.domicilioDAO = domicilioDAO;
    }

    @Override
    public int crear(Domicilio domicilio) throws Exception {

        validarDomicilio(domicilio);

        if (domicilioDAO.existe(domicilio)) {
            throw new Exception("El domicilio ya existe");
        }

        return domicilioDAO.crear(domicilio);
    }

    @Override
    public Domicilio leer(int id) throws Exception {

        if(id <= 0) throw new IllegalArgumentException("ID inválido");

        Domicilio domicilio = domicilioDAO.leer(id);
        if(domicilio == null) {
            throw new Exception("Domicilio no encontrado");
        }

        return domicilio;
    }

    @Override
    public boolean actualizar(Domicilio domicilio) throws Exception {

        if(domicilio == null || domicilio.getId() == null || domicilio.getId() <= 0) {
            throw new IllegalArgumentException("Domicilio o ID inválido");
        }

        validarDomicilio(domicilio);

        if(domicilioDAO.leer(domicilio.getId()) == null) {
            throw new Exception("No se puede actualizar un domicilio que no existe");
        }
        return domicilioDAO.actualizar(domicilio);
    }

    @Override
    public boolean eliminar(int id) throws Exception {
        if(id <= 0) throw new IllegalArgumentException("ID inválido");

        if(domicilioDAO.leer(id) == null) {
            throw new Exception("No se puede eliminar un domicilio que no existe");
        }
        return domicilioDAO.eliminar(id);
    }

    @Override
    public List<Domicilio> listar() throws Exception {
        return domicilioDAO.listar();
    }

    @Override
    public boolean existe(Domicilio domicilio) throws Exception {
        validarDomicilio(domicilio);
        return domicilioDAO.existe(domicilio);
    }

    public Integer obtenerId(Domicilio domicilio)throws Exception{
        validarDomicilio(domicilio);
        return domicilioDAO.obtenerId(domicilio);
    }

    public void validarDomicilio(Domicilio domicilio) throws IllegalArgumentException {
        if (domicilio == null) {
            throw new IllegalArgumentException("Domicilio no puede ser nulo");
        }

        if (domicilio.getCalle() == null || domicilio.getCalle().isBlank()) {
            throw new IllegalArgumentException("La calle es obligatoria");
        }

        if (domicilio.getCalle().matches("\\d+")) {
            throw new IllegalArgumentException("Uno o más campos contienen solo números, pero deberían ser texto.");
        }

        if (domicilio.getNumero() == null || domicilio.getNumero().isBlank()) {
            throw new IllegalArgumentException("El número es obligatorio");
        }

        try {
            int numero = Integer.parseInt(domicilio.getNumero());
            if (numero < 1) {
                throw new IllegalArgumentException("El número debe ser mayor o igual a 1");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El número debe ser un valor numérico válido");
        }

        if (domicilio.getCiudad() == null || domicilio.getCiudad().isBlank()) {
            throw new IllegalArgumentException("La ciudad es obligatoria");
        }

        if (domicilio.getCiudad().matches("\\d+")) {
            throw new IllegalArgumentException("Uno o más campos contienen solo números, pero deberían ser texto.");
        }

        if (domicilio.getProvincia() == null || domicilio.getProvincia().isBlank()) {
            throw new IllegalArgumentException("La provincia es obligatoria");
        }

        if (domicilio.getProvincia().matches("\\d+")) {
            throw new IllegalArgumentException("Uno o más campos contienen solo números, pero deberían ser texto.");
        }

        if (domicilio.getPais() == null || domicilio.getPais().isBlank()) {
            throw new IllegalArgumentException("El país es obligatorio");
        }

        if (domicilio.getPais().matches("\\d+")) {
            throw new IllegalArgumentException("Uno o más campos contienen solo números, pero deberían ser texto.");
        }

        if (domicilio.getCodigoPostal() == null || domicilio.getCodigoPostal().isBlank()) {
            throw new IllegalArgumentException("El código postal es obligatorio");
        }

        try {
            int codigoPostal = Integer.parseInt(domicilio.getCodigoPostal());
            if (codigoPostal < 1) {
                throw new IllegalArgumentException("El código postal debe ser mayor o igual a 1");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El código postal debe ser un valor numérico válido");
        }
    }
}
