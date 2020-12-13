package dao.interfaces;

import java.util.List;

import model.PedidoVenda;

public interface PedidoVendaDAO {
	public void salvaPedidoVenda(PedidoVenda pedidoVenda);

	public List<PedidoVenda> getAllPedidosVenda();

	public PedidoVenda editaPedidoVenda(PedidoVenda pedidoVendaObjeto);

	public void excluiPedidoVenda(int pedidoVendaId);
}
