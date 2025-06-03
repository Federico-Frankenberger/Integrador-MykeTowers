package org.example.service;

import java.util.List;

public interface GenericService <T> {
    int crear(T t) throws Exception;
    T leer(int id) throws Exception;
    boolean actualizar(T t) throws Exception;
    boolean eliminar(int id) throws Exception;
    List<T> listar()throws Exception;
    boolean existe(T t) throws Exception;
}
