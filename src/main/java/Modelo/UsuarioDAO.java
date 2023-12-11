
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UsuarioDAO {
    
    private Conexion conectar = new Conexion();
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public List<Usuario> obtenerUsuarios() {
        List<Usuario> datos = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setCodigo(rs.getInt("cod_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setTelefono(rs.getInt("num_telefono"));
                u.setEmail(rs.getString("email"));
                u.setNombreusuario(rs.getString("nom_usuario"));
                u.setContraseña(rs.getString("contraseña"));
                datos.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Manejo básico de excepciones. Debes mejorarlo según tus necesidades.
        } finally {
            cerrarConexion(); // Método para cerrar la conexión. Debes implementarlo según tu lógica.
        }

        return datos;
    }

    // Método para cerrar la conexión y otros recursos
    private void cerrarConexion() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Puedes mejorar el manejo de excepciones según tus necesidades.
        }
    }

    public int agregar(Usuario u) {
        String sql = "INSERT INTO usuario(cod_usuario, nombre, apellido, num_telefono, email, nom_usuario, contraseña) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = conectar.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, u.getCodigo());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellido());
            ps.setInt(4, u.getTelefono());
            ps.setString(5, u.getEmail());
            ps.setString(6, u.getNombreusuario());
            ps.setString(7, u.getContraseña());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return 1;
    }
    public boolean autenticarUsuario(String nombreUsuario, String contrasena) {
        String sql = "SELECT * FROM usuario WHERE nom_usuario = ? AND contraseña = ?";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasena);
            rs = ps.executeQuery();
            return rs.next(); // Devuelve true si hay coincidencia, false si no
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrarConexion();
        }
    }
}
