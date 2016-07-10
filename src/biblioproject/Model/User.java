package biblioproject.Model;

import java.util.ArrayList;
import biblioproject.DAO.UserDAO;

public class User {
	
	private int id;
	private String username;
	private String password;
	private String email;
	private int role;
	
	public User(int id, String u, String p, String e, int r){
		this.id       = id;
		this.username = u;
		this.password = p;
		this.email    = e;
		this.role     = r;
	}
	
	public User(int id){
		UserDAO DAO            = new UserDAO();
		ArrayList<String> data = DAO.retrieve(id);
		this.id       = Integer.parseInt(data.get(0));
		this.username = data.get(1);
		this.password = data.get(2);
		this.role     = Integer.parseInt(data.get(3));
		this.email    = data.get(4);
		
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public int getRole(){
		return this.role;
	}
	
	public void setUsername(String u){
		this.username=u;
	}
	
	public void setPassword(String p){
		this.password=p;
	}
	
	public void setEmail(String e){
		this.email=e;
	}
	
	public void setRole(int r){
		this.role=r;
	}
	
	public ArrayList<String> toArray(){
		ArrayList<String> data= new ArrayList<String>();
		data.add(Integer.toString(this.id));
		data.add(this.username);
		data.add(this.password);
		data.add(Integer.toString(this.role));
		data.add(this.email);
		return data;
	}
}
