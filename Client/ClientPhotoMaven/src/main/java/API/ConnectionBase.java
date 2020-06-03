package API;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Util.Log;
import Model.Photo;
import Model.User;

public class ConnectionBase {
	
	private final String ConnectionString = "jdbc:mysql://localhost:3306/projet/root\",\"Jklm4826oo!AZ";
	private static Connection Connection;
	
	
	public ConnectionBase() {
		try{
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection = DriverManager.getConnection(ConnectionString);	
		}
		catch(Exception ex) {
			Util.Log.Write(""+ex);
		}
	}
	
	public List<Photo> GetPhotoPublic() throws SQLException{
		
		ResultSet results;
		Statement stmt = Connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		results = stmt.executeQuery("SELECT * FROM Photo");
		
		ArrayList<Photo> photos = new ArrayList<Photo>();
		
		while(results.next()){		
			String chemin = results.getString("chemin");
			System.out.println(results.getString("chemin") + " " + results.getString("last_name") );
			photos.add(new Photo(chemin));
		}		
		
		return photos;		
	}
	
	public boolean Inscription(String nom, String prenom, String email, String pwd, Date date) {
		try {
			Statement stmt = Connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt.executeQuery("INSERT INTO `projet`.`person` (`name`, `last_name`, `mail`, `password`, `isAdmin`) VALUES ('"+ prenom
					+"', '" + nom +"', '"+ email +"', '" + pwd + "', '0');");
			return true;
		}
		catch(Exception ex) {
			return false;
		}
	}
	
	@SuppressWarnings("finally")
	public User Connection(String nom, String password) {
		try {
			Statement stmt = Connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			stmt.executeQuery("Select * From Person where nom = '" + nom + "' and password = '"+ password +"';");
			User user = new User();
			
		}
		catch(Exception ex) {
			Log.Write(""+ex);
		}
		finally {
			return null;
		}
	}

	
}
