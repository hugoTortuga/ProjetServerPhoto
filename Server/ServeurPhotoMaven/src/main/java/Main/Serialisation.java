package Main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serialisation {
	
	
	@SuppressWarnings("finally")
	public static byte[] serialize(Serializable o) throws IOException {
		if(o == null)
			throw new NullPointerException("L'objet que vous tentez de s�rialiser est null");
		ByteArrayOutputStream byteArray = null;
		ObjectOutputStream object = null;
		try{
			byteArray = new ByteArrayOutputStream();
			object = new ObjectOutputStream(byteArray);
			object.writeObject(o);
			object.flush();
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		finally {
			if(byteArray != null){
				byteArray.close();
			}
			if(object != null){
				object.close();
			}
			return byteArray.toByteArray();
		}
	}

	@SuppressWarnings("finally")
	public static Serializable deserialize(byte[] data) throws IOException, ClassNotFoundException {
		if(data == null)
			throw new NullPointerException("Le tableau de byte que vous tentez de d�s�rialiser est null");

		ByteArrayInputStream byteArray = null;
		Serializable ser = null;
		try{
			byteArray = new ByteArrayInputStream(data);
			ser = byteArray.read();
		}
		catch(Exception ex)
		{
			System.out.println(ex);
			throw new ClassNotFoundException("Object non reconnu");
		}
		finally{
			if(byteArray != null){
				byteArray.close();
			}
			return ser;
		}
	}
	

}
