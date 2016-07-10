package biblioproject.Controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ListModel;

import biblioproject.DAO.UserDAO;
import biblioproject.Model.Biblioteca;
import biblioproject.Model.User;
import biblioproject.View.MainFrame;
import biblioproject.View.RegistrazioneFrame;

public class GestioneUser extends AbstractAction  implements Gestione{

	private static final long serialVersionUID = 3931651081791068737L;
	private JFrame frame;
	private Biblioteca data;
	private Object o;
	private Object o2;
	private Object o3;
	int editrole = 0;
	
	public GestioneUser (JFrame f, Biblioteca d, Object u, Object p, Object e){
		
		this.frame   = f;
		this.data    = d;
		this.o       = u;
		this.o3      = p;
		this.o2      = e;
		
	}
	

	public void actionPerformed(ActionEvent e) {
            switch(e.getActionCommand()){
            case "Login"         : login();break;
            case "Registrati"    : RegistrazioneFrame f = new RegistrazioneFrame(data); f.setVisible(true);break;
            case "Conferma"      : add();break;
            case "Elimina Utente": if(((JList)o).getSelectedValue()==null){JOptionPane.showMessageDialog(new JFrame(), "      Seleziona user","Attenzione", JOptionPane.WARNING_MESSAGE);break;}remove();break;
            case "Promuovi"      : if(((JList)o).getSelectedValue()==null){JOptionPane.showMessageDialog(new JFrame(), "      Seleziona user","Attenzione", JOptionPane.WARNING_MESSAGE);break;}editrole = 1; edit();break;
            case "Declassa"      : if(((JList)o).getSelectedValue()==null){JOptionPane.showMessageDialog(new JFrame(), "      Seleziona user","Attenzione", JOptionPane.WARNING_MESSAGE);break;}editrole = -1;edit();break;
            default : break;
            }
		  
	}
	
	public void login(){
		    String username = ((JTextField)o).getText();
			char[] chars    = ((JPasswordField)o3).getPassword();
			String password = new String(chars);
			boolean error   = true;
			
			LinkedList<User> users = data.getUserList();
			Iterator<User> itr = users.iterator();
			
			while(itr.hasNext()){
				User u = itr.next();

				if( u.getUsername().equals(username)){
					if( u.getPassword().equals(password)){
						error = false;
						MainFrame f = new MainFrame(data, u);
						f.setVisible(true);
						JOptionPane.showMessageDialog(new JFrame(), "        Bentornato " + username);
						frame.dispose();
					} else {
						break;
					}
				} 	
			}
			if(error)JOptionPane.showMessageDialog(new JFrame(), "Combinazione User/Password errata","Inane warning", JOptionPane.WARNING_MESSAGE);
	}
	
	public void add() {
		String username = ((JTextField)o).getText();
		String password = ((JTextField)o3).getText();
		String email    = ((JTextField)o2).getText();
		boolean error   = false;
		
		LinkedList<User> users = data.getUserList();
		Iterator<User> itr = users.iterator();
		
		while(itr.hasNext()){
			User u = itr.next();
			if(u.getUsername().equals(username)){
				error = true;
				JOptionPane.showMessageDialog(new JFrame(), "L'username inserito risulta già registrato","Inane warning", JOptionPane.WARNING_MESSAGE);
				break;
			}
			if(u.getEmail().equals(email)){
				error = true;
				JOptionPane.showMessageDialog(new JFrame(), "L'email inserita risulta già registrata","Inane warning", JOptionPane.WARNING_MESSAGE);
				break;
			}
		}
		
		if(!error){
			User signup = new User(0, username, password, email, 0);
			UserDAO DAO = new UserDAO();
			DAO.add(signup.toArray());	
			data.getUserList().add(signup);
			JOptionPane.showMessageDialog(new JFrame(), "  Registrazione Completata!");
			frame.dispose();
		}		
	}


	public void remove() {
       String selected = (String) ((JList)o).getSelectedValue();
       Iterator<User> itr = data.getUserList().iterator();
       int count = 0;
       while(itr.hasNext()){
    	   count++;
    	   User next = itr.next();
    	   if(next.getUsername().equals(selected)){
    		   UserDAO DAO = new UserDAO();
    		   DAO.delete(next.getId());
    		   break;
    	   }
       }
       data.setUserList();
       DefaultListModel model = new DefaultListModel();
       for(User u : data.getUserList()){
    	   model.addElement(u.getUsername());
       }
       refresh();  
	}


	public void edit() {
		 String selected = (String) ((JList)o).getSelectedValue();
	       Iterator<User> itr = data.getUserList().iterator();
	       int count = 0;
	       while(itr.hasNext()){
	    	   count++;
	    	   User next = itr.next();
	    	   if(next.getUsername().equals(selected)){
	    		   int role = next.getRole();
	    		   next.setRole(role+editrole);
	    		   UserDAO DAO = new UserDAO();
	    		   DAO.edit(next.toArray());
	    		   if(editrole == 1) JOptionPane.showMessageDialog(new JFrame(), "Promosso!");
	    		   else JOptionPane.showMessageDialog(new JFrame(), "Declassato!");
	    		   break;
	    	   }
	       }
	       data.setUserList();
	}
	
	public void refresh(){
		ArrayList<String> listaUser = new ArrayList<String>();
		LinkedList<User> utenti = data.getUserList();
		Iterator<User> itr = utenti.iterator();
		while(itr.hasNext()){
			listaUser.add(itr.next().getUsername());
		}
		Collections.sort(listaUser);
		DefaultListModel model = new DefaultListModel();
		for(String u : listaUser){
			model.addElement(u);
		}
		((JList)o).setModel(model);
	}

}
