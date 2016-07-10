package biblioproject.View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import biblioproject.Controller.GestionePagina;
import biblioproject.Controller.GestioneUser;
import biblioproject.Controller.GestioneMouse;
import biblioproject.Model.Biblioteca;
import biblioproject.Model.Opera;
import biblioproject.Model.Page;
import biblioproject.Model.User;

import javax.swing.JList;
import javax.swing.border.BevelBorder;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;

public class GestisciFrame extends JFrame {

	private JPanel contentPane;

	public GestisciFrame(Biblioteca data, String type, String opera) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 531, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		
		if(type.equals("user")){
			
			JLabel lblStatus = new JLabel("Ruolo :");
			lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblStatus.setBounds(324, 33, 46, 14);
			contentPane.add(lblStatus);
			
			JLabel label = new JLabel("-");
			label.setBounds(380, 33, 100, 14);
			contentPane.add(label);
	
		JList list = new JList();
		JScrollPane scroll = new JScrollPane(list);
		scroll.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scroll.setBounds(10, 11, 285, 239);
		contentPane.add(scroll);
		
		GestioneUser act = new GestioneUser(this, data, list, null, null);
		act.refresh();
		GestioneMouse mouse = new GestioneMouse(data, "UserFrame", list, label, null, null, null);
		list.addMouseListener(mouse);
		
		JButton btnNewButton = new JButton("Elimina Utente");
		btnNewButton.setBounds(320,85, 173, 33);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.addActionListener(act);
		contentPane.add(btnNewButton);
			
		JButton btnNewButton_1 = new JButton("Promuovi");
		btnNewButton_1.setBounds(320, 130, 173, 33);
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_1.addActionListener(act);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Declassa");
		btnNewButton_2.setBounds(320, 174, 173, 33);
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_2.addActionListener(act);
		contentPane.add(btnNewButton_2);
		} else {
			
			JLabel lblStatus = new JLabel("Status :");
			lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblStatus.setBounds(324, 33, 46, 14);
			contentPane.add(lblStatus);
			
			JLabel label = new JLabel("-");
			label.setBounds(380, 33, 150, 14);
			contentPane.add(label);
			
			
			Opera selected = null;
			LinkedList<Opera> operaList = data.getOperaList();
			Iterator<Opera> itrO = operaList.iterator();
			while(itrO.hasNext()){
				Opera next = itrO.next();
				if(next.getTitle().equals(opera)){
					selected = next;
					break;
				}
			}
			
			ArrayList<Integer> listaPagine=new ArrayList<Integer>();
			Iterator<Page> itr = data.getPageList().iterator();
			while(itr.hasNext()){
				Page next = itr.next();
				if(next.getOperaID() == selected.getId()) listaPagine.add(next.getNumber());
			}
			Collections.sort(listaPagine);
			DefaultListModel<Integer> model = new DefaultListModel<Integer>();
			for(int p : listaPagine) model.addElement(p);
			
			JList list = new JList(model);
			JScrollPane scroll = new JScrollPane(list);
			scroll.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			scroll.setBounds(10, 11, 285, 239);
			
			contentPane.add(scroll);
		
		Action act2 = new GestionePagina(this, data, list, null, null, null);
		GestioneMouse mouse = new GestioneMouse(data, "PageFrame", list, label, null, null, null);
		list.addMouseListener(mouse);
			
		JButton btnNewButton = new JButton("Elimina Pagina");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setBounds(320, 85, 173, 33);
		btnNewButton.addActionListener(act2);
		contentPane.add(btnNewButton);
			
		JButton btnNewButton_1 = new JButton("Elimina Testo");
		btnNewButton_1.setBounds(320, 130, 173, 33);
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_1.addActionListener(act2);
		contentPane.add(btnNewButton_1);

		}
	}
}
