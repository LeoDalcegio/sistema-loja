package model;

public class PedidoItemVenda extends BaseClass {
	private int PedidoVendaId;
	private int ProdutoId;
	private float Quantidade;
	private float ValorUnitario;

	public int getPedidoVendaId() {
		return PedidoVendaId;
	}

	public void setPedidoVendaId(int pedidoVendaId) {
		PedidoVendaId = pedidoVendaId;
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
