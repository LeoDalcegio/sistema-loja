package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.PedidoVendaDAO;
import model.PedidoVenda;

public class PedidoVendaDAOImpl extends BDGenericoDAO implements PedidoVendaDAO {
	private Connection connection = null;

	public PedidoVendaDAOImpl() throws SQLException, ClassNotFoundException {
		this.connection = getConnection("sistema-loja");
	}

	@Override
	public PedidoVenda salvaPedidoVenda(PedidoVenda pedidoVenda) {
		PreparedStatement pstmt = null;

		try {
			String sql = "INSERT INTO PedidoVenda " + "(DataDaVenda, ClienteId, ValorPedido)" + "VALUES(?, ?, ?)";
			pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setDate(1, new java.sql.Date(pedidoVenda.getDataDaVenda().getTime()));
			pstmt.setInt(2, pedidoVenda.getClienteId());
			pstmt.setFloat(3, pedidoVenda.getValorPedido());
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
			String sql = "SELECT * " + "FROM PedidoVenda";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				PedidoVenda pedidoVenda = new PedidoVenda();
				pedidoVenda.setId(Integer.parseInt(rs.getString("Id")));
				pedidoVenda.setDataDaVenda(rs.getDate("DataDaVenda"));
				pedidoVenda.setClienteId(rs.getInt("ClienteId"));
				pedidoVenda.setValorPedido(rs.getFloat("ValorPedido"));
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
	public PedidoVenda editaPedidoVenda(PedidoVenda pedidoVendaObjeto) {
		if (pedidoVendaObjeto.getId() == 0) {
			throw new IllegalArgumentException("Id informado inválido");
		}

		PreparedStatement pstmt = null;

		try {
			String sql = "UPDATE PedidoVenda SET DataDaVenda = ?, ClienteId = ?, ValorPedido = ? WHERE Id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setDate(1, (Date) pedidoVendaObjeto.getDataDaVenda());
			pstmt.setInt(2, pedidoVendaObjeto.getClienteId());
			pstmt.setFloat(3, pedidoVendaObjeto.getValorPedido());
			pstmt.setInt(4, pedidoVendaObjeto.getId());
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
		PreparedStatement pstmt = null;

		try {
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
