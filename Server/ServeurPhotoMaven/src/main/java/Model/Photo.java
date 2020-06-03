package Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class Photo implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private transient Image image;	
	private String chemin;
	private String description;
	private String visibility;
	private List<Tag> Tags;
	private Date date;
	private String location;
	
	
	public Photo() {}
	
	public Photo(String _url) {
		chemin = _url;
		image = new Image(_url);
	}
	
	public Photo(String _url, String _description) {
		chemin = _url;
		image = new Image( _url, 300,300, true ,true);
		description = _description;
	}
	
	public Photo(String _url, String _description, ArrayList<Tag> tags) {

		chemin = _url;
		image = new Image(_url);
		description = _description;
		Tags = tags;
	}
	
	
	
	public String getVisibility() {
		return visibility;
	}

	public String getChemin() {
		return chemin;
	}
	
	public Image getImage() {
		return image;
	}
	
	public String getDescription() {
		return description;
	}
	
	public List<Tag> getTags(){
		return Tags;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	public Date getDate() {
		return date;
	}

	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}

	public void setChemin(String chemin) {
		this.chemin = chemin;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTags(List<Tag> tags) {
		Tags = tags;
	}
	
	
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        image = SwingFXUtils.toFXImage(ImageIO.read(s), null);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", s);
    }
	
}
