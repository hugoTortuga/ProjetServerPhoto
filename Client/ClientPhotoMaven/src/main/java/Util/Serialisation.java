package Util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serialisation {
	
	
	public static byte[] serialize(Serializable o) throws IOException {
        if(o != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            byte[] binaryTab = null;
            try {
                oos.writeObject(o);
                oos.flush();
                binaryTab = bos.toByteArray();
            } finally {
                try {
                    if (bos != null) {
                        bos.close();
                    }
                } catch (IOException e) {
                    throw new IOException(e);
                }
            }
            return binaryTab;
        } else{
            throw new NullPointerException("Object is null.");
        }

    }

	public static Serializable deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInput in = null;
        Serializable o = null;
        try {
            in = new ObjectInputStream(bis);
            o = (Serializable) in.readObject();
        }catch (final ClassNotFoundException e) {
            e.printStackTrace();
        } finally
         {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                throw new IOException(e);
            }
        }
        return o;
    }
	
	

}
