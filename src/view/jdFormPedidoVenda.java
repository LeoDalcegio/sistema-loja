package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.PedidoVendaController;
import dao.PedidoVendaDAOImpl;
import enums.RequestType;
import model.PedidoVenda;

public class jdFormPedidoVenda extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtDataPedidoVenda;
	private RequestType windowRequestType;
	private PedidoVenda pedidoVendaEditar;
	private JTextField txtCliente;
	private JTextField txtValorPedido;
	private JTextField txCliente;

	/**
	 * Launch the application.
	 */
	public void run(RequestType requestType, PedidoVenda pedidoVendaEditar) {
		try {
			this.pedidoVendaEditar = pedidoVendaEditar;
			this.windowRequestType = requestType;

			jdFormPedidoVenda dialog = new jdFormPedidoVenda(requestType, pedidoVendaEditar);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean pocessaSalvar() {
		if (txtDataPedidoVenda.getText().equals("")) {
			txtDataPedidoVenda.requestFocus();
			return true;
		}

		if (txtCliente.getText().equals("")) {
			txtCliente.requestFocus();
			return true;
		}

		PedidoVenda pedidoVenda = new PedidoVenda();
		pedidoVenda.setId(pedidoVenda != null ? pedidoVenda.getId() : 0);
		// pedidoVenda.setClienteId(txt);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {
			pedidoVenda.setDataDaVenda(formatter.parse(txtDataPedidoVenda.getText()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		PedidoVendaController pedidoVendaController = null;

		try {
			PedidoVendaDAOImpl pedidoVendaDAOImpl = new PedidoVendaDAOImpl();

			pedidoVendaController = new PedidoVendaController(pedidoVendaDAOImpl);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (jdFormPedidoVenda.this.windowRequestType == RequestType.Create) {
			pedidoVendaEditar = pedidoVendaController.salvaPedidoVenda(pedidoVenda);
		} else if (jdFormPedidoVenda.this.windowRequestType == RequestType.Edit) {
			pedidoVendaEditar = pedidoVendaController.editaPedidoVenda(pedidoVenda);
		}

		return false;
	}

	/**
	 * Create the dialog.
	 */
	public jdFormPedidoVenda(RequestType requestType, PedidoVenda pedidoVendaEditar) {
		setModal(true);
		this.pedidoVendaEditar = pedidoVendaEditar;
		this.windowRequestType = requestType;

		setBounds(100, 100, 579, 286);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		if (this.pedidoVendaEditar != null) {
			txtDataPedidoVenda.setText(this.pedidoVendaEditar.getDataDaVenda().toString());
			// txtCliente.setText(this.pedidoVendaEditar.getClienteId());
			// txtValorPedido = 0;
		}
		{
			JButton okButton = new JButton("Salvar");
			okButton.setForeground(Color.BLACK);
			okButton.setBorderPainted(false);
			okButton.setBackground(Color.GREEN);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					if (pocessaSalvar() == true) {
						return;
					}

					jdFormPedidoVenda.this
							.dispatchEvent(new WindowEvent(jdFormPedidoVenda.this, WindowEvent.WINDOW_CLOSING));
				}
			});

			okButton.setBounds(387, 227, 86, 27);
			contentPanel.add(okButton);
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			JButton cancelButton = new JButton("Cancelar");
			cancelButton.setBackground(Color.LIGHT_GRAY);
			cancelButton.setForeground(Color.BLACK);
			cancelButton.setBorderPainted(false);

		}

		txtValorPedido = new JTextField();
		txtValorPedido.setColumns(10);
		txtValorPedido.setBounds(93, 151, 114, 21);
		contentPanel.add(txtValorPedido);

		JLabel lblValorTotal = new JLabel("Valor Total:");
		lblValorTotal.setBounds(12, 153, 81, 17);
		contentPanel.add(lblValorTotal);

		JButton cancelButton = new JButton("Cancelar");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jdFormPedidoVenda.this
						.dispatchEvent(new WindowEvent(jdFormPedidoVenda.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		cancelButton.setForeground(Color.BLACK);
		cancelButton.setBorderPainted(false);
		cancelButton.setBackground(Color.LIGHT_GRAY);
		cancelButton.setActionCommand("Cancel");
		cancelButton.setBounds(480, 227, 86, 27);
		contentPanel.add(cancelButton);

		txCliente = new JTextField();
		txCliente.setBounds(93, 43, 114, 21);
		contentPanel.add(txCliente);
		txCliente.setColumns(10);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(10, 45, 60, 17);
		contentPanel.add(lblCliente);

		JLabel lblNewLabel = new JLabel("Data:");
		lblNewLabel.setBounds(10, 14, 60, 17);
		contentPanel.add(lblNewLabel);

		txtDataPedidoVenda = new JTextField();
		txtDataPedidoVenda.setBounds(93, 12, 114, 21);
		contentPanel.add(txtDataPedidoVenda);
		txtDataPedidoVenda.setColumns(10);

		JButton btnItens = new JButton("Itens");
		btnItens.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (pocessaSalvar() == true) {
					return;
				}

				try {
					new jdListPedidoItemVenda(jdFormPedidoVenda.this.pedidoVendaEditar.getId())
							.run(jdFormPedidoVenda.this.pedidoVendaEditar.getId());
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnItens.setForeground(Color.BLACK);
		btnItens.setBorderPainted(false);
		btnItens.setBackground(Color.ORANGE);
		btnItens.setActionCommand("OK");
		btnItens.setBounds(7, 227, 86, 27);
		contentPanel.add(btnItens);
	}
}
