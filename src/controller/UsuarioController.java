package controller;

import java.util.List;

import dao.interfaces.UsuarioDAO;
import model.Usuario;

public class UsuarioController {
	private static UsuarioController instance;

	private Usuario usuario;
	private UsuarioDAO usuarioDAO;

	private UsuarioController() {
	}

	public static UsuarioController getInstance() {
		if (instance == null) {
			instance = new UsuarioController();
		}
		return instance;
	}

	public void iniciaDadosUsuario(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public void setLoginUsuario(String login) {
		this.usuario.setLogin(login);
	}

	public String getLoginUsuario() {
		return this.usuario.getLogin();
	}

	public Usuario realizaLoginUsuario(Usuario usuario) {
		String strPass = new String(usuario.getSenha()).trim();

		this.usuario = this.usuarioDAO.getUsuarioByLogin(usuario.getLogin(), strPass);

		return this.usuario;
	}

	public List<Usuario> getAllUsuarios() {
		return this.usuarioDAO.getAllUsuarios();
	}

	public Usuario getUsuarioLogado() {
		return this.usuario;
	}

	public void salvaUsuario(Usuario usuario) {
		this.usuarioDAO.salvaUsuario(usuario);
	}

	public Usuario editaUsuario(Usuario usuario) {
		return this.usuarioDAO.editaUsuario(usuario);
	}

	public void excluiUsuario(int usuarioId) {
		this.usuarioDAO.excluiUsuario(usuarioId);
	}
}