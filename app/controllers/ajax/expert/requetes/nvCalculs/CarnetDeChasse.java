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
package controllers.ajax.expert.requetes.nvCalculs;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import play.mvc.Controller;
import models.Observation;
import models.InformationsComplementaires;
import models.Fiche;
import models.UTMS;
import models.FicheHasMembre;
import models.Membre;

public class CarnetDeChasse extends Controller {
	
	
	public static Map<UTMS,ArrayList<InformationsComplementaires>> calculeCarnetDeChasse(Map<String,String> info) throws ParseException {
		

    	Membre temoin = Membre.find.where().eq("membre_email", session("username")).findUnique();

		List<FicheHasMembre> fhms = FicheHasMembre.find.where().eq("membre", temoin).findList();

		List<Fiche> fiches = new ArrayList<Fiche>();
		for (FicheHasMembre fhm: fhms){
			fiches.add(0,fhm.fiche);
		}

		Map<UTMS,ArrayList<InformationsComplementaires>> observationsParMaille = new HashMap<UTMS,ArrayList<InformationsComplementaires>>();


		List<InformationsComplementaires> carnet = InformationsComplementaires.find.where()
				.in("informations_complementaires_observation.observation_fiche", fiches)
				.orderBy("informations_complementaires_observation.observation_fiche.fiche_utm asc")
				.findList();
		
		
		UTMS maille = new UTMS();
		
		if (carnet!=null && carnet.size()!=0){
			maille = carnet.get(0).informations_complementaires_observation.observation_fiche.fiche_utm;
			ArrayList<InformationsComplementaires> observationsMaille = new ArrayList<InformationsComplementaires>();
		
			for (InformationsComplementaires information : carnet){
				if (information.informations_complementaires_observation.observation_fiche.fiche_utm == maille){
					observationsMaille.add(information);

				}else {
					//on copie toutes les observations dans une deuxième liste pour pouvoir effacer la première
					ArrayList<InformationsComplementaires> observationsAAjouter = new ArrayList<InformationsComplementaires>();
					observationsAAjouter.addAll(observationsMaille);
					observationsParMaille.put(maille,observationsAAjouter);
					
					maille = information.informations_complementaires_observation.observation_fiche.fiche_utm;
					observationsMaille.clear();
					observationsMaille.add(information);

				}
				
			}
			observationsParMaille.put(maille,observationsMaille);
		}
		
		return observationsParMaille;

			
		}
	
	

}