package biblioproject.DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

import com.sun.xml.internal.messaging.saaj.util.Base64;


public class PageDAO extends AbstractDAO {
	
	
	public ArrayList<String> retrieve(int id){
		
		ArrayList<String> data = new ArrayList<String>();
		try{
			Connection con=connection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM page WHERE id='"+ id +"' ");
			
			   if(rs.next()){ 
				 data.add(rs.getString("id"));
			     data.add(rs.getString("operaID"));
			     data.add(rs.getString("number"));
			     data.add(new String(Base64.base64Decode(rs.getString("text"))));
			     data.add(rs.getString("image"));
			     data.add(rs.getString("status"));
			   }	
		} catch (Exception E){
			E.printStackTrace();
		}
		return data;
	}
	
	
	
	public void add(ArrayList<String> data){
		try{
			Connection con=connection();
			PreparedStatement pst=con.prepareStatement("INSERT INTO page(operaID, number, text, image, status) VALUES ('"+ data.get(1) +"','"+ data.get(2) +"','"+ data.get(3) +"','"+ data.get(4) +"','"+ data.get(5) +"')");
			pst.executeUpdate();
			   	
		} catch (Exception E){
			E.printStackTrace();
		}
	}
	
	
	
	public void edit(ArrayList<String> data){
		try{
			Connection con=connection();
			PreparedStatement pst=con.prepareStatement("UPDATE page SET operaID='"+ data.get(1) +"',number='"+ data.get(2) +"',text='"+ data.get(3) +"',image='"+ data.get(4) +"',status='"+ data.get(5) +"' WHERE id='"+ data.get(0) +"'");
			pst.executeUpdate();
			
		} catch (Exception E){
			E.printStackTrace();
		}
	}
	
	
	
	public void delete(int i){
		try{
			Connection con=connection();
			PreparedStatement pst=con.prepareStatement("DELETE FROM page WHERE id='"+ i +"'");
			pst.executeUpdate();
			
		} catch (Exception E){
			E.printStackTrace();
		}
	}
	
	public LinkedList<Integer> retrieveAll(){
		LinkedList<Integer> data = new LinkedList<Integer>();
		try{
			Connection con=connection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM page");
			
			while(rs.next()){
				data.add(rs.getInt("id"));
			}
			
		} catch (Exception E){
			E.printStackTrace();
		}
		return data;
	}
}