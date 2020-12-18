package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.PedidoVendaController;
import controller.UsuarioController;
import dao.PedidoVendaDAOImpl;
import enums.RequestType;
import enums.TipoUsuario;
import model.PedidoVenda;

public class jdListPedidoVenda extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private PedidoVendaController pedidoVendaController;

	/**
	 * Create the dialog.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public jdListPedidoVenda() throws ClassNotFoundException, SQLException {
		PedidoVendaDAOImpl pedidoVendaDAOImpl = null;

		pedidoVendaDAOImpl = new PedidoVendaDAOImpl();

		this.pedidoVendaController = new PedidoVendaController(pedidoVendaDAOImpl);

		setResizable(false);
		getContentPane().setForeground(UIManager.getColor("DesktopIcon.background"));
		setTitle("Pedidos de Compra");
		getContentPane().setBackground(UIManager.getColor("Desktop.background"));
		setBackground(Color.WHITE);
		setBounds(100, 100, 789, 443);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBackground(UIManager.getColor("Desktop.background"));
			scrollPane.setBounds(0, 0, 789, 383);
			contentPanel.add(scrollPane);
			{
				table = new JTable();
				table.setGridColor(Color.WHITE);
				table.setAutoCreateRowSorter(true);
				table.setShowHorizontalLines(false);
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				table.setShowVerticalLines(false);
				table.setBackground(UIManager.getColor("Desktop.background"));
				table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Id", "Cliente", "Data" }) {
					boolean[] columnEditables = new boolean[] { false, false, false };

					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
				table.getColumnModel().getColumn(0).setPreferredWidth(70);
				table.getColumnModel().getColumn(0).setMaxWidth(70);
				table.getColumnModel().getColumn(1).setPreferredWidth(106);
				table.getColumnModel().getColumn(2).setPreferredWidth(94);

				table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
				table.getTableHeader().setBackground(new Color(32, 163, 203));
				table.getTableHeader().setForeground(new Color(255, 255, 255));
				table.getTableHeader().setOpaque(false);
				table.setRowHeight(25);
				table.setIntercellSpacing(new Dimension(0, 0));
				table.setSelectionBackground(new Color(171, 250, 169));

				model = (DefaultTableModel) table.getModel();

				this.montaList();

				scrollPane.setViewportView(table);
			}
		}

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setForeground(Color.BLACK);
		btnExcluir.setBackground(Color.RED);
		btnExcluir.setBorderPainted(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectionModel().isSelectionEmpty()) {
					return;
				}

				int column = 0;
				int row = table.getSelectedRow();
				int id = Integer.parseInt(table.getModel().getValueAt(row, column).toString());

				pedidoVendaController.excluiPedidoVenda(id);

				try {
					montaList();
				} catch (ClassNotFoundException | SQLException exception) {
					// TODO Auto-generated catch block
					exception.printStackTrace();
				}

			}
		});
		btnExcluir.setBounds(673, 386, 105, 27);
		contentPanel.add(btnExcluir);

		JButton btnEditar = new JButton("Editar");
		btnEditar.setForeground(Color.BLACK);
		btnEditar.setBorderPainted(false);
		btnEditar.setBackground(Color.YELLOW);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (table.getSelectionModel().isSelectionEmpty()) {
					return;
				}

				int row = table.getSelectedRow();
				int pedidoVendaId = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());

				PedidoVenda pedidoVenda = pedidoVendaController.getPedidoVendaById(pedidoVendaId);

				new jdFormPedidoVenda(RequestType.Edit, pedidoVenda);

				try {
					montaList();
				} catch (ClassNotFoundException | SQLException exception) {
					// TODO Auto-generated catch block
					exception.printStackTrace();
				}
			}
		});
		btnEditar.setBounds(556, 386, 105, 27);
		contentPanel.add(btnEditar);

		JButton btnIncluir = new JButton("Incluir");
		btnIncluir.setFont(new Font("Dialog", Font.BOLD, 12));
		btnIncluir.setForeground(Color.BLACK);
		btnIncluir.setBorderPainted(false);
		btnIncluir.setBackground(Color.GREEN);

		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new jdFormPedidoVenda(RequestType.Create, null);

				try {
					montaList();
				} catch (ClassNotFoundException | SQLException exception) {
					// TODO Auto-generated catch block
					exception.printStackTrace();
				}
			}
		});
		btnIncluir.setBounds(439, 386, 105, 27);
		contentPanel.add(btnIncluir);

		JButton btnVisualizar = new JButton("Itens");
		btnVisualizar.setVisible(false);
		btnVisualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectionModel().isSelectionEmpty()) {
					return;
				}

				int row = table.getSelectedRow();
				int pedidoVendaId = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());

				try {
					new jdListPedidoItemVenda(pedidoVendaId);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnVisualizar.setForeground(Color.BLACK);
		btnVisualizar.setFont(new Font("Dialog", Font.BOLD, 12));
		btnVisualizar.setBorderPainted(false);
		btnVisualizar.setBackground(Color.ORANGE);
		btnVisualizar.setBounds(4, 386, 105, 27);
		contentPanel.add(btnVisualizar);

		UsuarioController usuarioController = UsuarioController.getInstance();

		TipoUsuario tipoUsuario = usuarioController.getUsuarioLogado().getTipo();

		if (tipoUsuario != TipoUsuario.Administrador) {
			btnEditar.setVisible(false);
			btnExcluir.setVisible(false);
			btnIncluir.setBounds(btnExcluir.getBounds());
			btnVisualizar.setVisible(true);
		}

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	private void montaList() throws ClassNotFoundException, SQLException {
		List<PedidoVenda> pedidos = this.pedidoVendaController.getAllPedidosVenda();

		int rowCount = model.getRowCount();

		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		for (PedidoVenda pedido : pedidos) {
			Object[] linha = { pedido.getId(), pedido.getCliente().getNome(),
					formatter.format(pedido.getDataDaVenda()) };

			model.addRow(linha);
		}
	}
}
