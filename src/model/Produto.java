package model;

public class Produto extends BaseClass {
	private String codigoProduto;
	private String descricaoProduto;
	private float quantidadeEmEstoque;
	private float precoPadrao;

	public Produto() {
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

	public float getQuantidadeEmEstoque() {
		return quantidadeEmEstoque;
	}

	public void setQuantidadeEmEstoque(float quantidadeEmEstoque) {
		this.quantidadeEmEstoque = quantidadeEmEstoque;
	}

	public float getPrecoPadrao() {
		return precoPadrao;
	}

	public void setPrecoPadrao(float precoPadrao) {
		this.precoPadrao = precoPadrao;
	}
}
