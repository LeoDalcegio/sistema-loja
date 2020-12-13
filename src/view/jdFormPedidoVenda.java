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
import javax.swing.JTabbedPane;
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

	/**
	 * Create the dialog.
	 */
	public jdFormPedidoVenda(RequestType requestType, PedidoVenda pedidoVendaEditar) {
		setModal(true);
		this.pedidoVendaEditar = pedidoVendaEditar;
		this.windowRequestType = requestType;

		setBounds(100, 100, 699, 418);
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
					// VALIDAR CAMPOS
					if (txtDataPedidoVenda.getText().equals("")) {
						txtDataPedidoVenda.requestFocus();
						return;
					}

					if (txtCliente.getText().equals("")) {
						txtCliente.requestFocus();
						return;
					}

					PedidoVenda pedidoVenda = new PedidoVenda();
					pedidoVenda.setId(pedidoVenda != null ? pedidoVenda.getId() : 0);
					// pedidoVenda.setClienteId(txt);

					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

					try {
						pedidoVenda.setDataDaVenda(formatter.parse(txtDataPedidoVenda.getText()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
						pedidoVendaController.salvaPedidoVenda(pedidoVenda);
					} else if (jdFormPedidoVenda.this.windowRequestType == RequestType.Edit) {
						pedidoVendaController.editaPedidoVenda(pedidoVenda);
					}

					jdFormPedidoVenda.this
							.dispatchEvent(new WindowEvent(jdFormPedidoVenda.this, WindowEvent.WINDOW_CLOSING));
				}
			});

			okButton.setBounds(504, 357, 86, 27);
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
		txtValorPedido.setBounds(109, 363, 114, 21);
		contentPanel.add(txtValorPedido);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, -1, 691, 353);
		contentPanel.add(tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Pedido", null, panel, null);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Data:");
		lblNewLabel.setBounds(12, 14, 60, 17);
		panel.add(lblNewLabel);

		txtDataPedidoVenda = new JTextField();
		txtDataPedidoVenda.setBounds(95, 12, 114, 21);
		panel.add(txtDataPedidoVenda);
		txtDataPedidoVenda.setColumns(10);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(12, 45, 60, 17);
		panel.add(lblCliente);

		txCliente = new JTextField();
		txCliente.setColumns(10);
		txCliente.setBounds(95, 43, 114, 21);
		panel.add(txCliente);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Itens", null, panel_1, null);

		JLabel lblValorTotal = new JLabel("Valor Total:");
		lblValorTotal.setBounds(10, 365, 81, 17);
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
		cancelButton.setBounds(597, 357, 86, 27);
		contentPanel.add(cancelButton);
	}
}
