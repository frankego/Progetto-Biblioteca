package biblioproject.Controller;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import biblioproject.Model.Action;
import biblioproject.Model.Biblioteca;
import biblioproject.Model.User;
import biblioproject.View.CronologiaFrame;
import biblioproject.View.MainFrame;
import biblioproject.Model.Opera;

public class GestioneAction extends AbstractAction implements Gestione {
	
	private static final long serialVersionUID = 3939558628081151887L;
	private Biblioteca data;
	private Object o;
	private Object o2;
	
	public GestioneAction(Biblioteca data, Object o, Object o2){
		this.data = data;
		this.o    = o;
		this.o2   = o2;
	}
	
public void actionPerformed(ActionEvent e) {
	switch(e.getActionCommand()){
	case "Cronologia Operazioni" : CronologiaFrame f = new CronologiaFrame(data, ((User)o));f.setVisible(true);break;
	default           : break;
	}
	}

	
	public void retrieve() {
		User user    = ((User)o);
	    DefaultTableModel table = ((DefaultTableModel)o2);
		LinkedList<Action> actionList = data.getActionList();
		Iterator<Action> itr = actionList.iterator();
		while(itr.hasNext()){
			Action next = itr.next();
			if(next.getUserID() == user.getId()){
				String type="";
				switch(next.getType()){
				case 0  : type="Acquisizione"; break;
				case 1  : type="Trascrizione"; break;
				default : break;
				}
				String status="";
				switch(next.getStatus()){
				case 0  : status="Da Controllare"; break;
				case 1  : status="Accettato"; break;
				case 2  : status="Rifiutato";break;
				default : break;
				}
				table.addRow(new Object[]{new User(next.getUserID()).getUsername(),new Opera(next.getOperaID()).getTitle(),next.getPage(),type,status});
			
		}
	  }
   }
	
	public void add() {
	}
	public void remove() {
	}
	public void edit() {
	}
	
}
