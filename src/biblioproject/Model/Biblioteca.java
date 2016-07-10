package biblioproject.Model;
import biblioproject.DAO.*;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class Biblioteca {
	
	private LinkedList<Opera> operaList   = new LinkedList<Opera>();
	private LinkedList<User> userList     = new LinkedList<User>();
	private LinkedList<Page> pageList     = new LinkedList<Page>();
	private LinkedList<Action> actionList = new LinkedList<Action>();
	
	public Biblioteca(){
		
		setOperaList();
		setUserList();
		setPageList();
		setActionList();
			
	}
	
	public void setOperaList(){
		operaList.clear();
		OperaDAO ODAO = new OperaDAO();
		LinkedList<Integer> OIDs = ODAO.retrieveAll();
		Iterator<Integer> itr = OIDs.iterator();
		while(itr.hasNext()){
		operaList.add(new Opera(itr.next()));
		}
	}
	
	public void setUserList(){
		userList.clear();
		UserDAO UDAO = new UserDAO();
		LinkedList<Integer> UIDs = UDAO.retrieveAll();
		Iterator<Integer> itr = UIDs.iterator();
		while(itr.hasNext()){
			userList.add(new User(itr.next()));
		}
	}
	
	public void setPageList(){
		pageList.clear();
		PageDAO PDAO = new PageDAO();
		LinkedList<Integer> PIDs = PDAO.retrieveAll();
		Iterator<Integer> itr = PIDs.iterator();
		while(itr.hasNext()){
			pageList.add(new Page(itr.next()));
		}
	}
	
	public void setActionList(){
		actionList.clear();
		ActionDAO ADAO = new ActionDAO();
		LinkedList<Integer> AIDs = ADAO.retrieveAll();
		Iterator<Integer> itr = AIDs.iterator();
		while(itr.hasNext()){
		actionList.add(new Action(itr.next()));
		}
	}
	
	public LinkedList<Opera> getOperaList(){
		return this.operaList;
	}
	
	public LinkedList<User> getUserList(){
		return this.userList;
	}
	
	public LinkedList<Page> getPageList(){
		return this.pageList;
	}
	
	public LinkedList<Action> getActionList(){
		return this.actionList;
	}
	
	

}
