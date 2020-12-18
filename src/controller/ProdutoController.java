package controller;

import java.util.List;

import dao.interfaces.ProdutoDAO;
import model.Produto;

public class ProdutoController {
	private ProdutoDAO produtoDAO;

	public ProdutoController(ProdutoDAO produtoDAO) {
		this.produtoDAO = produtoDAO;
	}

	public List<Produto> getAllProdutos() {
		return this.produtoDAO.getAllProdutos();
	}

	public void salvaPrduto(Produto produto) {
		this.produtoDAO.salvaProduto(produto);
	}

	public Produto editaProduto(Produto produto) {
		return this.produtoDAO.editaProduto(produto);
	}

	public void excluiProduto(int produtoId) {
		this.produtoDAO.excluiProduto(produtoId);
	}

	public Produto getProdutoById(int produtoId) {
		return this.produtoDAO.getProdutoById(produtoId);
	}

	public Produto getProdutoByCodigoDeBarras(String codigoDeBarras) {
		return this.produtoDAO.getProdutoByCodigoDeBarras(codigoDeBarras);
	}
}
