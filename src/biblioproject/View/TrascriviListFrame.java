package biblioproject.View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import biblioproject.Controller.GestioneAction;
import biblioproject.Controller.GestionePagina;
import biblioproject.Model.Biblioteca;
import biblioproject.Model.Opera;
import biblioproject.Model.Page;
import biblioproject.Model.User;

import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;

public class TrascriviListFrame extends JFrame {

	private JPanel contentPane;
	private JTable table_1;

	public TrascriviListFrame(Biblioteca data, User user) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 442, 252);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
	
		
		table_1 = new JTable();
		table_1.setBounds(10, 32, 400, 163);
		DefaultTableModel model = new DefaultTableModel(
				new Object[][] {
					
				},
				new String[] {
					"Opera", "Pagina"
				}
			);
		table_1.setModel(model);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(151);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(50);
		
		for(Page p : data.getPageList()){
			if(p.getStatus() == 1){
			Opera next = new Opera(p.getOperaID());
			model.addRow(new Object[]{ next.getTitle(), p.getNumber() });
			}
		}
		
		JScrollPane scroll = new JScrollPane(table_1);
		scroll.setPreferredSize(new Dimension(400,163));
		contentPane.add(scroll);
		
		Action act = new GestionePagina(this, data, table_1, null, model, user);
		JButton btnTrascrivi = new JButton("Trascrivi");
		btnTrascrivi.setBounds(127, 236, 180, 38);
		btnTrascrivi.addActionListener(act);
		contentPane.add(btnTrascrivi);
		
	}
}