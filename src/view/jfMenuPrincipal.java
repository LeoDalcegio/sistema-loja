package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class jfMenuPrincipal extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					jfMenuPrincipal frame = new jfMenuPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public jfMenuPrincipal() {
		setTitle("Sistema");
		setBackground(UIManager.getColor("Button.select"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 752, 434);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("Button.select"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel contentPane_1 = new JPanel();
		contentPane_1.setLayout(null);
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane_1.setBackground(UIManager.getColor("Button.select"));
		contentPane.add(contentPane_1, BorderLayout.CENTER);

		JButton btnUsuarios = new JButton("  Usuários");
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					new jdListUsuario().run();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnUsuarios.setIcon(new ImageIcon(jfMenuPrincipal.class.getResource("/assets/grupo-de-usuarios.png")));
		btnUsuarios.setFont(new Font("Dialog", Font.BOLD, 16));
		btnUsuarios.setBackground(Color.WHITE);
		btnUsuarios.setBounds(12, 12, 216, 108);
		contentPane_1.add(btnUsuarios);

		JButton btnClientes = new JButton("  Clientes");
		btnClientes.setIcon(new ImageIcon(jfMenuPrincipal.class.getResource("/assets/cliente.png")));
		btnClientes.setFont(new Font("Dialog", Font.BOLD, 16));
		btnClientes.setBackground(Color.WHITE);
		btnClientes.setBounds(240, 12, 216, 108);
		contentPane_1.add(btnClientes);

		JButton btnProdutos = new JButton("  Produtos");
		btnProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					new jdListProduto().run();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btnProdutos.setIcon(new ImageIcon(jfMenuPrincipal.class.getResource("/assets/produtos.png")));
		btnProdutos.setFont(new Font("Dialog", Font.BOLD, 16));
		btnProdutos.setBackground(Color.WHITE);
		btnProdutos.setBounds(468, 12, 216, 108);
		contentPane_1.add(btnProdutos);
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					new jdListCliente().run();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
