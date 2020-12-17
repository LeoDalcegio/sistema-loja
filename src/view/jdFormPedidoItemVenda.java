package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.PedidoItemVendaController;
import dao.PedidoItemVendaDAOImpl;
import enums.RequestType;
import model.PedidoItemVenda;

public class jdFormPedidoItemVenda extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private RequestType windowRequestType;
	private PedidoItemVenda pedidoItemVendaEditar;
	private int idPedido;
	private JTextField txtProduto;
	private JTextField txtValorUnitario;
	private JTextField txtQuantidade;

	/**
	 * Create the dialog.
	 */
	public jdFormPedidoItemVenda(RequestType requestType, PedidoItemVenda pedidoItemVendaEditar, int idPedido) {
		setModal(true);
		this.pedidoItemVendaEditar = pedidoItemVendaEditar;
		this.windowRequestType = requestType;
		this.idPedido = idPedido;

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		if (this.pedidoItemVendaEditar != null) {
			txtQuantidade.setText(String.valueOf(this.pedidoItemVendaEditar.getQuantidade()));
			txtValorUnitario.setText(String.valueOf(this.pedidoItemVendaEditar.getValorUnitario()));
		}
		{
			JButton okButton = new JButton("Salvar");
			okButton.setForeground(Color.BLACK);
			okButton.setBorderPainted(false);
			okButton.setBackground(Color.GREEN);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// VALIDAR CAMPOS
					if (txtQuantidade.getText().equals("")) {
						txtQuantidade.requestFocus();
						return;
					}

					if (txtValorUnitario.getText().equals("")) {
						txtValorUnitario.requestFocus();
						return;
					}

					if (txtProduto.getText().equals("")) {
						txtProduto.requestFocus();
						return;
					}

					PedidoItemVenda pedidoItemVenda = new PedidoItemVenda();
					pedidoItemVenda.setId(pedidoItemVenda != null ? pedidoItemVenda.getId() : 0);
					pedidoItemVenda.setPedidoVendaId(idPedido);
					pedidoItemVenda.setProdutoId(Integer.parseInt(txtProduto.getText()));
					pedidoItemVenda.setQuantidade(Float.parseFloat(txtQuantidade.getText()));
					pedidoItemVenda.setValorUnitario(Float.parseFloat(txtValorUnitario.getText()));

					PedidoItemVendaController pedidoItemVendaController = null;

					try {
						PedidoItemVendaDAOImpl pedidoItemVendaDAOImpl = new PedidoItemVendaDAOImpl();

						pedidoItemVendaController = new PedidoItemVendaController(pedidoItemVendaDAOImpl);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

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

		txtProduto = new JTextField();
		txtProduto.setColumns(10);
		txtProduto.setBounds(107, 15, 114, 21);
		contentPanel.add(txtProduto);

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

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
}
