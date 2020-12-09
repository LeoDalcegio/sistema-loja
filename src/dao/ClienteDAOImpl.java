package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.ClienteDAO;
import model.Cliente;

public class ClienteDAOImpl extends BDGenericoDAO implements ClienteDAO {
	private Connection connection = null;

	public ClienteDAOImpl() throws SQLException, ClassNotFoundException {
		this.connection = getConnection("sistema-loja");
	}

	@Override
	public void salvaCliente(Cliente cliente) {
		PreparedStatement pstmt = null;

		try {
			String sql = "INSERT INTO Cliente " + "(Nome, Cpf)" + "VALUES(?, ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, cliente.getNome());
			pstmt.setString(2, cliente.getCpf());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}
	}

	@Override
	public List<Cliente> getAllClientes() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Cliente> lstClientes = new ArrayList<Cliente>();

		try {
			String sql = "SELECT * " + "FROM Cliente";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(Integer.parseInt(rs.getString("Id")));
				cliente.setCpf(rs.getString("CPF"));
				cliente.setNome(rs.getString("Nome"));
				lstClientes.add(cliente);
			}

			return lstClientes;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		} finally {
			close(rs);

			close(connection);
		}
	}

	@Override
	public Cliente editaCliente(Cliente clienteObjeto) {
		if (clienteObjeto.getId() == 0) {
			throw new IllegalArgumentException("Id informado inválido");
		}

		PreparedStatement pstmt = null;

		try {
			String sql = "UPDATE Cliente SET Nome = ?, CPF = ? WHERE Id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, clienteObjeto.getNome());
			pstmt.setString(2, clienteObjeto.getCpf());
			pstmt.setInt(3, clienteObjeto.getId());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}

		return clienteObjeto;
	}

	@Override
	public void excluiCliente(int clienteId) {
		if (clienteId == 0) {
			throw new IllegalArgumentException("Id informado inválido");
		}
		PreparedStatement pstmt = null;

		try {
			String sql = "DELETE FROM Cliente WHERE Id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, clienteId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}
	}
}
