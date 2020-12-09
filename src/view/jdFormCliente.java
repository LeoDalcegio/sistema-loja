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

import controller.ClienteController;
import dao.ClienteDAOImpl;
import enums.RequestType;
import model.Cliente;

public class jdFormCliente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNome;
	private JTextField txtCpfCnpj;
	private RequestType windowRequestType;
	private Cliente clienteEditar;

	/**
	 * Launch the application.
	 */
	public void run(RequestType requestType, Cliente clienteEditar) {
		try {
			this.clienteEditar = clienteEditar;
			this.windowRequestType = requestType;

			jdFormCliente dialog = new jdFormCliente(requestType, clienteEditar);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public jdFormCliente(RequestType requestType, Cliente clienteEditar) {
		setModal(true);
		this.clienteEditar = clienteEditar;
		this.windowRequestType = requestType;

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		txtNome = new JTextField();
		txtNome.setBounds(95, 12, 114, 21);
		contentPanel.add(txtNome);
		txtNome.setColumns(10);

		JLabel lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setBounds(12, 14, 60, 17);
		contentPanel.add(lblNewLabel);

		JLabel lblCpfcnpj = new JLabel("CPF/CNPJ:");
		lblCpfcnpj.setBounds(12, 45, 78, 17);
		contentPanel.add(lblCpfcnpj);

		txtCpfCnpj = new JTextField();
		txtCpfCnpj.setColumns(10);
		txtCpfCnpj.setBounds(95, 43, 114, 21);

		if (this.clienteEditar != null) {
			txtNome.setText(this.clienteEditar.getNome());
			txtCpfCnpj.setText(this.clienteEditar.getCpf());
		}

		contentPanel.add(txtCpfCnpj);
		{
			JButton okButton = new JButton("Salvar");
			okButton.setForeground(Color.BLACK);
			okButton.setBorderPainted(false);
			okButton.setBackground(Color.GREEN);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// VALIDAR CAMPOS
					if (txtNome.getText().equals("")) {
						txtNome.requestFocus();
						return;
					}

					if (txtCpfCnpj.getText().equals("")) {
						txtCpfCnpj.requestFocus();
						return;
					}

					Cliente cliente = new Cliente();
					cliente.setId(clienteEditar != null ? clienteEditar.getId() : 0);
					cliente.setNome(txtNome.getText());
					cliente.setCpf(txtCpfCnpj.getText());

					ClienteController clienteController = null;

					try {
						ClienteDAOImpl clienteDAOImpl = new ClienteDAOImpl();

						clienteController = new ClienteController(clienteDAOImpl);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (jdFormCliente.this.windowRequestType == RequestType.Create) {
						clienteController.salvaCliente(cliente);
					} else if (jdFormCliente.this.windowRequestType == RequestType.Edit) {
						clienteController.editaCliente(cliente);
					}

					jdFormCliente.this.dispatchEvent(new WindowEvent(jdFormCliente.this, WindowEvent.WINDOW_CLOSING));
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
					jdFormCliente.this.dispatchEvent(new WindowEvent(jdFormCliente.this, WindowEvent.WINDOW_CLOSING));
				}
			});
			cancelButton.setBounds(352, 236, 86, 27);
			contentPanel.add(cancelButton);
			cancelButton.setActionCommand("Cancel");
		}
	}
}
