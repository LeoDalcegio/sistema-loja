package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.UsuarioController;
import dao.UsuarioDAOImpl;
import model.Usuario;

public class JfLogin extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private UsuarioController usuarioController;
	private JPasswordField passwordField;
	private final JButton btnLogin = new JButton("Login");

	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JfLogin frame = new JfLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void processaLogin(String login, char[] senha) throws ClassNotFoundException, SQLException {
		if (login != "" && senha != null) {
			Usuario usuarioModel = new Usuario();
			usuarioModel.setLogin(login);
			usuarioModel.setSenha(senha);

			UsuarioDAOImpl usuarioDAOImpl = new UsuarioDAOImpl();

			this.usuarioController.iniciaDadosUsuario(usuarioModel, usuarioDAOImpl);

			Usuario usuarioLogin = this.usuarioController.getUsuarioLogado();

			if (usuarioLogin == null) {
				JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos");
				return;
			}

			new jfMenuPrincipal().run();

			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
	}

	/**
	 * Create the frame.
	 */
	public JfLogin() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.usuarioController = UsuarioController.getInstance();

		txtUsuario = new JTextField();
		txtUsuario.setBounds(144, 101, 170, 29);
		txtUsuario.setToolTipText("Usuário");
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(144, 134, 170, 29);
		passwordField.setToolTipText("Senha");
		contentPane.add(passwordField);

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					processaLogin(txtUsuario.getText(), passwordField.getPassword());
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btnLogin.setBounds(144, 168, 170, 35);
		contentPane.add(btnLogin);

		JLabel lblNewLabel = new JLabel("Usuário:");
		lblNewLabel.setBounds(79, 107, 60, 17);
		contentPane.add(lblNewLabel);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(80, 140, 60, 17);
		contentPane.add(lblSenha);
	}
}
