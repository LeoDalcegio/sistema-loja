package model;

public class PedidoItemVenda extends BaseClass {
	private int PedidoVendaId;
	private int ProdutoId;
	private Float Quantidade;
	private Float ValorUnitario;
	private Produto produto;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

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

	public Float getQuantidade() {
		return Quantidade;
	}

	public void setQuantidade(Float quantidade) {
		Quantidade = quantidade;
	}

	public Float getValorUnitario() {
		return ValorUnitario;
	}

	public void setValorUnitario(Float valorUnitario) {
		ValorUnitario = valorUnitario;
	}

}
