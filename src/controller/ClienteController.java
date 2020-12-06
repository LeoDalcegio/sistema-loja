package controller;

import java.util.List;

import dao.interfaces.ClienteDAO;
import model.Cliente;

public class ClienteController {
	private ClienteDAO clienteDAO;

	public ClienteController(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}

	public List<Cliente> getAllClientes() {
		return this.clienteDAO.getAllClientes();
	}

	public void salvaCliente(Cliente cliente) {
		this.clienteDAO.salvaCliente(cliente);
	}

	public Cliente editaCliente(Cliente cliente) {
		return this.clienteDAO.editaCliente(cliente);
	}

	public void excluiCliente(int clienteId) {
		this.clienteDAO.excluiCliente(clienteId);
	}
}
