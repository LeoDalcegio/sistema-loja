package model;

import java.util.Date;

public class PedidoCompra extends BaseClass { 
	private Date DataHoraDaCompra;
	private int ClienteId;

	public Date getDataHoraDaCompra() {
		return DataHoraDaCompra;
	}

	public void setDataHoraDaCompra(Date dataHoraDaCompra) {
		DataHoraDaCompra = dataHoraDaCompra;
	}

	public int getClienteId() {
		return ClienteId;
	}

	public void setClienteId(int clienteId) {
		ClienteId = clienteId;
	}
}
