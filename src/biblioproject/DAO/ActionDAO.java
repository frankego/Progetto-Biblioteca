package biblioproject.DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;


public class ActionDAO extends AbstractDAO {
	
	
	public ArrayList<String> retrieve(int id){
		
		ArrayList<String> data = new ArrayList<String>();
		try{
			Connection con=connection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM action WHERE id='"+ id +"' ");
			
			   if(rs.next()){ 
				 data.add(rs.getString("id"));
				 data.add(rs.getString("userID"));
			     data.add(rs.getString("operaID"));
			     data.add(rs.getString("page"));
			     data.add(rs.getString("type"));
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
			PreparedStatement pst=con.prepareStatement("INSERT INTO action(userID, operaID, page, type, status) VALUES ('"+ data.get(1) +"','"+ data.get(2) +"','"+ data.get(3) +"','"+ data.get(4) +"','"+ data.get(5) +"')");
			pst.executeUpdate();
			   	
		} catch (Exception E){
			E.printStackTrace();
		}
	}
	
	
	
	public void edit(ArrayList<String> data){
		try{
			Connection con=connection();
			PreparedStatement pst=con.prepareStatement("UPDATE action SET userID='"+ data.get(1) +"',operaID='"+ data.get(2) +"',page='"+ data.get(3) +"',type='"+ data.get(4) +"',status='"+ data.get(5) +"' WHERE id='"+ data.get(0) +"'");
			pst.executeUpdate();
			
		} catch (Exception E){
			E.printStackTrace();
		}
	}
	
	
	
	public void delete(int i){
		try{
			Connection con=connection();
			PreparedStatement pst=con.prepareStatement("DELETE FROM action WHERE id='"+ i +"'");
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
			ResultSet rs=st.executeQuery("SELECT * FROM action");
			
			while(rs.next()){
				data.add(rs.getInt("id"));
			}
			
		} catch (Exception E){
			E.printStackTrace();
		}
		return data;
	}
}