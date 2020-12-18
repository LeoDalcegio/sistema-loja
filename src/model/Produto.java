package model;

public class Produto extends BaseClass {
	private String codigoProduto;
	private String descricaoProduto;
	private Float quantidadeEmEstoque;
	private Float precoPadrao;
	private String codigoBarra;

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public Produto() {
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public String getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public String getDescricaoProduto() {
		return descricaoProduto;
	}

	public void setDescricaoProduto(String descricaoProduto) {
		this.descricaoProduto = descricaoProduto;
	}

	public Float getQuantidadeEmEstoque() {
		return quantidadeEmEstoque;
	}

	public void setQuantidadeEmEstoque(Float quantidadeEmEstoque) {
		this.quantidadeEmEstoque = quantidadeEmEstoque;
	}

	public Float getPrecoPadrao() {
		return precoPadrao;
	}

	public void setPrecoPadrao(Float precoPadrao) {
		this.precoPadrao = precoPadrao;
	}
}
