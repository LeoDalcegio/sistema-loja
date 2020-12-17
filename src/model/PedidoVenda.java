package model;

import java.util.Date;

public class PedidoVenda extends BaseClass {
	private Date DataDaVenda;
	private int ClienteId;
	private Float ValorPedido;

	public Float getValorPedido() {
		return ValorPedido;
	}

	public void setValorPedido(Float valorPedido) {
		ValorPedido = valorPedido;
	}

	public Date getDataDaVenda() {
		return DataDaVenda;
	}

	public void setDataDaVenda(Date dataDaVenda) {
		DataDaVenda = dataDaVenda;
	}

	public int getClienteId() {
		return ClienteId;
	}

	public void setClienteId(int clienteId) {
		ClienteId = clienteId;
	}
}
