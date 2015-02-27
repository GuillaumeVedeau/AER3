/*********************************************************************************
 * 
 *   Copyright 2014 BOUSSEJRA Malik Olivier, HALDEBIQUE Geoffroy, ROYER Johan
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   
 ********************************************************************************/
package controllers.admin.groupesSousGroupes;

import controllers.admin.Admin;
import models.Groupe;
import models.SousGroupe;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.gererGroupesEtSousGroupes;

public class GererGroupesEtSousGroupes extends Controller {

	public static Result main(){
		if(Admin.isAdminConnected())
			return ok(gererGroupesEtSousGroupes.render(Groupe.findAll(), null));
		else
			return Admin.nonAutorise();
	}
	
	/**
	 * Déplace un sous-groupe dans un autre groupe
	 * @param groupeId : id du groupe à déplacer
	 * @return
	 */
	public static Result deplacerGroupe(Integer groupeId){
		if(Admin.isAdminConnected()){
			Groupe groupe = Groupe.find.byId(groupeId);
			DynamicForm df = DynamicForm.form().bindFromRequest();
			Integer groupePereId = Integer.parseInt(df.get("groupePereId"));
			Groupe groupePere = Groupe.find.byId(groupePereId);
			if(groupe!=null && groupePere!=null){
				groupe.groupe_pere=groupePere;
				groupe.save();
			}
			return redirect("/gererGroupesEtSousGroupes");
		}else
			return Admin.nonAutorise();
	}

	/**
	 * Crée un groupe et l'ajoute dans la base de données.
	 * Il ne contient aucune espèce.
	 * @return
	 */
	public static Result creerGroupe(){
		if(Admin.isAdminConnected()){
			DynamicForm df = DynamicForm.form().bindFromRequest();
			String nomGroupe = df.get("nomGroupe");
			if(!nomGroupe.equals("") && Groupe.find.where().eq("groupe_nom", nomGroupe).findList().isEmpty()){
				new Groupe(nomGroupe, "groupe").save();
			}
			return redirect("/gererGroupesEtSousGroupes");
		}else
			return Admin.nonAutorise();
	}

	/**
	 * Crée un sous-groupe et l'ajoute dans la base de données.
	 * Il ne contient aucune espèce.
	 * @return
	 */
	public static Result creerSousGroupe(){
		if(Admin.isAdminConnected()){
			DynamicForm df = DynamicForm.form().bindFromRequest();
			String nomSousGroupe = df.get("nomSousGroupe");
			Integer groupe_id = Integer.parseInt(df.get("groupeId"));
			Groupe groupe = Groupe.find.byId(groupe_id);
			if(!nomSousGroupe.equals("") &&
					Groupe.find.where().eq("groupe_nom", nomSousGroupe).findList().isEmpty()
					&& groupe!=null){
				new Groupe(nomSousGroupe, "sous-groupe", groupe).save();
			}
			return redirect("/gererGroupesEtSousGroupes");
		}else
			return Admin.nonAutorise();
	}

	/**
	 * Supprime un groupe de la base de données DEFINITIVEMENT
	 * @param groupe_id
	 * @return
	 */
	public static Result supprimerGroupe(Integer groupe_id){
		if(Admin.isAdminConnected()){
			Groupe groupe = Groupe.find.byId(groupe_id);
			if(groupe!=null)
				groupe.supprimer();
			return redirect("/gererGroupesEtSousGroupes");
		}else
			return Admin.nonAutorise();
	}
	
	/**
	 * Supprime un sous-groupe et toutes ses occurrences de la base de données DEFINITIVEMENT
	 * @param sous_groupe_id
	 * @return
	 */
	public static Result supprimerSousGroupe(Integer sous_groupe_id){
		if(Admin.isAdminConnected()){
			Groupe groupe = Groupe.find.byId(sous_groupe_id);
			if(groupe!=null)
				groupe.supprimer();
			return redirect("/gererGroupesEtSousGroupes");
		}else
			return Admin.nonAutorise();
	}
}
