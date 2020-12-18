package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.PedidoVendaDAO;
import model.Cliente;
import model.PedidoItemVenda;
import model.PedidoVenda;

public class PedidoVendaDAOImpl extends BDGenericoDAO implements PedidoVendaDAO {
	private Connection connection = null;

	public PedidoVendaDAOImpl() {

	}

	@Override
	public PedidoVenda salvaPedidoVenda(PedidoVenda pedidoVenda) {
		PreparedStatement pstmt = null;

		try {
			this.connection = getConnection("sistema-loja");
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			String sql = "INSERT INTO PedidoVenda " + "(DataDaVenda, ClienteId)" + "VALUES(?, ?)";
			pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setDate(1, new java.sql.Date(pedidoVenda.getDataDaVenda().getTime()));
			pstmt.setInt(2, pedidoVenda.getClienteId());
			pstmt.executeUpdate();

			ResultSet keys = pstmt.getGeneratedKeys();

			keys.next();
			int key = keys.getInt(1);

			pedidoVenda.setId(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}

		return pedidoVenda;
	}

	@Override
	public List<PedidoVenda> getAllPedidosVenda() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PedidoVenda> lstPedidoVenda = new ArrayList<PedidoVenda>();

		try {
			this.connection = getConnection("sistema-loja");
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			String sql = "SELECT PedidoVenda.*, Cliente.Nome, Cliente.CPF "
					+ "FROM PedidoVenda LEFT JOIN Cliente ON Cliente.Id = PedidoVenda.ClienteId";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				PedidoVenda pedidoVenda = new PedidoVenda();
				pedidoVenda.setId(Integer.parseInt(rs.getString("Id")));
				pedidoVenda.setDataDaVenda(rs.getDate("DataDaVenda"));
				pedidoVenda.setClienteId(rs.getInt("ClienteId"));

				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("ClienteId"));
				cliente.setCpf(rs.getString("CPF"));
				cliente.setNome(rs.getString("Nome"));
				pedidoVenda.setCliente(cliente);

				lstPedidoVenda.add(pedidoVenda);
			}

			return lstPedidoVenda;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		} finally {
			close(rs);

			close(connection);
		}
	}

	@Override
	public PedidoVenda getPedidoVendaById(int pedidoVendaId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			this.connection = getConnection("sistema-loja");
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			String sql = "SELECT PedidoVenda.*, Cliente.Nome, Cliente.CPF "
					+ "FROM PedidoVenda LEFT JOIN Cliente ON Cliente.Id = PedidoVenda.ClienteId WHERE PedidoVenda.Id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, pedidoVendaId);
			rs = pstmt.executeQuery();

			PedidoVenda pedidoVenda = new PedidoVenda();

			while (rs.next()) {
				pedidoVenda.setId(Integer.parseInt(rs.getString("Id")));
				pedidoVenda.setDataDaVenda(rs.getDate("DataDaVenda"));
				pedidoVenda.setClienteId(rs.getInt("ClienteId"));

				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("ClienteId"));
				cliente.setCpf(rs.getString("CPF"));
				cliente.setNome(rs.getString("Nome"));

				pedidoVenda.setCliente(cliente);
			}

			return pedidoVenda;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		} finally {
			close(rs);

			close(connection);
		}
	}

	@Override
	public PedidoVenda editaPedidoVenda(PedidoVenda pedidoVendaObjeto) {
		if (pedidoVendaObjeto.getId() == 0) {
			throw new IllegalArgumentException("Id informado inválido");
		}

		try {
			this.connection = getConnection("sistema-loja");
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		PreparedStatement pstmt = null;

		try {
			String sql = "UPDATE PedidoVenda SET DataDaVenda = ?, ClienteId = ? WHERE Id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setDate(1, new java.sql.Date(pedidoVendaObjeto.getDataDaVenda().getTime()));
			pstmt.setInt(2, pedidoVendaObjeto.getClienteId());
			pstmt.setInt(3, pedidoVendaObjeto.getId());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}

		return pedidoVendaObjeto;
	}

	@Override
	public void excluiPedidoVenda(int pedidoVendaId) {
		if (pedidoVendaId == 0) {
			throw new IllegalArgumentException("Id informado inválido");
		}

		try {
			this.connection = getConnection("sistema-loja");
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		PreparedStatement pstmt = null;

		try {
			PedidoItemVendaDAOImpl pedidoItemVendaDAOImpl = new PedidoItemVendaDAOImpl();

			List<PedidoItemVenda> pedidosItem = pedidoItemVendaDAOImpl.getAllPedidoItemVenda(pedidoVendaId);

			for (PedidoItemVenda pedidoItemVenda : pedidosItem) {
				pedidoItemVendaDAOImpl.excluiPedidoItemVenda(pedidoItemVenda.getId());
			}

			String sql = "DELETE FROM PedidoVenda WHERE Id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, pedidoVendaId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}
	}
}
