package biblioproject.Controller;

import java.awt.Component;
import java.awt.event.*;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

import com.sun.xml.internal.ws.org.objectweb.asm.Label;

import biblioproject.Model.Biblioteca;
import biblioproject.Model.Opera;
import biblioproject.Model.Page;
import biblioproject.Model.User;

public class GestioneMouse implements MouseListener {
	
	private Biblioteca data;
	private JList list;
	private String type;
	private JLabel l;
	private JLabel l2;
	private JButton b;
	private Object o;
	
	public GestioneMouse(Biblioteca data, String t, JList list, JLabel l, JLabel l2, JButton b, Object o){
		this.data = data;
		this.type = t;
		this.list = list;
		this.l    = l;
		this.l2   = l2;
		this.b    = b;
		this.o    = o;
	}

	public void mousePressed(MouseEvent e) {
		
		switch(type){
		case "MainFrame" : mainList();break;
		case "UserFrame" : userList();break;
		case "PageFrame" : pageList();break;
		default          : break;
		}
	}
	
	public void pageList(){
		int selected = (int) list.getSelectedValue();
		Iterator<Page> itr = data.getPageList().iterator();
		while(itr.hasNext()){
			Page next = itr.next();
			if(next.getNumber() == selected){
			   switch(next.getStatus()){
			   case 0 : l.setText("Immagine in attesa");break;
			   case 1 : l.setText("Immagine convalidata");break;
			   case 2 : l.setText("Testo in attesa");break;
			   case 3 : l.setText("Testo convalidato");break;
			   default: break;
			   }
			}
		}
	}
	
	public void userList(){
		   String selected = (String) list.getSelectedValue();
	       Iterator<User> itr = data.getUserList().iterator();
	       int count = 0;
	       while(itr.hasNext()){
	    	   count++;
	    	   User next = itr.next();
	    	   if(next.getUsername().equals(selected)){
	    		   switch(next.getRole()){
	    		   case 0: l.setText("Utente");break;
	    		   case 1: l.setText("Utente Avanzato");break;
	    		   case 2: l.setText("Acquisitore");break;
	    		   case 3: l.setText("Revisore Img");break;
	    		   case 4: l.setText("Trascrittore");break;
	    		   case 5: l.setText("Revisore Txt");break;
	    		   case 6: l.setText("Admin");break;
	    		   default : break;
	    		   }
	    	   }
	       }
	}
	
	public void mainList(){
		String titolo = (String) list.getSelectedValue();
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itr = operaList.iterator();
		while(itr.hasNext()){
			String status = "Pubblico";
			Opera o = itr.next();
			if(o.getTitle().equals(titolo)){
				l.setText(o.getAuthor());
				l2.setText(Integer.toString(o.getPages()));
				if(!o.getStatus()) status = "Privato";
				if(this.o != null)((JLabel)this.o).setText(status);
				break;
			}
		}
		b.setEnabled(true);
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

	

}
