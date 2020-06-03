package Model;

import java.io.Serializable;

public enum MessageClient implements Serializable {
	
	Hello,
	HelloBack,
	Connexion,
	Inscription,
	DeleteOnePhoto,
	EditOnePhoto,
	UploadOnePhoto,
	EditMyProfil,
	GetMyPhotos,
	GetPhotosMot,
	GetPhotosTag,
	GetPhotosPublic,
	GetTag,
	GetAmis,
	Success,
	Fail	
}
