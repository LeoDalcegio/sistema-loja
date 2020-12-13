package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Panel;
import java.awt.SystemColor;
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
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

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
		if (!login.equals("") && senha != null) {
			Usuario usuarioModel = new Usuario();
			usuarioModel.setLogin(login);
			usuarioModel.setSenha(senha);

			UsuarioDAOImpl usuarioDAOImpl = new UsuarioDAOImpl();

			this.usuarioController.iniciaDadosUsuario(usuarioDAOImpl);

			Usuario usuarioLogin = this.usuarioController.realizaLoginUsuario(usuarioModel);

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
		setBounds(100, 100, 551, 352);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.usuarioController = UsuarioController.getInstance();

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(SystemColor.controlDkShadow));
		panel_2.setBackground(UIManager.getColor("FormattedTextField.inactiveBackground"));
		panel_2.setBounds(104, 75, 335, 191);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(91, 51, 170, 29);
		panel_2.add(txtUsuario);
		txtUsuario.setToolTipText("Usuário");
		txtUsuario.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(91, 84, 170, 29);
		panel_2.add(passwordField);
		passwordField.setToolTipText("Senha");
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(Color.DARK_GRAY);
		btnLogin.setBounds(91, 118, 170, 35);
		panel_2.add(btnLogin);

		JLabel lblNewLabel = new JLabel("Usuário:");
		lblNewLabel.setBounds(22, 60, 60, 17);
		panel_2.add(lblNewLabel);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(22, 94, 60, 17);
		panel_2.add(lblSenha);

		Panel panel_1 = new Panel();
		panel_1.setBackground(UIManager.getColor("MenuItem.acceleratorForeground"));
		panel_1.setBounds(0, 0, 543, 115);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

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
	}
}
