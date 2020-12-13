package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.PedidoItemVendaDAO;
import model.PedidoItemVenda;

public class PedidoItemVendaDAOImpl extends BDGenericoDAO implements PedidoItemVendaDAO {
	private Connection connection = null;

	public PedidoItemVendaDAOImpl() throws SQLException, ClassNotFoundException {
		this.connection = getConnection("sistema-loja");
	}

	@Override
	public void salvaPedidoItemVenda(PedidoItemVenda pedidoItemVenda) {
		PreparedStatement pstmt = null;

		try {
			String sql = "INSERT INTO PedidoItemVenda " + "(ProdutoId, PedidoVendaId, Quantidade, ValorUnitario)"
					+ "VALUES(?, ?, ?, ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, pedidoItemVenda.getProdutoId());
			pstmt.setInt(2, pedidoItemVenda.getPedidoVendaId());
			pstmt.setFloat(3, pedidoItemVenda.getQuantidade());
			pstmt.setFloat(4, pedidoItemVenda.getValorUnitario());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}
	}

	@Override
	public List<PedidoItemVenda> getAllPedidoItemVenda() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PedidoItemVenda> lstPedidoItemVenda = new ArrayList<PedidoItemVenda>();

		try {
			String sql = "SELECT * " + "FROM PedidoItemVenda";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				PedidoItemVenda pedidoItemVenda = new PedidoItemVenda();
				pedidoItemVenda.setId(Integer.parseInt(rs.getString("Id")));
				pedidoItemVenda.setPedidoVendaId(rs.getInt("PedidoVendaId"));
				pedidoItemVenda.setQuantidade(rs.getFloat("Quantidade"));
				pedidoItemVenda.setValorUnitario(rs.getFloat("ValorUnitario"));
				lstPedidoItemVenda.add(pedidoItemVenda);
			}

			return lstPedidoItemVenda;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		} finally {
			close(rs);

			close(connection);
		}
	}

	@Override
	public PedidoItemVenda editaPedidoItemVenda(PedidoItemVenda pedidoItemVendaObjeto) {
		if (pedidoItemVendaObjeto.getId() == 0) {
			throw new IllegalArgumentException("Id informado inválido");
		}

		PreparedStatement pstmt = null;

		try {
			String sql = "UPDATE PedidoItemVenda SET ProdutoId = ?, PedidoVendaId = ?, Quantidade = ?, ValorUnitario = ? WHERE Id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, pedidoItemVendaObjeto.getProdutoId());
			pstmt.setInt(2, pedidoItemVendaObjeto.getPedidoVendaId());
			pstmt.setFloat(3, pedidoItemVendaObjeto.getQuantidade());
			pstmt.setFloat(4, pedidoItemVendaObjeto.getValorUnitario());
			pstmt.setInt(5, pedidoItemVendaObjeto.getId());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}

		return pedidoItemVendaObjeto;
	}

	@Override
	public void excluiPedidoItemVenda(int pedidoItemVendaId) {
		if (pedidoItemVendaId == 0) {
			throw new IllegalArgumentException("Id informado inválido");
		}
		PreparedStatement pstmt = null;

		try {
			String sql = "DELETE FROM PedidoItemVenda WHERE Id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, pedidoItemVendaId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}
	}
}
