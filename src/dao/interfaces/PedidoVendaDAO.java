package dao.interfaces;

import java.util.List;

import model.PedidoVenda;

public interface PedidoVendaDAO {
	public PedidoVenda salvaPedidoVenda(PedidoVenda pedidoVenda);

	public List<PedidoVenda> getAllPedidosVenda();

	public PedidoVenda editaPedidoVenda(PedidoVenda pedidoVendaObjeto);

	public void excluiPedidoVenda(int pedidoVendaId);

	public PedidoVenda getPedidoVendaById(int pedidoVendaId);
}
