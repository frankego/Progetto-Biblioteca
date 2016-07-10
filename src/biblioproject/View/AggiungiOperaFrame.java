package biblioproject.View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import biblioproject.Controller.GestioneOpera;
import biblioproject.Model.Biblioteca;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

public class AggiungiOperaFrame extends JFrame {

	private JPanel contentPane;
	private JTextField titoloField;
	private JTextField autoreField;
	private JTextField npagineField;


	public AggiungiOperaFrame(Biblioteca data, DefaultListModel<String> list) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 255, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		titoloField = new JTextField();
		titoloField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		titoloField.setBounds(37, 82, 162, 20);
		contentPane.add(titoloField);
		titoloField.setColumns(10);
		
		autoreField = new JTextField();
		autoreField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		autoreField.setBounds(37, 122, 162, 20);
		contentPane.add(autoreField);
		autoreField.setColumns(10);
		
		npagineField = new JTextField();
		npagineField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		npagineField.setBounds(37, 160, 86, 20);
		contentPane.add(npagineField);
		npagineField.setColumns(10);
		
		JLabel lblAggiungiOpera = new JLabel("Aggiungi Opera");
		lblAggiungiOpera.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblAggiungiOpera.setBounds(49, 21, 152, 24);
		contentPane.add(lblAggiungiOpera);
		
		JLabel lblTitolo = new JLabel("Titolo");
		lblTitolo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTitolo.setBounds(37, 67, 46, 14);
		contentPane.add(lblTitolo);
		
		JLabel lblAutore = new JLabel("Autore");
		lblAutore.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAutore.setBounds(37, 108, 46, 14);
		contentPane.add(lblAutore);
		
		JLabel lblN = new JLabel("n\u00B0 Pagine");
		lblN.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblN.setBounds(37, 147, 46, 14);
		contentPane.add(lblN);
		
		Action act = new GestioneOpera(this, data, list, titoloField, autoreField, npagineField);
		JButton btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAggiungi.setBounds(70, 203, 97, 35);
		contentPane.add(btnAggiungi);
		btnAggiungi.addActionListener(act);
	}
}
