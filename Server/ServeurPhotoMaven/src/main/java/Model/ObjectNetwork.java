package Model;

import java.io.Serializable;

public class ObjectNetwork implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MessageClient message;
	
	private Object params;

	public void setMessage(MessageClient message) {
		this.message = message;
	}

	public void setParams(Object params) {
		this.params = params;
	}

	public MessageClient getMessage() {
		return message;
	}

	public Object getParams() {
		return params;
	}
	
	public ObjectNetwork() {
		
	}
	
	public ObjectNetwork(MessageClient c, Object s) {
		message = c;
		params = s;
	}
	
}
