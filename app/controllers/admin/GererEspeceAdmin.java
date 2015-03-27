package controllers.admin;

import java.io.IOException;

import controllers.membre.SecuredMembre;
import models.*;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;
import java.util.List;


import functions.UploadImage;
import views.html.admin.organisationLocale.editerEspece;
import views.html.admin.organisationScientifique.editerEspeceScientifique;
import views.html.admin.organisationScientifique.ajouterEspeceScientifique;



public class GererEspeceAdmin extends Controller{


	/****************** classification locale *****************/

	public static Result getEditEspeceLocale(Integer espece_id) {
    	Espece espece=Espece.find.where().eq("espece_id", espece_id).findUnique();
    	return ok(editerEspece.render("", espece));
    }


	public static Result postEditEspeceLocale(Integer espece_id) {
		Espece espece=Espece.find.where().eq("espece_id", espece_id).findUnique();
		DynamicForm df = DynamicForm.form().bindFromRequest();

		// mise à jour des informations de la table espèce

		if(espece != null){
			String espece_nom = df.get("espece_nom");
			if(espece_nom!=null){
				espece.espece_nom=espece_nom;
			}
			espece.espece_auteur=df.get("espece_auteur");
			String espece_systematique_string = df.get("espece_systematique");
			if (espece_systematique_string != null) {
				espece.espece_systematique = Integer.parseInt(espece_systematique_string);
			}
			espece.espece_commentaires=df.get("espece_commentaires");
			espece.update();
		}

		// mise à jour des relations avec le groupe père

		String string_groupe_pere_id = df.get("pere");

		if (string_groupe_pere_id.equals("NULL")) {
			// si pas de parents, on supprime les relations
			List<EspeceIsInGroupementLocal> relations = EspeceIsInGroupementLocal.find.where().eq("espece",espece).findList();
			for (EspeceIsInGroupementLocal r : relations){
				r.delete();
			}
		} else {
			//TODO idéalement, il faudrait tester si la relation a changé ou pas. Pb lié à l'autoincrement de l'id dans la table EspeceIsInGroupementLocal
			// si un parent est sélectionné, on supprime les relations existantes
			List<EspeceIsInGroupementLocal> relations = EspeceIsInGroupementLocal.find.where().eq("espece",espece).findList();
			for (EspeceIsInGroupementLocal r : relations){
				r.delete();
			}
			// puis on ajoute la nouvelle
			Groupe groupePere = Groupe.find.byId(Integer.parseInt(string_groupe_pere_id));
			if (groupePere != null) {
				EspeceIsInGroupementLocal r = new EspeceIsInGroupementLocal(espece,groupePere);
				r.save();
			}
		}
		return ok(editerEspece.render("Informations mises à jour avec succès",espece));
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


	/****************** classification Scientifique *****************/
	 
	 public static Result getEditEspeceScientifique(Integer espece_id) {
	    	Espece espece=Espece.find.where().eq("espece_id", espece_id).findUnique();
	    	return ok(editerEspeceScientifique.render("",espece));
	    }
		
		
		 
		 public static Result getNewEspeceScientifique() {
		    	return ok(ajouterEspeceScientifique.render(""));
		    }
		    
		    public static Result postNewEspeceScientifique() {
		    	DynamicForm df = DynamicForm.form().bindFromRequest();
		    	String espece_nom = df.get("espece_nom");
		    	String espece_auteur = df.get("espece_auteur");
		    	int espece_systematique = Integer.parseInt(df.get("espece_systematique"));
		    	String espece_commentaires = df.get("espece_commentaires");
		    	
		    	Espece espece = new Espece(espece_nom, espece_auteur, espece_systematique, espece_commentaires);
		    	String pere = df.get("pere");
		    	
		    	espece.save();
		    	
		    	return ok(ajouterEspeceScientifique.render("Informations mises a  jour avec succes"));
		    }
		    
		    public static Result postEditEspeceScientifique(Integer espece_id) {
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
					String espece_commentaires = df.get("espece_commentaires");
					if(Espece.find.where().eq("espece_commentaires", espece_commentaires).findUnique()==null){
						espece.espece_commentaires=espece_commentaires;
					}
					espece.update();
				}
				return ok(editerEspeceScientifique.render("Informations mises à jour avec succès",espece));
			} 
		 
		 public static Result deleteEspeceScientifique(Integer espece_id){
				Espece espece = Espece.find.byId(espece_id);
				Espece.supprEspece(espece_id);
				return ok("L'espece a bien ete supprimee.");
			}
		 
		 public static Result changerPhotoScientifique(Integer espece_id) throws IOException{
					MultipartFormData body = request().body().asMultipartFormData();
					Espece espece = Espece.find.byId(espece_id);
					FilePart fp = body.getFile("photo");
					Image photo = UploadImage.upload(fp);
					if(photo!=null){
						espece.espece_photo=photo;
						espece.update();
						return ok(editerEspeceScientifique.render("image mise a  jour avec succes",espece));
					}else{
						return ok(editerEspeceScientifique.render("la mise a jour de l'image a echouee",espece));
					}
			}
	
}
