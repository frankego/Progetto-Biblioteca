package biblioproject.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import biblioproject.Controller.GestionePagina;
import biblioproject.Model.Biblioteca;
import biblioproject.Model.Opera;
import biblioproject.Model.Page;

public class RevisioneFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private String actioncmd;

	
	public RevisioneFrame(Biblioteca data, String type) {
		setAutoRequestFocus(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 309);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		table = new JTable();				
		DefaultTableModel model = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Opera", "Autore", "Pagina"
			}
		);
		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(104);
		table.getColumnModel().getColumn(1).setPreferredWidth(187);
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setBounds(10, 11, 414, 214);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(414,214));
		contentPane.add(scroll);
        if(type.equals("img")){
			LinkedList<Page> listaPagine = data.getPageList();
			Iterator<Page> itr = listaPagine.iterator();
			while(itr.hasNext()){
				Page next = itr.next();
				if(next.getStatus() == 0){
					Opera toAdd = new Opera(next.getOperaID());
					model.addRow(new Object[]{ toAdd.getTitle(), toAdd.getAuthor(), next.getNumber()});
				}
			}
		} else{
			LinkedList<Page> listaPagine = data.getPageList();
			Iterator<Page> itr = listaPagine.iterator();
			while(itr.hasNext()){
				Page next = itr.next();
				if(next.getStatus() == 2){
					Opera toAdd = new Opera(next.getOperaID());
					model.addRow(new Object[]{ toAdd.getTitle(), toAdd.getAuthor(), next.getNumber()});
				}
			}
		}
		
        if(type.equals("img")) actioncmd="Revisiona Acquisizione";
        else actioncmd="Revisiona Trascrizione";
        
		Action act = new GestionePagina(this, data, table, null, model, null);
		JButton btnRevisiona = new JButton(actioncmd);
		btnRevisiona.setBounds(127, 236, 180, 38);
		btnRevisiona.addActionListener(act);
		contentPane.add(btnRevisiona);
	}
}
