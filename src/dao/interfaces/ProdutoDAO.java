package dao.interfaces;

import java.util.List;

import model.Produto;

public interface ProdutoDAO {
	public void salvaProduto(Produto produto);

	public List<Produto> getAllProdutos();

	public Produto editaProduto(Produto produtoObjeto);

	public void excluiProduto(int produtoId);
}