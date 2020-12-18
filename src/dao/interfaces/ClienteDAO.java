package dao.interfaces;

import java.util.List;

import model.Cliente;

public interface ClienteDAO {
	public void salvaCliente(Cliente cliente);

	public List<Cliente> getAllClientes();

	public Cliente editaCliente(Cliente clienteObjeto);

	public void excluiCliente(int clienteId);

	public Cliente getClienteById(int clienteId);
}