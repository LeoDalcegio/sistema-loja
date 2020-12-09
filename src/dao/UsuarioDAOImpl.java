package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.UsuarioDAO;
import enums.TipoUsuario;
import model.Usuario;

public class UsuarioDAOImpl extends BDGenericoDAO implements UsuarioDAO {
	private Connection connection = null;

	public UsuarioDAOImpl() throws SQLException, ClassNotFoundException {
		this.connection = getConnection("sistema-loja");
	}

	@Override
	public List<Usuario> getAllUsuarios() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Usuario> lstUsuarios = new ArrayList<Usuario>();

		try {
			String sql = "SELECT * " + "FROM Usuario";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setLogin(rs.getString("Login"));

				if (rs.getInt("Tipo") == TipoUsuario.Administrador.getValue()) {
					usuario.setTipo(TipoUsuario.Administrador);
				} else {
					usuario.setTipo(TipoUsuario.Funcionario);
				}

				// usuario.setTipo(TipoUsuario.valueOf(rs.getString("Tipo").toLowerCase().trim()));
				lstUsuarios.add(usuario);
			}

			return lstUsuarios;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		} finally {
			close(rs);

			close(connection);
		}
	}

	@Override
	public void salvaUsuario(Usuario usuario) {
		PreparedStatement pstmt = null;
		try {

			String strPass = new String(usuario.getSenha()).trim();

			String sql = "INSERT INTO Usuario " + "(Login, Senha, Tipo)" + "VALUES(?, ?, ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, usuario.getLogin());
			pstmt.setString(2, strPass);
			pstmt.setString(3, usuario.getTipo().toString().toLowerCase().trim());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}
	}

	@Override
	public Usuario editaUsuario(Usuario usuarioObjeto) {
		if (usuarioObjeto.getId() == 0) {
			throw new IllegalArgumentException("Id informado inválido");
		}

		PreparedStatement pstmt = null;

		try {
			String strPass = new String(usuarioObjeto.getSenha()).trim();

			String sql = "UPDATE Usario SET Login = ?, Senha = ?, Tipo = ? WHERE Id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, usuarioObjeto.getLogin());
			pstmt.setString(2, strPass);
			pstmt.setString(3, usuarioObjeto.getTipo().toString().toLowerCase().trim());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}

		return usuarioObjeto;
	}

	@Override
	public Usuario getUsuarioByLogin(String login, String senha) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * " + "FROM Usuario " + "WHERE Login = ? AND Senha = ? ";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, login);
			pstmt.setString(2, senha);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("Id"));
				usuario.setLogin(rs.getString("Login"));

				if (rs.getInt("Tipo") == TipoUsuario.Administrador.getValue()) {
					usuario.setTipo(TipoUsuario.Administrador);
				} else {
					usuario.setTipo(TipoUsuario.Funcionario);
				}

				return usuario;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			close(rs);
			close(connection);
		}
	}

	@Override
	public void excluiUsuario(int usuarioId) {
		if (usuarioId == 0) {
			throw new IllegalArgumentException("Id informado inválido");
		}
		PreparedStatement pstmt = null;

		try {
			String sql = "DELETE FROM Usuaro WHERE Id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, usuarioId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}
	}
}
