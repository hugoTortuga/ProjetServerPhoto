package Util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import API.QueryManager;
import Model.*;

public class Client implements Runnable{
	
	private boolean active;
	private Socket socket;
	private ObjectInputStream Input;	
	private ObjectOutputStream Writer;

	public Client(int port) throws UnknownHostException, IOException {
		Log.InitialiserLog();
		try {
			socket = new Socket("localhost", port);
			Log.Write("Lancement d'une connexion sur le port " + port);
			Input = new ObjectInputStream(socket.getInputStream());
			Writer = new ObjectOutputStream(socket.getOutputStream());			
			active = true;
		}
		catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(null,"Serveur coupé");
			throw e;
		}
	}
	
	public void OnClose() {
		active = false;
	}
	
	public void Send(Object message){
		try {
			Writer.writeObject(message);
			Writer.flush();
		} catch (Exception e) {
			System.out.println("Impossible de send : " + e);
		}		
	}
	
	public void Send(ObjectNetwork message){
		try {
			Writer.writeObject(message);
			Writer.flush();
		} catch (Exception e) {
			System.out.println("Impossible de send : " + e);
		}		
	}
	
	

	@Override
	public void run() {
	}
	
	// Requetes

		
	public User connexion(String mail, String password) throws ClassNotFoundException, IOException {
			
		ArrayList<String> params = new ArrayList<String>();
		params.add(mail);
		params.add(password);
		ObjectNetwork net = new ObjectNetwork(MessageClient.Connexion, params);
		Send(net);
			
		User user = (User)Input.readObject();		
		return user;
	}
	
	public boolean inscription(User user) throws ClassNotFoundException, IOException {
		
		ObjectNetwork net = new ObjectNetwork(MessageClient.Inscription, user);
		Send(net);		
		MessageClient msg = (MessageClient)Input.readObject();		
		if(msg == MessageClient.Success) 
			return true;
		else
			return false;
	}
	
	public boolean DeleteOnePhoto(User user, Photo photo) throws ClassNotFoundException, IOException {
		Object[] tab = new Object[2];
		tab[0] = user;
		tab[1] = photo;
		ObjectNetwork net = new ObjectNetwork(MessageClient.DeleteOnePhoto, tab);
		Send(net);		
		MessageClient msg = (MessageClient)Input.readObject();		
		if(msg == MessageClient.Success) 
			return true;
		else
			return false;
	}
	
	public boolean EditOnePhoto(User user, Photo photo) throws ClassNotFoundException, IOException {
		Object[] tab = new Object[2];
		tab[0] = user;
		tab[1] = photo;
		ObjectNetwork net = new ObjectNetwork(MessageClient.EditOnePhoto, tab);
		Send(net);		
		MessageClient msg = (MessageClient)Input.readObject();		
		if(msg == MessageClient.Success) 
			return true;
		else
			return false;
	}
	
	public boolean UploadOnePhoto(User user, Photo photo) {
		Object[] tab = new Object[2];
		tab[0] = user;
		tab[1] = photo;
		ObjectNetwork net = new ObjectNetwork(MessageClient.UploadOnePhoto, tab);				
		try{
			Send(net);
			//MessageClient msg = (MessageClient)Input.readObject();		
			//if(msg == MessageClient.Success) 
			//	return true;
			//else
			//	return false;
			
			return true;
		}
		catch(Exception ex) {
			Log.Write(ex);
			return false;
		}
		
	}
	
	public boolean EditMyProfil(User user) throws ClassNotFoundException, IOException {
		ObjectNetwork net = new ObjectNetwork(MessageClient.EditMyProfil, user);
		Send(net);		
		MessageClient msg = (MessageClient)Input.readObject();		
		if(msg == MessageClient.Success) 
			return true;
		else
			return false;
	}
	
	public ArrayList<Photo> GetMyPhotos(User user){
		ObjectNetwork net = new ObjectNetwork(MessageClient.GetMyPhotos, user);
		Send(net);
		ArrayList<Photo> photos = new ArrayList<Photo>();
		try {
			photos = (ArrayList<Photo>)Input.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return photos;
	}
	
	public ArrayList<Photo> GetPhotosMot(String recherche) throws IOException, ClassNotFoundException {
		ObjectNetwork net = new ObjectNetwork(MessageClient.GetPhotosMot, recherche);
		Send(net);	
		@SuppressWarnings("unchecked")
		ArrayList<Photo> photos = (ArrayList<Photo>)Input.readObject();
		return photos;
	}
	
	public ArrayList<Photo> GetPhotosTag(Tag tag) throws ClassNotFoundException, IOException{
		ObjectNetwork net = new ObjectNetwork(MessageClient.GetPhotosTag, tag);
		Send(net);	
		@SuppressWarnings("unchecked")
		ArrayList<Photo> photos = (ArrayList<Photo>)Input.readObject();
		return photos;
	}

	public ArrayList<Photo> GetPhotoPublic(){			
		try {
			Send(MessageClient.GetPhotosPublic);
			ArrayList<Photo> photos = (ArrayList<Photo>)Input.readObject();
			return photos;
		}
		catch(Exception ex) {
			Log.Write(ex);
			return null;
		}
	}
		
		// Fin requetes	
	
	
	
	

}