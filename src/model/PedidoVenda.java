package model;

import java.util.Date;

public class PedidoVenda extends BaseClass {
	private Date DataDaVenda;
	private int ClienteId;
	private Cliente cliente;

	public Date getDataDaVenda() {
		return DataDaVenda;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
