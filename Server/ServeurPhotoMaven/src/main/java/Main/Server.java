package Main;

import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import API.QueryManager;

import java.io.*;

public class Server implements Runnable{
	
	private ServerSocket serverSocket;
	private Socket socket;	
	private ArrayList<UUID> Connections;
	private boolean isOn;
	
	public Socket GetSocket() {
		return socket;
	}
	
	public ArrayList<UUID> GetConnection() {
		return Connections;
	}
	
	public void AddConnection(UUID id){
		if(Connections != null) {
			Connections.add(id);
		}
	}
	
	public void RemoveConnection(UUID id) {
		if(Connections != null) {
			Connections.remove(id);
		}
	}
	
	

	public Server(int port) throws Exception {

		isOn = false;
		Log.InitialiserLog();
		serverSocket = new ServerSocket(port);	
		Connections = new ArrayList<UUID> ();
		
	}
	
	public void Listen() throws IOException, SQLException{
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		
		while (true) {
						
			try {
				Socket clientSocket = serverSocket.accept();
	            Log.Write("Un client s'est connecté");
	            
	            //Créer un uuid qui identifie la connection
	            UUID idConnection = UUID.randomUUID();            
	            executorService.submit(new ServerConnectionClient(clientSocket, idConnection, this));
	            AddConnection(idConnection);
	            
			}
			catch(Exception ex) {
				Log.Write("Erreur lors de la connexion d'un client - erreur : " + ex);
			}
            
        }
		
	}
	
	public void On() {
		isOn = true;
	}
	public void Off() {
		isOn = false;
	}

	@Override
	public void run() {
		try {
			Listen();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
}
