package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.PedidoItemVendaController;
import controller.ProdutoController;
import controller.UsuarioController;
import dao.PedidoItemVendaDAOImpl;
import dao.ProdutoDAOImpl;
import enums.RequestType;
import enums.TipoUsuario;
import model.PedidoItemVenda;
import model.Produto;

public class jdListPedidoItemVenda extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private int idPedido;
	private PedidoItemVendaController pedidoItemVendaController;
	private JTextField txtCodigoBarras;

	/**
	 * Create the dialog.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public jdListPedidoItemVenda(int idPedido) throws ClassNotFoundException, SQLException {
		this.idPedido = idPedido;

		PedidoItemVendaDAOImpl pedidoItemVendaDAOImpl = null;

		pedidoItemVendaDAOImpl = new PedidoItemVendaDAOImpl();

		this.pedidoItemVendaController = new PedidoItemVendaController(pedidoItemVendaDAOImpl);

		setResizable(false);
		getContentPane().setForeground(UIManager.getColor("DesktopIcon.background"));
		setTitle("Itens do Pedido de Venda");
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

				table.setModel(new DefaultTableModel(new Object[][] {},
						new String[] { "Id", "Pedido Venda Id", "Produto", "Quantidade", "Valor Unit\u00E1rio" }) {
					boolean[] columnEditables = new boolean[] { false, false, false, true, false };

					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});

				table.getColumnModel().getColumn(0).setPreferredWidth(70);
				table.getColumnModel().getColumn(0).setMaxWidth(70);
				table.getColumnModel().getColumn(2).setPreferredWidth(94);
				table.getColumnModel().getColumn(4).setPreferredWidth(90);

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

				pedidoItemVendaController.excluiPedidoItemVenda(id);

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
				int pedidoItemVendaId = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());

				PedidoItemVenda pedidoItemVenda = pedidoItemVendaController.getPedidoItemVendaById(pedidoItemVendaId);

				new jdFormPedidoItemVenda(RequestType.Edit, pedidoItemVenda, idPedido);

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
				new jdFormPedidoItemVenda(RequestType.Create, null, idPedido);

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

		JLabel lblCpfcnpj_2_1 = new JLabel("Código de barras:");
		lblCpfcnpj_2_1.setBounds(10, 393, 114, 17);
		contentPanel.add(lblCpfcnpj_2_1);

		txtCodigoBarras = new JTextField();
		txtCodigoBarras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (txtCodigoBarras.getText().equals("")) {
					return;
				}

				ProdutoDAOImpl produtoDaoImpl = new ProdutoDAOImpl();

				ProdutoController produtoController = new ProdutoController(produtoDaoImpl);

				Produto produto = produtoController.getProdutoByCodigoDeBarras(txtCodigoBarras.getText());

				if (produto.getQuantidadeEmEstoque() >= 1) {
					produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() - 1);

					produtoController.editaProduto(produto);
				} else {
					JOptionPane.showMessageDialog(null, "Produto não disponível em estoque");

					return;
				}

				PedidoItemVenda pedidoItemVenda = new PedidoItemVenda();
				pedidoItemVenda.setId(0);
				pedidoItemVenda.setPedidoVendaId(idPedido);
				pedidoItemVenda.setQuantidade((float) 1);
				pedidoItemVenda.setValorUnitario(produto.getPrecoPadrao());

				pedidoItemVenda.setProdutoId(produto.getId());

				pedidoItemVendaController.salvaPedidoItemVenda(pedidoItemVenda);

				try {
					montaList();
				} catch (ClassNotFoundException | SQLException exception) {
					// TODO Auto-generated catch block
					exception.printStackTrace();
				}
			}
		});
		txtCodigoBarras.setColumns(10);
		txtCodigoBarras.setBounds(128, 390, 114, 21);
		contentPanel.add(txtCodigoBarras);
		UsuarioController usuarioController = UsuarioController.getInstance();

		TipoUsuario tipoUsuario = usuarioController.getUsuarioLogado().getTipo();

		if (tipoUsuario != TipoUsuario.Administrador) {
			btnEditar.setVisible(false);
			btnExcluir.setVisible(false);
			btnIncluir.setBounds(btnExcluir.getBounds());
		}

		this.toFront();

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	private void montaList() throws ClassNotFoundException, SQLException {
		List<PedidoItemVenda> pedidosItem = this.pedidoItemVendaController.getAllPedidosItemVenda(this.idPedido);

		int rowCount = model.getRowCount();

		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}

		for (PedidoItemVenda pedidoItem : pedidosItem) {
			Object[] linha = { pedidoItem.getId(), pedidoItem.getPedidoVendaId(),
					pedidoItem.getProduto().getCodigoProduto() + " - " + pedidoItem.getProduto().getDescricaoProduto(),
					pedidoItem.getQuantidade(), pedidoItem.getValorUnitario() };

			model.addRow(linha);
		}
	}
}
