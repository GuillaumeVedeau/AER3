package controllers.admin;

import java.io.IOException;

import controllers.membre.SecuredMembre;
import models.*;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;

import javax.naming.NamingException;
import javax.persistence.PersistenceException;

import java.io.File;
import java.io.IOException;

import functions.UploadImage;
import play.mvc.Security;
import views.html.admin.organisationLocale.gererOrganisationLocale;
import views.html.admin.organisationLocale.editerGroupe;
import views.html.admin.organisationLocale.ajouterGroupe;
import views.html.admin.organisationLocale.organisationLocale;
import views.html.admin.organisationLocale.editerEspece;
import views.html.admin.organisationLocale.ajouterEspece;



public class GererEspeceAdmin extends Controller{

	public static Result getEditEspece(Integer espece_id) {
    	Espece espece=Espece.find.where().eq("espece_id", espece_id).findUnique();
    	return ok(editerEspece.render("",espece));
    }
	
	
	 
	 public static Result getNewEspece() {
	    	return ok(ajouterEspece.render(""));
	    }
	    
	    public static Result postNewEspece() {
	    	DynamicForm df = DynamicForm.form().bindFromRequest();
	    	String espece_nom = df.get("espece_nom");
	    	String espece_auteur = df.get("espece_auteur");
	    	int espece_systematique = Integer.parseInt(df.get("espece_systematique"));
	    	String espece_commentaires = df.get("espece_commentaires");
	    	Espece espece = new Espece(espece_nom, espece_auteur, espece_systematique, espece_commentaires);
	    	espece.save();
	    	
	    	return ok(ajouterEspece.render("Informations mises a  jour avec succes"));
	    }
	    
	    public static Result postEditEspece(Integer espece_id) {
	    	Espece espece=Espece.find.where().eq("espece_id", espece_id).findUnique();
			if(espece!=null){
				DynamicForm df = DynamicForm.form().bindFromRequest();
				String espece_nom = df.get("espece_nom");
				if(Espece.find.where().eq("espece_nom", espece_nom).findUnique()==null){
					espece.espece_nom=espece_nom;
				}
				String espece_auteur = df.get("espece_auteur");
				if(Espece.find.where().eq("espece_auteur", espece_auteur).findUnique()==null){
					espece.espece_auteur=espece_auteur;
				}
				int espece_systematique = Integer.parseInt(df.get("espece_systematique"));
				if(Espece.find.where().eq("espece_systematique", espece_systematique).findUnique()==null){
					espece.espece_systematique= espece_systematique;
				}
				espece.update();
			}
			return ok(editerEspece.render("Informations mises à jour avec succès",espece));
		} 
	 
	 public static Result deleteEspece(Integer espece_id){
			Espece espece = Espece.find.byId(espece_id);
			Espece.supprEspece(espece_id);
			return ok("L'espece a bien ete supprimee.");
		}
	 
	 public static Result changerPhoto(Integer espece_id) throws IOException{
				MultipartFormData body = request().body().asMultipartFormData();
				Espece espece = Espece.find.byId(espece_id);
				FilePart fp = body.getFile("photo");
				Image photo = UploadImage.upload(fp);
				if(photo!=null){
					espece.espece_photo=photo;
					espece.update();
					return ok(editerEspece.render("image mise a  jour avec succes",espece));
				}else{
					return ok(editerEspece.render("la mise a jour de l'image a echouee",espece));
				}
		}
	
}