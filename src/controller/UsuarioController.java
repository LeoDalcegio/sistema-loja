package controller;

import dao.interfaces.UsuarioDAO;
import model.Usuario;

public class UsuarioController {
	private static UsuarioController instance;

	private Usuario _model;
	private UsuarioDAO _usuarioDAO;

	private UsuarioController() {
	}

	public static UsuarioController getInstance() {
		if (instance == null) {
			instance = new UsuarioController();
		}
		return instance;
	}

	public void iniciaDadosUsuario(Usuario model, UsuarioDAO usuarioDAO) {
		_model = model;
		_usuarioDAO = usuarioDAO;
	}

	public void setLoginUsuario(String login) {
		_model.setLogin(login);
	}

	public String getLoginUsuario() {
		return _model.getLogin();
	}

	public Usuario getUsuarioByLogin() {
		String strPass = new String(_model.getSenha()).trim();

		return _usuarioDAO.getUsuarioByLogin(_model.getLogin(), strPass);
	}
}