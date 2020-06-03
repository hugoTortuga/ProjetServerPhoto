package Model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class User implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int Id;
	private String Name;
	private String Surname;
	private String Mail;
	private Date DateOfBirth;
	private String password;
	private String userName;

	private Boolean IsAdmin;
	
	private List<User> Amis;
	private List<Photo> Photos;
	
	public User() {

	}
	
	public User(int _Id, String _nom,String _prenom, String _mail, Date _dateOfBirth) {
		Id= _Id;
		Name = _nom;
		Surname = _prenom;
		DateOfBirth = _dateOfBirth;
		
	}
	
	public int getId() {
		return Id;
	}

	public String getName() {
		return Name;
	}

	public String getSurname() {
		return Surname;
	}

	public String getMail() {
		return Mail;
	}

	public Date getDateOfBirth() {
		return DateOfBirth;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
	}

	public Boolean getIsAdmin() {
		return IsAdmin;
	}

	public List<User> getAmis() {
		return Amis;
	}

	public List<Photo> getPhotos() {
		return Photos;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setId(int id) {
		Id = id;
	}

	public void setName(String name) {
		Name = name;
	}

	public void setSurname(String surname) {
		Surname = surname;
	}

	public void setMail(String mail) {
		Mail = mail;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setIsAdmin(Boolean isAdmin) {
		IsAdmin = isAdmin;
	}

	public void setAmis(List<User> amis) {
		Amis = amis;
	}

	public void setPhotos(List<Photo> photos) {
		Photos = photos;
	}
	
}
