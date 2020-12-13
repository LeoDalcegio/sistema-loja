package controller;

import java.util.List;

import dao.interfaces.PedidoVendaDAO;
import model.PedidoVenda;

public class PedidoVendaController {
	private PedidoVendaDAO pedidoVendaDAO;

	public PedidoVendaController(PedidoVendaDAO pedidoVendaDAO) {
		this.pedidoVendaDAO = pedidoVendaDAO;
	}

	public List<PedidoVenda> getAllPedidosVenda() {
		return this.pedidoVendaDAO.getAllPedidosVenda();
	}

	public void salvaPedidoVenda(PedidoVenda pedidoVenda) {
		this.pedidoVendaDAO.salvaPedidoVenda(pedidoVenda);
	}

	public PedidoVenda editaPedidoVenda(PedidoVenda pedidoVenda) {
		return this.pedidoVendaDAO.editaPedidoVenda(pedidoVenda);
	}

	public void excluiPedidoVenda(int pedidoVendaId) {
		this.pedidoVendaDAO.excluiPedidoVenda(pedidoVendaId);
	}
}
