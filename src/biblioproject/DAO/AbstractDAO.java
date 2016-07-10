package biblioproject.DAO;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

public abstract class AbstractDAO {


	static String serverdata= "";
	static String DBuser= "";
	static String DBpass= "";
	
	public static Connection connection() throws SQLException, IOException{
		
		FileReader reader = new FileReader("db.txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
        serverdata = bufferedReader.readLine();
        DBuser = bufferedReader.readLine();
        DBpass = bufferedReader.readLine();
        reader.close();
		Connection con=DriverManager.getConnection(serverdata, DBuser , DBpass);
		return con;
	}
	
	public abstract ArrayList<String> retrieve(int id);
	public abstract void add(ArrayList<String> data);
	public abstract void edit(ArrayList<String> data);
	public abstract void delete(int id);
	public abstract LinkedList<Integer> retrieveAll();
}
