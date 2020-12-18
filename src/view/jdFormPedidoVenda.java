package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.ClienteController;
import controller.PedidoVendaController;
import dao.ClienteDAOImpl;
import dao.PedidoVendaDAOImpl;
import enums.RequestType;
import model.Cliente;
import model.PedidoVenda;
import util.DefaultComboItem;

public class jdFormPedidoVenda extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtDataPedidoVenda;
	private RequestType windowRequestType;
	private PedidoVenda pedidoVendaEditar;
	private JComboBox<String> comboBox;
	private HashMap<String, Integer> map;
	private PedidoVendaController pedidoVendaController;

	private boolean pocessaSalvar() {
		if (txtDataPedidoVenda.getText().equals("")) {
			txtDataPedidoVenda.requestFocus();
			return true;
		}

		PedidoVenda pedidoVenda = new PedidoVenda();
		pedidoVenda.setId(this.pedidoVendaEditar != null ? this.pedidoVendaEditar.getId() : 0);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {
			pedidoVenda.setDataDaVenda(formatter.parse(txtDataPedidoVenda.getText()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int clienteId = (int) map.get(comboBox.getSelectedItem().toString());
		pedidoVenda.setClienteId(clienteId);

		if (jdFormPedidoVenda.this.windowRequestType == RequestType.Create && this.pedidoVendaEditar == null) {
			this.pedidoVendaEditar = pedidoVendaController.salvaPedidoVenda(pedidoVenda);
		} else if (jdFormPedidoVenda.this.windowRequestType == RequestType.Edit) {
			this.pedidoVendaEditar = pedidoVendaController.editaPedidoVenda(pedidoVenda);
		}

		return false;
	}

	/**
	 * Create the dialog.
	 */
	public jdFormPedidoVenda(RequestType requestType, PedidoVenda pedidoVendaEditar) {
		setModalityType(ModalityType.MODELESS);
		setModal(false);

		this.pedidoVendaEditar = pedidoVendaEditar;
		this.windowRequestType = requestType;

		PedidoVendaDAOImpl pedidoVendaDAOImpl = new PedidoVendaDAOImpl();

		pedidoVendaController = new PedidoVendaController(pedidoVendaDAOImpl);

		setBounds(100, 100, 579, 286);
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
					new jdListPedidoItemVenda(jdFormPedidoVenda.this.pedidoVendaEditar.getId());

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

		comboBox = new JComboBox<String>();
		comboBox.setBackground(Color.WHITE);
		comboBox.setBounds(93, 40, 219, 21);
		contentPanel.add(comboBox);

		this.BindCompo();

		if (this.pedidoVendaEditar != null) {

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

			txtDataPedidoVenda.setText(formatter.format(this.pedidoVendaEditar.getDataDaVenda()));

			comboBox.setSelectedItem(this.pedidoVendaEditar.getCliente().getNome());
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

		ClienteDAOImpl clienteDAOImpl = new ClienteDAOImpl();

		ClienteController clienteController = new ClienteController(clienteDAOImpl);

		List<Cliente> clientes = clienteController.getAllClientes();

		DefaultComboItem cmi;

		for (Cliente cliente : clientes) {
			cmi = new DefaultComboItem(cliente.getId(), cliente.getNome());
			map.put(cmi.getDescricao(), cmi.getId());
		}

		return map;
	}
}
