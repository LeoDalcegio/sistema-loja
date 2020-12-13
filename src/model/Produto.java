package model;

public class Produto extends BaseClass {
	private String codigoProduto;
	private String descricaoProduto;
	private double quantidadeEmEstoque;
	private double precoPadrao;

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

	public double getQuantidadeEmEstoque() {
		return quantidadeEmEstoque;
	}

	public void setQuantidadeEmEstoque(double quantidadeEmEstoque) {
		this.quantidadeEmEstoque = quantidadeEmEstoque;
	}

	public double getPrecoPadrao() {
		return precoPadrao;
	}

	public void setPrecoPadrao(double precoPadrao) {
		this.precoPadrao = precoPadrao;
	}
}
