package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.PedidoItemVendaDAO;
import model.PedidoItemVenda;
import model.Produto;

public class PedidoItemVendaDAOImpl extends BDGenericoDAO implements PedidoItemVendaDAO {
	private Connection connection = null;

	public PedidoItemVendaDAOImpl() {

	}

	@Override
	public void salvaPedidoItemVenda(PedidoItemVenda pedidoItemVenda) {
		PreparedStatement pstmt = null;

		try {
			this.connection = getConnection("sistema-loja");
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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
	public List<PedidoItemVenda> getAllPedidoItemVenda(int pedidoId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PedidoItemVenda> lstPedidoItemVenda = new ArrayList<PedidoItemVenda>();

		try {
			this.connection = getConnection("sistema-loja");
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			String sql = "SELECT PedidoItemVenda.*, Produto.CodigoProduto, Produto.DescricaoProduto , Produto.PrecoPadrao, Produto.QuantidadeEmEstoque "
					+ "FROM PedidoItemVenda LEFT JOIN Produto ON Produto.Id = PedidoItemVenda.ProdutoId WHERE PedidoItemVenda.PedidoVendaId = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, pedidoId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				PedidoItemVenda pedidoItemVenda = new PedidoItemVenda();
				pedidoItemVenda.setId(Integer.parseInt(rs.getString("Id")));
				pedidoItemVenda.setProdutoId(Integer.parseInt(rs.getString("ProdutoId")));
				pedidoItemVenda.setPedidoVendaId(rs.getInt("PedidoVendaId"));
				pedidoItemVenda.setQuantidade(rs.getFloat("Quantidade"));
				pedidoItemVenda.setValorUnitario(rs.getFloat("ValorUnitario"));

				Produto produto = new Produto();
				produto.setId(rs.getInt("ProdutoId"));
				produto.setCodigoProduto(rs.getString("CodigoProduto"));
				produto.setDescricaoProduto(rs.getString("DescricaoProduto"));
				produto.setPrecoPadrao(rs.getFloat("PrecoPadrao"));
				produto.setQuantidadeEmEstoque(rs.getFloat("QuantidadeEmEstoque"));

				pedidoItemVenda.setProduto(produto);

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

		try {
			this.connection = getConnection("sistema-loja");
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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

		var pedidoItemVenda = this.getPedidoItemVendaById(pedidoItemVendaId);

		ProdutoDAOImpl produtoDAOImpl = new ProdutoDAOImpl();
		Produto produto = produtoDAOImpl.getProdutoById(pedidoItemVenda.getProdutoId());

		produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() - pedidoItemVenda.getQuantidade());

		produtoDAOImpl.editaProduto(produto);

		try {
			this.connection = getConnection("sistema-loja");
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			String sql = "DELETE FROM PedidoItemVenda WHERE Id = ?";
			pstmt = this.connection.prepareStatement(sql);
			pstmt.setInt(1, pedidoItemVendaId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}
	}

	@Override
	public PedidoItemVenda getPedidoItemVendaById(int pedidoItemVendaId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			this.connection = getConnection("sistema-loja");
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			String sql = "SELECT PedidoItemVenda.*, Produto.CodigoProduto, Produto.DescricaoProduto , Produto.PrecoPadrao, Produto.QuantidadeEmEstoque "
					+ "FROM PedidoItemVenda LEFT JOIN Produto ON Produto.Id = PedidoItemVenda.ProdutoId WHERE PedidoItemVenda.Id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, pedidoItemVendaId);
			rs = pstmt.executeQuery();

			PedidoItemVenda pedidoItemVenda = new PedidoItemVenda();

			if (rs.next()) {
				pedidoItemVenda.setId(Integer.parseInt(rs.getString("Id")));
				pedidoItemVenda.setPedidoVendaId(rs.getInt("PedidoVendaId"));
				pedidoItemVenda.setQuantidade(rs.getFloat("Quantidade"));
				pedidoItemVenda.setValorUnitario(rs.getFloat("ValorUnitario"));
				pedidoItemVenda.setProdutoId(Integer.parseInt(rs.getString("ProdutoId")));

				Produto produto = new Produto();
				produto.setId(rs.getInt("ProdutoId"));
				produto.setCodigoProduto(rs.getString("CodigoProduto"));
				produto.setDescricaoProduto(rs.getString("DescricaoProduto"));
				produto.setPrecoPadrao(rs.getFloat("PrecoPadrao"));
				produto.setQuantidadeEmEstoque(rs.getFloat("QuantidadeEmEstoque"));

				pedidoItemVenda.setProduto(produto);
			}

			return pedidoItemVenda;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		} finally {
			close(rs);

			close(connection);
		}
	}
}
