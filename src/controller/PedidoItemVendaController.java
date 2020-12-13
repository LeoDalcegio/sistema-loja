package controller;

import java.util.List;

import dao.interfaces.PedidoItemVendaDAO;
import model.PedidoItemVenda;

public class PedidoItemVendaController {
	private PedidoItemVendaDAO pedidoItemVendaDAO;

	public PedidoItemVendaController(PedidoItemVendaDAO pedidoItemVendaDAO) {
		this.pedidoItemVendaDAO = pedidoItemVendaDAO;
	}

	public List<PedidoItemVenda> getAllPedidosItemVenda() {
		return this.pedidoItemVendaDAO.getAllPedidoItemVenda();
	}

	public void salvaPedidoItemVenda(PedidoItemVenda pedidoItemVenda) {
		this.pedidoItemVendaDAO.salvaPedidoItemVenda(pedidoItemVenda);
	}

	public PedidoItemVenda editaPedidoItemVenda(PedidoItemVenda pedidoItemVenda) {
		return this.pedidoItemVendaDAO.editaPedidoItemVenda(pedidoItemVenda);
	}

	public void excluiPedidoItemVenda(int pedidoItemVendaId) {
		this.pedidoItemVendaDAO.excluiPedidoItemVenda(pedidoItemVendaId);
	}
}
