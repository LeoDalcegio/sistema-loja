package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.ProdutoDAO;
import model.Produto;

public class ProdutoDAOImpl extends BDGenericoDAO implements ProdutoDAO {
	private Connection connection = null;

	public ProdutoDAOImpl() {

	}

	@Override
	public List<Produto> getAllProdutos() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Produto> lstProdutos = new ArrayList<Produto>();

		try {
			this.connection = getConnection("sistema-loja");
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			String sql = "SELECT * " + "FROM Produto";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setId(Integer.parseInt(rs.getString("Id")));
				produto.setCodigoProduto(rs.getString("CodigoProduto"));
				produto.setDescricaoProduto(rs.getString("DescricaoProduto"));
				produto.setQuantidadeEmEstoque(Float.parseFloat(rs.getString("QuantidadeEmEstoque")));
				produto.setPrecoPadrao(Float.parseFloat(rs.getString("PrecoPadrao")));
				produto.setCodigoBarra(rs.getString("CodigoBarra"));
				lstProdutos.add(produto);
			}

			return lstProdutos;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		} finally {
			close(rs);

			close(connection);
		}
	}

	@Override
	public Produto getProdutoById(int produtoId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			this.connection = getConnection("sistema-loja");
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			String sql = "SELECT * " + "FROM Produto WHERE Id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, produtoId);
			rs = pstmt.executeQuery();

			Produto produto = new Produto();

			if (rs.next()) {
				produto.setId(Integer.parseInt(rs.getString("Id")));
				produto.setCodigoProduto(rs.getString("CodigoProduto"));
				produto.setDescricaoProduto(rs.getString("DescricaoProduto"));
				produto.setQuantidadeEmEstoque(Float.parseFloat(rs.getString("QuantidadeEmEstoque")));
				produto.setPrecoPadrao(Float.parseFloat(rs.getString("PrecoPadrao")));
				produto.setCodigoBarra(rs.getString("CodigoBarra"));
			}

			return produto;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		} finally {
			close(rs);

			close(connection);
		}
	}

	@Override
	public void salvaProduto(Produto produto) {
		PreparedStatement pstmt = null;

		try {
			this.connection = getConnection("sistema-loja");
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			String sql = "INSERT INTO Produto "
					+ "(CodigoProduto, DescricaoProduto, QuantidadeEmEstoque, PrecoPadrao, CodigoBarra)"
					+ "VALUES(?, ?, ?, ?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, produto.getCodigoProduto());
			pstmt.setString(2, produto.getDescricaoProduto());
			pstmt.setDouble(3, produto.getQuantidadeEmEstoque());
			pstmt.setDouble(4, produto.getPrecoPadrao());
			pstmt.setString(5, produto.getCodigoBarra());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}
	}

	@Override
	public Produto editaProduto(Produto produtoObjeto) {
		if (produtoObjeto.getId() == 0) {
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
			String sql = "UPDATE Produto SET CodigoProduto = ?, DescricaoProduto = ?, QuantidadeEmEstoque = ?, PrecoPadrao = ?, CodigoBarra = ? WHERE Id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, produtoObjeto.getCodigoProduto());
			pstmt.setString(2, produtoObjeto.getDescricaoProduto());
			pstmt.setFloat(3, produtoObjeto.getQuantidadeEmEstoque());
			pstmt.setFloat(4, produtoObjeto.getPrecoPadrao());
			pstmt.setString(5, produtoObjeto.getCodigoBarra());
			pstmt.setInt(6, produtoObjeto.getId());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}

		return produtoObjeto;
	}

	@Override
	public Produto getProdutoByCodigoDeBarras(String codigoDeBarras) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			this.connection = getConnection("sistema-loja");
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			String sql = "SELECT * " + "FROM Produto WHERE CodigoBarra = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, codigoDeBarras);
			rs = pstmt.executeQuery();

			Produto produto = new Produto();

			if (rs.next()) {
				produto.setId(Integer.parseInt(rs.getString("Id")));
				produto.setCodigoProduto(rs.getString("CodigoProduto"));
				produto.setDescricaoProduto(rs.getString("DescricaoProduto"));
				produto.setQuantidadeEmEstoque(Float.parseFloat(rs.getString("QuantidadeEmEstoque")));
				produto.setPrecoPadrao(Float.parseFloat(rs.getString("PrecoPadrao")));
				produto.setCodigoBarra(rs.getString("CodigoBarra"));
			}

			return produto;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		} finally {
			close(rs);

			close(connection);
		}
	}

	@Override
	public void excluiProduto(int produtoId) {
		if (produtoId == 0) {
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
			String sql = "DELETE FROM Produto WHERE Id = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, produtoId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}

	}
}