package biblioproject.View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import biblioproject.Controller.GestioneUser;
import biblioproject.Model.Biblioteca;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.Action;
import javax.swing.JButton;
import java.awt.Font;

public class RegistrazioneFrame extends JFrame {

	private JPanel contentPane;
	private JTextField userField;
	private JTextField passField;
	private JTextField emailField;


	public RegistrazioneFrame(Biblioteca data) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 265, 314);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		userField = new JTextField();
		userField.setBounds(35, 89, 181, 20);
		contentPane.add(userField);
		userField.setColumns(10);
		
		passField = new JTextField();
		passField.setBounds(35, 130, 181, 20);
		contentPane.add(passField);
		passField.setColumns(10);
		
		emailField = new JTextField();
		emailField.setBounds(35, 175, 181, 20);
		contentPane.add(emailField);
		emailField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUsername.setBounds(33, 72, 73, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPassword.setBounds(35, 115, 46, 14);
		contentPane.add(lblPassword);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEmail.setBounds(35, 161, 46, 14);
		contentPane.add(lblEmail);
		
		Action act = new GestioneUser(this, data, userField, passField, emailField);
		JButton btnRegistrati = new JButton("Conferma");
		btnRegistrati.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRegistrati.setBounds(75, 223, 105, 31);
		btnRegistrati.addActionListener(act);
		contentPane.add(btnRegistrati);
		
		JLabel lblRegistrazione = new JLabel("Registrazione");
		lblRegistrazione.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblRegistrazione.setBounds(62, 11, 129, 50);
		contentPane.add(lblRegistrazione);
	}

}
