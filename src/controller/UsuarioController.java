package controller;

import dao.interfaces.UsuarioDAO;
import model.Usuario;

public class UsuarioController {
	private static UsuarioController instance;

	private Usuario model;
	private UsuarioDAO usuarioDAO;

	private UsuarioController() {
	}

	public static UsuarioController getInstance() {
		if (instance == null) {
			instance = new UsuarioController();
		}
		return instance;
	}

	public void iniciaDadosUsuario(Usuario model, UsuarioDAO usuarioDAO) {
		this.model = model;
		this.usuarioDAO = usuarioDAO;
	}

	public void setLoginUsuario(String login) {
		this.model.setLogin(login);
	}

	public String getLoginUsuario() {
		return this.model.getLogin();
	}

	public Usuario getUsuarioByLogin() {
		String strPass = new String(this.model.getSenha()).trim();

		return this.usuarioDAO.getUsuarioByLogin(this.model.getLogin(), strPass);
	}
}