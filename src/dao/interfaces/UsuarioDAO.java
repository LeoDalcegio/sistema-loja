package dao.interfaces;

import java.util.List;

import model.Usuario;

public interface UsuarioDAO {
	public void salvaUsuario(Usuario usuario);

	public Usuario getUsuarioByLogin(String login, String senha);

	public List<Usuario> getAllUsuarios();

	public Usuario editaUsuario(Usuario usuarioObjeto);

	public void excluiUsuario(int usuarioId);

	public Usuario getUsuarioById(int usuarioId);
}
