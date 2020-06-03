package API;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Model.Photo;
import Model.Tag;
import Model.User;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class Queries {	
	
	public static ArrayList<Photo> GetPhotos() throws MalformedURLException{
	    	
	    File folder = new File("C:\\Users\\Utilisateur\\Desktop\\RepPhoto\\public\\");
	    ArrayList<Photo> photos = new ArrayList<Photo>();
	    for (File fileEntry : folder.listFiles()) {	    	      
	    	if (fileEntry.isFile()) {
	    	   photos.add(new Photo(fileEntry.toURI().toURL().toExternalForm(), fileEntry.getName()));
	    	}
	    }   	
	    return photos;
	}
	
	public static ArrayList<File> GetLogs(){
		File folder = new File("D:\\Code\\Projet\\Serveur Photo\\Server\\Server\\Logs\\");
		ArrayList<File> files = new ArrayList<File>();
		for (File fileEntry : folder.listFiles()) {	    	      
	    	if (fileEntry.isFile()) {
	    	   files.add(fileEntry);
	    	}
	    }   
		return files;
	}

	public static ArrayList<Tag> GetTags() {
				
		/*QueryManager q = new QueryManager();
		q.connection();
		q.getQuery("Tag", conditions, "*")*/
		
		return null;
	}

	public static User GetUser(String mail, String pwd) throws SQLException {
		try {
			QueryManager q = new QueryManager();
			ResultSet  res = q.sendSelect("Select name, last_name, mail, id_person from serveurphoto.person where mail=\"" + mail + "\" and password = \"" + pwd + "\";" );
			
			String nom = "";
			String ln = "";
			String mailget = "";
			int idPers = 0;
			if(res.next())
			{
				nom = res.getString(1);
				ln = res.getString(2);
				mailget = res.getString(3);
				idPers = res.getInt(4);
				User user = new User(idPers, ln, nom, mailget, null);
				return user;
			}	
			else {
				return null;
			}
		}
		catch(Exception ex) {
			return null;
		}
	}
	
	public static boolean Inscription(User user) {
		try {
			QueryManager q = new QueryManager();
			int answer = q.createUser(user.getSurname(), user.getName(), user.getMail(), null, user.getDateOfBirth().toString(), 0);
			
			File file = new File("C:\\Users\\Utilisateur\\Desktop\\RepPhoto\\"+user.getId()+"\\");
			file.createNewFile();
					
			if(answer == 1)
				return true;			
			else
				return false;
		}
		catch(Exception ex) {
			return false;
		}
	}

	public static boolean DeletePhoto(User userget, Photo photo) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean EditOnePhoto(User userget2, Photo photo2) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean UploadOnePhoto(User userget3, Photo photo3) {
		try {
			String tags = "";
			if(photo3 != null) {
				for(Tag tag : photo3.getTags()) {
					tags += tag.getLibelle() + " ";
				}
			}			
			QueryManager q = new QueryManager();
			int i = q.uploadPhoto(userget3.getId(), photo3.getDescription(), photo3.getChemin(),
											 photo3.getDate().toString(), photo3.getDescription(),photo3.getLocation(),
											 tags);
			Image uploadPhoto = photo3.getImage();
			BufferedImage swingImage = SwingFXUtils.fromFXImage(uploadPhoto, null);
	        ImageIO.write(swingImage, "png", new File("C:\\Users\\Utilisateur\\Desktop\\RepPhoto\\"+userget3.getId()+"\\"));
			return true;
		}
		catch(Exception ex) {
			return false;
		}
	}

	public static boolean EditProfil(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	public static ArrayList<Photo> GetMyPhotos(User user5) throws MalformedURLException {
		
	    File folder = new File("C:\\Users\\Utilisateur\\Desktop\\RepPhoto\\"+user5.getId()+"\\");
	    ArrayList<Photo> photos = new ArrayList<Photo>();
	    for (File fileEntry : folder.listFiles()) {	    	      
	    	if (fileEntry.isFile()) {
	    	   photos.add(new Photo(fileEntry.toURI().toURL().toExternalForm(), fileEntry.getName()));
	    	}
	    }    	
	    return photos;
	}

	public static ArrayList<Photo> GetPhotosMot(String mot) throws MalformedURLException {
		File folder = new File("C:\\Users\\Utilisateur\\Desktop\\RepPhoto\\public\\");
	    ArrayList<Photo> photos = new ArrayList<Photo>();
	    for (File fileEntry : folder.listFiles()) {	    	      
	    	if (fileEntry.isFile()) {
	    		if(mot.contains(fileEntry.getName()) || fileEntry.getName().contains(mot))
	    			photos.add(new Photo(fileEntry.toURI().toURL().toExternalForm(), fileEntry.getName()));
	    	}
	    }   	
	    return photos;
	}

	public static ArrayList<Photo> GetPhotosTag(Tag tag) {
        try {
            QueryManager q = new QueryManager();
            ResultSet  res = q.sendSelect("Select name, last_name, mail, id_person from serveurphoto.person where mail=\"" + "" + "\" and password = \"" + "" + "\";");

            String nom = "";
            String ln = "";
            String mailget = "";
            int idPers = 0;
            if(res.next())
            {
                nom = res.getString(1);
                ln = res.getString(2);
                mailget = res.getString(3);
                idPers = res.getInt(4);
                User user = new User(idPers, ln, nom, mailget, null);
                return null;
            }
            else {
                return null;
            }
        }
        catch(Exception ex) {
            return null;
        }
    }
	
	public static boolean test() {
        try {
            QueryManager q = new QueryManager();
            ResultSet  res = q.sendSelect("Select * from tag");
            if(res.next())
            	return true;
            else
            	return false;
           
        }
        catch(Exception ex) {
            return false;
        }
    }
	
}
