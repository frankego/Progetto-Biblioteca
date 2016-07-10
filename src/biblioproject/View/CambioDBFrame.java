package biblioproject.View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class CambioDBFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CambioDBFrame frame = new CambioDBFrame();
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
	public CambioDBFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblServer = new JLabel("Server");
		lblServer.setBounds(20, 25, 46, 14);
		contentPane.add(lblServer);
		
		textField = new JTextField();
		textField.setBounds(20, 42, 383, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblUser = new JLabel("User");
		lblUser.setBounds(20, 73, 46, 14);
		contentPane.add(lblUser);
		
		textField_1 = new JTextField();
		textField_1.setBounds(20, 89, 178, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblPass = new JLabel("Pass");
		lblPass.setBounds(218, 73, 46, 14);
		contentPane.add(lblPass);
		
		textField_2 = new JTextField();
		textField_2.setBounds(218, 89, 178, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnModifica = new JButton("Aggiorna");
		btnModifica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					FileWriter writer = new FileWriter("db.txt");
					writer.write(textField.getText());
		            writer.write("\r\n");   
		            writer.write(textField_1.getText());
		            writer.write("\r\n");   
		            writer.write(textField_2.getText());
		            writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnModifica.setBounds(87, 133, 111, 32);
		contentPane.add(btnModifica);
		
		JButton btnCreaTabelle = new JButton("Crea Tabelle");
		btnCreaTabelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					FileReader reader = new FileReader("dump.sql");
		            BufferedReader bufferedReader = new BufferedReader(reader);
		            String line;
		            String dump = "";
		            while ((line = bufferedReader.readLine()) != null) {
		            	dump+=line;
		            }
		            reader.close();
					Connection con=DriverManager.getConnection(textField.getText(), textField_1.getText() , textField_2.getText());
					PreparedStatement pst=con.prepareStatement(dump);
					pst.executeUpdate();
				} catch (IOException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnCreaTabelle.setBounds(218, 133, 111, 32);
		contentPane.add(btnCreaTabelle);
	}
}
