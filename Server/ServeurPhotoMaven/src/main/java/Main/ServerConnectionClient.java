package Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;

import API.Queries;
import Model.MessageClient;
import Model.ObjectNetwork;
import Model.Photo;
import Model.Tag;
import Model.User;

/*
 * Instance de connexion du client
 */

public class ServerConnectionClient implements Runnable {


	private Socket Socket;
	private ObjectInputStream Input;	
	private ObjectOutputStream Writer;
	private UUID uuid;
	private Server serverParent;
	
	public UUID getUUID() {
		return uuid;
	}

	public ServerConnectionClient(Socket _socket, UUID id, Server server) throws IOException, SQLException, ClassNotFoundException {		
		Socket = _socket;
		Writer = new ObjectOutputStream(Socket.getOutputStream());
		Input = new ObjectInputStream(Socket.getInputStream());			
		uuid = id;
		serverParent = server;
		
	}	
	
	@Override
	public void run() {

		try {
			Listen();
		}
		catch (Exception ex) {
			if(ex.getClass() == SocketException.class) {
				Log.Write("La connexion avec le client a été perdu" + ex);
				serverParent.RemoveConnection(this.uuid);
				return;
			}
			else
				Log.Write(""+ex);
		}
	}
	
	public void Send(Object o) throws IOException {
		
		Writer.writeObject(o);
		Writer.flush();
		
	}
	
	
	public void Listen() throws IOException, SQLException, ClassNotFoundException {

		while(true) {
			
			Object obj = null;
			try{
				obj = Input.readObject();					
			}
			catch(Exception ex) {
				if(ex.getClass() == SocketException.class) {
					Log.Write("La connexion avec le client a été perdu : " + ex);
					return;
				}
				else
					Log.Write(""+ex);
			}
			
			if(obj.getClass()==ObjectNetwork.class) 
			{
				TraiterMessageObject((ObjectNetwork)obj);
			}
			else if(obj.getClass()==MessageClient.class) {
			
				TraiterMessage((MessageClient)obj);
			}	
			else {
				Send(MessageClient.HelloBack);
			}
		}	
	}
	
	
	public void TraiterMessageObject(ObjectNetwork obj) throws IOException {
				
		
		switch(obj.getMessage()) {
		case Connexion :
			String mail = "";
			String pwd = "";
			User user = null;
			try {
				mail = ((ArrayList<String>)obj.getParams()).get(0);
				pwd = ((ArrayList<String>)obj.getParams()).get(1);
				user = Queries.GetUser(mail, pwd);
			}catch(Exception ex) {
				
			}			
			Send(user);
			break;	
		
		case Inscription:
			User userInscription = (User)obj.getParams();
			boolean reps = Queries.Inscription(userInscription);
			if(reps)
				Send(MessageClient.Success);
			else
				Send(MessageClient.Fail);
			
			break;		
			
		case DeleteOnePhoto:
			Object[] tab = (Object[])(obj.getParams());
			User userget = (User)tab[0];
			Photo photo = (Photo)tab[1];
			
			boolean rep = Queries.DeletePhoto(userget, photo);
			
			if(rep)
				Send(MessageClient.Success);
			else
				Send(MessageClient.Fail);
					
			break;
		
		case EditOnePhoto:
			Object[] tab2 = (Object[])(obj.getParams());
			User userget2 = (User)tab2[0];
			Photo photo2 = (Photo)tab2[1];
			
			boolean rep2 = Queries.EditOnePhoto(userget2, photo2);
			
			if(rep2)
				Send(MessageClient.Success);
			else
				Send(MessageClient.Fail);					
			break;
		
		case UploadOnePhoto:
			Object[] tab3 = (Object[])(obj.getParams());
			User userget3 = (User)tab3[0];
			Photo photo3 = (Photo)tab3[1];
			
			boolean rep3 = Queries.UploadOnePhoto(userget3, photo3);
			
			if(rep3)
				Send(MessageClient.Success);
			else
				Send(MessageClient.Fail);	
			break;
		
		case EditMyProfil:
			User user4 = (User)obj.getParams();			
			
			boolean rep4 = Queries.EditProfil(user4);
			if(rep4)
				Send(MessageClient.Success);
			else
				Send(MessageClient.Fail);	
			break;

		case GetMyPhotos:
			User user5 = (User)obj.getParams();						
			ArrayList<Photo> photos = Queries.GetMyPhotos(user5);			
			Send(photos);	
			break;
		
		case GetPhotosMot:
			String mot = (String)obj.getParams();						
			ArrayList<Photo> photos2 = Queries.GetPhotosMot(mot);			
			Send(photos2);	
			break;
			
		case GetPhotosTag:
			Tag tag = (Tag)obj.getParams();						
			ArrayList<Photo> photos3 = Queries.GetPhotosTag(tag);			
			Send(photos3);	
			break;
			
		default:
			break;
			
		}
	}
	
	public void TraiterMessage(MessageClient msg) throws IOException {
		
		switch(msg) {
		case GetPhotosPublic:
			ArrayList<Photo> photos = Queries.GetPhotos();
			Send(photos);
			break;
		case GetTag :
			ArrayList<Tag> tags = Queries.GetTags();
			Send(tags);
			break;
		default:
			break;
			
		}
	}

}
