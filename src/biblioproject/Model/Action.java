package biblioproject.Model;

import java.util.ArrayList;

import biblioproject.DAO.ActionDAO;

public class Action {
	
	private int id;
	private int userID;
	private int operaID;
	private int page;
	private int type;
	private int status;
	
	public Action (int id, int u, int o, int p, int t, int s){
		this.id      = id;
		this.userID  = u;
		this.operaID = o;
		this.page    = p;
		this.type    = t;
		this.status  = s;
	}
	
	public Action(int id){
		ActionDAO DAO           = new ActionDAO();
		ArrayList<String> data = DAO.retrieve(id);
		this.id      = Integer.parseInt(data.get(0));
		this.userID  = Integer.parseInt(data.get(1));
		this.operaID = Integer.parseInt(data.get(2));
		this.page    = Integer.parseInt(data.get(3));
		this.type    = Integer.parseInt(data.get(4));
		this.status  = Integer.parseInt(data.get(5));	
	}
	
	public int getID() {
		return this.id;
	}
	
    public int getUserID() {
		return this.userID;
	}

	public int getOperaID() {
		return this.operaID;
	}

	public int getPage() {
		return this.page;
	}

	public int getType() {
		return this.type;
	}

	public int getStatus() {
		return this.status;
	}
	
	public void setUserID(int u) {
		this.userID = u;
	}

	public void setOperaID(int o) {
		this.operaID = o;
	}

	public void setPage(int p) {
		this.page = p;
	}

	public void setType(int t) {
		this.type = t;
	}

	public void setStatus(int s) {
		this.status = s;
	}
	
	public ArrayList<String> toArray(){
		ArrayList<String> data= new ArrayList<String>();
		data.add(Integer.toString(this.id));
		data.add(Integer.toString(this.userID));
		data.add(Integer.toString(this.operaID));
		data.add(Integer.toString(this.page));
		data.add(Integer.toString(this.type));
		data.add(Integer.toString(this.status));
        return data;
	}
	

}
