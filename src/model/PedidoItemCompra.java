package model;

public class PedidoItemCompra extends BaseClass {
	private int PedidoCompraId;
	private int ProdutoId;
	private float Quantidade;
	private float ValorUnitario;

	public int getPedidoCompraId() {
		return PedidoCompraId;
	}

	public void setPedidoCompraId(int pedidoCompraId) {
		PedidoCompraId = pedidoCompraId;
	}

	public int getProdutoId() {
		return ProdutoId;
	}

	public void setProdutoId(int produtoId) {
		ProdutoId = produtoId;
	}

	public float getQuantidade() {
		return Quantidade;
	}

	public void setQuantidade(float quantidade) {
		Quantidade = quantidade;
	}

	public float getValorUnitario() {
		return ValorUnitario;
	}

	public void setValorUnitario(float valorUnitario) {
		ValorUnitario = valorUnitario;
	}

}
