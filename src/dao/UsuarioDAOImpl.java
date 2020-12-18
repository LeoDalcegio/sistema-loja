package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.UsuarioDAO;
import enums.TipoUsuario;
import model.Usuario;

public class UsuarioDAOImpl extends BDGenericoDAO implements UsuarioDAO {
	private Connection connection = null;

	public UsuarioDAOImpl() {
	}

	@Override
	public List<Usuario> getAllUsuarios() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Usuario> lstUsuarios = new ArrayList<Usuario>();

		try {
			this.connection = getConnection("sistema-loja");

			String sql = "SELECT * " + "FROM Usuario";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("Id"));
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

	public Usuario getUsuarioById(int usuarioId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			this.connection = getConnection("sistema-loja");

			String sql = "SELECT * " + "FROM Usuario WHERE Id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, usuarioId);
			rs = pstmt.executeQuery();

			Usuario usuario = new Usuario();

			if (rs.next()) {
				usuario.setId(rs.getInt("Id"));
				usuario.setLogin(rs.getString("Login"));

				if (rs.getInt("Tipo") == TipoUsuario.Administrador.getValue()) {
					usuario.setTipo(TipoUsuario.Administrador);
				} else {
					usuario.setTipo(TipoUsuario.Funcionario);
				}
			}

			return usuario;

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
			this.connection = getConnection("sistema-loja");

			String strPass = new String(usuario.getSenha()).trim();

			String sql = "INSERT INTO Usuario " + "(Login, Senha, Tipo)" + "VALUES(?, ?, ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, usuario.getLogin());
			pstmt.setString(2, strPass);
			pstmt.setInt(3, usuario.getTipo().getValue());
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
			this.connection = getConnection("sistema-loja");

			String strPass = new String(usuarioObjeto.getSenha()).trim();

			String sql = "UPDATE Usuario SET Login = ?, Senha = ?, Tipo = ? WHERE Id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, usuarioObjeto.getLogin());
			pstmt.setString(2, strPass);
			pstmt.setInt(3, usuarioObjeto.getTipo().getValue());
			pstmt.setInt(4, usuarioObjeto.getId());
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
			this.connection = getConnection("sistema-loja");

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
			this.connection = getConnection("sistema-loja");

			String sql = "DELETE FROM Usuario WHERE Id = ?";
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
