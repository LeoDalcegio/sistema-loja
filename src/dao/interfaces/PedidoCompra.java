package dao.interfaces;

import java.util.List;

public interface PedidoCompra {
	public void salvaPedidoCompra(PedidoCompra pedidoCompra);

	public List<PedidoCompra> getAllPedidos();

	public PedidoCompra editaProduto(PedidoCompra pedidoCompraObjeto);

	public void excluiProduto(int pedidoCompraId);
}
