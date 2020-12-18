package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.PedidoItemVendaController;
import controller.ProdutoController;
import dao.PedidoItemVendaDAOImpl;
import dao.ProdutoDAOImpl;
import enums.RequestType;
import model.PedidoItemVenda;
import model.Produto;
import util.DefaultComboItem;

public class jdFormPedidoItemVenda extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private RequestType windowRequestType;
	private PedidoItemVenda pedidoItemVendaEditar;
	private int idPedido;
	private JTextField txtValorUnitario;
	private JTextField txtQuantidade;
	private JComboBox<String> comboBox;
	private HashMap<String, Integer> map;
	private PedidoItemVendaController pedidoItemVendaController;
	private ProdutoController produtoController;

	/**
	 * Create the dialog.
	 */
	public jdFormPedidoItemVenda(RequestType requestType, PedidoItemVenda pedidoItemVendaEditar, int idPedido) {
		setModal(true);
		this.pedidoItemVendaEditar = pedidoItemVendaEditar;
		this.windowRequestType = requestType;
		this.idPedido = idPedido;

		PedidoItemVendaDAOImpl pedidoItemVendaDAOImpl = new PedidoItemVendaDAOImpl();

		pedidoItemVendaController = new PedidoItemVendaController(pedidoItemVendaDAOImpl);

		ProdutoDAOImpl produtoDAOImpl = null;

		produtoDAOImpl = new ProdutoDAOImpl();

		produtoController = new ProdutoController(produtoDAOImpl);

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		{
			JButton okButton = new JButton("Salvar");
			okButton.setForeground(Color.BLACK);
			okButton.setBorderPainted(false);
			okButton.setBackground(Color.GREEN);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// VALIDAR CAMPOS
					if (Float.parseFloat(txtQuantidade.getText()) == 0) {
						txtQuantidade.requestFocus();
						return;
					}

					if (Float.parseFloat(txtValorUnitario.getText()) == 0) {
						txtValorUnitario.requestFocus();
						return;
					}

					int produtoId = (int) map.get(comboBox.getSelectedItem().toString());

					Produto produto = produtoController.getProdutoById(produtoId);

					Float quantidadeInicial = pedidoItemVendaEditar == null ? 0 : pedidoItemVendaEditar.getQuantidade();

					if (produto.getQuantidadeEmEstoque() + quantidadeInicial >= Float
							.parseFloat(txtQuantidade.getText())) {
						produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() + quantidadeInicial
								- Float.parseFloat(txtQuantidade.getText()));

						produtoController.editaProduto(produto);
					} else {
						JOptionPane.showMessageDialog(null,
								"Quantidade informada do produto não disponível em estoque");

						return;
					}

					PedidoItemVenda pedidoItemVenda = new PedidoItemVenda();
					pedidoItemVenda.setId(pedidoItemVendaEditar != null ? pedidoItemVendaEditar.getId() : 0);
					pedidoItemVenda.setPedidoVendaId(idPedido);
					pedidoItemVenda.setQuantidade(Float.parseFloat(txtQuantidade.getText()));
					pedidoItemVenda.setValorUnitario(Float.parseFloat(txtValorUnitario.getText()));

					pedidoItemVenda.setProdutoId(produtoId);

					if (jdFormPedidoItemVenda.this.windowRequestType == RequestType.Create) {
						pedidoItemVendaController.salvaPedidoItemVenda(pedidoItemVenda);
					} else if (jdFormPedidoItemVenda.this.windowRequestType == RequestType.Edit) {
						pedidoItemVendaController.editaPedidoItemVenda(pedidoItemVenda);
					}

					jdFormPedidoItemVenda.this
							.dispatchEvent(new WindowEvent(jdFormPedidoItemVenda.this, WindowEvent.WINDOW_CLOSING));
				}
			});

			okButton.setBounds(254, 236, 86, 27);
			contentPanel.add(okButton);
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			JButton cancelButton = new JButton("Cancelar");
			cancelButton.setBackground(Color.LIGHT_GRAY);
			cancelButton.setForeground(Color.BLACK);
			cancelButton.setBorderPainted(false);
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					jdFormPedidoItemVenda.this
							.dispatchEvent(new WindowEvent(jdFormPedidoItemVenda.this, WindowEvent.WINDOW_CLOSING));
				}
			});
			cancelButton.setBounds(352, 236, 86, 27);
			contentPanel.add(cancelButton);
			cancelButton.setActionCommand("Cancel");
		}

		JLabel lblCliente = new JLabel("Produto:");
		lblCliente.setBounds(12, 17, 60, 17);
		contentPanel.add(lblCliente);

		JLabel lblValor = new JLabel("Valor Unitário:");
		lblValor.setBounds(12, 71, 86, 17);
		contentPanel.add(lblValor);

		txtValorUnitario = new JTextField();
		txtValorUnitario.setColumns(10);
		txtValorUnitario.setBounds(107, 69, 114, 21);
		contentPanel.add(txtValorUnitario);

		JLabel lblQuantidade = new JLabel("Quantidade:");
		lblQuantidade.setBounds(12, 44, 86, 17);
		contentPanel.add(lblQuantidade);

		txtQuantidade = new JTextField();
		txtQuantidade.setColumns(10);
		txtQuantidade.setBounds(107, 42, 114, 21);
		contentPanel.add(txtQuantidade);

		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (requestType == RequestType.Create) {
					int produtoId = (int) map.get(comboBox.getSelectedItem().toString());

					if (Float.parseFloat(txtValorUnitario.getText().toString()) == 0) {
						Produto produtoQuantidade = produtoController.getProdutoById(produtoId);
						txtValorUnitario.setText(produtoQuantidade.getPrecoPadrao().toString());
					}
				}
			}
		});
		comboBox.setBackground(Color.WHITE);
		comboBox.setBounds(107, 15, 233, 21);
		contentPanel.add(comboBox);

		this.BindCompo();

		if (this.pedidoItemVendaEditar != null) {
			txtQuantidade.setText(String.valueOf(this.pedidoItemVendaEditar.getQuantidade()));
			txtValorUnitario.setText(String.valueOf(this.pedidoItemVendaEditar.getValorUnitario()));

			comboBox.setSelectedItem(this.pedidoItemVendaEditar.getProduto().getCodigoProduto() + " - "
					+ this.pedidoItemVendaEditar.getProduto().getDescricaoProduto());
		}

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	private void BindCompo() {
		try {
			this.map = this.populateCombo();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String s : this.map.keySet()) {
			comboBox.addItem(s);
		}
	}

	private HashMap<String, Integer> populateCombo() throws ClassNotFoundException, SQLException {
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		ProdutoDAOImpl produtoDAOImpl = new ProdutoDAOImpl();

		ProdutoController produtoController = new ProdutoController(produtoDAOImpl);

		List<Produto> produtos = produtoController.getAllProdutos();

		DefaultComboItem cmi;

		for (Produto produto : produtos) {
			cmi = new DefaultComboItem(produto.getId(),
					produto.getCodigoProduto() + " - " + produto.getDescricaoProduto());
			map.put(cmi.getDescricao(), cmi.getId());
		}

		return map;
	}
}
