package dao.interfaces;

import java.util.List;

public interface PedidoItemCompra {
	public void salvaPedidoCompra(PedidoItemCompra pedidoCompra);

	public List<PedidoItemCompra> getAllPedidoCompra();

	public PedidoItemCompra editaPedidoCompra(PedidoItemCompra pedidoCompraObjeto);

	public void excluiPedidoCompra(int pedidoCompraId);
}
