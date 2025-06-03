package org.example.dao;

import org.example.config.DatabaseConnection;
import org.example.model.Domicilio;
import org.example.model.Persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAOImpl implements GenericDAO<Persona>{

    @Override
    public int crear(Persona persona) throws SQLException {
        String sql = "INSERT INTO personas (nombre, apellido, fecha_nacimiento, id_domicilio) VALUES (?,?,?,?)";
        try (Connection conn = DatabaseConnection.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setDate(3, java.sql.Date.valueOf(persona.getFechaNacimiento()));
            stmt.setInt(4,persona.getDomicilio().getId());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    return -1;
                }
            }
        }
    }

    @Override
    public Persona leer(int id) throws SQLException {
        String sql = "SELECT p.id_persona, p.nombre, p.apellido, p.fecha_nacimiento, " +
                "d.id_domicilio, d.calle, d.numero, d.ciudad, d.provincia, d.codigo_postal, d.pais " +
                "FROM personas p " +
                "JOIN domicilios d ON p.id_domicilio = d.id_domicilio " +
                "WHERE p.id_persona = ?";

        try (Connection conn = DatabaseConnection.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Domicilio domicilio = Domicilio.builder()
                            .id(rs.getInt("id_domicilio"))
                            .calle(rs.getString("calle"))
                            .numero(rs.getString("numero"))
                            .ciudad(rs.getString("ciudad"))
                            .provincia(rs.getString("provincia"))
                            .pais(rs.getString("pais"))
                            .codigoPostal(rs.getString("codigo_postal"))
                            .build();

                    Persona persona = Persona.builder()
                            .id(rs.getInt("id_persona"))
                            .nombre(rs.getString("nombre"))
                            .apellido(rs.getString("apellido"))
                            .fechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate())
                            .domicilio(domicilio)
                            .build();

                    return persona;
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public boolean actualizar(Persona persona) throws SQLException {
        String sql = "UPDATE personas SET nombre = ?, apellido = ?, fecha_nacimiento = ?, id_domicilio = ? WHERE id_persona = ?";

        try (Connection conn = DatabaseConnection.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setDate(3, java.sql.Date.valueOf(persona.getFechaNacimiento()));
            stmt.setInt(4, persona.getDomicilio().getId());
            stmt.setInt(5, persona.getId());

            int filasActualizadas = stmt.executeUpdate();

            return filasActualizadas > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM personas WHERE id_persona = ?";

        try (Connection conn = DatabaseConnection.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int filasEliminadas = stmt.executeUpdate();

            return filasEliminadas > 0;
        }
    }

    @Override
    public boolean existe(Persona persona) throws SQLException {
        String sql = "SELECT 1 FROM personas WHERE nombre = ? AND apellido = ? AND fecha_nacimiento = ? AND id_domicilio = ?";

        try (Connection conn = DatabaseConnection.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setDate(3, java.sql.Date.valueOf(persona.getFechaNacimiento()));
            stmt.setInt(4, persona.getDomicilio().getId());

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public List<Persona> listar() throws SQLException {
        String sql = "SELECT p.id_persona, p.nombre, p.apellido, p.fecha_nacimiento, " +
                "d.id_domicilio, d.calle, d.numero, d.ciudad, d.provincia, d.codigo_postal, d.pais " +
                "FROM personas p " +
                "JOIN domicilios d ON p.id_domicilio = d.id_domicilio";

        List<Persona> personas = new ArrayList<>();

        try (Connection conn = DatabaseConnection.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Domicilio domicilio = Domicilio.builder()
                        .id(rs.getInt("id_domicilio"))
                        .calle(rs.getString("calle"))
                        .numero(rs.getString("numero"))
                        .ciudad(rs.getString("ciudad"))
                        .provincia(rs.getString("provincia"))
                        .codigoPostal(rs.getString("codigo_postal"))
                        .pais(rs.getString("pais"))
                        .build();

                Persona persona = Persona.builder()
                        .id(rs.getInt("id_persona"))
                        .nombre(rs.getString("nombre"))
                        .apellido(rs.getString("apellido"))
                        .fechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate())
                        .domicilio(domicilio)
                        .build();

                personas.add(persona);
            }
        }

        return personas;
    }

    public int contarPorDomicilio(int idDomicilio) throws SQLException {
        String sql = "SELECT COUNT(*) FROM personas WHERE id_domicilio = ?";
        try (Connection conn = DatabaseConnection.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDomicilio);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}
