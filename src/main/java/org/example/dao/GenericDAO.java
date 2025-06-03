package org.example.dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO <T> {
    int crear(T t) throws SQLException;
    T leer(int id) throws SQLException;
    boolean actualizar(T t) throws SQLException;
    boolean eliminar(int id) throws SQLException;
    boolean existe(T t) throws SQLException;
    List<T> listar()throws SQLException;
}
