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

import controller.ProdutoController;
import dao.ProdutoDAOImpl;
import enums.RequestType;
import model.Produto;

public class jdFormProduto extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCodigoProduto;
	private JTextField txtDescricao;
	private RequestType windowRequestType;
	private Produto produtoEditar;
	private JTextField txtQuantidadeEstoque;
	private JTextField txtPrecoPadrao;

	/**
	 * Create the dialog.
	 */
	public jdFormProduto(RequestType requestType, Produto produtoEditar) {
		setModal(true);
		this.produtoEditar = produtoEditar;
		this.windowRequestType = requestType;

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		txtCodigoProduto = new JTextField();
		txtCodigoProduto.setBounds(178, 14, 114, 21);
		contentPanel.add(txtCodigoProduto);
		txtCodigoProduto.setColumns(10);

		JLabel lblNewLabel = new JLabel("Código:");
		lblNewLabel.setBounds(12, 16, 60, 17);
		contentPanel.add(lblNewLabel);

		JLabel lblCpfcnpj = new JLabel("Descrição:");
		lblCpfcnpj.setBounds(12, 49, 78, 17);
		contentPanel.add(lblCpfcnpj);

		txtDescricao = new JTextField();
		txtDescricao.setColumns(10);
		txtDescricao.setBounds(178, 47, 114, 21);

		txtQuantidadeEstoque = new JTextField();
		txtQuantidadeEstoque.setColumns(10);
		txtQuantidadeEstoque.setBounds(178, 79, 114, 21);
		contentPanel.add(txtQuantidadeEstoque);

		JLabel lblQuantidadeEmEstoque = new JLabel("Quantidade em estoque:");
		lblQuantidadeEmEstoque.setBounds(12, 81, 162, 17);
		contentPanel.add(lblQuantidadeEmEstoque);

		txtPrecoPadrao = new JTextField();
		txtPrecoPadrao.setColumns(10);
		txtPrecoPadrao.setBounds(178, 112, 114, 21);
		contentPanel.add(txtPrecoPadrao);

		JLabel lblCpfcnpj_2 = new JLabel("Preço padrão:");
		lblCpfcnpj_2.setBounds(12, 114, 125, 17);
		contentPanel.add(lblCpfcnpj_2);

		if (this.produtoEditar != null) {
			txtCodigoProduto.setText(this.produtoEditar.getCodigoProduto());
			txtDescricao.setText(this.produtoEditar.getDescricaoProduto());
			txtPrecoPadrao.setText(this.produtoEditar.getPrecoPadrao().toString());
			txtQuantidadeEstoque.setText(String.valueOf(this.produtoEditar.getQuantidadeEmEstoque()));

		}

		contentPanel.add(txtDescricao);
		{
			JButton okButton = new JButton("Salvar");
			okButton.setForeground(Color.BLACK);
			okButton.setBorderPainted(false);
			okButton.setBackground(Color.GREEN);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// VALIDAR CAMPOS
					if (txtCodigoProduto.getText().equals("")) {
						txtCodigoProduto.requestFocus();
						return;
					}

					if (txtDescricao.getText().equals("")) {
						txtDescricao.requestFocus();
						return;
					}

					Produto produto = new Produto();
					produto.setId(produtoEditar != null ? produtoEditar.getId() : 0);
					produto.setCodigoProduto(txtCodigoProduto.getText());
					produto.setDescricaoProduto(txtDescricao.getText());
					produto.setPrecoPadrao(Float.parseFloat(txtPrecoPadrao.getText()));
					produto.setQuantidadeEmEstoque(Float.parseFloat(txtQuantidadeEstoque.getText()));

					ProdutoController produtoController = null;

					try {
						ProdutoDAOImpl produtoDAOImpl = new ProdutoDAOImpl();

						produtoController = new ProdutoController(produtoDAOImpl);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (jdFormProduto.this.windowRequestType == RequestType.Create) {
						produtoController.salvaPrduto(produto);
					} else if (jdFormProduto.this.windowRequestType == RequestType.Edit) {
						produtoController.editaProduto(produto);
					}

					jdFormProduto.this.dispatchEvent(new WindowEvent(jdFormProduto.this, WindowEvent.WINDOW_CLOSING));
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
					jdFormProduto.this.dispatchEvent(new WindowEvent(jdFormProduto.this, WindowEvent.WINDOW_CLOSING));
				}
			});
			cancelButton.setBounds(352, 236, 86, 27);
			contentPanel.add(cancelButton);
			cancelButton.setActionCommand("Cancel");

			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.setVisible(true);

		}
	}
}