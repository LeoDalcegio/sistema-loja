package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.UsuarioController;
import enums.RequestType;
import enums.TipoUsuario;
import model.Usuario;

public class jdFormUsuario extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtLogin;
	private JTextField txtSenha;
	private RequestType windowRequestType;
	private Usuario usuarioEditar;

	/**
	 * Create the dialog.
	 */
	public jdFormUsuario(RequestType requestType, Usuario usuarioEditar) {
		setModal(true);
		this.usuarioEditar = usuarioEditar;
		this.windowRequestType = requestType;

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		txtLogin = new JTextField();
		txtLogin.setBounds(178, 14, 114, 21);
		contentPanel.add(txtLogin);
		txtLogin.setColumns(10);

		JLabel lblNewLabel = new JLabel("Login:");
		lblNewLabel.setBounds(12, 16, 60, 17);
		contentPanel.add(lblNewLabel);

		JLabel lblCpfcnpj = new JLabel("Senha:");
		lblCpfcnpj.setBounds(12, 49, 78, 17);
		contentPanel.add(lblCpfcnpj);

		JComboBox comboTipoUsuario = new JComboBox();
		comboTipoUsuario.setModel(new DefaultComboBoxModel(TipoUsuario.values()));
		comboTipoUsuario.setBounds(178, 79, 114, 21);
		contentPanel.add(comboTipoUsuario);

		txtSenha = new JTextField();
		txtSenha.setColumns(10);
		txtSenha.setBounds(178, 47, 114, 21);

		if (this.usuarioEditar != null) {
			txtLogin.setText(this.usuarioEditar.getLogin());
		}

		contentPanel.add(txtSenha);
		{
			JButton okButton = new JButton("Salvar");
			okButton.setForeground(Color.BLACK);
			okButton.setBorderPainted(false);
			okButton.setBackground(Color.GREEN);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// VALIDAR CAMPOS
					if (txtLogin.getText().equals("")) {
						txtLogin.requestFocus();
						return;
					}

					if (txtSenha.getText().equals("")) {
						txtSenha.requestFocus();
						return;
					}

					Usuario usuario = new Usuario();
					usuario.setId(usuarioEditar != null ? usuarioEditar.getId() : 0);
					usuario.setLogin(txtLogin.getText());
					usuario.setSenha(txtSenha.getText().toCharArray());
					usuario.setTipo(TipoUsuario.valueOf(comboTipoUsuario.getSelectedItem().toString()));

					UsuarioController usuarioController = UsuarioController.getInstance();

					if (jdFormUsuario.this.windowRequestType == RequestType.Create) {
						usuarioController.salvaUsuario(usuario);
					} else if (jdFormUsuario.this.windowRequestType == RequestType.Edit) {
						usuarioController.editaUsuario(usuario);
					}

					jdFormUsuario.this.dispatchEvent(new WindowEvent(jdFormUsuario.this, WindowEvent.WINDOW_CLOSING));
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
					jdFormUsuario.this.dispatchEvent(new WindowEvent(jdFormUsuario.this, WindowEvent.WINDOW_CLOSING));
				}
			});
			cancelButton.setBounds(352, 236, 86, 27);
			contentPanel.add(cancelButton);
			cancelButton.setActionCommand("Cancel");
		}

		JLabel lblQuantidadeEmEstoque = new JLabel("Tipo:");
		lblQuantidadeEmEstoque.setBounds(12, 81, 60, 17);
		contentPanel.add(lblQuantidadeEmEstoque);

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
}
