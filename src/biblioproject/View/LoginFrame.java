package biblioproject.View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import biblioproject.Controller.GestioneUser;
import biblioproject.Model.Biblioteca;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = -2190644274278007540L;
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField userField;
	Biblioteca data = new Biblioteca();

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginFrame() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 289, 287);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(36, 139, 206, 20);
		contentPane.add(passwordField);
		
		userField = new JTextField();
		userField.setBounds(36, 101, 206, 20);
		contentPane.add(userField);
		userField.setColumns(10);
		
		JLabel lblNomeUtente = new JLabel("Nome Utente");
		lblNomeUtente.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNomeUtente.setBounds(36, 87, 81, 14);
		contentPane.add(lblNomeUtente);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPassword.setBounds(36, 125, 81, 14);
		contentPane.add(lblPassword);
		
		Action act = new GestioneUser( this, data, userField, passwordField, null);
		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnLogin.setBounds(144, 182, 98, 29);
		btnLogin.addActionListener(act);
		contentPane.add(btnLogin);
		
		JLabel lblBiblioproject = new JLabel("BiblioProject");
		lblBiblioproject.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblBiblioproject.setBounds(43, 11, 180, 56);
		contentPane.add(lblBiblioproject);
		
		JButton btnNewButton = new JButton("Registrati");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setBounds(36, 182, 98, 29);
		btnNewButton.addActionListener(act);
		contentPane.add(btnNewButton);
	}

}
