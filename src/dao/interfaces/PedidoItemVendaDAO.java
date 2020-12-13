package dao.interfaces;

import java.util.List;

import model.PedidoItemVenda;

public interface PedidoItemVendaDAO {
	public void salvaPedidoItemVenda(PedidoItemVenda pedidoItemVenda);

	public List<PedidoItemVenda> getAllPedidoItemVenda();

	public PedidoItemVenda editaPedidoItemVenda(PedidoItemVenda pedidoItemVendaObjeto);

	public void excluiPedidoItemVenda(int pedidoItemVendaId);
}
