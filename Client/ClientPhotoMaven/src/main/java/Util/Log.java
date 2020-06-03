package Util;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
	
	private static java.util.logging.Logger log = Logger.getLogger(Client.class.getName());
	private static java.util.logging.FileHandler fileHand; 

	public static void Write(String msg){
		
		try {
			log.info(msg + '\n');
			System.out.println(msg);
		}
		catch(Exception ex) {
			log.info("" + ex);
		}
	}
	
public static void Write(Exception msg){
		
		try {
			log.info("Erreur : " + msg + '\n');
			System.out.println(msg);
		}
		catch(Exception ex) {
			log.info("" + ex);
		}
	}

	public static void InitialiserLog(){
		LogFormatter format = new LogFormatter();
		try {
			log.setLevel(Level.ALL);
			log.setUseParentHandlers(false);
			fileHand = new FileHandler("D:/Code/Projet/Serveur Photo/ProjetM/ClientPhoto/Logs/clientLog%u.log", 8000000, 1, true);
			fileHand.setFormatter(format);
			log.addHandler(fileHand);
		}
		catch(Exception ex) {
			Log.Write("" + ex);
		}
	}
}
