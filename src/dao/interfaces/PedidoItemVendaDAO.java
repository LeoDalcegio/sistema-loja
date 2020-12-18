package dao.interfaces;

import java.util.List;

import model.PedidoItemVenda;

public interface PedidoItemVendaDAO {
	public void salvaPedidoItemVenda(PedidoItemVenda pedidoItemVenda);

	public List<PedidoItemVenda> getAllPedidoItemVenda(int pedidoId);

	public PedidoItemVenda editaPedidoItemVenda(PedidoItemVenda pedidoItemVendaObjeto);

	public void excluiPedidoItemVenda(int pedidoItemVendaId);

	public PedidoItemVenda getPedidoItemVendaById(int pedidoItemVendaId);
}
