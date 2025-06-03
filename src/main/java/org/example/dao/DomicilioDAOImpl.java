package org.example.dao;

import org.example.config.DatabaseConnection;
import org.example.model.Domicilio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DomicilioDAOImpl implements GenericDAO<Domicilio> {

    @Override
    public int crear(Domicilio domicilio) throws SQLException {
        String sql = "INSERT INTO domicilios (calle, numero,ciudad,provincia,pais,codigo_postal) VALUES (?,?,?,?,?,?)";
        try(Connection conn = DatabaseConnection.obtenerConexion();
            PreparedStatement stmt = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, domicilio.getCalle());
            stmt.setString(2, domicilio.getNumero());
            stmt.setString(3, domicilio.getCiudad());
            stmt.setString(4, domicilio.getProvincia());
            stmt.setString(5, domicilio.getPais());
            stmt.setString(6, domicilio.getCodigoPostal());
            int filasAfectadas = stmt.executeUpdate();
            if(filasAfectadas > 0){
                try(ResultSet rs = stmt.getGeneratedKeys()) {
                    if(rs.next()){
                        int idDomicilio = rs.getInt(1);
                        return idDomicilio;
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public Domicilio leer(int id) throws SQLException {
        String sql ="SELECT * FROM domicilios WHERE id_domicilio = ?";

        try(Connection conn = DatabaseConnection.obtenerConexion();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    return Domicilio.builder()
                            .id(rs.getInt("id_domicilio"))
                            .calle(rs.getString("calle"))
                            .numero(rs.getString("numero"))
                            .ciudad(rs.getString("ciudad"))
                            .provincia(rs.getString("provincia"))
                            .pais(rs.getString("pais"))
                            .codigoPostal(rs.getString("codigo_postal"))
                            .build();
                }
            }
        }
        return null;
    }

    @Override
    public boolean actualizar(Domicilio domicilio) throws SQLException {
        String sql = "UPDATE domicilios SET calle = ?,numero = ?,ciudad = ?,provincia = ?,codigo_postal= ?,pais = ?  WHERE id_domicilio = ?";
        try(Connection conn = DatabaseConnection.obtenerConexion();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, domicilio.getCalle());
            stmt.setString(2, domicilio.getNumero());
            stmt.setString(3, domicilio.getCiudad());
            stmt.setString(4, domicilio.getProvincia());
            stmt.setString(5, domicilio.getCodigoPostal());
            stmt.setString(6, domicilio.getPais());
            stmt.setInt(7, domicilio.getId());

            int filasAfectadas = stmt.executeUpdate();

            return filasAfectadas > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM domicilios WHERE id_domicilio = ?";

        try(Connection conn = DatabaseConnection.obtenerConexion();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    @Override
    public boolean existe(Domicilio domicilio) throws SQLException {

        String sql = "SELECT 1 FROM domicilios WHERE calle = ? AND numero = ? AND ciudad = ? AND provincia = ? AND pais = ? AND codigo_postal = ?";
        try (Connection conn = DatabaseConnection.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, domicilio.getCalle());
            stmt.setString(2, domicilio.getNumero());
            stmt.setString(3, domicilio.getCiudad());
            stmt.setString(4, domicilio.getProvincia());
            stmt.setString(5, domicilio.getPais());
            stmt.setString(6, domicilio.getCodigoPostal());

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public List<Domicilio> listar() throws SQLException {
        String sql = "SELECT * FROM domicilios";

        try(Connection conn = DatabaseConnection.obtenerConexion();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){

            List<Domicilio> domicilios = new ArrayList<>();
            while(rs.next()){
                Domicilio domicilio = Domicilio.builder()
                        .id(rs.getInt("id_domicilio"))
                        .calle(rs.getString("calle"))
                        .numero(rs.getString("numero"))
                        .ciudad(rs.getString("ciudad"))
                        .provincia(rs.getString("provincia"))
                        .pais(rs.getString("pais"))
                        .codigoPostal(rs.getString("codigo_postal"))
                        .build();
                domicilios.add(domicilio);
            }
            return domicilios;
        }
    }

    public Integer obtenerId(Domicilio domicilio) throws SQLException {
        String sql = "SELECT id_domicilio FROM domicilios WHERE calle = ? AND numero = ? AND ciudad = ? AND provincia = ? AND codigo_postal = ? AND pais = ?";

        try (Connection conn = DatabaseConnection.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, domicilio.getCalle());
            stmt.setString(2, domicilio.getNumero());
            stmt.setString(3, domicilio.getCiudad());
            stmt.setString(4, domicilio.getProvincia());
            stmt.setString(5, domicilio.getCodigoPostal());
            stmt.setString(6, domicilio.getPais());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_domicilio");
                } else {
                    return null;
                }
            }
        }
    }
}
