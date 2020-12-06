package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.ClienteController;
import dao.ClienteDAOImpl;
import enums.RequestType;
import model.Cliente;

public class jdListCliente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public void run() {
		try {
			jdListCliente dialog = new jdListCliente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public jdListCliente() throws ClassNotFoundException, SQLException {
		setBounds(100, 100, 781, 443);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(0, 0, 781, 383);
			contentPanel.add(scrollPane);
			{
				table = new JTable();
				table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Id", "Nome", "CPF/CNPJ" }));
				table.getColumnModel().getColumn(0).setPreferredWidth(70);
				table.getColumnModel().getColumn(0).setMaxWidth(70);

				model = (DefaultTableModel) table.getModel();

				ClienteDAOImpl clienteDAOImpl = new ClienteDAOImpl();

				ClienteController clienteController = new ClienteController(clienteDAOImpl);

				List<Cliente> clientes = clienteController.getAllClientes();

				for (Cliente cliente : clientes) {
					Object[] linha = { cliente.getId(), cliente.getNome(), cliente.getCpf() };

					model.addRow(linha);
				}

				scrollPane.setViewportView(table);
			}
		}

		JButton btnNewButton = new JButton("Excluir");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int column = 0;
				int row = table.getSelectedRow();
				int id = Integer.parseInt(table.getModel().getValueAt(row, column).toString());

				ClienteController clienteController = null;

				ClienteDAOImpl clienteDAOImpl = null;

				try {
					clienteDAOImpl = new ClienteDAOImpl();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				clienteController = new ClienteController(clienteDAOImpl);
				clienteController.excluiCliente(id);

			}
		});
		btnNewButton.setBounds(673, 386, 105, 27);
		contentPanel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Editar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new jdFormCliente().run(RequestType.Edit, null);
			}
		});
		btnNewButton_1.setBounds(556, 386, 105, 27);
		contentPanel.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Incluir");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new jdFormCliente().run(RequestType.Create, null);
			}
		});
		btnNewButton_2.setBounds(439, 386, 105, 27);
		contentPanel.add(btnNewButton_2);
	}
}
