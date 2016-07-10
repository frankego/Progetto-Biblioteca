package biblioproject.View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import biblioproject.Controller.GestioneAction;
import biblioproject.Model.Biblioteca;
import biblioproject.Model.User;

import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;

public class CronologiaFrame extends JFrame {

	private JPanel contentPane;
	private JTable table_1;

	public CronologiaFrame(Biblioteca data, User user) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 577, 224);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
	
		
		table_1 = new JTable();
		table_1.setBounds(10, 32, 541, 163);
		DefaultTableModel model = new DefaultTableModel(
				new Object[][] {
					
				},
				new String[] {
					"User", "Opera", "Pagina", "Tipo", "Status"
				}
			);
		table_1.setModel(model);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(109);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(151);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(57);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(128);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(91);
		
		JScrollPane scroll = new JScrollPane(table_1);
		scroll.setPreferredSize(new Dimension(541,163));
		contentPane.add(scroll);
		
		GestioneAction act = new GestioneAction(data, user, model);
		act.retrieve();
	}
}
