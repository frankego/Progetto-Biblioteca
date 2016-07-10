package biblioproject.Model;

import java.util.ArrayList;

import biblioproject.DAO.PageDAO;

public class Page {
	
	private int id;
	private int operaID;
	private int number;
	private String text;
	private String image;
	private int status;
	
	public Page(int id, int oid, int n, String t, String i, int s){
		this.id      = id;
		this.operaID = oid;
		this.number  = n;
		this.text    = t;
		this.image   = i;
		this.status  = s;
	}
	
	public Page(int id){
		PageDAO DAO            = new PageDAO();
		ArrayList<String> data = DAO.retrieve(id);
		this.id      = Integer.parseInt(data.get(0));
		this.operaID = Integer.parseInt(data.get(1));
		this.number  = Integer.parseInt(data.get(2));
		this.text    = data.get(3);
		this.image   = data.get(4);
		this.status  = Integer.parseInt(data.get(5));
		
		
	}
	
	public int getID(){
		return this.id;
	}
	
	public int getOperaID(){
		return this.operaID;
	}
	
	public int getNumber(){
		return this.number;
	}
	
	public String getImage(){
		return this.image;
	}
	
	public int getStatus(){
		return this.status;
	}
	
	public String getText(){
		return this.text;
	}
	
	public void setNumber(int n){
		this.number=n;
	}
	
	public void setImage(String i){
		this.image=i;
	}
	
	public void setStatus(int s){
		this.status=s;
	}
	
	public void setText(String t){
		this.text=t;
	}
	
	public ArrayList<String> toArray(){
		ArrayList<String> data= new ArrayList<String>();
		data.add(Integer.toString(this.id));
		data.add(Integer.toString(this.operaID));
		data.add(Integer.toString(this.number));
		data.add(this.text);
		data.add(this.image);
		data.add(Integer.toString(this.status));
		return data;
	}
}
